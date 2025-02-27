package repositories  // ✅ Vérifie que le package est bien déclaré

import database.DB
import ktorm.Caristes
import org.ktorm.dsl.*

object `CaristeRepository` {
    fun getAllCaristes(): List<Map<String, Any>> {
        return DB.database.from(Caristes)
            .select()
            .map { row ->
                mapOf(
                    "id" to row[Caristes.id]!!,
                    "nom" to row[Caristes.nom]!!,
                    "prenom" to row[Caristes.prenom]!!,
                    "naissance" to row[Caristes.naissance]!!,
                    "embauche" to row[Caristes.embauche]!!,
                    "login" to row[Caristes.login]!!
                )
            }
    }
}

