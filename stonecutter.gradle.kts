val IS_CI = System.getenv("CI") == "true"

plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.116" apply false
	id("org.quiltmc.loom") version "1.11.1" apply false
	id("net.minecraftforge.accesstransformers") version "5.0.1" apply false
	id("net.minecraftforge.gradle") version "7.0.0-beta.46" apply false
	id("net.minecraftforge.jarjar") version "0.2.3" apply false
}

if (IS_CI) stonecutter active null
else stonecutter active "1.21.10" /* [SC] DO NOT EDIT */