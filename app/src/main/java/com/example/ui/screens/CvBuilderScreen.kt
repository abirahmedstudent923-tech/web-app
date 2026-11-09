package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.CvTemplate
import com.example.model.PhotoShape
import com.example.ui.CvViewModel
import com.example.ui.components.CvPaperPreview
import com.example.util.PdfPrinter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CvBuilderScreen(
    viewModel: CvViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cvData by viewModel.cvData.collectAsState()
    val activeLang by viewModel.activeLang.collectAsState()
    val template by viewModel.selectedTemplate.collectAsState()
    val isTwoPage by viewModel.isTwoPage.collectAsState()
    val accentHex by viewModel.accentColorHex.collectAsState()

    // Mode: 0 = Form Editor, 1 = Live Preview
    var screenMode by remember { mutableStateOf(0) }
    var newSkillText by remember { mutableStateOf("") }

    val isBn = activeLang == "bn"
    val accentColor = remember(accentHex) {
        try {
            Color(android.graphics.Color.parseColor(accentHex))
        } catch (e: Exception) {
            Color(0xFF0F172A)
        }
    }

    // Zero-permission Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri: Uri? ->
            uri?.let { viewModel.setPhotoUri(it.toString()) }
        }
    )

    LazyColumn(
        modifier = modifier
            .testTag("cv_builder_screen")
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Quick Demo Loader Bar
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = null,
                                tint = Color(0xFF34D399),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isBn) "বাংলা ও ইংরেজি সিভি মোড" else "Bilingual Preset Mode",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = if (isBn) "১-ক্লিকে ডেমো তথ্য লোড করে পড়ুন" else "1-Click load sample preset data",
                            color = Color(0xFF94A3B8),
                            fontSize = 10.sp
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Button(
                            onClick = { viewModel.loadSampleData("bn") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isBn) Color(0xFF059669) else Color.White.copy(alpha = 0.15f)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = ButtonDefaults.TextButtonContentPadding,
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "🇧🇩 বাংলা", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.loadSampleData("en") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!isBn) Color(0xFF059669) else Color.White.copy(alpha = 0.15f)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = ButtonDefaults.TextButtonContentPadding,
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(text = "🇬🇧 English", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Mode Switcher: Form vs Live A4 Preview
        item {
            TabRow(
                selectedTabIndex = screenMode,
                containerColor = Color.White,
                contentColor = Color(0xFF0F172A),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = screenMode == 0,
                    onClick = { screenMode = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isBn) "সিভি এডিটর (Editor)" else "CV Editor", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                )
                Tab(
                    selected = screenMode == 1,
                    onClick = { screenMode = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isBn) "লাইভ A4 প্রিভিউ (Preview)" else "Live A4 Preview", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                )
            }
        }

        // If Live Preview Mode is Selected
        if (screenMode == 1) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isBn) "লাইভ A4 প্রিভিউ (${template.titleBn})" else "Live A4 Preview (${template.titleEn})",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = Color(0xFF0F172A)
                    )
                    Button(
                        onClick = {
                            PdfPrinter.printCv(context, cvData, template, activeLang, accentHex)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Icon(Icons.Default.Print, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isBn) "PDF / প্রিন্ট" else "Print / PDF", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item {
                CvPaperPreview(
                    cvData = cvData,
                    template = template,
                    activeLang = activeLang,
                    accentColor = accentColor,
                    isTwoPage = isTwoPage
                )
            }
        } else {
            // -------------------------------------------------------------
            // SECTION 1: PHOTO & DISPLAY FRAME SETTINGS
            // -------------------------------------------------------------
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isBn) "প্রফেশনাল ছবি (Profile Photo)" else "Profile Photograph",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFF0F172A)
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = cvData.showPhoto,
                                    onCheckedChange = { viewModel.toggleShowPhoto(it) },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF059669))
                                )
                                Text(
                                    text = if (isBn) "ছবি দেখান" else "Show Photo",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        AnimatedVisibility(visible = cvData.showPhoto) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    // Thumbnail
                                    val shapeMod = when (cvData.photoShape) {
                                        PhotoShape.ROUNDED_FULL -> CircleShape
                                        PhotoShape.ROUNDED_CORNER -> RoundedCornerShape(10.dp)
                                        PhotoShape.PASSPORT_BOX -> RoundedCornerShape(2.dp)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(68.dp)
                                            .clip(shapeMod)
                                            .background(Color(0xFFF1F5F9))
                                            .border(1.5.dp, Color(0xFFCBD5E1), shapeMod)
                                    ) {
                                        if (!cvData.photoUri.isNullOrEmpty()) {
                                            AsyncImage(
                                                model = cvData.photoUri,
                                                contentDescription = "Avatar",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    }

                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Button(
                                                onClick = {
                                                    photoPickerLauncher.launch(
                                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                    )
                                                },
                                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                                                shape = RoundedCornerShape(8.dp),
                                                modifier = Modifier.height(34.dp)
                                            ) {
                                                Text(if (isBn) "ছবি নির্বাচন" else "Upload", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                            if (cvData.photoUri != null) {
                                                OutlinedButton(
                                                    onClick = { viewModel.setPhotoUri(null) },
                                                    shape = RoundedCornerShape(8.dp),
                                                    modifier = Modifier.height(34.dp)
                                                ) {
                                                    Text(if (isBn) "মুছুন" else "Remove", fontSize = 11.sp, color = Color.Red)
                                                }
                                            }
                                        }
                                        Text(
                                            text = if (isBn) "স্পষ্ট পোর্ট্রেট বা পাসপোর্ট সাইজের ছবি সিলেক্ট করুন" else "Select a crisp portrait or passport-sized photo",
                                            fontSize = 9.5.sp,
                                            color = Color(0xFF64748B),
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Frame styles
                                Text(
                                    text = if (isBn) "ছবির ফ্রেম স্টাইল:" else "Photo Frame Style:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF475569)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf(
                                        Triple(PhotoShape.ROUNDED_FULL, "গোলাকার", "Circle"),
                                        Triple(PhotoShape.ROUNDED_CORNER, "মডার্ন স্কয়ার", "Square"),
                                        Triple(PhotoShape.PASSPORT_BOX, "পাসপোর্ট বক্স", "Passport")
                                    ).forEach { (shape, labelBn, labelEn) ->
                                        val isSelected = cvData.photoShape == shape
                                        Surface(
                                            onClick = { viewModel.setPhotoShape(shape) },
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isSelected) Color(0xFF0F172A) else Color(0xFFF8FAFC),
                                            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(32.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = if (isBn) labelBn else labelEn,
                                                    fontSize = 10.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    color = if (isSelected) Color.White else Color(0xFF334155)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // -------------------------------------------------------------
            // SECTION 2: TEMPLATE DESIGN & COLOR PALETTE
            // -------------------------------------------------------------
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
                                text = if (isBn) "সিভি ডিজাইন ও ফরম্যাট" else "CV Layout & Design",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF0F172A)
                            )
                            // Accent Colors
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("#0F172A", "#047857", "#1E3A8A", "#701A75", "#334155").forEach { hex ->
                                    val isSelected = accentHex == hex
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(Color(android.graphics.Color.parseColor(hex)))
                                            .clickable { viewModel.setAccentColor(hex) }
                                            .border(
                                                width = if (isSelected) 2.dp else 0.dp,
                                                color = if (isSelected) Color(0xFF38BDF8) else Color.Transparent,
                                                shape = CircleShape
                                            )
                                    )
                                }
                            }
                        }

                        // 3 Templates
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CvTemplate.values().forEach { tpl ->
                                val isSelected = template == tpl
                                Surface(
                                    onClick = { viewModel.setTemplate(tpl) },
                                    shape = RoundedCornerShape(10.dp),
                                    color = if (isSelected) Color(0xFFECFDF5) else Color(0xFFF8FAFC),
                                    border = androidx.compose.foundation.BorderStroke(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) Color(0xFF059669) else Color(0xFFE2E8F0)
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = if (isBn) tpl.titleBn else tpl.titleEn,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = if (isSelected) Color(0xFF065F46) else Color(0xFF1E293B)
                                        )
                                        Text(
                                            text = if (isBn) tpl.subtitleBn else tpl.subtitleEn,
                                            fontSize = 9.sp,
                                            color = Color(0xFF64748B),
                                            lineHeight = 11.sp,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // -------------------------------------------------------------
            // SECTION 3: LANGUAGE PROFICIENCY & 10-POINT RATING
            // -------------------------------------------------------------
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট (Language Points)" else "Language Proficiency & Points",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = if (isBn) "১-১০ পয়েন্ট রেটিং ও পড়া, লেখা, বলার মান" else "1-10 Point proficiency rating & skills",
                                    fontSize = 10.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                            Button(
                                onClick = { viewModel.addLanguage() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isBn) "যোগ" else "Add", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        cvData.languages.forEachIndexed { index, lang ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color(0xFFF0FDF4), RoundedCornerShape(10.dp))
                                    .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(10.dp))
                                    .padding(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = lang.name,
                                        onValueChange = { viewModel.updateLanguage(index, lang.copy(name = it)) },
                                        label = { Text(if (isBn) "ভাষার নাম" else "Language", fontSize = 10.sp) },
                                        modifier = Modifier.weight(0.55f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                                    )
                                    OutlinedTextField(
                                        value = lang.level,
                                        onValueChange = { viewModel.updateLanguage(index, lang.copy(level = it)) },
                                        label = { Text(if (isBn) "স্তর (Native/Fluent)" else "Level", fontSize = 10.sp) },
                                        modifier = Modifier.weight(0.45f),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.White)
                                    )
                                    if (cvData.languages.size > 1) {
                                        IconButton(
                                            onClick = { viewModel.removeLanguage(index) },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }

                                // 1-10 Point Slider
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${if (isBn) "পয়েন্ট:" else "Score:"} ${lang.points}/10",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = Color(0xFF065F46),
                                        modifier = Modifier.width(80.dp)
                                    )
                                    Slider(
                                        value = lang.points.toFloat(),
                                        onValueChange = { viewModel.updateLanguage(index, lang.copy(points = it.toInt())) },
                                        valueRange = 1f..10f,
                                        steps = 8,
                                        colors = SliderDefaults.colors(
                                            thumbColor = Color(0xFF059669),
                                            activeTrackColor = Color(0xFF059669)
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                // R/W/S/L Fields
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    OutlinedTextField(
                                        value = lang.reading,
                                        onValueChange = { viewModel.updateLanguage(index, lang.copy(reading = it)) },
                                        label = { Text(if (isBn) "পড়া" else "Read", fontSize = 9.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    OutlinedTextField(
                                        value = lang.writing,
                                        onValueChange = { viewModel.updateLanguage(index, lang.copy(writing = it)) },
                                        label = { Text(if (isBn) "লেখা" else "Write", fontSize = 9.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    OutlinedTextField(
                                        value = lang.speaking,
                                        onValueChange = { viewModel.updateLanguage(index, lang.copy(speaking = it)) },
                                        label = { Text(if (isBn) "বলা" else "Speak", fontSize = 9.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // -------------------------------------------------------------
            // SECTION 4: PERSONAL & CONTACT INFORMATION
            // -------------------------------------------------------------
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = if (isBn) "ব্যক্তিগত তথ্যাবলী (Personal Details)" else "Personal Details",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF0F172A)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cvData.personal.fullName,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(fullName = it) } },
                                label = { Text("পূর্ণ নাম (বাংলা)", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = cvData.personal.fullNameEn,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(fullNameEn = it) } },
                                label = { Text("Full Name (English)", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cvData.personal.title,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(title = it) } },
                                label = { Text(if (isBn) "পদবী (যেমন: সফটওয়্যার ইঞ্জিনিয়ার)" else "Professional Title", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = cvData.personal.phone,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(phone = it) } },
                                label = { Text(if (isBn) "মোবাইল নম্বর" else "Phone Number", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cvData.personal.email,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(email = it) } },
                                label = { Text("Email", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = cvData.personal.linkedin,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(linkedin = it) } },
                                label = { Text("LinkedIn", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        OutlinedTextField(
                            value = cvData.personal.presentAddress,
                            onValueChange = { viewModel.updatePersonal { p -> p.copy(presentAddress = it) } },
                            label = { Text(if (isBn) "বর্তমান ঠিকানা" else "Present Address", fontSize = 10.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = cvData.personal.permanentAddress,
                            onValueChange = { viewModel.updatePersonal { p -> p.copy(permanentAddress = it) } },
                            label = { Text(if (isBn) "স্থায়ী ঠিকানা (বায়োডাটার জন্য)" else "Permanent Address", fontSize = 10.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cvData.personal.fatherName,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(fatherName = it) } },
                                label = { Text(if (isBn) "পিতার নাম" else "Father's Name", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = cvData.personal.motherName,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(motherName = it) } },
                                label = { Text(if (isBn) "মাতার নাম" else "Mother's Name", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cvData.personal.nid,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(nid = it) } },
                                label = { Text("NID No.", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            OutlinedTextField(
                                value = cvData.personal.bloodGroup,
                                onValueChange = { viewModel.updatePersonal { p -> p.copy(bloodGroup = it) } },
                                label = { Text(if (isBn) "রক্তের গ্রুপ" else "Blood Group", fontSize = 10.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        // Summary
                        OutlinedTextField(
                            value = if (isBn) cvData.summary else cvData.summaryEn,
                            onValueChange = { viewModel.updateSummary(it) },
                            label = { Text(if (isBn) "ক্যারিয়ার উদ্দেশ্য / প্রফেশনাল সামারি" else "Executive Summary / Career Objective", fontSize = 10.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            maxLines = 5,
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }
            }

            // -------------------------------------------------------------
            // SECTION 5: WORK EXPERIENCE
            // -------------------------------------------------------------
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
                                text = if (isBn) "চাকরির অভিজ্ঞতা (Work Experience)" else "Work Experience",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF0F172A)
                            )
                            Button(
                                onClick = { viewModel.addExperience() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isBn) "পদ যোগ" else "Add", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        cvData.experiences.forEachIndexed { index, exp ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = exp.company,
                                        onValueChange = { viewModel.updateExperience(index, exp.copy(company = it)) },
                                        label = { Text(if (isBn) "কোম্পানির নাম" else "Company", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = exp.role,
                                        onValueChange = { viewModel.updateExperience(index, exp.copy(role = it)) },
                                        label = { Text(if (isBn) "পদবী" else "Role", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = exp.startDate,
                                        onValueChange = { viewModel.updateExperience(index, exp.copy(startDate = it)) },
                                        label = { Text(if (isBn) "সময়কাল" else "Duration", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = exp.location,
                                        onValueChange = { viewModel.updateExperience(index, exp.copy(location = it)) },
                                        label = { Text(if (isBn) "লোকেশন" else "Location", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                                OutlinedTextField(
                                    value = exp.description,
                                    onValueChange = { viewModel.updateExperience(index, exp.copy(description = it)) },
                                    label = { Text(if (isBn) "দায়িত্ব ও অর্জন (বুলেট পয়েন্ট)" else "Responsibilities & Achievements", fontSize = 10.sp) },
                                    modifier = Modifier.fillMaxWidth(),
                                    minLines = 2,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                if (cvData.experiences.size > 1) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                        IconButton(
                                            onClick = { viewModel.removeExperience(index) },
                                            modifier = Modifier.size(28.dp)
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

            // -------------------------------------------------------------
            // SECTION 6: EDUCATIONAL QUALIFICATIONS
            // -------------------------------------------------------------
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
                                text = if (isBn) "শিক্ষাগত যোগ্যতা (Education)" else "Educational Qualifications",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF0F172A)
                            )
                            Button(
                                onClick = { viewModel.addEducation() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isBn) "ডিগ্রী যোগ" else "Add", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        cvData.educations.forEachIndexed { index, edu ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(10.dp))
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = edu.degree,
                                        onValueChange = { viewModel.updateEducation(index, edu.copy(degree = it)) },
                                        label = { Text(if (isBn) "ডিগ্রী / পরীক্ষা" else "Degree", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = edu.institution,
                                        onValueChange = { viewModel.updateEducation(index, edu.copy(institution = it)) },
                                        label = { Text(if (isBn) "শিক্ষা প্রতিষ্ঠান" else "Institution", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = edu.boardOrUni,
                                        onValueChange = { viewModel.updateEducation(index, edu.copy(boardOrUni = it)) },
                                        label = { Text(if (isBn) "বোর্ড / ভার্সিটি" else "Board / Uni", fontSize = 10.sp) },
                                        modifier = Modifier.weight(0.45f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = edu.passingYear,
                                        onValueChange = { viewModel.updateEducation(index, edu.copy(passingYear = it)) },
                                        label = { Text(if (isBn) "সন" else "Year", fontSize = 10.sp) },
                                        modifier = Modifier.weight(0.25f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = edu.cgpa,
                                        onValueChange = { viewModel.updateEducation(index, edu.copy(cgpa = it)) },
                                        label = { Text(if (isBn) "জিপিএ" else "GPA", fontSize = 10.sp) },
                                        modifier = Modifier.weight(0.30f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    if (cvData.educations.size > 1) {
                                        IconButton(
                                            onClick = { viewModel.removeEducation(index) },
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

            // -------------------------------------------------------------
            // SECTION 7: CORE SKILLS
            // -------------------------------------------------------------
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = if (isBn) "পেশাগত দক্ষতা ও স্কিলস (Skills)" else "Core & Technical Skills",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF0F172A)
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 8.dp, bottom = 10.dp)
                        ) {
                            cvData.skills.forEach { skill ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFF1F5F9),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(text = skill, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove",
                                            tint = Color.Gray,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .clickable { viewModel.removeSkill(skill) }
                                        )
                                    }
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newSkillText,
                                onValueChange = { newSkillText = it },
                                placeholder = { Text(if (isBn) "নতুন স্কিল লিখুন..." else "Add new skill...", fontSize = 11.sp) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            Button(
                                onClick = {
                                    if (newSkillText.isNotBlank()) {
                                        viewModel.addSkill(newSkillText)
                                        newSkillText = ""
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(if (isBn) "যোগ করুন" else "Add", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Print / Preview Action Footer
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { screenMode = 1 },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isBn) "লাইভ প্রিভিউ দেখুন" else "View Live Preview", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { PdfPrinter.printCv(context, cvData, template, activeLang, accentHex) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F172A)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Print, contentDescription = null, tint = Color(0xFF34D399), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isBn) "প্রিন্ট / PDF ডাউনলোড" else "Print / PDF", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
