package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxSessionDurationFunction implements AnalysisFunction {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long maxDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max()
                .orElse(0L);
        return new SleepAnalysisResult<>("Максимальная продолжительность сессии сна (мин)", maxDuration);
    }
}