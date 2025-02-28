package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import repositories.ColisRepository
import routing.Router
import routing.Routes

@Composable
fun GestionColisScreen(router: Router) {
    var colisList by remember { mutableStateOf(emptyList<Map<String, Any>>()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var code by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var poids by remember { mutableStateOf("") }
    var volume by remember { mutableStateOf("") }
    var dateReception by remember { mutableStateOf("") }
    var idEmplacement by remember { mutableStateOf("") }
    var selectedColisId by remember { mutableStateOf<Int?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    // Charger les colis au démarrage
    LaunchedEffect(Unit) {
        colisList = ColisRepository.getAllColis()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Gestion des Colis", style = MaterialTheme.typography.h4)
            Button(onClick = { router.navigateTo(Routes.HOME) }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)) {
                Text("⬅ Menu", color = Color.White)
            }
        }

        // 🔎 Barre de recherche
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                colisList = ColisRepository.getAllColis().filter { colis ->
                    colis["code"].toString().contains(it.text, ignoreCase = true) ||
                            colis["description"].toString().contains(it.text, ignoreCase = true) ||
                            colis["idEmplacement"].toString().contains(it.text, ignoreCase = true)
                }
            },
            label = { Text("Rechercher un colis...") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Formulaire d'ajout ou modification
        Text(if (isEditing) "Modifier un colis" else "Ajouter un colis", style = MaterialTheme.typography.h6)

        OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Code") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = poids, onValueChange = { poids = it }, label = { Text("Poids (kg)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = volume, onValueChange = { volume = it }, label = { Text("Volume (m³)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = dateReception, onValueChange = { dateReception = it }, label = { Text("Date de réception (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = idEmplacement, onValueChange = { idEmplacement = it }, label = { Text("ID Emplacement") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                if (code.isNotBlank() && description.isNotBlank() && poids.isNotBlank() && volume.isNotBlank() && dateReception.isNotBlank() && idEmplacement.isNotBlank()) {
                    if (isEditing && selectedColisId != null) {
                        ColisRepository.updateColis(selectedColisId!!, code, description, poids.toDouble(), volume.toDouble(), dateReception, idEmplacement.toInt())
                    } else {
                        ColisRepository.addColis(code, description, poids.toDouble(), volume.toDouble(), dateReception, idEmplacement.toInt())
                    }
                    colisList = ColisRepository.getAllColis()
                    code = ""; description = ""; poids = ""; volume = ""; dateReception = ""; idEmplacement = ""
                    isEditing = false
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(if (isEditing) "Modifier Colis" else "Ajouter Colis")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Liste des colis
        if (colisList.isEmpty()) {
            Text("Aucun colis trouvé dans la base de données.", color = Color.Red)
        } else {
            LazyColumn {
                items(colisList) { colis ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("ID: ${colis["id"]}")
                            Text("Code: ${colis["code"]}")
                            Text("Description: ${colis["description"]}")
                            Text("Poids: ${colis["poids"]} kg")
                            Text("Volume: ${colis["volume"]} m³")
                            Text("Réception: ${colis["dateReception"]}")
                            Text("ID Emplacement: ${colis["idEmplacement"]}")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        selectedColisId = colis["id"] as Int
                                        code = colis["code"] as String
                                        description = colis["description"] as String
                                        poids = colis["poids"].toString()
                                        volume = colis["volume"].toString()
                                        dateReception = colis["dateReception"] as String
                                        idEmplacement = colis["idEmplacement"].toString()
                                        isEditing = true
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                                ) {
                                    Text("Modifier", color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        ColisRepository.deleteColis(colis["id"] as Int)
                                        colisList = ColisRepository.getAllColis()
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                ) {
                                    Text("Supprimer", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}