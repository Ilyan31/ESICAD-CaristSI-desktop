package ktorm

import org.ktorm.schema.*

object Emplacements : Table<Nothing>("Emplacement") {
    val id = int("ID").primaryKey()
    val nom = varchar("Nom")
}