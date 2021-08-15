/*
 * This file is generated by jOOQ.
 */
package com.chengde.system.database.tables


import com.chengde.system.database.Public
import com.chengde.system.database.keys.APP_NAME_UNIQUE
import com.chengde.system.database.keys.PK_APP_ID
import com.chengde.system.database.tables.records.AppRecord

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row3
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class App(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, AppRecord>?,
    aliased: Table<AppRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<AppRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>public.app</code>
         */
        val APP: App = App()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<AppRecord> = AppRecord::class.java

    /**
     * The column <code>public.app.id</code>.
     */
    val ID: TableField<AppRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "")

    /**
     * The column <code>public.app.name</code>.
     */
    val NAME: TableField<AppRecord, String?> = createField(DSL.name("name"), SQLDataType.VARCHAR(50).nullable(false), this, "")

    /**
     * The column <code>public.app.remark</code>.
     */
    val REMARK: TableField<AppRecord, String?> = createField(DSL.name("remark"), SQLDataType.VARCHAR(150), this, "")

    private constructor(alias: Name, aliased: Table<AppRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<AppRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.app</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.app</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.app</code> table reference
     */
    constructor(): this(DSL.name("app"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, AppRecord>): this(Internal.createPathAlias(child, key), child, key, APP, null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIdentity(): Identity<AppRecord, Int?> = super.getIdentity() as Identity<AppRecord, Int?>
    override fun getPrimaryKey(): UniqueKey<AppRecord> = PK_APP_ID
    override fun getUniqueKeys(): List<UniqueKey<AppRecord>> = listOf(APP_NAME_UNIQUE)
    override fun `as`(alias: String): App = App(DSL.name(alias), this)
    override fun `as`(alias: Name): App = App(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): App = App(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): App = App(name, null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<Int?, String?, String?> = super.fieldsRow() as Row3<Int?, String?, String?>
}