package dev.sodiograaz.datasource.repositories;

import dev.sodiograaz.datasource.ReadRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* @author Sodiograaz
 @since 01/05/2024
*/
@AllArgsConstructor
public class PartyRepository implements ReadRepository<String, String>
{
	
	private static final Logger log = LoggerFactory.getLogger(PartyRepository.class);
	private final Connection connection;
	
	@Override
	public String findOne(String s)
	{
		try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM parties_parties WHERE name = ?"))
		{
			statement.setString(1, s);
			var rs = statement.executeQuery();
			if(rs.next())
				return rs.getString("id");
		}
		catch (SQLException e)
		{
			log.error(e.getLocalizedMessage());
		}
		return "";
	}
}