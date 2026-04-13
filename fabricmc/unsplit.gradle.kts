plugins {
	multiloader
	id("net.fabricmc.fabric-loom-remap")
}

val commonJava: Configuration by configurations.creating {
	isCanBeResolved = true
}
val commonResources: Configuration by configurations.creating {
	isCanBeResolved = true
}

repositories {
	maven("https://libraries.minecraft.net") { name = "Mojang" }
	mavenCentral()
	exclusiveContent {
		forRepositories(
			maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
		)
		filter { includeGroup("org.parchmentmc.data") }
	}
	maven("https://maven.quiltmc.org/repository/release/") { name = "QuiltMC" }
	maven("https://maven.fabricmc.net/") { name = "FabricMC" }
}

loom {
	accessWidenerPath =
		common.project.file("../../src/main/resources/classtweakers/${commonMod.minecraft_version}-${mod.id}.classtweaker")

	mods {
		create("jingling_journeys") {
			sourceSet(sourceSets.main.get())
		}
	}

	runConfigs.configureEach {
		ideConfigGenerated(false)
	}

	runs {
		getByName("client") {
			client()
			configName = "Fabric Client"
		}
		getByName("server") {
			server()
			configName = "Fabric Server"
		}
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.propOrNull("parchment_mappings")?.let { parchmentVersion ->
			if (parchmentVersion != "") parchment("org.parchmentmc.data:parchment-${commonMod.minecraft_version}:$parchmentVersion@zip")
		}
	})

	modImplementation("net.fabricmc:fabric-loader:${commonMod.prop("fabric_loader_version")}")
	modApi("net.fabricmc.fabric-api:fabric-api:${commonMod.prop("fabric_api_version")}")

	include("net.fabricmc:sponge-mixin:${commonMod.prop("fabric_mixin_version")}")
	annotationProcessor("io.github.llamalad7:mixinextras-fabric:${commonMod.prop("mixinextras_version")}")
	include("io.github.llamalad7:mixinextras-fabric:${commonMod.prop("mixinextras_version")}")

	compileOnly(project(":common"))
	commonJava(project(":common", "commonJava"))
	commonResources(project(":common", "commonResources"))
}

if (stonecutter.eval(stonecutter.current.version, ">=1.17")) {
	fabricApi {
		configureDataGeneration() {
			client = true
			modId = mod.id
		}
	}
}

tasks {
	compileJava {
		dependsOn(commonJava)
		source(commonJava)
	}

	processResources {
		dependsOn(commonResources)
		from(commonResources)
	}

	jar {
		duplicatesStrategy = DuplicatesStrategy.WARN
	}
}