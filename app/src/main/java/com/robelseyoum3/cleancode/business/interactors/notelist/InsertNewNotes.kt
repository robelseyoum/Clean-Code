package com.robelseyoum3.cleancode.business.interactors.notelist

import android.icu.text.CaseMap
import com.robelseyoum3.cleancode.business.data.cache.CacheResponseHandler
import com.robelseyoum3.cleancode.business.data.cache.abstraction.NoteCacheDataSource
import com.robelseyoum3.cleancode.business.data.network.abstraction.NoteNetworkDataSource
import com.robelseyoum3.cleancode.business.data.util.safeApi
import com.robelseyoum3.cleancode.business.data.util.safeCacheCall
import com.robelseyoum3.cleancode.business.domain.model.Note
import com.robelseyoum3.cleancode.business.domain.model.NoteFactory
import com.robelseyoum3.cleancode.business.domain.state.*
import com.robelseyoum3.cleancode.framework.presentation.notelist.state.NoteListStateEvent
import com.robelseyoum3.cleancode.framework.presentation.notelist.state.NoteListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class InsertNewNotes(
    private val noteCacheDataSource: NoteCacheDataSource,
    private val noteNetworkDataSource: NoteNetworkDataSource,
    private val noteFactory: NoteFactory
) {

    fun insertNewNote(
        id: String? = null,
        title: String,
        stateEvent: StateEvent
    ): Flow<DataState<NoteListViewState>?> = flow {

        val newNote = noteFactory.createSingleNote(
            id = id?: UUID.randomUUID().toString(),
            title = title,
            body = ""
        )

        val cacheResult = safeCacheCall(IO){
            noteCacheDataSource.insertNote(newNote)
        }

        val cacheResponse = object : CacheResponseHandler<NoteListViewState, Long>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override fun handleSuccess(resultObj: Long): DataState<NoteListViewState> {

                return if(resultObj > 0) {
                    val viewState = NoteListViewState(
                        newNote = newNote
                    )

                    DataState.data(
                        response = Response(
                            message = INSERT_NOTE_SUCCESS,
                            uiComponentType = UIComponentType.Toast(),
                            messageType =  MessageType.Success()
                        ),
                        data = viewState,
                        stateEvent = stateEvent
                    )

                } else {

                     DataState.data(
                        response = Response(
                            message = INSERT_NOTE_FAILED,
                            uiComponentType = UIComponentType.Toast(),
                            messageType =  MessageType.Error()
                        ),
                        data = null,
                        stateEvent = stateEvent
                    )
                }
            }

        }.getResult()

        emit(cacheResponse)

        updateNetwork(cacheResponse?.stateMessage?.response?.message, newNote)
    }


    private suspend fun updateNetwork(cacheResponse: String?, newNote: Note) {
        if(cacheResponse.equals(INSERT_NOTE_SUCCESS)){
            safeApi(IO){
                noteNetworkDataSource.insertOrUpdateNote(newNote)
            }
        }
    }

    companion object{
        const val INSERT_NOTE_SUCCESS = "Successfully inserted new note."
        const val INSERT_NOTE_FAILED = "Failed to insert new note."
    }
}