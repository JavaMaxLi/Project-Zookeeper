package com.zk;

import com.contants.FrameWorkConstants;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author LiXiaoFeng
 * @date 2021年11月29日 16:45
 */
public class ZKServersInit extends FrameWorkConstants {

    public static void main(String[] args) throws Exception {
        ZKServersInit ZKServersInit = new ZKServersInit();
        ZKServersInit.getZkConnection();
        for (int i = 1; i <= 3; i++) {
            ZKServersInit.register("/servers/server"+i,"192.168.34.15"+i+":8080");
        }
    }

    /**
     * 开启连接
     * @return
     * @throws IOException
     */
    public ZooKeeper getZkConnection() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKURL, TIMEOUT, (var1) -> {

        });
        return zooKeeper;
    }

    /**
     * 服务器注册服务到Zookeeper节点
     * @param path
     * @param value
     * @throws Exception
     */
    public void register(String path, String value) throws Exception {
        getZkConnection().create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

}
