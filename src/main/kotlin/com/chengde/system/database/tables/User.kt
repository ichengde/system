/*
 * This file is generated by jOOQ.
 */
package com.chengde.system.database.tables


import com.chengde.system.database.Public
import com.chengde.system.database.keys.PK_USER_ID
import com.chengde.system.database.keys.USER_USERNAME_UNIQUE
import com.chengde.system.database.tables.records.UserRecord

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row4
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
open class User(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserRecord>?,
    aliased: Table<UserRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<UserRecord>(
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
         * The reference instance of <code>public.user</code>
         */
        val USER: User = User()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<UserRecord> = UserRecord::class.java

    /**
     * The column <code>public.user.id</code>.
     */
    val ID: TableField<UserRecord, Int?> = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "")

    /**
     * The column <code>public.user.username</code>.
     */
    val USERNAME: TableField<UserRecord, String?> = createField(DSL.name("username"), SQLDataType.VARCHAR(50).nullable(false), this, "")

    /**
     * The column <code>public.user.password</code>.
     */
    val PASSWORD: TableField<UserRecord, String?> = createField(DSL.name("password"), SQLDataType.VARCHAR(50).nullable(false), this, "")

    /**
     * The column <code>public.user.object_id</code>.
     */
    val OBJECT_ID: TableField<UserRecord, String?> = createField(DSL.name("object_id"), SQLDataType.VARCHAR(50), this, "")

    private constructor(alias: Name, aliased: Table<UserRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<UserRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.user</code> table reference
     */
    constructor(): this(DSL.name("user"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, UserRecord>): this(Internal.createPathAlias(child, key), child, key, USER, null)
    override fun getSchema(): Schema? = if (aliased()) null else Public.PUBLIC
    override fun getIdentity(): Identity<UserRecord, Int?> = super.getIdentity() as Identity<UserRecord, Int?>
    override fun getPrimaryKey(): UniqueKey<UserRecord> = PK_USER_ID
    override fun getUniqueKeys(): List<UniqueKey<UserRecord>> = listOf(USER_USERNAME_UNIQUE)
    override fun `as`(alias: String): User = User(DSL.name(alias), this)
    override fun `as`(alias: Name): User = User(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): User = User(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): User = User(name, null)

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row4<Int?, String?, String?, String?> = super.fieldsRow() as Row4<Int?, String?, String?, String?>
}