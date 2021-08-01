package com.chengde.system

import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpMethod
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.RunTestOnContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import io.vertx.kotlin.coroutines.await
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(VertxUnitRunner::class)
class TestMainVerticle {

  @Rule
  @JvmField
  val rule = RunTestOnContext()

  @Before
  fun deploy_verticle(testContext: TestContext) {
    val vertx = rule.vertx()
    vertx.deployVerticle(App(), testContext.asyncAssertSuccess())
  }

  @Test
  fun verticle_deployed(testContext: TestContext) {
    val async = testContext.async()
    async.complete()
  }

  @Test
  fun whenNoteNullToken(testContext: TestContext) {
    val async = testContext.async()

    val client: HttpClient = rule.vertx().createHttpClient()
    client.request(HttpMethod.GET, port, "127.0.0.1", "/note/1") { req ->
      req.result().send(
      )
        .onSuccess { resp ->
          resp.body { dsds ->
            testContext.assertEquals("{\"message\":\"auth fail\"}", dsds.result().toString())
            async.complete()
          }
        }
    }

  }
}
