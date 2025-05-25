package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.model.BasicTaskItem
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripTilesListViewModel(application: Application) : AndroidViewModel(application) {

    val service = Network.tripService

    private val _processes = MutableStateFlow<List<BasicTaskItem>?>(emptyList())
    val processes = _processes.asStateFlow()

    private val _options = MutableStateFlow<Set<String>>(emptySet())
    val options = _options.asStateFlow()

    private val _selectedType = MutableStateFlow<String>("")
    val selectedType = _selectedType.asStateFlow()

    private val allTasks: MutableMap<String, List<BasicTaskItem>> = mutableMapOf()

    fun fetchTasks() {
        viewModelScope.launch {
            val activeTasks = service.getActiveTripsForUser(AppSingleton.userId ?: -1)
            allTasks.putAll(activeTasks)
            _options.update { activeTasks.keys }
            _selectedType.update { activeTasks.keys.firstOrNull() ?: "" }
            _processes.update { activeTasks[activeTasks.keys.firstOrNull()] }
        }
    }

    fun changeTaskType(taskType: String) {
        _selectedType.update { taskType }
        _processes.update { allTasks[taskType] }
    }

    fun createTrip(createTripCallback: (Long) -> Unit) {
        viewModelScope.launch {
            val tripInstanceKey = service.createTripInstance(AppSingleton.userId!!)
            if (tripInstanceKey.isSuccessful) {
                tripInstanceKey.body()?.let { key ->
                    createTripCallback(key)
                }
            }
        }
    }
}