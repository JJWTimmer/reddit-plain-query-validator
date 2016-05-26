package com.github.jjwtimmer.reddit.validation

import fastparse.WhitespaceApi
import org.slf4j.LoggerFactory

import scala.language.postfixOps

class RedditPlainQueryParser {
  val White = WhitespaceApi.Wrapper {
    import fastparse.all._
    NoTrace(CharIn(Seq(' ', '\t', '\n', '\r', '\f')).rep)
  }

  import White._
  import fastparse.noApi._

  implicit class RichParser[T](p: Parser[T]) {
    def + = p.rep(min = 1)

    def ++ = p.repX(min = 1)

    def * = p.rep

    def ** = p.repX
  }

  private val term = P(CharsWhile((('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9') ++ Seq('_')).contains(_))!).filter(term => !Seq("AND", "OR", "NOT").contains(term))
  private val field = P(StringIn("title", "author", "selftext", "subreddit", "url", "site", "nsfw", "self", "flair") ~~ ":" ~~ term)
  private val fieldOp = P(field | term)
  private val andOp = P("AND" ~ query)
  private val orOp = P("OR" ~ query)
  private val notOp = P("NOT" | "-")
  private val parens = P("(" ~ query ~ ")")

  private def query: Parser[Any] = P(notOp.? ~ (fieldOp | parens) ~ (andOp | orOp).? )

  def parse(rule: String) = P(Start ~ query.rep(1) ~ End).parse(rule)

}

object RedditPlainQueryValidator {

  import fastparse.core.ParseError
  import fastparse.core.Parsed._

  val log = LoggerFactory.getLogger(getClass)

  def apply(rule: String) = new RedditPlainQueryParser().parse(rule) match {
    case Success(matched, index) =>
      log.debug(s"Matched: $matched")
      scala.util.Success(matched)
    case f@Failure(lastParser, index, extra) =>
      val parseError = ParseError(f)
      log.warn(s"Failed to parse rule, expected '$lastParser'. Trace: ${parseError.getMessage}")
      scala.util.Failure(parseError)
  }
}
