package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class SleepingSession {

    private final LocalDateTime sleepStart;
    private final LocalDateTime wakeUp;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime sleepStart, LocalDateTime wakeUp, SleepQuality quality) {
        if (sleepStart == null || wakeUp == null || quality == null) {
            throw new IllegalArgumentException("Параметры сессии сна не могут быть null");
        }
        if (!wakeUp.isAfter(sleepStart)) {
            throw new IllegalArgumentException("Время пробуждения должно быть позже времени засыпания");
        }
        this.sleepStart = sleepStart;
        this.wakeUp = wakeUp;
        this.quality = quality;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public LocalDateTime getWakeUp() {
        return wakeUp;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationMinutes() {
        return Duration.between(sleepStart, wakeUp).toMinutes();
    }

    public boolean overlaps(LocalDateTime intervalStart, LocalDateTime intervalEnd) {
        return sleepStart.isBefore(intervalEnd) && wakeUp.isAfter(intervalStart);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SleepingSession)) {
            return false;
        }
        SleepingSession that = (SleepingSession) other;
        return sleepStart.equals(that.sleepStart)
                && wakeUp.equals(that.wakeUp)
                && quality == that.quality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sleepStart, wakeUp, quality);
    }

    @Override
    public String toString() {
        return "SleepingSession{" +
                "sleepStart=" + sleepStart +
                ", wakeUp=" + wakeUp +
                ", quality=" + quality +
                '}';
    }
}