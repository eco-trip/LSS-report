plugins {
    alias(libs.plugins.spotless)
}

repositories {
    mavenCentral()
}

/*
spotless {
    format("styling") {
        target("src/*.md")
        prettier().configFile(rootDir.absolutePath + "/.prettierrc.yml")
    }
}
*/
