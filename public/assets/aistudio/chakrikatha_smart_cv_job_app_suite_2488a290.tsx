import React, { useState, useRef } from 'react';
import { 
  FileText, Send, Briefcase, Award, CheckCircle2, Copy, Download, 
  Printer, Sparkles, Plus, Trash2, Eye, RefreshCw, Mail, Phone, 
  MapPin, Globe, Linkedin, Check, ChevronRight, LayoutTemplate,
  Calendar, Building2, User, BookOpen, Layers, Edit3, ArrowRight,
  Camera, Image as ImageIcon, EyeOff, ShieldCheck, ExternalLink,
  GraduationCap, CheckSquare, Sparkle, Upload, Languages, Star
} from 'lucide-react';

// Bilingual UI Dictionary for seamless Bengali & English reading
const uiTranslations = {
  bn: {
    appName: "চাকরিকথা",
    appSub: "বাংলাদেশের সম্পূর্ণ প্রফেশনাল সিভি ও ক্যারিয়ার অ্যাপ্লিকেশন স্যুট",
    tabBuilder: "সিভি মেকার",
    tabCoverLetter: "দরখাস্ত ও কভার লেটার",
    tabTracker: "আবেদন ট্র্যাকার",
    printBtn: "প্রিন্ট / PDF ডাউনলোড",
    loadSampleBn: "বাংলা ডেমো তথ্য",
    loadSampleEn: "English Demo Data",
    photoSection: "প্রফেশনাল ছবি (Profile Photo)",
    showPhotoLabel: "সিভিতে ছবি দেখান",
    uploadPhoto: "ছবি আপলোড করুন",
    changePhoto: "পরিবর্তন",
    photoHint: "পাসপোর্ট সাইজ বা স্পষ্ট পোর্ট্রেট ছবি দিন (JPG, PNG সর্বোচ্চ ৩MB)",
    frameStyle: "ছবির ফ্রেম স্টাইল:",
    frameRound: "গোলাকার",
    frameSquare: "মডার্ন স্কয়ার",
    framePassport: "পাসপোর্ট বক্স",
    templateSection: "সিভি ডিজাইন ও ফরম্যাট",
    tplModern: "মডার্ন এক্সিকিউটিভ",
    tplModernSub: "সাইডবার ও ল্যাঙ্গুয়েজ বার",
    tplClassic: "বিডি বায়োডাটা",
    tplClassicSub: "সরকারি ও ঐতিহ্যবাহী ছক",
    tplExecutive: "কর্পোরেট এটিএস",
    tplExecutiveSub: "আন্তর্জাতিক ১-পেজ ফরম্যাট",
    pageLength: "পেজের দৈর্ঘ্য:",
    page1: "১-পেজ",
    page2: "২-পেজ পূর্ণাঙ্গ",
    personalSection: "ব্যক্তিগত তথ্যাবলী",
    summaryLabel: "ক্যারিয়ার উদ্দেশ্য / প্রফেশনাল সামারি",
    expSection: "চাকরির অভিজ্ঞতা (Work Experience)",
    addExp: "পদ যোগ করুন",
    eduSection: "শিক্ষাগত যোগ্যতা (Education)",
    addEdu: "ডিগ্রী যোগ করুন",
    langPointSection: "ভাষাগত দক্ষতা ও ল্যাঙ্গুয়েজ পয়েন্ট (Language Points)",
    addLang: "নতুন ভাষা যোগ করুন",
    skillsSection: "পেশাগত দক্ষতা ও স্কিলস",
    addSkillBtn: "যোগ করুন",
    // CV Preview Section Headings
    cvContact: "যোগাযোগ",
    cvSkills: "মূল দক্ষতা",
    cvLangPoints: "ভাষাগত দক্ষতা ও পয়েন্ট",
    cvPersonalBio: "ব্যক্তিগত তথ্য",
    cvSummary: "প্রফেশনাল বিবরণ (Executive Summary)",
    cvCareerObj: "ক্যারিয়ার উদ্দেশ্য (Career Objective)",
    cvExperience: "কর্মঅভিজ্ঞতা (Work Experience)",
    cvEducation: "শিক্ষাগত যোগ্যতা (Educational Qualifications)",
    cvCerts: "প্রফেশনাল সার্টিফিকেশন",
    cvRefs: "রেফারেন্স (References)",
    cvBiodataTitle: "জীবনবৃত্তান্ত (CURRICULUM VITAE)",
    cvPersonalTable: "ব্যক্তিগত বিবরণী (Personal Information)",
    cvFather: "পিতার নাম",
    cvMother: "মাতার নাম",
    cvPermAddr: "স্থায়ী ঠিকানা",
    cvPresentAddr: "বর্তমান ঠিকানা",
    cvDobNat: "জন্ম তারিখ ও জাতীয়তা",
    cvNid: "জাতীয় পরিচয়পত্র নম্বর (NID)",
    cvRelBlood: "ধর্ম ও রক্তের গ্রুপ",
    cvMarital: "বৈবাহিক অবস্থা",
    cvBlood: "রক্তের গ্রুপ",
    cvNationality: "জাতীয়তা",
    cvExam: "পরীক্ষা / ডিগ্রী",
    cvInst: "শিক্ষা প্রতিষ্ঠান",
    cvBoard: "বোর্ড / বিশ্ববিদ্যালয়",
    cvYear: "পাসের সন",
    cvResult: "প্রাপ্ত জিপিএ / বিভাগ",
    cvLangName: "ভাষার নাম",
    cvLangLevel: "দক্ষতার স্তর",
    cvLangRWSL: "পড়া / লেখা / বলা / শোনা",
    cvLangScore: "পয়েন্ট স্কোর",
    cvDeclaration: "ঘোষণা ও সত্যায়ন (Declaration)",
    cvDeclText: "আমি এই মর্মে ঘোষণা করছি যে, উপরে বর্ণিত সকল তথ্য ও শিক্ষাগত যোগ্যতার বিবরণ আমার জ্ঞান ও বিশ্বাসমতে সম্পূর্ণ সত্য এবং নির্ভুল।",
    cvSign: "স্বাক্ষর",
    cvDate: "তারিখ: .......................................",
    cvPlace: "স্থান: ঢাকা, বাংলাদেশ"
  },
  en: {
    appName: "ChakriKatha",
    appSub: "Complete Professional CV & Career Application Suite for Bangladesh",
    tabBuilder: "CV Builder",
    tabCoverLetter: "Cover Letter & Application",
    tabTracker: "Job Tracker",
    printBtn: "Print / Save PDF",
    loadSampleBn: "বাংলা ডেমো তথ্য",
    loadSampleEn: "English Demo Data",
    photoSection: "Profile Photograph",
    showPhotoLabel: "Show Photo on CV",
    uploadPhoto: "Upload Photo",
    changePhoto: "Change",
    photoHint: "Select passport size or crisp portrait photo (JPG, PNG up to 3MB)",
    frameStyle: "Photo Frame Style:",
    frameRound: "Circle",
    frameSquare: "Modern Square",
    framePassport: "Passport Box",
    templateSection: "CV Layout & Format",
    tplModern: "Modern Executive",
    tplModernSub: "Sidebar & Language Bars",
    tplClassic: "BD Biodata",
    tplClassicSub: "Govt. & Formal Grid",
    tplExecutive: "Corporate ATS",
    tplExecutiveSub: "International 1-Page",
    pageLength: "Page Length:",
    page1: "1-Page",
    page2: "2-Page Full",
    personalSection: "Personal Details",
    summaryLabel: "Career Objective / Executive Summary",
    expSection: "Work Experience",
    addExp: "Add Role",
    eduSection: "Educational Qualifications",
    addEdu: "Add Degree",
    langPointSection: "Language Proficiency & Points (বাংলা / English)",
    addLang: "Add Language",
    skillsSection: "Technical & Core Skills",
    addSkillBtn: "Add Skill",
    // CV Preview Section Headings
    cvContact: "Contact Info",
    cvSkills: "Core Competencies",
    cvLangPoints: "Language Proficiency & Points",
    cvPersonalBio: "Personal Particulars",
    cvSummary: "Executive Summary",
    cvCareerObj: "Career Objective",
    cvExperience: "Professional Experience",
    cvEducation: "Educational Qualifications",
    cvCerts: "Certifications & Licenses",
    cvRefs: "References",
    cvBiodataTitle: "CURRICULUM VITAE (BIODATA)",
    cvPersonalTable: "Personal Particulars",
    cvFather: "Father's Name",
    cvMother: "Mother's Name",
    cvPermAddr: "Permanent Address",
    cvPresentAddr: "Present Address",
    cvDobNat: "Date of Birth & Nationality",
    cvNid: "National ID (NID) No.",
    cvRelBlood: "Religion & Blood Group",
    cvMarital: "Marital Status",
    cvBlood: "Blood Group",
    cvNationality: "Nationality",
    cvExam: "Degree / Exam",
    cvInst: "Institution",
    cvBoard: "Board / University",
    cvYear: "Passing Year",
    cvResult: "GPA / CGPA",
    cvLangName: "Language",
    cvLangLevel: "Proficiency Level",
    cvLangRWSL: "Read / Write / Speak / Listen",
    cvLangScore: "Point Score",
    cvDeclaration: "Declaration & Authenticity",
    cvDeclText: "I hereby declare that all the information and statements furnished above are true, complete, and correct to the best of my knowledge and belief.",
    cvSign: "Signature",
    cvDate: "Date: .......................................",
    cvPlace: "Place: Dhaka, Bangladesh"
  }
};

// Sample data tailored for Bangladesh professional job market (Bilingual)
const initialDataBn = {
  photo: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400&auto=format&fit=crop&q=80",
  showPhoto: true,
  photoShape: "rounded-xl",
  personal: {
    fullName: "মোঃ রাকিবুল হাসান",
    fullNameEn: "Md. Rakibul Hasan",
    title: "সিনিয়র সফটওয়্যার ইঞ্জিনিয়ার ও টিম লিড",
    email: "rakibul.hasan@example.com",
    phone: "+৮৮০ ১৭১২-৩৪৫৬৭৮",
    altPhone: "+880 1987-654321",
    presentAddress: "বাড়ি #৪৫, রোড #১২, ধানমন্ডি, ঢাকা-১২০৯",
    permanentAddress: "গ্রাম: শিবপুর, ডাকঘর: উলিপুর, জেলা: কুড়িগ্রাম",
    website: "https://rakibul.dev",
    linkedin: "linkedin.com/in/rakibul-hasan",
    github: "github.com/rakibul-dev",
    fatherName: "মোঃ রফিকুল ইসলাম",
    motherName: "মোসাম্মৎ সুফিয়া বেগম",
    dob: "১৫ এপ্রিল ১৯৯৭",
    bloodGroup: "B+ (পজিটিভ)",
    religion: "ইসলাম (Islam)",
    maritalStatus: "অবিবাহিত (Single)",
    nationality: "বাংলাদেশি (By Birth)",
    nid: "১৯৯৭৪৯১৮২৩৯০০০১২৩"
  },
  summary: "ফলাফল-ভিত্তিক ও উদ্ভাবনী সিনিয়র সফটওয়্যার প্রকৌশলী, যার এন্টারপ্রাইজ ওয়েব অ্যাপ্লিকেশন, ক্লাউড আর্কিটেকচার এবং টিম ম্যানেজমেন্টে ৫+ বছরের সফল বাস্তব কাজের অভিজ্ঞতা রয়েছে। দেশীয় শীর্ষস্থানীয় ডিজিটাল পেমেন্ট ও ব্যাংকিং সলিউশনের কর্মক্ষমতা ৪০% বৃদ্ধি করার ট্র্যাক রেকর্ড বিদ্যমান। নিষ্ঠা, দ্রুত ডিসিশন মেকিং ও প্রাতিষ্ঠানিক লক্ষ্য অর্জনে সর্বদা প্রতিশ্রুতিবদ্ধ।",
  summaryEn: "Strategic & performance-driven Senior Software Engineer with 5+ years of hands-on expertise building enterprise-grade web applications, high-throughput microservices, and leading agile engineering squads across fintech & telecommunication domains in Bangladesh.",
  experiences: [
    {
      id: "exp-1",
      company: "ব্রেইনস্টেশন ২৩ পিএলসি (Brain Station 23 PLC)",
      role: "লিড ফুল-স্ট্যাক সফটওয়্যার ইঞ্জিনিয়ার",
      location: "গুলশান-১, ঢাকা",
      startDate: "জানুয়ারি ২০২২ - বর্তমান",
      current: true,
      description: "• দেশের শীর্ষ ডিজিটাল ব্যাংকিং প্ল্যাটফর্মের কোর ট্রানজ্যাকশন ইঞ্জিন আর্কিটেক্ট করে প্রতি সেকেন্ডে ৫,০০০+ ট্রানজ্যাকশন নিশ্চিত করেছি।\n• ১০ সদস্যের একটি ডেডিকেটেড ডেভেলপার দলকে টেকনিক্যাল মেন্টরিং, কোড রিভিউ এবং অ্যাজাইল স্প্রিন্ট পরিচালনার মাধ্যমে সময়মতো ডেলিভারি নিশ্চিত করেছি।\n• ক্লাউড অপ্টিমাইজেশন ও ক্যাশিং সলিউশন প্রয়োগ করে সার্ভার রেসপন্স টাইম ৩৫% এবং অবকাঠামো খরচ ২০% কমিয়েছি।"
    },
    {
      id: "exp-2",
      company: "ড্যাফোডিল সফটওয়্যার লিমিটেড",
      role: "সফটওয়্যার ইঞ্জিনিয়ার",
      location: "ধানমন্ডি, ঢাকা",
      startDate: "মার্চ ২০২০ - ডিসেম্বর ২০২১",
      current: false,
      description: "• বিশ্ববিদ্যালয় ও হাসপাতাল ম্যানেজমেন্ট সিস্টেমের রেসপন্সিভ ফ্রন্টএন্ড এবং সিকিউর ব্যাকএন্ড এপিআই বাস্তবায়ন।\n• রিঅ্যাক্ট এবং নোড.জেএস ফ্রেমওয়ার্ক ব্যবহার করে পেজ লোডিং গতি প্রায় ৪০% দ্রুততর করা হয়েছে।\n• ডেটাবেজ কুয়েরি ও ইনডেক্সিং অপ্টিমাইজ করে রিপোর্ট জেনারেশন সময় উল্লেখযোগ্যভাবে হ্রাস করেছি।"
    }
  ],
  educations: [
    {
      id: "edu-1",
      degree: "বি.এসসি ইন কম্পিউটার সায়েন্স অ্যান্ড ইঞ্জিনিয়ারিং (CSE)",
      institution: "বাংলাদেশ প্রকৌশল বিশ্ববিদ্যালয় (BUET)",
      boardOrUni: "বুয়েট (BUET)",
      passingYear: "২০১৯",
      cgpa: "৩.৮২ / ৪.০০"
    },
    {
      id: "edu-2",
      degree: "উচ্চ মাধ্যমিক সার্টিফিকেট (HSC) - বিজ্ঞান",
      institution: "নটর ডেম কলেজ, ঢাকা",
      boardOrUni: "ঢাকা শিক্ষা বোর্ড",
      passingYear: "২০১৫",
      cgpa: "৫.০০ (Golden A+)"
    },
    {
      id: "edu-3",
      degree: "মাধ্যমিক স্কুল সার্টিফিকেট (SSC) - বিজ্ঞান",
      institution: "কুড়িগ্রাম সরকারি উচ্চ বিদ্যালয়",
      boardOrUni: "দিনাজপুর শিক্ষা বোর্ড",
      passingYear: "২০১৩",
      cgpa: "৫.০০ (Golden A+)"
    }
  ],
  skills: [
    "JavaScript (ES6+) / TypeScript", "React.js / Next.js", "Node.js & Express", 
    "Python / FastAPI", "PostgreSQL & Redis", "Docker & Kubernetes", 
    "AWS Cloud Services", "Microservices Architecture", "System Design & Leadership"
  ],
  languages: [
    { 
      id: "lang-1",
      name: "বাংলা (Bengali)", 
      level: "মাতৃভাষা (Native)", 
      points: 10, 
      reading: "উচ্চতর (১০/১০)", 
      writing: "উচ্চতর (১০/১০)", 
      speaking: "সাবলীল (১০/১০)", 
      listening: "সম্পূর্ণ (১০/১০)",
      certScore: "প্রাতিষ্ঠানিক ও শুদ্ধ বাংলা লেখনী"
    },
    { 
      id: "lang-2",
      name: "ইংরেজি (English)", 
      level: "পেশাদার সাবলীল (Fluent)", 
      points: 9, 
      reading: "চমৎকার (৯.৫/১০)", 
      writing: "পেশাদার (৯/১০)", 
      speaking: "সাবলীল (৮.৫/১০)", 
      listening: "চমৎকার (৯/১০)",
      certScore: "IELTS Band: 7.5"
    }
  ],
  certifications: [
    "AWS Certified Solutions Architect – Associate (Amazon Web Services)",
    "Professional Scrum Master™ I (PSM I - Scrum.org)"
  ],
  references: [
    {
      id: "ref-1",
      name: "ড. মাহফুজুর রহমান",
      designation: "অধ্যাপক, সিএসই বিভাগ",
      org: "বাংলাদেশ প্রকৌশল বিশ্ববিদ্যালয় (BUET)",
      contact: "mahfuz@cse.buet.ac.bd | +৮৮০ ১৮১৯-XXXXXX"
    },
    {
      id: "ref-2",
      name: "তানভীর আহমেদ",
      designation: "হেড অব ইঞ্জিনিয়ারিং",
      org: "ব্রেইনস্টেশন ২৩ পিএলসি",
      contact: "tanvir.eng@brainstation-23.com | +৮৮০ ১৭১১-XXXXXX"
    }
  ]
};

const initialDataEn = {
  photo: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=400&auto=format&fit=crop&q=80",
  showPhoto: true,
  photoShape: "rounded-xl",
  personal: {
    fullName: "Md. Rakibul Hasan",
    fullNameEn: "Md. Rakibul Hasan",
    title: "Senior Software Engineer & Team Lead",
    email: "rakibul.hasan@example.com",
    phone: "+880 1712-345678",
    altPhone: "+880 1987-654321",
    presentAddress: "House #45, Road #12, Dhanmondi, Dhaka-1209",
    permanentAddress: "Vill: Shibpur, P.O: Ulipur, Dist: Kurigram",
    website: "https://rakibul.dev",
    linkedin: "linkedin.com/in/rakibul-hasan",
    github: "github.com/rakibul-dev",
    fatherName: "Md. Rafiqul Islam",
    motherName: "Mst. Sufia Begum",
    dob: "15 April 1997",
    bloodGroup: "B+ (Positive)",
    religion: "Islam",
    maritalStatus: "Single",
    nationality: "Bangladeshi (By Birth)",
    nid: "19974918239000123"
  },
  summary: "Strategic & performance-driven Senior Software Engineer with 5+ years of hands-on expertise building enterprise-grade web applications, high-throughput microservices, and leading agile engineering squads across fintech & telecommunication domains in Bangladesh.",
  summaryEn: "Strategic & performance-driven Senior Software Engineer with 5+ years of hands-on expertise building enterprise-grade web applications, high-throughput microservices, and leading agile engineering squads across fintech & telecommunication domains in Bangladesh.",
  experiences: [
    {
      id: "exp-1",
      company: "Brain Station 23 PLC",
      role: "Lead Full-Stack Software Engineer",
      location: "Gulshan-1, Dhaka",
      startDate: "Jan 2022 - Present",
      current: true,
      description: "• Architected core high-concurrency transaction engine for a leading digital banking platform handling 5,000+ TPS.\n• Mentored a cross-functional squad of 10 engineers through code reviews, architectural governance, and Agile sprints.\n• Reduced AWS cloud latency by 35% and monthly infrastructure billing by 20% via Redis distributed caching."
    },
    {
      id: "exp-2",
      company: "Daffodil Software Ltd.",
      role: "Software Engineer",
      location: "Dhanmondi, Dhaka",
      startDate: "Mar 2020 - Dec 2021",
      current: false,
      description: "• Developed responsive React frontends and secure Node.js REST APIs for university & hospital ERP modules.\n• Improved web application initial load performance by 40% and optimized complex PostgreSQL reporting queries."
    }
  ],
  educations: [
    {
      id: "edu-1",
      degree: "B.Sc. in Computer Science & Engineering (CSE)",
      institution: "Bangladesh University of Engineering & Technology (BUET)",
      boardOrUni: "BUET, Dhaka",
      passingYear: "2019",
      cgpa: "3.82 / 4.00"
    },
    {
      id: "edu-2",
      degree: "Higher Secondary Certificate (HSC) - Science",
      institution: "Notre Dame College, Dhaka",
      boardOrUni: "Dhaka Education Board",
      passingYear: "2015",
      cgpa: "5.00 (Golden A+)"
    },
    {
      id: "edu-3",
      degree: "Secondary School Certificate (SSC) - Science",
      institution: "Kurigram Govt. High School",
      boardOrUni: "Dinajpur Education Board",
      passingYear: "2013",
      cgpa: "5.00 (Golden A+)"
    }
  ],
  skills: [
    "JavaScript (ES6+) / TypeScript", "React.js / Next.js", "Node.js & Express", 
    "Python / FastAPI", "PostgreSQL & Redis", "Docker & Kubernetes", 
    "AWS Cloud Services", "Microservices Architecture", "System Design & Leadership"
  ],
  languages: [
    { 
      id: "lang-1",
      name: "Bengali (বাংলা)", 
      level: "Native / Mother Tongue", 
      points: 10, 
      reading: "Native (10/10)", 
      writing: "Native (10/10)", 
      speaking: "Fluent (10/10)", 
      listening: "Native (10/10)",
      certScore: "Official & Literary Proficiency"
    },
    { 
      id: "lang-2",
      name: "English (ইংরেজি)", 
      level: "Full Professional Proficiency", 
      points: 9, 
      reading: "Fluent (9.5/10)", 
      writing: "Advanced (9/10)", 
      speaking: "Fluent (8.5/10)", 
      listening: "Fluent (9/10)",
      certScore: "IELTS Academic: Band 7.5"
    }
  ],
  certifications: [
    "AWS Certified Solutions Architect – Associate (Amazon Web Services)",
    "Professional Scrum Master™ I (PSM I - Scrum.org)"
  ],
  references: [
    {
      id: "ref-1",
      name: "Dr. Mahfuzur Rahman",
      designation: "Professor, Dept. of CSE",
      org: "Bangladesh University of Engineering & Technology (BUET)",
      contact: "mahfuz@cse.buet.ac.bd | +880 1819-XXXXXX"
    },
    {
      id: "ref-2",
      name: "Tanvir Ahmed",
      designation: "Head of Engineering",
      org: "Brain Station 23 PLC",
      contact: "tanvir.eng@brainstation-23.com | +880 1711-XXXXXX"
    }
  ]
};

export default function App() {
  const [activeTab, setActiveTab] = useState('builder'); // 'builder' | 'coverletter' | 'tracker'
  const [template, setTemplate] = useState('modern'); // 'modern' | 'classic' | 'executive'
  const [pageMode, setPageMode] = useState('1-page'); // '1-page' | '2-page'
  const [cvData, setCvData] = useState(initialDataBn);
  const [activeLang, setActiveLang] = useState('bn'); // 'bn' | 'en'
  const [accentColor, setAccentColor] = useState('#0f172a'); // Default rich slate/navy
  const [toastMessage, setToastMessage] = useState('');
  const fileInputRef = useRef(null);
  const previewRef = useRef(null);

  // Cover Letter & Application Generator State
  const [letterForm, setLetterForm] = useState({
    companyName: "গ্রামীণফোন লিমিটেড (Grameenphone Ltd.)",
    companyAddress: "জিপি হাউস, বসুন্ধরা, বারিধারা, ঢাকা-১২২৯",
    recipientTitle: "প্রধান মানবসম্পদ কর্মকর্তা (Head of HR)",
    jobTitle: "সিনিয়র সফটওয়্যার ইঞ্জিনিয়ার",
    source: "বিডিজবস.কম (Bdjobs.com) ও লিঙ্কডইন",
    tone: "dorkhast", // 'dorkhast' | 'professional'
    language: "bn", // 'bn' | 'en'
    customSkillsToHighlight: "মাইক্রোসার্ভিসেস আর্কিটেকচার, ক্লাউড অপ্টিমাইজেশন এবং ১০ জনের ইঞ্জিনিয়ারিং টিম লিডারশিপ",
    userExperienceYears: "৫+ বছর"
  });
  const [generatedLetter, setGeneratedLetter] = useState("");
  const [isGenerating, setIsGenerating] = useState(false);

  // Job Application Tracker State
  const [applications, setApplications] = useState([
    {
      id: 1,
      company: "বিকাশ লিমিটেড (bKash Limited)",
      position: "লিড সফটওয়্যার ইঞ্জিনিয়ার",
      contactEmail: "careers@bkash.com",
      appliedDate: "০৫ নভেম্বর ২০২৬",
      status: "ইন্টারভিউ কল (Interview Scheduled)",
      notes: "আগামী সপ্তাহে টেকনিক্যাল রাউন্ড।"
    },
    {
      id: 2,
      company: "ব্র্যাক ব্যাংক পিএলসি (BRAC Bank PLC)",
      position: "সিনিয়র সিস্টেম আর্কিটেক্ট",
      contactEmail: "hr@bracbank.com",
      appliedDate: "০২ নভেম্বর ২০২৬",
      status: "আবেদন সম্পন্ন (Applied)",
      notes: "সিভি ও বাংলা দরখাস্ত মেইল করা হয়েছে।"
    }
  ]);

  const [newApp, setNewApp] = useState({
    company: "",
    position: "",
    status: "আবেদন সম্পন্ন (Applied)",
    contactEmail: "",
    notes: ""
  });

  const t = uiTranslations[activeLang] || uiTranslations.bn;

  const showToast = (msg) => {
    setToastMessage(msg);
    setTimeout(() => {
      setToastMessage('');
    }, 3200);
  };

  const handlePhotoUpload = (e) => {
    const file = e.target.files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onloadend = () => {
      setCvData((prev) => ({ ...prev, photo: reader.result, showPhoto: true }));
      showToast(activeLang === 'bn' ? "ছবি সফলভাবে যুক্ত হয়েছে!" : "Photo uploaded successfully!");
    };
    reader.readAsDataURL(file);
  };

  const handlePrint = () => {
    window.print();
  };

  // Switch full language and optionally load matching bilingual preset
  const handleLanguageSwitch = (lang) => {
    setActiveLang(lang);
    showToast(lang === 'bn' ? "ভাষা বাংলাতে পরিবর্তন করা হয়েছে (বাংলা মোড)" : "Switched to English Reading & CV Mode");
  };

  const handleLoadLanguageSample = (lang) => {
    setActiveLang(lang);
    if (lang === 'bn') {
      setCvData({ ...initialDataBn, photo: cvData.photo, showPhoto: cvData.showPhoto, photoShape: cvData.photoShape });
      showToast("সম্পূর্ণ বাংলা সিভি নমুনা লোড করা হয়েছে!");
    } else {
      setCvData({ ...initialDataEn, photo: cvData.photo, showPhoto: cvData.showPhoto, photoShape: cvData.photoShape });
      showToast("Full English CV Sample Loaded Successfully!");
    }
  };

  // Natural Human-Like Letter Generator (100% human-sounding official Bengali & English)
  const generateHumanCoverLetter = () => {
    setIsGenerating(true);
    setTimeout(() => {
      let content = "";
      const todayBn = new Date().toLocaleDateString('bn-BD', { year: 'numeric', month: 'long', day: 'numeric' });
      const todayEn = new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });

      if (letterForm.language === 'bn') {
        if (letterForm.tone === 'dorkhast') {
          // Authentic Bangladeshi Formal Dorkhast (বরাবর / দরখাস্ত ফরম্যাট)
          content = `তারিখ: ${todayBn}

বরাবর,
${letterForm.recipientTitle || 'ব্যবস্থাপনা পরিচালক / মানবসম্পদ বিভাগ'}
${letterForm.companyName}
${letterForm.companyAddress}

বিষয়: '${letterForm.jobTitle}' পদে নিয়োগের জন্য আবেদন পত্র।

মহোদয়/জনাব,
সবিনয় বিনীত নিবেদন এই যে, বিগত ${letterForm.source || 'একটি নির্ভরযোগ্য সূত্রে'} জানতে পারলাম আপনার স্বনামধন্য ও শীর্ষস্থানীয় প্রতিষ্ঠানে '${letterForm.jobTitle}' পদে কিছু সংখ্যক যোগ্য ও অভিজ্ঞ কর্মকর্তা নিয়োগ করা হবে। আমি উক্ত পদের জন্য একজন পূর্ণ আন্তরিক ও নিষ্ঠাবান প্রার্থী হিসেবে আমার জীবনবৃত্তান্ত আপনার সদয় অবগতি ও সুবিবেচনার জন্য উপস্থাপন করছি।

আমি কম্পিউটার সায়েন্স বিষয়ে স্নাতক ডিগ্রি অর্জনের পাশাপাশি গত ${letterForm.userExperienceYears} ধরে সফটওয়্যার আর্কিটেকচার, এন্টারপ্রাইজ সিস্টেম ও টিম ম্যানেজমেন্টে পেশাদারিত্বের সাথে কাজ করে আসছি। বিশেষ করে ${letterForm.customSkillsToHighlight}-এ আমার বাস্তব অভিজ্ঞতা আপনার প্রতিষ্ঠানের বর্তমান কর্মপরিকল্পনা ও লক্ষ্য অর্জনে অত্যন্ত কার্যকর অবদান রাখবে বলে আমি দৃঢ়ভাবে বিশ্বাস করি। আমি প্রতিকূল পরিস্থিতিতে দায়িত্বশীলভাবে কার্যসম্পাদনে এবং একটি দলকে সফলভাবে নেতৃত্ব দিতে অভ্যস্ত।

অতএব, মহোদয়ের নিকট বিনীত প্রার্থনা, আমার শিক্ষাগত যোগ্যতা, কাজের অভিজ্ঞতা ও সততা বিবেচনা করে উল্লেখিত পদে আমাকে নিয়োগ লাভের সুযোগ দানে বাধিত করবেন।

বিনীত নিবেদক,
স্বাক্ষর: .......................................
${cvData.personal.fullName}
পদবী: ${cvData.personal.title}
মোবাইল: ${cvData.personal.phone}
ইমেইল: ${cvData.personal.email}
ঠিকানা: ${cvData.personal.presentAddress}

সংযুক্তি:
১. পূর্ণাঙ্গ জীবনবৃত্তান্ত (CV)
২. পাসপোর্ট সাইজের সত্যায়িত ছবি
৩. সকল শিক্ষাগত সনদের কপি
৪. জাতীয় পরিচয়পত্রের (NID) কপি`;
        } else {
          // Human-like Corporate Cover Letter (Bengali)
          content = `তারিখ: ${todayBn}

শ্রদ্ধেয় ${letterForm.recipientTitle},
${letterForm.companyName}
${letterForm.companyAddress}

বিষয়: '${letterForm.jobTitle}' পদের জন্য আবেদন ও আগ্রহ প্রকাশ।

আসসালামু আলাইকুম / নমস্কার। 
${letterForm.source} আপনার স্বনামধন্য প্রতিষ্ঠানে '${letterForm.jobTitle}' পদের নিয়োগ বিজ্ঞপ্তিটি আমার দৃষ্টি আকর্ষণ করেছে। ${letterForm.companyName}-এর কাজের মান ও প্রযুক্তিগত অগ্রগতি আমাকে প্রতিনিয়ত অনুপ্রাণিত করে। আমি এই প্রতিষ্ঠানের চলমান প্রবৃদ্ধির অংশ হতে আন্তরিকভাবে আগ্রহী।

বিগত ${letterForm.userExperienceYears} ধরে আমি মূলত ${letterForm.customSkillsToHighlight} নিয়ে কাজ করছি। আমার বর্তমান দায়িত্বে আমি কেবল নতুন সিস্টেম তৈরি বা কোড লেখাই নয়, বরং বিজনেস লজিককে সহজ ও টেকসই সফটওয়্যার সলিউশনে রূপান্তর করতে সক্ষম হয়েছি। একাধিক জটিল প্রজেক্ট নির্ধারিত সময়ের পূর্বে সফলভাবে ডেলিভারি করা এবং দলের সদস্যদের সাথে চমৎকার সমন্বয় বজায় রাখা আমার কাজের অন্যতম শক্তি।

${letterForm.companyName}-এর কাজের সংস্কৃতি ও মানের সাথে আমার অর্জিত অভিজ্ঞতা চমৎকারভাবে মানানসই হবে বলে আমার বিশ্বাস। আমার জীবনবৃত্তান্ত (CV) পর্যালোচনা করলে আমার পূর্ববর্তী কাজের পরিধি সম্পর্কে আপনি স্পষ্ট ধারণা পাবেন।

আপনার সুবিধাজনক যেকোনো সময়ে একটি সংক্ষিপ্ত সরাসরি বা অনলাইন সাক্ষাৎকারের সুযোগ পেলে আমি অত্যন্ত কৃতজ্ঞ থাকব। আপনার আন্তরিক সহযোগিতা ও মূল্যবান সময়ের জন্য ধন্যবাদ।

শুভকামনাসহ,
${cvData.personal.fullName}
${cvData.personal.title}
ফোন: ${cvData.personal.phone} | ইমেইল: ${cvData.personal.email}`;
        }
      } else {
        // High-Standard English Professional Cover Letter (Zero AI fluff, confident & concise)
        content = `Date: ${todayEn}

To,
${letterForm.recipientTitle || 'Hiring Manager'}
${letterForm.companyName}
${letterForm.companyAddress}

Subject: Application for the position of ${letterForm.jobTitle}

Dear Hiring Team,

I am writing to express my strong interest in the ${letterForm.jobTitle} position currently open at ${letterForm.companyName}, as advertised via ${letterForm.source || 'LinkedIn / Bdjobs'}. Having closely followed the innovative work produced by your team, I am eager to contribute my hands-on technical and leadership experience to your ongoing objectives.

With over ${letterForm.userExperienceYears} of proven expertise focusing on ${letterForm.customSkillsToHighlight}, I have consistently built resilient, high-volume production systems that directly improved performance metrics and operational efficiency. In my recent leadership roles, I have spearheaded core architecture modernizations, mentored engineering teams, and collaborated closely with cross-functional product stakeholders to ensure high-velocity delivery without sacrificing code quality.

What sets my profile apart is my commitment to practical problem-solving and institutional ownership. I understand that ${letterForm.companyName} values not just syntax, but scalable architectures, proactive team communication, and strategic execution.

I have attached my detailed Curriculum Vitae for your review. I would welcome the opportunity to discuss further how my technical background and problem-solving mindset can bring measurable value to your department.

Thank you very much for your time and kind consideration.

Warm regards,

${cvData.personal.fullNameEn || cvData.personal.fullName}
${cvData.personal.title}
Phone: ${cvData.personal.phone} | Email: ${cvData.personal.email}
LinkedIn: ${cvData.personal.linkedin}`;
      }

      setGeneratedLetter(content);
      setIsGenerating(false);
      showToast("শতভাগ বাস্তবসম্মত পেশাদার আবেদনপত্র প্রস্তুত হয়েছে!");
    }, 450);
  };

  // Direct Apply Button Launcher (Opens Email Client)
  const triggerJobDispatch = (email, subject, bodyText) => {
    const targetEmail = email || letterForm.companyAddress?.includes('@') ? letterForm.companyAddress : 'careers@company.com';
    const encodedSubject = encodeURIComponent(subject || `Application for ${letterForm.jobTitle} - ${cvData.personal.fullName}`);
    const encodedBody = encodeURIComponent(bodyText || generatedLetter || "Respected Hiring Team,\n\nPlease find my CV attached for the job application.");
    window.open(`mailto:${targetEmail}?subject=${encodedSubject}&body=${encodedBody}`, '_blank');
  };

  const handleAddExperience = () => {
    setCvData({
      ...cvData,
      experiences: [
        ...cvData.experiences,
        {
          id: `exp-${Date.now()}`,
          company: "প্রতিষ্ঠানের নাম",
          role: "পদবী (যেমন: সিনিয়র অফিসার)",
          location: "ঢাকা, বাংলাদেশ",
          startDate: "২০২৩ - বর্তমান",
          current: true,
          description: "• প্রধান দায়িত্ব ও প্রাতিষ্ঠানিক অর্জনসমূহ এখানে স্পষ্ট পয়েন্ট আকারে লিখুন।"
        }
      ]
    });
  };

  const handleAddEducation = () => {
    setCvData({
      ...cvData,
      educations: [
        ...cvData.educations,
        {
          id: `edu-${Date.now()}`,
          degree: activeLang === 'bn' ? "ডিগ্রী বা পরীক্ষার নাম" : "Degree / Examination Name",
          institution: activeLang === 'bn' ? "শিক্ষা প্রতিষ্ঠানের নাম" : "Institution Name",
          boardOrUni: activeLang === 'bn' ? "শিক্ষা বোর্ড / বিশ্ববিদ্যালয়" : "Board / University",
          passingYear: activeLang === 'bn' ? "২০২৪" : "2024",
          cgpa: activeLang === 'bn' ? "৩.৮০" : "3.80"
        }
      ]
    });
  };

  const handleAddLanguagePoint = () => {
    setCvData({
      ...cvData,
      languages: [
        ...cvData.languages,
        {
          id: `lang-${Date.now()}`,
          name: activeLang === 'bn' ? "হিন্দি / আরবি (Language)" : "Arabic / French",
          level: activeLang === 'bn' ? "কথোপকথন উপযোগী (Conversational)" : "Intermediate Proficiency",
          points: 8,
          reading: activeLang === 'bn' ? "ভালো (৮/১০)" : "Good (8/10)",
          writing: activeLang === 'bn' ? "মাধ্যমিক (৭/১০)" : "Fair (7/10)",
          speaking: activeLang === 'bn' ? "সাবলীল (৮.৫/১০)" : "Fluent (8.5/10)",
          listening: activeLang === 'bn' ? "চমৎকার (৯/১০)" : "Good (9/10)",
          certScore: ""
        }
      ]
    });
  };

  // Helper to render visual 10-point dots
  const renderPointDots = (points, isDarkBg = false) => {
    const total = 10;
    const filled = Math.min(10, Math.max(1, Number(points) || 8));
    return (
      <div className="flex items-center gap-1">
        {[...Array(total)].map((_, idx) => (
          <span
            key={idx}
            className={`w-2 h-2 rounded-full transition-all ${
              idx < filled
                ? isDarkBg
                  ? 'bg-emerald-400 shadow-sm shadow-emerald-400/50'
                  : 'bg-emerald-600'
                : isDarkBg
                ? 'bg-white/20'
                : 'bg-slate-200'
            }`}
          />
        ))}
        <span className={`ml-1.5 text-[10px] font-black ${isDarkBg ? 'text-emerald-300' : 'text-slate-700'}`}>
          {filled}/10
        </span>
      </div>
    );
  };

  return (
    <div className="min-h-screen bg-slate-100/80 text-slate-900 font-sans antialiased selection:bg-emerald-500 selection:text-white">
      {/* Toast Alert */}
      {toastMessage && (
        <div className="fixed bottom-6 right-6 z-50 bg-slate-950 text-white px-5 py-3.5 rounded-2xl shadow-2xl flex items-center gap-3 border border-slate-700 animate-slide-up">
          <Sparkle className="w-5 h-5 text-emerald-400 shrink-0" />
          <span className="text-sm font-medium">{toastMessage}</span>
        </div>
      )}

      {/* Top Professional Header */}
      <header className="sticky top-0 z-40 bg-white/95 backdrop-blur-md border-b border-slate-200/90 shadow-sm print:hidden">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between gap-2">
          
          {/* Logo & Branding */}
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl bg-slate-900 flex items-center justify-center text-white shadow-md shadow-slate-900/20 shrink-0">
              <Briefcase className="w-5 h-5 text-emerald-400" />
            </div>
            <div>
              <div className="flex items-center gap-2">
                <span className="text-lg sm:text-xl font-black tracking-tight text-slate-900">{t.appName}</span>
                <span className="text-[10px] bg-emerald-600 text-white font-bold px-2 py-0.5 rounded-full uppercase tracking-wider hidden md:inline-block">
                  Pro Suite
                </span>
              </div>
              <p className="text-[11px] text-slate-500 font-medium hidden xl:block">
                {t.appSub}
              </p>
            </div>
          </div>

          {/* Navigation Pill Tabs */}
          <nav className="flex items-center gap-1 bg-slate-100 p-1 rounded-xl border border-slate-200">
            <button
              onClick={() => setActiveTab('builder')}
              className={`px-2.5 sm:px-3.5 py-1.5 rounded-lg text-xs font-bold flex items-center gap-1.5 transition-all ${
                activeTab === 'builder'
                  ? 'bg-white text-slate-900 shadow-sm'
                  : 'text-slate-600 hover:text-slate-900'
              }`}
            >
              <FileText className="w-4 h-4 text-emerald-600 shrink-0" />
              <span>{t.tabBuilder}</span>
            </button>
            <button
              onClick={() => setActiveTab('coverletter')}
              className={`px-2.5 sm:px-3.5 py-1.5 rounded-lg text-xs font-bold flex items-center gap-1.5 transition-all ${
                activeTab === 'coverletter'
                  ? 'bg-white text-slate-900 shadow-sm'
                  : 'text-slate-600 hover:text-slate-900'
              }`}
            >
              <Sparkles className="w-4 h-4 text-amber-500 shrink-0" />
              <span>{t.tabCoverLetter}</span>
            </button>
            <button
              onClick={() => setActiveTab('tracker')}
              className={`px-2.5 sm:px-3.5 py-1.5 rounded-lg text-xs font-bold flex items-center gap-1.5 transition-all ${
                activeTab === 'tracker'
                  ? 'bg-white text-slate-900 shadow-sm'
                  : 'text-slate-600 hover:text-slate-900'
              }`}
            >
              <Send className="w-4 h-4 text-blue-600 shrink-0" />
              <span className="hidden sm:inline">{t.tabTracker}</span>
            </button>
          </nav>

          {/* Language Switcher & Print Action Bar */}
          <div className="flex items-center gap-2">
            {/* Prominent Bilingual Toggle (বাংলা / English) */}
            <div className="flex items-center bg-slate-100 p-1 rounded-xl border border-slate-200" title="বাংলা এবং ইংরেজি ভাষা পরিবর্তন করুন">
              <button
                onClick={() => handleLanguageSwitch('bn')}
                className={`px-2.5 py-1 rounded-lg text-xs font-black transition flex items-center gap-1 ${
                  activeLang === 'bn'
                    ? 'bg-emerald-600 text-white shadow-sm'
                    : 'text-slate-600 hover:text-slate-900'
                }`}
              >
                <span>🇧🇩</span>
                <span>বাংলা</span>
              </button>
              <button
                onClick={() => handleLanguageSwitch('en')}
                className={`px-2.5 py-1 rounded-lg text-xs font-black transition flex items-center gap-1 ${
                  activeLang === 'en'
                    ? 'bg-emerald-600 text-white shadow-sm'
                    : 'text-slate-600 hover:text-slate-900'
                }`}
              >
                <span>🇬🇧</span>
                <span>English</span>
              </button>
            </div>

            <button
              onClick={handlePrint}
              className="bg-slate-900 hover:bg-slate-800 text-white px-3.5 py-2 rounded-xl text-xs font-bold flex items-center gap-1.5 shadow-sm transition hover:scale-105 active:scale-95"
              title="A4 সাইজে প্রিন্ট করুন বা সরাসরি নিখুঁত PDF সেভ করুন"
            >
              <Printer className="w-4 h-4 text-emerald-400 shrink-0" />
              <span className="hidden md:inline">{t.printBtn}</span>
            </button>
          </div>
        </div>
      </header>

      {/* Main Body */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">

        {/* -------------------- TAB 1: CV BUILDER -------------------- */}
        {activeTab === 'builder' && (
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
            
            {/* Left Control Column (5 Cols) */}
            <div className="lg:col-span-5 space-y-5 print:hidden">

              {/* Quick Bilingual Sample Data Loader Bar */}
              <div className="bg-gradient-to-r from-slate-900 via-slate-800 to-slate-900 text-white p-4 rounded-2xl shadow-md flex flex-wrap items-center justify-between gap-2">
                <div className="flex items-center gap-2">
                  <Languages className="w-5 h-5 text-emerald-400 shrink-0" />
                  <div>
                    <div className="text-xs font-black">
                      {activeLang === 'bn' ? 'বাংলা ও ইংরেজি সিভি রিডিং মোড' : 'Bilingual CV Reading & Preset Mode'}
                    </div>
                    <div className="text-[10px] text-slate-300">
                      {activeLang === 'bn' ? 'এক ক্লিকে সম্পূর্ণ বাংলা বা ইংরেজি সিভি লোড করে পড়ুন' : 'Load full Bengali or English CV sample in 1 click'}
                    </div>
                  </div>
                </div>
                <div className="flex items-center gap-1.5">
                  <button
                    onClick={() => handleLoadLanguageSample('bn')}
                    className={`px-2.5 py-1.5 rounded-lg text-[11px] font-bold transition border ${
                      activeLang === 'bn'
                        ? 'bg-emerald-500 text-slate-950 border-emerald-400'
                        : 'bg-white/10 text-white border-white/15 hover:bg-white/20'
                    }`}
                  >
                    🇧🇩 বাংলা সিভি লোড
                  </button>
                  <button
                    onClick={() => handleLoadLanguageSample('en')}
                    className={`px-2.5 py-1.5 rounded-lg text-[11px] font-bold transition border ${
                      activeLang === 'en'
                        ? 'bg-emerald-500 text-slate-950 border-emerald-400'
                        : 'bg-white/10 text-white border-white/15 hover:bg-white/20'
                    }`}
                  >
                    🇬🇧 English CV Load
                  </button>
                </div>
              </div>

              {/* 1. Photo & Display Frame Settings Box */}
              <div className="bg-white p-5 rounded-2xl border border-slate-200/90 shadow-sm space-y-4">
                <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                  <div className="flex items-center gap-2">
                    <Camera className="w-4 h-4 text-emerald-600" />
                    <h3 className="text-sm font-bold text-slate-900">{t.photoSection}</h3>
                  </div>
                  <label className="flex items-center gap-1.5 cursor-pointer text-xs font-semibold text-slate-600 select-none">
                    <input
                      type="checkbox"
                      checked={cvData.showPhoto}
                      onChange={(e) => setCvData({ ...cvData, showPhoto: e.target.checked })}
                      className="rounded text-emerald-600 focus:ring-emerald-500 w-4 h-4"
                    />
                    <span>{t.showPhotoLabel}</span>
                  </label>
                </div>

                {cvData.showPhoto && (
                  <div className="space-y-3">
                    <div className="flex items-center gap-4">
                      {/* Photo Thumbnail */}
                      <div className="relative group w-20 h-20 rounded-xl overflow-hidden border-2 border-slate-200 shadow-inner bg-slate-50 flex items-center justify-center shrink-0">
                        {cvData.photo ? (
                          <img 
                            src={cvData.photo} 
                            alt="Profile" 
                            className="w-full h-full object-cover"
                          />
                        ) : (
                          <User className="w-8 h-8 text-slate-300" />
                        )}
                        <button
                          onClick={() => fileInputRef.current?.click()}
                          className="absolute inset-0 bg-black/50 text-white flex flex-col items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity text-[10px] font-bold"
                        >
                          <Upload className="w-4 h-4 mb-0.5" />
                          {t.changePhoto}
                        </button>
                      </div>

                      {/* Photo Upload & Delete Actions */}
                      <div className="space-y-2 flex-1">
                        <input
                          ref={fileInputRef}
                          type="file"
                          accept="image/*"
                          className="hidden"
                          onChange={handlePhotoUpload}
                        />
                        <div className="flex gap-2">
                          <button
                            onClick={() => fileInputRef.current?.click()}
                            className="px-3 py-1.5 bg-slate-900 hover:bg-slate-800 text-white text-xs font-bold rounded-lg flex items-center gap-1.5 transition"
                          >
                            <Upload className="w-3.5 h-3.5" />
                            <span>{t.uploadPhoto}</span>
                          </button>
                          {cvData.photo && (
                            <button
                              onClick={() => setCvData({ ...cvData, photo: null })}
                              className="px-2.5 py-1.5 border border-slate-200 hover:bg-red-50 text-red-600 text-xs font-medium rounded-lg transition"
                              title="ছবি সরান"
                            >
                              <Trash2 className="w-3.5 h-3.5" />
                            </button>
                          )}
                        </div>
                        <p className="text-[11px] text-slate-500 leading-tight">
                          {t.photoHint}
                        </p>
                      </div>
                    </div>

                    {/* Frame Shape Options */}
                    <div className="pt-2 border-t border-slate-100">
                      <label className="block text-[11px] font-bold text-slate-600 mb-1.5">{t.frameStyle}</label>
                      <div className="grid grid-cols-3 gap-2">
                        <button
                          onClick={() => setCvData({ ...cvData, photoShape: 'rounded-full' })}
                          className={`py-1.5 text-xs font-medium rounded-lg border flex items-center justify-center gap-1.5 transition ${
                            cvData.photoShape === 'rounded-full'
                              ? 'bg-slate-900 text-white border-slate-900'
                              : 'bg-slate-50 text-slate-700 hover:bg-slate-100'
                          }`}
                        >
                          <span className="w-3 h-3 rounded-full border border-current"></span>
                          <span>{t.frameRound}</span>
                        </button>
                        <button
                          onClick={() => setCvData({ ...cvData, photoShape: 'rounded-xl' })}
                          className={`py-1.5 text-xs font-medium rounded-lg border flex items-center justify-center gap-1.5 transition ${
                            cvData.photoShape === 'rounded-xl'
                              ? 'bg-slate-900 text-white border-slate-900'
                              : 'bg-slate-50 text-slate-700 hover:bg-slate-100'
                          }`}
                        >
                          <span className="w-3 h-3 rounded-md border border-current"></span>
                          <span>{t.frameSquare}</span>
                        </button>
                        <button
                          onClick={() => setCvData({ ...cvData, photoShape: 'rounded-none' })}
                          className={`py-1.5 text-xs font-medium rounded-lg border flex items-center justify-center gap-1.5 transition ${
                            cvData.photoShape === 'rounded-none'
                              ? 'bg-slate-900 text-white border-slate-900'
                              : 'bg-slate-50 text-slate-700 hover:bg-slate-100'
                          }`}
                        >
                          <span className="w-3 h-3.5 border border-current"></span>
                          <span>{t.framePassport}</span>
                        </button>
                      </div>
                    </div>
                  </div>
                )}
              </div>

              {/* 2. Format & Layout Selector */}
              <div className="bg-white p-5 rounded-2xl border border-slate-200/90 shadow-sm space-y-4">
                <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                  <h3 className="text-sm font-bold text-slate-900 flex items-center gap-2">
                    <LayoutTemplate className="w-4 h-4 text-emerald-600" />
                    {t.templateSection}
                  </h3>
                  
                  {/* Color Palette */}
                  <div className="flex items-center gap-1.5" title="থিম কালার নির্বাচন করুন">
                    {[
                      { color: '#0f172a', name: 'Navy Slate' },
                      { color: '#047857', name: 'Emerald' },
                      { color: '#1e3a8a', name: 'Royal Blue' },
                      { color: '#701a75', name: 'Plum' },
                      { color: '#334155', name: 'Graphite' }
                    ].map((item) => (
                      <button
                        key={item.color}
                        onClick={() => setAccentColor(item.color)}
                        style={{ backgroundColor: item.color }}
                        className={`w-5 h-5 rounded-full transition-transform ${
                          accentColor === item.color ? 'scale-125 ring-2 ring-offset-2 ring-slate-400' : 'hover:scale-110'
                        }`}
                      />
                    ))}
                  </div>
                </div>

                {/* 3 Major Formats */}
                <div className="grid grid-cols-3 gap-2">
                  <button
                    onClick={() => setTemplate('modern')}
                    className={`p-3 rounded-xl border text-left transition-all ${
                      template === 'modern'
                        ? 'border-emerald-600 bg-emerald-50/80 text-emerald-950 font-bold ring-2 ring-emerald-500/20 shadow-sm'
                        : 'border-slate-200 hover:border-slate-300 text-slate-700 bg-slate-50/50'
                    }`}
                  >
                    <div className="text-xs font-black">{t.tplModern}</div>
                    <div className="text-[10px] text-slate-500 mt-0.5">{t.tplModernSub}</div>
                  </button>

                  <button
                    onClick={() => setTemplate('classic')}
                    className={`p-3 rounded-xl border text-left transition-all ${
                      template === 'classic'
                        ? 'border-emerald-600 bg-emerald-50/80 text-emerald-950 font-bold ring-2 ring-emerald-500/20 shadow-sm'
                        : 'border-slate-200 hover:border-slate-300 text-slate-700 bg-slate-50/50'
                    }`}
                  >
                    <div className="text-xs font-black">{t.tplClassic}</div>
                    <div className="text-[10px] text-slate-500 mt-0.5">{t.tplClassicSub}</div>
                  </button>

                  <button
                    onClick={() => setTemplate('executive')}
                    className={`p-3 rounded-xl border text-left transition-all ${
                      template === 'executive'
                        ? 'border-emerald-600 bg-emerald-50/80 text-emerald-950 font-bold ring-2 ring-emerald-500/20 shadow-sm'
                        : 'border-slate-200 hover:border-slate-300 text-slate-700 bg-slate-50/50'
                    }`}
                  >
                    <div className="text-xs font-black">{t.tplExecutive}</div>
                    <div className="text-[10px] text-slate-500 mt-0.5">{t.tplExecutiveSub}</div>
                  </button>
                </div>

                {/* Page Length */}
                <div className="flex items-center justify-between pt-1 border-t border-slate-100">
                  <span className="text-xs font-bold text-slate-600">{t.pageLength}</span>
                  <div className="inline-flex rounded-lg bg-slate-100 p-0.5 border border-slate-200">
                    <button
                      onClick={() => setPageMode('1-page')}
                      className={`px-3 py-1 text-xs rounded-md font-bold transition ${
                        pageMode === '1-page' ? 'bg-white shadow text-slate-900' : 'text-slate-500 hover:text-slate-800'
                      }`}
                    >
                      {t.page1}
                    </button>
                    <button
                      onClick={() => setPageMode('2-page')}
                      className={`px-3 py-1 text-xs rounded-md font-bold transition ${
                        pageMode === '2-page' ? 'bg-white shadow text-slate-900' : 'text-slate-500 hover:text-slate-800'
                      }`}
                    >
                      {t.page2}
                    </button>
                  </div>
                </div>
              </div>

              {/* 3. Dedicated LANGUAGE POINTS & PROFICIENCY SECTION (ল্যাঙ্গুয়েজ পয়েন্ট) */}
              <div className="bg-white p-5 rounded-2xl border-2 border-emerald-500/30 shadow-sm space-y-4">
                <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                  <div>
                    <h4 className="font-black text-sm text-slate-900 flex items-center gap-2">
                      <Languages className="w-4 h-4 text-emerald-600" />
                      {t.langPointSection}
                    </h4>
                    <p className="text-[11px] text-slate-500 mt-0.5">
                      {activeLang === 'bn' 
                        ? 'বাংলা ও ইংরেজি ভাষার পঠন, লিখন, কথন ও ১০-পয়েন্ট রেটিং সেট করুন' 
                        : 'Configure Reading, Writing, Speaking & 10-Point proficiency ratings'}
                    </p>
                  </div>
                  <button
                    onClick={handleAddLanguagePoint}
                    className="text-xs font-bold text-white bg-emerald-600 hover:bg-emerald-700 px-3 py-1.5 rounded-lg flex items-center gap-1 transition shadow-sm shrink-0"
                  >
                    <Plus className="w-3.5 h-3.5" /> {t.addLang}
                  </button>
                </div>

                <div className="space-y-3">
                  {cvData.languages.map((lang, idx) => (
                    <div key={lang.id || idx} className="p-3.5 bg-emerald-50/40 rounded-xl border border-emerald-200/80 space-y-2.5 text-xs">
                      <div className="flex items-center justify-between gap-2">
                        <input
                          type="text"
                          placeholder="ভাষার নাম (যেমন: বাংলা / English)"
                          className="w-1/2 px-3 py-1.5 bg-white border border-slate-200 rounded-lg font-bold text-slate-900"
                          value={lang.name}
                          onChange={(e) => {
                            const updated = [...cvData.languages];
                            updated[idx].name = e.target.value;
                            setCvData({ ...cvData, languages: updated });
                          }}
                        />
                        <input
                          type="text"
                          placeholder="দক্ষতার স্তর (Native / Fluent)"
                          className="w-1/2 px-3 py-1.5 bg-white border border-slate-200 rounded-lg font-medium text-emerald-800"
                          value={lang.level}
                          onChange={(e) => {
                            const updated = [...cvData.languages];
                            updated[idx].level = e.target.value;
                            setCvData({ ...cvData, languages: updated });
                          }}
                        />
                      </div>

                      {/* Language Point Slider (1 to 10 Points) */}
                      <div className="bg-white px-3 py-2 rounded-lg border border-slate-200 flex items-center justify-between gap-3">
                        <span className="font-bold text-slate-700 shrink-0">
                          {activeLang === 'bn' ? 'ল্যাঙ্গুয়েজ পয়েন্ট (১-১০):' : 'Language Point (1-10):'}
                        </span>
                        <input
                          type="range"
                          min="1"
                          max="10"
                          step="1"
                          value={lang.points || 9}
                          onChange={(e) => {
                            const updated = [...cvData.languages];
                            updated[idx].points = Number(e.target.value);
                            setCvData({ ...cvData, languages: updated });
                          }}
                          className="w-full accent-emerald-600 cursor-pointer"
                        />
                        <span className="px-2 py-0.5 bg-emerald-600 text-white font-black rounded text-xs shrink-0">
                          {lang.points || 9}/10
                        </span>
                      </div>

                      {/* 4 Sub-skills: Reading, Writing, Speaking, Listening */}
                      <div className="grid grid-cols-2 sm:grid-cols-4 gap-2">
                        <div>
                          <label className="block text-[10px] font-bold text-slate-500 mb-0.5">
                            {activeLang === 'bn' ? 'পড়া (Reading)' : 'Reading'}
                          </label>
                          <input
                            type="text"
                            className="w-full px-2 py-1 bg-white border border-slate-200 rounded text-[11px]"
                            value={lang.reading || ''}
                            onChange={(e) => {
                              const updated = [...cvData.languages];
                              updated[idx].reading = e.target.value;
                              setCvData({ ...cvData, languages: updated });
                            }}
                          />
                        </div>
                        <div>
                          <label className="block text-[10px] font-bold text-slate-500 mb-0.5">
                            {activeLang === 'bn' ? 'লেখা (Writing)' : 'Writing'}
                          </label>
                          <input
                            type="text"
                            className="w-full px-2 py-1 bg-white border border-slate-200 rounded text-[11px]"
                            value={lang.writing || ''}
                            onChange={(e) => {
                              const updated = [...cvData.languages];
                              updated[idx].writing = e.target.value;
                              setCvData({ ...cvData, languages: updated });
                            }}
                          />
                        </div>
                        <div>
                          <label className="block text-[10px] font-bold text-slate-500 mb-0.5">
                            {activeLang === 'bn' ? 'বলা (Speaking)' : 'Speaking'}
                          </label>
                          <input
                            type="text"
                            className="w-full px-2 py-1 bg-white border border-slate-200 rounded text-[11px]"
                            value={lang.speaking || ''}
                            onChange={(e) => {
                              const updated = [...cvData.languages];
                              updated[idx].speaking = e.target.value;
                              setCvData({ ...cvData, languages: updated });
                            }}
                          />
                        </div>
                        <div>
                          <label className="block text-[10px] font-bold text-slate-500 mb-0.5">
                            {activeLang === 'bn' ? 'শোনা (Listening)' : 'Listening'}
                          </label>
                          <input
                            type="text"
                            className="w-full px-2 py-1 bg-white border border-slate-200 rounded text-[11px]"
                            value={lang.listening || ''}
                            onChange={(e) => {
                              const updated = [...cvData.languages];
                              updated[idx].listening = e.target.value;
                              setCvData({ ...cvData, languages: updated });
                            }}
                          />
                        </div>
                      </div>

                      <div className="flex items-center justify-between gap-2 pt-1">
                        <input
                          type="text"
                          placeholder={activeLang === 'bn' ? "সার্টিফিকেট / নোট (যেমন: IELTS 7.5 বা শুদ্ধ প্রমিত বাংলা)" : "Note / Score (e.g. IELTS Band 7.5)"}
                          className="flex-1 px-2.5 py-1 bg-white border border-slate-200 rounded text-[11px]"
                          value={lang.certScore || ''}
                          onChange={(e) => {
                            const updated = [...cvData.languages];
                            updated[idx].certScore = e.target.value;
                            setCvData({ ...cvData, languages: updated });
                          }}
                        />
                        {cvData.languages.length > 1 && (
                          <button
                            onClick={() => {
                              setCvData({
                                ...cvData,
                                languages: cvData.languages.filter((_, i) => i !== idx)
                              });
                            }}
                            className="text-red-500 hover:text-red-700 text-[11px] font-bold flex items-center gap-1 px-2 py-1"
                          >
                            <Trash2 className="w-3.5 h-3.5" />
                          </button>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              {/* 4. Personal & Contact Information */}
              <div className="bg-white p-5 rounded-2xl border border-slate-200/90 shadow-sm space-y-4">
                <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                  <h4 className="font-bold text-sm text-slate-900 flex items-center gap-2">
                    <User className="w-4 h-4 text-emerald-600" /> {t.personalSection}
                  </h4>
                  <button 
                    onClick={() => handleLanguageSwitch(activeLang === 'bn' ? 'en' : 'bn')}
                    className="text-xs font-bold text-emerald-700 bg-emerald-50 px-2.5 py-1 rounded-md border border-emerald-200/80 hover:bg-emerald-100 transition"
                  >
                    {activeLang === 'bn' ? '🇧🇩 বাংলা সক্রিয়' : '🇬🇧 English Active'}
                  </button>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">পূর্ণ নাম (বাংলা)</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.fullName}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, fullName: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">Full Name (English)</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.fullNameEn}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, fullNameEn: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">পেশাগত পদবী (Title)</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.title}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, title: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">মোবাইল নম্বর</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.phone}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, phone: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">ইমেইল ঠিকানা</label>
                    <input
                      type="email"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.email}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, email: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">বর্তমান ঠিকানা</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.presentAddress}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, presentAddress: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">স্থায়ী ঠিকানা (বায়োডাটার জন্য)</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                      value={cvData.personal.permanentAddress}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, permanentAddress: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">পিতার নাম</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200"
                      value={cvData.personal.fatherName}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, fatherName: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">মাতার নাম</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200"
                      value={cvData.personal.motherName}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, motherName: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">জাতীয় পরিচয়পত্র (NID) নম্বর</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200"
                      value={cvData.personal.nid}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, nid: e.target.value } })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">রক্তের গ্রুপ ও জন্ম তারিখ</label>
                    <div className="flex gap-2">
                      <input
                        type="text"
                        placeholder="রক্তের গ্রুপ"
                        className="w-1/2 px-2.5 py-2 border rounded-xl border-slate-200"
                        value={cvData.personal.bloodGroup}
                        onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, bloodGroup: e.target.value } })}
                      />
                      <input
                        type="text"
                        placeholder="জন্ম তারিখ"
                        className="w-1/2 px-2.5 py-2 border rounded-xl border-slate-200"
                        value={cvData.personal.dob}
                        onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, dob: e.target.value } })}
                      />
                    </div>
                  </div>
                  <div>
                    <label className="block text-slate-700 mb-1 font-semibold">লিঙ্কডইন প্রোফাইল</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl border-slate-200"
                      value={cvData.personal.linkedin}
                      onChange={(e) => setCvData({ ...cvData, personal: { ...cvData.personal, linkedin: e.target.value } })}
                    />
                  </div>
                </div>

                {/* Career Objective / Professional Summary */}
                <div className="pt-2">
                  <label className="block text-slate-800 mb-1 text-xs font-bold">
                    ক্যারিয়ার উদ্দেশ্য / প্রফেশনাল সামারি
                  </label>
                  <textarea
                    rows={3}
                    className="w-full px-3 py-2 border rounded-xl border-slate-200 text-xs focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500 focus:outline-none"
                    value={activeLang === 'bn' ? cvData.summary : cvData.summaryEn}
                    onChange={(e) => {
                      if (activeLang === 'bn') {
                        setCvData({ ...cvData, summary: e.target.value });
                      } else {
                        setCvData({ ...cvData, summaryEn: e.target.value });
                      }
                    }}
                  />
                </div>
              </div>

              {/* 4. Work Experience */}
              <div className="bg-white p-5 rounded-2xl border border-slate-200/90 shadow-sm space-y-4">
                <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                  <h4 className="font-bold text-sm text-slate-900 flex items-center gap-2">
                    <Briefcase className="w-4 h-4 text-emerald-600" /> চাকরির অভিজ্ঞতা (Work Experience)
                  </h4>
                  <button
                    onClick={handleAddExperience}
                    className="text-xs font-bold text-emerald-700 bg-emerald-50 hover:bg-emerald-100 px-3 py-1.5 rounded-lg flex items-center gap-1 transition"
                  >
                    <Plus className="w-3.5 h-3.5" /> পদ যোগ করুন
                  </button>
                </div>

                {cvData.experiences.map((exp, idx) => (
                  <div key={exp.id} className="p-3.5 bg-slate-50/70 rounded-xl border border-slate-200/80 space-y-2.5 text-xs">
                    <div className="grid grid-cols-2 gap-2">
                      <input
                        type="text"
                        placeholder="প্রতিষ্ঠানের নাম"
                        className="px-3 py-2 bg-white border rounded-lg font-semibold"
                        value={exp.company}
                        onChange={(e) => {
                          const updated = [...cvData.experiences];
                          updated[idx].company = e.target.value;
                          setCvData({ ...cvData, experiences: updated });
                        }}
                      />
                      <input
                        type="text"
                        placeholder="পদবী (Role)"
                        className="px-3 py-2 bg-white border rounded-lg font-semibold"
                        value={exp.role}
                        onChange={(e) => {
                          const updated = [...cvData.experiences];
                          updated[idx].role = e.target.value;
                          setCvData({ ...cvData, experiences: updated });
                        }}
                      />
                      <input
                        type="text"
                        placeholder="সময়কাল (যেমন: ২০২২ - বর্তমান)"
                        className="px-3 py-2 bg-white border rounded-lg"
                        value={exp.startDate}
                        onChange={(e) => {
                          const updated = [...cvData.experiences];
                          updated[idx].startDate = e.target.value;
                          setCvData({ ...cvData, experiences: updated });
                        }}
                      />
                      <input
                        type="text"
                        placeholder="লোকেশন (ঢাকা, বাংলাদেশ)"
                        className="px-3 py-2 bg-white border rounded-lg"
                        value={exp.location}
                        onChange={(e) => {
                          const updated = [...cvData.experiences];
                          updated[idx].location = e.target.value;
                          setCvData({ ...cvData, experiences: updated });
                        }}
                      />
                    </div>
                    <textarea
                      rows={3}
                      placeholder="দায়িত্ব ও অর্জনসমূহ (পয়েন্ট আকারে)"
                      className="w-full px-3 py-2 bg-white border rounded-lg text-xs leading-relaxed"
                      value={exp.description}
                      onChange={(e) => {
                        const updated = [...cvData.experiences];
                        updated[idx].description = e.target.value;
                        setCvData({ ...cvData, experiences: updated });
                      }}
                    />
                    {cvData.experiences.length > 1 && (
                      <div className="flex justify-end">
                        <button
                          onClick={() => {
                            setCvData({
                              ...cvData,
                              experiences: cvData.experiences.filter((_, i) => i !== idx)
                            });
                          }}
                          className="text-red-500 hover:text-red-700 text-[11px] font-semibold flex items-center gap-1"
                        >
                          <Trash2 className="w-3.5 h-3.5" /> মুছে ফেলুন
                        </button>
                      </div>
                    )}
                  </div>
                ))}
              </div>

              {/* 5. Education Credentials */}
              <div className="bg-white p-5 rounded-2xl border border-slate-200/90 shadow-sm space-y-4">
                <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                  <h4 className="font-bold text-sm text-slate-900 flex items-center gap-2">
                    <GraduationCap className="w-4 h-4 text-emerald-600" /> শিক্ষাগত যোগ্যতা (Education)
                  </h4>
                  <button
                    onClick={handleAddEducation}
                    className="text-xs font-bold text-emerald-700 bg-emerald-50 hover:bg-emerald-100 px-3 py-1.5 rounded-lg flex items-center gap-1 transition"
                  >
                    <Plus className="w-3.5 h-3.5" /> ডিগ্রী যোগ করুন
                  </button>
                </div>

                {cvData.educations.map((edu, idx) => (
                  <div key={edu.id} className="p-3.5 bg-slate-50/70 rounded-xl border border-slate-200/80 grid grid-cols-2 gap-2 text-xs">
                    <input
                      type="text"
                      placeholder="ডিগ্রী (যেমন: SSC / HSC / B.Sc)"
                      className="px-3 py-2 bg-white border rounded-lg font-bold"
                      value={edu.degree}
                      onChange={(e) => {
                        const updated = [...cvData.educations];
                        updated[idx].degree = e.target.value;
                        setCvData({ ...cvData, educations: updated });
                      }}
                    />
                    <input
                      type="text"
                      placeholder="শিক্ষা প্রতিষ্ঠান"
                      className="px-3 py-2 bg-white border rounded-lg"
                      value={edu.institution}
                      onChange={(e) => {
                        const updated = [...cvData.educations];
                        updated[idx].institution = e.target.value;
                        setCvData({ ...cvData, educations: updated });
                      }}
                    />
                    <input
                      type="text"
                      placeholder="বোর্ড / বিশ্ববিদ্যালয়"
                      className="px-3 py-2 bg-white border rounded-lg"
                      value={edu.boardOrUni}
                      onChange={(e) => {
                        const updated = [...cvData.educations];
                        updated[idx].boardOrUni = e.target.value;
                        setCvData({ ...cvData, educations: updated });
                      }}
                    />
                    <div className="flex gap-2">
                      <input
                        type="text"
                        placeholder="পাসের সন"
                        className="w-1/2 px-2.5 py-2 bg-white border rounded-lg"
                        value={edu.passingYear}
                        onChange={(e) => {
                          const updated = [...cvData.educations];
                          updated[idx].passingYear = e.target.value;
                          setCvData({ ...cvData, educations: updated });
                        }}
                      />
                      <input
                        type="text"
                        placeholder="প্রাপ্ত GPA"
                        className="w-1/2 px-2.5 py-2 bg-white border rounded-lg font-bold text-emerald-700"
                        value={edu.cgpa}
                        onChange={(e) => {
                          const updated = [...cvData.educations];
                          updated[idx].cgpa = e.target.value;
                          setCvData({ ...cvData, educations: updated });
                        }}
                      />
                    </div>
                  </div>
                ))}
              </div>

              {/* 6. Skills Management */}
              <div className="bg-white p-5 rounded-2xl border border-slate-200/90 shadow-sm space-y-3">
                <h4 className="font-bold text-sm text-slate-900 flex items-center gap-2">
                  <Award className="w-4 h-4 text-emerald-600" /> পেশাগত দক্ষতা ও স্কিলস
                </h4>
                <div className="flex flex-wrap gap-2">
                  {cvData.skills.map((skill, index) => (
                    <span 
                      key={index} 
                      className="inline-flex items-center gap-1.5 px-3 py-1 rounded-lg text-xs font-semibold bg-slate-100 text-slate-800 border border-slate-200"
                    >
                      {skill}
                      <button 
                        onClick={() => setCvData({ ...cvData, skills: cvData.skills.filter((_, i) => i !== index) })}
                        className="text-slate-400 hover:text-red-500 font-bold ml-0.5"
                      >
                        ×
                      </button>
                    </span>
                  ))}
                </div>
                <div className="flex gap-2 pt-1">
                  <input
                    id="new-skill-input"
                    type="text"
                    placeholder="নতুন দক্ষতা লিখে এন্টার চাপুন"
                    className="flex-1 px-3 py-2 border rounded-xl text-xs border-slate-200 focus:outline-none"
                    onKeyDown={(e) => {
                      if (e.key === 'Enter' && e.target.value.trim()) {
                        e.preventDefault();
                        setCvData({ ...cvData, skills: [...cvData.skills, e.target.value.trim()] });
                        e.target.value = '';
                      }
                    }}
                  />
                  <button
                    onClick={() => {
                      const input = document.getElementById('new-skill-input');
                      if (input && input.value.trim()) {
                        setCvData({ ...cvData, skills: [...cvData.skills, input.value.trim()] });
                        input.value = '';
                      }
                    }}
                    className="px-4 py-2 bg-slate-900 hover:bg-slate-800 text-white rounded-xl text-xs font-bold"
                  >
                    যোগ করুন
                  </button>
                </div>
              </div>

            </div>

            {/* Right Live Interactive A4 Paper (7 Cols) */}
            <div className="lg:col-span-7 space-y-4">
              
              {/* Paper Top Floating Utility Bar */}
              <div className="flex items-center justify-between bg-slate-900 text-white px-5 py-3 rounded-2xl shadow-md print:hidden">
                <div className="flex items-center gap-2 text-xs font-bold">
                  <span className="w-2.5 h-2.5 rounded-full bg-emerald-400 animate-pulse"></span>
                  <span>লাইভ A4 প্রিভিউ — {template.toUpperCase()} ({pageMode})</span>
                </div>
                <div className="flex items-center gap-3">
                  <span className="text-[11px] text-slate-300 hidden sm:inline">
                    প্রিন্ট করলে ১০০% স্ট্যান্ডার্ড A4 পেপারে ফিট হবে
                  </span>
                  <button
                    onClick={handlePrint}
                    className="bg-emerald-500 hover:bg-emerald-600 text-slate-950 font-black px-3 py-1 rounded-lg text-xs flex items-center gap-1 transition"
                  >
                    <Printer className="w-3.5 h-3.5" />
                    প্রিন্ট
                  </button>
                </div>
              </div>

              {/* A4 Paper Container */}
              <div 
                ref={previewRef}
                className="bg-white shadow-2xl rounded-sm mx-auto border border-slate-200 overflow-hidden text-slate-800 transition-all print:shadow-none print:border-none print:m-0 print:p-0"
                style={{
                  minHeight: pageMode === '2-page' ? '2200px' : '1100px',
                  width: '100%',
                  maxWidth: '840px'
                }}
              >

                {/* ============================================================== */}
                {/* 1. TEMPLATE: MODERN EXECUTIVE (SIDEBAR & PHOTO PREMIUM LAYOUT) */}
                {/* ============================================================== */}
                {template === 'modern' && (
                  <div className="grid grid-cols-12 min-h-full">
                    
                    {/* Left Colored Sidebar (4 Columns) */}
                    <div 
                      className="col-span-4 p-6 text-white space-y-6"
                      style={{ backgroundColor: accentColor }}
                    >
                      {/* Photo Box */}
                      {cvData.showPhoto && cvData.photo && (
                        <div className="flex justify-center pb-2">
                          <div className={`overflow-hidden border-4 border-white/20 shadow-xl ${
                            cvData.photoShape === 'rounded-full' 
                              ? 'w-32 h-32 rounded-full' 
                              : cvData.photoShape === 'rounded-xl'
                              ? 'w-32 h-32 rounded-2xl'
                              : 'w-32 h-40 rounded-sm' // Classic portrait passport
                          }`}>
                            <img 
                              src={cvData.photo} 
                              alt="Profile" 
                              className="w-full h-full object-cover"
                            />
                          </div>
                        </div>
                      )}

                      {/* Contact Details */}
                      <div>
                        <h3 className="text-xs font-black uppercase tracking-widest text-emerald-300 border-b border-white/20 pb-1.5 mb-3">
                          যোগাযোগ (Contact)
                        </h3>
                        <div className="space-y-2.5 text-[11px] text-white/90">
                          <div className="flex items-start gap-2">
                            <Phone className="w-3.5 h-3.5 text-emerald-400 shrink-0 mt-0.5" />
                            <span>{cvData.personal.phone}</span>
                          </div>
                          <div className="flex items-start gap-2">
                            <Mail className="w-3.5 h-3.5 text-emerald-400 shrink-0 mt-0.5" />
                            <span className="break-all">{cvData.personal.email}</span>
                          </div>
                          <div className="flex items-start gap-2">
                            <MapPin className="w-3.5 h-3.5 text-emerald-400 shrink-0 mt-0.5" />
                            <span>{cvData.personal.presentAddress}</span>
                          </div>
                          {cvData.personal.linkedin && (
                            <div className="flex items-start gap-2">
                              <Linkedin className="w-3.5 h-3.5 text-emerald-400 shrink-0 mt-0.5" />
                              <span className="break-all">{cvData.personal.linkedin}</span>
                            </div>
                          )}
                        </div>
                      </div>

                      {/* Skills Sidebar */}
                      <div>
                        <h3 className="text-xs font-black uppercase tracking-widest text-emerald-300 border-b border-white/20 pb-1.5 mb-3">
                          {t.cvSkills}
                        </h3>
                        <div className="flex flex-wrap gap-1.5">
                          {cvData.skills.map((skill, i) => (
                            <span 
                              key={i} 
                              className="px-2 py-0.5 rounded bg-white/10 text-white text-[10px] font-medium border border-white/10"
                            >
                              {skill}
                            </span>
                          ))}
                        </div>
                      </div>

                      {/* Language Proficiency & Points Sidebar */}
                      <div>
                        <h3 className="text-xs font-black uppercase tracking-widest text-emerald-300 border-b border-white/20 pb-1.5 mb-3 flex items-center justify-between">
                          <span>{t.cvLangPoints}</span>
                        </h3>
                        <div className="space-y-3 text-xs">
                          {cvData.languages.map((lang, i) => (
                            <div key={lang.id || i} className="bg-white/10 p-2.5 rounded-lg border border-white/10 space-y-1.5">
                              <div className="flex justify-between items-center">
                                <span className="font-bold text-white text-[11.5px]">{lang.name}</span>
                                <span className="text-[10px] bg-emerald-500/30 text-emerald-200 px-1.5 py-0.5 rounded font-bold">
                                  {lang.level}
                                </span>
                              </div>
                              {/* Visual 10-Point Dots */}
                              {renderPointDots(lang.points, true)}
                              {/* R/W/S/L Breakdown */}
                              <div className="grid grid-cols-2 gap-1 pt-1 text-[9.5px] text-white/80 border-t border-white/10">
                                <div>• {activeLang === 'bn' ? 'পড়া:' : 'Read:'} {lang.reading}</div>
                                <div>• {activeLang === 'bn' ? 'লেখা:' : 'Write:'} {lang.writing}</div>
                                <div>• {activeLang === 'bn' ? 'বলা:' : 'Speak:'} {lang.speaking}</div>
                                <div>• {activeLang === 'bn' ? 'শোনা:' : 'Listen:'} {lang.listening}</div>
                              </div>
                              {lang.certScore && (
                                <div className="text-[9.5px] text-emerald-300 font-semibold pt-0.5">
                                  ★ {lang.certScore}
                                </div>
                              )}
                            </div>
                          ))}
                        </div>
                      </div>

                      {/* Bio Details */}
                      <div>
                        <h3 className="text-xs font-black uppercase tracking-widest text-emerald-300 border-b border-white/20 pb-1.5 mb-3">
                          {t.cvPersonalBio}
                        </h3>
                        <div className="space-y-1.5 text-[11px] text-white/80">
                          <div><span className="text-white/60">{t.cvFather}:</span> {cvData.personal.fatherName}</div>
                          <div><span className="text-white/60">{t.cvMother}:</span> {cvData.personal.motherName}</div>
                          <div><span className="text-white/60">{t.cvBlood}:</span> {cvData.personal.bloodGroup}</div>
                          <div><span className="text-white/60">{t.cvNationality}:</span> {cvData.personal.nationality}</div>
                          <div><span className="text-white/60">NID:</span> {cvData.personal.nid}</div>
                        </div>
                      </div>
                    </div>

                    {/* Right Main Body (8 Columns) */}
                    <div className="col-span-8 p-7 space-y-6">
                      
                      {/* Name & Title Header */}
                      <div className="border-b-2 border-slate-100 pb-4">
                        <h1 className="text-2xl sm:text-3xl font-black text-slate-900 tracking-tight">
                          {activeLang === 'bn' ? cvData.personal.fullName : cvData.personal.fullNameEn}
                        </h1>
                        <p className="text-sm font-bold text-emerald-700 tracking-wide uppercase mt-1">
                          {cvData.personal.title}
                        </p>
                      </div>

                      {/* Professional Summary */}
                      <div>
                        <h2 className="text-xs font-black uppercase tracking-wider text-slate-900 border-b-2 border-slate-900 pb-1 mb-2.5 flex items-center gap-1.5">
                          <User className="w-3.5 h-3.5 text-emerald-600" />
                          {t.cvSummary}
                        </h2>
                        <p className="text-xs text-slate-700 leading-relaxed text-justify">
                          {activeLang === 'bn' ? cvData.summary : cvData.summaryEn}
                        </p>
                      </div>

                      {/* Work Experience */}
                      <div>
                        <h2 className="text-xs font-black uppercase tracking-wider text-slate-900 border-b-2 border-slate-900 pb-1 mb-3 flex items-center gap-1.5">
                          <Briefcase className="w-3.5 h-3.5 text-emerald-600" />
                          {t.cvExperience}
                        </h2>
                        <div className="space-y-4">
                          {cvData.experiences.map((exp) => (
                            <div key={exp.id} className="text-xs space-y-1">
                              <div className="flex justify-between items-baseline font-bold text-slate-900">
                                <span className="text-sm text-slate-800">{exp.role}</span>
                                <span className="text-slate-500 font-semibold text-[11px]">{exp.startDate}</span>
                              </div>
                              <div className="text-emerald-700 font-bold text-xs flex justify-between">
                                <span>{exp.company}</span>
                                <span className="text-slate-400 font-normal">{exp.location}</span>
                              </div>
                              <p className="text-slate-700 whitespace-pre-line leading-relaxed pt-1 text-[11.5px]">
                                {exp.description}
                              </p>
                            </div>
                          ))}
                        </div>
                      </div>

                      {/* Page Break for 2-Page Mode */}
                      {pageMode === '2-page' && (
                        <div className="relative py-3 print:hidden">
                          <div className="border-t-2 border-dashed border-red-300"></div>
                          <span className="absolute left-1/2 -top-2.5 -translate-x-1/2 bg-red-100 text-red-700 px-3 py-0.5 rounded-full text-[10px] font-black">
                            --- পেজ ২ (Page 2 Starts Here) ---
                          </span>
                        </div>
                      )}

                      {/* Education */}
                      <div>
                        <h2 className="text-xs font-black uppercase tracking-wider text-slate-900 border-b-2 border-slate-900 pb-1 mb-3 flex items-center gap-1.5">
                          <GraduationCap className="w-3.5 h-3.5 text-emerald-600" />
                          {t.cvEducation}
                        </h2>
                        <div className="space-y-2.5">
                          {cvData.educations.map((edu) => (
                            <div key={edu.id} className="text-xs flex justify-between items-start border-b border-slate-100 pb-1.5 last:border-none">
                              <div>
                                <div className="font-bold text-slate-900">{edu.degree}</div>
                                <div className="text-slate-600 text-[11px]">{edu.institution} ({edu.boardOrUni})</div>
                              </div>
                              <div className="text-right">
                                <div className="font-bold text-emerald-800">{edu.cgpa}</div>
                                <div className="text-slate-500 text-[11px]">{edu.passingYear}</div>
                              </div>
                            </div>
                          ))}
                        </div>
                      </div>

                      {/* Certifications */}
                      <div>
                        <h2 className="text-xs font-black uppercase tracking-wider text-slate-900 border-b-2 border-slate-900 pb-1 mb-2.5 flex items-center gap-1.5">
                          <Award className="w-3.5 h-3.5 text-emerald-600" />
                          {t.cvCerts}
                        </h2>
                        <ul className="text-xs space-y-1 text-slate-700">
                          {cvData.certifications.map((cert, i) => (
                            <li key={i} className="flex items-center gap-2">
                              <CheckCircle2 className="w-3.5 h-3.5 text-emerald-600 shrink-0" />
                              <span className="font-medium">{cert}</span>
                            </li>
                          ))}
                        </ul>
                      </div>

                      {/* References */}
                      <div>
                        <h2 className="text-xs font-black uppercase tracking-wider text-slate-900 border-b-2 border-slate-900 pb-1 mb-2.5">
                          {t.cvRefs}
                        </h2>
                        <div className="grid grid-cols-2 gap-3 text-xs">
                          {cvData.references.map((ref) => (
                            <div key={ref.id} className="p-2.5 bg-slate-50 rounded-lg border border-slate-200">
                              <div className="font-bold text-slate-900">{ref.name}</div>
                              <div className="text-slate-600 text-[11px]">{ref.designation}</div>
                              <div className="text-slate-500 text-[10px]">{ref.org}</div>
                              <div className="text-emerald-700 text-[10px] font-mono mt-1">{ref.contact}</div>
                            </div>
                          ))}
                        </div>
                      </div>

                    </div>
                  </div>
                )}

                {/* ============================================================== */}
                {/* 2. TEMPLATE: CLASSIC BD BIODATA (বাংলাদেশি ঐতিহ্যবাহী বায়োডাটা) */}
                {/* ============================================================== */}
                {template === 'classic' && (
                  <div className="p-10 space-y-6 font-serif text-slate-900">
                    
                    {/* Header With Passport Photo on Top Right */}
                    <div className="flex justify-between items-start border-b-2 border-slate-900 pb-4">
                      <div className="space-y-1 flex-1">
                        <h1 className="text-2xl font-black uppercase tracking-wider">{t.cvBiodataTitle}</h1>
                        <h2 className="text-xl font-bold text-slate-800">
                          {activeLang === 'bn' ? cvData.personal.fullName : cvData.personal.fullNameEn}
                        </h2>
                        <p className="text-xs font-bold text-slate-600 font-sans">{cvData.personal.title}</p>
                        <div className="text-xs text-slate-700 space-y-0.5 pt-1 font-sans">
                          <p><strong>{activeLang === 'bn' ? 'মোবাইল:' : 'Phone:'}</strong> {cvData.personal.phone} | <strong>Email:</strong> {cvData.personal.email}</p>
                          <p><strong>{t.cvPresentAddr}:</strong> {cvData.personal.presentAddress}</p>
                        </div>
                      </div>

                      {/* Passport Photo Box on Top Right */}
                      {cvData.showPhoto && cvData.photo && (
                        <div className="shrink-0 pl-4">
                          <div className={`w-28 h-36 border-2 border-slate-800 p-1 bg-white shadow-sm overflow-hidden ${
                            cvData.photoShape === 'rounded-full' ? 'rounded-full' : 'rounded-none'
                          }`}>
                            <img src={cvData.photo} alt="Passport Photo" className="w-full h-full object-cover" />
                          </div>
                        </div>
                      )}
                    </div>

                    {/* Career Objective */}
                    <div>
                      <h3 className="text-xs font-bold uppercase bg-slate-200 px-3 py-1 mb-2 border-l-4 border-slate-900 font-sans tracking-wide">
                        {t.cvCareerObj}
                      </h3>
                      <p className="text-xs leading-relaxed text-justify px-1 font-sans">
                        {activeLang === 'bn' ? cvData.summary : cvData.summaryEn}
                      </p>
                    </div>

                    {/* Employment History */}
                    <div>
                      <h3 className="text-xs font-bold uppercase bg-slate-200 px-3 py-1 mb-2 border-l-4 border-slate-900 font-sans tracking-wide">
                        {t.cvExperience}
                      </h3>
                      <div className="space-y-3 font-sans">
                        {cvData.experiences.map((exp) => (
                          <div key={exp.id} className="text-xs">
                            <div className="flex justify-between font-bold text-slate-900">
                              <span>{exp.role} — {exp.company}</span>
                              <span className="text-slate-600">{exp.startDate}</span>
                            </div>
                            <p className="text-slate-700 whitespace-pre-line mt-1 text-[11px] leading-relaxed">
                              {exp.description}
                            </p>
                          </div>
                        ))}
                      </div>
                    </div>

                    {/* Education Table (Official Bangladeshi Table Layout) */}
                    <div>
                      <h3 className="text-xs font-bold uppercase bg-slate-200 px-3 py-1 mb-2 border-l-4 border-slate-900 font-sans tracking-wide">
                        {t.cvEducation}
                      </h3>
                      <table className="w-full text-xs border border-slate-400 font-sans text-center">
                        <thead className="bg-slate-100 border-b border-slate-400">
                          <tr>
                            <th className="p-2 border-r border-slate-300">{t.cvExam}</th>
                            <th className="p-2 border-r border-slate-300">{t.cvInst}</th>
                            <th className="p-2 border-r border-slate-300">{t.cvBoard}</th>
                            <th className="p-2 border-r border-slate-300">{t.cvYear}</th>
                            <th className="p-2">{t.cvResult}</th>
                          </tr>
                        </thead>
                        <tbody>
                          {cvData.educations.map((edu) => (
                            <tr key={edu.id} className="border-b border-slate-300">
                              <td className="p-2 border-r border-slate-300 font-bold text-left">{edu.degree}</td>
                              <td className="p-2 border-r border-slate-300 text-left">{edu.institution}</td>
                              <td className="p-2 border-r border-slate-300">{edu.boardOrUni}</td>
                              <td className="p-2 border-r border-slate-300">{edu.passingYear}</td>
                              <td className="p-2 font-bold">{edu.cgpa}</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>

                    {/* Language Proficiency & Points Table (ভাষাগত দক্ষতা ও পয়েন্ট ছক) */}
                    <div>
                      <h3 className="text-xs font-bold uppercase bg-slate-200 px-3 py-1 mb-2 border-l-4 border-slate-900 font-sans tracking-wide">
                        {t.cvLangPoints}
                      </h3>
                      <table className="w-full text-xs border border-slate-400 font-sans text-center">
                        <thead className="bg-slate-100 border-b border-slate-400">
                          <tr>
                            <th className="p-2 border-r border-slate-300 text-left">{t.cvLangName}</th>
                            <th className="p-2 border-r border-slate-300">{t.cvLangLevel}</th>
                            <th className="p-2 border-r border-slate-300">{t.cvLangRWSL}</th>
                            <th className="p-2">{t.cvLangScore}</th>
                          </tr>
                        </thead>
                        <tbody>
                          {cvData.languages.map((lang, idx) => (
                            <tr key={lang.id || idx} className="border-b border-slate-300">
                              <td className="p-2 border-r border-slate-300 font-bold text-left">
                                {lang.name}
                                {lang.certScore && <div className="text-[10px] text-emerald-700 font-normal">{lang.certScore}</div>}
                              </td>
                              <td className="p-2 border-r border-slate-300 font-medium">{lang.level}</td>
                              <td className="p-2 border-r border-slate-300 text-[11px]">
                                {lang.reading} | {lang.writing} | {lang.speaking} | {lang.listening}
                              </td>
                              <td className="p-2 font-black text-emerald-800">{lang.points || 9} / 10</td>
                            </tr>
                          ))}
                        </tbody>
                      </table>
                    </div>

                    {/* Traditional Biodata Personal Information Table */}
                    <div>
                      <h3 className="text-xs font-bold uppercase bg-slate-200 px-3 py-1 mb-2 border-l-4 border-slate-900 font-sans tracking-wide">
                        {t.cvPersonalTable}
                      </h3>
                      <table className="w-full text-xs border border-slate-400 font-sans">
                        <tbody>
                          <tr className="border-b border-slate-300">
                            <td className="w-1/3 p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvFather}</td>
                            <td className="p-2">{cvData.personal.fatherName}</td>
                          </tr>
                          <tr className="border-b border-slate-300">
                            <td className="p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvMother}</td>
                            <td className="p-2">{cvData.personal.motherName}</td>
                          </tr>
                          <tr className="border-b border-slate-300">
                            <td className="p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvPermAddr}</td>
                            <td className="p-2">{cvData.personal.permanentAddress}</td>
                          </tr>
                          <tr className="border-b border-slate-300">
                            <td className="p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvDobNat}</td>
                            <td className="p-2">{cvData.personal.dob} | {cvData.personal.nationality}</td>
                          </tr>
                          <tr className="border-b border-slate-300">
                            <td className="p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvNid}</td>
                            <td className="p-2 font-mono font-bold">{cvData.personal.nid}</td>
                          </tr>
                          <tr className="border-b border-slate-300">
                            <td className="p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvRelBlood}</td>
                            <td className="p-2">{cvData.personal.religion} | {cvData.personal.bloodGroup}</td>
                          </tr>
                          <tr>
                            <td className="p-2 bg-slate-50 font-bold border-r border-slate-300">{t.cvMarital}</td>
                            <td className="p-2">{cvData.personal.maritalStatus}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>

                    {/* Declaration & Signature (Mandatory for BD Govt/Corporate Biodata) */}
                    <div className="space-y-6 pt-2 font-sans text-xs">
                      <div>
                        <h4 className="font-bold underline mb-1">{t.cvDeclaration}:</h4>
                        <p className="text-slate-700">{t.cvDeclText}</p>
                      </div>
                      <div className="pt-8 flex justify-between items-end">
                        <div>
                          <p>{t.cvDate}</p>
                          <p className="mt-1">{t.cvPlace}</p>
                        </div>
                        <div className="text-center space-y-1">
                          <div className="border-t-2 border-slate-800 w-48 mx-auto pt-1 font-bold">
                            {t.cvSign} ({activeLang === 'bn' ? cvData.personal.fullName : cvData.personal.fullNameEn})
                          </div>
                        </div>
                      </div>
                    </div>

                  </div>
                )}

                {/* ============================================================== */}
                {/* 3. TEMPLATE: CORPORATE ATS CLEAN (1-PAGE / RECRUITER APPROVED) */}
                {/* ============================================================== */}
                {template === 'executive' && (
                  <div className="p-8 space-y-5 text-slate-900 font-sans">
                    
                    {/* Header Clean ATS */}
                    <div className="border-b pb-4 flex justify-between items-center">
                      <div>
                        <h1 className="text-3xl font-black tracking-tight" style={{ color: accentColor }}>
                          {activeLang === 'bn' ? cvData.personal.fullName : cvData.personal.fullNameEn}
                        </h1>
                        <p className="text-sm font-bold text-slate-600 tracking-wide mt-0.5">
                          {cvData.personal.title}
                        </p>
                        <div className="flex flex-wrap gap-x-4 gap-y-1 text-xs text-slate-600 mt-2 font-medium">
                          <span>{cvData.personal.phone}</span>
                          <span>•</span>
                          <span>{cvData.personal.email}</span>
                          <span>•</span>
                          <span>{cvData.personal.presentAddress}</span>
                          <span>•</span>
                          <span className="text-blue-700">{cvData.personal.linkedin}</span>
                        </div>
                      </div>

                      {cvData.showPhoto && cvData.photo && (
                        <div className="shrink-0 ml-4">
                          <img 
                            src={cvData.photo} 
                            alt="Profile" 
                            className={`w-24 h-24 object-cover border-2 border-slate-300 shadow-sm ${
                              cvData.photoShape === 'rounded-full' ? 'rounded-full' : 'rounded-lg'
                            }`}
                          />
                        </div>
                      )}
                    </div>

                    {/* Summary */}
                    <div>
                      <h2 className="text-xs font-black uppercase tracking-widest text-slate-900 border-b pb-1 mb-2" style={{ borderColor: accentColor }}>
                        {t.cvSummary}
                      </h2>
                      <p className="text-xs text-slate-700 leading-relaxed text-justify">
                        {activeLang === 'bn' ? cvData.summary : cvData.summaryEn}
                      </p>
                    </div>

                    {/* Core Skills Matrix */}
                    <div>
                      <h2 className="text-xs font-black uppercase tracking-widest text-slate-900 border-b pb-1 mb-2" style={{ borderColor: accentColor }}>
                        {t.cvSkills}
                      </h2>
                      <div className="flex flex-wrap gap-1.5 text-xs">
                        {cvData.skills.map((skill, i) => (
                          <span key={i} className="px-2.5 py-1 bg-slate-100 rounded text-slate-800 text-[11px] font-semibold border border-slate-200">
                            {skill}
                          </span>
                        ))}
                      </div>
                    </div>

                    {/* Experience */}
                    <div>
                      <h2 className="text-xs font-black uppercase tracking-widest text-slate-900 border-b pb-1 mb-3" style={{ borderColor: accentColor }}>
                        {t.cvExperience}
                      </h2>
                      <div className="space-y-4">
                        {cvData.experiences.map((exp) => (
                          <div key={exp.id} className="text-xs space-y-1">
                            <div className="flex justify-between font-bold text-slate-900">
                              <span className="text-sm">{exp.role}</span>
                              <span className="text-slate-500 font-semibold">{exp.startDate}</span>
                            </div>
                            <div className="text-slate-600 font-semibold">{exp.company} — {exp.location}</div>
                            <p className="text-slate-700 whitespace-pre-line leading-relaxed text-[11px] pt-0.5">
                              {exp.description}
                            </p>
                          </div>
                        ))}
                      </div>
                    </div>

                    {/* Education */}
                    <div>
                      <h2 className="text-xs font-black uppercase tracking-widest text-slate-900 border-b pb-1 mb-2.5" style={{ borderColor: accentColor }}>
                        {t.cvEducation}
                      </h2>
                      <div className="space-y-2">
                        {cvData.educations.map((edu) => (
                          <div key={edu.id} className="text-xs flex justify-between items-center">
                            <div>
                              <span className="font-bold text-slate-900">{edu.degree}</span>
                              <span className="text-slate-600"> — {edu.institution}</span>
                            </div>
                            <div className="font-bold text-slate-700">{edu.cgpa} ({edu.passingYear})</div>
                          </div>
                        ))}
                      </div>
                    </div>

                    {/* Language Points Compact Section for Corporate ATS */}
                    <div>
                      <h2 className="text-xs font-black uppercase tracking-widest text-slate-900 border-b pb-1 mb-2.5" style={{ borderColor: accentColor }}>
                        {t.cvLangPoints}
                      </h2>
                      <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                        {cvData.languages.map((lang, idx) => (
                          <div key={lang.id || idx} className="p-2.5 bg-slate-50 rounded-lg border border-slate-200 flex flex-col justify-between gap-1">
                            <div className="flex items-center justify-between">
                              <span className="font-bold text-slate-900">{lang.name}</span>
                              <span className="text-[11px] font-bold text-emerald-700 bg-emerald-50 px-2 py-0.5 rounded border border-emerald-200">
                                {lang.points || 9}/10 Points • {lang.level}
                              </span>
                            </div>
                            <div className="text-[10.5px] text-slate-600">
                              Read: {lang.reading} | Write: {lang.writing} | Speak: {lang.speaking} | Listen: {lang.listening}
                            </div>
                            {lang.certScore && (
                              <div className="text-[10px] font-semibold text-slate-800">Note: {lang.certScore}</div>
                            )}
                          </div>
                        ))}
                      </div>
                    </div>

                  </div>
                )}

              </div>
            </div>

          </div>
        )}

        {/* -------------------- TAB 2: AI COVER LETTER & DORKHAST -------------------- */}
        {activeTab === 'coverletter' && (
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
            
            {/* Form Inputs (5 Cols) */}
            <div className="lg:col-span-5 bg-white p-6 rounded-2xl border border-slate-200/90 shadow-sm space-y-5">
              <div className="flex items-center gap-2.5 border-b border-slate-100 pb-3">
                <div className="w-8 h-8 rounded-lg bg-amber-100 text-amber-700 flex items-center justify-center font-bold">
                  <Sparkles className="w-4 h-4" />
                </div>
                <div>
                  <h3 className="font-black text-slate-900 text-sm">হিউম্যানাইজড আবেদনপত্র ও কভার লেটার</h3>
                  <p className="text-[11px] text-slate-500 font-medium">রোবটিক এআই ক্লিশে ছাড়া খাঁটি মানুষের মতো অফিসিয়াল আবেদন</p>
                </div>
              </div>

              <div className="space-y-3.5 text-xs">
                <div>
                  <label className="block text-slate-700 font-bold mb-1">আবেদনের ফরম্যাট ও টোন</label>
                  <select
                    className="w-full px-3 py-2 border rounded-xl bg-slate-50 font-semibold"
                    value={letterForm.tone}
                    onChange={(e) => setLetterForm({ ...letterForm, tone: e.target.value })}
                  >
                    <option value="dorkhast">ঐতিহ্যবাহী বাংলাদেশি সরকারি ও প্রাতিষ্ঠানিক দরখাস্ত (বরাবর, বিষয়, বিনীত নিবেদন...)</option>
                    <option value="professional">কর্পোরেট কভার লেটার (Natural Professional & High Impact)</option>
                  </select>
                </div>

                <div className="grid grid-cols-2 gap-2">
                  <div>
                    <label className="block text-slate-700 font-bold mb-1">ভাষা (Language)</label>
                    <select
                      className="w-full px-3 py-2 border rounded-xl bg-slate-50 font-semibold"
                      value={letterForm.language}
                      onChange={(e) => setLetterForm({ ...letterForm, language: e.target.value })}
                    >
                      <option value="bn">বাংলা (শুদ্ধ ও প্রাতিষ্ঠানিক)</option>
                      <option value="en">English (Corporate Standard)</option>
                    </select>
                  </div>
                  <div>
                    <label className="block text-slate-700 font-bold mb-1">মোট অভিজ্ঞতা</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl"
                      value={letterForm.userExperienceYears}
                      onChange={(e) => setLetterForm({ ...letterForm, userExperienceYears: e.target.value })}
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-slate-700 font-bold mb-1">প্রতিষ্ঠানের নাম</label>
                  <input
                    type="text"
                    className="w-full px-3 py-2 border rounded-xl font-medium"
                    placeholder="যেমন: গ্রামীণফোন লিমিটেড"
                    value={letterForm.companyName}
                    onChange={(e) => setLetterForm({ ...letterForm, companyName: e.target.value })}
                  />
                </div>

                <div className="grid grid-cols-2 gap-2">
                  <div>
                    <label className="block text-slate-700 font-bold mb-1">আবেদনের পদবী</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl font-medium"
                      value={letterForm.jobTitle}
                      onChange={(e) => setLetterForm({ ...letterForm, jobTitle: e.target.value })}
                    />
                  </div>
                  <div>
                    <label className="block text-slate-700 font-bold mb-1">প্রাপক / সম্বোধন</label>
                    <input
                      type="text"
                      className="w-full px-3 py-2 border rounded-xl"
                      value={letterForm.recipientTitle}
                      onChange={(e) => setLetterForm({ ...letterForm, recipientTitle: e.target.value })}
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-slate-700 font-bold mb-1">বিজ্ঞপ্তির সূত্র (Source)</label>
                  <input
                    type="text"
                    className="w-full px-3 py-2 border rounded-xl"
                    value={letterForm.source}
                    onChange={(e) => setLetterForm({ ...letterForm, source: e.target.value })}
                  />
                </div>

                <div>
                  <label className="block text-slate-700 font-bold mb-1">যে বিশেষ দক্ষতাগুলো চিঠিতে জোর দেবেন</label>
                  <textarea
                    rows={2}
                    className="w-full px-3 py-2 border rounded-xl text-xs"
                    value={letterForm.customSkillsToHighlight}
                    onChange={(e) => setLetterForm({ ...letterForm, customSkillsToHighlight: e.target.value })}
                  />
                </div>

                <button
                  onClick={generateHumanCoverLetter}
                  disabled={isGenerating}
                  className="w-full py-3 bg-slate-900 hover:bg-slate-800 text-white font-black rounded-xl shadow-md transition flex items-center justify-center gap-2"
                >
                  {isGenerating ? (
                    <>
                      <RefreshCw className="w-4 h-4 animate-spin" />
                      <span>চিঠি প্রস্তুত হচ্ছে...</span>
                    </>
                  ) : (
                    <>
                      <Sparkles className="w-4 h-4 text-amber-400" />
                      <span>বাস্তবসম্মত মানুষের ভাষায় চিঠি লিখুন</span>
                    </>
                  )}
                </button>
              </div>
            </div>

            {/* Letter Output Preview (7 Cols) */}
            <div className="lg:col-span-7 space-y-4">
              <div className="bg-white p-6 rounded-2xl border border-slate-200/90 shadow-sm space-y-4">
                <div className="flex flex-wrap items-center justify-between gap-2 border-b border-slate-100 pb-3">
                  <h4 className="font-black text-slate-900 text-sm flex items-center gap-2">
                    <FileText className="w-4 h-4 text-emerald-600" />
                    তৈরিকৃত আবেদনপত্র (সম্পাদনাযোগ্য)
                  </h4>
                  <div className="flex items-center gap-2">
                    <button
                      onClick={() => {
                        if (!generatedLetter) return;
                        navigator.clipboard.writeText(generatedLetter);
                        showToast("সম্পূর্ণ চিঠি কপি করা হয়েছে!");
                      }}
                      className="px-3 py-1.5 bg-slate-100 hover:bg-slate-200 text-slate-800 text-xs font-bold rounded-lg flex items-center gap-1.5 transition"
                    >
                      <Copy className="w-3.5 h-3.5" /> কপি
                    </button>
                    <button
                      onClick={() => triggerJobDispatch("", `Application for ${letterForm.jobTitle} - ${cvData.personal.fullName}`, generatedLetter)}
                      className="px-3.5 py-1.5 bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-black rounded-lg flex items-center gap-1.5 transition shadow-sm"
                    >
                      <Send className="w-3.5 h-3.5" /> মেইল অ্যাপ্লিকেশন পাঠান
                    </button>
                  </div>
                </div>

                {generatedLetter ? (
                  <textarea
                    rows={19}
                    className="w-full p-4 rounded-xl border border-slate-200 bg-slate-50/60 font-serif text-sm leading-relaxed text-slate-900 focus:outline-none focus:ring-2 focus:ring-emerald-500/20 focus:border-emerald-500"
                    value={generatedLetter}
                    onChange={(e) => setGeneratedLetter(e.target.value)}
                  />
                ) : (
                  <div className="py-24 text-center text-slate-400 space-y-3">
                    <Sparkles className="w-10 h-10 mx-auto text-slate-300" />
                    <p className="text-sm font-semibold text-slate-600">বামপাশের তথ্যগুলো পূরণ করে 'লিখুন' বাটনে চাপুন।</p>
                    <p className="text-xs text-slate-400 max-w-sm mx-auto">
                      কোনো এআই বা রোবট এটা লিখেছে বলে কেউ বুঝতে পারবে না—একেবারে প্রাতিষ্ঠানিক ও মানুষের মতো সাবলীল ভাষায় তৈরি হবে।
                    </p>
                  </div>
                )}
              </div>
            </div>

          </div>
        )}

        {/* -------------------- TAB 3: APPLICATION TRACKER -------------------- */}
        {activeTab === 'tracker' && (
          <div className="space-y-6">
            
            {/* Stats Overview */}
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
              <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm flex items-center gap-4">
                <div className="w-12 h-12 bg-blue-50 text-blue-600 rounded-xl flex items-center justify-center font-bold">
                  <Briefcase className="w-6 h-6" />
                </div>
                <div>
                  <div className="text-2xl font-black text-slate-900">{applications.length}</div>
                  <div className="text-xs font-semibold text-slate-500">মোট প্রেরিত আবেদন</div>
                </div>
              </div>
              <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm flex items-center gap-4">
                <div className="w-12 h-12 bg-amber-50 text-amber-600 rounded-xl flex items-center justify-center font-bold">
                  <Calendar className="w-6 h-6" />
                </div>
                <div>
                  <div className="text-2xl font-black text-slate-900">
                    {applications.filter(a => a.status.includes('ইন্টারভিউ')).length}
                  </div>
                  <div className="text-xs font-semibold text-slate-500">ইন্টারভিউ কল পেয়েছে</div>
                </div>
              </div>
              <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm flex items-center gap-4">
                <div className="w-12 h-12 bg-emerald-50 text-emerald-600 rounded-xl flex items-center justify-center font-bold">
                  <CheckSquare className="w-6 h-6" />
                </div>
                <div>
                  <div className="text-2xl font-black text-slate-900">১০০%</div>
                  <div className="text-xs font-semibold text-slate-500">সিভি ও দরখাস্ত রেডি</div>
                </div>
              </div>
            </div>

            {/* Add Application Form */}
            <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
              <h3 className="text-sm font-bold text-slate-900 mb-3 flex items-center gap-2">
                <Plus className="w-4 h-4 text-emerald-600" /> নতুন চাকরির আবেদন ট্র্যাকারে যোগ করুন
              </h3>
              <div className="grid grid-cols-1 sm:grid-cols-4 gap-3 text-xs">
                <input
                  type="text"
                  placeholder="কোম্পানির নাম (যেমন: বিকাশ / ব্র্যাক ব্যাংক)"
                  className="px-3 py-2 border rounded-xl font-medium"
                  value={newApp.company}
                  onChange={(e) => setNewApp({ ...newApp, company: e.target.value })}
                />
                <input
                  type="text"
                  placeholder="পদবী (যেমন: সফটওয়্যার ইঞ্জিনিয়ার)"
                  className="px-3 py-2 border rounded-xl font-medium"
                  value={newApp.position}
                  onChange={(e) => setNewApp({ ...newApp, position: e.target.value })}
                />
                <input
                  type="email"
                  placeholder="এইচআর ইমেইল (careers@company.com)"
                  className="px-3 py-2 border rounded-xl"
                  value={newApp.contactEmail}
                  onChange={(e) => setNewApp({ ...newApp, contactEmail: e.target.value })}
                />
                <button
                  onClick={() => {
                    if (!newApp.company || !newApp.position) {
                      showToast("কোম্পানি ও পদের নাম পূরণ করুন!");
                      return;
                    }
                    setApplications([
                      ...applications,
                      {
                        id: Date.now(),
                        company: newApp.company,
                        position: newApp.position,
                        contactEmail: newApp.contactEmail || "careers@company.com",
                        appliedDate: new Date().toLocaleDateString('bn-BD', { year: 'numeric', month: 'numeric', day: 'numeric' }),
                        status: newApp.status,
                        notes: "সিভি ও আবেদনপত্র পাঠানো হয়েছে।"
                      }
                    ]);
                    setNewApp({ company: "", position: "", status: "আবেদন সম্পন্ন (Applied)", contactEmail: "", notes: "" });
                    showToast("আবেদন সফলভাবে ট্র্যাকারে সেভ হয়েছে!");
                  }}
                  className="bg-slate-900 hover:bg-slate-800 text-white font-bold py-2 rounded-xl transition"
                >
                  রেকর্ড সংরক্ষণ করুন
                </button>
              </div>
            </div>

            {/* Applications Table */}
            <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
              <div className="overflow-x-auto">
                <table className="w-full text-left text-xs text-slate-700">
                  <thead className="bg-slate-50 text-slate-600 font-bold border-b border-slate-100 uppercase text-[10px] tracking-wider">
                    <tr>
                      <th className="px-5 py-3">কোম্পানি ও পদবী</th>
                      <th className="px-4 py-3">তারিখ</th>
                      <th className="px-4 py-3">স্ট্যাটাস</th>
                      <th className="px-4 py-3">নোটস</th>
                      <th className="px-5 py-3 text-right">পদক্ষেপ</th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-100">
                    {applications.map((app) => (
                      <tr key={app.id} className="hover:bg-slate-50/80 transition">
                        <td className="px-5 py-3.5">
                          <div className="font-bold text-slate-900 text-sm">{app.company}</div>
                          <div className="text-slate-500 font-medium text-xs">{app.position}</div>
                        </td>
                        <td className="px-4 py-3.5 font-mono text-slate-600">
                          {app.appliedDate}
                        </td>
                        <td className="px-4 py-3.5">
                          <span className={`inline-flex px-2.5 py-1 rounded-full text-[11px] font-bold ${
                            app.status.includes('ইন্টারভিউ') 
                              ? 'bg-amber-100 text-amber-900 border border-amber-200' 
                              : 'bg-emerald-100 text-emerald-900 border border-emerald-200'
                          }`}>
                            {app.status}
                          </span>
                        </td>
                        <td className="px-4 py-3.5 text-slate-600 max-w-xs truncate">
                          {app.notes}
                        </td>
                        <td className="px-5 py-3.5 text-right space-x-2">
                          <button
                            onClick={() => triggerJobDispatch(app.contactEmail, `Application for ${app.position} - ${cvData.personal.fullName}`)}
                            className="inline-flex items-center gap-1.5 bg-emerald-600 hover:bg-emerald-700 text-white px-3 py-1.5 rounded-lg font-bold text-xs transition shadow-sm"
                            title="সরাসরি সিভি সহ মেইল পাঠান"
                          >
                            <Mail className="w-3.5 h-3.5" />
                            <span>মেইল করুন</span>
                          </button>
                          <button
                            onClick={() => {
                              setApplications(applications.filter(a => a.id !== app.id));
                              showToast("তালিকা থেকে মুছে ফেলা হয়েছে");
                            }}
                            className="p-1.5 text-slate-400 hover:text-red-500 transition"
                          >
                            <Trash2 className="w-3.5 h-3.5" />
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>

          </div>
        )}

      </main>

      {/* Global Print Style Settings */}
      <style>{`
        @media print {
          body {
            background-color: white !important;
            color: black !important;
            margin: 0 !important;
            padding: 0 !important;
          }
          header, nav, button, .print\\:hidden {
            display: none !important;
          }
          @page {
            size: A4 portrait;
            margin: 10mm;
          }
        }
      `}</style>
    </div>
  );
}