package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.CoverLetterData
import com.example.model.CvData
import com.example.model.CvTemplate
import com.example.model.EducationItem
import com.example.model.ExperienceItem
import com.example.model.JobApplication
import com.example.model.LanguageItem
import com.example.model.PersonalDetails
import com.example.model.PhotoShape
import com.example.model.SampleData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CvViewModel : ViewModel() {

    // Tab state: 0 = CV Builder, 1 = Cover Letter & Application, 2 = Job Tracker
    private val _activeTab = MutableStateFlow(0)
    val activeTab: StateFlow<Int> = _activeTab.asStateFlow()

    // Active reading & interface language: "bn" or "en"
    private val _activeLang = MutableStateFlow("bn")
    val activeLang: StateFlow<String> = _activeLang.asStateFlow()

    // Template & styling
    private val _selectedTemplate = MutableStateFlow(CvTemplate.MODERN)
    val selectedTemplate: StateFlow<CvTemplate> = _selectedTemplate.asStateFlow()

    private val _isTwoPage = MutableStateFlow(false)
    val isTwoPage: StateFlow<Boolean> = _isTwoPage.asStateFlow()

    private val _accentColorHex = MutableStateFlow("#0F172A")
    val accentColorHex: StateFlow<String> = _accentColorHex.asStateFlow()

    // CV Data
    private val _cvData = MutableStateFlow(SampleData.sampleBengali)
    val cvData: StateFlow<CvData> = _cvData.asStateFlow()

    // Cover letter form & generation
    private val _coverLetterData = MutableStateFlow(CoverLetterData())
    val coverLetterData: StateFlow<CoverLetterData> = _coverLetterData.asStateFlow()

    private val _isGeneratingLetter = MutableStateFlow(false)
    val isGeneratingLetter: StateFlow<Boolean> = _isGeneratingLetter.asStateFlow()

    // Job tracker applications
    private val _applications = MutableStateFlow(SampleData.sampleApplications)
    val applications: StateFlow<List<JobApplication>> = _applications.asStateFlow()

    // Toast message trigger
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    fun selectTab(index: Int) {
        _activeTab.value = index
    }

    fun setLanguage(lang: String) {
        _activeLang.value = lang
        showNotification(if (lang == "bn") "ভাষা বাংলাতে পরিবর্তন করা হয়েছে" else "Switched to English Mode")
    }

    fun loadSampleData(lang: String) {
        _activeLang.value = lang
        if (lang == "bn") {
            val photo = _cvData.value.photoUri
            val shape = _cvData.value.photoShape
            val show = _cvData.value.showPhoto
            _cvData.value = SampleData.sampleBengali.copy(photoUri = photo, photoShape = shape, showPhoto = show)
            showNotification("সম্পূর্ণ বাংলা সিভি নমুনা লোড করা হয়েছে!")
        } else {
            val photo = _cvData.value.photoUri
            val shape = _cvData.value.photoShape
            val show = _cvData.value.showPhoto
            _cvData.value = SampleData.sampleEnglish.copy(photoUri = photo, photoShape = shape, showPhoto = show)
            showNotification("Full English CV Sample Loaded Successfully!")
        }
    }

    fun setTemplate(template: CvTemplate) {
        _selectedTemplate.value = template
    }

    fun setPageMode(isTwoPage: Boolean) {
        _isTwoPage.value = isTwoPage
    }

    fun setAccentColor(hex: String) {
        _accentColorHex.value = hex
    }

    fun updatePersonal(updater: (PersonalDetails) -> PersonalDetails) {
        _cvData.value = _cvData.value.copy(personal = updater(_cvData.value.personal))
    }

    fun updateSummary(newSummary: String) {
        if (_activeLang.value == "bn") {
            _cvData.value = _cvData.value.copy(summary = newSummary)
        } else {
            _cvData.value = _cvData.value.copy(summaryEn = newSummary)
        }
    }

    fun setPhotoUri(uriString: String?) {
        _cvData.value = _cvData.value.copy(photoUri = uriString, showPhoto = true)
        showNotification(if (_activeLang.value == "bn") "ছবি আপডেট করা হয়েছে!" else "Photo updated successfully!")
    }

    fun toggleShowPhoto(show: Boolean) {
        _cvData.value = _cvData.value.copy(showPhoto = show)
    }

    fun setPhotoShape(shape: PhotoShape) {
        _cvData.value = _cvData.value.copy(photoShape = shape)
    }

    // Experiences
    fun addExperience() {
        val newExp = ExperienceItem(
            company = if (_activeLang.value == "bn") "প্রতিষ্ঠানের নাম" else "Company Name",
            role = if (_activeLang.value == "bn") "পদবী (যেমন: সফটওয়্যার ইঞ্জিনিয়ার)" else "Role Title",
            location = if (_activeLang.value == "bn") "ঢাকা, বাংলাদেশ" else "Dhaka, Bangladesh",
            startDate = if (_activeLang.value == "bn") "২০২৩ - বর্তমান" else "2023 - Present",
            description = if (_activeLang.value == "bn") "• মূল দায়িত্ব ও প্রাতিষ্ঠানিক সাফল্যসমূহ এখানে লিখুন।" else "• Key responsibilities and organizational achievements."
        )
        _cvData.value = _cvData.value.copy(experiences = _cvData.value.experiences + newExp)
    }

    fun updateExperience(index: Int, updated: ExperienceItem) {
        val list = _cvData.value.experiences.toMutableList()
        if (index in list.indices) {
            list[index] = updated
            _cvData.value = _cvData.value.copy(experiences = list)
        }
    }

    fun removeExperience(index: Int) {
        val list = _cvData.value.experiences.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _cvData.value = _cvData.value.copy(experiences = list)
        }
    }

    // Educations
    fun addEducation() {
        val newEdu = EducationItem(
            degree = if (_activeLang.value == "bn") "ডিগ্রী বা পরীক্ষা" else "Degree / Exam",
            institution = if (_activeLang.value == "bn") "শিক্ষা প্রতিষ্ঠান" else "Institution Name",
            boardOrUni = if (_activeLang.value == "bn") "শিক্ষা বোর্ড / বিশ্ববিদ্যালয়" else "Board / University",
            passingYear = if (_activeLang.value == "bn") "২০২৪" else "2024",
            cgpa = if (_activeLang.value == "bn") "৩.৮৫" else "3.85"
        )
        _cvData.value = _cvData.value.copy(educations = _cvData.value.educations + newEdu)
    }

    fun updateEducation(index: Int, updated: EducationItem) {
        val list = _cvData.value.educations.toMutableList()
        if (index in list.indices) {
            list[index] = updated
            _cvData.value = _cvData.value.copy(educations = list)
        }
    }

    fun removeEducation(index: Int) {
        val list = _cvData.value.educations.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _cvData.value = _cvData.value.copy(educations = list)
        }
    }

    // Skills
    fun addSkill(skill: String) {
        val trimmed = skill.trim()
        if (trimmed.isNotEmpty() && !trimmed.contains("×") && !_cvData.value.skills.contains(trimmed)) {
            _cvData.value = _cvData.value.copy(skills = _cvData.value.skills + trimmed)
        }
    }

    fun removeSkill(skill: String) {
        _cvData.value = _cvData.value.copy(skills = _cvData.value.skills.filter { it != skill })
    }

    // Languages & Points
    fun addLanguage() {
        val newLang = LanguageItem(
            name = if (_activeLang.value == "bn") "নতুন ভাষা (যেমন: হিন্দি / আরবি)" else "Language (e.g. Arabic)",
            level = if (_activeLang.value == "bn") "কথোপকথন উপযোগী" else "Conversational",
            points = 8,
            reading = if (_activeLang.value == "bn") "ভালো (৮/১০)" else "Good (8/10)",
            writing = if (_activeLang.value == "bn") "মাধ্যমিক (৭/১০)" else "Fair (7/10)",
            speaking = if (_activeLang.value == "bn") "সাবলীল (৮.৫/১০)" else "Fluent (8.5/10)",
            listening = if (_activeLang.value == "bn") "চমৎকার (৯/১০)" else "Good (9/10)"
        )
        _cvData.value = _cvData.value.copy(languages = _cvData.value.languages + newLang)
    }

    fun updateLanguage(index: Int, updated: LanguageItem) {
        val list = _cvData.value.languages.toMutableList()
        if (index in list.indices) {
            list[index] = updated
            _cvData.value = _cvData.value.copy(languages = list)
        }
    }

    fun removeLanguage(index: Int) {
        val list = _cvData.value.languages.toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
            _cvData.value = _cvData.value.copy(languages = list)
        }
    }

    // Cover letter logic
    fun updateCoverLetterForm(updater: (CoverLetterData) -> CoverLetterData) {
        _coverLetterData.value = updater(_coverLetterData.value)
    }

    fun generateHumanCoverLetter() {
        viewModelScope.launch {
            _isGeneratingLetter.value = true
            delay(400) // smooth reactive feedback
            val f = _coverLetterData.value
            val cv = _cvData.value
            val p = cv.personal
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val todayStr = dateFormat.format(Date())

            val text = if (f.language == "bn") {
                if (f.tone == "dorkhast") {
                    """তারিখ: $todayStr

বরাবর,
${f.recipientTitle.ifEmpty { "প্রধান মানবসম্পদ কর্মকর্তা / ব্যবস্থাপনা পরিচালক" }}
${f.companyName}
${f.companyAddress}

বিষয়: '${f.jobTitle}' পদে নিয়োগের জন্য আবেদন পত্র।

মহোদয়/জনাব,
সবিনয় বিনীত নিবেদন এই যে, বিগত ${f.source.ifEmpty { "একটি নির্ভরযোগ্য মাধ্যমে" }} জানতে পারলাম আপনার স্বনামধন্য প্রতিষ্ঠানে '${f.jobTitle}' পদে কিছু সংখ্যক দক্ষ ও যোগ্য কর্মী নিয়োগ করা হবে। আমি উক্ত পদের জন্য একজন পূর্ণ আন্তরিক ও উদ্যমী প্রার্থী হিসেবে আমার জীবনবৃত্তান্ত আপনার সদয় অবগতি ও সুবিবেচনার জন্য উপস্থাপন করছি।

আমি সংশ্লিষ্ট বিষয়ে শিক্ষাগত যোগ্যতা অর্জনের পাশাপাশি বিগত ${f.userExperienceYears} ধরে পেশাদারিত্ব ও সততার সাথে কার্যসম্পাদন করে আসছি। বিশেষ করে ${f.customSkillsToHighlight}-এ আমার বাস্তব অভিজ্ঞতা আপনার প্রতিষ্ঠানের বর্তমান কর্মপরিকল্পনা ও লক্ষ্য অর্জনে অত্যন্ত কার্যকর অবদান রাখবে বলে আমি দৃঢ়ভাবে বিশ্বাস করি। আমি একটি দলকে সফলভাবে পরিচালনা করতে এবং প্রাতিষ্ঠানিক ডেডলাইন বজায় রাখতে সর্বদা সচেষ্ট।

অতএব, মহোদয়ের নিকট বিনীত প্রার্থনা, আমার শিক্ষাগত যোগ্যতা, কাজের অভিজ্ঞতা ও সততা বিবেচনা করে উল্লেখিত পদে আমাকে নিয়োগ লাভের সুযোগ দানে বাধিত করবেন।

বিনীত নিবেদক,
স্বাক্ষর: .......................................
${p.fullName}
পদবী: ${p.title}
মোবাইল: ${p.phone}
ইমেইল: ${p.email}
ঠিকানা: ${p.presentAddress}

সংযুক্তি:
১. পূর্ণাঙ্গ জীবনবৃত্তান্ত (CV)
২. পাসপোর্ট সাইজের ছবি
৩. সকল শিক্ষাগত সনদের কপি
৪. জাতীয় পরিচয়পত্রের (NID) কপি"""
                } else {
                    """তারিখ: $todayStr

শ্রদ্ধেয় ${f.recipientTitle},
${f.companyName}
${f.companyAddress}

বিষয়: '${f.jobTitle}' পদের জন্য আবেদন ও আগ্রহ প্রকাশ।

আসসালামু আলাইকুম / নমস্কার। 
${f.source} সূত্রে জানতে পারলাম ${f.companyName}-এ '${f.jobTitle}' পদে দক্ষ ও উদ্ভাবনী জনবল নিয়োগ দেওয়া হচ্ছে। আপনার প্রতিষ্ঠানের কাজের মান ও সুনাম আমাকে প্রতিনিয়ত অনুপ্রাণিত করে।

বিগত ${f.userExperienceYears} ধরে আমি মূলত ${f.customSkillsToHighlight} নিয়ে গভীর মনোযোগের সাথে কাজ করছি। জটিল সমস্যা সহজ সমাধান দিয়ে নিরসন করা এবং টিম সদস্যদের সাথে চমৎকার সমন্বয় বজায় রেখে নির্ধারিত সময়ের মধ্যে লক্ষ্য অর্জন করাই আমার কাজের মূল শক্তি।

আপনার প্রতিষ্ঠানের গতিশীল কর্মপরিবেশের সাথে আমার অভিজ্ঞতা চমৎকারভাবে সামঞ্জস্যপূর্ণ হবে বলে আমার বিশ্বাস। আমার পূর্ণাঙ্গ জীবনবৃত্তান্ত (CV) পর্যালোচনার জন্য এই পত্রের সাথে সংযুক্ত করলাম।

আপনার সুবিধাজনক যেকোনো সময়ে একটি সংক্ষিপ্ত সাক্ষাৎকারের সুযোগ পেলে অত্যন্ত বাধিত থাকব। আপনার আন্তরিক সহযোগিতার জন্য ধন্যবাদ।

শুভকামনাসহ,
${p.fullName}
${p.title}
ফোন: ${p.phone} | ইমেইল: ${p.email}"""
                }
            } else {
                """Date: $todayStr

To,
${f.recipientTitle.ifEmpty { "Hiring Manager" }}
${f.companyName}
${f.companyAddress}

Subject: Application for the position of ${f.jobTitle}

Dear Hiring Team,

I am writing to express my keen interest in the ${f.jobTitle} position currently available at ${f.companyName}, as advertised via ${f.source.ifEmpty { "official job portals" }}. Having followed the commendable growth and standards set by your organization, I am excited about the opportunity to bring my hands-on technical and operational expertise to your team.

With over ${f.userExperienceYears} of practical experience, specializing in ${f.customSkillsToHighlight}, I have consistently demonstrated a track record of delivering resilient, high-standard results that directly improved team productivity and operational outcomes. My focus has always been on pragmatic execution, continuous learning, and fostering proactive communication across cross-functional teams.

I am confident that my technical proficiency and commitment to excellence will make a measurable contribution toward achieving the strategic goals of ${f.companyName}.

I have attached my detailed Curriculum Vitae for your review. I would welcome the opportunity to discuss how my skill set and proactive attitude align with your team's immediate and long-term needs.

Thank you very much for your time and kind consideration.

Warm regards,

${p.fullNameEn.ifEmpty { p.fullName }}
${p.title}
Phone: ${p.phone} | Email: ${p.email}
LinkedIn: ${p.linkedin}"""
            }

            _coverLetterData.value = _coverLetterData.value.copy(generatedLetter = text)
            _isGeneratingLetter.value = false
            showNotification(if (f.language == "bn") "বাস্তবসম্মত মানুষের ভাষায় আবেদনপত্র প্রস্তুত হয়েছে!" else "Professional Cover Letter generated successfully!")
        }
    }

    // Job tracker
    fun addApplication(company: String, position: String, contactEmail: String, notes: String) {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val newApp = JobApplication(
            company = company,
            position = position,
            contactEmail = contactEmail.ifEmpty { "careers@company.com" },
            appliedDate = dateFormat.format(Date()),
            status = if (_activeLang.value == "bn") "আবেদন সম্পন্ন (Applied)" else "Applied",
            notes = notes.ifEmpty { "সিভি ও দরখাস্ত পাঠানো হয়েছে।" }
        )
        _applications.value = listOf(newApp) + _applications.value
        showNotification(if (_activeLang.value == "bn") "আবেদন সফলভাবে সংরক্ষণ করা হয়েছে!" else "Application saved to tracker!")
    }

    fun updateApplicationStatus(id: Long, newStatus: String) {
        _applications.value = _applications.value.map {
            if (it.id == id) it.copy(status = newStatus) else it
        }
    }

    fun removeApplication(id: Long) {
        _applications.value = _applications.value.filter { it.id != id }
        showNotification(if (_activeLang.value == "bn") "তালিকা থেকে মুছে ফেলা হয়েছে" else "Removed from tracker")
    }

    fun showNotification(msg: String) {
        _userMessage.value = msg
    }

    fun clearNotification() {
        _userMessage.value = null
    }
}
