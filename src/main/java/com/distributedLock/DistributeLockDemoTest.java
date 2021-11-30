package com.distributedLock;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LiXiaoFeng
 * @date 2021年11月30日 15:39
 */
public class DistributeLockDemoTest {
    public static Logger logger = LoggerFactory.getLogger(DistributeLockDemoTest.class);
    public static void main(String[] args) {
        DistributedLockDemo lock = new DistributedLockDemo();
        DistributedLockDemo lock1 = new DistributedLockDemo();
        DistributedLockDemo lock2 = new DistributedLockDemo();
        DistributedLockDemo lock3 = new DistributedLockDemo();

        new Thread(()->{
            try {
                lock.zkLock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
                Thread.sleep(5000);

                System.out.println(Thread.currentThread().getName()+"释放锁");
                lock.unZkLock();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AA").start();

        new Thread(()->{
            try {
                lock1.zkLock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
                Thread.sleep(5000);

                System.out.println(Thread.currentThread().getName()+"释放锁");
                lock1.unZkLock();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BB").start();
        new Thread(()->{
            try {
                lock2.zkLock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
                Thread.sleep(5000);

                System.out.println(Thread.currentThread().getName()+"释放锁");
                lock2.unZkLock();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"CC").start();

        new Thread(()->{
            try {
                lock3.zkLock();
                System.out.println(Thread.currentThread().getName()+"获取到锁");
                Thread.sleep(5000);

                System.out.println(Thread.currentThread().getName()+"释放锁");
                lock3.unZkLock();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"DD").start();
    }
}


