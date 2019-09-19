package amata1219.masquerade;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public interface AsyncTask extends Runnable {

	static final HashMap<AsyncTask, BukkitTask> RUNNING_TASKS = new HashMap<>();

	public static void main(String[] $){
		AtomicInteger counter = new AtomicInteger();
		define(task -> {
			if(counter.getAndIncrement() >= 10) task.cancel();
			else System.out.println();
		}).executeTimer(20);
	}

	public static AsyncTask define(Consumer<AsyncTask> processing){
		AsyncTask task = new AsyncTask(){

			@Override
			public void run() {
				processing.accept(this);
			}

		};
		return task;
	}

	default void execute(){
		executeLater(0);
	}

	default void executeLater(long delay){
		executeTimer(delay, -1);
	}

	default void executeTimer(long delay){
		executeTimer(delay, delay);
	}

	default void executeTimer(long delay, long period){
		BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Masquerade.plugin(), this, delay, period);
		RUNNING_TASKS.put(this, task);
	}

	default void cancel(){
		if(!isCancelled()) RUNNING_TASKS.remove(this).cancel();
	}

	default boolean isCancelled(){
		return RUNNING_TASKS.containsKey(this);
	}

}
