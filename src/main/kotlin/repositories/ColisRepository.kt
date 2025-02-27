package repositories

import database.DB
import ktorm.Colis
import ktorm.Emplacements
import org.ktorm.dsl.*
import java.time.LocalDate // ✅ Ajout de l'import correct pour `LocalDate`

object ColisRepository {

    // 🔹 Ajouter un colis
    fun addColis(code: String, description: String, poids: Double, volume: Double, dateReception: String, idEmplacement: Int) {
        DB.database.insert(Colis) {
            set(it.code, code)
            set(it.description, description)
            set(it.poids, poids.toBigDecimal()) // ✅ Conversion en `BigDecimal`
            set(it.volume, volume.toBigDecimal()) // ✅ Conversion en `BigDecimal`
            set(it.dateReception, LocalDate.parse(dateReception)) // ✅ Correction String -> LocalDate
            set(it.idEmplacement, idEmplacement) // ✅ Correction sans `wrapArg()`
        }
    }

    // 🔹 Supprimer un colis
    fun deleteColis(id: Int) {
        DB.database.delete(Colis) {
            it.id eq id
        }
    }
}