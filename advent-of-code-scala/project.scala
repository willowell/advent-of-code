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

//> using scala 3.3.1

// MINIMUM JVM VERSION: 21

//====================================================================================================================//
// DEPENDENCIES                                                                                                       //
//====================================================================================================================//
/*
  Dependencies Overview
*/

/* Toolkits */
//> using dep "org.typelevel::toolkit:0.1.17"

/* Test Frameworks */
//> using dep "org.scalameta::munit:1.0.0-M10"

/* Typelevel */
//> using dep "org.typelevel::cats-core:2.10.0"
//> using dep "org.typelevel::cats-free:2.10.0"
//> using dep "org.typelevel::cats-mtl:1.3.1"
//> using dep "org.typelevel::kittens:3.0.0"
//> using dep "org.typelevel::spire:0.18.0"

/* Circe */
//> using dep "io.circe::circe-core:0.14.6"
//> using dep "io.circe::circe-generic:0.14.6"
//> using dep "io.circe::circe-parser:0.14.6"

/* Monocle */
//> using dep "dev.optics::monocle-core:3.2.0"

/* Scodec */
//> using dep "org.scodec::scodec-bits:1.1.37"

/* Parsley */
//> using dep "com.github.j-mie6::parsley:4.4.0"

/* lihaoyi */
//> using dep "com.lihaoyi::fansi:0.4.0"
//> using dep "com.lihaoyi::os-lib:0.9.1"
//> using dep "com.lihaoyi::requests:0.8.0"

/* sttp */
//> using dep "com.softwaremill.sttp.client4::core:4.0.0-M5"

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
