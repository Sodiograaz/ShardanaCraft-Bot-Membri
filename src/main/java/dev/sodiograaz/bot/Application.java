package dev.sodiograaz.bot;

import dev.sodiograaz.bot.commands.utils.LocalCommandsFetcher;
import dev.sodiograaz.bot.listeners.SlashCommandListener;
import dev.sodiograaz.concurrency.ThreaderShrouder;
import dev.sodiograaz.concurrency.threads.MinealphaTask;
import dev.sodiograaz.concurrency.threads.MinecraftItaliaNetTask;
import dev.sodiograaz.configuration.Configuration;
import dev.sodiograaz.configuration.ConfigurationFactory;
import dev.sodiograaz.datasource.DataSource;
import dev.sodiograaz.datasource.DataSourceFactory;
import dev.sodiograaz.web.IGetVotesFactory;
import dev.sodiograaz.web.minealpha.GetVotesMineAlphaFactory;
import dev.sodiograaz.web.minealpha.Minealpha;
import dev.sodiograaz.web.minecraftitalia.GetVotesMinecraftItaliaFactory;
import dev.sodiograaz.web.minecraftitalia.MinecraftItaliaVote;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/* @author Sodiograaz
 @since 01/05/2024
*/
public class Application
{
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static final @Getter OkHttpClient okhttpClient = new OkHttpClient.Builder()
			.build();
	private static final @Getter IGetVotesFactory<Optional<Minealpha>> minealphaVoteFetcher =
			new GetVotesMineAlphaFactory();
	private static final @Getter IGetVotesFactory<Optional<MinecraftItaliaVote>> minecraftItaliaVoteFetcher =
			new GetVotesMinecraftItaliaFactory();
	private static @Getter Configuration configuration;
	private static @Getter DataSource dataSource;
	private static @Getter JDA client;
	private static @Getter @Nullable Guild guild;
	
	static {
		try {
			configuration = new ConfigurationFactory()
					.loadConfiguration();
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		dataSource = new DataSourceFactory(configuration.getConfigurationDatabase().getHost(),
				configuration.getConfigurationDatabase().getPort(),
				configuration.getConfigurationDatabase().getUsername(),
				configuration.getConfigurationDatabase().getPassword(),
				configuration.getConfigurationDatabase().getEntryPoint());
		
		client = JDABuilder.createDefault(configuration.getToken())
				.enableCache(CacheFlag.MEMBER_OVERRIDES)
				.enableIntents(GatewayIntent.MESSAGE_CONTENT,GatewayIntent.GUILD_MEMBERS,
						GatewayIntent.GUILD_MESSAGES)
				.setChunkingFilter(ChunkingFilter.NONE)
				.setCompression(Compression.NONE)
				.setMemberCachePolicy(member -> !member.getUser().isBot())
				.addEventListeners(new SlashCommandListener())
				.build()
				.awaitReady();
		
		guild = client.getGuildById(configuration.getGuild());
		
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
		
		// Minealpha
		final var mineAlphaToken = getConfiguration().getMinealphaVoteFetchToken();
		final var mineAlphaTextChannelId = getConfiguration().getMinealphaVoteFetchTextChannel();
		
		// MinecraftItaliaNet
		final var minecraftItaliaNetToken = getConfiguration().getMinecraftItaliaNetVoteFetchToken();
		final var minecraftItaliaNetTextChannelId = getConfiguration().getMinecraftItaliaNetVoteFetchTextChannel();
		
		
		if ((mineAlphaToken == null || mineAlphaToken.isEmpty()) &&
				(mineAlphaTextChannelId == null || mineAlphaTextChannelId == 0L))
		{
			log.error("Minealpha task will not start due to token and textChannel missing");
			return;
		}
		ThreaderShrouder.execute(new MinealphaTask(), 1, 1, TimeUnit.DAYS);
		if( (minecraftItaliaNetToken == null || minecraftItaliaNetToken.isEmpty()) &&
				(minecraftItaliaNetTextChannelId == null || minecraftItaliaNetTextChannelId == 0))
		{
			log.error("MinecraftItalia.Net task will not start due to token and textChannel missing");
			return;
		}
		ThreaderShrouder.execute(new MinecraftItaliaNetTask(), 1, 1, TimeUnit.DAYS);
	}
	
}