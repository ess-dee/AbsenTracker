import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class AbsentAddStudent extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtStudentNumber;
	private JTextField txtInstrument;
	private JButton btnBack;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbsentAddStudent frame = new AbsentAddStudent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AbsentAddStudent() {
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 1125, 625);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddStudent = new JLabel("Add Student");
		lblAddStudent.setFont(new Font("Tahoma", Font.BOLD, 38));
		lblAddStudent.setBounds(483, 71, 236, 46);
		contentPane.add(lblAddStudent);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFirstName.setBounds(151, 277, 97, 20);
		contentPane.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLastName.setBounds(637, 277, 97, 20);
		contentPane.add(lblLastName);

		JLabel lblStudentNumber = new JLabel("Student Number:");
		lblStudentNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStudentNumber.setBounds(109, 378, 139, 20);
		contentPane.add(lblStudentNumber);

		JLabel lblInstrument = new JLabel("Instrument:");
		lblInstrument.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblInstrument.setBounds(637, 378, 97, 20);
		contentPane.add(lblInstrument);

		txtFirstName = new JTextField();
		txtFirstName.setBounds(255, 275, 212, 26);
		contentPane.add(txtFirstName);
		txtFirstName.setColumns(10);

		txtLastName = new JTextField();
		txtLastName.setBounds(749, 275, 212, 26);
		contentPane.add(txtLastName);
		txtLastName.setColumns(10);

		txtStudentNumber = new JTextField();
		txtStudentNumber.setBounds(255, 376, 212, 26);
		contentPane.add(txtStudentNumber);
		txtStudentNumber.setColumns(10);

		txtInstrument = new JTextField();
		txtInstrument.setBounds(749, 376, 212, 26);
		contentPane.add(txtInstrument);
		txtInstrument.setColumns(10);

		JComboBox comboBoxSelectClass = new JComboBox();
		comboBoxSelectClass.setBounds(483, 183, 253, 26);
		contentPane.add(comboBoxSelectClass);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbsentAddStudent.this.dispose();
				AbsentMain window = new AbsentMain();
				window.frame.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(163, 459, 147, 63);
		contentPane.add(btnBack);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					   //Check if a mandatory JComboBox is empty
					   if (comboBoxSelectClass.getSelectedIndex() == -1)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please select a Class");
						   //Exit program without executing remaining codes
						   return;						   
					   }
				
					   //Check if a mandatory JTextField() is empty
					   if (txtFirstName.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter First Name");
						   //Clear mandatory JTextField()
					       txtFirstName.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   //Check if a mandatory JTextField() is empty
					   if (txtLastName.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Last Name");
						   //Clear mandatory JTextField()
					       txtLastName.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   //Check if a mandatory JTextField() is empty
					   if (txtStudentNumber.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Student Number");
						   //Clear mandatory JTextField()
					       txtStudentNumber.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   //Check if a mandatory JTextField() is empty
					   if (txtInstrument.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Instrument");
						   //Clear mandatory JTextField()
					       txtInstrument.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					
					   Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       String query="insert into Student (ClassName, FirstName, LastName, StudentNumber, Instrument) values (?, ?, ?, ?, ?)";
				       PreparedStatement pst=dbConnection.prepareStatement(query);

				       pst.setString(1, String.valueOf(comboBoxSelectClass.getSelectedItem()));
				       pst.setString(2, txtFirstName.getText());
				       pst.setString(3, txtLastName.getText());
				       pst.setString(4, txtStudentNumber.getText());
				       pst.setString(5, txtInstrument.getText());
				       
				       pst.executeUpdate();
				       dbConnection.close();

				       JOptionPane.showMessageDialog(null, "Entry Saved!");
				       
				       //Clear mandatory JTextField() after saving
				       txtFirstName.setText("");
				       txtLastName.setText("");
				       txtStudentNumber.setText("");
				       txtInstrument.setText("");
				       
				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }


			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSave.setBounds(774, 459, 147, 63);
		contentPane.add(btnSave);

		JLabel lblSelectClass = new JLabel("Select Class:");
		lblSelectClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectClass.setBounds(370, 185, 114, 20);
		contentPane.add(lblSelectClass);
		
		JButton btnPopulate = new JButton("Populate");
		btnPopulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				       Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       String query="SELECT ClassName FROM Class ";
				       PreparedStatement pst=dbConnection.prepareStatement(query);
				       ResultSet rs = pst.executeQuery();
				       
				       comboBoxSelectClass.removeAllItems();
				       
				       while (rs.next())
				       {
				    	   String strClassName = rs.getString("ClassName");
				    	   comboBoxSelectClass.addItem(strClassName);
				       }
				       
				       dbConnection.close();

				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }
			}
		});
		btnPopulate.setBounds(774, 182, 115, 29);
		contentPane.add(btnPopulate);
		
		
	}
}
