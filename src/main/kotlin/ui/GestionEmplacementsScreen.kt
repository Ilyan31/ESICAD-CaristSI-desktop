package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import repositories.AlleeRepository
import repositories.ColonneRepository
import repositories.EmplacementRepository
import routing.Router
import routing.Routes

@Composable
fun GestionEmplacementsScreen(router: Router) {

    // Charger la liste des emplacements
    var emplacements by remember { mutableStateOf(EmplacementRepository.getAllEmplacements()) }

    // Charger la liste des allées et des colonnes
    val allees = remember { AlleeRepository.getAllAllees() }
    val colonnes = remember { ColonneRepository.getAllColonnes() }

    // Variables de sélection en dropdown
    var selectedAlleeIndex by remember { mutableStateOf(-1) }
    var expandedAllee by remember { mutableStateOf(false) }

    var selectedColonneIndex by remember { mutableStateOf(-1) }
    var expandedColonne by remember { mutableStateOf(false) }

    // Champs du formulaire
    var nom by remember { mutableStateOf("") }

    var selectedEmplacementId by remember { mutableStateOf<Int?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Recherche
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("📍 Gestion des Emplacements", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        // 🔎 Barre de recherche
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                emplacements = EmplacementRepository.getAllEmplacements().filter { emp ->
                    emp["nom"].toString().contains(searchQuery, ignoreCase = true)
                }
            },
            label = { Text("🔍 Rechercher un emplacement...") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 📌 Liste des emplacements
        LazyColumn {
            items(emplacements) { emplacement ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ID: ${emplacement["id"]}")
                        Text("Nom: ${emplacement["nom"]}")
                        Text("Allée: ${emplacement["allee"]}")
                        Text("Colonne: ${emplacement["colonne"]}")
                        Text("Places Disponibles: ${emplacement["places_disponibles"]}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            // Bouton Modifier
                            Button(
                                onClick = {
                                    selectedEmplacementId = emplacement["id"] as Int
                                    nom = emplacement["nom"] as String

                                    // Récupérer index de l'allée correspondante
                                    val alleeName = emplacement["allee"]?.toString() ?: ""
                                    selectedAlleeIndex = allees.indexOfFirst { it["nom"] == alleeName }

                                    // Récupérer index de la colonne correspondante
                                    val colonneName = emplacement["colonne"]?.toString() ?: ""
                                    selectedColonneIndex = colonnes.indexOfFirst { it["nom"] == colonneName }

                                    isEditing = true
                                }
                            ) {
                                Text("Modifier")
                            }

                            // Bouton Supprimer
                            Button(
                                onClick = {
                                    val success = EmplacementRepository.deleteEmplacement(emplacement["id"] as Int)
                                    if (!success) {
                                        errorMessage = "Impossible de supprimer (ou erreur)."
                                    }
                                    emplacements = EmplacementRepository.getAllEmplacements()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                            ) {
                                Text("Supprimer", color = MaterialTheme.colors.onError)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📋 Formulaire d'ajout / modification
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(if (isEditing) "Modifier un emplacement" else "Ajouter un emplacement", style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(8.dp))

                // 🔸 Nom de l'emplacement
                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text("Nom de l'emplacement") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 🔹 Dropdown Allée
                Text("Allée sélectionnée : " + (if (selectedAlleeIndex == -1) "Aucune" else allees[selectedAlleeIndex]["nom"].toString()))
                Button(onClick = { expandedAllee = true }) {
                    Text(if (selectedAlleeIndex == -1) "Choisir une Allée" else allees[selectedAlleeIndex]["nom"].toString())
                }
                DropdownMenu(expanded = expandedAllee, onDismissRequest = { expandedAllee = false }) {
                    allees.forEachIndexed { index, al ->
                        DropdownMenuItem(onClick = {
                            selectedAlleeIndex = index
                            expandedAllee = false
                        }) {
                            Text(al["nom"].toString())
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 🔹 Dropdown Colonne
                Text("Colonne sélectionnée : " + (if (selectedColonneIndex == -1) "Aucune" else colonnes[selectedColonneIndex]["nom"].toString()))
                Button(onClick = { expandedColonne = true }) {
                    Text(if (selectedColonneIndex == -1) "Choisir une Colonne" else colonnes[selectedColonneIndex]["nom"].toString())
                }
                DropdownMenu(expanded = expandedColonne, onDismissRequest = { expandedColonne = false }) {
                    colonnes.forEachIndexed { index, col ->
                        DropdownMenuItem(onClick = {
                            selectedColonneIndex = index
                            expandedColonne = false
                        }) {
                            Text(col["nom"].toString())
                        }
                    }
                }

                errorMessage?.let {
                    Text(it, color = MaterialTheme.colors.error)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        // Vérification
                        if (nom.isBlank() || selectedAlleeIndex == -1 || selectedColonneIndex == -1) {
                            errorMessage = "Veuillez remplir le nom et sélectionner une allée et une colonne."
                            return@Button
                        }

                        val alleyId = allees[selectedAlleeIndex]["id"] as Int
                        val colonneId = colonnes[selectedColonneIndex]["id"] as Int

                        if (selectedEmplacementId != null && isEditing) {
                            // Modification
                            EmplacementRepository.updateEmplacement(
                                id = selectedEmplacementId!!,
                                nom = nom,
                                idAllee = alleyId,
                                idColonne = colonneId
                            )
                        } else {
                            // Ajout
                            EmplacementRepository.addEmplacement(
                                nom = nom,
                                idAllee = alleyId,
                                idColonne = colonneId
                            )
                        }

                        // Reset
                        nom = ""
                        selectedAlleeIndex = -1
                        selectedColonneIndex = -1
                        isEditing = false
                        errorMessage = null

                        // Recharger la liste
                        emplacements = EmplacementRepository.getAllEmplacements()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(if (isEditing) "Modifier Emplacement" else "Ajouter Emplacement")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔙 Bouton retour menu
        Button(
            onClick = { router.navigateTo(Routes.HOME) },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("⬅ Retour au menu principal")
        }
    }
}