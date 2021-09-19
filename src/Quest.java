import java.awt.BorderLayout;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Quest extends JFrame {

	private JPanel contentPane;
	boolean isTwoPhase=true;
	private JTextField textField;
	int variableNum;
	int constraintNum;
	
	double[] zRow=new double[variableNum];
	
	double[] bCol=new double[constraintNum];
	
	double[][] A=new double[constraintNum][variableNum];
	
	ArrayList<JTextField> cRowCells=new ArrayList<JTextField>();
	
	ArrayList<JTextField> bColumnCells=new ArrayList<JTextField>();
	
	JTextField[][] ACells=new JTextField[constraintNum][variableNum];
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quest frame = new Quest(0,0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Quest(int variableNum, int constraintNum) {
		zRow=new double[variableNum];
		
		bCol=new double[constraintNum];
		
		A=new double[constraintNum][variableNum];
		
		cRowCells=new ArrayList<JTextField>();
		
		bColumnCells=new ArrayList<JTextField>();
		
		ACells=new JTextField[constraintNum][variableNum];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Two-phase Method");
		rdbtnNewRadioButton.setBounds(6, 231, 144, 23);
		contentPane.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setSelected(true);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Revised Simplex");
		rdbtnNewRadioButton_1.setBounds(152, 231, 144, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setSelected(false);
		
		JButton btnNewButton = new JButton("Solve");
		btnNewButton.setBounds(302, 231, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Optimize:");
		lblNewLabel.setBounds(6, 11, 56, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblConstraints = new JLabel("Subject to:");
		lblConstraints.setBounds(6, 42, 60, 20);
		contentPane.add(lblConstraints);
		
		int i;
		for(i=0; i<variableNum;i++) {
			textField = new JTextField();
			textField.setBounds(85+i*60, 11, 50, 20);
			contentPane.add(textField);
			textField.setColumns(10);
			cRowCells.add(textField);
		}
		i++;
		
		
		int j;
		for(j=0; j<constraintNum;j++) {
			for(i=0; i<variableNum;i++) {
				textField = new JTextField();
				textField.setBounds(85+i*60, 42+20*j, 50, 20);
				contentPane.add(textField);
				textField.setColumns(10);	
				ACells[j][i]=textField;
			}		
		}
		JLabel colgoster = new JLabel("b Column");
		i++;
		j++;
		colgoster.setBounds(85+i*60, 11, 70, 20);
		contentPane.add(colgoster);
		
		JLabel lblNewLabel_1 = new JLabel("Enter 0 values otherwise won't work.");
		lblNewLabel_1.setBounds(6, 192, 269, 32);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("It doesn't matter if max, min or constraints being <,>,=");
		lblNewLabel_1_1.setBounds(6, 167, 350, 32);
		contentPane.add(lblNewLabel_1_1);
		this.setVisible(true);
		
		for(int z=0; z<constraintNum;z++) {
			textField = new JTextField();
			textField.setBounds(85+i*60, 42+20*z, 50, 20);
			contentPane.add(textField);
			textField.setColumns(10);	
			bColumnCells.add(textField);
		}
		
		btnNewButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  //System.out.println(e.toString());

				  for(int i=0;i<cRowCells.size();i++) {
					System.out.println("sayi "+cRowCells.get(i).getText());
					zRow[i]=Double.parseDouble(cRowCells.get(i).getText());
					
				  }
				  
				  for(int z=0; z<constraintNum;z++) {
						bCol[z]=Double.parseDouble(bColumnCells.get(z).getText());
					}
				 
				  for(int j=0; j<constraintNum;j++) {
						for(int i=0; i<variableNum;i++) {
							A[j][i]=Double.parseDouble((ACells[j][i].getText()));
						}		
					}
				  
				  TwoPhaseSimplex si=new TwoPhaseSimplex(A,bCol,zRow);
				 // si.show();
				  
				  } 
				});
		
		
		
	}
}
