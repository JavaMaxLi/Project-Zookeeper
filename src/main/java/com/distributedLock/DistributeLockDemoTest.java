package com.distributedLock;

import org.apache.zookeeper.KeeperException;

/**
 * @author LiXiaoFeng
 * @date 2021年11月30日 15:39
 */
public class DistributeLockDemoTest {
    public static void main(String[] args) {
        DistributedLockDemo lock = new DistributedLockDemo();
        DistributedLockDemo lock1 = new DistributedLockDemo();

        new Thread(()->{
            try {
                lock.zkLock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
                Thread.sleep(5000);

                lock.unZkLock();
                System.out.println(Thread.currentThread().getName()+"释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                lock1.zkLock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
                Thread.sleep(5000);

                lock1.unZkLock();
                System.out.println(Thread.currentThread().getName()+"释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
