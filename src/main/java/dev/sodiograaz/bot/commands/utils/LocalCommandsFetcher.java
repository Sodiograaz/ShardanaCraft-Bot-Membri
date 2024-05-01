package dev.sodiograaz.bot.commands.utils;

import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.util.LinkedList;
import java.util.List;

/* @author Sodiograaz
 @since 01/05/2024
*/
public class LocalCommandsFetcher
{
	
	@SneakyThrows
	public static List<CommandAnnotation> getLocalCommandsAnnotations() {
		var localCommandsList = new LinkedList<CommandAnnotation>();
		
		for (var command : localCommands())
		{
			var instance = command.get();
			localCommandsList.add(instance);
		}
		
		return localCommandsList;
	}
	
	@SneakyThrows
	public static List<LocalCommand> localCommands()
	{
		var localCommandsList = new LinkedList<LocalCommand>();
		var fetchedCommands = new Reflections("dev.sodiograaz.bot.commands")
				.getSubTypesOf(LocalCommand.class);
		
		for(var command : fetchedCommands)
		{
			var instance = command.getConstructor().newInstance();
			localCommandsList.add(instance);
		}
		
		return localCommandsList;
	}

}