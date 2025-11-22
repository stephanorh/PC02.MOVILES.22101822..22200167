package dev.stephano.pc02moviles.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import dev.stephano.pc02moviles.data.model.TeamModel
import kotlinx.coroutines.tasks.await

class FirestoreRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
){
    // Guarda un equipo en la collection 'equipos'
    suspend fun saveTeam(team: TeamModel){
        // Generar un id automático
        val docRef = firestore.collection("equipos").document()
        val id = docRef.id
        val payload = mapOf(
            "id" to id,
            "name" to team.name,
            "year" to team.year,
            "titles" to team.titles,
            "imageUrl" to team.imageUrl
        )
        docRef.set(payload).await()
    }

    // Obtener lista de equipos (lectura única)
    suspend fun getTeams(): List<TeamModel> {
        val snapshot = firestore.collection("equipos").get().await()
        return snapshot.documents.map { doc ->
            TeamModel(
                id = doc.getString("id") ?: doc.id,
                name = doc.getString("name") ?: "",
                year = (doc.getLong("year") ?: 0L).toInt(),
                titles = (doc.getLong("titles") ?: 0L).toInt(),
                imageUrl = doc.getString("imageUrl") ?: ""
            )
        }
    }
}
