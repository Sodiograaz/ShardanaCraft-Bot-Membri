package dev.sodiograaz.web;

import java.io.IOException;

/* @author Sodiograaz
 @since 02/05/2024
*/
public interface IGetVotesFactory<T>
{
	
	T getVotesFrom(String token) throws IOException;

}