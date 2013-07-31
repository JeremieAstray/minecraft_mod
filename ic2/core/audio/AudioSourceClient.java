// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AudioSourceClient.java

package ic2.core.audio;

import ic2.core.IC2;
import java.io.PrintStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import paulscode.sound.SoundSystem;

// Referenced classes of package ic2.core.audio:
//			AudioSource, PositionSpec, AudioPosition, AudioManagerClient, 
//			AudioManager

public final class AudioSourceClient extends AudioSource
	implements Comparable
{

	private SoundSystem soundSystem;
	private String sourceName;
	private boolean valid;
	private boolean culled;
	private Reference obj;
	private AudioPosition position;
	private PositionSpec positionSpec;
	private float configuredVolume;
	private float realVolume;
	private boolean isPlaying;

	public AudioSourceClient(SoundSystem soundSystem, String sourceName, Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, 
			float volume)
	{
		valid = false;
		culled = false;
		isPlaying = false;
		this.soundSystem = soundSystem;
		this.sourceName = sourceName;
		this.obj = new WeakReference(obj);
		this.positionSpec = positionSpec;
		URL url = ic2/core/audio/AudioSource.getClassLoader().getResource((new StringBuilder()).append("ic2/sounds/").append(initialSoundFile).toString());
		if (url == null)
		{
			System.out.println((new StringBuilder()).append("Invalid sound file: ").append(initialSoundFile).toString());
			return;
		} else
		{
			position = AudioPosition.getFrom(obj, positionSpec);
			soundSystem.newSource(priorized, sourceName, url, initialSoundFile, loop, position.x, position.y, position.z, 0, ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(volume, 1.0F));
			valid = true;
			setVolume(volume);
			return;
		}
	}

	public int compareTo(AudioSourceClient x)
	{
		if (culled)
			return (int)((realVolume * 0.9F - x.realVolume) * 128F);
		else
			return (int)((realVolume - x.realVolume) * 128F);
	}

	public void remove()
	{
		if (!valid)
		{
			return;
		} else
		{
			stop();
			soundSystem.removeSource(sourceName);
			sourceName = null;
			valid = false;
			return;
		}
	}

	public void play()
	{
		if (!valid)
			return;
		if (isPlaying)
		{
			return;
		} else
		{
			isPlaying = true;
			soundSystem.play(sourceName);
			return;
		}
	}

	public void pause()
	{
		if (!valid)
			return;
		if (!isPlaying)
		{
			return;
		} else
		{
			isPlaying = false;
			soundSystem.pause(sourceName);
			return;
		}
	}

	public void stop()
	{
		if (!valid)
			return;
		if (!isPlaying)
		{
			return;
		} else
		{
			isPlaying = false;
			soundSystem.stop(sourceName);
			return;
		}
	}

	public void flush()
	{
		if (!valid)
			return;
		if (!isPlaying)
		{
			return;
		} else
		{
			soundSystem.flush(sourceName);
			return;
		}
	}

	public void cull()
	{
		if (!valid || culled)
		{
			return;
		} else
		{
			soundSystem.cull(sourceName);
			culled = true;
			return;
		}
	}

	public void activate()
	{
		if (!valid || !culled)
		{
			return;
		} else
		{
			soundSystem.activate(sourceName);
			culled = false;
			return;
		}
	}

	public float getVolume()
	{
		if (!valid)
			return 0.0F;
		else
			return soundSystem.getVolume(sourceName);
	}

	public float getRealVolume()
	{
		return realVolume;
	}

	public void setVolume(float volume)
	{
		configuredVolume = volume;
		soundSystem.setVolume(sourceName, 0.001F);
	}

	public void setPitch(float pitch)
	{
		if (!valid)
		{
			return;
		} else
		{
			soundSystem.setPitch(sourceName, pitch);
			return;
		}
	}

	public void updatePosition()
	{
		if (!valid)
			return;
		position = AudioPosition.getFrom(obj.get(), positionSpec);
		if (position == null)
		{
			return;
		} else
		{
			soundSystem.setPosition(sourceName, position.x, position.y, position.z);
			return;
		}
	}

	public void updateVolume(EntityPlayer player)
	{
		if (!valid || !isPlaying)
		{
			realVolume = 0.0F;
			return;
		}
		float maxDistance = ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(configuredVolume, 1.0F);
		float rolloffFactor = 1.0F;
		float referenceDistance = 1.0F;
		float x = (float)((Entity) (player)).posX;
		float y = (float)((Entity) (player)).posY;
		float z = (float)((Entity) (player)).posZ;
		float distance;
		if (position.world == ((Entity) (player)).worldObj)
		{
			float deltaX = position.x - x;
			float deltaY = position.y - y;
			float deltaZ = position.z - z;
			distance = (float)Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		} else
		{
			distance = (1.0F / 0.0F);
		}
		if (distance > maxDistance)
		{
			realVolume = 0.0F;
			cull();
			return;
		}
		if (distance < referenceDistance)
			distance = referenceDistance;
		float gain = 1.0F - (rolloffFactor * (distance - referenceDistance)) / (maxDistance - referenceDistance);
		float newRealVolume = gain * configuredVolume * IC2.audioManager.getMasterVolume();
		float dx = (position.x - x) / distance;
		float dy = (position.y - y) / distance;
		float dz = (position.z - z) / distance;
		if ((double)newRealVolume > 0.10000000000000001D)
		{
			for (int i = 0; (float)i < distance; i++)
			{
				int blockId = ((Entity) (player)).worldObj.getBlockId((int)x, (int)y, (int)z);
				if (blockId != 0)
					if (Block.opaqueCubeLookup[blockId])
						newRealVolume *= 0.6F;
					else
						newRealVolume *= 0.8F;
				x += dx;
				y += dy;
				z += dz;
			}

		}
		if ((double)Math.abs(realVolume / newRealVolume - 1.0F) > 0.059999999999999998D)
			soundSystem.setVolume(sourceName, IC2.audioManager.getMasterVolume() * Math.min(newRealVolume, 1.0F));
		realVolume = newRealVolume;
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((AudioSourceClient)x0);
	}
}
