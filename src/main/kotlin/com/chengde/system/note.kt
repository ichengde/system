package com.chengde.system.service

import com.chengde.system.dbIdentity
import io.vertx.core.Future
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.awaitEvent
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.pgclient.PgPool
import io.vertx.sqlclient.SqlClient
import io.vertx.sqlclient.SqlConnection
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.*

suspend fun note(ctx: RoutingContext) {
  val db = ctx.get<Future<SqlConnection>>(dbIdentity)
  val con = db.await()
  con.close()


  val response = ctx.response();
  response.putHeader("content-type", "text/plain");
  response.end("test");
}
