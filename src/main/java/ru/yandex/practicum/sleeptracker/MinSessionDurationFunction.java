package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinSessionDurationFunction implements AnalysisFunction {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long minDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0L);
        return new SleepAnalysisResult<>("Минимальная продолжительность сессии сна (мин)", minDuration);
    }
}