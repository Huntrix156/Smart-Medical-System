package com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

// ---------------------------------------------
// DATA MODELS
// ---------------------------------------------

data class MedicineItem(
    var medicineName: String = "",
    var dosage: String = "",
    var frequency: String = "",
    var route: String = "",
    var duration: String = "",
    var timing: String = "",
    var quantity: String = "",
    var notes: String = ""
)

// ---------------------------------------------
// MAIN SCREEN
// ---------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WritePrescriptionScreen(navController: NavController) {

    val context = LocalContext.current

    // Patient Info
    var patientName by remember { mutableStateOf("John Doe") }
    var patientId by remember { mutableStateOf("PAT-1023") }
    var age by remember { mutableStateOf("29") }
    var gender by remember { mutableStateOf("Male") }
    var weight by remember { mutableStateOf("70kg") }
    var bloodGroup by remember { mutableStateOf("O+") }
    var allergies by remember { mutableStateOf("Penicillin") }
    var diagnosis by remember { mutableStateOf("Upper Respiratory Infection") }

    // Doctor Info
    var doctorName by remember { mutableStateOf("Dr. Sarah Wilson") }
    var specialization by remember { mutableStateOf("General Physician") }
    var hospital by remember { mutableStateOf("SmartCare Hospital") }
    var license by remember { mutableStateOf("LIC-45920") }
    var contact by remember { mutableStateOf("+254700000000") }

    // Notes
    var symptoms by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var labFindings by remember { mutableStateOf("") }

    // Status
    var prescriptionStatus by remember { mutableStateOf("Draft") }

    // Follow-up
    var followUpDate by remember { mutableStateOf("") }
    var reminderEnabled by remember { mutableStateOf(true) }

    // Medicines
    var medicines by remember {
        mutableStateOf(
            mutableListOf(
                MedicineItem()
            )
        )
    }

    // Prescription ID
    val prescriptionId = remember {
        UUID.randomUUID().toString().take(8)
    }

    val currentDate = SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).format(Date())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ---------------------------------------------
        // TITLE
        // ---------------------------------------------

        item {
            Text(
                text = "Write Prescription",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // ---------------------------------------------
        // PATIENT INFORMATION
        // ---------------------------------------------

        item {
            SectionCard(title = "Patient Information") {

                PrescriptionField("Patient Name", patientName) {
                    patientName = it
                }

                PrescriptionField("Patient ID", patientId) {
                    patientId = it
                }

                PrescriptionField("Age", age) {
                    age = it
                }

                PrescriptionField("Gender", gender) {
                    gender = it
                }

                PrescriptionField("Weight", weight) {
                    weight = it
                }

                PrescriptionField("Blood Group", bloodGroup) {
                    bloodGroup = it
                }

                PrescriptionField("Allergies", allergies) {
                    allergies = it
                }

                PrescriptionField("Diagnosis", diagnosis) {
                    diagnosis = it
                }

                Text("Visit Date: $currentDate")
            }
        }

        // ---------------------------------------------
        // DOCTOR INFORMATION
        // ---------------------------------------------

        item {
            SectionCard(title = "Doctor Information") {

                PrescriptionField("Doctor Name", doctorName) {
                    doctorName = it
                }

                PrescriptionField("Specialization", specialization) {
                    specialization = it
                }

                PrescriptionField("Hospital", hospital) {
                    hospital = it
                }

                PrescriptionField("License Number", license) {
                    license = it
                }

                PrescriptionField("Contact", contact) {
                    contact = it
                }
            }
        }

        // ---------------------------------------------
        // MEDICINES
        // ---------------------------------------------

        item {
            Text(
                text = "Medicines",
                fontSize = 22.sp
            )
        }

        itemsIndexed(medicines) { index, medicine ->

            MedicineCard(
                medicine = medicine,
                onDelete = {
                    if (medicines.size > 1) {
                        medicines.removeAt(index)
                    }
                }
            )

            // ---------------------------------------------
            // ALLERGY WARNING
            // ---------------------------------------------

            if (
                allergies.lowercase().contains("penicillin") &&
                medicine.medicineName.lowercase().contains("amoxicillin")
            ) {

                WarningCard(
                    "⚠ Patient allergic to Penicillin-related drugs"
                )
            }

            // ---------------------------------------------
            // OVERDOSE WARNING
            // ---------------------------------------------

            if (
                medicine.medicineName.lowercase()
                    .contains("paracetamol") &&
                medicine.dosage.contains("2000")
            ) {

                WarningCard(
                    "⚠ High Paracetamol dosage detected"
                )
            }
        }

        // ---------------------------------------------
        // ADD MEDICINE BUTTON
        // ---------------------------------------------

        item {
            Button(
                onClick = {
                    medicines.add(MedicineItem())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Another Medicine")
            }
        }

        // ---------------------------------------------
        // CLINICAL NOTES
        // ---------------------------------------------

        item {
            SectionCard(title = "Clinical Notes") {

                PrescriptionField("Symptoms", symptoms) {
                    symptoms = it
                }

                PrescriptionField(
                    "Clinical Observations",
                    observations
                ) {
                    observations = it
                }

                PrescriptionField(
                    "Lab Findings",
                    labFindings
                ) {
                    labFindings = it
                }
            }
        }

        // ---------------------------------------------
        // PRESCRIPTION STATUS
        // ---------------------------------------------

        item {
            SectionCard(title = "Prescription Status") {

                val statuses = listOf(
                    "Draft",
                    "Finalized",
                    "Sent to Pharmacy",
                    "Dispensed"
                )

                statuses.forEach { status ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected = prescriptionStatus == status,
                            onClick = {
                                prescriptionStatus = status
                            }
                        )

                        Text(status)
                    }
                }
            }
        }

        // ---------------------------------------------
        // FOLLOW-UP
        // ---------------------------------------------

        item {
            SectionCard(title = "Follow-up & Reminders") {

                PrescriptionField(
                    "Follow-up Date",
                    followUpDate
                ) {
                    followUpDate = it
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = reminderEnabled,
                        onCheckedChange = {
                            reminderEnabled = it
                        }
                    )

                    Text("Enable Medication Reminder")
                }
            }
        }

        // ---------------------------------------------
        // PRESCRIPTION INFO
        // ---------------------------------------------

        item {
            SectionCard(title = "Prescription Verification") {

                Text("Prescription ID: $prescriptionId")
                Text("Timestamp: $currentDate")
                Text("Doctor Signature: __________________")
            }
        }

        // ---------------------------------------------
        // EMERGENCY WARNINGS
        // ---------------------------------------------

        item {
            SectionCard(title = "Emergency Warnings") {

                WarningCard("⚠ Pregnancy warning required")
                WarningCard("⚠ High-risk medication monitoring")
                WarningCard("⚠ Controlled substances check")
            }
        }

        // ---------------------------------------------
        // PRESCRIPTION HISTORY
        // ---------------------------------------------

        item {
            SectionCard(title = "Prescription History Timeline") {

                Text("• 01/04/2026 - Prescribed Paracetamol")
                Text("• 15/03/2026 - Prescribed Antibiotics")
                Text("• 20/02/2026 - Chronic Asthma Medication")
            }
        }

        // ---------------------------------------------
        // ACTION BUTTONS
        // ---------------------------------------------

        item {

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Prescription Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Prescription")
                }

                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "PDF Generated",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Generate PDF")
                }

                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Prescription Sent to Pharmacy",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send to Pharmacy")
                }

                OutlinedButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Reminder Scheduled",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Schedule Reminder")
                }

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ---------------------------------------------
// MEDICINE CARD
// ---------------------------------------------

@Composable
fun MedicineCard(
    medicine: MedicineItem,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Medicine",
                    fontSize = 20.sp
                )

                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }

            PrescriptionField(
                "Medicine Name",
                medicine.medicineName
            ) {
                medicine.medicineName = it
            }

            PrescriptionField(
                "Dosage",
                medicine.dosage
            ) {
                medicine.dosage = it
            }

            PrescriptionField(
                "Frequency",
                medicine.frequency
            ) {
                medicine.frequency = it
            }

            PrescriptionField(
                "Route",
                medicine.route
            ) {
                medicine.route = it
            }

            PrescriptionField(
                "Duration",
                medicine.duration
            ) {
                medicine.duration = it
            }

            PrescriptionField(
                "Timing",
                medicine.timing
            ) {
                medicine.timing = it
            }

            PrescriptionField(
                "Quantity",
                medicine.quantity
            ) {
                medicine.quantity = it
            }

            PrescriptionField(
                "Additional Notes",
                medicine.notes
            ) {
                medicine.notes = it
            }
        }
    }
}

// ---------------------------------------------
// WARNING CARD
// ---------------------------------------------

@Composable
fun WarningCard(message: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFFFE5E5),
                RoundedCornerShape(12.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            Icons.Default.Warning,
            contentDescription = null,
            tint = Color.Red
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = message,
            color = Color.Red
        )
    }
}

// ---------------------------------------------
// SECTION CARD
// ---------------------------------------------

@Composable
fun SectionCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = title,
                fontSize = 22.sp
            )

            content()
        }
    }
}

// ---------------------------------------------
// TEXT FIELD
// ---------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}