package dev.sodiograaz.bot;

import dev.sodiograaz.bot.commands.utils.LocalCommandsFetcher;
import dev.sodiograaz.bot.listeners.SlashCommandListener;
import dev.sodiograaz.datasource.DataSource;
import dev.sodiograaz.datasource.DataSourceFactory;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* @author Sodiograaz
 @since 01/05/2024
*/
public class Application
{
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static final int LAST = 1;
	private static @Getter DataSource dataSource;
	private static @Getter JDA client;
	private static @Getter String membersFormat;
	private static @Getter @Nullable Guild guild;
	
	public static void main(String[] args) throws InterruptedException
	{
		if(args.length > 0)
		{
			if(!args[0].startsWith("TOKEN"))
			{
				log.error("Token may not be null");
				return;
			}
			if(!args[1].startsWith("GUILD"))
			{
				log.error("Guild may not be null");
				return;
			}
			if(!args[2].startsWith("HOST"))
			{
				log.error("Host may not be null");
				return;
			}
			if(!args[3].startsWith("PORT"))
			{
				log.error("Port may not be null");
				return;
			}
			if(!args[4].startsWith("USERNAME"))
			{
				log.error("Username may not be null");
				return;
			}
			if(!args[5].startsWith("PASSWORD"))
			{
				log.error("Password may not be null");
				return;
			}
			if(!args[6].startsWith("ENTRY"))
			{
				log.error("Entry may not be null");
				return;
			}
			if(!args[7].startsWith("MEMBERS_FORMAT"))
			{
				log.error("Members Format may not be null, using default format\n usage: %s (name)");
				membersFormat = "%s";
			}
			else
			{
				membersFormat = args[7].replaceAll("%20", " ");
			}
		}
		
		final var __host = args[2].split("=")[LAST];
		final var __port = args[3].split("=")[LAST];
		final var __username = args[4].split("=")[LAST];
		final var __password = args[5].split("=")[LAST];
		final var __entry = args[6].split("=")[LAST];
		
		dataSource = new DataSourceFactory(__host, __port, __username, __password, __entry);
		
		final var __t = args[0].split("=")[LAST];
		final var __g = Long.parseLong(args[1].split("=")[LAST]);
		
		client = JDABuilder.createDefault(__t)
				.enableCache(CacheFlag.MEMBER_OVERRIDES)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS,
						GatewayIntent.GUILD_MESSAGES)
				.setChunkingFilter(ChunkingFilter.NONE)
				.setCompression(Compression.NONE)
				.setMemberCachePolicy(member -> !member.getUser().isBot())
				.addEventListeners(new SlashCommandListener())
				.build()
				.awaitReady();
		
		guild = client.getGuildById(__g);
		
		if(guild == null)
		{
			log.error("Guild is null, Guild Id has to be correct.");
			return;
		}
		
		guild.loadMembers()
				.onSuccess(x -> log.info("Loaded {} members",x.size()))
				.onError(x -> log.error(x.getLocalizedMessage()));
		
		var localCommands = LocalCommandsFetcher.getLocalCommandsAnnotations();
		
		guild.updateCommands()
				.addCommands(localCommands.stream()
						.map(command -> new CommandDataImpl(command.name(), command.description()))
						.toList())
				.queue(x -> log.info("Updated {} commands",x.size()));
	}
	
}