package dev.stephano.pc02moviles.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

/**
 * Pantalla de registro de equipos (RegistroScreen)
 * Campos: Nombre del equipo, Año de fundación, Número de títulos ganados, URL de la imagen
 * Al presionar "Guardar" se guarda en Firestore y navega a la pantalla "listado"
 */
@Composable
fun RegistroScreen(navController: NavController, vm: RegistroViewModel = viewModel()){

    var nombre by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var titulos by remember { mutableStateOf("") }
    var urlImagen by remember { mutableStateOf("") }

    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Registro Liga 1", modifier = Modifier.padding(bottom = 16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del equipo") },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = ano,
            onValueChange = { ano = it.filter { c -> c.isDigit() } },
            label = { Text("Año de fundación") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = titulos,
            onValueChange = { titulos = it.filter { c -> c.isDigit() } },
            label = { Text("Número de títulos ganados") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = urlImagen,
            onValueChange = { urlImagen = it },
            label = { Text("URL de la imagen del equipo") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (errorMessage != null) {
            Text(text = errorMessage ?: "", modifier = Modifier.padding(bottom = 8.dp))
        }

        Button(onClick = {
            // Validaciones mínimas
            errorMessage = null
            val añoInt = ano.toIntOrNull() ?: 0
            val titulosInt = titulos.toIntOrNull() ?: 0
            if (nombre.isBlank()) {
                errorMessage = "Ingrese el nombre del equipo"
                return@Button
            }
            isSaving = true
            vm.saveTeam(
                nombre.trim(),
                añoInt,
                titulosInt,
                urlImagen.trim()
            ) { success, error ->
                isSaving = false
                if (success) {
                    // Navegar a la pantalla de listado (la implementará el compañero)
                    navController.navigate("listado"){
                        // evitar volver a registro en el back stack
                        popUpTo("register"){ inclusive = true }
                    }
                } else {
                    errorMessage = error ?: "Error al guardar"
                }
            }

        }){
            Text(text = if (isSaving) "Guardando..." else "Guardar")
        }

    }
}

