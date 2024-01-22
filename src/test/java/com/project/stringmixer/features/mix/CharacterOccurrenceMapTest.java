package com.project.stringmixer.features.mix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterOccurrenceMapTest {
    @Test
    void incrementCharacterCount_singleString_singleOccurrence() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('a');
        characterOccurrenceMap.incrementCharacterCount(1);

        assertEquals(1, characterOccurrenceMap.getMaxOccurrence());
        assertEquals(1, characterOccurrenceMap.getMaxStringId());
        assertFalse(characterOccurrenceMap.isAllMaxesEqual());
    }

    @Test
    void incrementCharacterCount_singleString_multipleOccurrences() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('b');
        for (int i = 0; i < 3; i++) {
            characterOccurrenceMap.incrementCharacterCount(1);
        }

        assertEquals(3, characterOccurrenceMap.getMaxOccurrence());
        assertEquals(1, characterOccurrenceMap.getMaxStringId());
        assertFalse(characterOccurrenceMap.isAllMaxesEqual());
    }

    @Test
    public void incrementCharacterCount_multipleStrings_differentNumberOfOccurrences() {
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
    void incrementCharacterCount_multipleStrings_SameNumberOfOccurrences() {
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
    public void toString_allMaxesEqual() {
        CharacterOccurrenceMap characterOccurrenceMap = new CharacterOccurrenceMap('e');
        characterOccurrenceMap.setMaxOccurrence(3);
        characterOccurrenceMap.setAllMaxesEqual(true);
        String result = characterOccurrenceMap.toString();

        assertEquals("=:eee", result);
    }

    @Test
    public void toString_allMaxesNotEqual() {
        CharacterOccurrenceMap characterMap = new CharacterOccurrenceMap('f');
        characterMap.setMaxOccurrence(5);
        characterMap.setMaxStringId(2);
        characterMap.setAllMaxesEqual(false);
        String result = characterMap.toString();

        assertEquals("2:fffff", result);
    }

    @Test
    public void toString_maxOccurrenceIsZero() {
        CharacterOccurrenceMap characterMap = new CharacterOccurrenceMap('g');
        characterMap.setMaxOccurrence(0);
        characterMap.setMaxStringId(0);
        String result = characterMap.toString();

        assertEquals("0:", result);
    }
}
