package com.robelseyoum3.cleancode.business.data.network

import com.robelseyoum3.cleancode.business.data.network.NetworkErrors.NETWORK_DATA_NULL
import com.robelseyoum3.cleancode.business.data.network.NetworkErrors.NETWORK_ERROR
import com.robelseyoum3.cleancode.business.domain.state.*

abstract class ApiResponseHandler<ViewState, Data>(
    private val response: ApiResult<Data?>,
    private val stateEvent: StateEvent?
) {

    suspend fun getResult(): DataState<ViewState>? {

        return when(response){

                is ApiResult.GenericError -> {
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\n"+
                                    "Reason: ${response.errorMessages}",
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                    )
                }

                is ApiResult.NetworkError-> {
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\n"+
                                    "Reason: ${NETWORK_ERROR}",
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                    )
                }

                is ApiResult.Success -> {
                    if(response.value == null) {
                        DataState.error(
                            response = Response(
                                message = "${stateEvent?.errorInfo()}\n\n"+
                                        "Reason: ${NETWORK_DATA_NULL}",
                                uiComponentType = UIComponentType.Dialog(),
                                messageType = MessageType.Error()
                            ),
                            stateEvent = stateEvent
                        )
                    } else {
                        handleSuccess(resultObj = response.value)
                    }
                }
            }
        }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>
}