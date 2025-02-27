package ktorm

import org.ktorm.schema.*

object Colis : Table<Nothing>("Colis") {
    val id = int("ID").primaryKey()
    val code = varchar("Code")
    val description = varchar("Description")
    val poids = decimal("Poids") // ✅ Correction en `decimal()`
    val volume = decimal("Volume") // ✅ Correction en `decimal()`
    val dateReception = date("DateReception") // ✅ Assurer que c'est bien `date` !
    val idEmplacement = int("ID_Emplacement") // ✅ Supprimé `.references()`
}