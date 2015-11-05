package me.kyle1320.platformer;

/**	A Runnable which runs in a Thread and updates an Updatable object.
	@author Kyle Cutler
	@version 1/1/14
*/
public class UpdateThread implements Runnable {
	private Updatable update;
	private Thread thread;
	private volatile boolean running;

	/**	Creates a new RepaintThread that will update the given Updatable.
		@param update The Updatable to update
	*/
	public UpdateThread(Updatable update) {
		this.update = update;
	}

	/**	Creates a new Thread from this Runnable and starts it.
	*/
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Updater");

		thread.start();
	}

	/**	Stops the currently running thread if there is one.
	*/
	public synchronized void stop() {
		running = false;

		try{
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**	The Runnable method that runs in a Thread. Constantly updates the Updatable assigned to this UpdateThread.
	*/
	public void run() {
		long lastUpdateTime = System.currentTimeMillis();
		long now;

		double updates = 0;

		while (running) {
			now = System.currentTimeMillis();
			updates += (double)(now - lastUpdateTime) / Updatable.UPDATE_DELAY;
			lastUpdateTime = now;

			while (updates >= 1) {
				update.update();
				updates--;
			}

			try {
				Thread.sleep(Updatable.UPDATE_DELAY);
			} catch (InterruptedException e) {}
		}
	}
}