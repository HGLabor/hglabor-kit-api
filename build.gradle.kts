plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "hglabor.de"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
}

bukkit {
    name = "kit-api"
    apiVersion = "1.19"
    authors = listOf(
        "MooZiii",
        "NoRiskk"
    )
    main = "de.hglabor.kitapi.KitApi"
    version = project.version.toString()
}