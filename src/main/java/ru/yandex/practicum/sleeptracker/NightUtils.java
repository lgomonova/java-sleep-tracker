package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class NightUtils {

    private static final LocalTime NOON = LocalTime.NOON;
    private static final LocalTime NIGHT_WINDOW_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_WINDOW_END = LocalTime.of(6, 0);

    private NightUtils() {
    }

    public static LocalDate potentialNight(LocalDateTime moment) {
        return moment.toLocalTime().isAfter(NOON)
                ? moment.toLocalDate().plusDays(1)
                : moment.toLocalDate().minusDays(1);
    }

    public static List<LocalDate> getNightsInPeriod(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return List.of();
        }
        LocalDateTime periodStart = sessions.get(0).getSleepStart();
        LocalDateTime periodEnd = sessions.get(sessions.size() - 1).getWakeUp();

        LocalDate firstNight = potentialNight(periodStart);
        LocalDate lastNight = periodEnd.toLocalDate();
        if (lastNight.isBefore(firstNight)) {
            lastNight = firstNight;
        }

        return firstNight.datesUntil(lastNight.plusDays(1)).collect(Collectors.toList());
    }

    public static boolean overlapsNight(SleepingSession session, LocalDate night) {
        LocalDateTime windowStart = LocalDateTime.of(night, NIGHT_WINDOW_START);
        LocalDateTime windowEnd = LocalDateTime.of(night, NIGHT_WINDOW_END);
        return session.overlaps(windowStart, windowEnd);
    }

    public static Optional<SleepingSession> findNightSession(List<SleepingSession> sessions, LocalDate night) {
        return sessions.stream()
                .filter(session -> overlapsNight(session, night))
                .findFirst();
    }
}