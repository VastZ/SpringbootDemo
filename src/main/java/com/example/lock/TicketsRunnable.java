package com.example.lock;

class MyRunnable implements Runnable {
    
    private int ticketsCont = 500; // һ�����Ż�Ʊ

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
                    + "����1��Ʊ��ʣ��Ʊ��Ϊ��" + ticketsCont);
        }
    }
    
}

public class TicketsRunnable {

    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();
        // �����������߳�ģ����������
        Thread th1 = new Thread(mr, "����1");
        Thread th2 = new Thread(mr, "����2");
        Thread th3 = new Thread(mr, "����3");
        th1.start();
        th2.start();
        th3.start();
    }

}




//try {
//    // ����ͬ��������
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
//                    logger.error("[��ִ����][������Ļ�ִ����������][���߳�][���屣���ţ�" + ebizReceipt.getGrpContNo() + "���屣���ţ�" 
//                            + ebizReceipt.getGrpContNo() + "�����쳣][�쳣��"+ e.getMessage() + "]", e);
//                }
//                finally {
//                    // ͬ����������һ
//                    latch.countDown();
//                }
//            }
//        });
//    }
//    // ͬ���������ȴ�
//    latch.await();
//}
//catch (InterruptedException e) {
//    logger.error("[ǩ��������][������][���߳�][�����쳣][�쳣��" + e.getMessage() + "]", e);
//}