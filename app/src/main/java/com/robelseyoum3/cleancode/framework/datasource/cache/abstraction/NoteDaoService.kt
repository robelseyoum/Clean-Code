package com.robelseyoum3.cleancode.framework.datasource.cache.abstraction

import com.robelseyoum3.cleancode.business.domain.model.Note
import com.robelseyoum3.cleancode.framework.datasource.database.NOTE_PAGINATION_PAGE_SIZE

interface NoteDaoService {

    suspend fun insertNote(note: Note): Long //return type of insert SQLlight statement(Long)

    suspend fun insertNotes(notes: List<Note>): LongArray //for testing purpose

    suspend fun deleteNote(primary: String): Int

    suspend fun deleteNotes(note: List<Note>): Int

    suspend fun updateNote(primary: String, newTitle: String, newBody: String): Int

    suspend fun searchNote(): List<Note>

    suspend fun searchNotesOrderByDateDESC(
        query: String,
        page: Int,
        pageSize: Int = NOTE_PAGINATION_PAGE_SIZE
    ): List<Note>

    suspend fun searchNotesOrderByDateASC(
        query: String,
        page: Int,
        pageSize: Int = NOTE_PAGINATION_PAGE_SIZE
    ): List<Note>

    suspend fun searchNotesOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = NOTE_PAGINATION_PAGE_SIZE
    ): List<Note>

    suspend fun searchNotesOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = NOTE_PAGINATION_PAGE_SIZE
    ): List<Note>

    suspend fun searchNoteById(primaryKey: String): Note?

    suspend fun getNumNotes(): Int

    suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Note>

}