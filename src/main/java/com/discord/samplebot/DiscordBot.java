package com.discord.samplebot;

import java.util.List;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot extends ListenerAdapter {
	private static JDA jda = null;
	public static Dotenv dotenv = Dotenv.load();
	private static final String BOT_TOKEN = dotenv.get("DISCORD_API");
	private static final String GUILD_ID = dotenv.get("GUILD_ID");
	private static final String MODE = "dev";
	private static final Member members = new Member();

	public static void main(String[] args) {
		jda = JDABuilder.createDefault(BOT_TOKEN)
                .setRawEventsEnabled(true)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new DiscordBot())
                .setActivity(Activity.playing("プログラミング"))
                .build();
		
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (MODE.equals("dev")) {
			// 開発環境
			Guild guild = jda.getGuildById(GUILD_ID);
			
			guild.updateCommands()
			.addCommands(Commands.slash("register", "register member")
					.addOption(OptionType.STRING, "username", "register username", true))
			.addCommands(Commands.slash("delete", "delete member")
					.addOption(OptionType.STRING, "username", "delete username", true))
			.addCommands(Commands.slash("show", "delete member"))
			.queue();
		}
		else {
			// 本番環境
			jda.updateCommands()
			.addCommands(Commands.slash("register", "register member")
					.addOption(OptionType.STRING, "username", "register username", true))
			.addCommands(Commands.slash("delete", "delete member")
					.addOption(OptionType.STRING, "username", "delete username", true))
			.addCommands(Commands.slash("show", "delete member"))
			.queue();
		}
		
		
	

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
		String replyString = "";
		if (event.getName().equals("register")) {
			if (members.register(event.getOption("username").getAsString())) replyString = "登録完了";
			else replyString = "登録失敗";
			System.out.print("register");
		} 
		else if (event.getName().equals("delete")) {
			if (members.remove(event.getOption("username").getAsString())) replyString = "消去完了";
			else replyString = "消去失敗";
			System.out.print("delete");
		}
		else if (event.getName().equals("show")) {
			List<String> memberList = members.getMembers();
			replyString = "メンバーリスト\n" + String.join("\n", memberList);
			System.out.print("show");
		}
		else { 
			replyString = "該当するコマンドがありません";
		}
		event.reply(replyString).queue();
	}
}
