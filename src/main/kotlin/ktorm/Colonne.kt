package ktorm

import org.ktorm.schema.*

object Colonnes : Table<Nothing>("Colonne") {
    val id = int("ID").primaryKey()
    val nom = varchar("Nom")
    val poidsMin = double("Poids_Min")
    val poidsMax = double("Poids_Max")
}