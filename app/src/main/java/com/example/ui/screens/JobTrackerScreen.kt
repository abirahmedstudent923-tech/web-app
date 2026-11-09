package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CvViewModel

@Composable
fun JobTrackerScreen(
    viewModel: CvViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val applications by viewModel.applications.collectAsState()
    val cvData by viewModel.cvData.collectAsState()
    val activeLang by viewModel.activeLang.collectAsState()
    val isBn = activeLang == "bn"

    var companyName by remember { mutableStateOf("") }
    var jobPosition by remember { mutableStateOf("") }
    var hrEmail by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }

    val interviewCount = applications.count {
        it.status.contains("ইন্টারভিউ") || it.status.contains("Interview")
    }

    LazyColumn(
        modifier = modifier
            .testTag("job_tracker_screen")
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Summary Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Card 1
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFEFF6FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.BusinessCenter, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "${applications.size}", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                            Text(text = if (isBn) "মোট আবেদন" else "Total Applied", fontSize = 9.sp, color = Color(0xFF64748B))
                        }
                    }
                }

                // Card 2
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFFEF3C7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "$interviewCount", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                            Text(text = if (isBn) "ইন্টারভিউ কল" else "Interviews", fontSize = 9.sp, color = Color(0xFF64748B))
                        }
                    }
                }

                // Card 3
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFECFDF5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "১০০%", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                            Text(text = if (isBn) "সিভি রেডি" else "CV Ready", fontSize = 9.sp, color = Color(0xFF64748B))
                        }
                    }
                }
            }
        }

        // Add Application Form
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isBn) "নতুন চাকরির আবেদন ট্র্যাকারে যোগ করুন" else "Add New Job Application",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF0F172A)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = companyName,
                            onValueChange = { companyName = it },
                            label = { Text(if (isBn) "কোম্পানির নাম (যেমন: বিকাশ / ব্র্যাক ব্যাংক)" else "Company Name", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = jobPosition,
                            onValueChange = { jobPosition = it },
                            label = { Text(if (isBn) "পদবী (যেমন: সফটওয়্যার ইঞ্জিনিয়ার)" else "Position Title", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = hrEmail,
                            onValueChange = { hrEmail = it },
                            label = { Text(if (isBn) "এইচআর ইমেইল (careers@company.com)" else "HR Email", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            label = { Text(if (isBn) "মন্তব্য / নোটস" else "Notes", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            if (companyName.isNotBlank() && jobPosition.isNotBlank()) {
                                viewModel.addApplication(companyName, jobPosition, hrEmail, noteText)
                                companyName = ""
                                jobPosition = ""
                                hrEmail = ""
                                noteText = ""
                            } else {
                                viewModel.showNotification(if (isBn) "কোম্পানি ও পদের নাম পূরণ করুন!" else "Please enter company and position!")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                    ) {
                        Text(if (isBn) "রেকর্ড সংরক্ষণ করুন" else "Save Application Record", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Section Title
        item {
            Text(
                text = if (isBn) "প্রেরিত আবেদনসমূহ (${applications.size})" else "Tracked Applications (${applications.size})",
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = Color(0xFF0F172A)
            )
        }

        // List of Applications
        if (applications.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isBn) "কোনো আবেদন এখনো যোগ করা হয়নি।" else "No applications added yet.",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            items(applications, key = { it.id }) { app ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(14.dp),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = app.company,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = app.position,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF475569)
                                )
                                Text(
                                    text = "${if (isBn) "তারিখ:" else "Date:"} ${app.appliedDate}",
                                    fontSize = 10.sp,
                                    color = Color(0xFF64748B),
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            // Status Pill
                            val isInterview = app.status.contains("ইন্টারভিউ") || app.status.contains("Interview")
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isInterview) Color(0xFFFEF3C7) else Color(0xFFECFDF5),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isInterview) Color(0xFFFCD34D) else Color(0xFFA7F3D0)
                                ),
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = app.status,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isInterview) Color(0xFF92400E) else Color(0xFF065F46),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        if (app.notes.isNotEmpty()) {
                            Text(
                                text = "নোট: ${app.notes}",
                                fontSize = 10.5.sp,
                                color = Color(0xFF334155),
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Actions Row: Direct Send Email & Delete
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Button(
                                    onClick = {
                                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:")
                                            putExtra(Intent.EXTRA_EMAIL, arrayOf(app.contactEmail))
                                            putExtra(Intent.EXTRA_SUBJECT, "Application for ${app.position} - ${cvData.personal.fullName}")
                                            putExtra(Intent.EXTRA_TEXT, "Respected Hiring Team,\n\nPlease find attached my Curriculum Vitae (CV) for the position of ${app.position} at ${app.company}.\n\nSincerely,\n${cvData.personal.fullName}\nPhone: ${cvData.personal.phone}")
                                        }
                                        try {
                                            context.startActivity(emailIntent)
                                        } catch (e: Exception) {
                                            viewModel.showNotification("ইমেইল অ্যাপ পাওয়া যায়নি")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Icon(Icons.Default.Mail, contentDescription = null, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isBn) "মেইল পাঠান" else "Send Email", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }

                                // Quick Status Toggle
                                OutlinedButton(
                                    onClick = {
                                        val nextStatus = if (app.status.contains("আবেদন")) {
                                            if (isBn) "ইন্টারভিউ কল (Interview Scheduled)" else "Interview Scheduled"
                                        } else {
                                            if (isBn) "অফার প্রাপ্ত (Offer Received)" else "Offer Received"
                                        }
                                        viewModel.updateApplicationStatus(app.id, nextStatus)
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text(if (isBn) "স্ট্যাটাস পরিবর্তন" else "Update Status", fontSize = 10.sp)
                                }
                            }

                            IconButton(
                                onClick = { viewModel.removeApplication(app.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
