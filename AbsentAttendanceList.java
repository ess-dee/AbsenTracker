import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;

public class AbsentAttendanceList extends JFrame {

	private JPanel contentPane;
	private JTable tableAttendanceList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbsentAttendanceList frame = new AbsentAttendanceList();
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
	public AbsentAttendanceList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 1125, 625);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFindClass = new JLabel("Find Class:");
		lblFindClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFindClass.setBounds(628, 49, 92, 20);
		contentPane.add(lblFindClass);
		
		JComboBox comboBoxSelectClass = new JComboBox();
		comboBoxSelectClass.setBounds(722, 47, 242, 26);
		contentPane.add(comboBoxSelectClass);
		
		
		JButton btnFindClass = new JButton("Find Class");
		btnFindClass.addActionListener(new ActionListener() {
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
		btnFindClass.setBounds(979, 46, 109, 29);
		contentPane.add(btnFindClass);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbsentAttendanceList.this.dispose();
				AbsentMain window = new AbsentMain();
				window.frame.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(615, 490, 147, 63);
		contentPane.add(btnBack);
		
		JButton btnDeleteStudent = new JButton("Delete Student");
		btnDeleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbsentAttendanceList.this.dispose();
				AbsentDeleteStudent deleteStudent = new AbsentDeleteStudent();
				deleteStudent.setVisible(true);
			}
		});
		btnDeleteStudent.setBounds(777, 490, 147, 63);
		contentPane.add(btnDeleteStudent);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(735, 108, 189, 26);
		contentPane.add(dateChooser);
		
		dateChooser.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
		dateChooser.setDateFormatString("yyyy-MM-dd");
		
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
					   
					 //Check if JTable is empty
					   if (tableAttendanceList.getRowCount() == 0)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please click Mark Attendance");
						   //Exit program without executing remaining codes
						   return;						   
					   } 
					
					   Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       
				       PreparedStatement pst;
				       
				       int intRowCount = tableAttendanceList.getRowCount();
				       int row; 
				       String strAbsent;
				       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       String strDate = sdf.format(dateChooser.getDate());
				       
				       for (row = 0; row < intRowCount; row++)
				       {
				    	   strAbsent = (tableAttendanceList.getModel().getValueAt(row, 0).toString());
				    	   
				    	   if (strAbsent.equalsIgnoreCase("true"))
				    	   {
				    		   String query="INSERT INTO Attendance (ClassName, FirstName, LastName, StudentNumber, Absent, Date ) values (?, ?, ?, ?, ?, ?)";
				    		   pst = dbConnection.prepareStatement(query);
				    		   	    		   
				    		   pst.setString(1, tableAttendanceList.getModel().getValueAt(row, 4).toString());
				    		   pst.setString(2, tableAttendanceList.getModel().getValueAt(row, 1).toString());
				    		   pst.setString(3, tableAttendanceList.getModel().getValueAt(row, 2).toString());
				    		   pst.setString(4, tableAttendanceList.getModel().getValueAt(row, 3).toString());
				    		   pst.setString(5, tableAttendanceList.getModel().getValueAt(row, 0).toString());
				    		   pst.setString(6, strDate);
				    				   
				    						    		   
				    		   pst.executeUpdate();
				    	   }
				       }
				       
				       dbConnection.close();
				       JOptionPane.showMessageDialog(null, "Entry Saved!");
				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }
			}
		});
		btnSave.setBounds(941, 490, 147, 63);
		contentPane.add(btnSave);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 24, 573, 529);
		contentPane.add(scrollPane);
		
		tableAttendanceList = new JTable();
		scrollPane.setViewportView(tableAttendanceList);
		
		JLabel lblDate = new JLabel("Select Date:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDate.setBounds(626, 112, 94, 22);
		contentPane.add(lblDate);		
				
		JButton btnAttendance = new JButton("Mark Attendance");
		btnAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				       Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       String query= "SELECT FirstName, LastName, StudentNumber, ClassName FROM Student WHERE Student.ClassName = ?";
				       PreparedStatement pst=dbConnection.prepareStatement(query);
				       				       
				       pst.setString(1, String.valueOf(comboBoxSelectClass.getSelectedItem()));
				       
				       ResultSet rs = pst.executeQuery();
				       
				       TableModel utilsModel = DbUtils.resultSetToTableModel(rs);
				       TableModel wrapperModel = new CheckBoxWrapperTableModel((DefaultTableModel) utilsModel, "Absent");
				       tableAttendanceList.setModel( wrapperModel );
				       
				       //To display proper column name
				       tableAttendanceList.getColumnModel().getColumn(1).setHeaderValue("First Name");
				       tableAttendanceList.getColumnModel().getColumn(2).setHeaderValue("Last Name");
				       tableAttendanceList.getColumnModel().getColumn(3).setHeaderValue("Student #");
				       tableAttendanceList.getColumnModel().getColumn(4).setHeaderValue("Class Name");
				       
				       //To adjust column width of JTable
				       tableAttendanceList.getColumnModel().getColumn(0).setPreferredWidth(10);
			           tableAttendanceList.getColumnModel().getColumn(1).setPreferredWidth(10);
				       tableAttendanceList.getColumnModel().getColumn(2).setPreferredWidth(30);
				       tableAttendanceList.getColumnModel().getColumn(3).setPreferredWidth(10);
				       tableAttendanceList.getColumnModel().getColumn(4).setPreferredWidth(50);
				       
				       tableAttendanceList.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
				       
				       dbConnection.close();

				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }
			}
		});
		btnAttendance.setBounds(937, 108, 151, 29);
		contentPane.add(btnAttendance);
	}
}
