package com.zk;

import com.contants.FrameWorkConstants;
import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 * @author LiXiaoFeng
 * @date 2021年11月29日 11:11
 */
public class ZkClinet extends FrameWorkConstants {



    private ZooKeeper zooKeeper;

    /**
     * 连接Zookeeper
     * @throws Exception
     */
    @Before
    public void init() throws Exception {
        zooKeeper = new ZooKeeper(ZKURL, TIMEOUT, (WatchedEvent var1) ->{
            /*List<String> children = null;
            try {
                children = zooKeeper.getChildren("/", true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String child : children) {
                System.out.println(child);
            }*/
        });

    }

    /**
     * 创建/IDEA节点值为“test api”
     */
    @Test
    public void createNode() {
        try {
            for (int i = 0; i < 3; i++) {
                zooKeeper.create("/servers/server"+i,"test api".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void watchNode() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        for(;;){

        }
    }

}
