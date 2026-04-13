val IS_CI = System.getenv("CI") == "true"

plugins {
	id("dev.kikugie.stonecutter")
	id("net.neoforged.moddev") version "2.0.141" apply false
	id("net.fabricmc.fabric-loom-remap") version "1.16-SNAPSHOT" apply false
	id("net.fabricmc.fabric-loom") version "1.16-SNAPSHOT" apply false
	id("net.fabricmc.fabric-loom-companion") version "1.16-SNAPSHOT" apply false
	id("net.minecraftforge.gradle") version "7.0.20" apply false
	id("net.minecraftforge.jarjar") version "0.2.3" apply false
	id("net.minecraftforge.renamer") version "1.0.15" apply false
}

if (IS_CI) stonecutter active null
else stonecutter active "1.16.5" /* [SC] DO NOT EDIT */