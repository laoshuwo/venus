package learn.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试CountDouwnLatch使用方法
 * @author xukai
 * @note 一个CountDouwnLatch实例是不能重复使用的，也就是说它是一次性的，锁一经被打开就不能再关闭使用了，如果想重复使用，请考虑使用CyclicBarrier。
 */
public class CountDownLatchTest {
	
	// 跑步者人数
	private int iRunnerNum = 10;
	// 开始比赛计时
	private CountDownLatch start = new CountDownLatch(1);
	// 比赛结束计时
	private CountDownLatch end = new CountDownLatch(iRunnerNum);
	// 选手比赛赛道
	private ExecutorService exec = Executors.newFixedThreadPool(iRunnerNum);
	
	public void runnerGames() throws Exception{
		for(int i=0;i<iRunnerNum;i++){
			final int iNO = i+1;
			Runnable runner = new Runnable(){
				@Override
				public void run() {
					try {
						// 等待开始比赛
						start.await();
						long iRunTime = (long)(Math.random()*10000);
						Thread.sleep(iRunTime);
						System.out.println("第"+iNO+"个选手到达终点,用时:"+iRunTime);
					} catch (InterruptedException e) {
					} finally {
						// 某个选手到达终点,比赛结束进程-1
						end.countDown();
					}
				}
			};
			// 选手上赛道
			exec.submit(runner);
		}
		// 比赛开始
		start.countDown();
		System.out.println("Game Start!");
		// 等待全部选手到达终点,比赛结束
		end.await();
		System.out.println("Game End!");
		exec.shutdown();
	}
	
	public static void main(String[] args) throws Exception{
		CountDownLatchTest t = new CountDownLatchTest();
		t.runnerGames();
	}
}
