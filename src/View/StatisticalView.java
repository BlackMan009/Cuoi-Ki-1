package View;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

import Controller.ConnectSQL;
import Model.Statistical;

public class StatisticalView extends JFrame {

	private JPanel contentPane;

	public StatisticalView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(153, 51, 255));
		panel.setBounds(115, 46, 485, 354);
		contentPane.add(panel);
		panel.setLayout(null);

		ChartPanel chartPanel = new ChartPanel(createChart());
		chartPanel.setBounds(10, 10, 706, 442);
		chartPanel.setPreferredSize(new Dimension(300, 200));
		panel.add(chartPanel);
		chartPanel.setLayout(null);

		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setIcon(new ImageIcon(StatisticalView.class.getResource("/Icon/Exit.png")));
		btnNewButton.setBackground(new Color(153, 153, 255));
		btnNewButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 20));
		btnNewButton.setBounds(737, 411, 132, 41);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	public static JFreeChart createChart() {
		JFreeChart barChart = ChartFactory.createBarChart(
				"Account Classification Chart",
				"Type", "Quantily",
				createDataset(), PlotOrientation.VERTICAL, true, true, true);
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
}