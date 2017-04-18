/*
 * 
 */
package resources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import models.Port;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckState.
 */
public class CheckState extends Thread {
	
	/** The port. */
	private Port port;
	
	/** The file name. */
	private String fileName = "Report.txt";

	/**
	 * Instantiates a new check state.
	 *
	 * @param port the port
	 */
	public CheckState(Port port) {
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			File file = new File(fileName);
			if (!file.exists())
				file.createNewFile();
			else {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		while (!Thread.interrupted()) {
			File file = new File(fileName);
			try {
				FileWriter writer = new FileWriter(file, true);
				String buf;
				buf = "Time[" + Calendar.getInstance().getTime() + "]\r\n" + "Port:" + port.getPortName() + "\r\n";
				String str = new String();
				for (int i = 0; i < port.getVectPierSize(); i++) {
					synchronized (port.getFullPierVector().get(i)) {
						str = "Pier ¹" + port.getFullPierVector().get(i).getPierName() + "\r\n"
								+ "Containers on storage:" + port.getFullPierVector().get(i).getStorageCount() + "\r\n";
						if (port.getFullPierVector().get(i).getShip() == null) {
							str = str + "Ship near pier: null.\r\n";
						} else {
							str = str + "Ship near pier:" + port.getFullPierVector().get(i).getShip().getShipName()
									+ "\r\n";
						}

					}
					buf = buf.concat(str);
				}
				buf = buf.concat("\r\n");
				writer.write(buf);
				writer.close();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
