package dev.sodiograaz.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;

/* @author Sodiograaz
 @since 01/05/2024
*/
public class ShardanaEmbedUtils
{
	
	private static final Guild guild = Application.getGuild();
	
	public static EmbedBuilder getBuilder(String description, MessageEmbed.Field ...fields)
	{
		var guildName = guild.getName();
		var guildIconUrl = guild.getIconUrl();
		
		EmbedBuilder embedBuilder = new EmbedBuilder()
				.setAuthor(guildName, guildIconUrl, guildIconUrl)
				.setDescription(description)
				.setTimestamp(Instant.now())
				.setFooter(guildName, guildIconUrl);
		
		for(var field : fields)
			embedBuilder.addField(field);
		
		return embedBuilder;
	}
	
	public static MessageEmbed getPrebuiltEmbed(String descrption, MessageEmbed.Field ...fields)
	{
		return getBuilder(descrption, fields).build();
	}
	
	
}