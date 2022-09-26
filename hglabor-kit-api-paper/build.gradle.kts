plugins {
    `java-script`
    `paper-script`
    `mod-properties-script`
    `shadow-script`
}

dependencies {
    implementation(project(":${rootProject.name}-common"))
}

ext {
    set("modConfigFile", "plugin.yml")
    set("loaderNames", "Paper")
    set("loaderSlug", "paper")
}
