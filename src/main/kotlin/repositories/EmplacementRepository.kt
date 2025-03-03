package repositories

import database.DB
import ktorm.Emplacements // ✅ Vérifie que ton import est correct (ktorm au lieu de models)
import org.ktorm.dsl.*

object EmplacementRepository {

    // 🔹 Récupérer tous les emplacements
    fun getAllEmplacements(): List<Map<String, Any>> {
        return try {
            DB.database.from(Emplacements)
                .select()
                .map { row ->
                    mapOf(
                        "id" to row[Emplacements.id]!!,
                        "nom" to row[Emplacements.nom]!!
                    )
                }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des emplacements : ${e.localizedMessage}")
            emptyList()
        }
    }

    // 🔹 Ajouter un emplacement
    fun addEmplacement(nom: String): Boolean {
        return try {
            DB.database.insert(Emplacements) {
                set(it.nom, nom)
            }
            true
        } catch (e: Exception) {
            println("⚠️ Erreur lors de l'ajout de l'emplacement : ${e.localizedMessage}")
            false
        }
    }

    // 🔹 Modifier un emplacement
    fun updateEmplacement(id: Int, nom: String): Boolean {
        return try {
            val rowsUpdated = DB.database.update(Emplacements) {
                set(it.nom, nom)
                where { it.id eq id }
            }
            rowsUpdated > 0
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la modification de l'emplacement : ${e.localizedMessage}")
            false
        }
    }

    // 🔹 Supprimer un emplacement (avec vérification)
    fun deleteEmplacement(id: Int): Boolean {
        return try {
            val deletedRows = DB.database.delete(Emplacements) {
                it.id eq id
            }
            deletedRows > 0
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la suppression de l'emplacement : ${e.localizedMessage}")
            false
        }
    }
}