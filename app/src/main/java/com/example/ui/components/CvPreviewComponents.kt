package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.CvData
import com.example.model.CvTemplate
import com.example.model.PhotoShape

@Composable
fun CvPaperPreview(
    cvData: CvData,
    template: CvTemplate,
    activeLang: String,
    accentColor: Color,
    isTwoPage: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .testTag("cv_paper_preview")
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(4.dp))
    ) {
        when (template) {
            CvTemplate.MODERN -> ModernExecutiveLayout(cvData, activeLang, accentColor, isTwoPage)
            CvTemplate.CLASSIC -> BdBiodataLayout(cvData, activeLang, isTwoPage)
            CvTemplate.EXECUTIVE -> CorporateAtsLayout(cvData, activeLang, accentColor, isTwoPage)
        }
    }
}

// -------------------------------------------------------------
// 1. MODERN EXECUTIVE TEMPLATE
// -------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ModernExecutiveLayout(
    cvData: CvData,
    activeLang: String,
    accentColor: Color,
    isTwoPage: Boolean
) {
    val isBn = activeLang == "bn"
    val p = cvData.personal

    Row(modifier = Modifier.fillMaxWidth()) {
        // Left Column (Sidebar)
        Column(
            modifier = Modifier
                .weight(0.38f)
                .background(accentColor)
                .padding(14.dp)
        ) {
            // Profile photo
            if (cvData.showPhoto && !cvData.photoUri.isNullOrEmpty()) {
                val photoShapeModifier = when (cvData.photoShape) {
                    PhotoShape.ROUNDED_FULL -> Modifier.clip(CircleShape)
                    PhotoShape.ROUNDED_CORNER -> Modifier.clip(RoundedCornerShape(12.dp))
                    PhotoShape.PASSPORT_BOX -> Modifier.clip(RoundedCornerShape(2.dp))
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp)
                ) {
                    AsyncImage(
                        model = cvData.photoUri,
                        contentDescription = "Profile Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(if (cvData.photoShape == PhotoShape.PASSPORT_BOX) 88.dp else 84.dp)
                            .then(photoShapeModifier)
                            .border(2.dp, Color.White.copy(alpha = 0.35f), when (cvData.photoShape) {
                                PhotoShape.ROUNDED_FULL -> CircleShape
                                PhotoShape.ROUNDED_CORNER -> RoundedCornerShape(12.dp)
                                PhotoShape.PASSPORT_BOX -> RoundedCornerShape(2.dp)
                            })
                    )
                }
            }

            // Contact
            SidebarHeader(if (isBn) "যোগাযোগ" else "Contact")
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                ContactRow(Icons.Default.Phone, p.phone)
                ContactRow(Icons.Default.Email, p.email)
                ContactRow(Icons.Default.LocationOn, p.presentAddress)
                if (p.linkedin.isNotEmpty()) {
                    ContactRow(Icons.Default.Language, p.linkedin)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Core Skills
            SidebarHeader(if (isBn) "মূল দক্ষতা" else "Core Skills")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                cvData.skills.forEach { skill ->
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = skill,
                            fontSize = 9.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Language Proficiency & Points (ল্যাঙ্গুয়েজ পয়েন্ট)
            SidebarHeader(if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট" else "Language Points")
            cvData.languages.forEach { lang ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                        .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(6.dp))
                        .padding(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = lang.name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${lang.points}/10",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF6EE7B7)
                        )
                    }
                    // Visual 10-Point Rating Dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.padding(vertical = 3.dp)
                    ) {
                        repeat(10) { idx ->
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (idx < lang.points) Color(0xFF34D399) else Color.White.copy(alpha = 0.2f)
                                    )
                            )
                        }
                    }
                    Text(
                        text = "R:${lang.reading} • W:${lang.writing} • S:${lang.speaking}",
                        fontSize = 8.sp,
                        color = Color.White.copy(alpha = 0.75f),
                        lineHeight = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Personal Particulars
            SidebarHeader(if (isBn) "ব্যক্তিগত তথ্য" else "Personal Particulars")
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                SidebarDetailRow(if (isBn) "পিতার নাম" else "Father", p.fatherName)
                SidebarDetailRow(if (isBn) "মাতার নাম" else "Mother", p.motherName)
                SidebarDetailRow(if (isBn) "রক্তের গ্রুপ" else "Blood Group", p.bloodGroup)
                SidebarDetailRow(if (isBn) "জাতীয়তা" else "Nationality", p.nationality)
                SidebarDetailRow("NID", p.nid)
            }
        }

        // Right Column (Main Content)
        Column(
            modifier = Modifier
                .weight(0.62f)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Header: Name & Title
            val displayName = if (isBn) p.fullName else p.fullNameEn
            Text(
                text = displayName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                lineHeight = 22.sp
            )
            Text(
                text = p.title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )

            HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)

            // Executive Summary
            SectionHeading(if (isBn) "প্রফেশনাল বিবরণ (Executive Summary)" else "Executive Summary", accentColor)
            Text(
                text = if (isBn) cvData.summary else cvData.summaryEn,
                fontSize = 9.5.sp,
                color = Color(0xFF334155),
                lineHeight = 13.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Work Experience
            SectionHeading(if (isBn) "কর্মঅভিজ্ঞতা (Work Experience)" else "Work Experience", accentColor)
            cvData.experiences.forEach { exp ->
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = exp.role,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = exp.startDate,
                            fontSize = 9.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    Text(
                        text = "${exp.company} • ${exp.location}",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = accentColor
                    )
                    Text(
                        text = exp.description,
                        fontSize = 9.sp,
                        color = Color(0xFF334155),
                        lineHeight = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Educational Qualifications
            SectionHeading(if (isBn) "শিক্ষাগত যোগ্যতা (Educational Qualifications)" else "Education", accentColor)
            cvData.educations.forEach { edu ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = edu.degree,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "${edu.institution} (${edu.boardOrUni})",
                            fontSize = 8.5.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = edu.cgpa,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF047857)
                        )
                        Text(
                            text = edu.passingYear,
                            fontSize = 8.5.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            // Certifications
            if (cvData.certifications.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                SectionHeading(if (isBn) "সার্টিফিকেশন ও অ্যাওয়ার্ড" else "Certifications", accentColor)
                cvData.certifications.forEach { cert ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF059669),
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = cert,
                            fontSize = 8.5.sp,
                            color = Color(0xFF334155)
                        )
                    }
                }
            }

            // References
            if (cvData.references.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                SectionHeading(if (isBn) "রেফারেন্স (References)" else "References", accentColor)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    cvData.references.take(2).forEach { ref ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(4.dp))
                                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(4.dp))
                                .padding(5.dp)
                        ) {
                            Text(text = ref.name, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            Text(text = ref.designation, fontSize = 8.sp, color = Color(0xFF475569))
                            Text(text = ref.org, fontSize = 7.5.sp, color = Color(0xFF64748B))
                            Text(text = ref.contact, fontSize = 7.5.sp, color = Color(0xFF0284C7))
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. BD BIODATA (বাংলাদেশি ঐতিহ্যবাহী সরকারি ও প্রাতিষ্ঠানিক ছক)
// -------------------------------------------------------------
@Composable
fun BdBiodataLayout(
    cvData: CvData,
    activeLang: String,
    isTwoPage: Boolean
) {
    val isBn = activeLang == "bn"
    val p = cvData.personal

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(18.dp)
    ) {
        // Header with Passport Photo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isBn) "জীবনবৃত্তান্ত (CURRICULUM VITAE)" else "CURRICULUM VITAE (BIODATA)",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )
                Text(
                    text = if (isBn) p.fullName else p.fullNameEn,
                    fontSize = 17.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = p.title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155)
                )
                Text(
                    text = "${if (isBn) "মোবাইল:" else "Phone:"} ${p.phone} | Email: ${p.email}",
                    fontSize = 9.sp,
                    color = Color(0xFF475569),
                    modifier = Modifier.padding(top = 2.dp)
                )
                Text(
                    text = "${if (isBn) "বর্তমান ঠিকানা:" else "Present Address:"} ${p.presentAddress}",
                    fontSize = 9.sp,
                    color = Color(0xFF475569)
                )
            }

            // Passport photo on top right
            if (cvData.showPhoto && !cvData.photoUri.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .border(1.5.dp, Color.Black)
                        .padding(2.dp)
                ) {
                    AsyncImage(
                        model = cvData.photoUri,
                        contentDescription = "Passport Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(68.dp)
                            .height(84.dp)
                    )
                }
            }
        }

        HorizontalDivider(color = Color.Black, thickness = 1.5.dp)

        // Career Objective
        BiodataSectionBar(if (isBn) "ক্যারিয়ার উদ্দেশ্য (Career Objective)" else "Career Objective")
        Text(
            text = if (isBn) cvData.summary else cvData.summaryEn,
            fontSize = 9.5.sp,
            color = Color(0xFF1E293B),
            lineHeight = 13.5.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
        )

        // Work Experience
        BiodataSectionBar(if (isBn) "কর্মঅভিজ্ঞতা (Work Experience)" else "Work Experience")
        cvData.experiences.forEach { exp ->
            Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${exp.role} — ${exp.company}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(text = exp.startDate, fontSize = 9.sp, color = Color(0xFF475569))
                }
                Text(
                    text = exp.description,
                    fontSize = 8.5.sp,
                    color = Color(0xFF334155),
                    lineHeight = 12.sp,
                    modifier = Modifier.padding(top = 1.dp)
                )
            }
        }

        // Educational Qualifications Grid Table (ঐতিহ্যবাহী ছক)
        BiodataSectionBar(if (isBn) "শিক্ষাগত যোগ্যতা (Educational Qualifications)" else "Educational Qualifications")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        ) {
            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9))
                    .padding(vertical = 4.dp)
            ) {
                TableCell(if (isBn) "ডিগ্রী / পরীক্ষা" else "Degree / Exam", 0.28f, isHeader = true)
                TableCell(if (isBn) "শিক্ষা প্রতিষ্ঠান" else "Institution", 0.32f, isHeader = true)
                TableCell(if (isBn) "বোর্ড / ভার্সিটি" else "Board / Uni", 0.20f, isHeader = true)
                TableCell(if (isBn) "সন" else "Year", 0.10f, isHeader = true)
                TableCell(if (isBn) "জিপিএ" else "GPA", 0.10f, isHeader = true)
            }
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            cvData.educations.forEach { edu ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ) {
                    TableCell(edu.degree, 0.28f, isBold = true)
                    TableCell(edu.institution, 0.32f)
                    TableCell(edu.boardOrUni, 0.20f)
                    TableCell(edu.passingYear, 0.10f, alignCenter = true)
                    TableCell(edu.cgpa, 0.10f, isBold = true, alignCenter = true)
                }
                HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 0.5.dp)
            }
        }

        // Language Points Table
        BiodataSectionBar(if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট ছক" else "Language Proficiency & Points Table")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9))
                    .padding(vertical = 4.dp)
            ) {
                TableCell(if (isBn) "ভাষার নাম" else "Language", 0.30f, isHeader = true)
                TableCell(if (isBn) "দক্ষতার স্তর" else "Level", 0.25f, isHeader = true)
                TableCell(if (isBn) "পড়া / লেখা / বলা" else "R / W / S", 0.30f, isHeader = true)
                TableCell(if (isBn) "স্কোর" else "Score", 0.15f, isHeader = true)
            }
            HorizontalDivider(color = Color.Gray, thickness = 1.dp)
            cvData.languages.forEach { lang ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                ) {
                    TableCell(lang.name, 0.30f, isBold = true)
                    TableCell(lang.level, 0.25f)
                    TableCell("R:${lang.reading} • W:${lang.writing}", 0.30f)
                    TableCell("${lang.points}/10", 0.15f, isBold = true, alignCenter = true)
                }
                HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 0.5.dp)
            }
        }

        // Personal Particulars Table
        BiodataSectionBar(if (isBn) "ব্যক্তিগত বিবরণী (Personal Particulars)" else "Personal Particulars")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFCBD5E1))
        ) {
            BiodataTableRow(if (isBn) "পিতার নাম" else "Father's Name", p.fatherName)
            BiodataTableRow(if (isBn) "মাতার নাম" else "Mother's Name", p.motherName)
            BiodataTableRow(if (isBn) "স্থায়ী ঠিকানা" else "Permanent Address", p.permanentAddress)
            BiodataTableRow(if (isBn) "জন্ম তারিখ ও জাতীয়তা" else "Date of Birth & Nat.", "${p.dob} | ${p.nationality}")
            BiodataTableRow(if (isBn) "জাতীয় পরিচয়পত্র নম্বর (NID)" else "National ID (NID)", p.nid)
            BiodataTableRow(if (isBn) "ধর্ম ও রক্তের গ্রুপ" else "Religion & Blood Group", "${p.religion} | ${p.bloodGroup}")
            BiodataTableRow(if (isBn) "বৈবাহিক অবস্থা" else "Marital Status", p.maritalStatus)
        }

        // Formal Declaration & Signature
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = if (isBn) "ঘোষণা ও সত্যায়ন (Declaration):" else "Declaration & Authenticity:",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = if (isBn)
                "আমি এই মর্মে ঘোষণা করছি যে, উপরে বর্ণিত সকল তথ্য ও শিক্ষাগত যোগ্যতার বিবরণ আমার জ্ঞান ও বিশ্বাসমতে সম্পূর্ণ সত্য এবং নির্ভুল।"
            else
                "I hereby declare that all the information and statements furnished above are true, complete, and correct to the best of my knowledge and belief.",
            fontSize = 8.5.sp,
            color = Color(0xFF334155),
            lineHeight = 11.5.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(text = if (isBn) "তারিখ: ................................" else "Date: ................................", fontSize = 8.5.sp)
                Text(text = if (isBn) "স্থান: ঢাকা, বাংলাদেশ" else "Place: Dhaka, Bangladesh", fontSize = 8.5.sp, modifier = Modifier.padding(top = 2.dp))
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(1.dp)
                        .background(Color.Black)
                )
                Text(
                    text = "${if (isBn) "স্বাক্ষর" else "Signature"} (${if (isBn) p.fullName else p.fullNameEn})",
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

// -------------------------------------------------------------
// 3. CORPORATE ATS TEMPLATE
// -------------------------------------------------------------
@Composable
fun CorporateAtsLayout(
    cvData: CvData,
    activeLang: String,
    accentColor: Color,
    isTwoPage: Boolean
) {
    val isBn = activeLang == "bn"
    val p = cvData.personal

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // ATS Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isBn) p.fullName else p.fullNameEn,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black,
                    color = accentColor
                )
                Text(
                    text = p.title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF475569)
                )
                Text(
                    text = "${p.phone} • ${p.email} • ${p.presentAddress}",
                    fontSize = 8.5.sp,
                    color = Color(0xFF64748B),
                    modifier = Modifier.padding(top = 3.dp)
                )
                if (p.linkedin.isNotEmpty()) {
                    Text(
                        text = p.linkedin,
                        fontSize = 8.5.sp,
                        color = Color(0xFF0369A1)
                    )
                }
            }
            if (cvData.showPhoto && !cvData.photoUri.isNullOrEmpty()) {
                AsyncImage(
                    model = cvData.photoUri,
                    contentDescription = "Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(8.dp))
                )
            }
        }

        HorizontalDivider(color = accentColor, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))

        // Executive Summary
        AtsSectionHeader(if (isBn) "প্রফেশনাল বিবরণ (Executive Summary)" else "Executive Summary", accentColor)
        Text(
            text = if (isBn) cvData.summary else cvData.summaryEn,
            fontSize = 9.sp,
            color = Color(0xFF1E293B),
            lineHeight = 12.5.sp,
            textAlign = TextAlign.Justify
        )

        // Core Skills
        AtsSectionHeader(if (isBn) "মূল দক্ষতা (Core Competencies)" else "Core Competencies", accentColor)
        Text(
            text = cvData.skills.joinToString(" • "),
            fontSize = 9.sp,
            color = Color(0xFF334155),
            lineHeight = 12.sp
        )

        // Work Experience
        AtsSectionHeader(if (isBn) "কর্মঅভিজ্ঞতা (Work Experience)" else "Work Experience", accentColor)
        cvData.experiences.forEach { exp ->
            Column(modifier = Modifier.padding(bottom = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = exp.role, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Text(text = exp.startDate, fontSize = 8.5.sp, color = Color(0xFF64748B))
                }
                Text(text = "${exp.company} — ${exp.location}", fontSize = 9.sp, color = Color(0xFF475569))
                Text(
                    text = exp.description,
                    fontSize = 8.5.sp,
                    color = Color(0xFF334155),
                    lineHeight = 11.5.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Education
        AtsSectionHeader(if (isBn) "শিক্ষাগত যোগ্যতা (Education)" else "Education", accentColor)
        cvData.educations.forEach { edu ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${edu.degree} — ${edu.institution} (${edu.boardOrUni})",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "${edu.cgpa} (${edu.passingYear})",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF047857)
                )
            }
        }

        // Language Points
        AtsSectionHeader(if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট (Language Proficiency)" else "Languages", accentColor)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cvData.languages.forEach { l ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFF8FAFC), RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(4.dp))
                        .padding(5.dp)
                ) {
                    Text(text = "${l.name} (${l.points}/10)", fontSize = 8.5.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Text(text = l.level, fontSize = 7.5.sp, color = Color(0xFF059669))
                    Text(text = "R:${l.reading} • W:${l.writing}", fontSize = 7.sp, color = Color(0xFF64748B))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Small Helper Composables
// -------------------------------------------------------------
@Composable
private fun SidebarHeader(title: String) {
    Column(modifier = Modifier.padding(bottom = 6.dp)) {
        Text(
            text = title,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF6EE7B7),
            letterSpacing = 0.5.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.7.dp)
                .background(Color.White.copy(alpha = 0.25f))
        )
    }
}

@Composable
private fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    if (text.isEmpty()) return
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF34D399),
            modifier = Modifier
                .size(11.dp)
                .padding(top = 1.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 8.sp,
            color = Color.White.copy(alpha = 0.9f),
            lineHeight = 10.sp
        )
    }
}

@Composable
private fun SidebarDetailRow(label: String, value: String) {
    if (value.isEmpty()) return
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontSize = 7.5.sp, color = Color.White.copy(alpha = 0.65f))
        Text(text = value, fontSize = 7.5.sp, color = Color.White.copy(alpha = 0.95f), fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun SectionHeading(title: String, accentColor: Color) {
    Column(modifier = Modifier.padding(bottom = 6.dp)) {
        Text(
            text = title,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF0F172A),
            letterSpacing = 0.3.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(Color(0xFF0F172A))
        )
    }
}

@Composable
private fun BiodataSectionBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFFE2E8F0))
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = title,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun TableCell(
    text: String,
    weight: Float,
    isHeader: Boolean = false,
    isBold: Boolean = false,
    alignCenter: Boolean = false
) {
    Text(
        text = text,
        fontSize = if (isHeader) 8.5.sp else 8.sp,
        fontWeight = if (isHeader || isBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = if (alignCenter) TextAlign.Center else TextAlign.Start,
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 2.dp)
    )
}

@Composable
private fun BiodataTableRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color(0xFFE2E8F0))
    ) {
        Box(
            modifier = Modifier
                .weight(0.35f)
                .background(Color(0xFFF8FAFC))
                .padding(4.dp)
        ) {
            Text(text = label, fontSize = 8.5.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Box(
            modifier = Modifier
                .weight(0.65f)
                .padding(4.dp)
        ) {
            Text(text = value, fontSize = 8.5.sp, color = Color(0xFF1E293B))
        }
    }
}

@Composable
private fun AtsSectionHeader(title: String, accentColor: Color) {
    Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = accentColor,
            letterSpacing = 0.5.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFCBD5E1))
        )
    }
}
