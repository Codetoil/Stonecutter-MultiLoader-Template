plugins {
	id("multiloader-common")
	id("org.quiltmc.loom")
}

loom {
	accessWidenerPath =
		common.project.file("../../src/main/resources/accesswideners/${commonMod.minecraft_version}-${mod.id}.accesswidener")
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.propOrNull("parchment_mappings")?.let { parchmentVersion ->
			if (parchmentVersion != "")
				parchment("org.parchmentmc.data:parchment-${commonMod.minecraft_version}:$parchmentVersion@zip")
		}
	})

	compileOnly("net.fabricmc:sponge-mixin:${commonMod.prop("fabric_mixin_version")}")

	"io.github.llamalad7:mixinextras-common:${commonMod.prop("mixinextras_version")}".let {
		compileOnly(it)
		annotationProcessor(it)
	}
}

val commonJava: Configuration by configurations.creating {
	isCanBeResolved = false
	isCanBeConsumed = true
}

val commonResources: Configuration by configurations.creating {
	isCanBeResolved = false
	isCanBeConsumed = true
}

artifacts {
	afterEvaluate {
		val mainSourceSet = sourceSets.main.get()
		mainSourceSet.java.sourceDirectories.files.forEach {
			add(commonJava.name, it)
		}
		mainSourceSet.resources.sourceDirectories.files.forEach {
			add(commonResources.name, it)
		}
	}
}