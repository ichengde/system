package com.chengde.system

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.openapi.Operation
import io.vertx.ext.web.validation.BadRequestException
import io.vertx.ext.web.validation.BodyProcessorException
import io.vertx.ext.web.validation.ParameterProcessorException
import io.vertx.ext.web.validation.RequestPredicateException
import io.vertx.kotlin.coroutines.dispatcher
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet

import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.kotlin.core.json.Json
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.launch

import kotlinx.coroutines.launch
import org.jooq.CloseableDSLContext
import org.jooq.impl.DSL
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext

fun RowSet<Row>.toJson(): List<JsonObject> = asSequence().map { row ->
  val json = JsonObject()
  columnsNames().asSequence().forEach {
    json.put(it, toPgType(row.getValue(it)))
  }
  json
}.toList()

private fun toPgType(value: Any?): Any? = when (value) {
  is String -> value
  is LocalDate -> value.format(DateTimeFormatter.ISO_DATE)
  is LocalDateTime -> value.format(DateTimeFormatter.ISO_DATE_TIME)
  is OffsetDateTime -> value.format(DateTimeFormatter.ISO_DATE_TIME)
  else -> value
}

fun errorCondition(code: Number?, message: String?, data: Any?): JsonObject {
  return json {
    obj(
      "message" to message,
      "code" to code,
      "data" to data
    )
  }
}

data class Response(val code: Int = 0, val message: String? = "success", val data: Any? = null)
data class Pagination(val total: Int, val data: Any? = null)
