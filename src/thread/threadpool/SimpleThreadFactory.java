package thread.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadFactory implements ThreadFactory {
	private static AtomicInteger runnableCount = new AtomicInteger(1);

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "SimpleThread-" + runnableCount.getAndIncrement());
	}
}
