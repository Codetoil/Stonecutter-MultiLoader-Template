plugins {
	`multiloader-loader`
	id("org.quiltmc.loom")
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
	if (commonMod.propOrNull("mcp_version") != null) {
		mappings("de.oceanlabs.mcp:mcp_${commonMod.propOrNull("mcp_branch")}:${commonMod.propOrNull("mcp_version")}")
	} else {
		mappings(loom.layered {
			officialMojangMappings()
			commonMod.propOrNull("parchment_mappings")?.let { parchmentVersion ->
				if (parchmentVersion != "") parchment("org.parchmentmc.data:parchment-${commonMod.minecraft_version}:$parchmentVersion@zip")
			}
		})
	}

	modImplementation("net.fabricmc:fabric-loader:${commonMod.prop("fabric_loader_version")}")
	modApi("net.fabricmc.fabric-api:fabric-api:${commonMod.prop("fabric_api_version")}")
}

loom {
	accessWidenerPath =
		common.project.file("../../src/main/resources/accesswideners/${commonMod.minecraft_version}-${mod.id}.accesswidener")

	runs {
		getByName("client") {
			client()
			configName = "Fabric Client"
			ideConfigGenerated(true)
		}
		getByName("server") {
			server()
			configName = "Fabric Server"
			ideConfigGenerated(true)
		}
	}
}

if (stonecutter.eval(stonecutter.current.version, ">=1.17")) {
	fabricApi {
		configureDataGeneration() {
			client = true
		}
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