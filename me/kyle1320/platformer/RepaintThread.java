package me.kyle1320.platformer;

import javax.swing.JPanel;

/**	A Runnable which runs in a Thread and repaints a JPanel.
	@author Kyle Cutler
	@version 1/1/14
*/
public class RepaintThread implements Runnable {
	// Static constant with the amount of delay between repaints. Fixed to be about 60 fps.
	private static final int REPAINT_DELAY = 16;

	private JPanel repaint;
	private Thread thread;
	private volatile boolean running;

	/**	Creates a new RepaintThread that will repaint the given JPanel.
		@param repaint The JPanel to repaint
	*/
	public RepaintThread(JPanel repaint) {
		this.repaint = repaint;
	}

	/**	Creates a new Thread from this Runnable and starts it.
	*/
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Repainter");

		thread.start();
	}

	/**	Stops the currently running thread if there is one.
	*/
	public synchronized void stop() {
		if (thread == null)
			return;

		running = false;

		try{
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**	The Runnable method that runs in a Thread. Constantly repaints the JPanel assigned to this RepaintThread.
	*/
	public void run() {
		while (running) {
			repaint.repaint();	// since this only schedules a repaint in the AWT thread, we don't really ned to account for delay.

			try {
				Thread.sleep(REPAINT_DELAY);
			} catch (InterruptedException e) {}
		}
	}
}