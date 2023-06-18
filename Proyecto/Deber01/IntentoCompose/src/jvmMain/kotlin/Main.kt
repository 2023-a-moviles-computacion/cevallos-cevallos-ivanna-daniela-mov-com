import Tab
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.text.SimpleDateFormat
import java.util.*
import java.io.*
import androidx.compose.ui.graphics.Color
enum class Tab {
    Welcome,
    Pharmacy,
    Medicine
}
val column1Weight = 0.2f
val column2Weight = 0.2f
val column3Weight = 0.2f
val column4Weight = 0.2f
val column5Weight = 0.1f
val column6Weight = 0.1f

data class Pharmacy(
    var dateOfOpening: Date,
    var isOpen: Boolean,
    var pharmacyName: String,
    var pharmacyID: Int,
    var stockValue: Double,
    var isModified: Boolean = false
)


fun main() = application {
    val dateValue = remember { mutableStateOf("") }
    val isOpenValue = remember { mutableStateOf("") }
    val pharmacyNameValue = remember { mutableStateOf("") }
    val pharmacyIDValue = remember { mutableStateOf("") }
    val stockValue = remember { mutableStateOf("") }
    val recordOfPharmacies = remember { mutableStateListOf<Pharmacy>() }
    val isPrintClicked = remember { mutableStateOf(false) }
    val selectedPharmacy = remember { mutableStateOf<Pharmacy?>(null) }
    val currentTab = remember { mutableStateOf(Tab.Welcome) }

    val file = File("RecordPharmacies.txt")
    if (file.exists()) {
        // If the file exists, read the pharmacies from it
        readPharmaciesFromFile(file)?.let { recordOfPharmacies.addAll(it) }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Farmacia",
        state = rememberWindowState(width = 1000.dp, height = 1000.dp)
    ) {
        MaterialTheme {
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                TabRow(selectedTabIndex = currentTab.value.ordinal) {
                    Tab(
                        selected = currentTab.value == Tab.Welcome,
                        onClick = { currentTab.value = Tab.Welcome },
                        text = { Text("Welcome") }
                    )
                    Tab(
                        selected = currentTab.value == Tab.Pharmacy,
                        onClick = { currentTab.value = Tab.Pharmacy },
                        text = { Text("Pharmacy") }
                    )
                    Tab(
                        selected = currentTab.value == Tab.Medicine,
                        onClick = { currentTab.value = Tab.Medicine },
                        text = { Text("Medicine") }
                    )
                }

                when (currentTab.value) {
                    Tab.Welcome -> {
                        Box(
                            Modifier.fillMaxSize().background(Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Bienvenido")
                        }
                    }
                    Tab.Pharmacy -> {
                        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                            TextField(
                                value = pharmacyNameValue.value,
                                onValueChange = { pharmacyNameValue.value = it },
                                label = { Text("Pharmacy Name") }
                            )

                            TextField(
                                value = pharmacyIDValue.value,
                                onValueChange = { pharmacyIDValue.value = it },
                                label = { Text("Pharmacy ID") }
                            )
                            TextField(
                                value = dateValue.value,
                                onValueChange = { dateValue.value = it },
                                label = { Text("Date of Opening") }
                            )

                            TextField(
                                value = isOpenValue.value,
                                onValueChange = { isOpenValue.value = it },
                                label = { Text("Is Open") }
                            )
                            TextField(
                                value = stockValue.value,
                                onValueChange = { stockValue.value = it },
                                label = { Text("Stock Value") }
                            )

                            if (selectedPharmacy.value != null) {
                                Button(
                                    onClick = {
                                        selectedPharmacy.value?.let { pharmacy ->
                                            pharmacy.pharmacyName = pharmacyNameValue.value
                                            pharmacy.pharmacyID = pharmacyIDValue.value.toIntOrNull() ?: 0
                                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                            pharmacy.dateOfOpening = dateFormat.parse(dateValue.value) ?: Date()
                                            pharmacy.isOpen = isOpenValue.value.toBoolean()
                                            pharmacy.stockValue = stockValue.value.toDoubleOrNull() ?: 0.0
                                            pharmacy.isModified = true

                                            // Update the recordOfPharmacies list with the modified pharmacy
                                            val index = recordOfPharmacies.indexOf(pharmacy)
                                            if (index != -1) {
                                                recordOfPharmacies[index] = pharmacy
                                            }
                                            writePharmaciesToFile(file, recordOfPharmacies)
                                        }
                                        selectedPharmacy.value = null
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("Save Changes")
                                }
                            } else {
                                Button(
                                    onClick = {
                                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                        val parsedDate = dateFormat.parse(dateValue.value)
                                        val pharmacy = Pharmacy(
                                            dateOfOpening = parsedDate ?: Date(),
                                            isOpen = isOpenValue.value.toBoolean(),
                                            pharmacyName = pharmacyNameValue.value,
                                            pharmacyID = pharmacyIDValue.value.toIntOrNull() ?: 0,
                                            stockValue = stockValue.value.toDoubleOrNull() ?: 0.0
                                        )
                                        recordOfPharmacies.add(pharmacy)
                                        writePharmaciesToFile(file, recordOfPharmacies)
                                        println("Pharmacy: $pharmacy")
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("Save Pharmacy")
                                }
                            }

                            Button(
                                onClick = { isPrintClicked.value = true },
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                Text("Print")
                            }

                            if (isPrintClicked.value) {
                                TableScreen(recordOfPharmacies, onEditClicked = { pharmacy ->
                                    // Set the values of text fields with the data from the selected row
                                    pharmacyNameValue.value = pharmacy.pharmacyName
                                    pharmacyIDValue.value = pharmacy.pharmacyID.toString()
                                    dateValue.value =
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(pharmacy.dateOfOpening)
                                    isOpenValue.value = pharmacy.isOpen.toString()
                                    stockValue.value = pharmacy.stockValue.toString()
                                    selectedPharmacy.value = pharmacy
                                }, onDeleteClicked = { pharmacy ->
                                    recordOfPharmacies.remove(pharmacy)
                                    writePharmaciesToFile(file, recordOfPharmacies)
                                })
                            }
                        }
                    }
                    Tab.Medicine -> {
                        Box(
                            Modifier.fillMaxSize().background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Medicine")
                        }
                    }
                }
            }
        }
    }
}


fun readPharmaciesFromFile(file: File): List<Pharmacy>? {
    try {
        val pharmacies = mutableListOf<Pharmacy>()
        BufferedReader(FileReader(file)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val parts = line!!.split(",")
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(parts[0])
                val isOpen = parts[1].toBoolean()
                val pharmacyName = parts[2]
                val pharmacyID = parts[3].toInt()
                val stockValue = parts[4].toDouble()
                pharmacies.add(Pharmacy(date, isOpen, pharmacyName, pharmacyID, stockValue))
            }
        }
        return pharmacies
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun writePharmaciesToFile(file: File, pharmacies: List<Pharmacy>) {
    try {
        val writer = FileWriter(file)
        pharmacies.forEach { pharmacy ->
            val dateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(pharmacy.dateOfOpening)
            val line = "$dateString,${pharmacy.isOpen},${pharmacy.pharmacyName},${pharmacy.pharmacyID},${pharmacy.stockValue}"
            writer.write(line)
            writer.write(System.lineSeparator())
        }
        writer.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


@Composable
fun TableScreen(recordOfPharmacies: List<Pharmacy>, onEditClicked: (Pharmacy) -> Unit, onDeleteClicked: (Pharmacy) -> Unit) {
    val tableData = recordOfPharmacies.map { pharmacy ->
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(pharmacy.dateOfOpening)
        " ${pharmacy.pharmacyID}, ${pharmacy.pharmacyName}, $formattedDate, ${pharmacy.isOpen}, ${pharmacy.stockValue}"
    }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Row() {
                TableCell(text = "Pharmacy ID", weight = column1Weight)
                TableCell(text = "Pharmacy Name", weight = column2Weight)
                TableCell(text = "Date of Opening", weight = column3Weight)
                TableCell(text = "Is Open", weight = column4Weight)
                TableCell(text = "Stock Value", weight = column5Weight)
                TableCell(text = "Edit", weight = 0.1f) // Empty cell for the "Edit" button
                TableCell(text = "Delete", weight = 0.1f) // Empty cell for the "Delete" button
            }
        }

        items(recordOfPharmacies) { pharmacy ->
            PharmacyRow(pharmacy = pharmacy, onEditClicked = onEditClicked, onDeleteClicked = onDeleteClicked)
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Box(
        Modifier
            .weight(weight)
            .padding(2.dp)
            .background(Color.White)
            .border(1.dp, Color.Black)
    ) {
        Text(
            text = text,
            Modifier.padding(2.dp)
        )
    }
}

@Composable
fun PharmacyRow(
    pharmacy: Pharmacy,
    onEditClicked: (Pharmacy) -> Unit,
    onDeleteClicked: (Pharmacy) -> Unit
) {
    val isSelected = remember { mutableStateOf(false) }

    Row(Modifier.fillMaxWidth()) {
        Text(
            text = pharmacy.pharmacyID.toString(),
            modifier = Modifier.weight(column1Weight)
        )
        Text(
            text = pharmacy.pharmacyName,
            modifier = Modifier.weight(column2Weight)
        )
        Text(
            text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(pharmacy.dateOfOpening),
            modifier = Modifier.weight(column3Weight)
        )
        Text(
            text = pharmacy.isOpen.toString(),
            modifier = Modifier.weight(column4Weight)
        )
        Text(
            text = pharmacy.stockValue.toString(),
            modifier = Modifier.weight(column5Weight)
        )
        IconButton(
            onClick = { onEditClicked(pharmacy) },
            modifier = Modifier.weight(column6Weight)
        ) {
            Icon(Icons.Filled.Edit, contentDescription = "Edit")
        }
        IconButton(
            onClick = { onDeleteClicked(pharmacy) },
            modifier = Modifier.weight(column6Weight)
        ) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}
