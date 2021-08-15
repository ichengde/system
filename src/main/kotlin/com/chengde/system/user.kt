package com.chengde.system

import com.chengde.system.database.tables.references.APP
import com.chengde.system.database.tables.references.APP_OF_USER
import com.chengde.system.database.tables.references.USER
import io.vertx.ext.auth.JWTOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.jooq.CloseableDSLContext


suspend fun login(ctx: RoutingContext, db: CloseableDSLContext) {
  val body = ctx.bodyAsJson
  val username = body.getString("username")
  val password = body.getString("password")

  val user = db
    .selectFrom(USER)
    .where(USER.USERNAME.eq(username), USER.PASSWORD.eq(password))
    .fetchOne()

  if (user == null) {
    ctx.json(Response(code = 400, message = "Invalid username/password supplied", data = null))
  } else {

    val provider = JWTAuth.create(ctx.vertx(), JWTconfig)
    val token = provider.generateToken(
      json {
        obj(
          "object_id" to user.objectId,
          "username" to user.username
        )
      },
      JWTOptions().setExpiresInMinutes(ExpiresInMinutesTime)
    )

    val appIds = db.select(APP_OF_USER.APP_ID).from(APP_OF_USER).where(APP_OF_USER.USER_ID.eq(user.id)).fetch()

    val apps = db.selectFrom(APP).where(APP.ID.`in`(appIds))

    ctx.response().end(
      json {
        obj(
          "data" to obj(
            "token" to token,
            "app" to apps.map {
              return@map obj(
                "name" to it.name,
                "remark" to (it.remark ?: "")
              )
            }
          ),
          "code" to 0
        ).encode()
      }
    )
  }

}

