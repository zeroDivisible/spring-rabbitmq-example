import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.*

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{ISO8601} [%thread] %-5level %logger{35} - %msg%n"
    }
}

logger("jndi", WARN, ["CONSOLE"], additivity = false)
logger("org.aspectj", INFO, ["CONSOLE"], additivity = false)
logger("org.eclipse", WARN, ["CONSOLE"], additivity = false)
logger("org.hibernate", INFO, ["CONSOLE"], additivity = false)
logger("org.springframework", INFO, ["CONSOLE"], additivity = false)
logger("org.springframework.data", WARN, ["CONSOLE"], additivity = false)
logger("org.springframework.transaction", WARN, ["CONSOLE"], additivity = false)
logger("org.springframework.security", INFO, ["CONSOLE"], additivity = false)
logger("org.springframework.security.web", WARN, ["CONSOLE"], additivity = false)
logger("io.zerodi", DEBUG, ["CONSOLE"], additivity = false)


root(DEBUG, ["CONSOLE"])
