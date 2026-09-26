package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.util.List;

public class SleeplessNightsCountFunction implements AnalysisFunction {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        List<LocalDate> nights = NightUtils.getNightsInPeriod(sessions);

        long sleeplessNightsCount = nights.stream()
                .filter(night -> NightUtils.findNightSession(sessions, night).isEmpty())
                .count();

        return new SleepAnalysisResult<>("Количество бессонных ночей", sleeplessNightsCount);
    }
}