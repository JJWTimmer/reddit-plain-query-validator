package com.github.jjwtimmer.reddit.validation

import org.scalatest._

class RedditPlainQueryValidatorSpec extends WordSpec with MustMatchers with TryValues {
  "Reddit Plain Query parser" should {
    "accept term query" in {
      RedditPlainQueryValidator("foobar").success
    }
    "accept field query" in {
      RedditPlainQueryValidator("selftext:buzzbuzz").success
    }
    "accept multi field query" in {
      RedditPlainQueryValidator("foobar baz selftext:programming (foo AND bar)").success
    }
    "not accept nonexisting field query" in {
      RedditPlainQueryValidator("parser:validator").failure
    }
    "accept AND query" in {
      RedditPlainQueryValidator("star AND wars").success
    }
    "accept OR query" in {
      RedditPlainQueryValidator("starwars OR startrek").success
    }
    "accept NOT query" in {
      RedditPlainQueryValidator("NOT startrek").success
    }
    "accept - query" in {
      RedditPlainQueryValidator("-starwars").success
    }
    "accept parens query" in {
      RedditPlainQueryValidator("(wordle)").success
    }
    "accept negated parens query" in {
      RedditPlainQueryValidator("NOT (a OR b)").success
    }
    "accept compound query" in {
      RedditPlainQueryValidator("(NOT selftext:buzz) AND (subreddit:disney OR subreddit:pixar)").success
    }
    "accept compound query 2" in {
      RedditPlainQueryValidator("-programming OR -scala AND (subreddit:programming OR NOT subreddit:softwareengineering)").success
    }
  }

}
