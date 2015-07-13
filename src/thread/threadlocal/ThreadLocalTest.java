package thread.threadlocal;

import java.util.UUID;

/**
 * 1、同一线程运行在不同方法中时，方便地保存、获取数据，而不需要通过方法传递参数
 * 2、ThreadLocal不是用来解决多线程访问同一变量的问题，ThreadLocal中缓存的数据只有当前线程访问，没有数据竞争，所以不需要加锁
 * 3、线程结束时记得清除缓存
 */
public class ThreadLocalTest {
	static SessionThreadLocal sessionThreadLocal = new SessionThreadLocal();

	public static void main(String[] args) {
		new Thread(new SessionRunnale()).start();
		new Thread(new SessionRunnale()).start();
	}

	public static void runMethod1() {
		Session session = sessionThreadLocal.get();
		System.out.println("Method1 tid:" + Thread.currentThread().getId()
				+ "," + session);
	}

	public static void runMethod2() {
		Session session = sessionThreadLocal.get();
		System.out.println("Method2 tid:" + Thread.currentThread().getId()
				+ "," + session);
	}

	/**
	 * Runnable
	 */
	static class SessionRunnale implements Runnable {
		@Override
		public void run() {
			try {
				initSession();
				runMethod1();
				runMethod2();
			} catch (Throwable t) {
				t.printStackTrace();
			} finally {
				// 清理缓存
				sessionThreadLocal.remove();
			}
		}

		private void initSession() {
			Session session = sessionThreadLocal.get();
			System.out.println("RUN tid:" + Thread.currentThread().getId()
					+ "," + session);
		}
	}

	/**
	 * ThreadLocal
	 */
	static class SessionThreadLocal extends ThreadLocal<Session> {
		@Override
		protected Session initialValue() {
			Session session = new Session();
			session.setId(UUID.randomUUID().toString());
			return session;
		}
	}

	/**
	 * Session
	 */
	static class Session {
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Session [id=");
			builder.append(id);
			builder.append("]");
			return builder.toString();
		}
	}
}
