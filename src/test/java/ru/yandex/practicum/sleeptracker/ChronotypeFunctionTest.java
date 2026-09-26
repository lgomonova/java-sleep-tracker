package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeFunctionTest {

    private final ChronotypeFunction function = new ChronotypeFunction();

    @Test
    void classifiesUserAsOwl() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void classifiesUserAsLark() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(Chronotype.LARK, result.getValue());
    }

    @Test
    void classifiesUserAsDoveWhenThresholdsAreNotMet() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void classifiesUserAsDoveOnTieBetweenOwlAndLark() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(Chronotype.DOVE, result.getValue());
    }

    @Test
    void ignoresDaytimeNapsAndSleeplessNightsWhenClassifying() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 15, 0),
                        SleepQuality.NORMAL),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 15),
                        LocalDateTime.of(2025, 10, 4, 9, 15),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }
}