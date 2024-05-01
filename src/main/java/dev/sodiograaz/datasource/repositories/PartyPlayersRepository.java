package dev.sodiograaz.datasource.repositories;

import dev.sodiograaz.datasource.ReadRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* @author Sodiograaz
 @since 01/05/2024
*/
@AllArgsConstructor
public class PartyPlayersRepository implements ReadRepository<Map<String,String>, String>
{
	
	private static final Logger log = LoggerFactory.getLogger(PartyPlayersRepository.class);
	private final Connection connection;
	
	@Override
	public Map<String,String> findOne(String s)
	{
		var map = new LinkedHashMap<String,String>();
		
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM parties_players WHERE party = ?"))
		{
			statement.setString(1,s);
			var rs = statement.executeQuery();
			while(rs.next())
				map.put(rs.getString("nickname"), rs.getString("rank"));
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage());
		}
		
		return map;
	}
}