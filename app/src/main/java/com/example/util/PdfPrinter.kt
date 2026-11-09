package com.example.util

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.model.CvData
import com.example.model.CvTemplate

object PdfPrinter {

    fun printCv(context: Context, cvData: CvData, template: CvTemplate, activeLang: String, accentHex: String) {
        val htmlContent = generateCvHtml(cvData, template, activeLang, accentHex)
        val webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
                val jobName = "Resume_${if (activeLang == "bn") cvData.personal.fullName else cvData.personal.fullNameEn}"
                val printAdapter = webView.createPrintDocumentAdapter(jobName)
                val printAttributes = PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .build()
                printManager?.print(jobName, printAdapter, printAttributes)
            }
        }
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null)
    }

    fun generateCvHtml(cvData: CvData, template: CvTemplate, lang: String, accentHex: String): String {
        val isBn = lang == "bn"
        val name = if (isBn) cvData.personal.fullName else cvData.personal.fullNameEn
        val summary = if (isBn) cvData.summary else cvData.summaryEn

        return when (template) {
            CvTemplate.MODERN -> generateModernHtml(cvData, name, summary, isBn, accentHex)
            CvTemplate.CLASSIC -> generateClassicHtml(cvData, name, summary, isBn)
            CvTemplate.EXECUTIVE -> generateExecutiveHtml(cvData, name, summary, isBn, accentHex)
        }
    }

    private fun generateModernHtml(cvData: CvData, name: String, summary: String, isBn: Boolean, accentHex: String): String {
        val p = cvData.personal
        val photoHtml = if (cvData.showPhoto && !cvData.photoUri.isNullOrEmpty()) {
            """<div style="text-align:center; margin-bottom: 20px;">
                <img src="${cvData.photoUri}" style="width: 120px; height: 120px; object-fit: cover; border-radius: 12px; border: 3px solid rgba(255,255,255,0.3);" />
            </div>"""
        } else ""

        val expHtml = cvData.experiences.joinToString("") { exp ->
            """<div style="margin-bottom: 14px;">
                <div style="display: flex; justify-content: space-between; font-weight: bold; font-size: 13px;">
                    <span>${exp.role}</span>
                    <span style="color: #64748b; font-size: 11px;">${exp.startDate}</span>
                </div>
                <div style="color: $accentHex; font-weight: 600; font-size: 12px; margin-bottom: 4px;">
                    ${exp.company} &bull; <span style="color: #64748b; font-weight: normal;">${exp.location}</span>
                </div>
                <div style="font-size: 11.5px; color: #334155; line-height: 1.5; white-space: pre-line;">${exp.description}</div>
            </div>"""
        }

        val eduHtml = cvData.educations.joinToString("") { edu ->
            """<div style="display: flex; justify-content: space-between; margin-bottom: 10px; font-size: 12px;">
                <div>
                    <div style="font-weight: bold;">${edu.degree}</div>
                    <div style="color: #475569; font-size: 11px;">${edu.institution} (${edu.boardOrUni})</div>
                </div>
                <div style="text-align: right;">
                    <div style="font-weight: bold; color: #047857;">${edu.cgpa}</div>
                    <div style="color: #64748b; font-size: 11px;">${edu.passingYear}</div>
                </div>
            </div>"""
        }

        val skillsHtml = cvData.skills.joinToString(" ") { skill ->
            """<span style="display: inline-block; background: rgba(255,255,255,0.12); padding: 4px 8px; border-radius: 4px; font-size: 10px; margin: 2px;">$skill</span>"""
        }

        val langHtml = cvData.languages.joinToString("") { l ->
            """<div style="background: rgba(255,255,255,0.08); padding: 8px; border-radius: 6px; margin-bottom: 8px; font-size: 11px;">
                <div style="display: flex; justify-content: space-between; font-weight: bold;">
                    <span>${l.name}</span>
                    <span style="color: #34d399;">${l.points}/10 &bull; ${l.level}</span>
                </div>
                <div style="font-size: 9.5px; opacity: 0.8; margin-top: 3px;">
                    Read: ${l.reading} | Write: ${l.writing} | Speak: ${l.speaking}
                </div>
            </div>"""
        }

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <style>
                @page { size: A4 portrait; margin: 0; }
                body { margin: 0; padding: 0; font-family: 'Segoe UI', Arial, sans-serif; color: #1e293b; background: #fff; }
                .container { display: flex; min-height: 100vh; }
                .sidebar { width: 33%; background-color: $accentHex; color: #fff; padding: 28px 20px; box-sizing: border-box; }
                .main { width: 67%; padding: 28px 24px; box-sizing: border-box; }
                h2 { font-size: 12px; text-transform: uppercase; letter-spacing: 1px; border-bottom: 2px solid #0f172a; padding-bottom: 4px; margin-top: 18px; margin-bottom: 12px; color: #0f172a; }
                .side-h2 { font-size: 11px; text-transform: uppercase; letter-spacing: 1.5px; border-bottom: 1px solid rgba(255,255,255,0.25); padding-bottom: 4px; margin-top: 20px; margin-bottom: 10px; color: #a7f3d0; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="sidebar">
                    $photoHtml
                    <div class="side-h2">${if (isBn) "যোগাযোগ" else "Contact"}</div>
                    <div style="font-size: 11px; line-height: 1.7; opacity: 0.95;">
                        <div>📞 ${p.phone}</div>
                        <div>✉️ ${p.email}</div>
                        <div>📍 ${p.presentAddress}</div>
                        ${if (p.linkedin.isNotEmpty()) "<div>🔗 ${p.linkedin}</div>" else ""}
                    </div>

                    <div class="side-h2">${if (isBn) "মূল দক্ষতা" else "Core Skills"}</div>
                    <div>$skillsHtml</div>

                    <div class="side-h2">${if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট" else "Language Proficiency"}</div>
                    <div>$langHtml</div>

                    <div class="side-h2">${if (isBn) "ব্যক্তিগত বিবরণ" else "Personal Bio"}</div>
                    <div style="font-size: 10.5px; line-height: 1.6; opacity: 0.85;">
                        <div>Father: ${p.fatherName}</div>
                        <div>Mother: ${p.motherName}</div>
                        <div>Blood: ${p.bloodGroup}</div>
                        <div>Nationality: ${p.nationality}</div>
                        <div>NID: ${p.nid}</div>
                    </div>
                </div>
                <div class="main">
                    <h1 style="margin: 0; font-size: 26px; font-weight: 900; color: #0f172a;">$name</h1>
                    <div style="font-size: 13px; font-weight: bold; color: $accentHex; text-transform: uppercase; margin-top: 4px; margin-bottom: 16px;">
                        ${p.title}
                    </div>

                    <h2>${if (isBn) "প্রফেশনাল বিবরণ (Executive Summary)" else "Executive Summary"}</h2>
                    <p style="font-size: 11.5px; line-height: 1.6; color: #334155; margin: 0; text-align: justify;">$summary</p>

                    <h2>${if (isBn) "কর্মঅভিজ্ঞতা (Work Experience)" else "Work Experience"}</h2>
                    $expHtml

                    <h2>${if (isBn) "শিক্ষাগত যোগ্যতা (Education)" else "Education"}</h2>
                    $eduHtml

                    <h2>${if (isBn) "প্রফেশনাল সার্টিফিকেশন" else "Certifications"}</h2>
                    <div style="font-size: 11.5px; line-height: 1.6;">
                        ${cvData.certifications.joinToString("") { "<div>✓ $it</div>" }}
                    </div>
                </div>
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    private fun generateClassicHtml(cvData: CvData, name: String, summary: String, isBn: Boolean): String {
        val p = cvData.personal
        val photoHtml = if (cvData.showPhoto && !cvData.photoUri.isNullOrEmpty()) {
            """<img src="${cvData.photoUri}" style="width: 110px; height: 135px; object-fit: cover; border: 2px solid #000; padding: 2px;" />"""
        } else ""

        val expHtml = cvData.experiences.joinToString("") { exp ->
            """<div style="margin-bottom: 12px; font-size: 12px;">
                <div style="display:flex; justify-content:space-between; font-weight: bold;">
                    <span>${exp.role} &mdash; ${exp.company}</span>
                    <span>${exp.startDate}</span>
                </div>
                <div style="color: #475569; font-size: 11px;">${exp.location}</div>
                <div style="font-size: 11.5px; margin-top: 3px; line-height: 1.5; white-space: pre-line;">${exp.description}</div>
            </div>"""
        }

        val eduTableRows = cvData.educations.joinToString("") { edu ->
            """<tr>
                <td style="padding: 6px; border: 1px solid #94a3b8; font-weight: bold;">${edu.degree}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8;">${edu.institution}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8;">${edu.boardOrUni}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8; text-align: center;">${edu.passingYear}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8; text-align: center; font-weight: bold;">${edu.cgpa}</td>
            </tr>"""
        }

        val langRows = cvData.languages.joinToString("") { l ->
            """<tr>
                <td style="padding: 6px; border: 1px solid #94a3b8; font-weight: bold;">${l.name}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8;">${l.level}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8;">Read: ${l.reading}, Write: ${l.writing}, Speak: ${l.speaking}</td>
                <td style="padding: 6px; border: 1px solid #94a3b8; text-align: center; font-weight: bold;">${l.points}/10</td>
            </tr>"""
        }

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <style>
                @page { size: A4 portrait; margin: 15mm; }
                body { font-family: 'Times New Roman', Georgia, serif; color: #000; padding: 20px; line-height: 1.4; }
                h1 { font-size: 22px; text-transform: uppercase; margin: 0; }
                .sec-title { font-size: 12px; font-weight: bold; background: #e2e8f0; padding: 4px 8px; border-left: 4px solid #000; text-transform: uppercase; margin: 16px 0 10px 0; font-family: Arial, sans-serif; }
                table { width: 100%; border-collapse: collapse; font-size: 11.5px; }
            </style>
        </head>
        <body>
            <div style="display: flex; justify-content: space-between; border-bottom: 2px solid #000; padding-bottom: 12px;">
                <div>
                    <h1>${if (isBn) "জীবনবৃত্তান্ত (CURRICULUM VITAE)" else "CURRICULUM VITAE"}</h1>
                    <div style="font-size: 18px; font-weight: bold; margin-top: 4px;">$name</div>
                    <div style="font-size: 12px; color: #333; font-weight: bold;">${p.title}</div>
                    <div style="font-size: 11px; margin-top: 6px; font-family: Arial, sans-serif;">
                        <div>Phone: ${p.phone} | Email: ${p.email}</div>
                        <div>Present Address: ${p.presentAddress}</div>
                    </div>
                </div>
                <div>$photoHtml</div>
            </div>

            <div class="sec-title">${if (isBn) "ক্যারিয়ার উদ্দেশ্য (Career Objective)" else "Career Objective"}</div>
            <div style="font-size: 11.5px; text-align: justify;">$summary</div>

            <div class="sec-title">${if (isBn) "চাকরির অভিজ্ঞতা (Work Experience)" else "Work Experience"}</div>
            $expHtml

            <div class="sec-title">${if (isBn) "শিক্ষাগত যোগ্যতা (Educational Qualifications)" else "Educational Qualifications"}</div>
            <table>
                <thead style="background: #f1f5f9;">
                    <tr>
                        <th style="padding: 6px; border: 1px solid #94a3b8; text-align: left;">Degree</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8; text-align: left;">Institution</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8; text-align: left;">Board/Uni</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8;">Year</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8;">Result</th>
                    </tr>
                </thead>
                <tbody>$eduTableRows</tbody>
            </table>

            <div class="sec-title">${if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট ছক" else "Language Proficiency & Points"}</div>
            <table>
                <thead style="background: #f1f5f9;">
                    <tr>
                        <th style="padding: 6px; border: 1px solid #94a3b8; text-align: left;">Language</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8; text-align: left;">Level</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8; text-align: left;">Details</th>
                        <th style="padding: 6px; border: 1px solid #94a3b8;">Score</th>
                    </tr>
                </thead>
                <tbody>$langRows</tbody>
            </table>

            <div class="sec-title">${if (isBn) "ব্যক্তিগত বিবরণী (Personal Particulars)" else "Personal Particulars"}</div>
            <table>
                <tr><td style="width: 30%; padding: 4px; border: 1px solid #cbd5e1; font-weight: bold;">Father's Name:</td><td style="padding: 4px; border: 1px solid #cbd5e1;">${p.fatherName}</td></tr>
                <tr><td style="padding: 4px; border: 1px solid #cbd5e1; font-weight: bold;">Mother's Name:</td><td style="padding: 4px; border: 1px solid #cbd5e1;">${p.motherName}</td></tr>
                <tr><td style="padding: 4px; border: 1px solid #cbd5e1; font-weight: bold;">Permanent Address:</td><td style="padding: 4px; border: 1px solid #cbd5e1;">${p.permanentAddress}</td></tr>
                <tr><td style="padding: 4px; border: 1px solid #cbd5e1; font-weight: bold;">Date of Birth & Nationality:</td><td style="padding: 4px; border: 1px solid #cbd5e1;">${p.dob} | ${p.nationality}</td></tr>
                <tr><td style="padding: 4px; border: 1px solid #cbd5e1; font-weight: bold;">National ID (NID):</td><td style="padding: 4px; border: 1px solid #cbd5e1;">${p.nid}</td></tr>
                <tr><td style="padding: 4px; border: 1px solid #cbd5e1; font-weight: bold;">Religion & Blood Group:</td><td style="padding: 4px; border: 1px solid #cbd5e1;">${p.religion} | ${p.bloodGroup}</td></tr>
            </table>

            <div style="margin-top: 24px; font-size: 11px;">
                <div style="font-weight: bold; text-decoration: underline;">${if (isBn) "ঘোষণা ও সত্যায়ন:" else "Declaration:"}</div>
                <div>${if (isBn) "আমি এই মর্মে ঘোষণা করছি যে, উপরে বর্ণিত সকল তথ্য ও শিক্ষাগত যোগ্যতার বিবরণ আমার জ্ঞান ও বিশ্বাসমতে সম্পূর্ণ সত্য এবং নির্ভুল।" else "I hereby declare that all the information furnished above is true, complete and correct to the best of my knowledge and belief."}</div>
            </div>

            <div style="margin-top: 45px; display: flex; justify-content: space-between; font-size: 11px;">
                <div>
                    <div>Date: .......................................</div>
                    <div style="margin-top: 4px;">Place: Dhaka, Bangladesh</div>
                </div>
                <div style="text-align: center; border-top: 1px solid #000; width: 180px; padding-top: 4px; font-weight: bold;">
                    Signature ($name)
                </div>
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    private fun generateExecutiveHtml(cvData: CvData, name: String, summary: String, isBn: Boolean, accentHex: String): String {
        val p = cvData.personal
        val expHtml = cvData.experiences.joinToString("") { exp ->
            """<div style="margin-bottom: 12px; font-size: 11.5px;">
                <div style="display:flex; justify-content:space-between; font-weight: bold; font-size: 12.5px;">
                    <span>${exp.role}</span>
                    <span style="color: #64748b;">${exp.startDate}</span>
                </div>
                <div style="color: $accentHex; font-weight: 600;">${exp.company} &mdash; ${exp.location}</div>
                <div style="margin-top: 3px; line-height: 1.5; white-space: pre-line; color: #334155;">${exp.description}</div>
            </div>"""
        }

        val eduHtml = cvData.educations.joinToString("") { edu ->
            """<div style="display:flex; justify-content:space-between; font-size: 12px; margin-bottom: 6px;">
                <div><strong>${edu.degree}</strong> &mdash; ${edu.institution} (${edu.boardOrUni})</div>
                <div style="font-weight: bold;">${edu.cgpa} (${edu.passingYear})</div>
            </div>"""
        }

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8" />
            <style>
                @page { size: A4 portrait; margin: 15mm; }
                body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif; color: #0f172a; padding: 20px; line-height: 1.5; }
                h1 { font-size: 26px; margin: 0; color: $accentHex; }
                h2 { font-size: 12px; text-transform: uppercase; letter-spacing: 1px; border-bottom: 1px solid #cbd5e1; padding-bottom: 4px; margin-top: 18px; margin-bottom: 10px; color: #0f172a; }
            </style>
        </head>
        <body>
            <div style="border-bottom: 2px solid $accentHex; padding-bottom: 12px;">
                <h1>$name</h1>
                <div style="font-size: 13px; font-weight: bold; color: #475569; text-transform: uppercase; margin-top: 2px;">${p.title}</div>
                <div style="font-size: 11px; color: #64748b; margin-top: 6px;">
                    ${p.phone} &bull; ${p.email} &bull; ${p.presentAddress} &bull; ${p.linkedin}
                </div>
            </div>

            <h2>${if (isBn) "প্রফেশনাল বিবরণ (Executive Summary)" else "Executive Summary"}</h2>
            <p style="font-size: 11.5px; text-align: justify; color: #334155; margin: 0;">$summary</p>

            <h2>${if (isBn) "দক্ষতা (Core Competencies)" else "Core Competencies"}</h2>
            <div style="font-size: 11px;">
                ${cvData.skills.joinToString(" &bull; ")}
            </div>

            <h2>${if (isBn) "কর্মঅভিজ্ঞতা (Work Experience)" else "Work Experience"}</h2>
            $expHtml

            <h2>${if (isBn) "শিক্ষাগত যোগ্যতা (Education)" else "Education"}</h2>
            $eduHtml

            <h2>${if (isBn) "ভাষাগত দক্ষতা ও পয়েন্ট" else "Languages"}</h2>
            <div style="font-size: 11px;">
                ${cvData.languages.joinToString(" | ") { "${it.name}: ${it.points}/10 (${it.level})" }}
            </div>
        </body>
        </html>
        """.trimIndent()
    }
}
