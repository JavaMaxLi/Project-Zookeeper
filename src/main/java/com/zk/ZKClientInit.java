package com.zk;

import com.contants.FrameWorkConstants;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiXiaoFeng
 * @date 2021年11月29日 17:14
 */
public class ZKClientInit extends FrameWorkConstants {


    private ZooKeeper zooKeeper;
    @Before
    public void clientConnection() throws Exception {
        zooKeeper = new ZooKeeper(ZKURL, TIMEOUT, (var1) -> {
            try {
                clientWatch();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void clientWatch() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/servers", true);
        ArrayList<String> serverLists = new ArrayList<>();
        System.out.println("----------------start----------------");
        children.forEach((child)->{
            System.out.println("--------child--------"+child);
            try {
                byte[] data = zooKeeper.getData("/servers/" + child,
                        false, null);
                serverLists.add(new String(data));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(serverLists);
        System.out.println("----------------end----------------");
    }
}
