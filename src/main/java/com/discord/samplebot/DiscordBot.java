package com.discord.samplebot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot extends ListenerAdapter {
	private static JDA jda = null;
	public static Dotenv dotenv = Dotenv.load();
	private static final String BOT_TOKEN = dotenv.get("DISCORD_API");

	public static void main(String[] args) {
		jda = JDABuilder.createDefault(BOT_TOKEN)
                .setRawEventsEnabled(true)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new DiscordBot())
                .setActivity(Activity.playing("プログラ"))
                .build();

		jda.updateCommands().queue();

	}
	
	//メッセージの反応メソッド
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;
		String s = "Hello World";
		event.getChannel().sendMessage(s).queue();
	}
	
	//コマンドの反応メソッド
	@Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		
	}
}
