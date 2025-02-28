package repositories

import database.DB
import ktorm.Caristes
import org.ktorm.dsl.*
import java.time.LocalDate

object CaristeRepository {

    // 🔹 Récupérer tous les caristes
    fun getAllCaristes(): List<Map<String, Any>> {
        return try {
            DB.database.from(Caristes)
                .select()
                .mapNotNull { row ->
                    val id = row[Caristes.id] ?: return@mapNotNull null
                    val nom = row[Caristes.nom] ?: return@mapNotNull null
                    val prenom = row[Caristes.prenom] ?: return@mapNotNull null
                    val login = row[Caristes.login] ?: return@mapNotNull null
                    val dateNaissance = row[Caristes.dateNaissance]?.toString() ?: return@mapNotNull null
                    val dateEmbauche = row[Caristes.dateEmbauche]?.toString() ?: return@mapNotNull null

                    mapOf(
                        "id" to id,
                        "nom" to nom,
                        "prenom" to prenom,
                        "login" to login,
                        "Naissance" to dateNaissance,
                        "Embauche" to dateEmbauche
                    )
                }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des caristes : ${e.localizedMessage}")
            emptyList()
        }
    }

    // 🔹 Ajouter un cariste
    fun addCariste(nom: String, prenom: String, login: String, mdp: String, dateNaissance: String) {
        try {
            val parsedDateNaissance = try {
                LocalDate.parse(dateNaissance) // ✅ Conversion en `LocalDate`
            } catch (e: Exception) {
                println("❌ Erreur : Format de date invalide ($dateNaissance). Utilise YYYY-MM-DD.")
                return
            }

            val dateEmbauche = LocalDate.now() // ✅ Date automatique

            DB.database.insert(Caristes) {
                set(Caristes.nom, nom)
                set(Caristes.prenom, prenom)
                set(Caristes.login, login)
                set(Caristes.mdp, mdp)
                set(Caristes.dateNaissance, parsedDateNaissance) // ✅ Type correct
                set(Caristes.dateEmbauche, dateEmbauche) // ✅ Type correct
            }
            println("✅ Cariste ajouté : $nom $prenom | Naissance: $parsedDateNaissance | Embauche: $dateEmbauche")
        } catch (e: Exception) {
            println("⚠️ Erreur lors de l'ajout du cariste : ${e.localizedMessage}")
        }
    }

    // 🔹 Modifier un cariste
    fun updateCariste(id: Int, nom: String, prenom: String, login: String, dateNaissance: String) {
        try {
            val parsedDateNaissance = try {
                LocalDate.parse(dateNaissance) // ✅ Conversion en `LocalDate`
            } catch (e: Exception) {
                println("❌ Erreur : Format de date invalide ($dateNaissance). Utilise YYYY-MM-DD.")
                return
            }

            DB.database.update(Caristes) {
                set(Caristes.nom, nom)
                set(Caristes.prenom, prenom)
                set(Caristes.login, login)
                set(Caristes.dateNaissance, parsedDateNaissance) // ✅ Type correct
                where { Caristes.id eq id }
            }
            println("✅ Cariste modifié : ID=$id, Nouveau Nom=$nom, Date de Naissance=$parsedDateNaissance")
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la modification du cariste : ${e.localizedMessage}")
        }
    }

    // 🔹 Supprimer un cariste
    fun deleteCariste(id: Int) {
        try {
            val deletedRows = DB.database.delete(Caristes) {
                it.id eq id
            }

            if (deletedRows > 0) {
                println("🗑️ Cariste supprimé avec succès (ID: $id)")
            } else {
                println("⚠️ Aucun cariste trouvé avec l'ID: $id")
            }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la suppression du cariste : ${e.localizedMessage}")
        }
    }
}
