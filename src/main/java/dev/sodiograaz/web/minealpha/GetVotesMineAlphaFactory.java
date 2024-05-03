package dev.sodiograaz.web.minealpha;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.sodiograaz.bot.Application;
import dev.sodiograaz.web.IGetVotesFactory;
import net.dv8tion.jda.internal.requests.FunctionalCallback;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/* @author Sodiograaz
 @since 02/05/2024
*/
public class GetVotesMineAlphaFactory implements IGetVotesFactory<Optional<Minealpha>>
{
	
	private static final HttpUrl.Builder httpBuilder = HttpUrl.parse("https://api.minealpha.it/v6/server-info/shardanacraft")
			.newBuilder();
	
	@Override
	public Optional<Minealpha> getVotesFrom(String token) throws IOException
	{
		httpBuilder.addQueryParameter("key",token);
		final var request = new Request.Builder()
				.url(httpBuilder.build())
				.build();
		
		final var response = Application.getOkhttpClient().newCall(request).execute();
		final var responseBody = response.body();
		final var responseInputStream = responseBody.byteStream();
		final var responseReaderInputStream = new InputStreamReader(responseInputStream);
		
		final var minealpha = new Gson().fromJson(responseReaderInputStream, Minealpha.class);
		
		return Optional.ofNullable(minealpha);
	}
}