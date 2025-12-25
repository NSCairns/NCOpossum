package com.NCOpossum.entity.model;

import org.lwjgl.opengl.GL11;

import com.NCOpossum.entity.EntityNCOpossum;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

public class ModelNCOpossum extends ModelBase
{

	// Declare all the parts of the model
	public ModelRenderer Body;
	public ModelRenderer RightFrontLeg;
	public ModelRenderer LeftRearLeg;
	public ModelRenderer RightRearLeg;
	public ModelRenderer LeftFrontLeg;
	public ModelRenderer Tail;
	public ModelRenderer Head;
	public ModelRenderer Nose;
	public ModelRenderer LowerJaw;
	final float AnimPI = (float) Math.PI;

	public ModelNCOpossum()
	{

		textureWidth = 128;
		textureHeight = 32;

		// Body
		Body = new ModelRenderer(this, 32, 10);
		Body.addBox(-5.0F, -4.0F, -7.0F, 10, 8, 14);
		Body.setRotationPoint(0.0F, 16.0F, 1.0F);

		// RightFrontLeg
		RightFrontLeg = new ModelRenderer(this, 117, 0);
		RightFrontLeg.addBox(-2.0F, -2.5F, 0.5F, 4, 4, 1);
		RightFrontLeg.setRotationPoint(-3.0F, 22.5F, -5.5F);
		RightFrontLeg.addChild(new ModelRenderer(this, 85, 5).addBox(-2.0F, -0.5F, -2.5F, 4, 2, 3));

		// LeftRearLeg
		LeftRearLeg = new ModelRenderer(this, 87, 0);
		LeftRearLeg.addBox(-2.0F, -2.5F, 0.5F, 4, 4, 1);
		LeftRearLeg.setRotationPoint(3.0F, 22.5F, 6.5F);
		LeftRearLeg.addChild(new ModelRenderer(this, 71, 5).addBox(-2.0F, -0.5F, -2.5F, 4, 2, 3));

		// RightRearLeg
		RightRearLeg = new ModelRenderer(this, 113, 5);
		RightRearLeg.addBox(-2.0F, -0.5F, -2.5F, 4, 2, 3);
		RightRearLeg.setRotationPoint(-3.0F, 22.5F, 6.5F);
		RightRearLeg.addChild(new ModelRenderer(this, 97, 0).addBox(-2.0F, -2.5F, 0.5F, 4, 4, 1));

		// LeftFrontLeg
		LeftFrontLeg = new ModelRenderer(this, 99, 5);
		LeftFrontLeg.addBox(-2.0F, -0.5F, -2.5F, 4, 2, 3);
		LeftFrontLeg.setRotationPoint(3.0F, 22.5F, -5.5F);
		LeftFrontLeg.addChild(new ModelRenderer(this, 107, 0).addBox(-2.0F, -2.5F, 0.5F, 4, 4, 1));

		// Tail
		Tail = new ModelRenderer(this, 5, 21);
		Tail.addBox(-1.0F, -1.0F, 4.8333F, 2, 3, 8);
		Tail.setRotationPoint(0.0F, 18.0F, 18.1667F);
		Tail.addChild(new ModelRenderer(this, 0, 17).addBox(-2.0F, -2.0F, -6.1667F, 4, 4, 11));
		Tail.addChild(new ModelRenderer(this, 12, 8).addBox(-3.0F, -3.0F, -10.1667F, 6, 5, 4));

		// Head
		Head = new ModelRenderer(this, 80, 20);
		Head.addBox(-4.0F, -1.5F, -2.25F, 8, 7, 5);
		Head.setRotationPoint(0.0F, 14.5F, -8.75F);
		Head.addChild(new ModelRenderer(this, 106, 17).addBox(-3.0F, 0.5F, -7.25F, 6, 3, 5));
		Head.addChild(new ModelRenderer(this, 80, 16).addBox(-6.0F, -3.5F, 1.75F, 3, 3, 1));
		Head.addChild(new ModelRenderer(this, 88, 16).addBox(3.0F, -3.5F, 1.75F, 3, 3, 1));

		LowerJaw = new ModelRenderer(this, 106, 25);
		LowerJaw.addBox(-3.0F, -1.0F, -2.5F, 6, 2, 5);
		LowerJaw.setRotationPoint(0.0F, 19.0F, -13.5F);
		Head.addChild(LowerJaw);

		// Nose
		Nose = new ModelRenderer(this, 122, 14);
		Nose.addBox(-1.0F, -1.0F, -0.5F, 2, 2, 1);
		Nose.setRotationPoint(0.0F, 16.0F, -16.5F); // Position nose relative to the head
		Head.addChild(Nose); // Make nose follow the head

	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float f5)
	{

		// Render the body parts
		this.Body.render(f5);
		this.RightFrontLeg.render(f5);
		this.LeftFrontLeg.render(f5);
		this.RightRearLeg.render(f5);
		this.LeftRearLeg.render(f5);
		this.Tail.render(f5);
		this.Head.render(f5);

	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale, Entity entity)
	{
		EntityNCOpossum opossum = (EntityNCOpossum) entity;
		// When opossum is not moving, no leg movement, return to idle pose

		float speed = 0.5F; // Default speed factor
		float factor = 0.4F;

		this.LowerJaw.offsetX = this.Head.offsetX + 0F;
		this.LowerJaw.offsetY = this.Head.offsetY - 0.905F;
		this.LowerJaw.offsetZ = this.Head.offsetZ + 0.549F;

		this.Nose.offsetX = this.Head.offsetX + 0F;
		this.Nose.offsetY = this.Head.offsetY - 0.95F;
		this.Nose.offsetZ = this.Head.offsetZ + 0.55F;

		this.Tail.rotateAngleY = MathHelper.cos(ageInTicks * 0.25F) * 0.1F;

		this.Head.rotateAngleX = MathHelper.cos(ageInTicks * 0.1F) * 0.1F;
		this.Head.rotateAngleY = -netHeadYaw * 0.017453292F;

		this.Nose.rotateAngleX = MathHelper.sin(ageInTicks * 0.5F) * 0.1F; // Nose twitching back and forth

		// Attach the held item to the tip of the head
		ItemStack heldItem = opossum.getHeldItemMainhand();

		float ANGLEX = this.Head.rotateAngleX;
		float ANGLEY = this.Head.rotateAngleY;
		float ANGLEZ = this.Head.rotateAngleZ;

		if (heldItem != null && !heldItem.isEmpty())
		{ 	
			
			GL11.glPushMatrix();

			float mouthOffsetX = 0.0F; // Horizontal offset of the item relative to the mouth
	        float mouthOffsetY = 1.2F; // Vertical offset of the item relative to the mouth
	        float mouthOffsetZ = -1.0F; // Depth offset of the item relative to the mouth

	        // Translate the item to the mouth's relative position (local space)
	        GL11.glTranslatef(mouthOffsetX, mouthOffsetY, mouthOffsetZ);

	        // Apply head's rotation to the item
	        // Rotation based on head's pitch (X axis) and yaw (Y axis)
	        GL11.glRotatef((float)(ANGLEZ * 180 / Math.PI), 0.0F, 0.0F, 1.0F); // Rotate around Z-axis (yaw)
	        GL11.glRotatef((float)(ANGLEX * 180 / Math.PI), 1.0F, 0.0F, 0.0F); // Rotate around X-axis (pitch)
	        GL11.glRotatef((float)(ANGLEY * 180 / Math.PI), 0.0F, 1.0F, 0.0F); // Rotate around Y-axis (roll)

	        GL11.glTranslatef(-ANGLEY / 2.5f, ANGLEX / 2.5F, ANGLEZ);

	        // Scale the item to fit appropriately
	        GL11.glScalef(0.5f, 0.5f, 0.5f); // Resizing the held item

	        // Adjust the jaw angle when the item is held
	        

			// Render the item at the tip of the head
			Minecraft.getMinecraft().getRenderItem().renderItem(heldItem, ItemCameraTransforms.TransformType.FIXED);

			GL11.glPopMatrix();
			if (heldItem.getItem() instanceof ItemBlock)
			{
				this.LowerJaw.rotateAngleX = 0.7f; // Adjust jaw angle when item is held
		        this.LowerJaw.offsetZ = 0.621f;
		        this.LowerJaw.offsetY = -0.82f;
			}

			else
			{ // This will handle the case where heldItem is not an ItemBlock

				this.LowerJaw.rotateAngleX = 0.3f;
				this.LowerJaw.offsetZ = 0.585f; // Slightly reduced from 0.621f
				this.LowerJaw.offsetY = -0.855f; // Slightly reduced from -0.82f
			}
		}

		else
		{
			// No item held, set LowerJaw to 0
			this.LowerJaw.rotateAngleX = 0f;
		}

		if (limbSwingAmount < 0.01F)

		{
			// Keep legs and body in idle pose when not moving
			this.RightFrontLeg.rotateAngleX = 0;
			this.LeftRearLeg.rotateAngleX = 0;
			this.RightRearLeg.rotateAngleX = 0;
			this.LeftFrontLeg.rotateAngleX = 0;

		}

		else
		{

			this.RightFrontLeg.rotateAngleX = MathHelper.cos(ageInTicks * speed + (float) Math.PI) * 0.5F * factor;
			this.LeftRearLeg.rotateAngleX = MathHelper.cos(ageInTicks * speed + (float) Math.PI) * 0.5F * factor;

			// LeftFrontLeg and RightRearLeg alternate motion
			this.LeftFrontLeg.rotateAngleX = MathHelper.cos(ageInTicks * speed) * 0.5F * factor;
			this.RightRearLeg.rotateAngleX = MathHelper.cos(ageInTicks * speed) * 0.5F * factor;

		}

	}
}