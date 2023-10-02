plugins {
    java
    jacoco
    antlr
}

group = "edu.postech.csed332"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.9"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    //TODO: add an extra dependency for ANTLR4 (version 4.13.1)
    antlr("org.antlr:antlr4:4.13.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        csv.required.set(true)
    }
}

tasks.generateGrammarSource {
    //TODO: pass the argument -visitor to this task.
    maxHeapSize = "64m"
    arguments = arguments + listOf("-visitor", "-long-messages")
}
