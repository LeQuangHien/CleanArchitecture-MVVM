package com.hienle.cleanarchitecture.core.common.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

/**
 * A [ViewModel] which contains a [StateFlow].
 *
 * @param STATE Usually a data class which describes the current view state.
 * @constructor Initial view state
 * @see ViewModel
 */
abstract class ViewStateModel<STATE>(
    initial: STATE,
    context: CoroutineContext = Dispatchers.Default,
) : ViewModel() {
    /**
     * [CoroutineContext] tied to this [ViewModel], similar to [viewModelScope]
     * Can be changed via constructor.
     */
    private val viewStateModelScope = viewModelScope + context

    /**
     * Only available from within the [ViewStateModel].
     * Is used to update the current state.
     */
    private var viewStateController = MutableStateFlow(initial)

    /**
     * Sends out the current view state and its updates.
     */
    val viewState: StateFlow<STATE> = viewStateController.asStateFlow()

    /**
     * Updates the [ViewStateModel] state atomically using the specified function of its value.
     */
    protected fun update(function: (STATE) -> STATE) {
        viewStateController.update(function)
    }

    /**
     * Launches a new coroutine without blocking the current thread.
     * The coroutine is cancelled when the scope of this [ViewStateModel] is cancelled.
     */
    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewStateModelScope.launch(block = block)
    }
}
