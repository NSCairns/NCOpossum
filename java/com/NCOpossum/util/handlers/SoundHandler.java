package com.NCOpossum.util.handlers;

import com.NCOpossum.util.NCReference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler
{
	public static SoundEvent ENTITY_OPOSSUM_AMBIENT, ENTITY_OPOSSUM_HURT, ENTITY_OPOSSUM_DEATH, ENTITY_OPOSSUM_EAT;
	
	public static void registerSounds() 
	{
		ENTITY_OPOSSUM_AMBIENT = registerSound("entity.opossum.ambient");
		ENTITY_OPOSSUM_HURT = registerSound("entity.opossum.hurt");
		ENTITY_OPOSSUM_DEATH = registerSound("entity.opossum.death");
		ENTITY_OPOSSUM_EAT = registerSound("entity.opossum.eat");
	}
	
	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(NCReference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
