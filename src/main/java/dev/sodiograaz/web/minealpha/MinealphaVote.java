package dev.sodiograaz.web.minealpha;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/* @author Sodiograaz
 @since 02/05/2024
*/
@Data
@Builder
@ToString
public class MinealphaVote
{
	private String discordUsername;
	private String discordDiscriminator;
	private String discordID;
	private String votedAt;
	private String minecraftUUID;
	private String minecraftNickname;
}