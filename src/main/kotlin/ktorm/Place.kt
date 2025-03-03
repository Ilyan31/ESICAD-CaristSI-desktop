package ktorm

import org.ktorm.schema.*

object Places : Table<Nothing>("Place") {
    val id = int("ID").primaryKey()
    val numero = int("Numero")
    val idEmplacement = int("ID_Emplacement")
    val disponible = boolean("Disponible")
}