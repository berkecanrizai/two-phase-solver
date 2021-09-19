import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import java.awt.*;  
import java.awt.event.*; 
public class Window {

	protected Shell shell;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Window window = new Window();
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
	protected void createContents() {
		shell = new Shell();
		shell.setSize(846, 512);
		shell.setText("SWT Application");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(176, 77, 76, 21);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(176, 114, 76, 21);
		
		Label lblNumberOfVariables = new Label(shell, SWT.NONE);
		lblNumberOfVariables.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNumberOfVariables.setBounds(10, 76, 150, 28);
		lblNumberOfVariables.setText("Number of variables:");
		
		Label lblNumberOfConstraints = new Label(shell, SWT.NONE);
		lblNumberOfConstraints.setText("Number of constraints:");
		lblNumberOfConstraints.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNumberOfConstraints.setBounds(10, 113, 150, 28);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Sayi, "+text.getText()+" "+text_1.getText());
				Quest question=new Quest(Integer.parseInt(text.getText()), Integer.parseInt(text_1.getText()));
			}
		});
		btnNewButton.setBounds(10, 174, 232, 46);
		btnNewButton.setText("Initialize");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblNewLabel.setBounds(10, 288, 584, 86);
		lblNewLabel.setText("Answer will be printed on console after running.");
		

	}
}
