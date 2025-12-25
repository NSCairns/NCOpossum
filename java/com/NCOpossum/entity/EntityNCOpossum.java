package com.NCOpossum.entity;

import com.NCOpossum.entity.task.AITaskNCOLeafJump;
import com.NCOpossum.entity.task.AITaskNCOPickup;
import com.NCOpossum.entity.task.AITaskNCOTrashRand;
import com.NCOpossum.init.ItemInit;
import com.NCOpossum.util.handlers.SoundHandler;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EntityNCOpossum extends EntityAnimal
{

	private static final float MOVEMENT_SPEED = 0.3F;
	private NBTTagCompound itemTag = new NBTTagCompound();
	public ItemStack heldItem = ItemStack.EMPTY; // Store the item the opossum has picked up
	public EntityItem heldEntityItem = null; // The entity representing the item
	private boolean itemChanged = false;
	private static final DataParameter<NBTTagCompound> HELD_ITEM = EntityDataManager.createKey(EntityNCOpossum.class, DataSerializers.COMPOUND_TAG);

	public EntityNCOpossum(World worldIn)
	{
		super(worldIn);
		this.setSize(0.8F, 0.8F);
	}

	@Override
	protected void initEntityAI()
	{
		// The AI will be initialized automatically when we add the EntityAIWander task
		this.tasks.addTask(8, new EntityAIWander(this, MOVEMENT_SPEED));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(6, new EntityAITempt(this, MOVEMENT_SPEED, ItemInit.ALFREDO_EGG, false));
		this.tasks.addTask(4, new AITaskNCOPickup(this, MOVEMENT_SPEED));
		this.tasks.addTask(7, new AITaskNCOLeafJump(this, MOVEMENT_SPEED));
		this.tasks.addTask(5, new AITaskNCOTrashRand(this, MOVEMENT_SPEED));
 
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		// Initialize with an empty NBTTagCompound to ensure it's always in sync.
		this.getDataManager().register(HELD_ITEM, new NBTTagCompound());
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED);
    }

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (!this.world.isRemote && !this.heldItem.isEmpty() && itemChanged)
	    {
	        this.heldItem.writeToNBT(itemTag);
	        this.getDataManager().set(HELD_ITEM, itemTag); // Update DataWatcher on the server
	        itemChanged = false; // Reset change flag
	    }
		
		if (heldItem.getItem() == Item.getItemById(382) && this.isInLove() == false) 
		{
			this.setInLove(null);
		}

	}	

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		if (!this.heldItem.isEmpty())
		{
			this.heldItem.writeToNBT(itemTag); // Serialize the held item
			compound.setTag("HeldItem", itemTag);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if (compound.hasKey("HeldItem", 10))
		{
			NBTTagCompound itemTag = compound.getCompoundTag("HeldItem");
			this.heldItem = new ItemStack(itemTag); // Deserialize the held item from NBT
			this.getDataManager().set(HELD_ITEM, itemTag); // Sync item with DataWatcher
		}
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
		// Do nothing here to effectively disable fall damage
		// Alternatively, you can call super.fall() with a 0 distance to keep it safe
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		// Opossums don�t reproduce here for now
		return new EntityNCOpossum(this.world);
	}

	// Override other methods as needed, like rendering or other specific behaviors.

	// Getter for the held item
	public ItemStack getHeldItemMainhand()
	{
		return this.heldItem;
	}

	// Setter for the held item
	public void setHeldItemMainhand(ItemStack stack)
	{
	    this.heldItem = stack;
	    itemChanged = true; // Set flag to true when the item changes
	    NBTTagCompound itemTag = new NBTTagCompound();
	    stack.writeToNBT(itemTag);
	    this.getDataManager().set(HELD_ITEM, itemTag); // Update DataWatcher
	    this.dataManager.setDirty(HELD_ITEM); // Ensure immediate syncing
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		// Ensure that the client has the correct item state after a reconnect
		if (this.world.isRemote)
		{
			// Load item from DataWatcher (make sure the DataWatcher is updated properly)
			NBTTagCompound itemTag = this.getDataManager().get(HELD_ITEM);
			if (itemTag != null)
			{
				ItemStack clientItem = new ItemStack(itemTag);
				if (!clientItem.isEmpty())
				{
					this.heldItem = clientItem; // Sync item on client side after reconnect
				}

				else
				{
					this.heldItem = ItemStack.EMPTY;
				}
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundHandler.ENTITY_OPOSSUM_AMBIENT;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		// TODO Auto-generated method stub
		ItemStack ItemInHand = player.getHeldItem(hand);

		if (!ItemInHand.isEmpty() && ItemInHand.getItem() == ItemInit.ALFREDO_EGG)
		{
			eatItem(player, ItemInHand);
			playSound(SoundHandler.ENTITY_OPOSSUM_EAT, 1.0F, 1.0F);
			return true;
		}
		return super.processInteract(player, hand);
	}

	private void eatItem(EntityPlayer player, ItemStack ItemInHand)
	{
		ItemInHand.shrink(1);

		this.heal(10.0F);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundHandler.ENTITY_OPOSSUM_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundHandler.ENTITY_OPOSSUM_DEATH;
	}
}