package com.osrsirc;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


@Slf4j
@PluginDescriptor(
	name = "Fumo"
)
public class osrsircPlugin extends Plugin
{
	@Inject
	private Client client;

	org.kitteh.irc.client.library.Client ircClient;

	@Inject
	private osrsircConfig config;

	@Override
	protected void startUp() throws Exception
	{
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "startup", null);
		ircClient = org.kitteh.irc.client.library.Client.builder().nick("obamafan69").server().port(6667, org.kitteh.irc.client.library.Client.Builder.Server.SecurityType.INSECURE).host("irc.oftc.net").then().buildAndConnect();
		ircClient.addChannel("#420_memes_ayy_lmao");
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "connected to irc", null);
	}

	@Override
	protected void shutDown() throws Exception
	{
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "disconnected from irc", null);
		ircClient.shutdown();

	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
		switch (event.getType()) {

			case FRIENDSCHAT:
				ircClient.sendMessage("#420_memes_ayy_lmao", "[Friends Chat] <" + event.getName() + "> " + event.getMessage());
				break;
			case CLAN_CHAT:
				ircClient.sendMessage("#420_memes_ayy_lmao", "[Clan Chat] <" + event.getName() + "> " + event.getMessage());
				break;
			case PRIVATECHAT:
				ircClient.sendMessage("#420_memes_ayy_lmao", "[Private Chat] <" + event.getName() + "> " + event.getMessage());
				break;
			case PUBLICCHAT:
				ircClient.sendMessage("#420_memes_ayy_lmao", "[Public Chat] <" + event.getName() + "> " + event.getMessage());
				break;
		}
	}


	@Provides
	osrsircConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(osrsircConfig.class);
	}
}
