package repositories

import database.DB
import ktorm.Colis
import org.ktorm.dsl.*
import java.time.LocalDate

object ColisRepository {

    // 🔹 Récupérer tous les colis
    fun getAllColis(): List<Map<String, Any>> {
        return try {
            DB.database.from(Colis)
                .select()
                .mapNotNull { row ->
                    val id = row[Colis.id] ?: return@mapNotNull null
                    val code = row[Colis.code] ?: return@mapNotNull null
                    val description = row[Colis.description] ?: return@mapNotNull null
                    val poids = row[Colis.poids] ?: return@mapNotNull null
                    val volume = row[Colis.volume] ?: return@mapNotNull null
                    val dateReception = row[Colis.dateReception]?.toString() ?: return@mapNotNull null
                    val idEmplacement = row[Colis.idEmplacement] ?: return@mapNotNull null

                    mapOf(
                        "id" to id,
                        "code" to code,
                        "description" to description,
                        "poids" to poids,
                        "volume" to volume,
                        "dateReception" to dateReception,
                        "idEmplacement" to idEmplacement
                    )
                }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des colis : ${e.localizedMessage}")
            e.printStackTrace()
            emptyList()
        }
    }

    // 🔹 Ajouter un colis
    fun addColis(code: String, description: String, poids: Double, volume: Double, dateReception: String, idEmplacement: Int) {
        try {
            val parsedDateReception = LocalDate.parse(dateReception)

            DB.database.insert(Colis) {
                set(Colis.code, code)
                set(Colis.description, description)
                set(Colis.poids, poids)
                set(Colis.volume, volume)
                set(Colis.dateReception, parsedDateReception)
                set(Colis.idEmplacement, idEmplacement)
            }

            println("✅ Colis ajouté : Code=$code, Poids=$poids kg, Volume=$volume m³")
        } catch (e: Exception) {
            println("⚠️ Erreur lors de l'ajout du colis : ${e.localizedMessage}")
            e.printStackTrace()
        }
    }

    // 🔹 Modifier un colis
    fun updateColis(id: Int, code: String, description: String, poids: Double, volume: Double, dateReception: String, idEmplacement: Int) {
        try {
            val parsedDateReception = LocalDate.parse(dateReception) // ✅ Utilisation correcte

            DB.database.update(Colis) {
                set(Colis.code, code)
                set(Colis.description, description)
                set(Colis.poids, poids)
                set(Colis.volume, volume)
                set(Colis.dateReception, parsedDateReception)
                set(Colis.idEmplacement, idEmplacement)
                where { Colis.id eq id }
            }
            println("✅ Colis modifié : ID=$id, Nouveau Code=$code")
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la modification du colis : ${e.localizedMessage}")
            e.printStackTrace()
        }
    }

    // 🔹 Supprimer un colis
    fun deleteColis(id: Int) {
        try {
            val deletedRows = DB.database.delete(Colis) {
                it.id eq id
            }

            if (deletedRows > 0) {
                println("🗑️ Colis supprimé avec succès (ID: $id)")
            } else {
                println("⚠️ Aucun colis trouvé avec l'ID: $id")
            }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la suppression du colis : ${e.localizedMessage}")
            e.printStackTrace()
        }
    }
}