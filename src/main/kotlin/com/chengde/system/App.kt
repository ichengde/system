package com.chengde.system

import com.chengde.system.service.*
import io.vertx.core.Handler
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.validation.BadRequestException
import io.vertx.ext.web.validation.BodyProcessorException
import io.vertx.ext.web.validation.ParameterProcessorException
import io.vertx.ext.web.validation.RequestPredicateException
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch
import org.jooq.*
import org.jooq.impl.*
import java.sql.*
import javax.sql.*
import kotlin.coroutines.CoroutineContext

class App : CoroutineVerticle() {

  override suspend fun start() {
    val router = Router.router(vertx)
    router.route().handler(corsHandling())
    router.route().handler(userInfoHandling())
    router.route().handler(dealBody())

    router.route("/user/login").coroutineHandler { ctx, db -> login(ctx, db)}

    router.get("/note/:id").coroutineHandler { ctx, db -> getNote(ctx, db) }
    router.post("/note/create").coroutineHandler { ctx, db -> createNote(ctx, db) }
    router.post("/note/update").coroutineHandler { ctx, db -> updateNote(ctx, db) }
    router.get("/note/list/:page").coroutineHandler { ctx, db -> listNote(ctx, db) }
    router.get("/note/list/count").coroutineHandler { ctx, db -> listCount(ctx, db) }

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(port) { http ->
        if (http.succeeded()) {
          println("HTTP server started on port $port")
        }
      }
  }


  private fun Route.coroutineHandler(fn: suspend (RoutingContext, CloseableDSLContext) -> Unit) {
    handler { ctx ->
      launch(ctx.vertx().dispatcher() as CoroutineContext) {
        try {

          DSL.using(
            System.getenv("URL"),
            System.getenv("USER"),
            System.getenv("PASSWORD")
          ).use {
            fn(ctx, it)
          }

        } catch (e: Exception) {
          ctx.fail(e)
          throw e
        }
      }
    }.failureHandler {

      if (it.failure() is BadRequestException) {
        it.json((it.failure() as BadRequestException).toJson())

        if (it.failure() is ParameterProcessorException) {
          // Something went wrong while parsing/validating a parameter
        } else if (it.failure() is BodyProcessorException) {
          // Something went wrong while parsing/validating the body
        } else if (it.failure() is RequestPredicateException) {
          // A request predicate is unsatisfied
        }
      }

    }
  }


}
