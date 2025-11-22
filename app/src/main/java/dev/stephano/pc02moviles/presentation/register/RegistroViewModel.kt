package dev.stephano.pc02moviles.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.stephano.pc02moviles.data.model.TeamModel
import dev.stephano.pc02moviles.data.remote.firebase.FirestoreRepository
import kotlinx.coroutines.launch

class RegistroViewModel(private val repo: FirestoreRepository = FirestoreRepository()): ViewModel(){

    fun saveTeam(nombre: String, año: Int, titulos: Int, urlImagen: String, callback: (Boolean, String?) -> Unit){
        val team = TeamModel(
            id = null,
            name = nombre,
            year = año,
            titles = titulos,
            imageUrl = urlImagen
        )

        viewModelScope.launch {
            try {
                repo.saveTeam(team)
                callback(true, null)
            } catch (e: Exception){
                callback(false, e.message)
            }
        }
    }
}

