package com.chengde.system

import com.chengde.system.service.getNote
import com.chengde.system.service.note
import io.vertx.ext.auth.User
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.openapi.Operation
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.BadRequestException
import io.vertx.ext.web.validation.BodyProcessorException
import io.vertx.ext.web.validation.ParameterProcessorException
import io.vertx.ext.web.validation.RequestPredicateException
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.pgclient.PgPool
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class App : CoroutineVerticle() {

  override suspend fun start() {
    val router = Router.router(vertx)
    val routerBuilder = RouterBuilder.create(vertx, "src/main/resources/spec/api.yaml").await()

    routerBuilder.rootHandler(corsHandling())
    routerBuilder.rootHandler(userInfoHandling())
    routerBuilder.rootHandler(dealBody())
    routerBuilder.rootHandler(appendDataBase())


    routerBuilder.operation("loginUser").coroutineHandler { ctx -> login(ctx) }
    routerBuilder.operation("getNote").coroutineHandler { ctx -> getNote(ctx) }
    val openApiRouter = routerBuilder.createRouter()

    router.route("/note").coroutineHandler { ctx -> note(ctx) }

    vertx
      .createHttpServer()
      .requestHandler(router)
      .requestHandler(openApiRouter)
      .listen(port) { http ->
        if (http.succeeded()) {
          println("HTTP server started on port $port")
        }
      }
  }



  private fun Route.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
    handler { ctx ->
      launch(ctx.vertx().dispatcher() as CoroutineContext) {
        try {
          fn(ctx)
        } catch (e: Exception) {
          ctx.fail(e)
          throw e
        }
      }
    }
  }


  private fun Operation.coroutineHandler(fn: suspend (RoutingContext) -> Unit) {
    handler { ctx ->
      launch(ctx.vertx().dispatcher() as CoroutineContext) {
        try {
          fn(ctx)
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

