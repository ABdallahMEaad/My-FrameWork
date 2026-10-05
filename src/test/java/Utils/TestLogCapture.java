package Utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Collects everything logged by the current test (per thread) in memory,
 * so the log of a FAILED test can be attached to the Allure report.
 *
 * Usage: start() before the test, getLog() after it, stop() at the end.
 */
public final class TestLogCapture {

    private static final String APPENDER_NAME = "TestLogCaptureAppender";

    private static final ThreadLocal<StringBuilder> BUFFER = new ThreadLocal<>();

    private static boolean installed = false;

    private TestLogCapture() {
    }

    public static void start() {
        install();
        BUFFER.set(new StringBuilder());
    }

    public static String getLog() {
        StringBuilder buffer = BUFFER.get();
        return buffer == null ? "" : buffer.toString();
    }

    public static void stop() {
        BUFFER.remove();
    }

    private static synchronized void install() {

        if (installed) {
            return;
        }

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        Appender appender = new BufferAppender();
        appender.start();

        config.addAppender(appender);
        config.getRootLogger().addAppender(appender, Level.DEBUG, null);
        context.updateLoggers();

        installed = true;
    }

    private static class BufferAppender extends AbstractAppender {

        private static final DateTimeFormatter TIME =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                        .withZone(ZoneId.systemDefault());

        BufferAppender() {
            super(APPENDER_NAME, null, null, true, Property.EMPTY_ARRAY);
        }

        @Override
        public void append(LogEvent event) {

            StringBuilder buffer = BUFFER.get();

            if (buffer == null) {
                return;
            }

            String loggerName = event.getLoggerName();
            String shortName = loggerName.substring(loggerName.lastIndexOf('.') + 1);

            buffer.append(TIME.format(Instant.ofEpochMilli(event.getTimeMillis())))
                    .append(' ')
                    .append(String.format("%-5s", event.getLevel().name()))
                    .append(' ')
                    .append(shortName)
                    .append(" - ")
                    .append(event.getMessage().getFormattedMessage())
                    .append(System.lineSeparator());

            Throwable thrown = event.getThrown();

            if (thrown != null) {
                StringWriter stackTrace = new StringWriter();
                thrown.printStackTrace(new PrintWriter(stackTrace));
                buffer.append(stackTrace);
            }
        }
    }
}
