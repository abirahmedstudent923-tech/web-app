package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.CvViewModel
import com.example.ui.screens.CoverLetterScreen
import com.example.ui.screens.CvBuilderScreen
import com.example.ui.screens.JobTrackerScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.util.PdfPrinter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ResumeMakerApp()
            }
        }
    }
}

@Composable
fun ResumeMakerApp(viewModel: CvViewModel = viewModel()) {
    val context = LocalContext.current
    val activeTab by viewModel.activeTab.collectAsState()
    val activeLang by viewModel.activeLang.collectAsState()
    val cvData by viewModel.cvData.collectAsState()
    val template by viewModel.selectedTemplate.collectAsState()
    val accentHex by viewModel.accentColorHex.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()
    val applications by viewModel.applications.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val isBn = activeLang == "bn"

    // Show toast / snackbar on notification
    LaunchedEffect(userMessage) {
        userMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearNotification()
        }
    }

    // Android back button handling: if in tab 1 or 2, back button returns to tab 0
    BackHandler(enabled = activeTab != 0) {
        viewModel.selectTab(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            Surface(
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Logo & App Title
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.testTag("app_branding_header")
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF0F172A)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BusinessCenter,
                                contentDescription = null,
                                tint = Color(0xFF34D399),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isBn) "ওয়েব অ্যাপস" else "Web Apps",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF0F172A)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF059669))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "PRO",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                            Text(
                                text = if (isBn) "প্রফেশনাল সিভি ও অ্যাপ্লিকেশন স্যুট" else "Smart CV & Application Suite",
                                fontSize = 9.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Top Action Bar: Language Switcher & Print Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Language pill
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF1F5F9),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        ) {
                            Row(modifier = Modifier.padding(2.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(if (isBn) Color(0xFF059669) else Color.Transparent)
                                        .clickable { viewModel.setLanguage("bn") }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "বাংলা",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isBn) Color.White else Color(0xFF475569)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(if (!isBn) Color(0xFF059669) else Color.Transparent)
                                        .clickable { viewModel.setLanguage("en") }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "EN",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (!isBn) Color.White else Color(0xFF475569)
                                    )
                                }
                            }
                        }

                        // Print / PDF Button
                        IconButton(
                            onClick = {
                                PdfPrinter.printCv(context, cvData, template, activeLang, accentHex)
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF0F172A))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Print,
                                contentDescription = "Print or Save PDF",
                                tint = Color(0xFF34D399),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                // Tab 0: CV Builder
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    icon = {
                        Icon(Icons.Default.Description, contentDescription = "CV Builder")
                    },
                    label = {
                        Text(
                            text = if (isBn) "সিভি মেকার" else "CV Maker",
                            fontSize = 10.sp,
                            fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF059669),
                        indicatorColor = Color(0xFFECFDF5),
                        selectedTextColor = Color(0xFF059669),
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_tab_builder")
                )

                // Tab 1: Cover Letter
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    icon = {
                        Icon(Icons.Default.AutoAwesome, contentDescription = "Cover Letter")
                    },
                    label = {
                        Text(
                            text = if (isBn) "দরখাস্ত ও চিঠি" else "Cover Letter",
                            fontSize = 10.sp,
                            fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFD97706),
                        indicatorColor = Color(0xFFFEF3C7),
                        selectedTextColor = Color(0xFFD97706),
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_tab_cover_letter")
                )

                // Tab 2: Job Tracker
                NavigationBarItem(
                    selected = activeTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    icon = {
                        BadgedBox(badge = {
                            if (applications.isNotEmpty()) {
                                Badge(containerColor = Color(0xFF2563EB)) {
                                    Text("${applications.size}", fontSize = 9.sp)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Send, contentDescription = "Job Tracker")
                        }
                    },
                    label = {
                        Text(
                            text = if (isBn) "আবেদন ট্র্যাকার" else "Job Tracker",
                            fontSize = 10.sp,
                            fontWeight = if (activeTab == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2563EB),
                        indicatorColor = Color(0xFFEFF6FF),
                        selectedTextColor = Color(0xFF2563EB),
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag("nav_tab_tracker")
                )
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = activeTab,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            label = "ScreenTransition"
        ) { tab ->
            when (tab) {
                0 -> CvBuilderScreen(viewModel = viewModel)
                1 -> CoverLetterScreen(viewModel = viewModel)
                2 -> JobTrackerScreen(viewModel = viewModel)
            }
        }
    }
}
