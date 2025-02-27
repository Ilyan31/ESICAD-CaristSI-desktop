package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import routing.Router
import routing.Routes

@Composable
fun GestionColisScreen(router: Router) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Gestion des Colis", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { router.navigateTo(Routes.HOME) },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Retour au menu principal")
        }
    }
}

