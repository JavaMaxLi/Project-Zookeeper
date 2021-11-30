package com.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author LiXiaoFeng
 * @date 2021年11月30日 16:19
 */
public class CuratorApiTest {
    public static void main(String[] args) {
        InterProcessMutex lock = new InterProcessMutex(getCuratorFramework(),"/locks");
        InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(),"/locks");
        new Thread(()-> {
            try {
                lock.acquire();
                System.out.println(Thread.currentThread().getName()+"lock");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName()+"unlock");
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"AA").start();

        new Thread(()-> {
            try {
                lock2.acquire();
                System.out.println(Thread.currentThread().getName()+"lock");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName()+"unlock");
                lock2.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"BB").start();
    }

    public static CuratorFramework getCuratorFramework() {
        CuratorFramework build = CuratorFrameworkFactory.builder().connectString("101.43.13.93:2181,101.43.13.93:2191,101.43.13.93:2183").connectionTimeoutMs(2000)
                .sessionTimeoutMs(2000)
                .retryPolicy(new ExponentialBackoffRetry(2000, 3)).build();
        //启动
        build.start();
        return build;
    }
}
