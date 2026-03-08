val IS_CI = System.getenv("CI") == "true"

plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.140" apply false
	id("org.quiltmc.loom") version "1.15.1" apply false
	id("org.quiltmc.loom.no_remap") version "1.15.1" apply false
	id("net.minecraftforge.accesstransformers") version "5.0.3" apply false
	id("net.minecraftforge.gradle") version "7.0.13" apply false
	id("net.minecraftforge.jarjar") version "0.2.3" apply false
	id("net.minecraftforge.renamer") version "1.0.2" apply false
}

if (IS_CI) stonecutter active null
else stonecutter active "1.16.5" /* [SC] DO NOT EDIT */