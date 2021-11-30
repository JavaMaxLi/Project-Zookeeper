package com.distributedLock;

import com.contants.FrameWorkConstants;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper实现分布式锁
 * @author LiXiaoFeng
 * @date 2021年11月29日 17:57
 */
public class DistributedLockDemo extends FrameWorkConstants {
   private ZooKeeper zooKeeper;
   private CountDownLatch countDownLatch = new CountDownLatch(1);
   private CountDownLatch wait = new CountDownLatch(1);
    private String prevNode;
    private String currentNode;

   public DistributedLockDemo() {
       try {
           this.zooKeeper = new ZooKeeper(ZKURL, TIMEOUT, (WatchedEvent watchedEvent) -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }

                if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted && watchedEvent.getPath().equals(prevNode)) {
                    wait.countDown();
                }
           });

           //等待连接完成后执行初始化操作
           countDownLatch.await();
           //初始化操作
           Stat stat = zooKeeper.exists("/locks", false);
           if (stat == null) {
               zooKeeper.create("/locks","locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    /**
     *
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
   public boolean zkLock() throws KeeperException, InterruptedException {
       currentNode = zooKeeper.create("/locks/seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
       System.out.println("zkLock()===============node："+currentNode);
       List<String> children = zooKeeper.getChildren("/locks", false);

       if (children.size() == 1) {
           return true;
       } else {
           Collections.sort(children);
           int index = children.indexOf(currentNode.substring("/locks/".length()));
           if(index == -1) {
               throw new NullPointerException("空指针错误");
           } else if (index == 0) {
                return true;
           } else {
               //等待，监听前一个node，前一个node释放后执行后面代码
               prevNode = "/locks/" + children.get(index - 1);
               zooKeeper.getData(prevNode,true,null);
               System.out.println("正在监听前一个节点："+prevNode);
               wait.await();
               System.out.println("前面节点释放锁"+prevNode+"当前节点获取到锁"+currentNode);
               return true;
           }
       }

   }

   public void unZkLock() throws KeeperException, InterruptedException {
       zooKeeper.delete(currentNode,-1);
   }
}
