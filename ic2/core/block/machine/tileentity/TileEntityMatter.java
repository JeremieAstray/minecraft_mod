// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityMatter.java

package ic2.core.block.machine.tileentity;

import ic2.api.Direction;
import ic2.api.recipe.Recipes;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.ContainerMatter;
import ic2.core.block.machine.gui.GuiMatter;
import ic2.core.network.NetworkManager;
import java.util.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Referenced classes of package ic2.core.block.machine.tileentity:
//			TileEntityElectricMachine

public class TileEntityMatter extends TileEntityElectricMachine
	implements IHasGui
{

	public int soundTicker;
	public int scrap;
	private final int StateIdle = 0;
	private final int StateRunning = 1;
	private final int StateRunningScrap = 2;
	private int state;
	private int prevState;
	private AudioSource audioSource;
	private AudioSource audioSourceScrap;
	public final InvSlotProcessableGeneric amplifierSlot;
	public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1, 1);

	public TileEntityMatter()
	{
		super(0xf4240, 3, -1);
		scrap = 0;
		state = 0;
		prevState = 0;
		soundTicker = IC2.random.nextInt(32);
		amplifierSlot = new InvSlotProcessableGeneric(this, "scrap", 0, 1, Recipes.matterAmplifier);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		try
		{
			scrap = nbttagcompound.getInteger("scrap");
		}
		catch (Throwable e)
		{
			scrap = nbttagcompound.getShort("scrap");
		}
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("scrap", scrap);
	}

	public String getInvName()
	{
		return "Mass Fabricator";
	}

	public void updateEntity()
	{
		super.updateEntity();
		if (isRedstonePowered() || energy <= 0)
		{
			setState(0);
			setActive(false);
		} else
		{
			setState(scrap <= 0 ? 1 : 2);
			setActive(true);
			boolean needsInvUpdate = false;
			if (scrap < 1000)
			{
				Integer amplifier = (Integer)amplifierSlot.processRaw(false);
				if (amplifier != null && amplifier.intValue() > 0)
					scrap += amplifier.intValue();
			}
			if (energy >= 0xf4240)
				needsInvUpdate = attemptGeneration();
			if (needsInvUpdate)
				onInventoryChanged();
		}
	}

	public void onUnloaded()
	{
		if (IC2.platform.isRendering() && audioSource != null)
		{
			IC2.audioManager.removeSources(this);
			audioSource = null;
			audioSourceScrap = null;
		}
		super.onUnloaded();
	}

	public boolean attemptGeneration()
	{
		if (outputSlot.add(Ic2Items.matter.copy()) == 0)
		{
			energy -= 0xf4240;
			return true;
		} else
		{
			return false;
		}
	}

	public int demandsEnergy()
	{
		if (isRedstonePowered())
			return 0;
		else
			return maxEnergy - energy;
	}

	public int injectEnergy(Direction directionFrom, int amount)
	{
		if (amount > getMaxSafeInput())
		{
			super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, 0, 0, 7);
			ExplosionIC2 explosion = new ExplosionIC2(super.worldObj, null, (float)super.xCoord + 0.5F, (float)super.yCoord + 0.5F, (float)super.zCoord + 0.5F, 15F, 0.01F, 1.5F);
			explosion.doExplosion();
			return 0;
		}
		if (energy >= maxEnergy || isRedstonePowered())
		{
			return amount;
		} else
		{
			int bonus = Math.min(amount, scrap);
			scrap -= bonus;
			energy += amount + 5 * bonus;
			return 0;
		}
	}

	public int getMaxSafeInput()
	{
		return 512;
	}

	public String getProgressAsString()
	{
		int p = energy / 10000;
		if (p > 100)
			p = 100;
		return (new StringBuilder()).append("").append(p).append("%").toString();
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerMatter(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiMatter(new ContainerMatter(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	private void setState(int state)
	{
		this.state = state;
		if (prevState != state)
			IC2.network.updateTileEntityField(this, "state");
		prevState = state;
	}

	public List getNetworkedFields()
	{
		List ret = new Vector(1);
		ret.add("state");
		ret.addAll(super.getNetworkedFields());
		return ret;
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("state") && prevState != state)
		{
			switch (state)
			{
			default:
				break;

			case 0: // '\0'
				if (audioSource != null)
					audioSource.stop();
				if (audioSourceScrap != null)
					audioSourceScrap.stop();
				break;

			case 1: // '\001'
				if (audioSource == null)
					audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.defaultVolume);
				if (audioSource != null)
					audioSource.play();
				if (audioSourceScrap != null)
					audioSourceScrap.stop();
				break;

			case 2: // '\002'
				if (audioSource == null)
					audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.defaultVolume);
				if (audioSourceScrap == null)
					audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", true, false, IC2.audioManager.defaultVolume);
				if (audioSource != null)
					audioSource.play();
				if (audioSourceScrap != null)
					audioSourceScrap.play();
				break;
			}
			prevState = state;
		}
		super.onNetworkUpdate(field);
	}

	public float getWrenchDropRate()
	{
		return 0.7F;
	}

	public boolean amplificationIsAvailable()
	{
		if (scrap > 0)
		{
			return true;
		} else
		{
			Integer amplifier = (Integer)amplifierSlot.processRaw(false);
			return amplifier != null && amplifier.intValue() > 0;
		}
	}
}
