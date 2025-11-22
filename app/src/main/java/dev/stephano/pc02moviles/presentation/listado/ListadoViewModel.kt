package dev.stephano.pc02moviles.presentation.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.stephano.pc02moviles.data.model.TeamModel
import dev.stephano.pc02moviles.data.remote.firebase.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListadoViewModel(private val repo: FirestoreRepository = FirestoreRepository()): ViewModel(){

    private val _teams = MutableStateFlow<List<TeamModel>>(emptyList())
    val teams: StateFlow<List<TeamModel>> = _teams

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    fun loadTeams(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = repo.getTeams()
                _teams.value = list
            } catch (e: Exception){
                _teams.value = emptyList()
            }
            _isLoading.value = false
        }
    }
}