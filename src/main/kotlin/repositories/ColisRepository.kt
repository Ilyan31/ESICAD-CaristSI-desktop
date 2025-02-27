package repositories

import database.DB
import ktorm.Colis
import org.ktorm.dsl.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ColisRepository {

    // 🔹 Récupérer tous les colis
    fun getAllColis(): List<Map<String, Any>> {
        return try {
            val colisList = DB.database.from(Colis)
                .select()
                .mapNotNull { row ->
                    val id = row[Colis.id] ?: return@mapNotNull null
                    val code = row[Colis.code] ?: return@mapNotNull null
                    val description = row[Colis.description] ?: return@mapNotNull null
                    val poids = row[Colis.poids]?.let { it as BigDecimal } ?: return@mapNotNull null
                    val volume = row[Colis.volume]?.let { it as BigDecimal } ?: return@mapNotNull null
                    val dateReception = row[Colis.dateReception]?.toString() ?: return@mapNotNull null
                    val idEmplacement = row[Colis.idEmplacement] ?: return@mapNotNull null

                    mapOf(
                        "id" to id,
                        "code" to code,
                        "description" to description,
                        "poids" to poids.toDouble(),
                        "volume" to volume.toDouble(),
                        "dateReception" to dateReception,
                        "idEmplacement" to idEmplacement
                    )
                }

            println("📦 Colis récupérés : $colisList") // 🔹 Log pour voir les colis en console
            colisList
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des colis : ${e.localizedMessage}")
            emptyList()
        }
    }

    // 🔹 Ajouter un colis
    fun addColis(
        code: String,
        description: String,
        poids: Double,
        volume: Double,
        dateReception: String,
        idEmplacement: Int
    ) {
        try {
            // 🔹 Vérification et conversion de la date
            val parsedDate = try {
                LocalDate.parse(dateReception, DateTimeFormatter.ISO_DATE)
            } catch (e: Exception) {
                println("❌ Erreur : La date fournie ($dateReception) est invalide.")
                return
            }

            DB.database.insert(Colis) {
                set(it.code, code)
                set(it.description, description)
                set(it.poids, BigDecimal.valueOf(poids)) // ✅ Correction en `BigDecimal`
                set(it.volume, BigDecimal.valueOf(volume)) // ✅ Correction en `BigDecimal`
                set(it.dateReception, parsedDate) // ✅ Correction en `LocalDate`
                set(it.idEmplacement, idEmplacement) // ✅ Correction clé étrangère
            }
            println("✅ Colis ajouté avec succès : Code=$code, Emplacement=$idEmplacement")
        } catch (e: Exception) {
            println("⚠️ Erreur lors de l'ajout du colis : ${e.localizedMessage}")
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
        }
    }
}