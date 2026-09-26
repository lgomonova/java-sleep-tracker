package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AverageSessionDurationFunctionTest {

    private final AverageSessionDurationFunction function = new AverageSessionDurationFunction();

    @Test
    void returnsZeroForEmptyList() {
        SleepAnalysisResult<?> result = function.apply(List.of());

        assertEquals(0.0, (double) result.getValue());
    }

    @Test
    void computesAverageOfSeveralSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 8, 0),
                        SleepQuality.GOOD),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 14, 30),
                        LocalDateTime.of(2025, 10, 3, 15, 20),
                        SleepQuality.NORMAL)
        );

        SleepAnalysisResult<?> result = function.apply(sessions);

        assertEquals(325.0, (double) result.getValue(), 0.0001);
    }
}