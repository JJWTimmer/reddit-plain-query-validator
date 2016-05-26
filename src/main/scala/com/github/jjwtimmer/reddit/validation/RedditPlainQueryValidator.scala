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

  private val term = P(CharsWhile((('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9') ++ Seq('_')).contains(_))!).filter(term => !Seq("AND", "OR", "NOT").contains(term)).log()
  private val field = P(StringIn("title", "author", "selftext", "subreddit", "url", "site", "nsfw", "self", "flair") ~~ ":" ~~ term).log()
  private val fieldOp = P(field | term).log()
  private val andOp = P("AND" ~ query).log()
  private val orOp = P("OR" ~ query).log()
  private val notOp = P("NOT" | "-").log()
  private val parens = P("(" ~ query ~ ")").log()

  private def query: Parser[Any] = P((notOp ?) ~ (fieldOp | parens) ~ (andOp | orOp).? ).log()

  def parse(rule: String) = P(Start ~ query ~ End).log().parse(rule)

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
