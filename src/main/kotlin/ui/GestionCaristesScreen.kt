package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import repositories.CaristeRepository // ✅ Correction après le renommage
import routing.Router
import routing.Routes

@Composable
fun GestionCaristesScreen(router: Router) {
    val caristes = remember { mutableStateListOf<Map<String, Any>>() }

    LaunchedEffect(Unit) {
        caristes.addAll(`CaristeRepository`.getAllCaristes())
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Gestion des Caristes", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(caristes) { cariste ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nom: ${cariste["nom"]} ${cariste["prenom"]}")
                            Text("Login: ${cariste["login"]}")
                            Text("Embauche: ${cariste["embauche"]}")
                        }
                    }
                }
            }
        }

        Button(
            onClick = { router.navigateTo(Routes.HOME) },
            modifier = Modifier
                .align(Alignment.BottomCenter)  // 🔹 Positionne le bouton en bas
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Retour au menu principal")
        }
    }
}