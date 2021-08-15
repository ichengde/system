/*
 * This file is generated by jOOQ.
 */
package com.chengde.system.database.tables.records


import com.chengde.system.database.tables.User

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class UserRecord() : UpdatableRecordImpl<UserRecord>(User.USER), Record4<Int?, String?, String?, String?> {

    var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    var username: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    var password: String?
        set(value): Unit = set(2, value)
        get(): String? = get(2) as String?

    var objectId: String?
        set(value): Unit = set(3, value)
        get(): String? = get(3) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<Int?, String?, String?, String?> = super.fieldsRow() as Row4<Int?, String?, String?, String?>
    override fun valuesRow(): Row4<Int?, String?, String?, String?> = super.valuesRow() as Row4<Int?, String?, String?, String?>
    override fun field1(): Field<Int?> = User.USER.ID
    override fun field2(): Field<String?> = User.USER.USERNAME
    override fun field3(): Field<String?> = User.USER.PASSWORD
    override fun field4(): Field<String?> = User.USER.OBJECT_ID
    override fun component1(): Int? = id
    override fun component2(): String? = username
    override fun component3(): String? = password
    override fun component4(): String? = objectId
    override fun value1(): Int? = id
    override fun value2(): String? = username
    override fun value3(): String? = password
    override fun value4(): String? = objectId

    override fun value1(value: Int?): UserRecord {
        this.id = value
        return this
    }

    override fun value2(value: String?): UserRecord {
        this.username = value
        return this
    }

    override fun value3(value: String?): UserRecord {
        this.password = value
        return this
    }

    override fun value4(value: String?): UserRecord {
        this.objectId = value
        return this
    }

    override fun values(value1: Int?, value2: String?, value3: String?, value4: String?): UserRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised UserRecord
     */
    constructor(id: Int? = null, username: String? = null, password: String? = null, objectId: String? = null): this() {
        this.id = id
        this.username = username
        this.password = password
        this.objectId = objectId
    }
}