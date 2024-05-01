package dev.sodiograaz.bot.listeners;

import dev.sodiograaz.bot.commands.utils.LocalCommand;
import dev.sodiograaz.bot.commands.utils.LocalCommandsFetcher;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/* @author Sodiograaz
 @since 01/05/2024
*/
public class SlashCommandListener extends ListenerAdapter
{
	private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(4, (thread) ->
	{
		Thread tr = new Thread(thread, "Command-Adapter");
		tr.setDaemon(true);
		return tr;
	});
	
	private static final List<LocalCommand> localCommands = LocalCommandsFetcher
			.localCommands();
	
	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
		var name = event.getName();
		
		for(var command : localCommands)
		{
			if(command.get().name().equals(name))
			{
				command.execute(event);
			}
		}
		
		super.onSlashCommandInteraction(event);
	}
}