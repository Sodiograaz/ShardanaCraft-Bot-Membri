package dev.sodiograaz.web.minealpha;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/* @author Sodiograaz
 @since 02/05/2024
*/
@Data
@Builder
public class Minealpha
{
	private String name;
	private String slug;
	private String address;
	private Integer position;
	private Integer votesTotal;
	private Integer votesToday;
	private Integer votesYesterday;
	private List<MinealphaVote> userVotes;
	private boolean hidden;
	private List<String> versions;
	private String versionsString;
	private boolean online;
	private Integer player;
	private Integer slot;
	private List<MinealphaStaff> staff;
	private List<String> categories;
	private Long lastVotesUpdate;
}