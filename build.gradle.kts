plugins {
    kotlin("jvm") version "1.7.22"
}

group = "com.hanbong"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val poiVersion = "5.2.4"
dependencies {
    implementation("org.apache.poi:poi:$poiVersion")
    implementation("org.apache.poi:poi-ooxml:$poiVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
