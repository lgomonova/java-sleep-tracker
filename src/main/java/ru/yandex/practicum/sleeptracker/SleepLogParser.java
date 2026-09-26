package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SleepLogParser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private SleepLogParser() {
    }

    public static List<SleepingSession> parse(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .filter(line -> !line.isBlank())
                    .map(SleepLogParser::parseLine)
                    .collect(Collectors.toList());
        }
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректная строка лога сна: " + line);
        }
        LocalDateTime sleepStart = LocalDateTime.parse(parts[0].trim(), DATE_TIME_FORMATTER);
        LocalDateTime wakeUp = LocalDateTime.parse(parts[1].trim(), DATE_TIME_FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2].trim());
        return new SleepingSession(sleepStart, wakeUp, quality);
    }
}