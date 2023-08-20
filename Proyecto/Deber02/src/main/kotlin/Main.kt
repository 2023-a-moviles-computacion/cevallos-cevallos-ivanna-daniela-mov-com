import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop",
        state = rememberWindowState(width = 400.dp, height = 400.dp)
    ) {
        val count = remember { mutableStateOf(0) }
        val session = remember { mutableStateOf(1) }

        // Establish SQLite connection
        val url = "jdbc:sqlite:try1.db"
        val connection: Connection = DriverManager.getConnection(url)

        MaterialTheme {
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value++
                    }) {
                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        count.value = 0
                    }) {
                    Text("Reset")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        // Increment session and reset count
                        session.value++
                        count.value = 0
                    }) {
                    Text("New Session")
                }
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        // Print the table contents
                        val statement: Statement = connection.createStatement()
                        val query = "SELECT * FROM clicks"
                        val resultSet = statement.executeQuery(query)
                        println("Session\tClicks")
                        while (resultSet.next()) {
                            val sessionNumber = resultSet.getInt("session")
                            val clickCount = resultSet.getInt("clicks")
                            println("$sessionNumber\t$clickCount")
                        }
                    }) {
                    Text("Print Table")
                }
            }
        }
    }
}
