package ktorm

import org.ktorm.schema.*

object Colis : Table<Nothing>("Colis") {
    val id = int("ID").primaryKey()
    val code = varchar("Code")
    val description = varchar("Description")
    val poids = decimal("Poids") // ✅ Correction : suppression de la précision
    val volume = decimal("Volume") // ✅ Correction : suppression de la précision
    val dateReception = date("DateReception") // ✅ Garder `date()`
    val idEmplacement = int("ID_Emplacement") // ✅ Clé étrangère
}