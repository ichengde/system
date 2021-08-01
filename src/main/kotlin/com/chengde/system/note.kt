package com.chengde.system.service

import com.chengde.system.dbIdentity
import com.chengde.system.toJson
import io.vertx.core.Future
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple
import kotlinx.coroutines.*

suspend fun note(ctx: RoutingContext) {
  val db = ctx.get<Future<SqlConnection>>(dbIdentity)
  val con = db.await()
  con.close()


  val response = ctx.response();
  response.putHeader("content-type", "text/plain");
  response.end("test");
}


suspend fun getNote(ctx: RoutingContext) {
  val db = ctx.get<Future<SqlConnection>>(dbIdentity)
  val con = db.await()

  val id = ctx.pathParam("id")
  val rs = con.preparedQuery(
    """
      SELECT *
      FROM PUBLIC.note
      WHERE id = $1
    """.trimIndent()
  )
    .execute(Tuple.of(id)).await()

  con.close()


  ctx.json(rs.toJson())
}
