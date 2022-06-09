package View;

import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;
import com.toedter.calendar.JDateChooser;

import Controller.AccountDao;
import Controller.ConnectSQL;
import Model.Account;
import Model.Statistical;


public class ManagerAccount extends JFrame{

	public JPanel menumain, panel_card, panel_Admin, panel_List, panel_Feature, panelbutton;
	public JButton btnList, btnFeature, btnDelete, btnAdmin, btnShutdown, btnLogout, btnAdd, btnEdit;
	public JLabel lblAvatar, lblName, lblDate, lblGender, lblList, lblUsername, lblNameApp, lblPassword, lblLink,
			lblType, lblInfo, lblDateadd;
	public JTable tbManager, tbManager1;
	public JTextField tfLink, tfUsername, tfPassword;
	public JScrollPane scrollPane, scrollPane_1;
	AccountDao accountDao = new AccountDao();
	Account account = new Account();
	public String stt_acc;
	public CardLayout cardLayout;
	public JTextField tfSearch;
	public JButton btnReset;
	public DefaultTableModel model, model1;
	public String stt_acc1;
	public JComboBox comboBox;
	public JButton btnStatistics;
	private StatisticalView tkv;
	private JDateChooser dateChooser;

	// Khởi tạo giao diện
	public ManagerAccount() {
		setTitle("ManagerAccount");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		init();
		FillDataTable1();
	}

	public void init() {
		// Panel Chính
		menumain = new JPanel();
		menumain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(menumain);
		menumain.setLayout(null);

		// Panel nút chứa các mục
		panelbutton = new JPanel();
		panelbutton.setBackground(new Color(153, 51, 255));
		panelbutton.setBounds(0, 0, 190, 500);
		menumain.add(panelbutton);
		panelbutton.setLayout(null);

		btnList = new JButton("List");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FillDataTable();
				cardLayout.show(panel_card, "2");
			}
		});
		btnList.setHorizontalAlignment(SwingConstants.LEADING);
		btnList.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/danhsach.png")));
		btnList.setBackground(new Color(153, 153, 255));
		btnList.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		btnList.setBounds(10, 168, 170, 51);
		panelbutton.add(btnList);

		btnFeature = new JButton("Feature");
		btnFeature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel_card, "3");
			}
		});
		btnFeature.setHorizontalAlignment(SwingConstants.LEADING);
		btnFeature.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/feature.png")));
		btnFeature.setBackground(new Color(153, 153, 255));
		btnFeature.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		btnFeature.setBounds(10, 254, 170, 51);
		panelbutton.add(btnFeature);

		btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FillDataTable1();
				cardLayout.show(panel_card, "1");
			}
		});
		btnAdmin.setHorizontalAlignment(SwingConstants.LEADING);
		btnAdmin.setBackground(new Color(153, 153, 255));
		btnAdmin.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Admin.png")));
		btnAdmin.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 25));
		btnAdmin.setBounds(10, 25, 170, 86);
		panelbutton.add(btnAdmin);

		btnShutdown = new JButton("");
		btnShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnShutdown.setBackground(new Color(153, 153, 255));
		btnShutdown.setBounds(67, 426, 54, 51);
		panelbutton.add(btnShutdown);
		btnShutdown.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/off.png")));
		btnShutdown.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 132, 170, 2);
		panelbutton.add(separator);

		btnStatistics = new JButton("Statistics");
		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tkv = new StatisticalView();
				tkv.setVisible(true);
			}
		});
		btnStatistics.setHorizontalAlignment(SwingConstants.LEADING);
		btnStatistics.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/thongke.png")));
		btnStatistics.setBackground(new Color(153, 153, 255));
		btnStatistics.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		btnStatistics.setBounds(10, 330, 170, 51);
		panelbutton.add(btnStatistics);

		// Panel card (CardLayout) chứa các các Panel con
		cardLayout = new CardLayout();
		panel_card = new JPanel(cardLayout);
		panel_card.setBounds(188, 0, 712, 500);
		menumain.add(panel_card);

		// Panel card 1 (chứa thông tin người quản lí)
		panel_Admin = new JPanel();
		panel_Admin.setBackground(new Color(153, 153, 255));
		panel_Admin.setLayout(null);
		panel_card.add(panel_Admin, "1");

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginView().setVisible(true);
			}
		});
		btnLogout.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Log out.png")));
		btnLogout.setBackground(new Color(153, 153, 255));
		btnLogout.setBounds(31, 326, 127, 44);
		panel_Admin.add(btnLogout);
		btnLogout.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(387, 371, 325, 105);
		tbManager1 = new JTable();

		tbManager1.setModel(new DefaultTableModel(
				new Object[][] { { null, null } },
				new String[] {
						"Type", "Quantily"
				}));
		scrollPane_1.setViewportView(tbManager1);
		panel_Admin.add(scrollPane_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(153, 51, 255));
		panel_2.setBounds(0, 0, 712, 105);
		panel_Admin.add(panel_2);
		panel_2.setLayout(null);

		lblNameApp = new JLabel("Account Management");
		lblNameApp.setBounds(0, 0, 712, 105);
		panel_2.add(lblNameApp);
		lblNameApp.setForeground(Color.WHITE);
		lblNameApp.setHorizontalAlignment(SwingConstants.CENTER);
		lblNameApp.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 50));

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(153, 204, 255));
		panel_5.setBounds(204, 173, 508, 149);
		panel_Admin.add(panel_5);
		panel_5.setLayout(null);

		lblName = new JLabel("Manager name : Nguyen Van Hieu");
		lblName.setBounds(10, 35, 316, 37);
		panel_5.add(lblName);
		lblName.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		lblDate = new JLabel("Date : 20-11-2003");
		lblDate.setBounds(10, 75, 184, 37);
		panel_5.add(lblDate);
		lblDate.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		lblGender = new JLabel("Gender : Male");
		lblGender.setBounds(10, 112, 175, 37);
		panel_5.add(lblGender);
		lblGender.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		JLabel lblIdit = new JLabel("ID : 21IT610");
		lblIdit.setForeground(Color.BLACK);
		lblIdit.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		lblIdit.setBounds(10, 0, 184, 37);
		panel_5.add(lblIdit);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(153, 51, 255));
		panel_6.setBounds(204, 125, 508, 49);
		panel_Admin.add(panel_6);
		panel_6.setLayout(null);

		JLabel lblnorma = new JLabel("Information Admin");
		lblnorma.setBounds(0, 0, 343, 49);
		panel_6.add(lblnorma);
		lblnorma.setForeground(Color.WHITE);
		lblnorma.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 30));

		JPanel panel_6_1 = new JPanel();
		panel_6_1.setLayout(null);
		panel_6_1.setBackground(new Color(153, 51, 255));
		panel_6_1.setBounds(387, 326, 325, 49);
		panel_Admin.add(panel_6_1);

		JLabel lblNewLabel = new JLabel("Monitor");
		lblNewLabel.setBounds(10, 0, 325, 49);
		panel_6_1.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 30));

		lblAvatar = new JLabel("");
		lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvatar.setBounds(10, 125, 172, 191);
		panel_Admin.add(lblAvatar);
		lblAvatar.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/21IT610.png")));

		// Panel card 2 (chứa danh sách tài khoản , xóa tài khoản)
		panel_List = new JPanel();
		panel_List.setBackground(new Color(153, 153, 255));
		panel_card.add(panel_List, "2");

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 220, 671, 270);
		tbManager = new JTable();
		tbManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int Username = tbManager.rowAtPoint(e.getPoint());
				stt_acc = tbManager.getValueAt(Username, 0).toString();
				Account ac = accountDao.getAccountUsername(stt_acc);
				setModel(ac);
			}
		});
		panel_List.setLayout(null);
		tbManager.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null } },
				new String[] {
						"Account", "Password", "Type", "Date", "Link"
				}));
		scrollPane.setViewportView(tbManager);
		panel_List.add(scrollPane);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * if(stt_acc.length()>0)
				 * {}
				 */
				int op = JOptionPane.showConfirmDialog(null, "Ban muon xoa tai khoản nay khong", "Delete",
						JOptionPane.YES_NO_CANCEL_OPTION);
	
				if (op == JOptionPane.YES_OPTION) {
					try {
						accountDao.del(stt_acc);
					} catch (Exception o) {
						o.printStackTrace();
					}
	
					FillDataTable();
	
					clear();
				}
			}
		});
		btnDelete.setBackground(new Color(153, 51, 255));
		btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		btnDelete.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Delete.png")));
		btnDelete.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 25));
		btnDelete.setBounds(532, 138, 133, 48);
		panel_List.add(btnDelete);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 51, 255));
		panel.setBounds(33, 138, 424, 48);
		panel_List.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(374, 0, 50, 48);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Search.png")));

		tfSearch = new JTextField();
		tfSearch.setBounds(0, 0, 374, 48);
		panel.add(tfSearch);
		tfSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchString = tfSearch.getText();
				search(searchString);
			}
		});
		tfSearch.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		tfSearch.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(153, 0, 255));
		panel_1.setForeground(Color.WHITE);
		panel_1.setBounds(0, 0, 712, 105);
		panel_List.add(panel_1);
		panel_1.setLayout(null);

		lblList = new JLabel("List Of Accounts");
		lblList.setBounds(0, 0, 712, 105);
		panel_1.add(lblList);
		lblList.setForeground(Color.WHITE);
		lblList.setIcon(null);
		lblList.setHorizontalAlignment(SwingConstants.CENTER);
		lblList.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 45));

		// Panel card 3 (thêm, sửa)
		panel_Feature = new JPanel();
		panel_Feature.setBackground(new Color(153, 153, 255));
		panel_card.add(panel_Feature, "3");
		panel_Feature.setLayout(null);

		tfLink = new JTextField();
		tfLink.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		tfLink.setBounds(140, 441, 335, 35);
		panel_Feature.add(tfLink);
		tfLink.setColumns(10);

		tfPassword = new JTextField();
		tfPassword.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		tfPassword.setColumns(10);
		tfPassword.setBounds(140, 257, 335, 35);
		panel_Feature.add(tfPassword);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account account = getModel();
				if (validateForm()) {
					try {
						accountDao.add(account);
						JOptionPane.showMessageDialog(null,"Da them thanh cong", "Da them thanh cong",JOptionPane.CANCEL_OPTION);
						FillDataTable();
	
						clear();
	
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Ban nhap chua day du thong tin", "Ban nhap chua day du thong tin",JOptionPane.CANCEL_OPTION);
				}
			}
		});
		btnAdd.setBackground(new Color(153, 153, 255));
		btnAdd.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Add.png")));
		btnAdd.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 25));
		btnAdd.setBounds(545, 220, 157, 43);
		panel_Feature.add(btnAdd);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateForm()) {
					Account account = getModel();
	
					try {
						accountDao.edit(account);						
						JOptionPane.showMessageDialog(null,"Da sua thanh cong", "Da sua thanh cong",JOptionPane.CANCEL_OPTION);
						FillDataTable();
	
					} catch (Exception e1) {
	
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Ban nhap chua day du thong tin","Ban nhap chua day du thong tin",JOptionPane.CANCEL_OPTION);
				}
				clear();
			}
		});
		btnEdit.setBackground(new Color(153, 153, 255));
		btnEdit.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Edit.png")));
		btnEdit.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 25));
		btnEdit.setBounds(545, 293, 157, 43);
		panel_Feature.add(btnEdit);

		String[] app = { "Gmail", "Facebook", "Zalo", "Game" };

		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		btnReset.setBackground(new Color(153, 153, 255));
		btnReset.setIcon(new ImageIcon(ManagerAccount.class.getResource("/Icon/Refresh.png")));
		btnReset.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 25));
		btnReset.setBounds(545, 366, 157, 43);
		panel_Feature.add(btnReset);

		String[] type = { "Gmail", "Facebook", "Zalo", "Game", "Bank" };

		comboBox = new JComboBox(type);
		comboBox.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		comboBox.setBounds(140, 319, 168, 35);
		panel_Feature.add(comboBox);

		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 15));
		dateChooser.setBounds(140, 379, 257, 35);
		panel_Feature.add(dateChooser);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(153, 51, 255));
		panel_3.setBounds(0, 0, 712, 105);
		panel_Feature.add(panel_3);
		panel_3.setLayout(null);

		lblInfo = new JLabel("Information processing");
		lblInfo.setForeground(Color.WHITE);
		lblInfo.setBounds(0, 0, 712, 105);
		panel_3.add(lblInfo);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD | Font.ITALIC, 40));

		JPanel panel_6_2 = new JPanel();
		panel_6_2.setBackground(new Color(153, 51, 255));
		panel_6_2.setBounds(11, 197, 464, 35);
		panel_Feature.add(panel_6_2);
		panel_6_2.setLayout(null);

		tfUsername = new JTextField();
		tfUsername.setBounds(132, 0, 332, 35);
		panel_6_2.add(tfUsername);
		tfUsername.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 15));
		tfUsername.setColumns(10);

		lblUsername = new JLabel("Account");
		lblUsername.setBounds(10, 0, 123, 33);
		panel_6_2.add(lblUsername);
		lblUsername.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		JPanel panel_6_2_1 = new JPanel();
		panel_6_2_1.setBackground(new Color(153, 51, 255));
		panel_6_2_1.setBounds(11, 257, 461, 35);
		panel_Feature.add(panel_6_2_1);
		panel_6_2_1.setLayout(null);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 0, 109, 35);
		panel_6_2_1.add(lblPassword);
		lblPassword.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		JPanel panel_6_2_2 = new JPanel();
		panel_6_2_2.setBackground(new Color(153, 51, 255));
		panel_6_2_2.setBounds(11, 319, 297, 35);
		panel_Feature.add(panel_6_2_2);
		panel_6_2_2.setLayout(null);

		lblType = new JLabel("Type");
		lblType.setBounds(10, 0, 109, 35);
		panel_6_2_2.add(lblType);
		lblType.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		JPanel panel_6_2_3 = new JPanel();
		panel_6_2_3.setBackground(new Color(153, 51, 255));
		panel_6_2_3.setBounds(11, 379, 386, 35);
		panel_Feature.add(panel_6_2_3);
		panel_6_2_3.setLayout(null);

		lblDateadd = new JLabel("Date");
		lblDateadd.setBounds(10, 0, 109, 35);
		panel_6_2_3.add(lblDateadd);
		lblDateadd.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		JPanel panel_6_2_4 = new JPanel();
		panel_6_2_4.setBackground(new Color(153, 51, 255));
		panel_6_2_4.setBounds(11, 441, 461, 35);
		panel_Feature.add(panel_6_2_4);
		panel_6_2_4.setLayout(null);

		lblLink = new JLabel("Link");
		lblLink.setBounds(10, 0, 109, 35);
		panel_6_2_4.add(lblLink);
		lblLink.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(102, 153, 255));
		panel_7.setBounds(10, 197, 464, 279);
		panel_Feature.add(panel_7);
		panel_7.setLayout(null);


	}

	// 6
	public static JFreeChart createChart() {
		JFreeChart barChart = ChartFactory.createBarChart(
				"Account Classification Chart",
				"Type", "Quantily",
				createDataset(), PlotOrientation.VERTICAL, false, true, true);
		return barChart;
	}

	private static CategoryDataset createDataset() {
		Connection conn = null;
		PreparedStatement sttm = null;
		ResultSet rst = null;
		try {
			String sSQL = "select type,count(*) as 'soluong' from QLAccount group by type";
			conn = ConnectSQL.openConnection();
			sttm = conn.prepareStatement(sSQL);
			rst = sttm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			while (rst.next()) {
				Statistical tk = new Statistical();
				tk.setType(rst.getString("Type"));
				tk.setSoluong(rst.getInt("soluong"));
				dataset.addValue(tk.getSoluong(), "Quantily", tk.getType());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataset;
	}

	public boolean validateForm() {
		if (tfPassword.getText().isEmpty() || tfUsername.getText().isEmpty() || tfLink.getText().isEmpty()) {
			return false;
		}
		return true;
	}

	public Account getModel() {
		account.setUsername(tfUsername.getText());
		account.setPassword(tfPassword.getText());
		account.setType(comboBox.getSelectedItem().toString());
		account.setDateAdd(dateChooser.getDate());
		account.setLink(tfLink.getText());
		return account;
	}

	private void clear() {
		tfUsername.setText("");
		tfPassword.setText("");
		tfLink.setText("");
	}

	public void FillDataTable() {
		model = (DefaultTableModel) tbManager.getModel();
		model.setRowCount(0);
		for (Account ac : accountDao.getAllAccount()) {
			Object dataRow[] = new Object[5];
			dataRow[0] = ac.getUsername();
			dataRow[1] = ac.getPassword();
			dataRow[2] = ac.getType();
			dataRow[3] = ac.getDateAdd();
			dataRow[4] = ac.getLink();
			model.addRow(dataRow);
		}
	}

	public void FillDataTable1() {
		model1 = (DefaultTableModel) tbManager1.getModel();
		model1.setRowCount(0);
		for (Statistical tk : accountDao.getAllType()) {
			Object dataRow[] = new Object[5];
			dataRow[0] = tk.getType();
			dataRow[1] = tk.getSoluong();
			model1.addRow(dataRow);
		}
	}

	public void setModel(Account account) {
		tfUsername.setText(account.getUsername());
		tfPassword.setText(account.getPassword());
		comboBox.setSelectedItem(account.getType());
		dateChooser.setDate(account.getDateAdd());
		tfLink.setText(account.getLink());
	}

	private void search(String str) {
		model = (DefaultTableModel) tbManager.getModel();
		TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
		tbManager.setRowSorter(trs);
		trs.setRowFilter(RowFilter.regexFilter(str));
	}

}
