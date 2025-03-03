package repositories

import database.DB
import ktorm.Allees
import ktorm.Colonnes
import ktorm.Emplacements
import ktorm.Places
import org.ktorm.dsl.*

object EmplacementRepository {

    /**
     * 🔹 Récupérer tous les emplacements,
     *    en affichant aussi le nom de l'allée et de la colonne,
     *    et en calculant le nombre de places disponibles.
     */
    fun getAllEmplacements(): List<Map<String, Any>> {
        return try {
            // ⚠️ On n'utilise plus joinReferencesAndSelect(), on fait des leftJoin explicites :
            DB.database.from(Emplacements)
                .leftJoin(Allees, on = Emplacements.idAllee eq Allees.id)
                .leftJoin(Colonnes, on = Emplacements.idColonne eq Colonnes.id)
                .select()
                .map { row ->
                    val idEmp = row[Emplacements.id]!!
                    val nomEmp = row[Emplacements.nom]!!

                    // On récupère le nom de l'allée et de la colonne (sinon ça renvoie null -> "")
                    val nomAllee = row[Allees.nom] ?: ""
                    val nomColonne = row[Colonnes.nom] ?: ""

                    // Calcul du nombre de places disponibles
                    val placesDispo = DB.database.from(Places)
                        .select()
                        .where {
                            (Places.idEmplacement eq idEmp) and (Places.disponible eq true)
                        }
                        .totalRecords

                    mapOf(
                        "id" to idEmp,
                        "nom" to nomEmp,
                        "allee" to nomAllee,
                        "colonne" to nomColonne,
                        "places_disponibles" to placesDispo
                    )
                }
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la récupération des emplacements : ${e.localizedMessage}")
            emptyList()
        }
    }

    // 🔹 Ajouter un nouvel emplacement
    fun addEmplacement(nom: String, idAllee: Int, idColonne: Int): Boolean {
        return try {
            DB.database.insert(Emplacements) {
                set(it.nom, nom)
                set(it.idAllee, idAllee)
                set(it.idColonne, idColonne)
            }
            true
        } catch (e: Exception) {
            println("⚠️ Erreur lors de l'ajout de l'emplacement : ${e.localizedMessage}")
            false
        }
    }

    // 🔹 Modifier un emplacement
    fun updateEmplacement(id: Int, nom: String, idAllee: Int, idColonne: Int): Boolean {
        return try {
            val updatedRows = DB.database.update(Emplacements) {
                set(it.nom, nom)
                set(it.idAllee, idAllee)
                set(it.idColonne, idColonne)
                where { it.id eq id }
            }
            updatedRows > 0
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la modification de l'emplacement : ${e.localizedMessage}")
            false
        }
    }

    // 🔹 Supprimer un emplacement
    fun deleteEmplacement(id: Int): Boolean {
        return try {
            val deletedRows = DB.database.delete(Emplacements) { it.id eq id }
            deletedRows > 0
        } catch (e: Exception) {
            println("⚠️ Erreur lors de la suppression de l'emplacement : ${e.localizedMessage}")
            false
        }
    }
}