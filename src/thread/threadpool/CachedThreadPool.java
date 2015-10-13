package thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  无限制的提交任务，内存溢出
 *  
 *  java.lang.OutOfMemoryError: unable to create new native thread
 *
 */
public class CachedThreadPool {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();

		int i = 0;
		while (true) {
			System.out.println("execute runnalbe :" + i++);
			pool.execute(new TestRunnable());
		}
	}

	static class TestRunnable implements Runnable {
		@Override
		public void run() {
			try {
				TimeUnit.DAYS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
