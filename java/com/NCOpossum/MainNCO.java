package com.NCOpossum;

import com.NCOpossum.proxy.CommonProxy;
import com.NCOpossum.util.NCReference;
import com.NCOpossum.util.handlers.RegistryHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NCReference.MODID, name = NCReference.NAME, version = NCReference.VERSION)
public class MainNCO
{
	// Define mod id in a common place for everything to reference

	@Mod.Instance
	public static MainNCO instance;

	@SidedProxy(clientSide = NCReference.CLIENT, serverSide = NCReference.COMMON)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.preInitRegistries();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RegistryHandler.initRegistries();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		System.out.println("Post-Initialization");
	}

	public MainNCO()
	{

	}

}