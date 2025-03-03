package ktorm

import org.ktorm.schema.*

object Emplacements : Table<Nothing>("Emplacement") {
    val id = int("ID").primaryKey()
    val nom = varchar("Nom")
    // On NE met pas .references(…). Juste des colonnes simples.
    val idAllee = int("ID_Allee")
    val idColonne = int("ID_Colonne")
}