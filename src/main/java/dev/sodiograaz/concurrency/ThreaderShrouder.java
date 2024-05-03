package dev.sodiograaz.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* @author Sodiograaz
 @since 02/05/2024
*/
public class ThreaderShrouder
{
	
	private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = (ScheduledThreadPoolExecutor)
			Executors.newScheduledThreadPool(4, (th) ->
			{
				final var thread = new Thread(th, "Vote Fetcher Thread Pool Executor");
				thread.setDaemon(true);
				return thread;
			});
	
	public static void execute(Runnable runnable, long delay, long period, TimeUnit timeUnit)
	{
		SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(runnable,delay,period,timeUnit);
	}
	
}