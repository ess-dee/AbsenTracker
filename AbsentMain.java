import java.awt.EventQueue;  
import net.proteanit.sql.DbUtils;
import java.sql.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AbsentMain {

	public JFrame frame;
	private JButton btnFind;
	private JButton btnAddClass;
	private JTable tblStudent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AbsentMain window = new AbsentMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AbsentMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frame.getContentPane().setBackground(new Color(0, 153, 255));
		frame.setBounds(25, 25, 1125, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTextPane textSearchByFirstName = new JTextPane();
		textSearchByFirstName.setBounds(256, 96, 577, 41);
		frame.getContentPane().add(textSearchByFirstName);
		
		btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String strTextSearchByFirstName = textSearchByFirstName.getText();
					strTextSearchByFirstName = strTextSearchByFirstName + "%";
					
					Connection dbConnection = null;
					dbConnection = SqliteConnection.sqliteConnector();
					String query = "SELECT FirstName, LastName, Date  FROM Attendance "
							+ "WHERE FirstName LIKE ? "
							+ "ORDER BY FirstName ";
					PreparedStatement pst = dbConnection.prepareStatement(query);
					
					pst.setString(1, strTextSearchByFirstName);
					
					ResultSet rs = pst.executeQuery();
					
					tblStudent.setModel(DbUtils.resultSetToTableModel(rs));
					
					//To display proper column name
					tblStudent.getColumnModel().getColumn(0).setHeaderValue("First Name");
					tblStudent.getColumnModel().getColumn(1).setHeaderValue("Last Name");
					tblStudent.getColumnModel().getColumn(2).setHeaderValue("Dates Absent");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnFind.setBounds(871, 87, 144, 63);
		frame.getContentPane().add(btnFind);

		JLabel lblSearch = new JLabel("Search By First Name:");
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSearch.setBounds(34, 104, 206, 20);
		frame.getContentPane().add(lblSearch);

		JButton btnAttendance = new JButton("Attendance ");
		btnAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AbsentAttendanceList absentAttendanceList = new AbsentAttendanceList();
				absentAttendanceList.setVisible(true);
			}
		});
		btnAttendance.setBounds(539, 495, 147, 63);
		frame.getContentPane().add(btnAttendance);

		JButton btnAbsensesList = new JButton("Absences List");
		btnAbsensesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AbsentAbsencesList absencesList = new AbsentAbsencesList();
				absencesList.setVisible(true);
			}
		});
		btnAbsensesList.setBounds(795, 495, 147, 63);
		frame.getContentPane().add(btnAbsensesList);

		btnAddClass = new JButton("Add Class");
		btnAddClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AbsentAddClass addClass = new AbsentAddClass();
				addClass.setVisible(true);
			}
		});
		btnAddClass.setBounds(71, 496, 147, 61);
		frame.getContentPane().add(btnAddClass);

		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				AbsentAddStudent absentAddStudent = new AbsentAddStudent();
				absentAddStudent.setVisible(true);
			}
		});
		btnAddStudent.setBounds(299, 495, 147, 63);
		frame.getContentPane().add(btnAddStudent);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 197, 943, 264);
		frame.getContentPane().add(scrollPane);

		tblStudent = new JTable();
		scrollPane.setViewportView(tblStudent);

		JLabel lblNewLabel = new JLabel("Welcome to AbsenTracker");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 38));
		lblNewLabel.setBounds(330, 22, 497, 46);
		frame.getContentPane().add(lblNewLabel);
	}
}
