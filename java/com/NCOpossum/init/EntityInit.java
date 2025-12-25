package com.NCOpossum.init;

import com.NCOpossum.MainNCO;
import com.NCOpossum.entity.EntityNCOpossum;
import com.NCOpossum.util.NCReference;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit 
{
	public static void registerEntities()
	{
		//registerEntity("opossum", EntityNCOpossum.class, NCReference.ENTITY_OPOSSUM, 50, 13456423, 10237437);
		EntityRegistry.registerModEntity(new ResourceLocation(NCReference.MODID, "opossum"), EntityNCOpossum.class, "opossum", 50, MainNCO.instance, 64, 3, true, 13456423, 10237437);
		EntityRegistry.addSpawn(EntityNCOpossum.class, 10, 1, 1, EnumCreatureType.CREATURE, Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.SWAMPLAND, Biomes.BIRCH_FOREST_HILLS, Biomes.EXTREME_HILLS, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.COLD_TAIGA, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.REDWOOD_TAIGA);
	}
	
	/*private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int colour1, int colour2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(NCReference.MODID + ":" + name), entity, name, id, MainNCO.instance, 64, 3, true, colour1, colour2);
		
	}*/
}
