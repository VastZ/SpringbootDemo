package com.example.lock;

class MyRunnable implements Runnable {
    
    private int ticketsCont = 500; // 一共五张火车票

    @Override
    public void run() {
        while(ticketsCont > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ticketsCont--;
            System.out.println(Thread.currentThread().getName()
                    + "卖了1张票，剩余票数为：" + ticketsCont);
        }
    }
    
}

public class TicketsRunnable {

    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();
        // 创建是三个线程模拟三个窗口
        Thread th1 = new Thread(mr, "窗口1");
        Thread th2 = new Thread(mr, "窗口2");
        Thread th3 = new Thread(mr, "窗口3");
        th1.start();
        th2.start();
        th3.start();
    }

}




//try {
//    // 创建同步计数器
//    final CountDownLatch latch = new CountDownLatch(ebizReceiptLst.size());
//    ExecutorService executorService = Executors.newFixedThreadPool(3);
//    for (final EbizReceipt ebizReceipt : ebizReceiptLst) {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    logger.info(ebizReceipt + "*********************************");
//                    receiptService.receiptToLis(ebizReceipt);
//                }
//                catch (Exception e) {
//                    logger.error("[回执回销][导入核心回执申请批处理][多线程][团体保单号：" + ebizReceipt.getGrpContNo() + "团体保单号：" 
//                            + ebizReceipt.getGrpContNo() + "发生异常][异常："+ e.getMessage() + "]", e);
//                }
//                finally {
//                    // 同步计数器减一
//                    latch.countDown();
//                }
//            }
//        });
//    }
//    // 同步计数器等待
//    latch.await();
//}
//catch (InterruptedException e) {
//    logger.error("[签单导核心][批处理][多线程][发生异常][异常：" + e.getMessage() + "]", e);
//}