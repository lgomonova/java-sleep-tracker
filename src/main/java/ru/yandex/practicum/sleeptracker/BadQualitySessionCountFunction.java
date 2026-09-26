package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualitySessionCountFunction implements AnalysisFunction {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long badQualityCount = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult<>("Количество сессий с плохим качеством сна", badQualityCount);
    }
}