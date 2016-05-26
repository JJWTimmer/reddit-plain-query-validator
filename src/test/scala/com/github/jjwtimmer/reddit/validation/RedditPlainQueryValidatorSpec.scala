package com.github.jjwtimmer.reddit.validation

import org.scalatest._

class RedditPlainQueryValidatorSpec extends WordSpec with MustMatchers with TryValues {
  "Reddit Plain Query parser" should {
    "accept field query short" in {
      RedditPlainQueryValidator("selftext:'test query'").success
    }
    "accept field query long" in {
      RedditPlainQueryValidator("(field selftext 'test query')").success
    }
    "accept compound AND-query" in {
      RedditPlainQueryValidator("(and (field selftext 'buzz lightyear') (field title 'buzz lightyear'))").success
    }
    "accept matchall-query" in {
      RedditPlainQueryValidator("matchall").success
    }
    "not accept compound matchall-query" in {
      RedditPlainQueryValidator("(and (field selftext 'buzz lightyear') matchall)").failure
    }
    "accept compound OR-query" in {
      RedditPlainQueryValidator("(and (field selftext 'buzz lightyear') (field title 'buzz lightyear'))").success
    }
    "accept compound date range-query" in {
      RedditPlainQueryValidator("(field selftext {,'2016-05-20T13:57:35Z'])").success
    }
    "accept NOT-query" in {
      RedditPlainQueryValidator("(not (field selftext 'buzz lightyear'))").success
    }
    "accept phrase-query" in {
      RedditPlainQueryValidator("(phrase field=selftext boost=10 'some phrase to find')").success
    }
    "accept near-query" in {
      RedditPlainQueryValidator("(near field=selftext boost=10 distance=3 'wordle')").success
    }
    "accept prefix-query" in {
      RedditPlainQueryValidator("(prefix field=selftext 'prefi')").success
    }
    "accept another range-query" in {
      RedditPlainQueryValidator("(range field=numbers [1, 100])").success
    }
    "accept term-query" in {
      RedditPlainQueryValidator("(term field=other_field boost=0.9 500)").success
    }
    "accept reddit timestamp query" in {
      RedditPlainQueryValidator("timestamp:1464003707..1464104707").success
    }
    "accept boundingBox query" in {
      RedditPlainQueryValidator("(field place ['-50.4, 4.56', '45, -4.36'])").success
    }
    "accept this difficult query" in {
      RedditPlainQueryValidator("(and (field selftext 'hue') subreddit:'philips' (not (field author 'osram')) (or timestamp:1464003707..1464104707 (phrase field=title boost=10 'mood lighting')))").success
    }
  }

}
