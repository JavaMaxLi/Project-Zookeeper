package com.zk;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;


/**
 * @author LiXiaoFeng
 * @date 2021年11月29日 11:11
 */
public class ZkClinet {


    private static String zkUrl = "101.43.13.93:2191";
    private ZooKeeper zooKeeper;

    @Test
    public void init() throws Exception {
        zooKeeper = new ZooKeeper(zkUrl, 4000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
        try {
            zooKeeper.create("/IDEA","test api".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
