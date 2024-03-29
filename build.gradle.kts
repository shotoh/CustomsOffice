import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.0"
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
}

group = "io.github.shotoh.customsoffice"
version = "1.0.0-SNAPSHOT"
description = "Customs Office plugin"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://maven.citizensnpcs.co/repo")
    maven("https://maven.enginehub.org/repo")
    maven("https://repo.rpkit.com/repository/maven-releases")
}

dependencies {
    paperweight.paperDevBundle("1.19.3-R0.1-SNAPSHOT")
    // paperweight.devBundle("com.example.paperfork", "1.19.3-R0.1-SNAPSHOT")
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT") {
        exclude("*", "*")
    }
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.8-SNAPSHOT")
    implementation("com.rpkit:rpk-core-bukkit:2.4.1:all")
    implementation("com.rpkit:rpk-player-lib-bukkit:2.4.1:all")
    implementation("com.rpkit:rpk-character-lib-bukkit:2.4.1:all")
    implementation("com.rpkit:rpk-economy-lib-bukkit:2.4.1:all")
    implementation("com.rpkit:rpk-economy-bukkit:2.4.1:all")
}

tasks {
    // Configure reobfJar to run when invoking the build task
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    /*
    reobfJar {
      // This is an example of how you might change the output location for reobfJar. It's recommended not to do this
      // for a variety of reasons, however it's asked frequently enough that an example of how to do it is included here.
      outputJar.set(layout.buildDirectory.file("libs/PaperweightTestPlugin-${project.version}.jar"))
    }
     */
}

// Configure plugin.yml generation
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "io.github.shotoh.customsoffice.CustomsOffice"
    apiVersion = "1.19"
    authors = listOf("shotoh")
    commands {
        register("customs") {
            description = "Main command for Customs Office"
        }
    }
    depend = listOf("Citizens", "WorldGuard", "rpk-economy-bukkit")
}
