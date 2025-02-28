package ktorm

import org.ktorm.schema.*

object Caristes : Table<Nothing>("Caristes") {
    val id = int("ID").primaryKey()
    val nom = varchar("Nom")
    val prenom = varchar("Prenom")
    val login = varchar("Login")
    val mdp = varchar("Mdp")
    val dateNaissance = date("Naissance") // ✅ Type `date`, parfait !
    val dateEmbauche = date("Embauche") // ✅ Type `date`, parfait !
}