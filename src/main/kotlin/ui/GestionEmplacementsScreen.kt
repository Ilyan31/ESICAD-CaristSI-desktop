package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import repositories.EmplacementRepository
import routing.Router
import routing.Routes

@Composable
fun GestionEmplacementsScreen(router: Router) {
    var emplacements by remember { mutableStateOf(EmplacementRepository.getAllEmplacements()) }
    var searchQuery by remember { mutableStateOf("") }

    var nom by remember { mutableStateOf("") }
    var selectedEmplacementId by remember { mutableStateOf<Int?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF512DA8), Color(0xFF673AB7))
    )

    Box(
        modifier = Modifier.fillMaxSize().background(gradientBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🏠 En-tête
            Text("📍 Gestion des Emplacements", fontSize = 26.sp, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            // 🔎 Barre de recherche
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    emplacements = EmplacementRepository.getAllEmplacements().filter { emplacement ->
                        emplacement["nom"].toString().contains(it, ignoreCase = true)
                    }
                },
                label = { Text("🔍 Rechercher un emplacement...") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 📋 Formulaire d'ajout/modification
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(if (isEditing) "Modifier un emplacement" else "Ajouter un emplacement", fontSize = 20.sp, color = Color(0xFF6200EA))

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = nom,
                        onValueChange = { nom = it },
                        label = { Text("Nom de l'emplacement") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    errorMessage?.let {
                        Text(it, color = MaterialTheme.colors.error, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 🔹 Bouton d'ajout/modification
                    Button(
                        onClick = {
                            if (nom.isNotBlank()) {
                                if (isEditing && selectedEmplacementId != null) {
                                    EmplacementRepository.updateEmplacement(selectedEmplacementId!!, nom)
                                } else {
                                    EmplacementRepository.addEmplacement(nom)
                                }
                                emplacements = EmplacementRepository.getAllEmplacements()
                                nom = ""
                                isEditing = false
                                errorMessage = null
                            } else {
                                errorMessage = "⚠️ Veuillez renseigner un nom d'emplacement"
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA))
                    ) {
                        Text(if (isEditing) "Modifier Emplacement" else "Ajouter Emplacement", color = Color.White, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 📌 Liste des emplacements
            if (emplacements.isEmpty()) {
                Text("Aucun emplacement trouvé.", color = Color.White)
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

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            selectedEmplacementId = emplacement["id"] as Int
                                            nom = emplacement["nom"] as String
                                            isEditing = true
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                                    ) {
                                        Text("Modifier", color = Color.White)
                                    }

                                    Button(
                                        onClick = {
                                            EmplacementRepository.deleteEmplacement(emplacement["id"] as Int)
                                            emplacements = EmplacementRepository.getAllEmplacements()
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

            Spacer(modifier = Modifier.height(16.dp))

            // 🔙 Bouton retour au menu
            Button(
                onClick = { router.navigateTo(Routes.HOME) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("⬅ Retour au menu principal", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}