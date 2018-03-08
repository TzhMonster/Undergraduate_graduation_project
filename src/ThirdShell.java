import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment; 


public class ThirdShell {

	protected Shell shell;
	private Table table;
	private TableColumn tblclmnUser;
	private TableColumn tblclmnUser_1;
	private TableColumn tblclmnUser_2;
	private double score;
	final static int NEIGHBORHOOD_NUM = 2;
	final static int RECOMMENDER_NUM = 3;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Label lblNewLabel_4;
	/**
	 * Launch the application.
	 * @param args
	 * @throws TasteException 
	 * @throws IOException 
	 */
	public ThirdShell() throws IOException, TasteException {
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
	 * @throws TasteException 
	 * @throws IOException 
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
	 */
	protected void createContents()throws IOException, TasteException {
		shell = new Shell();
		shell.setSize(450, 500);
		shell.setText("基于物品的协同过滤");
		shell.setLayout(new FormLayout());

		Composite composite = new Composite(shell, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.right = new FormAttachment(0, 424);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.left = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Label label = new Label(composite, SWT.NONE);
		label.setText("基于物品的协同过滤：");

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.right = new FormAttachment(100, -20);
		fd_table.left = new FormAttachment(composite, 10, SWT.LEFT);

		tblclmnUser = new TableColumn(table, SWT.NONE);
		tblclmnUser.setWidth(100);
		tblclmnUser.setText("userID");
		tblclmnUser_1 = new TableColumn(table, SWT.NONE);
		tblclmnUser_1.setWidth(100);
		tblclmnUser_1.setText("itemID");
		tblclmnUser_2 = new TableColumn(table, SWT.NONE);
		tblclmnUser_2.setWidth(100);
		tblclmnUser_2.setText("value");

		RandomUtils.useTestSeed();
		String file;
		if(MainShell.matcher.matches()){
			file = "src/data/testCF.csv";
		}
		else
		{
			file = "src/data/testCF.base";
		}
		DataModel model = new FileDataModel(new File(file));
		ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
		Recommender recommender = 
				new GenericItemBasedRecommender(model,  similarity);
		List<RecommendedItem> recommendations = recommender.recommend(NEIGHBORHOOD_NUM, RECOMMENDER_NUM);
		LongPrimitiveIterator iter = model.getUserIDs();
		while (iter.hasNext()) {
			long uid = iter.nextLong();
			String uids=Long.toString(uid);
			List<RecommendedItem> list = recommender.recommend(uid, 1);
			for (RecommendedItem ritem : list) {
				String itemIDS=Long.toString(ritem.getItemID());
				String valueIDS=Float.toString(ritem.getValue());
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText( new String[] { uids, itemIDS, valueIDS } );
			}
		}
		RecommenderEvaluator evaluator = 
				new AverageAbsoluteDifferenceRecommenderEvaluator();
		RecommenderBuilder builder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model)  
					throws TasteException {
				ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
				Recommender recommender = 
						new GenericItemBasedRecommender(model,  similarity);
				List<RecommendedItem> recommendations = recommender.recommend(NEIGHBORHOOD_NUM, RECOMMENDER_NUM);
				return recommender; 
			}
		};
		score = evaluator.evaluate(builder, null, model, 0.7, 1.0); 
		score = evaluator.evaluate(builder, null, model, 0.9, 1.0);
		fd_table.bottom = new FormAttachment(100, -10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		String scoreS=String.valueOf(score);

		
		RecommenderIRStatsEvaluator evaluator1 = 
        		new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder builder1 = new RecommenderBuilder() {
        	@Override
        	public Recommender buildRecommender(DataModel model)  
                    throws TasteException { 
				ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
				Recommender recommender = 
						new GenericItemBasedRecommender(model,  similarity);
				List<RecommendedItem> recommendations = recommender.recommend(NEIGHBORHOOD_NUM, RECOMMENDER_NUM);
				return recommender; 
        	}
        };
        IRStatistics stats= evaluator1.evaluate(builder1,null, model, null, 2, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD,1.0);

		Label lblNewLabel = new Label(shell, SWT.NONE);
		fd_table.top = new FormAttachment(lblNewLabel, 6);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.right = new FormAttachment(100, -20);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText(scoreS);

		Label label_1 = new Label(shell, SWT.NONE);
		fd_lblNewLabel.left = new FormAttachment(label_1, 6);
		fd_lblNewLabel.top = new FormAttachment(label_1, 0, SWT.TOP);
		FormData fd_label_1 = new FormData();
		fd_label_1.left = new FormAttachment(0, 20);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("评估水平：");
		lblNewLabel_1 = new Label(shell, SWT.NONE);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(composite, 6);
		fd_lblNewLabel_1.left = new FormAttachment(composite, 10, SWT.LEFT);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("\u67E5\u51C6\u7387:");
		
		lblNewLabel_2 = new Label(shell, SWT.NONE);
		fd_label_1.top = new FormAttachment(lblNewLabel_2, 6);
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.left = new FormAttachment(0, 20);
		fd_lblNewLabel_2.top = new FormAttachment(lblNewLabel_1, 6);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		lblNewLabel_2.setText("\u67E5\u5168\u7387:");
		
		lblNewLabel_3 = new Label(shell, SWT.NONE);
		FormData fd_lblNewLabel_3 = new FormData();
		fd_lblNewLabel_3.right = new FormAttachment(100, -6);
		fd_lblNewLabel_3.top = new FormAttachment(composite, 6);
		fd_lblNewLabel_3.left = new FormAttachment(lblNewLabel_1, 6);
		lblNewLabel_3.setLayoutData(fd_lblNewLabel_3);
		lblNewLabel_3.setText(String.valueOf(stats.getPrecision()));
		
		lblNewLabel_4 = new Label(shell, SWT.NONE);
		FormData fd_lblNewLabel_4 = new FormData();
		fd_lblNewLabel_4.right = new FormAttachment(100, -20);
		fd_lblNewLabel_4.left = new FormAttachment(lblNewLabel_2, 6);
		fd_lblNewLabel_4.bottom = new FormAttachment(label_1, -6);
		lblNewLabel_4.setLayoutData(fd_lblNewLabel_4);
		lblNewLabel_4.setText(String.valueOf(stats.getRecall()));
	}
}