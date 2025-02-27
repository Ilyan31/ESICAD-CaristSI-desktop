package ktorm

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Caristes : Table<Nothing>("Caristes") {
    val id = int("ID").primaryKey()
    val nom = varchar("Nom") // Correction du champ
    val prenom = varchar("Prenom") // Correction du champ
    val mdp = varchar("MDP")
    val naissance = date("Naissance")
    val embauche = date("Embauche")
    val login = varchar("Login")
}