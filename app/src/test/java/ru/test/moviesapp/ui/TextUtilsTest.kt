package ru.test.moviesapp.ui

import org.junit.Test

import org.junit.Assert.*

class TextUtilsTest {

    @Test
    fun discardIfCropped_FullText() {
        val text = "1234567899"
        val count = TextUtils.discardNotFitted(0,10,text.toCharArray())
        assertEquals(count,text.length)
    }

    @Test
    fun discardIfCropped_oneWord() {
        val text = "1234567899"
        val count = TextUtils.discardNotFitted(0,8,text.toCharArray())
        assertEquals(count,8)
    }

    @Test
    fun discardIfCropped_WithSpacesInEnds() {
        val text = "12345678   "
        val count = TextUtils.discardNotFitted(0,text.length,text.toCharArray())
        assertEquals(count,text.length)
    }


    @Test
    fun discardIfCropped_WithTabsInEnds() {
        val text = "12345678        "
        val count = TextUtils.discardNotFitted(0,text.length,text.toCharArray())
        assertEquals(count,text.length)
    }


    @Test
    fun discardIfCropped_WithNewlineInEnds() {
        val text = "12345678\n\n"
        val count = TextUtils.discardNotFitted(0,text.length,text.toCharArray())
        assertEquals(count,text.length)
    }

    @Test
    fun discardIfCropped_WithNewlineInMiddle() {
        val text = "12345678\n\n\n"
        val count = TextUtils.discardNotFitted(0,text.length-1,text.toCharArray())
        assertEquals(count,text.length-1)
    }
    @Test
    fun discardIfCropped_WithCroppedText() {
        val text = "12345678 aaa"
        val count = TextUtils.discardNotFitted(0,text.length-1,text.toCharArray())
        assertEquals(text.length-3,count)
    }

    @Test
    fun discardIfCropped_WithCroppedText2() {
        val text = "56 789"
        val count = TextUtils.discardNotFitted(0,5,text.toCharArray())
        assertEquals(3,count)
    }
}