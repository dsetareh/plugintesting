package com.osrsirc;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("osrsircConfig")
public interface osrsircConfig extends Config
{
	@ConfigSection(
			name = "Connection Config",
			description = "IRC Client Configuration",
			position = 0,
			closedByDefault = true
	)
	String connConfig = "connConfig";

	@ConfigItem(
			keyName = "ircHostname",
			name = "Host",
			description = "IRC Server Address",
			section = connConfig,
			position = 0
	)
	default String ircHostname()
	{
		return "irc.oftc.net";
	}

	@ConfigItem(
			keyName = "ircPort",
			name = "Port",
			description = "IRC Server Port",
			section = connConfig,
			position = 1
	)
	default int ircPort()
	{
		return 6697;
	}

	@ConfigItem(
			keyName = "ircSecurity",
			name = "Security",
			description = "gig",
			section = connConfig,
			position = 2
	)
	default org.kitteh.irc.client.library.Client.Builder.Server.SecurityType ircSecurity()
	{
		return org.kitteh.irc.client.library.Client.Builder.Server.SecurityType.SECURE;
	}

	@ConfigItem(
			keyName = "ircChannel",
			name = "Channel",
			description = "IRC Channel Name",
			section = connConfig,
			position = 3
	)
	default String ircChannel()
	{
		return "#rs_memes_ayy_lmao";
	}

	@ConfigItem(
			keyName = "ircNick",
			name = "IRC Nick",
			description = "IRC Nickname",
			section = connConfig,
			position = 4
	)
	default String ircNick()
	{
		return "sneedscape";
	}

	@ConfigItem(
			keyName = "syncPublicChat",
			name = "Public Chat",
			description = "Whether to send Public Chat messages to IRC"
	)
	default boolean syncPublicChat()
	{
		return false;
	}

	@ConfigItem(
			keyName = "syncFriendsChat",
			name = "Friends Chat",
			description = "Whether to send Friends Chat messages to IRC"
	)
	default boolean syncFriendsChat()
	{
		return true;
	}

	@ConfigItem(
			keyName = "syncClanChat",
			name = "Clan Chat",
			description = "Whether to send Clan Chat messages to IRC"
	)
	default boolean syncClanChat()
	{
		return true;
	}
}
