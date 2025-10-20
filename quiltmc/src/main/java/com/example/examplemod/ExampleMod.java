package com.example.examplemod;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class ExampleMod implements ModInitializer
{

	@Override
	public void onInitialize(ModContainer mod) {

		// This method is invoked by the Quilt mod loader when it is ready
		// to load your mod. You can access Quilt and Common code in this
		// project.

		// Use Quilt to bootstrap the Common mod.
		Constants.LOG.info("Hello Quilt world!");
		CommonClass.init();
	}
}
