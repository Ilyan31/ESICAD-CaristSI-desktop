package ktorm

import org.ktorm.schema.*

object Colis : Table<Nothing>("Colis") {
    val id = int("ID").primaryKey()
    val code = varchar("Code") // ✅ Suppression du paramètre `length`
    val description = varchar("Description") // ✅ Suppression du paramètre `length`
    val poids = double("Poids") // ✅ Type correct pour `double`
    val volume = double("Volume") // ✅ Type correct pour `double`
    val dateReception = date("DateReception")
    val idEmplacement = int("ID_Emplacement")
}