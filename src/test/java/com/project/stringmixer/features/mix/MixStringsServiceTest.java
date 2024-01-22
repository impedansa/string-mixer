package com.project.stringmixer.features.mix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixStringsServiceTest {

    @Test
    void testMixStringsWhenEachCharacterOccursOnlyOnce() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("abcdefg", "abcdefghijklmn");

        assertEquals("", result);
    }

    @Test
    void testMixStringsWithDifferentNumberOfCharacterOccurrences() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("aaabccccdddryy", "aabbbcdcrrrrrtt");

        assertEquals("2:rrrrr/1:cccc/1:aaa/1:ddd/2:bbb/1:yy/2:tt", result);
    }

    @Test
    void testMixStringsWithSameNumberOfCharacterOccurrences() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("aaaabbbccd", "aaaabbbccdefgh");

        assertEquals("=:aaaa/=:bbb/=:cc", result);
    }

    @Test
    void testMixStringsWithBothSameAndDifferentNumberOfCharacterOccurrences() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("aaabbcddff", "aaaabbcccddeef");

        assertEquals("2:aaaa/2:ccc/1:ff/2:ee/=:bb/=:dd", result);
    }

    @Test
    void testMixStringsWithOneEmptyString() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("", "abcdeee");

        assertEquals("2:eee", result);
    }

    @Test
    void testMixStringWithBothEmptyStrings() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("", "");

        assertEquals("", result);
    }

    @Test
    void testMixStringWithUppercaseLetters() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("AAABCDDDEFGH", "EEEGGGGPKLLL");

        assertEquals("", result);
    }

    @Test
    void testMixStringWithAlphanumericCharacters() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings("AaaaBCDDD123789LLwwkkk", "BbbbaaaHJKTJS582345");

        assertEquals("1:kkk/2:bbb/=:aaa/1:ww", result);
    }

    @Test
    void testMixStringWithSpecialCharacters() {
        MixStringsService mixStringsService = new MixStringsService();
        String result = mixStringsService.mixStrings(";;:.'_=$$$$&&&%^@$%^***", "$$$&&&&&**;--:;;");

        assertEquals("", result);
    }
}
