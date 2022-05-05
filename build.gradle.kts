plugins {
    alias(libs.plugins.spotless)
}

repositories {
    mavenCentral()
}


spotless {
    format("styling") {
        target("docs/*.md")
        prettier().configFile(rootDir.absolutePath + "/.prettierrc.yml")
    }
}