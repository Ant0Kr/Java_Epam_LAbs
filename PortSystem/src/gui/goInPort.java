package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import models.Ship;
import resources.Structure;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class goInPort.
 */
public class goInPort extends Shell {

	/** The name. */
	private static Label name;
	
	/** The workload. */
	private static Label workload;
	
	/** The priority. */
	private static Label priority;
	
	/** The capacity. */
	private static Label capacity;
	
	/** The statistics. */
	private static Label statistics;
	
	/** The display. */
	private static Display display;
	
	/** The ship. */
	private Ship ship;
	
	
	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public goInPort(Ship shipNotShip) {
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setBackground(SWTResourceManager.getColor(153, 204, 204));

		this.ship = shipNotShip;

		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		nameLabel.setBounds(26, 27, 55, 15);
		nameLabel.setText("Name:");

		Label capacityLabel = new Label(this, SWT.NONE);
		capacityLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		capacityLabel.setBounds(26, 51, 55, 15);
		capacityLabel.setText("Capacity:");

		Label workLoadLabel = new Label(this, SWT.NONE);
		workLoadLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		workLoadLabel.setBounds(26, 72, 55, 15);
		workLoadLabel.setText("Workload:");

		Label priorityLabel = new Label(this, SWT.NONE);
		priorityLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		priorityLabel.setBounds(26, 93, 55, 15);
		priorityLabel.setText("Priority:");

		Combo comboTask = new Combo(this, SWT.NONE);
		comboTask.setBounds(26, 147, 197, 23);
		comboTask.setText("-choose task-");
		comboTask.add("Load");
		comboTask.add("Unload");

		Combo comboTime = new Combo(this, SWT.NONE);
		comboTime.setBounds(26, 205, 197, 23);
		comboTime.setText("-time for mooring(min)-");
		for (int i = 1; i < 13; i++) {
			comboTime.add(Integer.toString(5 * i));
		}

		name = new Label(this, SWT.NONE);
		name.setBackground(SWTResourceManager.getColor(153, 204, 204));
		name.setBounds(87, 27, 123, 15);
		name.setText(ship.getShipName());

		capacity = new Label(this, SWT.NONE);
		capacity.setBackground(SWTResourceManager.getColor(153, 204, 204));
		capacity.setBounds(87, 51, 123, 15);
		capacity.setText(Integer.toString(ship.getCapacity()));

		workload = new Label(this, SWT.NONE);
		workload.setBackground(SWTResourceManager.getColor(153, 204, 204));
		workload.setBounds(87, 72, 123, 15);
		workload.setText(Integer.toString(ship.getQueneSize()));

		priority = new Label(this, SWT.NONE);
		priority.setBackground(SWTResourceManager.getColor(153, 204, 204));
		priority.setBounds(87, 93, 123, 15);
		priority.setText(ship.getShipPriority());
		
		statistics = new Label(this, SWT.NONE);
		statistics.setBackground(SWTResourceManager.getColor(153, 204, 204));
		statistics.setBounds(87, 114, 55, 15);
		statistics.setText(ship.getStatistics());

		Combo comboContainers = new Combo(this, SWT.NONE);
		comboContainers.setBounds(26, 176, 197, 23);
		comboContainers.setText("-load/unload containers-");
		for (int i = 1; i < 100; i++) {
			comboContainers.add(Integer.toString(i));
		}

		Button btnRun = new Button(this, SWT.NONE);
		btnRun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (comboTime.getText().equals("-time for mooring(min)-"))
					ship.setAllTime(20);
				else
					ship.setAllTime(Integer.parseInt(comboTime.getText()));

				if (comboContainers.getText().equals("-load/unload containers-"))
					ship.setContainerTask(10);
				else
					ship.setContainerTask(Integer.parseInt(comboContainers.getText()));

				switch (comboTask.getText()) {
				case "Load":
					ship.setTask(true);
					break;
				case "Unload":
					ship.setTask(false);
					break;
				default:
					ship.setTask(false);
					break;
				}
				Structure.startThread(Structure.getNumShip(ship.getShipName()));
				comboTask.setText("-choose task-");
				comboTime.setText("-time for mooring(min)-");
				comboContainers.setText("-load/unload containers-");
				setVisible(false);

			}
		});
		btnRun.setBounds(87, 234, 75, 25);
		btnRun.setText("Run");
		
		Label shipStatistics = new Label(this, SWT.NONE);
		shipStatistics.setBackground(SWTResourceManager.getColor(153, 204, 204));
		shipStatistics.setBounds(26, 114, 55, 15);
		shipStatistics.setText("Statistics:");
		
		createContents();
	}

	/**
	 * Sets the values.
	 *
	 * @param shipNotShip the new values
	 */
	public void setValues(Ship shipNotShip) {
		this.ship = shipNotShip;
		name.setText(ship.getShipName());
		capacity.setText(Integer.toString(ship.getCapacity()));
		workload.setText(Integer.toString(ship.getQueneSize()));
		priority.setText(ship.getShipPriority());
		statistics.setText(ship.getStatistics());

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Port validation");
		setSize(265, 318);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Decorations#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
