/*
 * This file is generated by jOOQ.
 */
package com.chengde.system.database.tables.records


import com.chengde.system.database.tables.App

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record3
import org.jooq.Row3
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class AppRecord() : UpdatableRecordImpl<AppRecord>(App.APP), Record3<Int?, String?, String?> {

    var id: Int?
        set(value): Unit = set(0, value)
        get(): Int? = get(0) as Int?

    var name: String?
        set(value): Unit = set(1, value)
        get(): String? = get(1) as String?

    var remark: String?
        set(value): Unit = set(2, value)
        get(): String? = get(2) as String?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<Int?> = super.key() as Record1<Int?>

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row3<Int?, String?, String?> = super.fieldsRow() as Row3<Int?, String?, String?>
    override fun valuesRow(): Row3<Int?, String?, String?> = super.valuesRow() as Row3<Int?, String?, String?>
    override fun field1(): Field<Int?> = App.APP.ID
    override fun field2(): Field<String?> = App.APP.NAME
    override fun field3(): Field<String?> = App.APP.REMARK
    override fun component1(): Int? = id
    override fun component2(): String? = name
    override fun component3(): String? = remark
    override fun value1(): Int? = id
    override fun value2(): String? = name
    override fun value3(): String? = remark

    override fun value1(value: Int?): AppRecord {
        this.id = value
        return this
    }

    override fun value2(value: String?): AppRecord {
        this.name = value
        return this
    }

    override fun value3(value: String?): AppRecord {
        this.remark = value
        return this
    }

    override fun values(value1: Int?, value2: String?, value3: String?): AppRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        return this
    }

    /**
     * Create a detached, initialised AppRecord
     */
    constructor(id: Int? = null, name: String? = null, remark: String? = null): this() {
        this.id = id
        this.name = name
        this.remark = remark
    }
}