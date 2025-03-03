package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart // ✅ Remplace Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import routing.Router
import routing.Routes

@Composable
fun MainMenuScreen(router: Router, onLogout: () -> Unit) {
    // 🎨 Dégradé de fond pour un style moderne
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF6200EA), Color(0xFF3700B3))
    )

    Box(
        modifier = Modifier.fillMaxSize().background(gradientBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🏠 Titre du menu
            Text(
                "📌 Menu Principal",
                style = MaterialTheme.typography.h4.copy(fontSize = 28.sp),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔹 Boutons stylisés avec icônes corrigées
            MenuButton("Gestion des Caristes", Icons.Default.AccountCircle, Color(0xFF03DAC5)) {
                router.navigateTo(Routes.GESTION_CARISTES)
            }

            MenuButton("Gestion des Colis", Icons.Default.ShoppingCart, Color(0xFFFFA000)) { // 🛒 Correction : ShoppingCart
                router.navigateTo(Routes.GESTION_COLIS)
            }

            MenuButton("Gestion des Emplacements", Icons.Default.LocationOn, Color(0xFF4CAF50)) {
                router.navigateTo(Routes.GESTION_EMPLACEMENTS)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🔴 Bouton Déconnexion corrigé
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD32F2F)),
                modifier = Modifier.fillMaxWidth().height(55.dp).shadow(8.dp, RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Déconnexion", tint = Color.White) // 🔴 Correction
                Spacer(modifier = Modifier.width(8.dp))
                Text("Déconnexion", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

// 🌟 Composant générique pour un bouton stylisé
@Composable
fun MenuButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        modifier = Modifier.fillMaxWidth().height(55.dp).shadow(8.dp, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(icon, contentDescription = text, tint = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White, fontSize = 18.sp)
    }
}