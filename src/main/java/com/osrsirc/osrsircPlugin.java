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
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


import net.engio.mbassy.listener.Handler;

@Slf4j
@PluginDescriptor(
	name = "Reimu Fumo (Real)"
)
public class osrsircPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private osrsircConfig config;


	private org.kitteh.irc.client.library.Client ircClient;



	@Override
	protected void startUp() throws Exception
	{
		// TODO: this starts the client on plugin load rn, have it connect on login and dc on logout maybe?
		startIRC();
	}

	@Override
	protected void shutDown() throws Exception
	{
		stopIRC();
	}

	private void startIRC() {
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Starting Up", null);

		// Start irc client (insecure conn ONLY)
		ircClient = org.kitteh.irc.client.library.Client.builder().nick(config.ircNick()).server().port(config.ircPort(), org.kitteh.irc.client.library.Client.Builder.Server.SecurityType.INSECURE).host(config.ircHostname()).then().buildAndConnect();
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Connected To IRC", null);


		// join a channel
		ircClient.addChannel(config.ircChannel());
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Joined " + config.ircChannel(), null);
		ircClient.getEventManager().registerEventListener(this);
	}

	private void stopIRC() {
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "disconnecting from irc", null);
		ircClient.shutdown();
	}



	// fires on osrs chat message, sends messages osrs -> irc
	@Subscribe
	public void onChatMessage(ChatMessage event) {
		switch (event.getType()) {
			case PUBLICCHAT:
				if (config.syncPublicChat())
					ircClient.sendMessage("#rs_memes_ayy_lmao", "[Public Chat] <" + event.getName() + "> " + event.getMessage());
				break;
			case CLAN_CHAT:
				if (config.syncClanChat())
					ircClient.sendMessage("#rs_memes_ayy_lmao", "[Clan Chat] <" + event.getName() + "> " + event.getMessage());
				break;
			case FRIENDSCHAT:
				if (config.syncFriendsChat())
					ircClient.sendMessage("#rs_memes_ayy_lmao", "[Friends Chat] <" + event.getName() + "> " + event.getMessage());
				break;
		}
	}

	// restart irc client on config change
	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		// if config changed was not of our plugin
		if (!event.getGroup().equals("osrsircPlugin"))
			return; // don't reconnect
		// else, disconnect and reload irc client
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "IRC Config Changed, Reconnecting", null);
		// disconnect
		ircClient.shutdown();
		// reconstruct client with latest cfg values
		startIRC();
	}

	//
//	// fires on IRC PM, irc - > osrs
//	@Handler
//	public void privmsg(org.kitteh.irc.client.library.event.user.PrivateMessageEvent event) {
//		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "IRC PM RECIEVED: " + event.getMessage(), null);
//	}


	@Provides
	osrsircConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(osrsircConfig.class);
	}
}
