package dev.sodiograaz.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.sodiograaz.datasource.repositories.PartyPlayersRepository;
import dev.sodiograaz.datasource.repositories.PartyRepository;
import lombok.SneakyThrows;

import java.sql.Connection;

/* @author Sodiograaz
 @since 01/05/2024
*/
public class DataSourceFactory implements DataSource
{
	
	private final HikariConfig config = new HikariConfig();
	private HikariDataSource dataSource;
	
	public DataSourceFactory(String host,
	                         String port,
	                         String username,
	                         String password,
	                         String entryPoint) {
		this.config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s?autoReconnect=true",host,port,(entryPoint == null) ? "" : entryPoint));
		this.config.setUsername(username);
		this.config.setPassword(password);
	}
	
	@Override
	public DataSource initalizeDataSource() {
		this.dataSource = new HikariDataSource(this.config);
		return this;
	}
	
	@SneakyThrows
	@Override
	public Connection getConnection() {
		return this.dataSource.getConnection();
	}
	
	@Override
	public PartyRepository partyRepository() {
		return new PartyRepository(this.getConnection());
	}
	
	@Override
	public PartyPlayersRepository partyPlayersRepository() {
		return new PartyPlayersRepository(this.getConnection());
	}
}