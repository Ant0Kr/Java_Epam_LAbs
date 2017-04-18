package gui;

import resources.GeneratePDF;
import resources.Structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import com.itextpdf.text.DocumentException;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Pier;
import models.Port;
import models.Ship;
import models.containerGood;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main extends Shell {

	/** The table. */
	private static Table table;

	/** The pier table. */
	private static Table pierTable;

	/** The ship table. */
	private static Table shipTable;

	/** The shell. */
	private static Shell shell;

	/** The display. */
	private static Display display;

	/** The composite. */
	private static Composite composite;

	/** The combo ship. */
	private static Combo comboShip;

	/** The combo pier. */
	private static Combo comboPier;

	/** The add wnd. */
	private static newShip addWnd;

	/** The go in. */
	private static goInPort goIn;

	/** The ship label. */
	private static Label shipLabel;

	/** The pier label. */
	private static Label pierLabel;

	/** The vector ship size. */
	private static int vectorShipSize = 0;

	/** The vector pier size. */
	private static int vectorPierSize = 0;

	/** The go in btn. */
	private static Button goInBtn;

	/** The add pier btn. */
	private static Button addPierBtn;

	/** The add ship btn. */
	private static Button addShipBtn;

	/** The lbl new label. */
	private Label lblNewLabel;

	/** The Time. */
	private static Label Time;
	private Label label;

	private static Button ExportPier;
	private static Button ExportShip;

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            the arguments
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static void main(String args[]) throws InterruptedException, ClassNotFoundException, SQLException {

		Structure.startInit();
		try {
			display = Display.getDefault();
			shell = new Main();
			shell.open();
			shell.layout();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					if (!table.isDisposed()) {
						repaintMainTable();
					}
					if (!comboShip.isDisposed()) {
						repaintComboShip();
					}
					if (!comboPier.isDisposed()) {

						repaintComboPier();
					}
					if (!Time.isDisposed()) {
						repaintTimeLabel();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public Main() throws InterruptedException {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage("C:\\Users\\Antoha12018\\Desktop\\Maritime2.png"));
		setLayout(new FillLayout(SWT.HORIZONTAL));

		composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(153, 204, 204));

		shipLabel = new Label(composite, SWT.NONE);
		shipLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		shipLabel.setBounds(327, 280, 245, 15);

		pierLabel = new Label(composite, SWT.NONE);
		pierLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		pierLabel.setBounds(10, 280, 213, 15);

		createSourceMainTable(); // create table before Threads start

		createSourcePierTable();

		createSourceShipTable();

		goInBtn = new Button(composite, SWT.NONE);
		goInBtn.setBounds(578, 332, 75, 25);
		goInBtn.setText("Go in port");
		goInBtn.setEnabled(false);
		goInBtn.addSelectionListener(goInSelection());

		addPierBtn = new Button(composite, SWT.NONE);
		addPierBtn.addSelectionListener(addPierBtnSelection());
		addPierBtn.setBounds(578, 63, 75, 25);
		addPierBtn.setText("Add pier");

		addShipBtn = new Button(composite, SWT.NONE);
		addShipBtn.addSelectionListener(addShipBtnSelection());
		addShipBtn.setBounds(578, 301, 75, 25);
		addShipBtn.setText("Add ship");

		comboPier = new Combo(composite, SWT.NONE);
		comboPier.setBounds(10, 251, 138, 23);
		comboPier.setText("-piers-");
		comboPier.addSelectionListener(comboPierSelection());

		comboShip = new Combo(composite, SWT.NONE);
		comboShip.setBounds(327, 251, 145, 23);
		comboShip.setText("-ships-");

		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage("C:\\Users\\Antoha12018\\Desktop\\myImage.png"));
		lblNewLabel.setBackground(SWTResourceManager.getColor(153, 204, 204));
		lblNewLabel.setBounds(10, 0, 64, 57);

		Time = new Label(composite, SWT.NONE);
		Time.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Time.setBackground(SWTResourceManager.getColor(153, 204, 204));
		Time.setBounds(80, 32, 266, 25);

		Label lblPortsinyavka = new Label(composite, SWT.NONE);
		lblPortsinyavka.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblPortsinyavka.setBackground(SWTResourceManager.getColor(153, 204, 204));
		lblPortsinyavka.setBounds(80, 10, 213, 32);
		lblPortsinyavka.setText("Port:Sinyavka");

		Label lblByAntonKatachun = new Label(composite, SWT.NONE);
		lblByAntonKatachun.setAlignment(SWT.CENTER);
		lblByAntonKatachun.setText("All rights reserved.");
		lblByAntonKatachun.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblByAntonKatachun.setBackground(SWTResourceManager.getColor(153, 204, 204));
		lblByAntonKatachun.setBounds(0, 542, 663, 15);

		label = new Label(composite, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setText("By Anton Karachun,550504. Bsuir/Epam/Lab2/MultiThreading.");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		label.setBackground(SWTResourceManager.getColor(153, 204, 204));
		label.setBounds(0, 527, 663, 15);

		ExportPier = new Button(composite, SWT.NONE);
		ExportPier.setEnabled(false);
		ExportPier.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		ExportPier.setBounds(180, 249, 75, 25);
		ExportPier.setText("Export PDF");
		ExportPier.addSelectionListener(exportPierSelection());

		ExportShip = new Button(composite, SWT.NONE);
		ExportShip.setEnabled(false);
		ExportShip.setBounds(497, 251, 75, 25);
		ExportShip.setText("Export PDF");
		ExportShip.addSelectionListener(exportShipSelection());
		comboShip.addSelectionListener(comboShipSelection());

		this.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Structure.getGheckThread().interrupt();

			}

		});

		createContents();
	}

	/**
	 * Creates the source main table.
	 */
	public void createSourceMainTable() {

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 63, 562, 182);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNumberOfPier = new TableColumn(table, SWT.CENTER);
		tblclmnNumberOfPier.setWidth(62);
		tblclmnNumberOfPier.setText("Pier");

		TableColumn tblclmnShip = new TableColumn(table, SWT.CENTER);
		tblclmnShip.setWidth(118);
		tblclmnShip.setText("Ship");

		TableColumn tblclmnStatus = new TableColumn(table, SWT.CENTER);
		tblclmnStatus.setWidth(96);
		tblclmnStatus.setText("Status");

		TableColumn tblclmnStorage = new TableColumn(table, SWT.NONE);
		tblclmnStorage.setWidth(58);
		tblclmnStorage.setText("Storage");

		TableColumn tblclmnTimemin = new TableColumn(table, SWT.CENTER);
		tblclmnTimemin.setWidth(86);
		tblclmnTimemin.setText("All time(min)");

		TableColumn tblclmnAllTimemin = new TableColumn(table, SWT.NONE);
		tblclmnAllTimemin.setWidth(137);
		tblclmnAllTimemin.setText("Time for mooring(min)");

	}

	/**
	 * Creates the source pier table.
	 */
	public void createSourcePierTable() {

		pierTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		pierTable.setBounds(10, 301, 245, 203);
		pierTable.setHeaderVisible(true);
		pierTable.setLinesVisible(true);

		TableColumn tblclmnNameOfGood = new TableColumn(pierTable, SWT.CENTER);
		tblclmnNameOfGood.setWidth(174);
		tblclmnNameOfGood.setText("Name of good");

		TableColumn tblclmnCount_1 = new TableColumn(pierTable, SWT.NONE);
		tblclmnCount_1.setWidth(67);
		tblclmnCount_1.setText("Count");

	}

	/**
	 * Creates the source ship table.
	 */
	public void createSourceShipTable() {

		shipTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		shipTable.setBounds(327, 301, 245, 203);
		shipTable.setHeaderVisible(true);
		shipTable.setLinesVisible(true);

		TableColumn tblclmnName = new TableColumn(shipTable, SWT.CENTER);
		tblclmnName.setWidth(174);
		tblclmnName.setText("Name of good");

		TableColumn tblclmnCount = new TableColumn(shipTable, SWT.NONE);
		tblclmnCount.setWidth(66);
		tblclmnCount.setText("Count");
	}

	public static SelectionAdapter exportShipSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
				fileDialog.setText("Save Document");
				fileDialog.setFilterExtensions(new String[] { "*.pdf", "*.*" });
				fileDialog.setFilterNames(new String[] { "PDF Format", "Any" });
				String file = fileDialog.open();
				if (file != null) {
					file = file + ".pdf";
					try {
						Ship ship = Structure.getRealShip(comboShip.getText());
						GeneratePDF.createPDF(file,ship.getQueneGood(), true);
					} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException
							| InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		};

	}
	public static SelectionAdapter exportPierSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
				fileDialog.setText("Save Document");
				fileDialog.setFilterExtensions(new String[] { "*.pdf", "*.*" });
				fileDialog.setFilterNames(new String[] { "PDF Format", "Any" });
				String file = fileDialog.open();
				if (file != null) {
					file = file + ".pdf";
					try {
						Pier pier = Structure.getPort().getRealPier(comboPier.getText());
						GeneratePDF.createPDF(file,pier.getQueneGood(), true);
					} catch (FileNotFoundException | ClassNotFoundException | DocumentException | SQLException
							| InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		};

	}

	/**
	 * Go in selection.
	 *
	 * @return the selection adapter
	 */
	public static SelectionAdapter goInSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				goInBtn.setEnabled(false);
				ExportShip.setEnabled(false);
				if (goIn == null || goIn.isDisposed()) {
					goIn = new goInPort(Structure.getRealShip(comboShip.getText()));
					goIn.open();
				} else {
					goIn.setVisible(true);
					goIn.setValues(Structure.getRealShip(comboShip.getText()));
				}

			}

		};
	}

	/**
	 * Combo pier selection.
	 *
	 * @return the selection adapter
	 */
	public static SelectionAdapter comboPierSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String Label = Structure.getPort().getPierCombo(comboPier.getText());
				if (Label == "-pier-")
					ExportPier.setEnabled(false);
				else
					ExportPier.setEnabled(true);
				if (Label == "null") {
					pierLabel.setText("");
				} else
					pierLabel.setText("Pier ¹" + Label);
				repaintTablePier();
			}
		};
	}

	/**
	 * Combo ship selection.
	 *
	 * @return the selection adapter
	 */
	public static SelectionAdapter comboShipSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String Label = Structure.getShip(comboShip.getText());
				String buf = "";
				if (Label == "null")
					buf.concat("");
				else
					buf = buf + "Ship:" + Label;
				if (Structure.getRealShip(comboShip.getText()).getFlagInPort()) {
					goInBtn.setEnabled(false);
					ExportShip.setEnabled(false);
					buf = buf + ". In Port!";
				} else {
					goInBtn.setEnabled(true);
					ExportShip.setEnabled(true);
				}
				shipLabel.setText(buf);
				repaintTableShip();

			}
		};
	}

	/**
	 * Adds the pier btn selection.
	 *
	 * @return the selection adapter
	 */
	public static SelectionAdapter addPierBtnSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Port port = Structure.getPort();
				synchronized (port) {
					port.pierNumInc();
					Pier pier = new Pier(Integer.toString(port.getNumPier()));
					port.getFullPierVector().add(pier);
					port.getPierQuene().add(pier);
					port.releaseSemaphore();

				}
			}
		};
	}

	/**
	 * Adds the ship btn selection.
	 *
	 * @return the selection adapter
	 */
	public static SelectionAdapter addShipBtnSelection() {

		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (addWnd == null || addWnd.isDisposed()) {
					addWnd = new newShip();
					addWnd.open();
				} else
					addWnd.setVisible(true);

			}
		};
	}

	/**
	 * Repaint main table.
	 */
	public static void repaintMainTable() {

		Table newTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		newTable.setBounds(10, 63, 562, 182);
		newTable.setHeaderVisible(true);
		newTable.setLinesVisible(true);

		TableColumn tblclmnNumberOfPier = new TableColumn(newTable, SWT.CENTER);
		tblclmnNumberOfPier.setWidth(62);
		tblclmnNumberOfPier.setText("Pier");

		TableColumn tblclmnShip = new TableColumn(newTable, SWT.CENTER);
		tblclmnShip.setWidth(119);
		tblclmnShip.setText("Ship");

		TableColumn tblclmnStatus = new TableColumn(newTable, SWT.CENTER);
		tblclmnStatus.setWidth(96);
		tblclmnStatus.setText("Status");

		TableColumn tblclmnStorage = new TableColumn(newTable, SWT.NONE);
		tblclmnStorage.setWidth(58);
		tblclmnStorage.setText("Storage");

		TableColumn tblclmnTimemin = new TableColumn(newTable, SWT.CENTER);
		tblclmnTimemin.setWidth(82);
		tblclmnTimemin.setText("All time(min)");

		TableColumn tblclmnAllTimemin = new TableColumn(newTable, SWT.NONE);
		tblclmnAllTimemin.setWidth(141);
		tblclmnAllTimemin.setText("Time for mooring(min)");

		Vector<Pier> pierVect = Structure.getPort().getFullPierVector();

		for (int index = 0; index < pierVect.size(); index++) {
			if (pierVect.get(index) != null) {
				TableItem item = new TableItem(newTable, SWT.NULL);
				item.setText("Item");
				item.setText(0, pierVect.get(index).getPierName());

				if (pierVect.get(index).getShip() != null) {
					item.setText(1, pierVect.get(index).getShip().getShipName());
					Boolean task = pierVect.get(index).getShip().getTask();
					if (task)
						item.setText(2, "Load");
					else
						item.setText(2, "Unload");
					item.setText(3, Integer.toString(pierVect.get(index).getStorageCount()));
					item.setText(4, Integer.toString(pierVect.get(index).getShip().getRealTime()));
					item.setText(5, Integer.toString(pierVect.get(index).getShip().getAllTime()));
				} else {

					item.setText(1, "---");
					item.setText(2, "---");
					item.setText(3, Integer.toString(pierVect.get(index).getStorageCount()));
					item.setText(4, "---");
					item.setText(5, "---");
				}

			}
		}
		table.dispose();
		table = newTable;
		composite.layout();
	}

	/**
	 * Repaint table pier.
	 */
	public static void repaintTablePier() {

		Table newTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		newTable.setBounds(10, 301, 245, 203);
		newTable.setHeaderVisible(true);
		newTable.setLinesVisible(true);

		TableColumn tblclmnNameOfGood = new TableColumn(newTable, SWT.CENTER);
		tblclmnNameOfGood.setWidth(174);
		tblclmnNameOfGood.setText("Name of good");

		TableColumn tblclmnCount_1 = new TableColumn(newTable, SWT.NONE);
		tblclmnCount_1.setWidth(67);
		tblclmnCount_1.setText("Count");

		LinkedList<containerGood> goodVect = Structure.getPort().getRealPier(comboPier.getText()).getQueneGood();
		for (int index = 0; index < goodVect.size(); index++) {
			if (goodVect.get(index) != null) {
				TableItem item = new TableItem(newTable, SWT.NULL);
				item.setText("Item");
				item.setText(0, goodVect.get(index).getGoodName());
				item.setText(1, Integer.toString(goodVect.get(index).getGoodCount()));
			}
		}
		pierTable.dispose();
		pierTable = newTable;
		composite.layout();
	}

	/**
	 * Repaint table ship.
	 */
	public static void repaintTableShip() {

		Table newTable = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		newTable.setBounds(327, 301, 245, 203);
		newTable.setHeaderVisible(true);
		newTable.setLinesVisible(true);

		TableColumn tblclmnName = new TableColumn(newTable, SWT.CENTER);
		tblclmnName.setWidth(174);
		tblclmnName.setText("Name of good");

		TableColumn tblclmnCount = new TableColumn(newTable, SWT.NONE);
		tblclmnCount.setWidth(66);
		tblclmnCount.setText("Count");
		if (!Structure.getRealShip(comboShip.getText()).getFlagInPort()) {
			LinkedList<containerGood> goodVect = Structure.getRealShip(comboShip.getText()).getQueneGood();
			for (int index = 0; index < goodVect.size(); index++) {
				if (goodVect.get(index) != null) {
					TableItem item = new TableItem(newTable, SWT.NULL);
					item.setText("Item");
					item.setText(0, goodVect.get(index).getGoodName());
					item.setText(1, Integer.toString(goodVect.get(index).getGoodCount()));
				}
			}
		}

		shipTable.dispose();
		shipTable = newTable;
		composite.layout();
	}

	/**
	 * Repaint combo ship.
	 */
	public static void repaintComboShip() {

		synchronized (Structure.getVectShip()) {
			int vSize = Structure.getVectShip().size();
			if (vectorShipSize < vSize) {

				for (int i = vectorShipSize; i < vSize; i++) {
					comboShip.add(Structure.getVectShip().get(i).getShipName());
				}
				vectorShipSize = vSize;
			}
			composite.layout();
		}
	}

	/**
	 * Repaint combo pier.
	 */
	public static void repaintComboPier() {

		int vSize = Structure.getPort().getNumPier();
		if (vectorPierSize < vSize) {

			for (int i = vectorPierSize; i < vSize; i++) {
				comboPier.add(Structure.getPort().getFullPierVector().get(i).getPierName());
			}
			vectorPierSize = vSize;
		}
		composite.layout();

	}

	/**
	 * Repaint time label.
	 */
	public static void repaintTimeLabel() {
		Time.setText("" + Calendar.getInstance().getTime() + "");
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Port system");
		setSize(679, 606);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Decorations#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Gets the composite.
	 *
	 * @return the composite
	 */
	public static Composite getComposite() {
		return composite;
	}

	public static String getCurrentShipSection() {
		return comboShip.getText();
	}

	public static String getCurrentPierSection() {
		return comboPier.getText();
	}
}
