package thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 将有2个任务被拒绝
 * 
 * 核心线程池(5) + 线程等待队列(3) = 8 10 - 5 - 3 = 2
 * 
 */
public class FixedThreadPool {
	private static final int TASK_SIZE = 10;

	private static final int corePoolSize = 5;
	private static final int maximumPoolSize = 5;
	private static final long keepAliveTime = 60;
	private static final TimeUnit unit = TimeUnit.MINUTES;
	private static final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(
			3);
	private static final ThreadFactory threadFactory = new SimpleThreadFactory();
	private static final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			// 队列已经满，拒绝任务
			System.out.println("QSize:" + workQueue.size() + ",Rejected：" + r);
		}
	};

	public static void main(String[] args) {
		ExecutorService pool = new ThreadPoolExecutor(corePoolSize,
				maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
				handler);
		for (int i = 0; i < TASK_SIZE; i++) {
			pool.execute(new TestRunnable());
		}
	}

	static class TestRunnable implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName()
						+ " running");
				TimeUnit.DAYS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
