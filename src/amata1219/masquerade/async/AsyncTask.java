package amata1219.masquerade.async;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import amata1219.masquerade.Masquerade;

public abstract class AsyncTask implements Runnable {
	
	public static AsyncTask define(Runnable processing){
		return define(self -> processing.run());
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

	private BukkitTask activeTask;
	private long count;

	@Override
	public void run(){
		exe();
		count++;
	}

	public abstract void exe();
	
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
