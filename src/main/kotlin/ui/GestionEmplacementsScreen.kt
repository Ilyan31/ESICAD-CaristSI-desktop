package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import repositories.EmplacementRepository
import routing.Router
import routing.Routes

@Composable
fun GestionEmplacementsScreen(router: Router) {
    val emplacements = remember { mutableStateListOf<Map<String, Any>>() }

    LaunchedEffect(Unit) {
        emplacements.addAll(EmplacementRepository.getAllEmplacements())
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Gestion des Emplacements", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        if (emplacements.isEmpty()) {
            Text("Aucun emplacement trouvé dans la base de données.", color = MaterialTheme.colors.error)
        } else {
            LazyColumn {
                items(emplacements) { emplacement ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("ID: ${emplacement["id"]}")
                            Text("Nom: ${emplacement["nom"]}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { router.navigateTo(Routes.HOME) },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Retour au menu principal")
        }
    }
}