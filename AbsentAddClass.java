import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class AbsentAddClass extends JFrame {

	private JPanel contentPane;
	private JTextField txtWeekday;
	private JTextField txtTime;
	private JTextField txtClassName;
	private JTextField txtClassCode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbsentAddClass frame = new AbsentAddClass();
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
	public AbsentAddClass() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 1125, 625);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddClass = new JLabel("Add Class");
		lblAddClass.setFont(new Font("Tahoma", Font.BOLD, 38));
		lblAddClass.setBounds(478, 84, 184, 46);
		contentPane.add(lblAddClass);

		txtWeekday = new JTextField();
		txtWeekday.setBounds(234, 365, 229, 26);
		contentPane.add(txtWeekday);
		txtWeekday.setColumns(10);

		txtTime = new JTextField();
		txtTime.setBounds(757, 367, 229, 26);
		contentPane.add(txtTime);
		txtTime.setColumns(10);

		txtClassName = new JTextField();
		txtClassName.setBounds(757, 203, 229, 26);
		contentPane.add(txtClassName);
		txtClassName.setColumns(10);

		JLabel lblClassName = new JLabel("Name of Class (with year):");
		lblClassName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblClassName.setBounds(536, 205, 216, 20);
		contentPane.add(lblClassName);

		JLabel lblClassCode = new JLabel("Class Code:");
		lblClassCode.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblClassCode.setBounds(120, 205, 97, 20);
		contentPane.add(lblClassCode);

		JLabel lblWeekday = new JLabel("Day of the Week:");
		lblWeekday.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblWeekday.setBounds(82, 367, 138, 20);
		contentPane.add(lblWeekday);

		JLabel lblTime = new JLabel("Time of Meetings:");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTime.setBounds(604, 367, 146, 20);
		contentPane.add(lblTime);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbsentAddClass.this.dispose();
				AbsentMain window = new AbsentMain();
				window.frame.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(135, 463, 147, 63);
		contentPane.add(btnBack);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				       //Check if a mandatory JTextField() is empty
					   if (txtClassCode.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Class code");
						   //Clear mandatory JTextField()
					       txtClassCode.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   //Check if a mandatory JTextField() is empty
					   if (txtClassName.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Class Name");
						   //Clear mandatory JTextField()
					       txtClassName.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   //Check if a mandatory JTextField() is empty
					   if (txtWeekday.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Day of the Week");
						   //Clear mandatory JTextField()
					       txtWeekday.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   //Check if a mandatory JTextField() is empty
					   if (txtTime.getText().trim().isEmpty() == true)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please enter Time of Meeting");
						   //Clear mandatory JTextField()
					       txtTime.setText("");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					
					   Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       String query="insert into Class (ClassCode, ClassName, Weekday, Time) values (?, ?, ?, ?)";
				       PreparedStatement pst=dbConnection.prepareStatement(query);

				       pst.setString(1, txtClassCode.getText());
				       pst.setString(2, txtClassName.getText());
				       pst.setString(3, txtWeekday.getText());
				       pst.setString(4, txtTime.getText());
				       
				       pst.executeUpdate();
				       dbConnection.close();

				       JOptionPane.showMessageDialog(null, "Entry Saved!");
				       
				       //Clear mandatory JTextField() after saving
				       txtClassCode.setText("");
				       txtClassName.setText("");
				       txtWeekday.setText("");
				       txtTime.setText("");
				       
				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSave.setBounds(812, 463, 147, 63);
		contentPane.add(btnSave);

		txtClassCode = new JTextField();
		txtClassCode.setBounds(218, 203, 229, 26);
		contentPane.add(txtClassCode);
		txtClassCode.setColumns(10);
	}
}
