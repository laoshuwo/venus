package learn.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����CountDouwnLatchʹ�÷���
 * @author xukai
 * @note һ��CountDouwnLatchʵ���ǲ����ظ�ʹ�õģ�Ҳ����˵����һ���Եģ���һ�����򿪾Ͳ����ٹر�ʹ���ˣ�������ظ�ʹ�ã��뿼��ʹ��CyclicBarrier��
 */
public class CountDownLatchTest {
	
	// �ܲ�������
	private int iRunnerNum = 10;
	// ��ʼ������ʱ
	private CountDownLatch start = new CountDownLatch(1);
	// ����������ʱ
	private CountDownLatch end = new CountDownLatch(iRunnerNum);
	// ѡ�ֱ�������
	private ExecutorService exec = Executors.newFixedThreadPool(iRunnerNum);
	
	public void runnerGames() throws Exception{
		for(int i=0;i<iRunnerNum;i++){
			final int iNO = i+1;
			Runnable runner = new Runnable(){
				@Override
				public void run() {
					try {
						// �ȴ���ʼ����
						start.await();
						long iRunTime = (long)(Math.random()*10000);
						Thread.sleep(iRunTime);
						System.out.println("��"+iNO+"��ѡ�ֵ����յ�,��ʱ:"+iRunTime);
					} catch (InterruptedException e) {
					} finally {
						// ĳ��ѡ�ֵ����յ�,������������-1
						end.countDown();
					}
				}
			};
			// ѡ��������
			exec.submit(runner);
		}
		// ������ʼ
		start.countDown();
		System.out.println("Game Start!");
		// �ȴ�ȫ��ѡ�ֵ����յ�,��������
		end.await();
		System.out.println("Game End!");
		exec.shutdown();
	}
	
	public static void main(String[] args) throws Exception{
		CountDownLatchTest t = new CountDownLatchTest();
		t.runnerGames();
	}
}
