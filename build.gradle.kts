plugins {
    alias(libs.plugins.spotless)
}

repositories {
    jcenter()
}


spotless {
    format("styling") {
        target("docs/*.md")
        prettier().configFile(rootDir.absolutePath + "/.prettierrc.yml")
    }
}