package dev.sodiograaz.web.minecraftitalia;

import com.google.gson.Gson;
import dev.sodiograaz.bot.Application;
import dev.sodiograaz.web.IGetVotesFactory;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

/* @author Sodiograaz
 @since 02/05/2024
*/
public class GetVotesMinecraftItaliaFactory implements IGetVotesFactory<Optional<MinecraftItaliaVote>>
{
	
	private static final HttpUrl.Builder httpUrlBuilder = HttpUrl.parse("https://minecraft-italia.net/lista/api/server/fetch")
			.newBuilder();
	
	@Override
	public Optional<MinecraftItaliaVote> getVotesFrom(String token) throws IOException {
		final var uri = httpUrlBuilder.addQueryParameter("apiKey", token)
				.build();
		
		final var request = new Request.Builder()
				.url(uri)
				.build();
		
		final var response = Application.getOkhttpClient()
				.newCall(request).execute();
		
		final var responseBody = response.body().byteStream();
		final var reader = new InputStreamReader(responseBody);
		final var result = new Gson()
				.fromJson(reader, MinecraftItaliaVote.class);
		
		return Optional.of(result);
	}
}