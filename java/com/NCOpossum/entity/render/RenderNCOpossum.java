package com.NCOpossum.entity.render;

import com.NCOpossum.entity.EntityNCOpossum;
import com.NCOpossum.entity.model.ModelNCOpossum;
import com.NCOpossum.util.NCReference;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderNCOpossum extends RenderLiving<EntityNCOpossum> 
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(NCReference.MODID, "textures/entity/opossum.png");
	
    public RenderNCOpossum(RenderManager renderManager) {
        super(renderManager, new ModelNCOpossum(), 0.5F); // Adjust shadow size if necessary
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityNCOpossum entity) {
        return TEXTURE; // Return the texture for your entity
    }
    
    public static void registerRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityNCOpossum.class, RenderNCOpossum::new);
    }
    
    @Override
    protected void applyRotations(EntityNCOpossum entityLiving, float p_77043_2_, float rotationYaw,
    		float partialTicks) {
    	// TODO Auto-generated method stub
    	super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }

    @Override
    public void doRender(EntityNCOpossum entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	this.bindTexture(TEXTURE);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
}
    