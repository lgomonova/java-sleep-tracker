package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AverageSessionDurationFunction implements AnalysisFunction {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        double averageDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0.0);
        return new SleepAnalysisResult<>("Средняя продолжительность сессии сна (мин)", averageDuration);
    }
}