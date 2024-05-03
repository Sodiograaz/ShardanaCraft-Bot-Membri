package dev.sodiograaz.configuration;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/* @author Sodiograaz
 @since 02/05/2024
*/
@Data
@Builder
public class Configuration
{
	private String Token;
	private Long Guild;
	private String MembersFormat;
	
	private Long MinealphaVoteFetchTextChannel;
	private String MinealphaVoteFetchToken;
	private Long MinecraftItaliaNetVoteFetchTextChannel;
	private String MinecraftItaliaNetVoteFetchToken;
	
	private ConfigurationDatabase configurationDatabase;
	private List<String> StaffList;
}