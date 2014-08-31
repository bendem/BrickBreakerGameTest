package be.bendem.gametest.utils;

import be.bendem.gametest.core.logging.Logger;
import org.lwjgl.Sys;

import java.util.concurrent.Callable;

/**
 * @author bendem
 */
public class RepeatingTask implements Runnable {

    private final Thread thread;
    private final Runnable runnable;
    private final Runnable startup;
    private final long time;
    private long delay = 0;
    private volatile boolean cancelled = false;
    private volatile boolean idling    = false;
    private volatile boolean running   = false;
    private volatile boolean finished  = false;

    /**
     * Instanciate a task which will be repeated over time.
     *
     * @param runnable the task to run repeatidly
     * @param name Name of the thread handled by the class.
     * @param time Pause time between each repetition.
     */
    public RepeatingTask(Runnable runnable, String name, long time) {
        this(runnable, null, name, time);
    }

    /**
     * Instanciate a task which will be repeated over time.
     *
     * @param runnable the task to run repeatidly
     * @param startup the task to execute when the thread starts up
     * @param name the name of the thread handled by the class
     * @param time the time between each repetition
     */
    public RepeatingTask(Runnable runnable, Runnable startup, String name, long time) {
        thread = new Thread(this, name);
        this.runnable = runnable;
        this.startup = startup;
        this.time = time;
    }

    /**
     * Start a task with delay.
     *
     * @param delay Number of milliseconds to wait before starting the task.
     */
    public final void start(long delay) {
        this.delay = delay;
        start();
    }

    /**
     * Start the task.
     *
     * @throws IllegalStateException If the task is running or has run.
     */
    public final void start() {
        if(running || finished) {
            throw new IllegalStateException("Thread is already running or has run");
        }
        thread.start();
    }

    /**
     * Internal task handling, calling this will start the task on the main.
     * thread
     */
    @Override
    public final void run() {
        if(startup != null) {
            try {
                startup.run();
            } catch(Throwable e) {
                Logger.error("Couldn't start Repeating task " + thread.getName(), e);
                finished = true;
                return;
            }
        }
        running = true;
        if(delay > 0) {
            pause(delay);
        }
        long start;
        while(!cancelled) {
            start = Sys.getTime();
            try {
                runnable.run();
            } catch(Exception e) {
                Logger.error("Error while executing " + thread.getName(), e);
            }
            pause(time - (Sys.getTime() - start));
        }
        running = false;
        finished = true;
    }

    /**
     * Pause the current thread from defined number of milliseconds.
     * <p>
     * Note that the thread is marked as idling while paused.
     *
     * @param time The time the thread will pause.
     */
    protected final void pause(long time) {
        if(time < 1) {
            return;
        }
        idling = true;
        try {
            Thread.sleep(time);
        } catch(InterruptedException ignored) {
        } finally {
            idling = false;
        }
    }

    /**
     * Cancels the task
     */
    public final void cancel() {
        try {
            cancel(0);
        } catch(InterruptedException ignored) {}
    }

    /**
     * Cancels the task and wait for it to stop for a defined amount of
     * milliseconds.
     *
     * @param blockingTime The number of milliseconds to wait
     * @throws InterruptedException
     */
    public final void cancel(long blockingTime) throws InterruptedException {
        cancelled = true;
        if(idling) {
            thread.interrupt();
        }
        if(blockingTime > 0) {
            thread.join(blockingTime);
        }
    }

    /**
     * Checks if the task is currently running.
     *
     * @return True if the task is running, false otherwise.
     */
    public final boolean isRunning() {
        return running;
    }

    /**
     * Checks if the task is finished.
     *
     * @return True if the task is finished, false otherwise
     */
    public boolean isFinished() {
        return finished;
    }

}
