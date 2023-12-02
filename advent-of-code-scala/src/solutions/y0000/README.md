# Year and Day Template

## Year and Day supplied via Implicits

This project supplies the year and day as `implicit` parameters / `given` instances to `AocDayRunner`, which in turn uses them in `loadInput()(using year: Year, day: Day)` to load the input file from the `/resources/` directory.

`solutions.y0000` supplies `Year`, while `day01` imports this `given` instance and supplies its own `given` instance for `Day`.

So, when you create a file for a new day, all you need to do is increment the `given` instance for `Day`.

## AocDayRunner

Additionally, `AocDayRunner` provides a minimal `main` CLI for running the day's solution via Decline.

By default, `AocDayRunner` runs all parts, but you can pass a part to the CLI like so:

```sh
scala-cli run . --main-class solutions.y0000.day01.run -- --part a
```

## TDD

This project is setup to allow test suites right next to the implementation files, so you can test against the examples as close as possible to the implementation.

Metals also fully supports tests and incorporates them into VS Code's Test Explorer, so you don't need to run any command for the tests.
