package com.project.stringmixer.features.mix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterOccurrenceMapTest {
    @Test
    void testIncrementCharacterCountWithOneStringAndOneCharacterOccurrence() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('a');
        characterOccurrenceMap.incrementCharacterCount(1);

        assertEquals(1, characterOccurrenceMap.getMaxOccurrence());
        assertEquals(1, characterOccurrenceMap.getMaxStringId());
        assertFalse(characterOccurrenceMap.isAllMaxesEqual());
    }

    @Test
    void testIncrementCharacterCountWithOneStringAndMultipleCharacterOccurrences() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('b');
        for (int i = 0; i < 3; i++) {
            characterOccurrenceMap.incrementCharacterCount(1);
        }

        assertEquals(3, characterOccurrenceMap.getMaxOccurrence());
        assertEquals(1, characterOccurrenceMap.getMaxStringId());
        assertFalse(characterOccurrenceMap.isAllMaxesEqual());
    }

    @Test
    public void testIncrementCharacterCountWithTwoStringsAndDifferentNumberOfCharacterOccurrences() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('c');
        characterOccurrenceMap.incrementCharacterCount(1);
        for (int i = 0; i < 3; i++) {
            characterOccurrenceMap.incrementCharacterCount(1);
            characterOccurrenceMap.incrementCharacterCount(2);
        }

        assertEquals(4, characterOccurrenceMap.getMaxOccurrence());
        assertEquals(1, characterOccurrenceMap.getMaxStringId());
        assertFalse(characterOccurrenceMap.isAllMaxesEqual());
    }

    @Test
    void testIncrementCharacterCountWithTwoStringsAndSameNumberOfCharacterOccurrences() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('d');
        for (int i = 0; i < 3; i++) {
            characterOccurrenceMap.incrementCharacterCount(1);
            characterOccurrenceMap.incrementCharacterCount(2);
        }
        assertEquals(3, characterOccurrenceMap.getMaxOccurrence());
        assertEquals(1, characterOccurrenceMap.getMaxStringId());
        assertTrue(characterOccurrenceMap.isAllMaxesEqual());
    }

    @Test
    public void testToStringWhenAllMaximumCharacterOccurrencesAreEqual() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('e');
        characterOccurrenceMap.setMaxOccurrence(3);
        characterOccurrenceMap.setAllMaxesEqual(true);
        String result = characterOccurrenceMap.toString();

        assertEquals("=:eee", result);
    }

    @Test
    public void testToStringWhenMaximumCharacterOccurrenceIsInFirstString() {
        CharacterOccurrenceMap characterMap = new CharacterOccurrenceMap('f');
        characterMap.setMaxOccurrence(3);
        characterMap.setMaxStringId(1);
        characterMap.setAllMaxesEqual(false);
        String result = characterMap.toString();

        assertEquals("1:fff", result);
    }

    @Test
    public void testToStringWhenMaximumCharacterOccurrenceIsInSecondString() {
        CharacterOccurrenceMap characterMap = new CharacterOccurrenceMap('g');
        characterMap.setMaxOccurrence(5);
        characterMap.setMaxStringId(2);
        characterMap.setAllMaxesEqual(false);
        String result = characterMap.toString();

        assertEquals("2:ggggg", result);
    }

    @Test
    public void testToStringWhenMaximumCharacterOccurrenceIsZero() {
        CharacterOccurrenceMap characterMap = new CharacterOccurrenceMap('h');
        characterMap.setMaxOccurrence(0);
        characterMap.setMaxStringId(0);
        String result = characterMap.toString();

        assertEquals("0:", result);
    }
}
