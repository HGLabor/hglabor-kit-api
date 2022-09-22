plugins {
    id("java")
    id("org.quiltmc.loom") version "0.12.+"
}

group = "hglabor.de"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.parchmentmc.org")
}

dependencies {
    minecraft("com.mojang:minecraft:1.19.2")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.19.2:2022.09.18@zip")
    })
    modImplementation("org.quiltmc:quilt-loader:0.17.1-beta.4")
}
