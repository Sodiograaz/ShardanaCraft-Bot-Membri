package dev.sodiograaz.configuration;

import java.io.IOException;

/* @author Sodiograaz
 @since 02/05/2024
*/
public interface IConfigurationFactory
{
	Configuration loadConfiguration() throws IOException;
	Configuration getCached();
}