package dev.stephano.pc02moviles.presentation.listado

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dev.stephano.pc02moviles.data.model.TeamModel

@Composable
fun ListadoScreen(navController: NavController, vm: ListadoViewModel = viewModel()){

    val teams by vm.teams.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    LaunchedEffect(Unit){
        vm.loadTeams()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally){

        Text(text = "Equipos", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(modifier = Modifier.weight(1f)){
            items(teams){ team: TeamModel ->
                TeamItem(team)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

        Button(onClick = { navController.navigate("register") }){
            Text(text = "Nuevo Registro")
        }
    }
}

@Composable
fun TeamItem(team: TeamModel){
    Card(modifier = Modifier.fillMaxWidth()){
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){

            Image(
                painter = rememberAsyncImagePainter(team.imageUrl),
                contentDescription = team.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Column(modifier = Modifier.weight(1f)){
                Text(text = team.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = team.year.toString(), style = MaterialTheme.typography.bodySmall)
            }

            Text(text = team.titles.toString(), modifier = Modifier.padding(start = 8.dp))
        }
    }
}
