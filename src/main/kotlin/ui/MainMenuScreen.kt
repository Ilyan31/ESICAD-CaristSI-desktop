package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import routing.Router
import routing.Routes

@Composable
fun MainMenuScreen(router: Router) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Menu Principal", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        Button(
            onClick = { println("Naviguer vers Gestion des Caristes") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Gestion des Caristes")
        }

        Button(
            onClick = { println("Naviguer vers Gestion des Colis") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Gestion des Colis")
        }

        Button(
            onClick = { println("Naviguer vers Gestion des Emplacements") },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Gestion des Emplacements")
        }
    }
}