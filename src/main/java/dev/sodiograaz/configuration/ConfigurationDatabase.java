package dev.sodiograaz.configuration;

import lombok.Builder;
import lombok.Data;

/* @author Sodiograaz
 @since 02/05/2024
*/
@Data
@Builder
public class ConfigurationDatabase
{
	private String Host;
	private String Port;
	private String Username;
	private String Password;
	private String EntryPoint;
}