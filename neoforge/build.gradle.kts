plugins {
	multiloader
	id("net.neoforged.moddev")
	id("net.fabricmc.fabric-loom-companion")
}

val commonJava: Configuration by configurations.creating {
	isCanBeResolved = true
}
val commonResources: Configuration by configurations.creating {
	isCanBeResolved = true
}

repositories {
	mavenCentral()
	maven("https://maven.fabricmc.net/") { name = "FabricMC" }
}

neoForge {
	enable {
		version = commonMod.prop("neoforge_version")
	}
}

dependencies {
	jarJar("net.fabricmc:sponge-mixin:${commonMod.prop("fabric_mixin_version")}")
	jarJar("io.github.llamalad7:mixinextras-neoforge:${commonMod.prop("mixinextras_version")}")

	compileOnly(project(":common"))
	commonJava(project(":common", "commonJava"))
	commonResources(project(":common", "commonResources"))
	commonJava(project(":common", "commonClientJava"))
	commonResources(project(":common", "commonClientResources"))
}

neoForge {
	val at = project.file("build/resources/main/META-INF/accesstransformer.cfg");

	accessTransformers.from(at.absolutePath)
	validateAccessTransformers = true

	runs {
		register("client") {
			client()
			ideName = "NeoForge Client (${project.path})"
		}
		if (stonecutter.eval(stonecutter.current.version, ">=1.21.4")) {
			register("clientData") {
				clientData()
				ideName = "NeoForge Client Data (${project.path})"
			}
			register("serverData") {
				serverData()
				ideName = "NeoForge Server Data (${project.path})"
			}
		} else {
			register("data") {
				data()
				ideName = "NeoForge Data (${project.path})"
			}
		}
		register("server") {
			server()
			ideName = "NeoForge Server (${project.path})"
		}
	}

	parchment {
		commonMod.propOrNull("parchment_mappings")?.let {
			mappingsVersion = it
			minecraftVersion = if (it != "") commonMod.minecraft_version else ""
		}
	}

	mods {
		register(commonMod.id) {
			sourceSet(sourceSets.main.get())
		}
	}
}

sourceSets.main {
	resources.srcDir("src/generated/resources")
}

tasks {
	compileJava {
		dependsOn(commonJava)
		source(commonJava)
	}

	processResources {
		exclude("${mod.id}.accesswidener")
		dependsOn(commonResources)

		val atFile = project(":common").file("src/main/resources/accesstransformers/accesstransformer-${commonMod.minecraft_version}.cfg")

		from(commonResources, atFile.parentFile) {
			include(atFile.name)
			rename(atFile.name, "META-INF/accesstransformer.cfg")
			into("")
		}
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":neoforge:${commonMod.propOrNull("minecraft_version")}:processResources")
}