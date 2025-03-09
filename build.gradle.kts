plugins {
    id("org.openrewrite.build.recipe-library") version "latest.release"
}

group = "com.testdriven"
description = "Experiments with OpenRewrite"

//The bom version can also be set to a specific version or latest.release.
val rewriteVersion = "8.47.4"


dependencies {
    compileOnly("org.projectlombok:lombok:1.18.+")
    annotationProcessor("org.projectlombok:lombok:1.18.+")
    implementation(platform("org.openrewrite:rewrite-bom:${rewriteVersion}"))

    implementation("org.openrewrite:rewrite-java")
    runtimeOnly("org.openrewrite:rewrite-java-21")
    // Need to have a slf4j binding to see any output enabled from the parser.
    runtimeOnly("ch.qos.logback:logback-classic:1.2.+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:latest.release")
    testImplementation("org.junit.jupiter:junit-jupiter-params:latest.release")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:latest.release")

    testImplementation("org.openrewrite:rewrite-test")
    testImplementation("org.assertj:assertj-core:latest.release")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
