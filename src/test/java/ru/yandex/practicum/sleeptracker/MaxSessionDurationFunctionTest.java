package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSessionDurationFunctionTest {

    private final MaxSessionDurationFunction function = new MaxSessionDurationFunction();

    @Test
    void returnsZeroForEmptyList() {
        SleepAnalysisResult<?> result = function.apply(List.of());

        assertEquals(0L, result.getValue());
    }

    @Test
    void findsLongestSessionAmongSeveral() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 30),
                        LocalDateTime.of(2025, 10, 3, 15, 20),
                        SleepQuality.NORMAL),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        SleepQuality.BAD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(600L, result.getValue());
    }

    @Test
    void returnsSessionDurationWhenOnlyOneSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(570L, result.getValue());
    }
}