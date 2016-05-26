# Reddit plain query validator
[![Build Status](https://travis-ci.org/JJWTimmer/reddit-plain-query-validator.svg?branch=master)](https://travis-ci.org/JJWTimmer/reddit-plain-query-validator)

This is a Reddit plain query validator that parses queries using the [the FastParse library](https://lihaoyi.github.io/fastparse/). It's useful to validate the syntax of plain boolean queries when you allow input from users. In the wild it's used for querying the Reddit API.

If you want to use the CloudSearch syntax, please checkout this project: https://github.com/JJWTimmer/cloudsearch-query-validator

## Usage
Add the dependency to your build.sbt
```scala
libraryDependencies += "com.github.jjwtimmer" %% "reddit-plain-query-validator" % "0.1"
```
Use it!
```scala
import com.github.jjwtimmer.reddit.validation.RedditPlainQueryValidator
import scala.util.{Success, Failure}

// successful parse example
RedditPlainQueryValidator("subreddit:programming AND (selftext:scala OR title:scala)")

// failed parse example
RedditPlainQueryValidator("(some strange query syntax):is not [valid]")

// pattern matching example
RedditPlainQueryValidator("subreddit:programming AND (selftext:scala OR title:scala)") match {
  case Success(result) => println(s"Parsed: $result")
  case Failure(error) => println("Not a valid query")
}
```

## Disclaimer
The documented part of the [Reddit plain boolean query syntax](https://www.reddit.com/wiki/search) is now supported:

## Contributing
Pull requests are always welcome

Not sure if that typo is worth a pull request? Found a bug and know how to fix it? Do it! We will appreciate it. Any significant improvement should be documented as a [GitHub issue](https://github.com/JJWTimmer/reddit-plain-query-validator/issues) before anybody starts working on it.

I'm always thrilled to receive pull requests and will try to process them quickly. If your pull request is not accepted on the first try, don't get discouraged!

## Thanks
Please checkout this beautiful GNIP validator that was inspiration for this lib, made by my colleague Jeroen Rosenberg:
https://github.com/jeroenr/gnip-rule-validator

