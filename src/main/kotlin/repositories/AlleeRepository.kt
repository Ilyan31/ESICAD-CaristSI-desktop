package repositories

import database.DB
import ktorm.Allees
import org.ktorm.dsl.*

object AlleeRepository {

    // Récupérer toutes les allées (id, nom)
    fun getAllAllees(): List<Map<String, Any>> {
        return try {
            DB.database.from(Allees)
                .select()
                .map { row ->
                    mapOf(
                        "id" to row[Allees.id]!!,
                        "nom" to row[Allees.nom]!!
                    )
                }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des allées : ${e.localizedMessage}")
            emptyList()
        }
    }
}

