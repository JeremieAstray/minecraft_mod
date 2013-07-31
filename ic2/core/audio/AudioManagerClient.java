// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AudioManagerClient.java

package ic2.core.audio;

import ic2.core.IC2;
import ic2.core.Platform;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

// Referenced classes of package ic2.core.audio:
//			AudioManager, AudioSource, PositionSpec, AudioSourceClient, 
//			AudioPosition

public final class AudioManagerClient extends AudioManager
{
	public static class WeakObject extends WeakReference
	{

		public boolean equals(Object object)
		{
			if (object instanceof WeakObject)
				return ((WeakObject)object).get() == get();
			else
				return get() == object;
		}

		public int hashCode()
		{
			Object object = get();
			if (object == null)
				return 0;
			else
				return object.hashCode();
		}

		public WeakObject(Object referent)
		{
			super(referent);
		}
	}


	public float fadingDistance;
	private boolean enabled;
	private int maxSourceCount;
	private final int streamingSourceCount = 4;
	private SoundSystem soundSystem;
	private float masterVolume;
	private int nextId;
	private final Map objectToAudioSourceMap = new HashMap();

	public AudioManagerClient()
	{
		fadingDistance = 16F;
		enabled = true;
		maxSourceCount = 32;
		soundSystem = null;
		masterVolume = 0.5F;
		nextId = 0;
		defaultVolume = 1.2F;
	}

	public void initialize(Configuration config)
	{
		if (config != null)
		{
			Property prop = config.get("general", "soundsEnabled", enabled);
			prop.comment = "Enable sounds";
			enabled = prop.getBoolean(enabled);
			prop = config.get("general", "soundSourceLimit", maxSourceCount);
			prop.comment = "Maximum number of audio sources, only change if you know what you're doing";
			maxSourceCount = prop.getInt(maxSourceCount);
			config.save();
		}
		if (maxSourceCount <= 6)
		{
			IC2.log.info("Audio source limit too low to enable IC2 sounds.");
			enabled = false;
		}
		if (!enabled)
		{
			IC2.log.info("Sounds disabled.");
			return;
		}
		if (maxSourceCount < 6)
		{
			enabled = false;
			return;
		} else
		{
			IC2.log.info((new StringBuilder()).append("Using ").append(maxSourceCount).append(" audio sources.").toString());
			SoundSystemConfig.setNumberStreamingChannels(4);
			SoundSystemConfig.setNumberNormalChannels(maxSourceCount - 4);
			return;
		}
	}

	public void onTick()
	{
		if (!enabled || soundSystem == null)
			return;
		IC2.platform.profilerStartSection("UpdateMasterVolume");
		float configSoundVolume = Minecraft.getMinecraft().gameSettings.soundVolume;
		if (configSoundVolume != masterVolume)
			masterVolume = configSoundVolume;
		IC2.platform.profilerEndStartSection("UpdateSourceVolume");
		EntityPlayer player = IC2.platform.getPlayerInstance();
		List audioSourceObjectsToRemove = new Vector();
		if (player == null)
		{
			audioSourceObjectsToRemove.addAll(objectToAudioSourceMap.keySet());
		} else
		{
			Queue validAudioSources = new PriorityQueue();
			for (Iterator i$ = objectToAudioSourceMap.entrySet().iterator(); i$.hasNext();)
			{
				java.util.Map.Entry entry = (java.util.Map.Entry)i$.next();
				if (((WeakObject)entry.getKey()).isEnqueued())
				{
					audioSourceObjectsToRemove.add(entry.getKey());
				} else
				{
					Iterator i$ = ((List)entry.getValue()).iterator();
					while (i$.hasNext()) 
					{
						AudioSource audioSource = (AudioSource)i$.next();
						audioSource.updateVolume(player);
						if (audioSource.getRealVolume() > 0.0F)
							validAudioSources.add(audioSource);
					}
				}
			}

			IC2.platform.profilerEndStartSection("Culling");
			for (int i = 0; !validAudioSources.isEmpty(); i++)
				if (i < maxSourceCount)
					((AudioSource)validAudioSources.poll()).activate();
				else
					((AudioSource)validAudioSources.poll()).cull();

		}
		WeakObject obj;
		for (Iterator i$ = audioSourceObjectsToRemove.iterator(); i$.hasNext(); removeSources(obj))
			obj = (WeakObject)i$.next();

		IC2.platform.profilerEndSection();
	}

	public AudioSource createSource(Object obj, String initialSoundFile)
	{
		return createSource(obj, PositionSpec.Center, initialSoundFile, false, false, defaultVolume);
	}

	public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume)
	{
		if (!enabled)
			return null;
		if (soundSystem == null)
			getSoundSystem();
		if (soundSystem == null)
			return null;
		String sourceName = getSourceName(nextId);
		nextId++;
		AudioSource audioSource = new AudioSourceClient(soundSystem, sourceName, obj, positionSpec, initialSoundFile, loop, priorized, volume);
		WeakObject key = new WeakObject(obj);
		if (!objectToAudioSourceMap.containsKey(key))
			objectToAudioSourceMap.put(key, new LinkedList());
		((List)objectToAudioSourceMap.get(key)).add(audioSource);
		return audioSource;
	}

	public void removeSources(Object obj)
	{
		if (soundSystem == null)
			return;
		WeakObject key;
		if (obj instanceof WeakObject)
			key = (WeakObject)obj;
		else
			key = new WeakObject(obj);
		if (!objectToAudioSourceMap.containsKey(key))
			return;
		AudioSource audioSource;
		for (Iterator i$ = ((List)objectToAudioSourceMap.get(key)).iterator(); i$.hasNext(); audioSource.remove())
			audioSource = (AudioSource)i$.next();

		objectToAudioSourceMap.remove(key);
	}

	public void playOnce(Object obj, String soundFile)
	{
		playOnce(obj, PositionSpec.Center, soundFile, false, defaultVolume);
	}

	public void playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume)
	{
		if (!enabled)
			return;
		if (soundSystem == null)
			getSoundSystem();
		if (soundSystem == null)
			return;
		AudioPosition position = AudioPosition.getFrom(obj, positionSpec);
		if (position == null)
			return;
		URL url = ic2/core/audio/AudioSource.getClassLoader().getResource((new StringBuilder()).append("ic2/sounds/").append(soundFile).toString());
		if (url == null)
		{
			IC2.log.warning((new StringBuilder()).append("Invalid sound file: ").append(soundFile).toString());
			return;
		} else
		{
			String sourceName = soundSystem.quickPlay(priorized, url, soundFile, false, position.x, position.y, position.z, 2, fadingDistance * Math.max(volume, 1.0F));
			soundSystem.setVolume(sourceName, masterVolume * Math.min(volume, 1.0F));
			return;
		}
	}

	public float getMasterVolume()
	{
		return masterVolume;
	}

	private void getSoundSystem()
	{
		soundSystem = Minecraft.getMinecraft().sndManager.sndSystem;
	}

	private String getSourceName(int id)
	{
		return (new StringBuilder()).append("asm_snd").append(id).toString();
	}
}
