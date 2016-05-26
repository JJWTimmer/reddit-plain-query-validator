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
    "accept compound query" in {
      RedditPlainQueryValidator("(NOT selftext:buzz) AND (subreddit:disney OR subreddit:pixar)").success
    }
  }

}
