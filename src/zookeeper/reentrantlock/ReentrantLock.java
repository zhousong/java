package zookeeper.reentrantlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by helloworld on 16/4/18.
 */
public class ReentrantLock {
    private String lockName;
    private final int timeOut = 3000;
    private final String root = "/locks";
    private String myZnode;// 代表当前节点信息
    private String waitZnode;
    private static Logger logger = LoggerFactory
            .getLogger(ReentrantLock.class);
    private CuratorFramework client;
    private CountDownLatch latch = new CountDownLatch(1);

    public ReentrantLock(String server, String lockName) {
        this.lockName = lockName;
        client = CuratorFrameworkFactory.builder().connectionTimeoutMs(timeOut)
                .connectString(server)
                .retryPolicy(new RetryNTimes(3, 3000)).build();
        ConnectionStateListener listener = new ConnectionStateListener() {
            public void stateChanged(CuratorFramework client,
                                     ConnectionState newState) {
                if (newState == ConnectionState.CONNECTED) {
                    logger.info("连接成功了");
                    latch.countDown();
                }
            }
        };

        client.getConnectionStateListenable().addListener(listener);
        client.start();
        try {
            latch.await();
            createRoot();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void createRoot() {
        try {
            Stat stat = client.checkExists().forPath(root);
            if (stat != null) {
                logger.info("root has already exists");
            } else {
                // 创建跟节点
                client.create().creatingParentsIfNeeded().forPath(root);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLocks() {
        try {
            myZnode = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(root + "/" + lockName);
            logger.info(myZnode + "has created");
            // 取出所有的子节点，然后找出比自己小的节点，进行监听的设置
            List<String> subNodes = client.getChildren().forPath(root);
            // 取出所有带有lockname的节点信息
            List<String> lockObjNodes = new ArrayList<String>();
            for (String node : subNodes) {
                if (node.contains(lockName)) {
                    lockObjNodes.add(node);
                }
            }
            // 对当前节点进行排序
            Collections.sort(lockObjNodes);
            // 判断当前的节点是不是最小的节点
            if (myZnode.equals(root + "/" + lockObjNodes.get(0))) {
                doAction();
            } else {
                // 找到比自己节点大一的节点进行监听
                String subMyZone = myZnode
                        .substring(myZnode.lastIndexOf("/") + 1);
                waitZnode = lockObjNodes.get(Collections.binarySearch(
                        lockObjNodes, subMyZone) - 1);
                // 对节点进行监听
                Stat stat = client.checkExists()
                        .usingWatcher(deleteNodeWatcher).forPath("/" + waitZnode);
                if (stat != null) {
                    System.out.println(Thread.currentThread().getName()
                            + "处于等待状态");
                } else {
                    doAction();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    // 删除节点的事件监听
    CuratorWatcher deleteNodeWatcher = new CuratorWatcher() {
        public void process(WatchedEvent event) throws Exception {
            if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                doAction();
            }
        }
    };

    private void doAction() {
        System.out.println(Thread.currentThread().getName() + "开始执行");
        client.close();
    }

    public static void main(String[] args) throws Exception {
        Thread threads[] = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    ReentrantLock client = new ReentrantLock(
                            "127.0.0.1:2181;127.0.0.1:2182;127.0.0.1:2183", "locknametest");
                    client.getLocks();
                }
            });

            threads[i].start();

        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}

