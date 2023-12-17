package com.bignerdranch.android.wanderword

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Database schema constants
    companion object {
        const val DATABASE_NAME = "wanderword_database_full.db"
        const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_COLLECTED_ITEMS = "collected_items"
        const val COLUMN_FRIENDS = "friends"


        // SQL query to create the Users table
        const val CREATE_USERS_TABLE = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_COLLECTED_ITEMS TEXT," +
                "$COLUMN_FRIENDS TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create tables and initial data
        db.execSQL(CREATE_USERS_TABLE)
        // Add more tables as needed
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle upgrades, e.g., alter table structure or migrate data
    }

    fun insertUser(email: String, password: String, username: String = "", collectedItems: List<String> = emptyList(), friends: List<String> = emptyList()): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_USERNAME, username)
            put(COLUMN_COLLECTED_ITEMS, collectedItems.joinToString(","))
            put(COLUMN_FRIENDS, friends.joinToString(","))
        }

        // Insert the user into the database and get the generated ID
        val userId = db.insert(TABLE_USERS, null, values)

        db.close()

        return userId
    }

    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        val user = if (cursor != null && cursor.moveToFirst()) {
            User(
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                parseStringToList(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTED_ITEMS))),
                parseStringToList(cursor.getString(cursor.getColumnIndex(COLUMN_FRIENDS))),
                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            )
        } else {
            null
        }

        cursor?.close()
        db.close()
        return user
    }

    @SuppressLint("Range")
    fun getUserById(userId: Long): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )

        val user = if (cursor != null && cursor.moveToFirst()) {
            User(
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
                parseStringToList(cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTED_ITEMS))),
                parseStringToList(cursor.getString(cursor.getColumnIndex(COLUMN_FRIENDS))),
                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            )
        } else {
            null
        }

        cursor?.close()
        db.close()
        return user
    }

    private fun parseStringToList(input: String): List<String> {
        return input.split(",").map { it.trim() }
    }

}