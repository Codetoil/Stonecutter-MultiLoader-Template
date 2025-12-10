import org.slf4j.event.Level

plugins {
	`multiloader-loader`
	id("net.neoforged.moddev.legacyforge")
}

legacyForge {
	version = "${commonMod.minecraft_version}-${commonMod.prop("minecraftforge_version")}"
}

dependencies {

}

legacyForge {
	val at = project.file("build/resources/main/META-INF/accesstransformer.cfg");

	accessTransformers.from(at.absolutePath)
	validateAccessTransformers = true

	runs {
		register("client") {
			client()
			ideName = "MinecraftForge Client (${project.path})"
			logLevel = Level.TRACE
		}
		register("gameTestServer") {
			type = "gameTestServer"
			ideName = "MinecraftForge GameTestServer (${project.path})"
			logLevel = Level.TRACE
		}
		register("data") {
			data()
			ideName = "MinecraftForge Data (${project.path})"
			logLevel = Level.TRACE
		}
		register("server") {
			server()
			ideName = "MinecraftForge Server (${project.path})"
			logLevel = Level.TRACE
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

		val atFile =
			project(":common").file("src/main/resources/accesstransformers/accesstransformer-${commonMod.minecraft_version}.cfg")

		from(atFile.parentFile) {
			include(atFile.name)
			rename(atFile.name, "META-INF/accesstransformer.cfg")
			into("")
		}
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":legacy_minecraftforge:${commonMod.propOrNull("minecraft_version")}:processResources")
}