package dev.sodiograaz.datasource;

import dev.sodiograaz.datasource.repositories.PartyPlayersRepository;
import dev.sodiograaz.datasource.repositories.PartyRepository;

import java.sql.Connection;

/* @author Sodiograaz
 @since 01/05/2024
*/
public interface DataSource
{
	DataSource initalizeDataSource();
	Connection getConnection();
	
	PartyRepository partyRepository();
	PartyPlayersRepository partyPlayersRepository();
	
}