
# Advent of Code in Haskell

## Prerequisites

In order to work with this codebase, you need:

TBD

### Why Stack?

TBD

## Solutions

The solutions are all under `/src/solutions/`, organized by year.

Check [the progress chart](./PROGRESS.md) to see how many days I've completed.

Please also take a look [at the credits for this project](./CREDITS.md).

## How to run the solutions

Each day file is its own program entry. The `/src/core/` directory is a common library does not contain any program entrypoints.

To run the solutions, please follow these directions:

1. Check the structure of the `/src/solutions/` directory. Make a note of the names of the nested objects.
2. Open a terminal and run `scala-cli run --main.class solutions.y<year number>.d<day number>.run`. For instance, `scala-cli run --main-class solutions.y2015.day01.run`.
3. Scala-CLI will take a couple moments to to start the Bloop build server, collect dependencies, and then run the program.
4. That's it! If all goes well, you'll see the program's output in your terminal.

> [!WARNING]
> Don't forget to add the `.run` part!
> That part corresponds to the main class.

### How to run specific parts

The command above runs both parts of the day's puzzle solution. If you would like to run just one part, you can do that by adding `--part (a or b)` as an additional command line argument like so:

```sh
scala-cli run --main-class solutions.y<year number>.d<day number>.run`
```

For instance,

```sh
scala-cli run --main-class solutions.y2015.day01.run -- --part a
```

This will run part 1 of day 1 in year 2015.

Note the extra `--`! That separates commands to `scala-cli` from commands to the program.

### Use Scala-CLI to Find Main Classes

You can use Scala-CLI to find available main classes in this project. Just run `scala-cli run .` in the root directory!

Scala-CLI will then print out a list of available main classes, along with an example and more info on how to run them.

## How to interact with this project through GHCi

You can explore this whole project using the Scala REPL. Just run `scala-cli repl .` in the root directory - you will then have a Scala REPL in the context of this project.

You can then import and explore any package in this project, like so:

TBD

## Assumptions

I am assuming:

* You have downloaded and installed the Scala toolchain via Coursier and verified that it is working.
* You have downloaded and installed Scala-CLI and verified that it is working. (You might already have it via Coursier!)
* You are using a platform Scala readily supports.
* You are somewhat familiar with Scala and its standard library.

## How this repository is organized

This repository consists of a `/src/core/` directory that holds shared code and a `/src/solutions/` directory that consists of binary entrypoints which may or may not use code from the `/src/core/` directory.

It is forbidden for code files in `/src/core/` to import code from outside this directory, excluding external dependencies.

Every `.scala` file in `/src/solutions/` has a main class (i.e., is a binary entrypoint).

`/src/common.scala` is a prelude-like module that re-exports stuff from `/src/core/` and external dependencies.

Code in `/src/solutions/` may import `/src/common.scala` but must later be replaced with more explicit imports.

## License

This repository is licensed under the GNU Affero General Public License Version 3 or later.

As Haskell may be transpiled to JavaScript using GHCJS (or converted to PureScript), it is possible to use this repository as a web service; for instance, as the backend or part of a backend for a web visualization or on the frontend.

As such, I have applied the AGPL to ensure any changes and/or improvements to this repository are made public in kind.
