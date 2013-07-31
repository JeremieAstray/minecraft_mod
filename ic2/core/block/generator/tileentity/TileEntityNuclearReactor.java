// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TileEntityNuclearReactor.java

package ic2.core.block.generator.tileentity;

import ic2.api.Direction;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.*;
import ic2.core.audio.*;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.generator.container.ContainerNuclearReactor;
import ic2.core.block.generator.gui.GuiNuclearReactor;
import ic2.core.block.invslot.InvSlotReactor;
import ic2.core.network.NetworkManager;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

// Referenced classes of package ic2.core.block.generator.tileentity:
//			TileEntityReactorChamber

public abstract class TileEntityNuclearReactor extends TileEntityInventory
	implements IHasGui, IReactor
{

	public static Random randomizer = new Random();
	private static final Direction directions[] = Direction.values();
	public float output;
	public int updateTicker;
	public int heat;
	public int maxHeat;
	public float hem;
	public AudioSource audioSourceMain;
	public AudioSource audioSourceGeiger;
	private float lastOutput;
	public final InvSlotReactor reactorSlot = new InvSlotReactor(this, "reactor", 0, 54);

	public TileEntityNuclearReactor()
	{
		output = 0.0F;
		heat = 0;
		maxHeat = 10000;
		hem = 1.0F;
		lastOutput = 0.0F;
		updateTicker = randomizer.nextInt(getTickRate());
	}

	public void onUnloaded()
	{
		if (IC2.platform.isRendering())
		{
			IC2.audioManager.removeSources(this);
			audioSourceMain = null;
			audioSourceGeiger = null;
		}
		super.onUnloaded();
	}

	public String getInvName()
	{
		return "Nuclear Reactor";
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		try
		{
			heat = nbttagcompound.getInteger("heat");
		}
		catch (Exception e)
		{
			heat = nbttagcompound.getShort("heat");
		}
		output = nbttagcompound.getShort("output");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("heat", heat);
		nbttagcompound.setShort("output", (short)getOutput());
	}

	public boolean enableUpdateEntity()
	{
		return IC2.platform.isSimulating();
	}

	public void updateEntity()
	{
		super.updateEntity();
		sendEnergyToChambers(getOutput());
		if (updateTicker++ % getTickRate() != 0)
			return;
		if (!super.worldObj.doChunksNearChunkExist(super.xCoord, super.yCoord, super.zCoord, 2))
		{
			output = 0.0F;
		} else
		{
			dropAllUnfittingStuff();
			output = 0.0F;
			maxHeat = 10000;
			hem = 1.0F;
			processChambers();
			if (calculateHeatEffects())
				return;
			setActive(heat >= 1000 || output > 0.0F);
			onInventoryChanged();
		}
		IC2.network.updateTileEntityField(this, "output");
	}

	public void dropAllUnfittingStuff()
	{
		int size = getReactorSize();
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 6; y++)
			{
				ItemStack stack = getMatrixCoord(x, y);
				if (stack == null)
					continue;
				if (stack.stackSize <= 0)
				{
					setMatrixCoord(x, y, null);
					continue;
				}
				if (x >= size || !isUsefulItem(stack))
				{
					eject(stack);
					setMatrixCoord(x, y, null);
				}
			}

		}

	}

	public static boolean isUsefulItem(ItemStack item)
	{
		if (item == null)
			return false;
		if (item.getItem() instanceof IReactorComponent)
			return true;
		int id = item.itemID;
		return id == Ic2Items.reEnrichedUraniumCell.itemID || id == Ic2Items.nearDepletedUraniumCell.itemID;
	}

	public void eject(ItemStack drop)
	{
		if (!IC2.platform.isSimulating() || drop == null)
		{
			return;
		} else
		{
			float f = 0.7F;
			double d = (double)(super.worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d1 = (double)(super.worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d2 = (double)(super.worldObj.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(super.worldObj, (double)super.xCoord + d, (double)super.yCoord + d1, (double)super.zCoord + d2, drop);
			entityitem.delayBeforeCanPickup = 10;
			super.worldObj.spawnEntityInWorld(entityitem);
			return;
		}
	}

	public boolean calculateHeatEffects()
	{
		if (heat < 4000 || !IC2.platform.isSimulating() || IC2.explosionPowerReactorMax <= 0.0F)
			return false;
		float power = (float)heat / (float)maxHeat;
		if (power >= 1.0F)
		{
			explode();
			return true;
		}
		if (power >= 0.85F && super.worldObj.rand.nextFloat() <= 0.2F * hem)
		{
			int coord[] = getRandCoord(2);
			if (coord != null)
			{
				int id = super.worldObj.getBlockId(coord[0], coord[1], coord[2]);
				if (id == 0)
					super.worldObj.setBlock(coord[0], coord[1], coord[2], ((Block) (Block.fire)).blockID, 0, 7);
				else
				if (Block.blocksList[id] != null && Block.blocksList[id].getBlockHardness(super.worldObj, coord[0], coord[1], coord[2]) <= -1F)
				{
					Material mat = Block.blocksList[id].blockMaterial;
					if (mat == Material.rock || mat == Material.iron || mat == Material.lava || mat == Material.ground || mat == Material.clay)
						super.worldObj.setBlock(coord[0], coord[1], coord[2], ((Block) (Block.lavaMoving)).blockID, 15, 7);
					else
						super.worldObj.setBlock(coord[0], coord[1], coord[2], ((Block) (Block.fire)).blockID, 0, 7);
				}
			}
		}
		if (power >= 0.7F)
		{
			List list1 = super.worldObj.getEntitiesWithinAABB(net/minecraft/entity/EntityLivingBase, AxisAlignedBB.getBoundingBox(super.xCoord - 3, super.yCoord - 3, super.zCoord - 3, super.xCoord + 4, super.yCoord + 4, super.zCoord + 4));
			for (int l = 0; l < list1.size(); l++)
			{
				Entity ent = (Entity)list1.get(l);
				ent.attackEntityFrom(IC2DamageSource.radiation, (int)((float)super.worldObj.rand.nextInt(4) * hem));
			}

		}
		if (power >= 0.5F && super.worldObj.rand.nextFloat() <= hem)
		{
			int coord[] = getRandCoord(2);
			if (coord != null)
			{
				int id = super.worldObj.getBlockId(coord[0], coord[1], coord[2]);
				if (id > 0 && Block.blocksList[id].blockMaterial == Material.water)
					super.worldObj.setBlock(coord[0], coord[1], coord[2], 0, 0, 7);
			}
		}
		if (power >= 0.4F && super.worldObj.rand.nextFloat() <= hem)
		{
			int coord[] = getRandCoord(2);
			if (coord != null)
			{
				int id = super.worldObj.getBlockId(coord[0], coord[1], coord[2]);
				if (id > 0)
				{
					Material mat = Block.blocksList[id].blockMaterial;
					if (mat == Material.wood || mat == Material.leaves || mat == Material.cloth)
						super.worldObj.setBlock(coord[0], coord[1], coord[2], ((Block) (Block.fire)).blockID, 0, 7);
				}
			}
		}
		return false;
	}

	public int[] getRandCoord(int radius)
	{
		if (radius <= 0)
			return null;
		int c[] = new int[3];
		c[0] = (super.xCoord + super.worldObj.rand.nextInt(2 * radius + 1)) - radius;
		c[1] = (super.yCoord + super.worldObj.rand.nextInt(2 * radius + 1)) - radius;
		c[2] = (super.zCoord + super.worldObj.rand.nextInt(2 * radius + 1)) - radius;
		if (c[0] == super.xCoord && c[1] == super.yCoord && c[2] == super.zCoord)
			return null;
		else
			return c;
	}

	public void processChambers()
	{
		int size = getReactorSize();
		for (int y = 0; y < 6; y++)
		{
			for (int x = 0; x < size; x++)
			{
				ItemStack thing = getMatrixCoord(x, y);
				if (thing != null && (thing.getItem() instanceof IReactorComponent))
				{
					IReactorComponent comp = (IReactorComponent)thing.getItem();
					comp.processChamber(this, thing, x, y);
				}
			}

		}

	}

	public boolean produceEnergy()
	{
		return super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord) && IC2.energyGeneratorNuclear != 0;
	}

	public ItemStack getMatrixCoord(int x, int y)
	{
		if (x < 0 || x >= 9 || y < 0 || y >= 6)
			return null;
		else
			return super.getStackInSlot(x + y * 9);
	}

	public ItemStack getStackInSlot(int i)
	{
		int x = i % 9;
		int size = getReactorSize();
		if (x >= size)
			return getMatrixCoord(size - 1, i / 9);
		else
			return super.getStackInSlot(i);
	}

	public void setMatrixCoord(int x, int y, ItemStack stack)
	{
		if (x < 0 || x >= 9 || y < 0 || y >= 6)
		{
			return;
		} else
		{
			super.setInventorySlotContents(x + y * 9, stack);
			return;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		int x = i % 9;
		int size = getReactorSize();
		if (x >= size)
			setMatrixCoord(size - 1, i / 9, itemstack);
		else
			super.setInventorySlotContents(i, itemstack);
	}

	public short getReactorSize()
	{
		if (super.worldObj == null)
			return 9;
		short rows = 3;
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			TileEntity target = direction.applyToTileEntity(this);
			if (target instanceof TileEntityReactorChamber)
				rows++;
		}

		return rows;
	}

	public int sendEnergyToChambers(int send)
	{
		send = sendEnergy(send);
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction value = arr$[i$];
			TileEntity te = value.applyToTileEntity(this);
			if (te instanceof TileEntityReactorChamber)
				send = ((TileEntityReactorChamber)te).sendEnergy(send);
		}

		return send;
	}

	public int getTickRate()
	{
		return 20;
	}

	public ContainerBase getGuiContainer(EntityPlayer entityPlayer)
	{
		return new ContainerNuclearReactor(entityPlayer, this);
	}

	public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin)
	{
		return new GuiNuclearReactor(new ContainerNuclearReactor(entityPlayer, this));
	}

	public void onGuiClosed(EntityPlayer entityplayer)
	{
	}

	public void onNetworkUpdate(String field)
	{
		if (field.equals("output"))
		{
			if (output > 0.0F)
			{
				if (lastOutput <= 0.0F)
				{
					if (audioSourceMain == null)
						audioSourceMain = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/NuclearReactorLoop.ogg", true, false, IC2.audioManager.defaultVolume);
					if (audioSourceMain != null)
						audioSourceMain.play();
				}
				if (output < 40F)
				{
					if (lastOutput <= 0.0F || lastOutput >= 40F)
					{
						if (audioSourceGeiger != null)
							audioSourceGeiger.remove();
						audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerLowEU.ogg", true, false, IC2.audioManager.defaultVolume);
						if (audioSourceGeiger != null)
							audioSourceGeiger.play();
					}
				} else
				if (output < 80F)
				{
					if (lastOutput < 40F || lastOutput >= 80F)
					{
						if (audioSourceGeiger != null)
							audioSourceGeiger.remove();
						audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerMedEU.ogg", true, false, IC2.audioManager.defaultVolume);
						if (audioSourceGeiger != null)
							audioSourceGeiger.play();
					}
				} else
				if (output >= 80F && lastOutput < 80F)
				{
					if (audioSourceGeiger != null)
						audioSourceGeiger.remove();
					audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerHighEU.ogg", true, false, IC2.audioManager.defaultVolume);
					if (audioSourceGeiger != null)
						audioSourceGeiger.play();
				}
			} else
			if (lastOutput > 0.0F)
			{
				if (audioSourceMain != null)
					audioSourceMain.stop();
				if (audioSourceGeiger != null)
					audioSourceGeiger.stop();
			}
			lastOutput = output;
		}
		super.onNetworkUpdate(field);
	}

	public float getWrenchDropRate()
	{
		return 0.8F;
	}

	public ChunkCoordinates getPosition()
	{
		return new ChunkCoordinates(super.xCoord, super.yCoord, super.zCoord);
	}

	public World getWorld()
	{
		return super.worldObj;
	}

	public int getHeat()
	{
		return heat;
	}

	public void setHeat(int heat)
	{
		this.heat = heat;
	}

	public int addHeat(int amount)
	{
		heat += amount;
		return heat;
	}

	public ItemStack getItemAt(int x, int y)
	{
		return getMatrixCoord(x, y);
	}

	public void setItemAt(int x, int y, ItemStack item)
	{
		setMatrixCoord(x, y, item);
	}

	public void explode()
	{
		float boomPower = 10F;
		float boomMod = 1.0F;
		for (int y = 0; y < 6; y++)
		{
			for (int x = 0; x < getReactorSize(); x++)
			{
				ItemStack stack = getMatrixCoord(x, y);
				if (stack != null && (stack.getItem() instanceof IReactorComponent))
				{
					float f = ((IReactorComponent)stack.getItem()).influenceExplosion(this, stack);
					if (f > 0.0F && f < 1.0F)
						boomMod *= f;
					else
						boomPower += f;
				}
				setMatrixCoord(x, y, null);
			}

		}

		boomPower *= hem * boomMod;
		IC2.log.log(Level.INFO, (new StringBuilder()).append("Nuclear Reactor at ").append(super.worldObj.provider.dimensionId).append(":(").append(super.xCoord).append(",").append(super.yCoord).append(",").append(super.zCoord).append(") melted (explosion power ").append(boomPower).append(")").toString());
		if (boomPower > IC2.explosionPowerReactorMax)
			boomPower = IC2.explosionPowerReactorMax;
		Direction arr$[] = directions;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Direction direction = arr$[i$];
			TileEntity target = direction.applyToTileEntity(this);
			if (target instanceof TileEntityReactorChamber)
				super.worldObj.setBlock(target.xCoord, target.yCoord, target.zCoord, 0, 0, 7);
		}

		super.worldObj.setBlock(super.xCoord, super.yCoord, super.zCoord, 0, 0, 7);
		ExplosionIC2 explosion = new ExplosionIC2(super.worldObj, null, super.xCoord, super.yCoord, super.zCoord, boomPower, 0.01F, 1.5F, true);
		explosion.doExplosion();
	}

	public int getMaxHeat()
	{
		return maxHeat;
	}

	public void setMaxHeat(int newMaxHeat)
	{
		maxHeat = newMaxHeat;
	}

	public float getHeatEffectModifier()
	{
		return hem;
	}

	public void setHeatEffectModifier(float newHEM)
	{
		hem = newHEM;
	}

	public int getOutput()
	{
		return (int)Math.floor(output);
	}

	public float addOutput(float energy)
	{
		return output += energy;
	}

	public int addOutput(int energy)
	{
		return (int)Math.floor(addOutput(energy));
	}

	/**
	 * @deprecated Method getPulsePower is deprecated
	 */

	public int getPulsePower()
	{
		return 1;
	}

	public abstract int sendEnergy(int i);

}
