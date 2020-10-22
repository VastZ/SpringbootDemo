package com.example.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author zhang.wenhan
 * @description CyclicBarrierDemo
 * @date 2019/12/20 10:30
 */
public class CyclicBarrierDemo {
    /**
     * CyclicBarrier 的字面意思是可循环使用（Cyclic）的屏障（Barrier）。
     * 它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
     * 直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续工作。
     * CyclicBarrier 默认的构造方法是 CyclicBarrier(int parties)，
     * 其参数表示屏障拦截的线程数量，每个线程调用 await 方法告诉 CyclicBarrier
     * 当前线程已经到达了屏障，然后当前线程被阻塞
     * <p>
     * 使用场景 ：当存在需要所有的子任务都完成时，才执行主任务，这个时候就可以选择使用 CyclicBarrier
     */

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Thread(() -> System.out.println("开始进行数据分析")));
        new Thread(new DataImportThread(cyclicBarrier,"file1")).start();
        new Thread(new DataImportThread(cyclicBarrier,"file2")).start();
        new Thread(new DataImportThread(cyclicBarrier,"file3")).start();
    }

}

class DataImportThread extends Thread {
    private CyclicBarrier cyclicBarrier;
    private String path;

    public DataImportThread(CyclicBarrier cyclicBarrier, String path) {
        this.cyclicBarrier = cyclicBarrier;
        this.path = path;
    }

    @Override
    public void run() {

        try {
            System.out.println("等待导入：" + path + "位置的数据");
            //阻塞
            cyclicBarrier.await();
            System.out.println("开始导入：" + path + "位置的数据");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
