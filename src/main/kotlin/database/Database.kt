package database  // ✅ Vérifie que ce package est bien défini

import org.ktorm.database.Database

object DB {
    val database = Database.connect(
        url = "jdbc:mysql://localhost:3306/carist-si",
        user = "root",
        password = "",
        driver = "com.mysql.cj.jdbc.Driver"
    )
}

