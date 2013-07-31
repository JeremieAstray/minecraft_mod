// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityCanner.java

package ic2.core.block.machine.tileentity;

import ic2.core.*;
import ic2.core.audio.AudioManager;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.*;
import ic2.core.block.machine.ContainerCanner;
import ic2.core.block.machine.gui.GuiCanner;
import ic2.core.util.StackUtil;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine

public class TileEntityCanner extends TileEntityElectricMachine
	implements IHasGui
{

	public static final Map specialFood;
	public short progress;
	public int energyconsume;
	public int operationLength;
	private int fuelQuality;
	public AudioSource audioSource;
	public final InvSlot resInputSlot;
	public final InvSlotConsumable inputSlot;
	public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 2, 1);

	public TileEntityCanner()
	{
		super(600, 1, 1);
		progress = 0;
		fuelQuality = 0;
		energyconsume = 1;
		operationLength = 600;
		resInputSlot = new InvSlot(this, "input", 0, ic2.core.block.invslot.InvSlot.Access.I, 1);
		inputSlot = new InvSlotConsumableId(this, "canInput", 3, 1, new int[] {
			Ic2Items.tinCan.itemID, Ic2Items.fuelCan.itemID, Ic2Items.jetpack.itemID, Ic2Items.cfPack.itemID
		});
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		try
		{
			fuelQuality = nbttagcompound.getInteger("fuelQuality");
		}
		catch (Throwable e)
		{
			fuelQuality = nbttagcompound.getShort("fuelQuality");
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("fuelQuality", fuelQuality);
	}

	public int gaugeProgressScaled(int i)
	{
		int l = operationLength;
		if (getMode() == 1 && resInputSlot.get() != null)
		{
			int food = getFoodValue(resInputSlot.get());
			if (food > 0)
				l = 50 * food;
		}
		if (getMode() == 3)
			l = 50;
		return (progress * i) / l;
	}

	public int gaugeFuelScaled(int i)
	{
		if (energy <= 0)
			return 0;
		int r = (energy * i) / (operationLength * energyconsume);
		if (r > i)
			return i;
		else
			return r;
	}

	public void updateEntity()
	{
		super.updateEntity();
		boolean needsInvUpdate = false;
		boolean canOperate = canOperate();
		boolean newActive = getActive();
		if (canOperate && (getMode() == 1 && progress >= getFoodValue(resInputSlot.get()) * 50 || getMode() == 2 && progress > 0 && progress % 100 == 0 || getMode() == 3 && progress >= 50))
		{
			if (getMode() == 1 || getMode() == 3 || progress >= 600)
			{
				operate(false);
				fuelQuality = 0;
				progress = 0;
				newActive = false;
			} else
			{
				operate(true);
			}
			needsInvUpdate = true;
		}
		if (!newActive || progress == 0)
		{
			if (canOperate)
			{
				if (energy >= energyconsume)
					newActive = true;
			} else
			if (getMode() != 2)
			{
				fuelQuality = 0;
				progress = 0;
			}
		} else
		if (!canOperate || energy < energyconsume)
		{
			if (!canOperate && getMode() != 2)
			{
				fuelQuality = 0;
				progress = 0;
			}
			newActive = false;
		}
		if (newActive)
		{
			progress++;
			energy -= energyconsume;
		}
		if (needsInvUpdate)
			onInventoryChanged();
		if (newActive != getActive())
			setActive(newActive);
	}

	public void operate(boolean incremental)
	{
		switch (getMode())
		{
		default:
			break;

		case 1: // '\001'
			int food = getFoodValue(resInputSlot.get());
			int meta = getFoodMeta(resInputSlot.get());
			resInputSlot.get().stackSize--;
			if (resInputSlot.get().getItem() == Item.bowlSoup && resInputSlot.get().stackSize <= 0)
				resInputSlot.put(new ItemStack(Item.bowlEmpty));
			if (resInputSlot.get().stackSize <= 0)
				resInputSlot.clear();
			inputSlot.consume(food);
			outputSlot.add(new ItemStack(Ic2Items.filledTinCan.getItem(), food, meta));
			break;

		case 2: // '\002'
			int fuel = getFuelValue(resInputSlot.get().itemID);
			resInputSlot.get().stackSize--;
			if (resInputSlot.get().stackSize <= 0)
				resInputSlot.clear();
			fuelQuality += fuel;
			if (incremental)
				break;
			if (inputSlot.get().itemID == Ic2Items.fuelCan.itemID)
			{
				inputSlot.consume(1);
				ItemStack result = Ic2Items.filledFuelCan.copy();
				NBTTagCompound data = StackUtil.getOrCreateNbtData(result);
				data.setInteger("value", fuelQuality);
				outputSlot.add(result);
				break;
			}
			int damage = inputSlot.get().getItemDamage();
			damage -= fuelQuality;
			if (damage < 1)
				damage = 1;
			inputSlot.clear();
			outputSlot.add(new ItemStack(Ic2Items.jetpack.itemID, 1, damage));
			break;

		case 3: // '\003'
			resInputSlot.get().stackSize--;
			if (resInputSlot.get().stackSize <= 0)
				resInputSlot.clear();
			inputSlot.get().setItemDamage(inputSlot.get().getItemDamage() - 2);
			if (resInputSlot.isEmpty() || inputSlot.get().getItemDamage() <= 1)
			{
				outputSlot.add(inputSlot.get());
				inputSlot.clear();
			}
			break;
		}
	}

	public void onUnloaded()
	{
		if (audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
		}
		super.onUnloaded();
	}

	public boolean canOperate()
	{
		if (resInputSlot.isEmpty())
			return false;
		switch (getMode())
		{
		case 1: // '\001'
			int food = getFoodValue(resInputSlot.get());
			return food > 0 && food <= inputSlot.get().stackSize && outputSlot.canAdd(new ItemStack(Ic2Items.filledTinCan.itemID, food, getFoodMeta(resInputSlot.get())));

		case 2: // '\002'
			int fuel = getFuelValue(resInputSlot.get().itemID);
			return fuel > 0 && outputSlot.canAdd(Ic2Items.jetpack);

		case 3: // '\003'
			return inputSlot.get().getItemDamage() > 2 && getPelletValue(resInputSlot.get()) > 0 && outputSlot.canAdd(resInputSlot.get());
		}
		return false;
	}

	public int getMode()
	{
		if (inputSlot.isEmpty())
			return 0;
		if (inputSlot.get().itemID == Ic2Items.tinCan.itemID)
			return 1;
		if (inputSlot.get().itemID == Ic2Items.fuelCan.itemID || inputSlot.get().itemID == Ic2Items.jetpack.itemID)
			return 2;
		return inputSlot.get().itemID != Ic2Items.cfPack.itemID ? 0 : 3;
	}

	public String getInvName()
	{
		return "Canning Machine";
	}

	private int getFoodValue(ItemStack item)
	{
		if (item.itemID == Ic2Items.filledTinCan.itemID)
			return 0;
		if (item.getItem() instanceof ItemFood)
		{
			ItemFood food = (ItemFood)item.getItem();
			return (int)Math.ceil((double)food.getHealAmount() / 2D);
		}
		return item.itemID != Item.cake.itemID && item.itemID != Block.cake.blockID ? 0 : 6;
	}

	public int getFuelValue(int id)
	{
		if (id == Ic2Items.coalfuelCell.itemID)
			return 2548;
		if (id == Ic2Items.biofuelCell.itemID)
			return 868;
		if (id == Item.redstone.itemID && fuelQuality > 0)
			return (int)((double)fuelQuality * 0.20000000000000001D);
		if (id == Item.glowstone.itemID && fuelQuality > 0)
			return (int)((double)fuelQuality * 0.29999999999999999D);
		if (id == Item.gunpowder.itemID && fuelQuality > 0)
			return (int)((double)fuelQuality * 0.40000000000000002D);
		else
			return 0;
	}

	public int getPelletValue(ItemStack item)
	{
		if (item == null)
			return 0;
		if (item.itemID != Ic2Items.constructionFoamPellet.itemID)
			return 0;
		else
			return item.stackSize;
	}

	private int getFoodMeta(ItemStack item)
	{
		if (item == null)
		{
			return 0;
		} else
		{
			ChunkCoordIntPair ccip = new ChunkCoordIntPair(item.itemID, item.getItemDamage());
			return specialFood.containsKey(ccip) ? ((Integer)specialFood.get(ccip)).intValue() : 0;
		}
	}

	public String getStartSoundFile()
	{
		return null;
	}

	public String getInterruptSoundFile()
	{
		return null;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerCanner(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiCanner(new ContainerCanner(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public float getWrenchDropRate()
	{
		return 0.85F;
	}

	static 
	{
		specialFood = new HashMap();
		specialFood.put(new ChunkCoordIntPair(Item.rottenFlesh.itemID, 0), Integer.valueOf(1));
		specialFood.put(new ChunkCoordIntPair(Item.spiderEye.itemID, 0), Integer.valueOf(2));
		specialFood.put(new ChunkCoordIntPair(Item.poisonousPotato.itemID, 0), Integer.valueOf(2));
		specialFood.put(new ChunkCoordIntPair(Item.chickenRaw.itemID, 0), Integer.valueOf(3));
		specialFood.put(new ChunkCoordIntPair(Item.appleGold.itemID, 0), Integer.valueOf(4));
		specialFood.put(new ChunkCoordIntPair(Item.appleGold.itemID, 1), Integer.valueOf(5));
	}
}
