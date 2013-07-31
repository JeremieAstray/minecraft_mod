// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Localization.java

package ic2.core.init;

import com.google.common.base.Charsets;
import cpw.mods.fml.common.registry.LanguageRegistry;
import ic2.core.IC2;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Localization
{

	public Localization()
	{
	}

	public static void init(File modSourceFile)
	{
		ZipFile zipFile;
		if (modSourceFile.isDirectory())
		{
			File langFolder = new File(modSourceFile, "ic2/lang");
			if (langFolder.isDirectory())
			{
				File arr$[] = langFolder.listFiles(new FilenameFilter() {

					public boolean accept(File dir, String name)
					{
						return name.endsWith(".properties");
					}

				});
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					File langFile = arr$[i$];
					try
					{
						loadLocalization(langFile.toURI().toURL().openStream(), langFile.getName().split("\\.")[0]);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						IC2.log.warning("can't read language file");
					}
				}

			} else
			{
				IC2.log.warning("can't list language files (from folder)");
			}
			break MISSING_BLOCK_LABEL_313;
		}
		if (!modSourceFile.exists() || !modSourceFile.getName().endsWith(".jar"))
			break MISSING_BLOCK_LABEL_305;
		zipFile = null;
		zipFile = new ZipFile(modSourceFile);
		Enumeration entries = zipFile.entries();
		do
		{
			if (!entries.hasMoreElements())
				break;
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String name = entry.getName();
			if (name.startsWith("ic2/lang/"))
			{
				name = name.substring("ic2/lang/".length());
				if (!name.contains("/") && name.endsWith(".properties"))
					loadLocalization(zipFile.getInputStream(entry), name.split("\\.")[0]);
			}
		} while (true);
		Exception e;
		if (zipFile != null)
			try
			{
				zipFile.close();
			}
			// Misplaced declaration of an exception variable
			catch (Exception e) { }
		break MISSING_BLOCK_LABEL_313;
		e;
		e.printStackTrace();
		IC2.log.warning("can't list language files (from jar)");
		if (zipFile != null)
			try
			{
				zipFile.close();
			}
			// Misplaced declaration of an exception variable
			catch (Exception e) { }
		break MISSING_BLOCK_LABEL_313;
		Exception exception;
		exception;
		if (zipFile != null)
			try
			{
				zipFile.close();
			}
			catch (IOException e) { }
		throw exception;
		IC2.log.warning("can't find language files");
	}

	public static void loadLocalization(InputStream inputStream, String lang)
		throws IOException
	{
		Properties properties = new Properties();
		properties.load(new InputStreamReader(inputStream, Charsets.UTF_8));
		Iterator i$ = properties.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entries = (java.util.Map.Entry)i$.next();
			Object key = entries.getKey();
			Object value = entries.getValue();
			if ((key instanceof String) && (value instanceof String))
			{
				String newKey = (String)key;
				if (!newKey.startsWith("achievement.") && !newKey.startsWith("itemGroup.") && !newKey.startsWith("death."))
					newKey = (new StringBuilder()).append("ic2.").append(newKey).toString();
				LanguageRegistry.instance().addStringLocalization(newKey, lang, (String)value);
			}
		} while (true);
	}

	public static void addLocalization(String name, String desc)
	{
		LanguageRegistry.instance().addStringLocalization(name, desc);
	}
}
