package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsCountFunctionTest {

    private final SleeplessNightsCountFunction function = new SleeplessNightsCountFunction();

    @Test
    void returnsZeroForEmptyList() {
        SleepAnalysisResult<?> result = function.apply(List.of());

        assertEquals(0L, result.getValue());
    }

    @Test
    void noSleeplessNightsWhenEveryNightIsCovered() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void sessionFromTwoToSevenDoesNotCountAsSleepless() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 2, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.NORMAL)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void sessionFromSevenToElevenIsCountedAsSleepless() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        LocalDateTime.of(2025, 10, 3, 11, 0),
                        SleepQuality.NORMAL)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void countsGapNightBetweenTwoNormalNights() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 22, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void handlesPeriodCrossingMonthBoundary() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 31, 23, 0),
                        LocalDateTime.of(2025, 11, 1, 7, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 11, 2, 23, 0),
                        LocalDateTime.of(2025, 11, 3, 7, 0),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(1L, result.getValue());
    }
}