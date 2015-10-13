package thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 将有2个任务被拒绝
 * 
 * 核心线程池(5) + 线程等待队列(3) = 8
 * 10 - 5 - 3 = 2
 * 
 */
public class FixedThreadPool {
	public static void main(String[] args) {
		ExecutorService pool = new ThreadPoolExecutor(5, 5, 5000,
				TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(3),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r,
							ThreadPoolExecutor executor) {
						System.out.println("Rejected-------> " + r);
					}
				});
		for (int i = 0; i < 10; i++) {
			pool.execute(new TestRunnable());
		}
	}

	static class TestRunnable implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("running");
				TimeUnit.DAYS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
