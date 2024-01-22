package com.project.stringmixer.features.mix;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MixStringsService {

    public String mixStrings(String... strings) {
        Map<Character, CharacterOccurrenceMap> maxOccurrenceMap = new HashMap<>();

        IntStream.range(0, strings.length)
                .forEach(i -> {
                    int stringId = i + 1;
                    strings[i].chars()
                            .filter(Character::isLowerCase)
                            .forEach(c -> maxOccurrenceMap.computeIfAbsent((char) c, CharacterOccurrenceMap::new)
                                    .incrementCharacterCount(stringId));
                });

        return maxOccurrenceMap.values().stream()
                .filter(info -> info.getMaxOccurrence() > 1)
                .map(CharacterOccurrenceMap::toString)
                .sorted(Comparator.comparingInt(String::length).reversed()
                        .thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.joining("/"));
    }
}
