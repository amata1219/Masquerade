package amata1219.masquerade.async;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import amata1219.masquerade.Masquerade;

public interface Async extends Runnable {

	void exe();

	public static AsyncTask define(Async task){
		return define(self -> task.exe());
	}

	public static AsyncTask define(Consumer<AsyncTask> processing){
		AsyncTask task = new AsyncTask(){

			@Override
			public void exe() {
				processing.accept(this);
			}

		};
		return task;
	}

	static abstract class AsyncTask implements Async {

		private BukkitTask activeTask;
		private long count;

		@Override
		public void run(){
			exe();
			count++;
		}

		public void execute(){
			executeLater(0);
		}

		public void executeLater(long delay){
			executeTimer(delay, -1);
		}

		public void executeTimer(long delay){
			executeTimer(delay, delay);
		}

		public void executeTimer(long delay, long period){
			activeTask = Bukkit.getScheduler().runTaskTimerAsynchronously(Masquerade.plugin(), this, delay, period);
		}

		public long count(){
			return count;
		}

		public void cancel(){
			if(!isCancelled()){
				activeTask.cancel();
				activeTask = null;
			}
		}

		public boolean isCancelled(){
			return activeTask == null;
		}

	}

}