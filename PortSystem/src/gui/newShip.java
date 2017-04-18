package gui;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import resources.*;

// TODO: Auto-generated Javadoc
/**
 * The Class newShip.
 */
public class newShip extends Shell {
	
	/** The ship name. */
	private static Text shipName;
	
	/** The display. */
	private static Display display;
	
	/** The composite. */
	private static Composite composite;
	
	/** The lbl ship name. */
	private static Label lblShipName;
	
	/** The combo priority. */
	private static Combo comboPriority;
	
	/** The add btn. */
	private static Button addBtn;
	
	/** The combo capacity. */
	private Combo comboCapacity;


	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public newShip() {
		super(display, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		setBackground(SWTResourceManager.getColor(153, 204, 204));
		setLayout(new FillLayout(SWT.HORIZONTAL));

		composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(153, 204, 204));

		lblShipName = new Label(composite, SWT.NONE);
		lblShipName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		lblShipName.setBackground(SWTResourceManager.getColor(153, 204, 204));
		lblShipName.setBounds(68, 35, 70, 18);
		lblShipName.setText("Ship name:");

		shipName = new Text(composite, SWT.BORDER);
		shipName.setBounds(144, 34, 114, 21);

		comboPriority = new Combo(composite, SWT.NONE);
		comboPriority.setBounds(68, 59, 75, 23);
		comboPriority.setText("-priority-");
		comboPriority.add("MAX");
		comboPriority.add("NORM");
		comboPriority.add("MIN");

		addBtn = new Button(composite, SWT.NONE);
		addBtn.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));

		addBtn.setBounds(188, 88, 70, 25);
		addBtn.setText("Add");

		comboCapacity = new Combo(composite, SWT.NONE);
		comboCapacity.setBounds(144, 59, 114, 23);
		comboCapacity.setText("-capacity-");
		for (int i = 1; i < 11; i++)
			comboCapacity.add(Integer.toString(i) + "0");

		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Structure.addNewShip(shipName.getText(),  comboPriority.getText(),
							comboCapacity.getText());
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVisible(false);
				comboPriority.setText("-priority-");
				comboCapacity.setText("-capacity-");
				shipName.setText("");
			}

		});

		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Add Ship");
		setSize(340, 191);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Decorations#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
