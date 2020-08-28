package com.robelseyoum3.cleancode.business.data.cache.abstraction

import com.robelseyoum3.cleancode.business.domain.model.Note

interface NoteCacheDataSource {

    suspend fun insertNote(note: Note): Long //return type of insert SQLlight statement(Long)

    suspend fun insertNotes(notes: List<Note>): LongArray //for testing purpose

    suspend fun deleteNote(primary: String): Int

    suspend fun deleteNotes(note: List<Note>): Int

    suspend fun updateNote(primary: String, newTitle: String, newBody: String): Int

    suspend fun searchNote(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Note>

    suspend fun searchNoteById(primaryKey: String): Note?

    suspend fun getNumNotes(): Int

}