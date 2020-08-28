package com.robelseyoum3.cleancode.business.data.network.abstraction

import com.robelseyoum3.cleancode.business.domain.model.Note

interface NoteNetworkDataSource {

    suspend fun insertOrUpdateNote(note: Note)

    suspend fun deleteNote(primaryKey: String)

    suspend fun insertDeletedNote(notes: Note)

    suspend fun insertDeletedNotes(notes: List<Note>)

    suspend fun deleteDeletedNote(notes: Note)

    suspend fun getDeletedNote(): List<Note>

    suspend fun deleteAllNotes()

    suspend fun searchNote(note: Note): Note?

    suspend fun getAllNotes(): List<Note>

    suspend fun insertOrUpdateNotes(notes: List<Note>)

}