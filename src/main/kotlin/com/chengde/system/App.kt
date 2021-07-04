package com.chengde.system

import com.chengde.system.service.note
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.pgclient.PgPool
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class App : CoroutineVerticle() {
  val pool = PgPool.pool(vertx, connectionUri)
  val router: Router = Router.router(vertx)

  override suspend fun start() {
    dealBody(router)
    appendDadaBase(router)
    router.route("/note").coroutineHandler { ctx -> note(ctx) }
    router.route("/login").coroutineHandler { ctx -> login(ctx) }

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(port) { http ->
        if (http.succeeded()) {
          println("HTTP server started on port $port")
        }
      }
  }

  private fun dealBody(router: Router) {
    router.route().handler(BodyHandler.create())
  }

  private fun appendDadaBase(router: Router) {
    router.route().handler { ctx ->
      ctx.put(dbIdentity, pool.connection.onFailure { connectResult -> println(connectResult) });
      ctx.next();
    }
  }

  fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
    handler { ctx ->
      launch(ctx.vertx().dispatcher() as CoroutineContext) {
        try {
          fn(ctx)
        } catch (e: Exception) {
          ctx.fail(e)
        }
      }
    }
  }

}
