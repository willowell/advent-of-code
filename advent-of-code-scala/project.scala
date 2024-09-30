//====================================================================================================================//
// My Advent of Code solutions using Scala 3!
//
//
// Author: William Howell <wlm.howell@gmail.com>
//
//====================================================================================================================//

//====================================================================================================================//
// SCALA VERSION AND PLATFORMS                                                                                        //
//====================================================================================================================//

//> using scala 3.3.3

//> using jvm 21

//====================================================================================================================//
// DEPENDENCIES                                                                                                       //
//====================================================================================================================//
/*
  Dependencies Overview
*/

/* Toolkits */
//> using dep "org.typelevel::toolkit:0.1.27"
//> using dep "org.typelevel::toolkit-test:0.1.27"

/* Typelevel */
//> using dep "org.typelevel::cats-core:2.12.0"
//> using dep "org.typelevel::cats-free:2.12.0"
//> using dep "org.typelevel::cats-mtl:1.5.0"
//> using dep "org.typelevel::cats-parse:1.0.0"
//> using dep "org.typelevel::kittens:3.4.0"
//> using dep "org.typelevel::spire:0.18.0"

/* Circe */
//> using dep "io.circe::circe-core:0.14.9"
//> using dep "io.circe::circe-generic:0.14.9"
//> using dep "io.circe::circe-parser:0.14.9"

/* Monocle */
//> using dep "dev.optics::monocle-core:3.3.0"

/* Scodec */
//> using dep "org.scodec::scodec-bits:1.2.1"

/* Parsley */
//> using dep "com.github.j-mie6::parsley:4.5.2"

/* lihaoyi */
//> using dep "com.lihaoyi::fansi:0.5.0"
//> using dep "com.lihaoyi::os-lib:0.10.5"
//> using dep "com.lihaoyi::requests:0.9.0"

/* sttp */
//> using dep "com.softwaremill.sttp.client4::core:4.0.0-M5"

/* Math Stuff */
//> using dep "ai.dragonfly::slash:0.3.1"
//> using dep "com.manyangled::coulomb-core:0.8.0"
//> using dep "com.manyangled::coulomb-units:0.8.0"

//====================================================================================================================//
// RESOURCES                                                                                                          //
//====================================================================================================================//

//> using resourceDir ./resources

//====================================================================================================================//
// SCALA COMPILER OPTIONS                                                                                             //
//====================================================================================================================//
/*
  Scala Compiler Options Overview
*/

//> using option -deprecation
//> using option -explain
//> using option -feature
//> using option -source:future
//> using option -unchecked

//> using option -Werror
//> using option -Wvalue-discard

//> using option -Yexplicit-nulls
//> using option -Ykind-projector
//> using option -Ysafe-init

//====================================================================================================================//
// JAVA VIRTUAL MACHINE OPTIONS                                                                                       //
//====================================================================================================================//
/*
  Java Virtual Machine Options Overview
*/
