// ✅ FIXED: Package now matches this project — was wrongly set to com.example.nexora
package com.example.smartmedicalsystem.ui.theme.screens.Profile.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

// ✅ FIXED: Removed duplicate nexora imports — only smartmedicalsystem imports remain
import com.example.smartmedicalsystem.data.ProfileViewModel
import com.example.smartmedicalsystem.models.ProfileModel
import com.example.smartmedicalsystem.navigation.ROUTE_MAIN_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, userId: String) {

    val context = LocalContext.current
    val profileViewModel: ProfileViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var user by remember { mutableStateOf<ProfileModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isEditMode by remember { mutableStateOf(false) }
    var showDiscardDialog by remember { mutableStateOf(false) }

    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { imageUri = it } }

    // ── Load user from Firebase ───────────────────────────────────
    LaunchedEffect(userId) {
        isLoading = true
        val ref = FirebaseDatabase.getInstance()
            .getReference("Users").child(userId)
        val snapshot = ref.get().await()
        user = snapshot.getValue(ProfileModel::class.java)?.apply { id = userId }
        user?.let {
            firstname = it.firstname ?: ""
            lastname = it.lastname ?: ""
            username = it.username ?: ""
            email = it.email ?: ""
            gender = it.gender ?: ""
        }
        isLoading = false
    }

    // ── Discard dialog ────────────────────────────────────────────
    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            title = { Text("Discard Changes?") },
            text = { Text("You have unsaved changes. Discard them?") },
            confirmButton = {
                TextButton(onClick = {
                    user?.let {
                        firstname = it.firstname ?: ""
                        lastname = it.lastname ?: ""
                        username = it.username ?: ""
                        email = it.email ?: ""
                        gender = it.gender ?: ""
                        imageUri = null
                    }
                    isEditMode = false
                    showDiscardDialog = false
                }) { Text("Discard", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showDiscardDialog = false }) {
                    Text("Keep Editing")
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Profile" else "My Profile") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditMode) showDiscardDialog = true
                        else navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (!isEditMode) {
                        IconButton(onClick = { isEditMode = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                        }
                    } else {
                        IconButton(onClick = { showDiscardDialog = true }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF1565C0))
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(listOf(Color(0xFFE3F2FD), Color(0xFFFFFFFF)))
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // ── Profile Photo ─────────────────────────────────────
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = imageUri ?: user?.imageUrl,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFF1565C0), CircleShape)
                        .shadow(6.dp, CircleShape)
                        .clickable(enabled = isEditMode) { launcher.launch("image/*") }
                )
                if (isEditMode) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1565C0))
                            .clickable { launcher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$firstname $lastname",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )
            Text(text = "@$username", fontSize = 14.sp, color = Color.Gray)

            if (isEditMode) {
                Text(
                    text = "✏️ Tap a field to edit",
                    fontSize = 12.sp,
                    color = Color(0xFF1565C0),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Info Card ─────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Personal Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF1565C0)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    if (!isEditMode) {
                        ProfileInfoRow(Icons.Default.Person, "First Name", firstname.ifBlank { "Not set" })
                        ProfileInfoRow(Icons.Default.Person, "Last Name", lastname.ifBlank { "Not set" })
                        ProfileInfoRow(Icons.Default.Person, "Username", username.ifBlank { "Not set" })
                        ProfileInfoRow(Icons.Default.Email, "Email", email.ifBlank { "Not set" })
                        ProfileInfoRow(Icons.Default.Wc, "Gender", gender.ifBlank { "Not set" })
                    } else {
                        val fieldShape = RoundedCornerShape(12.dp)
                        val fieldModifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)

                        OutlinedTextField(
                            value = firstname, onValueChange = { firstname = it },
                            label = { Text("First Name") }, leadingIcon = { Icon(Icons.Default.Person, null) },
                            singleLine = true, modifier = fieldModifier, shape = fieldShape
                        )
                        OutlinedTextField(
                            value = lastname, onValueChange = { lastname = it },
                            label = { Text("Last Name") }, leadingIcon = { Icon(Icons.Default.Person, null) },
                            singleLine = true, modifier = fieldModifier, shape = fieldShape
                        )
                        OutlinedTextField(
                            value = username, onValueChange = { username = it },
                            label = { Text("Username") }, leadingIcon = { Icon(Icons.Default.Person, null) },
                            singleLine = true, modifier = fieldModifier, shape = fieldShape
                        )
                        OutlinedTextField(
                            value = email, onValueChange = { email = it },
                            label = { Text("Email") }, leadingIcon = { Icon(Icons.Default.Email, null) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true, modifier = fieldModifier, shape = fieldShape
                        )
                        OutlinedTextField(
                            value = gender, onValueChange = { gender = it },
                            label = { Text("Gender") }, leadingIcon = { Icon(Icons.Default.Wc, null) },
                            singleLine = true, modifier = fieldModifier, shape = fieldShape
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Save / Discard (edit mode only) ───────────────────
            if (isEditMode) {
                Button(
                    onClick = {
                        profileViewModel.updateUser(
                            userId = userId,
                            imageUri = imageUri,
                            firstname = firstname,
                            lastname = lastname,
                            username = username,
                            email = email,
                            gender = gender,
                            context = context,
                            navController = navController
                        )
                        scope.launch { snackbarHostState.showSnackbar("✅ Profile updated!") }
                        isEditMode = false
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Changes", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { showDiscardDialog = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE))
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color.DarkGray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Discard Changes", color = Color.DarkGray, fontWeight = FontWeight.Bold)
                }
            }

            // ── Back to Dashboard (view mode) ─────────────────────
            if (!isEditMode) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PROFILE)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                )
                {
                    Text("Back ", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ── Reusable view-mode row ────────────────────────────────────
@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = Color(0xFF1565C0), modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF212121))
        }
    }
    HorizontalDivider(color = Color(0xFFF5F5F5))
}