package dev.sodiograaz.concurrency.threads;

import dev.sodiograaz.bot.Application;
import dev.sodiograaz.bot.ShardanaEmbedUtils;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/* @author Sodiograaz
 @since 02/05/2024
*/
public class MinealphaTask implements Runnable
{
	
	private static final Logger logger = LoggerFactory.getLogger(MinealphaTask.class);
	private final Long textChannelIdLong = Application.getConfiguration()
			.getMinealphaVoteFetchTextChannel();
	
	private final List<String> staffList = Application.getConfiguration()
			.getStaffList();
	
	private final String TOKEN = Application.getConfiguration()
			.getMinealphaVoteFetchToken();
	
	private OffsetDateTime until = OffsetDateTime.now(ZoneId.systemDefault())
			.plusDays(1);
	
	@Override
	public void run()
	{
		final var dateNow = OffsetDateTime.now(ZoneId.systemDefault());
		try
		{
			if(dateNow.isAfter(until))
			{
				final var responseOptional = Application.getMinealphaVoteFetcher()
						.getVotesFrom(TOKEN);
				
				final var data = responseOptional.orElseThrow(() -> new RuntimeException("Data is null"));
				
				final var stringBuffer = new StringBuilder("```");
				
				for(var vote : data.getUserVotes())
				{
					stringBuffer.append(String.format("%s", vote.getMinecraftNickname()))
							.append(", ")
							.append("\n");
				}
				
				stringBuffer.append("```");
				
				Application.getGuild()
						.getTextChannelById(textChannelIdLong)
						.sendMessage(MessageCreateData.fromEmbeds(ShardanaEmbedUtils.getPrebuiltEmbed("Voti per Minealpha",
								new MessageEmbed.Field("Voti", stringBuffer.toString(), false))))
						.queue();
				return;
			}
			until = until.plusDays(1);
		}
		catch (IOException exception)
		{
			logger.error(exception.getLocalizedMessage());
		}
	}
}