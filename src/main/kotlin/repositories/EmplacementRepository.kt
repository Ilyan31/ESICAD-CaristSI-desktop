package repositories

import database.DB
import ktorm.Emplacements // ✅ Correction de l'import (ktorm au lieu de models)
import org.ktorm.dsl.*

object `EmplacementRepository` {

    // 🔹 Récupérer tous les emplacements
    fun getAllEmplacements(): List<Map<String, Any>> {
        return DB.database.from(Emplacements)
            .select()
            .map { row ->
                mapOf(
                    "id" to row[Emplacements.id]!!,
                    "nom" to row[Emplacements.nom]!!
                )
            }
    }

    // 🔹 Ajouter un emplacement
    fun addEmplacement(nom: String) {
        DB.database.insert(Emplacements) {
            set(it.nom, nom)
        }
    }

    // 🔹 Supprimer un emplacement
    fun deleteEmplacement(id: Int) {
        DB.database.delete(Emplacements) {
            it.id eq id
        }
    }
}