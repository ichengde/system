/*
 * This file is generated by jOOQ.
 */
package com.chengde.system.database


import com.chengde.system.database.tables.App
import com.chengde.system.database.tables.AppOfUser
import com.chengde.system.database.tables.Note
import com.chengde.system.database.tables.User
import com.chengde.system.database.tables.Website

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC: Public = Public()
    }

    /**
     * The table <code>public.app</code>.
     */
    val APP: App get() = App.APP

    /**
     * The table <code>public.app_of_user</code>.
     */
    val APP_OF_USER: AppOfUser get() = AppOfUser.APP_OF_USER

    /**
     * The table <code>public.note</code>.
     */
    val NOTE: Note get() = Note.NOTE

    /**
     * The table <code>public.user</code>.
     */
    val USER: User get() = User.USER

    /**
     * The table <code>public.website</code>.
     */
    val WEBSITE: Website get() = Website.WEBSITE

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        App.APP,
        AppOfUser.APP_OF_USER,
        Note.NOTE,
        User.USER,
        Website.WEBSITE
    )
}
