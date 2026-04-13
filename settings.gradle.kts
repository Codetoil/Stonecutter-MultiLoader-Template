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
	id("dev.kikugie.stonecutter") version "0.9.1-beta.5"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

val commonSplitVersions =
	providers.gradleProperty("stonecutter_enabled_common_split_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val commonUnsplitVersions =
	providers.gradleProperty("stonecutter_enabled_common_unsplit_versions").orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricMCSplitVersions =
	providers.gradleProperty("stonecutter_enabled_fabricmc_split_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val fabricMCUnsplitVersions =
	providers.gradleProperty("stonecutter_enabled_fabricmc_unsplit_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val minecraftForgeNormalVersions =
	providers.gradleProperty("stonecutter_enabled_minecraftforge_normal_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val minecraftForgeRenamerVersions =
	providers.gradleProperty("stonecutter_enabled_minecraftforge_renamer_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
val neoForgeVersions =
	providers.gradleProperty("stonecutter_enabled_neoforge_versions").orNull?.split(",")?.map { it.trim() }
		?: emptyList()
stonecutter {
	kotlinController = true
	centralScript = "build.gradle.kts"

	create(rootProject) {
		versions(*(commonSplitVersions + commonUnsplitVersions).toTypedArray())

		branch("common") {
			versions(*commonSplitVersions.toTypedArray()).buildscript("split.gradle.kts")
			versions(*commonUnsplitVersions.toTypedArray()).buildscript("unsplit.gradle.kts")
		}

		branch("fabricmc") {
			versions(*fabricMCSplitVersions.toTypedArray()).buildscript("split.gradle.kts")
			versions(*fabricMCUnsplitVersions.toTypedArray()).buildscript("unsplit.gradle.kts")
		}

		branch("minecraftforge") {
			versions(*minecraftForgeNormalVersions.toTypedArray()).buildscript("normal.gradle.kts")
			versions(*minecraftForgeRenamerVersions.toTypedArray()).buildscript("renamer.gradle.kts")
		}

		branch("neoforge") {
			versions(*neoForgeVersions.toTypedArray()).buildscript("build.gradle.kts")
		}
	}
}

rootProject.name = "Stonecutter-MultiLoader-Template-Java21"

