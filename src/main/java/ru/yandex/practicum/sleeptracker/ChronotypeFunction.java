package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChronotypeFunction implements AnalysisFunction {

    private static final LocalTime OWL_SLEEP_START_THRESHOLD = LocalTime.of(23, 0);
    private static final LocalTime OWL_WAKE_UP_THRESHOLD = LocalTime.of(9, 0);
    private static final LocalTime LARK_SLEEP_START_THRESHOLD = LocalTime.of(22, 0);
    private static final LocalTime LARK_WAKE_UP_THRESHOLD = LocalTime.of(7, 0);

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        List<LocalDate> nights = NightUtils.getNightsInPeriod(sessions);

        Map<Chronotype, Long> nightTypeCounts = nights.stream()
                .map(night -> NightUtils.findNightSession(sessions, night))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::classifyNight)
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        Chronotype chronotype = determineDominantChronotype(nightTypeCounts);

        return new SleepAnalysisResult<>("Хронотип пользователя", chronotype);
    }

    private Chronotype classifyNight(SleepingSession session) {
        LocalTime sleepStartTime = session.getSleepStart().toLocalTime();
        LocalTime wakeUpTime = session.getWakeUp().toLocalTime();

        if (sleepStartTime.isAfter(OWL_SLEEP_START_THRESHOLD) && wakeUpTime.isAfter(OWL_WAKE_UP_THRESHOLD)) {
            return Chronotype.OWL;
        }
        if (sleepStartTime.isBefore(LARK_SLEEP_START_THRESHOLD) && wakeUpTime.isBefore(LARK_WAKE_UP_THRESHOLD)) {
            return Chronotype.LARK;
        }
        return Chronotype.DOVE;
    }

    private Chronotype determineDominantChronotype(Map<Chronotype, Long> nightTypeCounts) {
        long owlCount = nightTypeCounts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = nightTypeCounts.getOrDefault(Chronotype.LARK, 0L);
        long doveCount = nightTypeCounts.getOrDefault(Chronotype.DOVE, 0L);

        if (owlCount > larkCount && owlCount > doveCount) {
            return Chronotype.OWL;
        }
        if (larkCount > owlCount && larkCount > doveCount) {
            return Chronotype.LARK;
        }
        return Chronotype.DOVE;
    }
}