package dev.sodiograaz.bot.commands;

import dev.sodiograaz.bot.Application;
import dev.sodiograaz.bot.ShardanaEmbedUtils;
import dev.sodiograaz.bot.commands.utils.CommandAnnotation;
import dev.sodiograaz.bot.commands.utils.LocalCommand;
import dev.sodiograaz.datasource.DataSource;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

/* @author Sodiograaz
 @since 01/05/2024
*/
@CommandAnnotation(name = "membri", description = "Comando membri per le città")
public class MembersCommand implements LocalCommand
{
	
	private final DataSource dataSource = Application.getDataSource();
	private final String regex = "[^[a-zA-Z]]";
	
	@Override
	public void execute(SlashCommandInteractionEvent event)
	{
		var partyRepository = dataSource.partyRepository();
		var partyPlayersRepository = dataSource.partyPlayersRepository();
		
		var name = event.getMessageChannel().getName();
		var processedName = name.replaceAll(regex, "");
		
		var partyId = partyRepository.findOne(processedName);
		var partyMembersContextMap = partyPlayersRepository.findOne(partyId);
		
		final StringBuilder members = new StringBuilder("```");
	
		var format = Application.getMembersFormat();
		
		for(var x : partyMembersContextMap.entrySet())
		{
			members.append(String.format(format, x.getKey()))
					.append(";")
					.append("\n");
		}
		
		members.append("```");
		
		var embed = ShardanaEmbedUtils.getPrebuiltEmbed(String.format("Lista Membri per %s", processedName),
				new MessageEmbed.Field("Membri", members.toString(), true, true));
	
		event.reply(MessageCreateData.fromEmbeds(embed))
				.queue();
	}
	
}