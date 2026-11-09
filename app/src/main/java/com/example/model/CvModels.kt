package com.example.model

import java.util.UUID

enum class PhotoShape {
    ROUNDED_FULL, // Circle
    ROUNDED_CORNER, // Modern rounded square
    PASSPORT_BOX // Classic portrait box
}

enum class CvTemplate(val id: String, val titleBn: String, val titleEn: String, val subtitleBn: String, val subtitleEn: String) {
    MODERN("modern", "মডার্ন এক্সিকিউটিভ", "Modern Executive", "সাইডবার ও ল্যাঙ্গুয়েজ বার", "Sidebar & Language Bars"),
    CLASSIC("classic", "বিডি বায়োডাটা", "BD Biodata", "সরকারি ও ঐতিহ্যবাহী ছক", "Govt. & Formal Grid"),
    EXECUTIVE("executive", "কর্পোরেট এটিএস", "Corporate ATS", "আন্তর্জাতিক ১-পেজ ফরম্যাট", "International 1-Page")
}

data class PersonalDetails(
    val fullName: String = "",
    val fullNameEn: String = "",
    val title: String = "",
    val email: String = "",
    val phone: String = "",
    val altPhone: String = "",
    val presentAddress: String = "",
    val permanentAddress: String = "",
    val website: String = "",
    val linkedin: String = "",
    val github: String = "",
    val fatherName: String = "",
    val motherName: String = "",
    val dob: String = "",
    val bloodGroup: String = "",
    val religion: String = "",
    val maritalStatus: String = "",
    val nationality: String = "",
    val nid: String = ""
)

data class ExperienceItem(
    val id: String = UUID.randomUUID().toString(),
    val company: String = "",
    val role: String = "",
    val location: String = "",
    val startDate: String = "",
    val current: Boolean = false,
    val description: String = ""
)

data class EducationItem(
    val id: String = UUID.randomUUID().toString(),
    val degree: String = "",
    val institution: String = "",
    val boardOrUni: String = "",
    val passingYear: String = "",
    val cgpa: String = ""
)

data class LanguageItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val level: String = "",
    val points: Int = 9, // 1 to 10
    val reading: String = "",
    val writing: String = "",
    val speaking: String = "",
    val listening: String = "",
    val certScore: String = ""
)

data class ReferenceItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val designation: String = "",
    val org: String = "",
    val contact: String = ""
)

data class CvData(
    val photoUri: String? = null,
    val showPhoto: Boolean = true,
    val photoShape: PhotoShape = PhotoShape.ROUNDED_CORNER,
    val personal: PersonalDetails = PersonalDetails(),
    val summary: String = "",
    val summaryEn: String = "",
    val experiences: List<ExperienceItem> = emptyList(),
    val educations: List<EducationItem> = emptyList(),
    val skills: List<String> = emptyList(),
    val languages: List<LanguageItem> = emptyList(),
    val certifications: List<String> = emptyList(),
    val references: List<ReferenceItem> = emptyList()
)

data class CoverLetterData(
    val companyName: String = "গ্রামীণফোন লিমিটেড (Grameenphone Ltd.)",
    val companyAddress: String = "জিপি হাউস, বসুন্ধরা, বারিধারা, ঢাকা-১২২৯",
    val recipientTitle: String = "প্রধান মানবসম্পদ কর্মকর্তা (Head of HR)",
    val jobTitle: String = "সিনিয়র সফটওয়্যার ইঞ্জিনিয়ার",
    val source: String = "বিডিজবস.কম (Bdjobs.com) ও লিঙ্কডইন",
    val tone: String = "dorkhast", // 'dorkhast' or 'professional'
    val language: String = "bn", // 'bn' or 'en'
    val customSkillsToHighlight: String = "মাইক্রোসার্ভিসেস আর্কিটেকচার, ক্লাউড অপ্টিমাইজেশন এবং ১০ জনের ইঞ্জিনিয়ারিং টিম লিডারশিপ",
    val userExperienceYears: String = "৫+ বছর",
    val generatedLetter: String = ""
)

data class JobApplication(
    val id: Long = System.currentTimeMillis(),
    val company: String = "",
    val position: String = "",
    val contactEmail: String = "",
    val appliedDate: String = "",
    val status: String = "আবেদন সম্পন্ন (Applied)",
    val notes: String = ""
)

object SampleData {
    val sampleBengali = CvData(
        photoUri = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400&auto=format&fit=crop&q=80",
        showPhoto = true,
        photoShape = PhotoShape.ROUNDED_CORNER,
        personal = PersonalDetails(
            fullName = "মোঃ রাকিবুল হাসান",
            fullNameEn = "Md. Rakibul Hasan",
            title = "সিনিয়র সফটওয়্যার ইঞ্জিনিয়ার ও টিম লিড",
            email = "rakibul.hasan@example.com",
            phone = "+৮৮০ ১৭১২-৩৪৫৬৭৮",
            altPhone = "+880 1987-654321",
            presentAddress = "বাড়ি #৪৫, রোড #১২, ধানমন্ডি, ঢাকা-১২০৯",
            permanentAddress = "গ্রাম: শিবপুর, ডাকঘর: উলিপুর, জেলা: কুড়িগ্রাম",
            website = "https://rakibul.dev",
            linkedin = "linkedin.com/in/rakibul-hasan",
            github = "github.com/rakibul-dev",
            fatherName = "মোঃ রফিকুল ইসলাম",
            motherName = "মোসাম্মৎ সুফিয়া বেগম",
            dob = "১৫ এপ্রিল ১৯৯৭",
            bloodGroup = "B+ (পজিটিভ)",
            religion = "ইসলাম (Islam)",
            maritalStatus = "অবিবাহিত (Single)",
            nationality = "বাংলাদেশি (By Birth)",
            nid = "১৯৯৭৪৯১৮২৩৯০০০১২৩"
        ),
        summary = "ফলাফল-ভিত্তিক ও উদ্ভাবনী সিনিয়র সফটওয়্যার প্রকৌশলী, যার এন্টারপ্রাইজ ওয়েব অ্যাপ্লিকেশন, ক্লাউড আর্কিটেকচার এবং টিম ম্যানেজমেন্টে ৫+ বছরের সফল বাস্তব কাজের অভিজ্ঞতা রয়েছে। দেশীয় শীর্ষস্থানীয় ডিজিটাল পেমেন্ট ও ব্যাংকিং সলিউশনের কর্মক্ষমতা ৪০% বৃদ্ধি করার ট্র্যাক রেকর্ড বিদ্যমান। নিষ্ঠা, দ্রুত ডিসিশন মেকিং ও প্রাতিষ্ঠানিক লক্ষ্য অর্জনে সর্বদা প্রতিশ্রুতিবদ্ধ।",
        summaryEn = "Strategic & performance-driven Senior Software Engineer with 5+ years of hands-on expertise building enterprise-grade web applications, high-throughput microservices, and leading agile engineering squads across fintech & telecommunication domains in Bangladesh.",
        experiences = listOf(
            ExperienceItem(
                company = "ব্রেইনস্টেশন ২৩ পিএলসি (Brain Station 23 PLC)",
                role = "লিড ফুল-স্ট্যাক সফটওয়্যার ইঞ্জিনিয়ার",
                location = "গুলশান-১, ঢাকা",
                startDate = "জানুয়ারি ২০২২ - বর্তমান",
                current = true,
                description = "• দেশের শীর্ষ ডিজিটাল ব্যাংকিং প্ল্যাটফর্মের কোর ট্রানজ্যাকশন ইঞ্জিন আর্কিটেক্ট করে প্রতি সেকেন্ডে ৫,০০০+ ট্রানজ্যাকশন নিশ্চিত করেছি।\n• ১০ সদস্যের একটি ডেডিকেটেড ডেভেলপার দলকে টেকনিক্যাল মেন্টরিং, কোড রিভিউ এবং অ্যাজাইল স্প্রিন্ট পরিচালনার মাধ্যমে সময়মতো ডেলিভারি নিশ্চিত করেছি।\n• ক্লাউড অপ্টিমাইজেশন ও ক্যাশিং সলিউশন প্রয়োগ করে সার্ভার রেসপন্স টাইম ৩৫% এবং অবকাঠামো খরচ ২০% কমিয়েছি।"
            ),
            ExperienceItem(
                company = "ড্যাফোডিল সফটওয়্যার লিমিটেড",
                role = "সফটওয়্যার ইঞ্জিনিয়ার",
                location = "ধানমন্ডি, ঢাকা",
                startDate = "মার্চ ২০২০ - ডিসেম্বর ২০২১",
                current = false,
                description = "• বিশ্ববিদ্যালয় ও হাসপাতাল ম্যানেজমেন্ট সিস্টেমের রেসপন্সিভ ফ্রন্টএন্ড এবং সিকিউর ব্যাকএন্ড এপিআই বাস্তবায়ন।\n• রিঅ্যাক্ট এবং নোড.জেএস ফ্রেমওয়ার্ক ব্যবহার করে পেজ লোডিং গতি প্রায় ৪০% দ্রুততর করা হয়েছে।\n• ডেটাবেজ কুয়েরি ও ইনডেক্সিং অপ্টিমাইজ করে রিপোর্ট জেনারেশন সময় উল্লেখযোগ্যভাবে হ্রাস করেছি।"
            )
        ),
        educations = listOf(
            EducationItem(
                degree = "বি.এসসি ইন কম্পিউটার সায়েন্স অ্যান্ড ইঞ্জিনিয়ারিং (CSE)",
                institution = "বাংলাদেশ প্রকৌশল বিশ্ববিদ্যালয় (BUET)",
                boardOrUni = "বুয়েট (BUET)",
                passingYear = "২০১৯",
                cgpa = "৩.৮২ / ৪.০০"
            ),
            EducationItem(
                degree = "উচ্চ মাধ্যমিক সার্টিফিকেট (HSC) - বিজ্ঞান",
                institution = "নটর ডেম কলেজ, ঢাকা",
                boardOrUni = "ঢাকা শিক্ষা বোর্ড",
                passingYear = "২০১৫",
                cgpa = "৫.০০ (Golden A+)"
            ),
            EducationItem(
                degree = "মাধ্যমিক স্কুল সার্টিফিকেট (SSC) - বিজ্ঞান",
                institution = "কুড়িগ্রাম সরকারি উচ্চ বিদ্যালয়",
                boardOrUni = "দিনাজপুর শিক্ষা বোর্ড",
                passingYear = "২০১৩",
                cgpa = "৫.০০ (Golden A+)"
            )
        ),
        skills = listOf(
            "JavaScript / TypeScript", "React.js / Next.js", "Kotlin & Android",
            "Node.js & Express", "Python / FastAPI", "PostgreSQL & Redis",
            "Docker & Kubernetes", "AWS Cloud Services", "System Design & Leadership"
        ),
        languages = listOf(
            LanguageItem(
                name = "বাংলা (Bengali)",
                level = "মাতৃভাষা (Native)",
                points = 10,
                reading = "উচ্চতর (১০/১০)",
                writing = "উচ্চতর (১০/১০)",
                speaking = "সাবলীল (১০/১০)",
                listening = "সম্পূর্ণ (১০/১০)",
                certScore = "প্রাতিষ্ঠানিক ও শুদ্ধ বাংলা লেখনী"
            ),
            LanguageItem(
                name = "ইংরেজি (English)",
                level = "পেশাদার সাবলীল (Fluent)",
                points = 9,
                reading = "চমৎকার (৯.৫/১০)",
                writing = "পেশাদার (৯/১০)",
                speaking = "সাবলীল (৮.৫/১০)",
                listening = "চমৎকার (৯/১০)",
                certScore = "IELTS Band: 7.5"
            )
        ),
        certifications = listOf(
            "AWS Certified Solutions Architect – Associate (Amazon Web Services)",
            "Professional Scrum Master™ I (PSM I - Scrum.org)"
        ),
        references = listOf(
            ReferenceItem(
                name = "ড. মাহফুজুর রহমান",
                designation = "অধ্যাপক, সিএসই বিভাগ",
                org = "বাংলাদেশ প্রকৌশল বিশ্ববিদ্যালয় (BUET)",
                contact = "mahfuz@cse.buet.ac.bd | +৮৮০ ১৮১৯-XXXXXX"
            ),
            ReferenceItem(
                name = "তানভীর আহমেদ",
                designation = "হেড অব ইঞ্জিনিয়ারিং",
                org = "ব্রেইনস্টেশন ২৩ পিএলসি",
                contact = "tanvir.eng@brainstation-23.com | +৮৮০ ১৭১১-XXXXXX"
            )
        )
    )

    val sampleEnglish = CvData(
        photoUri = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400&auto=format&fit=crop&q=80",
        showPhoto = true,
        photoShape = PhotoShape.ROUNDED_CORNER,
        personal = PersonalDetails(
            fullName = "Md. Rakibul Hasan",
            fullNameEn = "Md. Rakibul Hasan",
            title = "Senior Software Engineer & Team Lead",
            email = "rakibul.hasan@example.com",
            phone = "+880 1712-345678",
            altPhone = "+880 1987-654321",
            presentAddress = "House #45, Road #12, Dhanmondi, Dhaka-1209",
            permanentAddress = "Vill: Shibpur, P.O: Ulipur, Dist: Kurigram",
            website = "https://rakibul.dev",
            linkedin = "linkedin.com/in/rakibul-hasan",
            github = "github.com/rakibul-dev",
            fatherName = "Md. Rafiqul Islam",
            motherName = "Mst. Sufia Begum",
            dob = "15 April 1997",
            bloodGroup = "B+ (Positive)",
            religion = "Islam",
            maritalStatus = "Single",
            nationality = "Bangladeshi (By Birth)",
            nid = "19974918239000123"
        ),
        summary = "Strategic & performance-driven Senior Software Engineer with 5+ years of hands-on expertise building enterprise-grade web applications, high-throughput microservices, and leading agile engineering squads across fintech & telecommunication domains in Bangladesh.",
        summaryEn = "Strategic & performance-driven Senior Software Engineer with 5+ years of hands-on expertise building enterprise-grade web applications, high-throughput microservices, and leading agile engineering squads across fintech & telecommunication domains in Bangladesh.",
        experiences = listOf(
            ExperienceItem(
                company = "Brain Station 23 PLC",
                role = "Lead Full-Stack Software Engineer",
                location = "Gulshan-1, Dhaka",
                startDate = "Jan 2022 - Present",
                current = true,
                description = "• Architected core high-concurrency transaction engine for a leading digital banking platform handling 5,000+ TPS.\n• Mentored a cross-functional squad of 10 engineers through code reviews, architectural governance, and Agile sprints.\n• Reduced AWS cloud latency by 35% and monthly infrastructure billing by 20% via Redis distributed caching."
            ),
            ExperienceItem(
                company = "Daffodil Software Ltd.",
                role = "Software Engineer",
                location = "Dhanmondi, Dhaka",
                startDate = "Mar 2020 - Dec 2021",
                current = false,
                description = "• Developed responsive React frontends and secure Node.js REST APIs for university & hospital ERP modules.\n• Improved web application initial load performance by 40% and optimized complex PostgreSQL reporting queries."
            )
        ),
        educations = listOf(
            EducationItem(
                degree = "B.Sc. in Computer Science & Engineering (CSE)",
                institution = "Bangladesh University of Engineering & Technology (BUET)",
                boardOrUni = "BUET, Dhaka",
                passingYear = "2019",
                cgpa = "3.82 / 4.00"
            ),
            EducationItem(
                degree = "Higher Secondary Certificate (HSC) - Science",
                institution = "Notre Dame College, Dhaka",
                boardOrUni = "Dhaka Education Board",
                passingYear = "2015",
                cgpa = "5.00 (Golden A+)"
            ),
            EducationItem(
                degree = "Secondary School Certificate (SSC) - Science",
                institution = "Kurigram Govt. High School",
                boardOrUni = "Dinajpur Education Board",
                passingYear = "2013",
                cgpa = "5.00 (Golden A+)"
            )
        ),
        skills = listOf(
            "JavaScript / TypeScript", "React.js / Next.js", "Kotlin & Android",
            "Node.js & Express", "Python / FastAPI", "PostgreSQL & Redis",
            "Docker & Kubernetes", "AWS Cloud Services", "System Design & Leadership"
        ),
        languages = listOf(
            LanguageItem(
                name = "Bengali (বাংলা)",
                level = "Native / Mother Tongue",
                points = 10,
                reading = "Native (10/10)",
                writing = "Native (10/10)",
                speaking = "Fluent (10/10)",
                listening = "Native (10/10)",
                certScore = "Official & Literary Proficiency"
            ),
            LanguageItem(
                name = "English (ইংরেজি)",
                level = "Full Professional Proficiency",
                points = 9,
                reading = "Fluent (9.5/10)",
                writing = "Advanced (9/10)",
                speaking = "Fluent (8.5/10)",
                listening = "Fluent (9/10)",
                certScore = "IELTS Academic: Band 7.5"
            )
        ),
        certifications = listOf(
            "AWS Certified Solutions Architect – Associate (Amazon Web Services)",
            "Professional Scrum Master™ I (PSM I - Scrum.org)"
        ),
        references = listOf(
            ReferenceItem(
                name = "Dr. Mahfuzur Rahman",
                designation = "Professor, Dept. of CSE",
                org = "Bangladesh University of Engineering & Technology (BUET)",
                contact = "mahfuz@cse.buet.ac.bd | +880 1819-XXXXXX"
            ),
            ReferenceItem(
                name = "Tanvir Ahmed",
                designation = "Head of Engineering",
                org = "Brain Station 23 PLC",
                contact = "tanvir.eng@brainstation-23.com | +880 1711-XXXXXX"
            )
        )
    )

    val sampleApplications = listOf(
        JobApplication(
            id = 1L,
            company = "বিকাশ লিমিটেড (bKash Limited)",
            position = "লিড সফটওয়্যার ইঞ্জিনিয়ার",
            contactEmail = "careers@bkash.com",
            appliedDate = "০৫ নভেম্বর ২০২৬",
            status = "ইন্টারভিউ কল (Interview Scheduled)",
            notes = "আগামী সপ্তাহে টেকনিক্যাল ইন্টারভিউ রাউন্ড।"
        ),
        JobApplication(
            id = 2L,
            company = "ব্র্যাক ব্যাংক পিএলসি (BRAC Bank PLC)",
            position = "সিনিয়র সিস্টেম আর্কিটেক্ট",
            contactEmail = "hr@bracbank.com",
            appliedDate = "০২ নভেম্বর ২০২৬",
            status = "আবেদন সম্পন্ন (Applied)",
            notes = "সিভি ও বাংলা দরখাস্ত মেইল করা হয়েছে।"
        )
    )
}
