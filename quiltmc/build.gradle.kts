plugins {
	`multiloader-loader`
	id("org.quiltmc.loom")
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.propOrNull("parchment")?.let { parchmentVersion ->
			if (parchmentVersion != "") parchment("org.parchmentmc.data:parchment-${commonMod.minecraft_version}:$parchmentVersion@zip")
		}
	})

	modImplementation("org.quiltmc:quilt-loader:${commonMod.prop("quilt_loader_version")}")
	modApi("org.quiltmc:qsl:${commonMod.prop("qsl_version")}")
	commonMod.propOrNull("quilted_fabric_api_version")?.let {
		modApi("org.quiltmc.quilted-fabric-api:quilted-fabric-api:${it}")
	}
}

loom {
	accessWidenerPath =
		common.project.file("../../src/main/resources/accesswideners/${commonMod.minecraft_version}-${mod.id}.accesswidener")

	runs {
		getByName("client") {
			client()
			configName = "Quilt Client"
			ideConfigGenerated(true)
		}
		getByName("server") {
			server()
			configName = "Quilt Server"
			ideConfigGenerated(true)
		}
	}

	mods {
		// This should match your mod id.
		create(mod.id) {
			// Tell Loom about each source set used by your mod here. This ensures that your mod's classes are properly transformed by Loader.
			sourceSet("main")
			// If you shade (directly include classes, not JiJ) a dependency into your mod, include it here using one of these methods:
			// dependency("com.example.shadowedmod:1.2.3")
			// configuration("exampleShadedConfigurationName")
		}
	}

	mixin {
		defaultRefmapName = "${mod.id}.refmap.json"
	}
}

tasks.named<ProcessResources>("processResources") {
	val awFile = project(":common").file("src/main/resources/accesswideners/${commonMod.minecraft_version}-${mod.id}.accesswidener")

	from(awFile.parentFile) {
		include(awFile.name)
		rename(awFile.name, "${mod.id}.accesswidener")
		into("")
	}
}