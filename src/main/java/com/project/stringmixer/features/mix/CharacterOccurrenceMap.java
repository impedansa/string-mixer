package com.project.stringmixer.features.mix;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CharacterOccurrenceMap {
    private char character;
    private int maxOccurrence;
    private int maxStringId;
    private boolean allMaxesEqual;
    private Map<Integer, Integer> stringIdCharacterCountMap;

    public CharacterOccurrenceMap(char character) {
        this.character = character;
        this.stringIdCharacterCountMap = new HashMap<>();
    }

    public void incrementCharacterCount(int stringId) {
        stringIdCharacterCountMap.compute(stringId, (key, oldValue) -> (oldValue == null) ? 1 : oldValue + 1);
        findMaxOccurrences();
        allMaxesEqual = stringIdCharacterCountMap.values()
                .stream()
                .distinct()
                .count() == 1;
    }

    private void findMaxOccurrences() {
        Collection<Integer> maxOccurrences = stringIdCharacterCountMap.values();

        //If there is only one string or the character is not present in all strings, no need to evaluate further.
        if (maxOccurrences.size() == 1 || maxOccurrences.stream().anyMatch(val -> val < 1)) {
            maxOccurrence = -1;
            maxStringId = -1;
            return;
        }

        Map.Entry<Integer, Integer> maxEntry = Collections.max(stringIdCharacterCountMap.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue));
        maxOccurrence = maxEntry.getValue();
        maxStringId = maxEntry.getKey();
    }

    @Override
    public String toString() {
        return allMaxesEqual ? "=:" + String.valueOf(character).repeat(maxOccurrence) :
                maxStringId + ":" + String.valueOf(character).repeat(maxOccurrence);
    }
}
