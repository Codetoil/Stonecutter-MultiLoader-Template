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
		// Temporary until https://github.com/QuiltMC/quilt-loom/pull/59 is merged
		maven("./temp-quilt-loom-1.14")
	}
}

plugins {
	id("dev.kikugie.stonecutter") version "0.7.11"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val commonVersions =
	providers.gradleProperty("stonecutter_enabled_common_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricmcVersions =
	providers.gradleProperty("stonecutter_enabled_fabricmc_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val minecraftforgeVersions =
	providers.gradleProperty("stonecutter_enabled_minecraftforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val legacyMinecraftForgeVersions =
	providers.gradleProperty("stonecutter_enabled_legacy_minecraftforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val neoforgeVersions =
	providers.gradleProperty("stonecutter_enabled_neoforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val legacyNeoForgeVersions =
	providers.gradleProperty("stonecutter_enabled_legacy_neoforge_versions").orNull?.split(",")?.map { it.trim() }
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
			versions(*minecraftforgeVersions.toTypedArray())
			versions(*legacyMinecraftForgeVersions.toTypedArray()).buildscript("legacy.gradle.kts")
		}

		branch("neoforge") {
			versions(*neoforgeVersions.toTypedArray())
			//versions(*legacyNeoForgeVersions.toTypedArray()).buildscript("legacy.gradle.kts")
		}

		branch("quiltmc") {
			versions(*quiltmcVersions.toTypedArray())
		}
	}
}

rootProject.name = "Stonecutter-MultiLoader-Template"

