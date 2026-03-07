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
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.8.3"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val commonVersions =
	providers.gradleProperty("stonecutter_enabled_common_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricmcVersions =
	providers.gradleProperty("stonecutter_enabled_fabricmc_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val minecraftForgeVersions =
	providers.gradleProperty("stonecutter_enabled_minecraftforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val minecraftForgeRenamerVersions =
	providers.gradleProperty("stonecutter_enabled_minecraftforge_renamer_versions").orNull?.split(",")?.map { it.trim() }
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
			versions(*minecraftForgeVersions.toTypedArray())
			versions(*minecraftForgeRenamerVersions.toTypedArray()).buildscript("renamer.gradle.kts")
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

