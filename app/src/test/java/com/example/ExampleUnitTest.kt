package com.example

import com.example.model.CvTemplate
import com.example.ui.CvViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun `cv view model initializes with sample data`() {
        val viewModel = CvViewModel()
        assertNotNull(viewModel.cvData.value)
        assertEquals("bn", viewModel.activeLang.value)
        assertEquals(CvTemplate.MODERN, viewModel.selectedTemplate.value)
        assertTrue(viewModel.cvData.value.skills.isNotEmpty())
        assertTrue(viewModel.cvData.value.languages.isNotEmpty())
    }

    @Test
    fun `skills management works properly`() {
        val viewModel = CvViewModel()
        val originalCount = viewModel.cvData.value.skills.size
        viewModel.addSkill("Jetpack Compose")
        assertEquals(originalCount + 1, viewModel.cvData.value.skills.size)
        assertTrue(viewModel.cvData.value.skills.contains("Jetpack Compose"))

        viewModel.removeSkill("Jetpack Compose")
        assertEquals(originalCount, viewModel.cvData.value.skills.size)
        assertFalse(viewModel.cvData.value.skills.contains("Jetpack Compose"))
    }

    @Test
    fun `cover letter form updates and job application tracking works`() {
        val viewModel = CvViewModel()
        viewModel.updateCoverLetterForm { it.copy(jobTitle = "Lead Mobile Architect") }
        assertEquals("Lead Mobile Architect", viewModel.coverLetterData.value.jobTitle)

        val initialApps = viewModel.applications.value.size
        viewModel.addApplication("Test Org", "Engineer", "test@org.com", "Interview scheduled")
        assertEquals(initialApps + 1, viewModel.applications.value.size)
        assertEquals("Test Org", viewModel.applications.value.first().company)
    }

    @Test
    fun `language switching updates active language`() {
        val viewModel = CvViewModel()
        viewModel.setLanguage("en")
        assertEquals("en", viewModel.activeLang.value)

        viewModel.loadSampleData("en")
        assertEquals("Md. Rakibul Hasan", viewModel.cvData.value.personal.fullNameEn)
    }
}
