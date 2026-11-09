package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CvViewModel

@Composable
fun CoverLetterScreen(
    viewModel: CvViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val form by viewModel.coverLetterData.collectAsState()
    val isGenerating by viewModel.isGeneratingLetter.collectAsState()
    val cvData by viewModel.cvData.collectAsState()
    val activeLang by viewModel.activeLang.collectAsState()
    val isBn = activeLang == "bn"

    LazyColumn(
        modifier = modifier
            .testTag("cover_letter_screen")
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isBn) "হিউম্যানাইজড আবেদনপত্র ও কভার লেটার" else "Humanized Cover Letter & Dorkhast",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = if (isBn) "রোবটিক ক্লিশে ছাড়া খাঁটি মানুষের ভাষায় অফিসিয়াল আবেদন" else "100% natural, confident wording for official job applications",
                            color = Color(0xFF94A3B8),
                            fontSize = 10.5.sp
                        )
                    }
                }
            }
        }

        // Form Fields
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = if (isBn) "আবেদনের বিবরণ পূরণ করুন" else "Application Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color(0xFF0F172A)
                    )

                    // Format & Tone Selector
                    Text(text = if (isBn) "ফরম্যাট ও টোন:" else "Format & Tone:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF475569))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val isDorkhast = form.tone == "dorkhast"
                        Surface(
                            onClick = { viewModel.updateCoverLetterForm { it.copy(tone = "dorkhast") } },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isDorkhast) Color(0xFFECFDF5) else Color(0xFFF8FAFC),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isDorkhast) Color(0xFF059669) else Color(0xFFE2E8F0)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = if (isBn) "সরকারি/প্রাতিষ্ঠানিক দরখাস্ত" else "BD Formal Dorkhast",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isDorkhast) Color(0xFF065F46) else Color(0xFF1E293B)
                                )
                                Text(
                                    text = if (isBn) "বরাবর, বিষয়, বিনীত নিবেদন..." else "Traditional formal format",
                                    fontSize = 9.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        Surface(
                            onClick = { viewModel.updateCoverLetterForm { it.copy(tone = "professional") } },
                            shape = RoundedCornerShape(8.dp),
                            color = if (!isDorkhast) Color(0xFFECFDF5) else Color(0xFFF8FAFC),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (!isDorkhast) Color(0xFF059669) else Color(0xFFE2E8F0)),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = if (isBn) "কর্পোরেট কভার লেটার" else "Corporate Cover Letter",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (!isDorkhast) Color(0xFF065F46) else Color(0xFF1E293B)
                                )
                                Text(
                                    text = if (isBn) "আন্তর্জাতিক হাই-ইমপ্যাক্ট টোন" else "High-impact narrative",
                                    fontSize = 9.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }
                    }

                    // Language & Experience Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            onClick = {
                                val next = if (form.language == "bn") "en" else "bn"
                                viewModel.updateCoverLetterForm { it.copy(language = next) }
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier
                                .weight(0.5f)
                                .height(56.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = if (isBn) "ভাষা" else "Language", fontSize = 9.sp, color = Color(0xFF64748B))
                                    Text(
                                        text = if (form.language == "bn") "🇧🇩 বাংলা" else "🇬🇧 English",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }

                        OutlinedTextField(
                            value = form.userExperienceYears,
                            onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(userExperienceYears = it) } },
                            label = { Text(if (isBn) "মোট অভিজ্ঞতা" else "Total Experience", fontSize = 10.sp) },
                            modifier = Modifier.weight(0.5f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    OutlinedTextField(
                        value = form.companyName,
                        onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(companyName = it) } },
                        label = { Text(if (isBn) "প্রতিষ্ঠানের নাম (যেমন: গ্রামীণফোন / বিকাশ)" else "Company Name", fontSize = 10.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = form.jobTitle,
                            onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(jobTitle = it) } },
                            label = { Text(if (isBn) "আবেদনের পদবী" else "Job Position", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        OutlinedTextField(
                            value = form.recipientTitle,
                            onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(recipientTitle = it) } },
                            label = { Text(if (isBn) "প্রাপক / সম্বোধন" else "Recipient Title", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    OutlinedTextField(
                        value = form.source,
                        onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(source = it) } },
                        label = { Text(if (isBn) "বিজ্ঞপ্তির সূত্র (যেমন: বিডিজবস / লিঙ্কডইন)" else "Source (e.g. Bdjobs, LinkedIn)", fontSize = 10.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = form.customSkillsToHighlight,
                        onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(customSkillsToHighlight = it) } },
                        label = { Text(if (isBn) "যে বিশেষ দক্ষতাগুলো চিঠিতে জোর দেবেন" else "Key Skills to Highlight in Letter", fontSize = 10.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Button(
                        onClick = { viewModel.generateHumanCoverLetter() },
                        enabled = !isGenerating,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isBn) "চিঠি প্রস্তুত হচ্ছে..." else "Writing Letter...", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFBBF24), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isBn) "খাঁটি মানুষের ভাষায় চিঠি লিখুন" else "Generate Natural Cover Letter",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.5.sp
                            )
                        }
                    }
                }
            }
        }

        // Output Letter Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isBn) "তৈরিকৃত আবেদনপত্র (সম্পাদনাযোগ্য)" else "Generated Letter (Editable)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF0F172A)
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            // Copy button
                            Button(
                                onClick = {
                                    if (form.generatedLetter.isNotEmpty()) {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Cover Letter", form.generatedLetter))
                                        viewModel.showNotification(if (isBn) "চিঠি কপি করা হয়েছে!" else "Letter copied to clipboard!")
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color(0xFF1E293B), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isBn) "কপি" else "Copy", fontSize = 10.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold)
                            }

                            // Email Send button
                            Button(
                                onClick = {
                                    if (form.generatedLetter.isNotEmpty()) {
                                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                                            data = Uri.parse("mailto:")
                                            putExtra(Intent.EXTRA_SUBJECT, "Application for ${form.jobTitle} - ${cvData.personal.fullName}")
                                            putExtra(Intent.EXTRA_TEXT, form.generatedLetter)
                                        }
                                        try {
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            viewModel.showNotification("ইমেইল অ্যাপ পাওয়া যায়নি")
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isBn) "মেইল পাঠান" else "Send Email", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (form.generatedLetter.isNotEmpty()) {
                        OutlinedTextField(
                            value = form.generatedLetter,
                            onValueChange = { viewModel.updateCoverLetterForm { f -> f.copy(generatedLetter = it) } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp),
                            shape = RoundedCornerShape(12.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = Color(0xFF1E293B)
                            )
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF8FAFC))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFCBD5E1), modifier = Modifier.size(32.dp))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (isBn) "তথ্য পূরণ করে 'চিঠি লিখুন' বাটনে চাপুন" else "Fill details and click 'Generate Cover Letter'",
                                    fontSize = 11.5.sp,
                                    color = Color(0xFF64748B),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
