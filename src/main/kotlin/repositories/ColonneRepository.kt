package repositories

import database.DB
import ktorm.Colonnes
import org.ktorm.dsl.*

object ColonneRepository {

    // Récupérer toutes les colonnes (id, nom)
    fun getAllColonnes(): List<Map<String, Any>> {
        return try {
            DB.database.from(Colonnes)
                .select()
                .map { row ->
                    mapOf(
                        "id" to row[Colonnes.id]!!,
                        "nom" to row[Colonnes.nom]!!,
                        "poidsMin" to row[Colonnes.poidsMin]!!,
                        "poidsMax" to row[Colonnes.poidsMax]!!
                    )
                }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des colonnes : ${e.localizedMessage}")
            emptyList()
        }
    }
}

