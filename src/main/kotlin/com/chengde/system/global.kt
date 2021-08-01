package com.chengde.system

import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.pgclient.PgPool


fun userInfoHandling(): (RoutingContext) -> Unit {
  return { ctx ->

    val failHandler = {
      ctx.response().statusCode = 401
      ctx.json(json {
        obj(
          "message" to "auth fail"
        )
      })
    }

    val provider = JWTAuth.create(ctx.vertx(), JWTconfig)
    val token = ctx.request().getHeader("token")

    if (jumpURL.contains(ctx.normalizedPath())) {
      ctx.next()
    }

    if (token != null) {
      provider.authenticate(json {
        obj(
          "token" to token,
          "options" to JWTconfig
        )
      }).onSuccess { user ->
        ctx.setUser(user)

        println(user)
        ctx.next()
      }.onFailure { err ->
        println(err)

        failHandler()
      }
    } else {
      failHandler()
    }
  }
}


fun corsHandling(): CorsHandler {
  return CorsHandler.create()
    .addOrigin("http://localhost:52522")
    .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
    .allowedMethod(io.vertx.core.http.HttpMethod.GET)
    .allowedMethod(io.vertx.core.http.HttpMethod.POST)
    .allowedMethod(io.vertx.core.http.HttpMethod.PUT)
    .allowedMethod(io.vertx.core.http.HttpMethod.DELETE)
}

fun dealBody(): BodyHandler? {
  return BodyHandler.create()
}

fun appendDataBase(): (RoutingContext) -> Unit {
  return { ctx: RoutingContext ->
    val pool = PgPool.pool(ctx.vertx(), connectionUri)

    ctx.put(dbIdentity, pool.connection.onFailure { connectResult -> println(connectResult) });
    ctx.next()
  }
}
