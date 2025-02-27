package database

import org.ktorm.database.Database

object DB {
    val database: Database by lazy {
        try {
            val db = Database.connect(
                url = "jdbc:mysql://localhost:3306/carist-si",
                user = "root",
                password = "",
                driver = "com.mysql.cj.jdbc.Driver"
            )
            println("✅ Connexion réussie à la base de données MySQL.")
            db
        } catch (e: Exception) {
            println("❌ Erreur de connexion à la base de données : ${e.localizedMessage}")
            throw e
        }
    }
}