import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;


public class PreferenceShow {

	protected Shell shell;
	private Table table;
	private TableColumn tblclmnUser;
	private TableColumn tblclmnUser_1;
	private TableColumn tblclmnUser_2;

	/**
	 * Launch the application.
	 * @param args
	 * @throws TasteException 
	 * @throws IOException 
	 */
	public PreferenceShow() throws IOException, TasteException {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Open the window.
	 * @throws IOException 
	 * @throws TasteException 
	 */
	public void open() throws IOException, TasteException {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @throws IOException 
	 * @throws TasteException 
	 */
	protected void createContents() throws IOException, TasteException {
		shell = new Shell();
		shell.setSize(450, 500);
		shell.setText("Æ«ºÃ¼¯");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		String file;
		if(MainShell.matcher.matches()){
			file = "src/data/testCF.csv";
		}
		else
		{
			file = "src/data/testCF.base";
		}
		DataModel model = new FileDataModel(new File(file));
		LongPrimitiveIterator userID = model.getUserIDs();
		LongPrimitiveIterator itemID = model.getItemIDs();

		tblclmnUser = new TableColumn(table, SWT.NONE);
		tblclmnUser.setWidth(100);
		tblclmnUser.setText("userID");
		tblclmnUser_1 = new TableColumn(table, SWT.NONE);
		tblclmnUser_1.setWidth(100);
		tblclmnUser_1.setText("itemID");

		while (userID.hasNext()) {
			long user = userID.nextLong();
			while(itemID.hasNext()){
				long item=itemID.nextLong();
				if(model.getPreferenceValue(user, item)!=null){
					Float value=model.getPreferenceValue(user, item);
					if(value>3.5)
					{
						String userS=Long.toString(user);
						String itemS=Long.toString(item);
						String valueS=Float.toString(value);
						TableItem itemTable = new TableItem(table, SWT.NONE);
						itemTable.setText( new String[] { userS, itemS} );
					}
				}
			}
			itemID=model.getItemIDs();
		}
	}
}