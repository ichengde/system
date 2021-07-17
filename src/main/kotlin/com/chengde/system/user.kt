package com.chengde.system

import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.JWTOptions
import io.vertx.ext.auth.authentication.AuthenticationProvider
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.array
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
  val rs = con.preparedQuery(
    """
      SELECT *
      FROM PUBLIC.USER
      WHERE USERNAME = $1
        AND PASSWORD = $2
    """.trimIndent()
  )
    .execute(Tuple.of(username, password)).await()

  if (rs.count() == 0) {
    ctx.response().end(json {
      obj(
        "message" to "Invalid username/password supplied",
        "code" to 400
      ).encode()
    })
  } else {

    val user = rs.first()

    val provider = JWTAuth.create(ctx.vertx(), JWTconfig)
    val token = provider.generateToken(
      json {
        obj(
          "object_id" to user.getString("object_id"),
          "username" to user.getString("username")
        )
      },
      JWTOptions().setExpiresInMinutes(ExpiresInMinutesTime)
    )

    val apps =
      con.preparedQuery(
        """
          SELECT name, remark
          FROM APP
          WHERE ID in
              (SELECT APP_ID
                FROM PUBLIC.APP_OF_USER
                WHERE USER_ID =
                    (SELECT ID
                      FROM PUBLIC.USER
                      WHERE USERNAME = $1
                        AND PASSWORD = $2))
        """.trimIndent()
      ).execute(Tuple.of(username, password)).await()

    ctx.response().end(
      json {
        obj(
          "data" to obj(
            "token" to token,
            "app" to apps.map {
              return@map obj(
                "name" to it.getString("name"),
                "remark" to (it.getString("remark") ?: "")
              )
            }
          ),
          "code" to 0
        ).encode()
      }
    )
  }

  con.close()
}
