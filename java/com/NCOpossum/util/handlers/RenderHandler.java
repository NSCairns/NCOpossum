package com.NCOpossum.util.handlers;

import com.NCOpossum.entity.EntityNCOpossum;
import com.NCOpossum.entity.render.RenderNCOpossum;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class RenderHandler
{
	public static void registerEntityRenders()
	{
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			RenderingRegistry.registerEntityRenderingHandler(EntityNCOpossum.class,
					new IRenderFactory<EntityNCOpossum>()

					{
						@Override
						public Render<? super EntityNCOpossum> createRenderFor(RenderManager manager)
						{
							return new RenderNCOpossum(manager);
						}
					});
		}
	}
}
