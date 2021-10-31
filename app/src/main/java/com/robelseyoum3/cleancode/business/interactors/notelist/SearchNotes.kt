package com.robelseyoum3.cleancode.business.interactors.notelist

import com.robelseyoum3.cleancode.business.data.cache.CacheResponseHandler
import com.robelseyoum3.cleancode.business.data.cache.abstraction.NoteCacheDataSource
import com.robelseyoum3.cleancode.business.data.util.safeCacheCall
import com.robelseyoum3.cleancode.business.domain.model.Note
import com.robelseyoum3.cleancode.business.domain.state.*
import com.robelseyoum3.cleancode.framework.presentation.notelist.state.NoteListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchNotes (
    private val noteCacheDataSource: NoteCacheDataSource
){

    fun searchNotes(
        query: String,
        filterAndOrder: String,
        page: Int,
        stateEvent: StateEvent
    ): Flow<DataState<NoteListViewState>?> = flow {

        var updatedPage = page
        if(page <= 0){
            updatedPage = 1
        }

        val cacheResult = safeCacheCall(Dispatchers.IO) {
            noteCacheDataSource.searchNote(
                query = query,
                filterAndOrder = filterAndOrder,
                page = updatedPage
            )
        }

        val response = object : CacheResponseHandler<NoteListViewState, List<Note>>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override fun handleSuccess(resultObj: List<Note>): DataState<NoteListViewState> {
                var message: String? = SEARCH_NOTES_SUCCESS
                var uiComponentType: UIComponentType? = UIComponentType.None()

                if(resultObj.isEmpty()){
                    message = SEARCH_NOTES_NO_MATCHING_RESULTS
                    uiComponentType = UIComponentType.Toast()
                }

                return DataState.data(

                    response = Response(
                        message = message,
                        uiComponentType = uiComponentType as UIComponentType,
                        messageType = MessageType.Success()
                    ),

                    data = NoteListViewState(
                        noteList = ArrayList(resultObj)
                    ),

                    stateEvent = stateEvent
                )

            }

        }.getResult()

        emit(response)
    }

    companion object {
        val SEARCH_NOTES_SUCCESS = "Successfully retrieved list of notes."
        val SEARCH_NOTES_NO_MATCHING_RESULTS = "There are no notes that match that query."
        val SEARCH_NOTES_FAILED = "Failed to retrieve the list of notes."
    }
}
