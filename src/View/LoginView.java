package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import Controller.AccountDao;
import Model.Register;

import java.awt.event.*;
import java.sql.*;

public class LoginView extends JFrame{
	public JPanel contentPane, pnlnameapp, pnllogin, panelbutton;
	public JTextField tfusername;
	public JPasswordField pfpassword;
	public JLabel lbnameapp, lbchecklogin, lbpassword, lbusername;
	public JButton btnCancel, btLogin;
	public JToggleButton btShowandHide;
	private JButton btnRegister;
	AccountDao acd = new AccountDao();
	Register register = new Register();

	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LoginView login = new LoginView();
            login.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// Khá»Ÿi táº¡o login
	public LoginView() {
		setTitle("Dang Nhap");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);

		// Panel chÃ­nh
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel chá»©a tÃªn app
		pnlnameapp = new JPanel();
		pnlnameapp.setBackground(new Color(153, 51, 255));
		pnlnameapp.setBounds(0, 0, 500, 109);
		contentPane.add(pnlnameapp);
		pnlnameapp.setLayout(null);

		lbnameapp = new JLabel("Login");
		lbnameapp.setForeground(new Color(0, 0, 0));
		lbnameapp.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/Manage_account.png")));
		lbnameapp.setHorizontalAlignment(SwingConstants.CENTER);
		lbnameapp.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 30));
		lbnameapp.setBackground(new Color(255, 255, 255));
		lbnameapp.setBounds(0, 0, 500, 109);
		pnlnameapp.add(lbnameapp);

		// Panel chá»©a pháº§n login
		pnllogin = new JPanel();
		pnllogin.setBounds(0, 108, 357, 192);
		contentPane.add(pnllogin);
		pnllogin.setBackground(new Color(248, 248, 255));
		pnllogin.setLayout(null);

		lbchecklogin = new JLabel("");
		lbchecklogin.setForeground(Color.RED);
		lbchecklogin.setHorizontalAlignment(SwingConstants.CENTER);
		lbchecklogin.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 10));
		lbchecklogin.setBounds(130, 152, 210, 30);
		pnllogin.add(lbchecklogin);

		btShowandHide = new JToggleButton("Showpass");
		btShowandHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btShowandHide.isSelected()) {
					pfpassword.setEchoChar((char) 0);
					btShowandHide.setText("Hidepass");
				} else {
					pfpassword.setEchoChar('\u25cf');
					btShowandHide.setText("Showpass");
				}
			}
		});
		btShowandHide.setHorizontalAlignment(SwingConstants.LEADING);
		btShowandHide.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/Unlock.png")));
		btShowandHide.setBounds(-11, 147, 129, 35);
		btShowandHide.setForeground(new Color(102, 0, 255));
		btShowandHide.setBackground(Color.WHITE);
		btShowandHide.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		pnllogin.add(btShowandHide);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 51, 255));
		panel.setBounds(8, 43, 332, 39);
		pnllogin.add(panel);
		panel.setLayout(null);

		lbusername = new JLabel("Username");
		lbusername.setForeground(Color.WHITE);
		lbusername.setBounds(10, 0, 111, 39);
		panel.add(lbusername);
		lbusername.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/User.png")));
		lbusername.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));

		tfusername = new JTextField();
		tfusername.setBounds(122, 0, 210, 39);
		panel.add(tfusername);
		tfusername.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		tfusername.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(153, 51, 255));
		panel_1.setBounds(8, 103, 332, 39);
		pnllogin.add(panel_1);

		lbpassword = new JLabel("Password");
		lbpassword.setForeground(Color.WHITE);
		lbpassword.setBounds(10, 0, 111, 39);
		panel_1.add(lbpassword);
		lbpassword.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/Lock.png")));
		lbpassword.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));

		pfpassword = new JPasswordField();
		pfpassword.setBounds(121, 0, 211, 39);
		panel_1.add(pfpassword);
		pfpassword.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));

		// Panel chá»©a nÃºt
		panelbutton = new JPanel();
		panelbutton.setBackground(new Color(153, 51, 255));
		panelbutton.setBounds(357, 108, 143, 192);
		contentPane.add(panelbutton);
		panelbutton.setLayout(null);

		btLogin = new JButton("Login");
		btLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = tfusername.getText();
				String password = pfpassword.getText();
				try {
					Connection connection = (Connection) DriverManager.getConnection(
							"jdbc:sqlserver://localhost:1433;databaseName=BaiTapLonJava;user=sa;password=20112003");
					PreparedStatement st = (PreparedStatement) connection
							.prepareStatement("Select admin, pass from Login where admin=? and pass=?");
					st.setString(1, userName);
					st.setString(2, password);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						dispose();
						ManagerAccount managerAccount = new ManagerAccount();
						managerAccount.setVisible(true);
					} else {
						lbchecklogin.setText("Wrong Username & Password");
					}
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		});
		btLogin.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/Accept.png")));
		btLogin.setForeground(Color.BLACK);
		btLogin.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		btLogin.setBackground(new Color(102, 0, 255));
		btLogin.setBounds(15, 36, 124, 35);
		panelbutton.add(btLogin);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/Exit.png")));
		btnCancel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		btnCancel.setBackground(new Color(102, 0, 255));
		btnCancel.setBounds(15, 134, 124, 35);
		panelbutton.add(btnCancel);

		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register register = getModel();
				if (validateForm()) {
					try {
						acd.add(register);
						JOptionPane.showMessageDialog(null, "Da tao thanh cong", "Da tao thanh cong", getDefaultCloseOperation());

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					clear();
				} else {
					JOptionPane.showMessageDialog(null, "Ban nhap chua day du thong tin", "Ban nhap chua day du thong tin", getDefaultCloseOperation());
				}
				
			}
		});
		btnRegister.setIcon(new ImageIcon(LoginView.class.getResource("/Icon/Create.png")));
		btnRegister.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		btnRegister.setBackground(new Color(102, 0, 255));
		btnRegister.setBounds(15, 89, 124, 35);
		panelbutton.add(btnRegister);


	}
	
	public boolean validateForm() {
		if (pfpassword.getText().isEmpty() || tfusername.getText().isEmpty()) {
			return false;
		}
		return true;
	}

	public void clear() {
		tfusername.setText("");
		pfpassword.setText("");
	}
	
	private Register getModel() {
		register.setUsername(tfusername.getText());
		register.setPassword(pfpassword.getText());
		return register;

	}

}

