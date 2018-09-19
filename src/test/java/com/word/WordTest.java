package com.word;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class WordTest {

    private WordService service;

    @BeforeEach
    void setup() {
        service = new WordService();
    }

    @DisplayName("Positive Tests")
    @ParameterizedTest(name = "{index} => testData = ''{0}'' expectedCount = ''{1}'' searchCriteria = ''{2}''")
    @MethodSource("wordProvider")
    void test(String testData, int expectedCount, String searchCriteria) {
        service.addWord(testData);
        assertEquals(expectedCount, service.getWordCount(searchCriteria));
    }

    static Stream<Arguments> wordProvider() {
        return Stream.of(
            Arguments.of("asdasd", 0, "asd"),
            Arguments.of("asd", 1, "asd"),
            Arguments.of("asd;asd;asedasdaas", 2, "asd")
        );
    }

    @Test
    @DisplayName("User can add value with decimeter")
    void test1() {
        service.addWord("cat;cat;cAt");
        assertEquals(3, service.getWordCount("cAt", true));
    }

    @Test
    @DisplayName("User can not add non alphabetic symbols")
    void test2() {
        assertThrows(IllegalArgumentException.class, () -> service.addWord("123"));
    }
}
