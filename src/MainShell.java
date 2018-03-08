import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
//import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;


public class MainShell {

	protected Shell shlCf;
	private Text text;
	public static String fileName;
	private String csvAddr="E:\\EcWorkSpace\\CF\\src\\data\\testCF.csv";
	private String baseAddr="E:\\EcWorkSpace\\CF\\src\\data\\testCF.base";
	public String reg;
	public Pattern pattern;
	public static Matcher matcher;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainShell window = new MainShell();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCf.open();
		shlCf.layout();
		while (!shlCf.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCf = new Shell(SWT.CLOSE | SWT.MIN);
		shlCf.setSize(423, 274);
		shlCf.setText("CF");
		shlCf.setLocation(Display.getCurrent().getClientArea().width / 2 - shlCf.getShell().getSize().x/2, Display.getCurrent() 
				.getClientArea().height / 2 - shlCf.getSize().y/2); 

		Composite composite = new Composite(shlCf, SWT.NONE);
		composite.setBounds(10, 10, 387, 215);
		composite.setLayout(null);

		text = new Text(composite, SWT.BORDER);
		text.setBounds(90, 45, 193, 23);

		Button button = new Button(composite, SWT.NONE);
		button.setBounds(301, 43, 60, 27);
		button.addSelectionListener(new SelectionAdapter(){  

			public void widgetSelected(SelectionEvent e){  
				FileDialog fd=new FileDialog(shlCf ,SWT.OPEN|SWT.MULTI);
				String[] filter = {"*.csv","*.base"};//指定文件格式
				fd.setFilterExtensions(filter);
				fileName=fd.open();
				if (fileName!=null){
					reg= ".*\\.csv";
					pattern = Pattern.compile(reg);
					matcher = pattern.matcher(fileName);
					text.setText(fileName);
					if(matcher.matches()){
						copyFile(fileName,csvAddr);
					}
					else
					{
						copyFile(fileName,baseAddr);
					}
				}
			}  
		});
		button.setText("\u6D4F\u89C8...");

		Button btnItem = new Button(composite, SWT.NONE);
		btnItem.setBounds(215, 153, 68, 27);
		btnItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(fileName==null)
				{
					MessageBox messageBox =   
							new MessageBox(shlCf, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
					messageBox.setText("Error!");  
					messageBox.setMessage("请输入路径！");
					messageBox.open();
				}
				else{
					Display.getDefault ().asyncExec (new Runnable () {
						public void run() {
							try {
								new ThirdShell();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TasteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		btnItem.setText("基于项目");



		Button btnUser = new Button(composite, SWT.NONE);
		btnUser.setBounds(90, 153, 68, 27);
		btnUser.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if(fileName==null)
				{
					MessageBox messageBox =   
							new MessageBox(shlCf, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
					messageBox.setText("Error!");  
					messageBox.setMessage("请输入路径！");
					messageBox.open();
				}
				else{
					Display.getDefault ().asyncExec (new Runnable () {
						public void run() {
							try {
								new SecondShell();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TasteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		btnUser.setText("基于用户");

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(10, 48, 80, 17);
		label.setText("\u6570\u636E\u6587\u4EF6\u8DEF\u5F84");

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(fileName==null)
				{
					MessageBox messageBox =   
							new MessageBox(shlCf, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
					messageBox.setText("Error!");  
					messageBox.setMessage("请输入路径！");
					messageBox.open();
				}
				else{
					Display.getDefault ().asyncExec (new Runnable () {
						public void run() {
							try {
								new CSVShow();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TasteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		btnNewButton.setBounds(55, 100, 116, 27);
		btnNewButton.setText("\u6570\u636E\u96C6");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(fileName==null)
				{
					MessageBox messageBox =   
							new MessageBox(shlCf, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
					messageBox.setText("Error!");  
					messageBox.setMessage("请输入路径！");
					messageBox.open();
				}
				else{
					Display.getDefault ().asyncExec (new Runnable () {
						public void run() {
							try {
								new PreferenceShow();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TasteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		btnNewButton_1.setBounds(205, 100, 117, 27);
		btnNewButton_1.setText("\u504F\u597D\u96C6");

	}

	public void copyFile(String oldPath, String newPath) { 
		try { 
			int bytesum = 0; 
			int byteread = 0; 
			File oldfile = new File(oldPath); 
			if (oldfile.exists()) {                  //文件存在时 
				InputStream inStream = new FileInputStream(oldPath);      //读入原文件 
				FileOutputStream fs = new FileOutputStream(newPath); 
				byte[] buffer = new byte[1444]; 
				int length; 
				while ( (byteread = inStream.read(buffer)) != -1) { 
					bytesum += byteread;            //字节数 文件大小 
					fs.write(buffer, 0, byteread); 
				} 
				inStream.close(); 
			} 
		}  catch (Exception e) {
			MessageBox messageBox =   
					new MessageBox(shlCf, SWT.OK|SWT.CANCEL|SWT.ICON_ERROR);
			messageBox.setText("Error!");  
			messageBox.setMessage("出错！");
			messageBox.open();
			fileName=null;
		} 
	} 
}