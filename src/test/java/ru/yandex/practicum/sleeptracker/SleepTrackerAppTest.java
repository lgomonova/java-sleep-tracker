package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SleepTrackerAppTest {

    @Test
    void mainRunsWithoutErrorsOnValidLogFile(@TempDir Path tempDir) throws IOException {
        Path logFile = tempDir.resolve("sleep-log.txt");
        Files.write(logFile, List.of(
                "01.10.25 22:15;02.10.25 08:00;GOOD",
                "02.10.25 23:00;03.10.25 08:00;NORMAL",
                "03.10.25 14:30;03.10.25 15:20;NORMAL",
                "03.10.25 23:30;04.10.25 06:20;BAD"
        ));

        assertDoesNotThrow(() -> SleepTrackerApp.main(new String[]{logFile.toString()}));
    }

    @Test
    void logFileIsParsedIntoExpectedNumberOfSessions() throws IOException {
        Path logFile = Files.createTempFile("sleep-log", ".txt");
        try {
            Files.write(logFile, List.of(
                    "01.10.25 22:15;02.10.25 08:00;GOOD",
                    "02.10.25 23:00;03.10.25 08:00;NORMAL"
            ));

            List<SleepingSession> sessions = SleepLogParser.parse(logFile);

            assertEquals(2, sessions.size());
        } finally {
            Files.deleteIfExists(logFile);
        }
    }
}