pluginManagement {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		maven("https://maven.quiltmc.org/repository/release/") { name = "QuiltMC" }
		maven("https://maven.fabricmc.net/") { name = "FabricMC" }
		maven("https://maven.neoforged.net/releases") { name = "NeoForge" }
		maven("https://maven.minecraftforge.net") { name = "MinecraftForge" }
		maven("https://maven.kikugie.dev/snapshots")
		maven("https://maven.kikugie.dev/releases")
		// Temporary until Quilt Loom 1.14.2 gets fixed
		maven("./temp-quilt-loom-1.14.2-fix")
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.8.2"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val commonVersions =
	providers.gradleProperty("stonecutter_enabled_common_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricmcVersions =
	providers.gradleProperty("stonecutter_enabled_fabricmc_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val fg7MinecraftForgeVersions =
	providers.gradleProperty("stonecutter_enabled_fg7_minecraftforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val legacyMDGMinecraftForgeVersions =
	providers.gradleProperty("stonecutter_enabled_legacy_mdg_minecraftforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val neoForgeVersions =
	providers.gradleProperty("stonecutter_enabled_neoforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val quiltmcVersions =
	providers.gradleProperty("stonecutter_enabled_quiltmc_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
stonecutter {
	kotlinController = true
	centralScript = "build.gradle.kts"

	create(rootProject) {
		versions(*commonVersions.toTypedArray())

		branch("common") {
			versions(*commonVersions.toTypedArray())
		}

		branch("fabricmc") {
			versions(*fabricmcVersions.toTypedArray())
		}

		branch("minecraftforge") {
			versions(*fg7MinecraftForgeVersions.toTypedArray()).buildscript("fg7.gradle.kts")
			versions(*legacyMDGMinecraftForgeVersions.toTypedArray()).buildscript("legacy_mdg.gradle.kts")
		}

		branch("neoforge") {
			versions(*neoForgeVersions.toTypedArray())
		}

		branch("quiltmc") {
			versions(*quiltmcVersions.toTypedArray())
		}
	}
}

rootProject.name = "Stonecutter-MultiLoader-Template-Java21"

