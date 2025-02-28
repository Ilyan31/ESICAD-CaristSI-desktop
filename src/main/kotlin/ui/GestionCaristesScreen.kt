package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import repositories.CaristeRepository
import routing.Router
import routing.Routes

@Composable
fun GestionCaristesScreen(router: Router) {
    var caristesList by remember { mutableStateOf(CaristeRepository.getAllCaristes()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var mdp by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }

    var selectedCaristeId by remember { mutableStateOf<Int?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Gestion des Caristes", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        // 🔎 Barre de recherche
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                caristesList = CaristeRepository.getAllCaristes().filter { cariste ->
                    cariste["nom"].toString().contains(it.text, ignoreCase = true) ||
                            cariste["prenom"].toString().contains(it.text, ignoreCase = true) ||
                            cariste["login"].toString().contains(it.text, ignoreCase = true)
                }
            },
            label = { Text("Rechercher un cariste...") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Formulaire d'ajout ou de modification
        Text(
            if (isEditing) "Modifier un cariste" else "Ajouter un cariste",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(value = nom, onValueChange = { nom = it }, label = { Text("Nom") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = prenom, onValueChange = { prenom = it }, label = { Text("Prénom") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = login, onValueChange = { login = it }, label = { Text("Login") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = dateNaissance, onValueChange = { dateNaissance = it }, label = { Text("Date de Naissance (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())

        if (!isEditing) {
            OutlinedTextField(
                value = mdp,
                onValueChange = { mdp = it },
                label = { Text("Mot de passe") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Button(
            onClick = {
                if (nom.isNotBlank() && prenom.isNotBlank() && login.isNotBlank() && dateNaissance.isNotBlank()) {
                    if (isEditing && selectedCaristeId != null) {
                        CaristeRepository.updateCariste(selectedCaristeId!!, nom, prenom, login, dateNaissance)
                    } else {
                        CaristeRepository.addCariste(nom, prenom, login, mdp, dateNaissance)
                    }
                    caristesList = CaristeRepository.getAllCaristes()
                    nom = ""; prenom = ""; login = ""; mdp = ""; dateNaissance = ""
                    isEditing = false
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(if (isEditing) "Modifier Cariste" else "Ajouter Cariste")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Liste des caristes
        if (caristesList.isEmpty()) {
            Text("Aucun cariste trouvé dans la base de données.", color = MaterialTheme.colors.error)
        } else {
            LazyColumn {
                items(caristesList) { cariste ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("ID: ${cariste["id"]}")
                            Text("Nom: ${cariste["nom"]}")
                            Text("Prénom: ${cariste["prenom"]}")
                            Text("Login: ${cariste["login"]}")
                            Text("Naissance: ${cariste["Naissance"]}")
                            Text("Embauche: ${cariste["Embauche"]}")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        selectedCaristeId = cariste["id"] as Int
                                        nom = cariste["nom"] as String
                                        prenom = cariste["prenom"] as String
                                        login = cariste["login"] as String
                                        dateNaissance = cariste["Naissance"] as String
                                        isEditing = true
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                                ) {
                                    Text("Modifier", color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        CaristeRepository.deleteCariste(cariste["id"] as Int)
                                        caristesList = CaristeRepository.getAllCaristes()
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

        // 🔙 Bouton retour au menu principal
        Button(
            onClick = { router.navigateTo(Routes.HOME) },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Retour au menu principal")
        }
    }
}