plugins {
    `java-script`
    `paper-script`
    `mod-properties-script`
    `shadow-script`
}

dependencies {
    implementation(project(":${rootProject.name}-common", configuration = "namedElements"))
}

ext {
    set("modConfigFile", "plugin.yml")
    set("loaderNames", "Paper")
    set("loaderSlug", "paper")
}
