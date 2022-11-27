import java.awt.EventQueue;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class AbsentDeleteStudent extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearchStudent;
	private JTable tblDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbsentDeleteStudent frame = new AbsentDeleteStudent();
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
	public AbsentDeleteStudent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 1125, 625);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDeleteStudent = new JLabel("Delete Student");
		lblDeleteStudent.setFont(new Font("Tahoma", Font.BOLD, 38));
		lblDeleteStudent.setBounds(438, 73, 287, 46);
		contentPane.add(lblDeleteStudent);
		
		JComboBox comboBoxSelectClass = new JComboBox();
		comboBoxSelectClass.setBounds(469, 135, 302, 26);
		contentPane.add(comboBoxSelectClass);
		
		JLabel lblSelectClass = new JLabel("Select Class:");
		lblSelectClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSelectClass.setBounds(349, 135, 105, 20);
		contentPane.add(lblSelectClass);
		
		JLabel lblSearchStudent = new JLabel("Search Student:");
		lblSearchStudent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSearchStudent.setBounds(257, 235, 132, 20);
		contentPane.add(lblSearchStudent);
		
		txtSearchStudent = new JTextField();
		txtSearchStudent.setBounds(404, 233, 337, 26);
		contentPane.add(txtSearchStudent);
		txtSearchStudent.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(257, 299, 641, 141);
		contentPane.add(scrollPane);
		
		tblDelete = new JTable();
		scrollPane.setViewportView(tblDelete);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbsentDeleteStudent.this.dispose();
				AbsentAttendanceList window = new AbsentAttendanceList();
				window.setVisible(true);
			}
		});
		btnBack.setBounds(89, 472, 145, 63);
		contentPane.add(btnBack);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				       Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       
				       String strSearchStudent = txtSearchStudent.getText();
				       strSearchStudent = strSearchStudent + "%";
				       
				       String query = "SELECT StudentNumber, FirstName, LastName FROM Student "
								+ "WHERE FirstName LIKE ? "
								+ "AND ClassName = ? "
								+ "ORDER BY FirstName ";
				       
				       PreparedStatement pst=dbConnection.prepareStatement(query);
				       
				       pst.setString(1, strSearchStudent);
				       pst.setString(2, String.valueOf(comboBoxSelectClass.getSelectedItem()));
				       
				       ResultSet rs = pst.executeQuery();				       
				            
				       tblDelete.setModel(DbUtils.resultSetToTableModel(rs));
				       
				     //To display proper column name
						tblDelete.getColumnModel().getColumn(0).setHeaderValue("Student Number");
						tblDelete.getColumnModel().getColumn(1).setHeaderValue("First Name");
						tblDelete.getColumnModel().getColumn(2).setHeaderValue("Last Name");
				       
				       dbConnection.close();

				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }
			}
		});
		btnSearch.setBounds(765, 232, 109, 29);
		contentPane.add(btnSearch);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 try {
					 
					//Check if JTable is empty
					if (tblDelete.getRowCount() == 0)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please select the Student");
						   //Exit program without executing remaining codes
						   return;						   
					   } 
					
					 
				   Connection dbConnection=null;
			       dbConnection=SqliteConnection.sqliteConnector();
			       
				   int rowIndex = tblDelete.getSelectedRow();
			       String strStudentNumber = tblDelete.getModel().getValueAt(rowIndex, 0).toString();

			       String query="DELETE FROM Student WHERE StudentNumber = ? ";
			       PreparedStatement pst=dbConnection.prepareStatement(query);

			       pst.setString(1, strStudentNumber);
			       
			       pst.executeUpdate();
			       dbConnection.close();

			       JOptionPane.showMessageDialog(null, "Record Deleted!");
			       
			       //Clear JTable
			       btnSearch.doClick();
			      			       
			     }
			       catch(Exception e){
			                           JOptionPane.showMessageDialog(null, e);
						 }

			}
				 
		});
		btnDelete.setBounds(882, 472, 147, 63);
		contentPane.add(btnDelete);
		
		JButton btnPopulate = new JButton("Populate");
		btnPopulate.setVisible(false);
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
		btnPopulate.setBounds(775, 133, 109, 29);
		contentPane.add(btnPopulate);
		
		btnPopulate.doClick();		

		
	}
}
