package com.ATM_Programm;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Bank {
	float accbalance;
	int accno;
	int withdrawn_amount;
	int deposited_amount;

	public void mini() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");

			String query = "INSERT INTO MINI_STMT VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			try {
				pstmt.setInt(1, this.accno);
				pstmt.setInt(2, this.withdrawn_amount);
				pstmt.setInt(3, this.deposited_amount);
				pstmt.setFloat(4, this.accbalance);
				pstmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
				pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void ministatment() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			stmt = conn.createStatement();

			rs = stmt.executeQuery(
					"select * from mini_stmt WHERE ACCOUNT_NUMBER=" + this.accno + "ORDER BY DATE_TIME DESC");
			System.out.println(
					"ACC_NO" + "\t" + "\t" + "DEBIT" + "\t" + "CREDIT" + "\t" + "BALANCE" + "\t" + "DATE&TIME");
			while (rs.next()) {
				System.out.print(rs.getInt("ACCOUNT_NUMBER") + "  \t");
				System.out.print(rs.getInt("DEBIT") + "  \t");
				System.out.print(rs.getInt("CREDIT") + "  \t");
				System.out.print(rs.getFloat("BALANCE") + "\t");
				Timestamp ts = rs.getTimestamp("DATE_TIME");
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				System.out.println(formatter.format(ts));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}