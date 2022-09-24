plugins {
    java
    id("com.github.johnrengelman.shadow")
}

tasks {
    shadowJar {
        dependencies {
            exclude {
                it.moduleGroup != "de.hglabor"
            }
        }
    }
}
