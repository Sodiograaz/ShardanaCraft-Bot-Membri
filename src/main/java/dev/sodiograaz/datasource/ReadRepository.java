package dev.sodiograaz.datasource;

/* @author Sodiograaz
 @since 01/05/2024
*/
public interface ReadRepository<T,Q>
{
	
	T findOne(Q q);
	
}