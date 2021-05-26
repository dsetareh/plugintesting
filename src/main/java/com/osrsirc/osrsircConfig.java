package com.osrsirc;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface osrsircConfig extends Config
{
	@ConfigItem(
		keyName = "fumo",
		name = "Enable Fumo",
		description = "toggle fumo"
	)
	default boolean fumo()
	{
		return true;
	}
}
