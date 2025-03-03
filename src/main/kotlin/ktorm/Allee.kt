package ktorm

import org.ktorm.schema.*

object Allees : Table<Nothing>("Allee") {
    val id = int("ID").primaryKey()
    val nom = varchar("Nom")
}