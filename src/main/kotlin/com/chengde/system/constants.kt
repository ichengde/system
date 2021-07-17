package com.chengde.system

import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuthOptions


const val dbIdentity = "postgres"

var connectionUri: String = System.getenv("connectionUri")

const val port = 18889

val JWTconfig: JWTAuthOptions = JWTAuthOptions()
  .addPubSecKey(
    PubSecKeyOptions()
      .setAlgorithm("HS256")
      .setBuffer("system-.,")
  )

const val ExpiresInMinutesTime = 24 * 60
