plugins {
	`multiloader-loader`
	id("net.neoforged.moddev")
}

neoForge {
	enable {
		version = commonMod.prop("neoforge_version")
	}
}

dependencies {

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
	processResources {
		exclude("${mod.id}.accesswidener")

		val atFile = project(":common").file("src/main/resources/accesstransformers/accesstransformer-${commonMod.minecraft_version}.cfg")

		from(atFile.parentFile) {
			include(atFile.name)
			rename(atFile.name, "META-INF/accesstransformer.cfg")
			into("")
		}
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":neoforge:${commonMod.propOrNull("minecraft_version")}:processResources")
}