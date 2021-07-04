package com.chengde.system

import io.vertx.core.Future
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.await
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.Tuple


suspend fun login(ctx: RoutingContext) {
  val db = ctx.get<Future<SqlConnection>>(dbIdentity)
  val body = ctx.bodyAsJson
  val username = body.getString("username")
  val password = body.getString("password")

  val con = db.await()
  val res = con.preparedQuery("select * from public.user where username=$1 and password=$2")
    .execute(Tuple.of(username, password))

  val rs = res.await()
  if (rs.count() == 0) {
    ctx.response().end(json {
      obj(
        "data" to "Not found",
        "code" to 404
      ).encode()
    })
  }

  val user = rs.first()
  ctx.response().end(
    json {
      obj(
        "data" to obj(
          "username" to user.getString("username"),
          "object_id" to user.getString("object_id")
        ),
        "code" to 0
      ).encode()
    }
  )

  con.close()
}
