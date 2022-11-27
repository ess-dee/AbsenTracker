import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.sql.*;


public class AbsentAbsencesList extends JFrame {

	private JPanel contentPane;
	private JTable tableAbsencesList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbsentAbsencesList frame = new AbsentAbsencesList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//declare public variable
	public String strClassName;
	public String strDateFrom;
	public String strDateTo;

	/**
	 * Create the frame.
	 */
	public AbsentAbsencesList() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(25, 25, 1125, 625);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAbsencesList = new JLabel("Absences List");
		lblAbsencesList.setFont(new Font("Tahoma", Font.BOLD, 38));
		lblAbsencesList.setBounds(436, 54, 260, 46);
		contentPane.add(lblAbsencesList);
		
		JLabel lblFindClass = new JLabel("Find Class:");
		lblFindClass.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFindClass.setBounds(208, 116, 85, 20);
		contentPane.add(lblFindClass);
		
		JComboBox comboBoxSelectClass = new JComboBox();
		comboBoxSelectClass.setBounds(308, 114, 388, 26);
		contentPane.add(comboBoxSelectClass);
		
		JButton btnFindClass = new JButton("Find");
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
		btnFindClass.setBounds(711, 114, 147, 26);
		contentPane.add(btnFindClass);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbsentAbsencesList.this.dispose();
				AbsentMain window = new AbsentMain();
				window.frame.setVisible(true);
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBack.setBounds(121, 472, 147, 63);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(240, 193, 614, 263);
		contentPane.add(scrollPane);
		
		tableAbsencesList = new JTable();
		scrollPane.setViewportView(tableAbsencesList);
		
		JDateChooser dateChooserFrom = new JDateChooser();
		dateChooserFrom.setBounds(299, 151, 238, 26);
		contentPane.add(dateChooserFrom);
		dateChooserFrom.setDateFormatString("yyyy-MM-dd");
		
		JDateChooser dateChooserTo = new JDateChooser();
		dateChooserTo.setBounds(665, 151, 228, 26);
		contentPane.add(dateChooserTo);
		dateChooserTo.setDateFormatString("yyyy-MM-dd");
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
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
					
					   
				       //Check if a mandatory JDateChooser is empty
				       java.util.Date dateFrom = dateChooserFrom.getDate();				       
				       
					   if (dateFrom == null)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please select From Date");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   
					   
					   //Check if a mandatory JDateChooser is empty
					   java.util.Date dateTo = dateChooserTo.getDate();
					   
					   if (dateTo == null)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "Please select To Date");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   
					   
					 //Check if dateFrom is not greater than dateTo				          
				     if (dateFrom.compareTo(dateTo) > 0)						
					   {
						   //Send message to user
						   JOptionPane.showMessageDialog(null, "To Date will be more or equal to From Date");
						   //Exit program without executing remaining codes
						   return;						   
					   }
					   					   
					   
					   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				       strDateFrom = sdf.format(dateChooserFrom.getDate());
				       strDateTo = sdf.format(dateChooserTo.getDate());
					   strClassName = String.valueOf(comboBoxSelectClass.getSelectedItem());
				       
					   Connection dbConnection=null;
				       dbConnection=SqliteConnection.sqliteConnector();
				       String query= "SELECT FirstName, LastName, StudentNumber, ClassName, COUNT(Absent) FROM Attendance "
				       		+ "WHERE (ClassName = ?) "
				       		+ "AND (Date BETWEEN ? AND ?) "
				       		+ "GROUP BY (StudentNumber) ";
				       
				       PreparedStatement pst=dbConnection.prepareStatement(query);
				       
				       pst.setString(1, strClassName);
				       pst.setString(2, strDateFrom);
				       pst.setString(3, strDateTo);
				       
				       ResultSet rs = pst.executeQuery();
				       
				       tableAbsencesList.setModel(DbUtils.resultSetToTableModel(rs));
				       
				       //To display proper column name
				       tableAbsencesList.getColumnModel().getColumn(0).setHeaderValue("First Name");
				       tableAbsencesList.getColumnModel().getColumn(1).setHeaderValue("Last Name");
				       tableAbsencesList.getColumnModel().getColumn(2).setHeaderValue("Student #");
				       tableAbsencesList.getColumnModel().getColumn(3).setHeaderValue("Class Name");
				       
				       //To adjust column width of JTable
				       tableAbsencesList.getColumnModel().getColumn(0).setPreferredWidth(10);
			           tableAbsencesList.getColumnModel().getColumn(1).setPreferredWidth(10);
				       tableAbsencesList.getColumnModel().getColumn(2).setPreferredWidth(30);
				       tableAbsencesList.getColumnModel().getColumn(3).setPreferredWidth(10);
				       tableAbsencesList.getColumnModel().getColumn(4).setPreferredWidth(50);
				       
				       tableAbsencesList.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
				       
				       dbConnection.close();

				     }
				       catch(Exception e){
				                           JOptionPane.showMessageDialog(null, e);
							 }
			}
		});
		btnGenerate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnGenerate.setBounds(477, 472, 147, 63);
		contentPane.add(btnGenerate);
		
		JLabel lblFromDate = new JLabel("From Date:");
		lblFromDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFromDate.setBounds(208, 152, 88, 22);
		contentPane.add(lblFromDate);
		
		JLabel lblToDate = new JLabel("To Date:");
		lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblToDate.setBounds(581, 151, 69, 22);
		contentPane.add(lblToDate);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
			    
				MessageFormat header = new MessageFormat("Absent Report from: " + strDateFrom + " to: " + strDateTo );
				
				MessageFormat footer = new MessageFormat("Glenforest Secondary School, printed on: " + 
				java.sql.Date.valueOf(java.time.LocalDate.now()) + "; "
				+ "page {0}");				
			    
					try{
						tableAbsencesList.print(JTable.PrintMode.FIT_WIDTH, header, footer);
					}
					catch (Exception e){
						JOptionPane.showMessageDialog(null, "Failed to print Report"+e.getMessage());
					}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPrint.setBounds(797, 472, 147, 63);
		contentPane.add(btnPrint);
	}
}
