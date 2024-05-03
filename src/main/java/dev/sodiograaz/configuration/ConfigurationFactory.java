package dev.sodiograaz.configuration;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;

/* @author Sodiograaz
 @since 02/05/2024
*/
public class ConfigurationFactory implements IConfigurationFactory
{
	private Configuration configurationCache;
	
	@Override
	public Configuration loadConfiguration() throws IOException
	{
		if(configurationCache != null)
			return getCached();
		
		@Nullable final var inputStream = this.getClass()
				.getClassLoader()
				.getResourceAsStream("application.toml");
		
		@Nullable final var inputStreamReader = new InputStreamReader(inputStream);
		
		TomlMapper tomlMapper = new TomlMapper();
		
		final var result = tomlMapper.readValue(inputStreamReader, Configuration.class);
		
		configurationCache = result;
		
		return result;
	}
	
	@Override
	public Configuration getCached() {
		return configurationCache;
	}
}