plugins {
	id("java-library")
	id("idea")
}

version = "${loader}-${commonMod.version}+mc${stonecutterBuild.current.version}"

base {
	archivesName = commonMod.id
}

java {
	println("Java ${commonProject.prop("java.version")}")
	toolchain.languageVersion = JavaLanguageVersion.of(commonProject.prop("java.version")!!)
	// withSourcesJar()
	// withJavadocJar()
}

repositories {
	mavenCentral()
	exclusiveContent {
		forRepository {
			maven("https://repo.spongepowered.org/repository/maven-public") { name = "Sponge" }
		}
		filter { includeGroupAndSubgroups("org.spongepowered") }
	}
	exclusiveContent {
		forRepositories(
			maven("https://maven.parchmentmc.org") { name = "ParchmentMC" },
			maven("https://maven.neoforged.net/releases") { name = "NeoForge" }
		)
		filter { includeGroup("org.parchmentmc.data") }
	}
	maven("https://maven.quiltmc.org/repository/release/") { name = "QuiltMC" }
	maven("https://maven.fabricmc.net/") { name = "FabricMC" }
	maven("https://maven.neoforged.net/releases") { name = "NeoForge" }
	maven("https://maven.minecraftforge.net") { name = "MinecraftForge" }
}

tasks {
	processResources {
		val expandProps = mapOf(
			"java_version" to commonMod.propOrNull("java.version"),
			"version" to commonMod.version,
			"group" to commonMod.group,
			"mod_name" to commonMod.name,
			"mod_author" to commonMod.author,
			"mod_id" to commonMod.id,
			"license" to commonMod.license,
			"description" to commonMod.description,
			"credits" to commonMod.credits,
			"minecraft_version" to commonMod.propOrNull("minecraft_version"),
			"minecraft_version_range" to commonMod.propOrNull("minecraft_version_range"),
			"fabric_api_version" to commonMod.propOrNull("fabric_api_version"),
			"fabric_loader_version" to commonMod.propOrNull("fabric_loader_version"),
			"neoforge_version" to commonMod.propOrNull("neoforge_version"),
			"neoforge_version_range" to commonMod.propOrNull("neoforge_version_range"),
			"neoforge_loader_version_range" to commonMod.propOrNull("neoforge_loader_version_range"),
			"minecraftforge_version" to commonMod.propOrNull("minecraftforge_version"),
			"minecraftforge_version_range" to commonMod.propOrNull("minecraftforge_version_range"),
			"minecraftforge_eventbus_validator_version" to
				commonMod.propOrNull("minecraftforge_eventbus_validator_version"),
			"quilted_fabric_api_version" to commonMod.propOrNull("quilted_fabric_api_version"),
			"quilt_loader_version" to commonMod.propOrNull("quilt_loader_version"),
		).filterValues { it?.isNotEmpty() == true }.mapValues { (_, v) -> v!! }

		val jsonExpandProps = expandProps.mapValues { (_, v) -> v.replace("\n", "\\\\n") }

		filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
			expand(expandProps)
		}

		filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "quilt.mod.json", "*.mixins.json")) {
			expand(jsonExpandProps)
		}

		inputs.properties(expandProps)
	}
}

tasks.named("processResources") {
	dependsOn(":common:${commonMod.propOrNull("minecraft_version")}:stonecutterGenerate")
}
