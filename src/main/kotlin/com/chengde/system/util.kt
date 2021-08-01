package com.chengde.system

import io.vertx.core.json.JsonObject
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

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
