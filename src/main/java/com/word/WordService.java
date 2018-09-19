package com.word;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import lombok.NonNull;

public final class WordService {

    private ArrayList<WordEntity> entities;

    public WordService() {
        this.entities = new ArrayList<WordEntity>();
    }

    public void addWord(@NonNull String word) {

        if (word.contains(";")) {
            Stream.of(word.split(";")).forEach(this::addWord);
            return;
        }

        checkIfWordHasOnlyAlphabeticSymbols(word);

        if (checkEntityHasBeenAddedAlready(word)) {
            increaseCounterForExistingWord(word);
        } else {
            entities.add(WordEntity.builder()
                .word(word)
                .build());
        }
    }

    private void checkIfWordHasOnlyAlphabeticSymbols(String word) {
        if (!StringUtils.isAlpha(word)) {
            System.out.println(word);
            throw new IllegalArgumentException();
        }
    }

    public int getWordCount(@NonNull String word, boolean ignoreCaseSearch) {
        if (ignoreCaseSearch) {
            String wordLowerCased = word.toLowerCase();
            return entities.stream()
                .filter(wordEntity -> wordLowerCased.equals(wordEntity.getWord().toLowerCase()))
                .mapToInt(WordEntity::getCount)
                .sum();
        } else {
            return entities.stream()
                .filter(wordEntity -> word.equals(wordEntity.getWord()))
                .mapToInt(WordEntity::getCount)
                .sum();
        }
    }

    public int getWordCount(@NonNull String word) {
        return getWordCount(word, false);
    }

    private void increaseCounterForExistingWord(@NonNull String word) {
        WordEntity wordEntity = entities
            .stream()
            .filter(wordEnt -> word.equals(wordEnt.getWord()))
            .findFirst()
            .get();
        wordEntity.increaseCount();
    }

    private boolean checkEntityHasBeenAddedAlready(String word) {
        return entities.stream()
            .map(WordEntity::getWord)
            .anyMatch(word::equals);
    }
}
