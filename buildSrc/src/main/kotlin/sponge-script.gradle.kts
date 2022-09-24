import org.spongepowered.gradle.plugin.config.PluginLoaders

plugins {
    java
    id("org.spongepowered.gradle.plugin")
}

sponge {
    apiVersion("7.2.0")
    license(licenseName)
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin(rootProject.name) {
        displayName(projectDisplayName)
        entrypoint("de.hglabor.kitapi.sponge.KitApiPlugin")
        links {
            homepage(githubUrl)
            source(githubUrl)
            issues(issuesUrl)
        }
        contributor(author) {
            description("Developer")
        }
        dependency("spongeapi") {
            optional(false)
        }
    }
}
