package com.robelseyoum3.cleancode.business.data.cache.implementation

import com.robelseyoum3.cleancode.business.data.cache.abstraction.NoteCacheDataSource
import com.robelseyoum3.cleancode.business.domain.model.Note
import com.robelseyoum3.cleancode.framework.datasource.cache.abstraction.NoteDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteCacheDataSourceImplementation @Inject constructor(private val noteDaoService: NoteDaoService) : NoteCacheDataSource {

    override suspend fun insertNote(note: Note): Long = noteDaoService.insertNote(note)

    override suspend fun insertNotes(notes: List<Note>): LongArray = noteDaoService.insertNotes(notes)

    override suspend fun deleteNote(primaryKey: String): Int = noteDaoService.deleteNote(primaryKey)

    override suspend fun deleteNotes(note: List<Note>): Int = noteDaoService.deleteNotes(note)

    override suspend fun updateNote(primaryKey: String, newTitle: String, newBody: String): Int = noteDaoService.updateNote(primaryKey, newTitle, newBody)

    override suspend fun searchNote(query: String, filterAndOrder: String, page: Int): List<Note> =
        TODO("check filterAndOrder and make query")
//        noteDaoService.searchNote(query, filterAndOrder, page)

    override suspend fun searchNoteById(primaryKey: String): Note? = noteDaoService.searchNoteById(primaryKey)

    override suspend fun getNumNotes(): Int = noteDaoService.getNumNotes()

}