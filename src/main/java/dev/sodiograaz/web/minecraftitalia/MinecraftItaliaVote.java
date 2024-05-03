package dev.sodiograaz.web.minecraftitalia;

import lombok.Builder;
import lombok.Data;

/* @author Sodiograaz
 @since 02/05/2024
*/
@Data
@Builder
public class MinecraftItaliaVote
{
	private Integer id,server_id;
	private String timestamp,username;
}