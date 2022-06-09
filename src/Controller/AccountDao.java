package Controller;

import java.sql.*;
import java.util.*;

import Model.Account;
import Model.Register;
import Model.Statistical;

public class AccountDao {
	public Connection conn = null;
	public PreparedStatement sttm = null;
	public ResultSet rst = null;
	public Statement attm = null;
	public List<Account> ls;
	private ArrayList ls1;

	public int add(Account ac) throws SQLException {

		try {
			String sSQL = "insert into QLAccount (Username,Password,Type,DateAdd,Link) values (?,?,?,?,?)";
			conn = ConnectSQL.openConnection();
			sttm = conn.prepareStatement(sSQL);
			sttm.setString(1, ac.getUsername());
			sttm.setString(2, ac.getPassword());
			sttm.setString(3, ac.getType());
			sttm.setDate(4, new java.sql.Date(ac.getDateAdd().getTime()));
			sttm.setString(5, ac.getLink());
			if (sttm.executeUpdate() > 0) {
				System.out.println("da them thanh cong");
			}

		} catch (Exception e) {
			System.out.println("da them khong thanh cong");
		}
		conn.close();
		sttm.close();

		return -1;

	}

	public int add(Register rg) throws SQLException {

		try {
			String sSQL = "insert into Login (admin,pass) values (?,?)";
			conn = ConnectSQL.openConnection();
			sttm = conn.prepareStatement(sSQL);
			sttm.setString(1, rg.getUsername());
			sttm.setString(2, rg.getPassword());
			if (sttm.executeUpdate() > 0) {
				System.out.println("da them thanh cong");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		sttm.close();

		return -1;

	}

	public int edit(Account ac) throws SQLException {

		try {
			String sSQL = "update QLAccount set Password = ? ,Type = ? ,DateAdd = ? ,Link = ? where Username = ? ";
			conn = ConnectSQL.openConnection();
			sttm = conn.prepareStatement(sSQL);

			sttm.setString(1, ac.getPassword());
			sttm.setString(2, ac.getType());
			sttm.setDate(3, new java.sql.Date(ac.getDateAdd().getTime()));
			sttm.setString(4, ac.getLink());
			sttm.setString(5, ac.getUsername());

			// sttm.executeUpdate();
			if (sttm.executeUpdate() > 0) {
				System.out.println("update thanh cong");
				return 1;
			}
		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
		}
		conn.close();
		sttm.close();

		return -1;

	}

	public int del(String Username) throws SQLException {

		try {
			String sSQL = "DELETE QLAccount where Username = ?";
			conn = ConnectSQL.openConnection();
			sttm = conn.prepareStatement(sSQL);
			sttm.setString(1, Username);
			sttm.executeUpdate();

		} catch (Exception e) {
			System.out.println("Error :" + e.toString());
		}
		conn.close();
		sttm.close();

		return -1;

	}

	public List<Account> getAllAccount() {
		ls = new ArrayList<>();
		try {
			String sSQL = "select Username,Password,Type,DateAdd,Link from QLAccount";
			conn = ConnectSQL.openConnection();
			attm = conn.createStatement();
			rst = attm.executeQuery(sSQL);
			while (rst.next()) {
				Account ac = new Account();
				ac.setUsername(rst.getString(1));
				ac.setPassword(rst.getString(2));
				ac.setType(rst.getString(3));
				ac.setDateAdd(rst.getDate(4));
				ac.setLink(rst.getString(5));
				ls.add(ac);
			}

		} catch (Exception e) {
			System.out.println("Error :" + e.toString());
		} finally {
			try {
				conn.close();
				sttm.close();
			} catch (Exception e) {
			}
		}
		return ls;
	}

	public List<Model.Statistical> getAllType() {
		ls1 = new ArrayList<>();
		try {
			String sSQL = "select type,count(*) as 'soluong'  from QLAccount group by type";
			conn = ConnectSQL.openConnection();
			attm = conn.createStatement();
			rst = attm.executeQuery(sSQL);
			while (rst.next()) {
				Statistical tk = new Statistical();
				tk.setType(rst.getString(1));
				tk.setSoluong(rst.getInt(2));

				ls1.add(tk);
			}

		} catch (Exception e) {
			System.out.println("Error :" + e.toString());
		} finally {
			try {
				conn.close();
				sttm.close();
			} catch (Exception e) {
			}
		}
		return ls1;
	}

	public Account getAccountUsername(String Username) {
		Account ac = new Account();
		try {
			String sSQL = "select Username,Password,Type,DateAdd,Link from QLAccount where Username = ?";
			conn = ConnectSQL.openConnection();
			sttm = conn.prepareStatement(sSQL);
			sttm.setString(1, Username);
			rst = sttm.executeQuery();
			while (rst.next()) {

				ac.setUsername(rst.getString(1));
				ac.setPassword(rst.getString(2));
				ac.setType(rst.getString(3));
				ac.setDateAdd(rst.getDate(4));
				ac.setLink(rst.getString(5));
				return ac;
			}

		} catch (Exception e) {
			System.out.println("Error :" + e.toString());
		} finally {
			try {
				conn.close();
				sttm.close();
				rst.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

}
