val IS_CI = System.getenv("CI") == "true"

plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.124" apply false
	id("net.neoforged.moddev.legacyforge") version "2.0.124" apply false
	id("org.quiltmc.loom.remap") version "1.14-SNAPSHOT" apply false
	id("net.minecraftforge.accesstransformers") version "5.0.2" apply false
	id("net.minecraftforge.gradle") version "7.0.0-beta.51" apply false
	id("net.minecraftforge.jarjar") version "0.2.3" apply false
}

if (IS_CI) stonecutter active null
else stonecutter active "1.21.11" /* [SC] DO NOT EDIT */