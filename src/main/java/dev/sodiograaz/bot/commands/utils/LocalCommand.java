package dev.sodiograaz.bot.commands.utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.Nullable;

/* @author Sodiograaz
 @since 01/05/2024
*/
public interface LocalCommand
{
	
	void execute(SlashCommandInteractionEvent event);
	
	default @Nullable CommandAnnotation get()
	{
		return this.getClass()
				.getAnnotation(CommandAnnotation.class);
	}
	
}