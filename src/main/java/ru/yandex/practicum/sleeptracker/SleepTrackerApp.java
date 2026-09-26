package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: java SleepTrackerApp <путь к файлу с логом сна>");
            return;
        }

        Path logFilePath = Path.of(args[0]);
        List<SleepingSession> sessions;
        try {
            sessions = SleepLogParser.parse(logFilePath);
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл с логом сна: " + e.getMessage());
            return;
        }

        List<AnalysisFunction> analysisFunctions = List.of(
                new SessionCountFunction(),
                new MinSessionDurationFunction(),
                new MaxSessionDurationFunction(),
                new AverageSessionDurationFunction(),
                new BadQualitySessionCountFunction(),
                new SleeplessNightsCountFunction(),
                new ChronotypeFunction()
        );

        analysisFunctions.stream()
                .map(function -> function.apply(sessions))
                .forEach(result -> System.out.println(result.getDescription() + ": " + result.getValue()));
    }
}