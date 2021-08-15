package com.chengde.system.service

import com.chengde.system.*
import com.chengde.system.database.tables.references.NOTE
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.*
import org.jooq.CloseableDSLContext


fun getNote(ctx: RoutingContext, db: CloseableDSLContext) {
  val id = ctx.pathParam("id")

  val note = db.selectFrom(NOTE).where(NOTE.ID.eq(id)).fetchOne()

  if (note !== null) {
    ctx.json(note)
  } else {
    ctx.json(Response(code = 401, message = "not Found", data = null))
  }
}


fun createNote(ctx: RoutingContext, db: CloseableDSLContext) {
  val paramsJson = ctx.bodyAsJson

  val newNote = db.newRecord(NOTE)
  newNote.content = paramsJson.getString("content")
  newNote.title = paramsJson.getString("title")
  newNote.type = paramsJson.getNumber("type").toShort()
  newNote.userid = ctx.user().get("object_id")

  val res = newNote.store()

  ctx.json(if (res > 0) Response() else Response(code = 400, message = "create failed"))
}


fun updateNote(ctx: RoutingContext, db: CloseableDSLContext) {
  val paramsJson = ctx.bodyAsJson
  val note = db.fetchOne(
    NOTE,
    NOTE.ID.eq(paramsJson.getString("id")),
    NOTE.USERID.eq(ctx.user().get<String>("object_id")),
  )

  if (note != null) {
    val currentVersion = paramsJson.getInteger("version")
    if (note.version == currentVersion) {

      note.content = paramsJson.getString("content")
      note.title = paramsJson.getString("title")
      note.type = paramsJson.getNumber("type").toShort()
      note.userid = ctx.user().get("object_id")
      note.version = currentVersion + 1

      val res = note.store()
      ctx.json(if (res > 0) Response() else Response(code = 400, message = "update failed"))
    } else {
      ctx.json(Response(code = 400, message = "outdated"))
    }

  } else {
    ctx.json(Response(code = 400, message = "not found"))
  }
}

fun listNote(ctx: RoutingContext, db: CloseableDSLContext) {
  val page = ctx.pathParam("page")
  val list = db.selectFrom(NOTE)
    .orderBy(NOTE.CREATETIME)
    .offset(page.toInt() * 30)
    .limit(30).fetch()

  ctx.response().end(list.formatJSON())
}

fun listCount(ctx: RoutingContext, db: CloseableDSLContext) {
  val total = db.selectFrom(NOTE).where(NOTE.USERID.eq(ctx.user().get<String>("object_id"))).count()

  ctx.json(Pagination(total))
}
