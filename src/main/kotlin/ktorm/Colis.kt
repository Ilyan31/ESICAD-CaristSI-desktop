package ktorm

import org.ktorm.schema.*

object Colis : Table<Nothing>("Colis") {
    val id = int("ID").primaryKey()
    val code = varchar("Code")
    val description = varchar("Description")
    val poids = double("Poids")
    val volume = double("Volume")
    val dateReception = date("DateReception")
    val idEmplacement = int("ID_Emplacement")
}