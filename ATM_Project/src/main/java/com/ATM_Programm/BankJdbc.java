package com.ATM_Programm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BankJdbc {
	public static void main(String[] args) {
		Bank obj = new Bank();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("::WELCOME TO OUR ATM::");
			System.out.println("----------------------");
			System.out.println("Enter your Username:");
			String eusername = scan.nextLine();
			System.out.println("Enter your Password:");
			String epassword = scan.nextLine();

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery(
					"Select ACCOUNT_NUMBER,username,password,ACCOUNT_BALANCE,Name from BANKPROGRAMM where username='"
							+ eusername + "' AND password='" + epassword + "'");// if u need to update don't select *
			if (rs.next()) {
				obj.accno = rs.getInt("ACCOUNT_NUMBER");
				obj.accbalance = rs.getFloat("ACCOUNT_BALANCE");
				String name = rs.getString("name");
				System.out.println("WELCOME MR." + name);
				System.out.println();
				while (true) {
					System.out.println("PLEASE SELECT FOLLOWING OPTIONS");
					System.out.println("1)Check Balance    2)Withdraw Money");
					System.out.println("3)Deposit Money    4)MiniStatment");
					System.out.println("5)Exit");

					int inp_opt1 = scan.nextInt();

					switch (inp_opt1) {
					case 1:
						System.out.print("Your Account Balance is:");
						System.err.println(+obj.accbalance);
						System.out.println("---------------------------------");
						break;

					case 2: {
						boolean var2 = true;
						while (var2) {
							System.out.println("ENTER AMOUNT TO BE WITHDRAWN");
							obj.withdrawn_amount = scan.nextInt();
							System.out.println("Entered Amount is:" + obj.withdrawn_amount);
							System.out.println("1)CONFORM\t2)NOT CONFORM\t3)Exit");
							System.out.println("SELECT AN OPTION");
							int inp_opt2 = scan.nextInt();
							switch (inp_opt2) {
							case 1:
								if (obj.withdrawn_amount <= obj.accbalance) {
									System.out.println("Rs." + obj.withdrawn_amount + " has been Withdrawn");
									obj.accbalance = obj.accbalance - obj.withdrawn_amount;
									if (rs.getInt("ACCOUNT_NUMBER") == obj.accno)
										rs.updateFloat("ACCOUNT_BALANCE", obj.accbalance);
									rs.updateRow();
									obj.mini();
									System.out.println("Please Collect the Cash");
									System.out.println("1)Check Balance\t2)Exit");
									System.out.println("SELECT AN OPTION");
									int inp_opt3=scan.nextInt();
									switch(inp_opt3) {
									case 1:System.out.println(obj.accbalance);
									System.exit(0);
									case 2:System.exit(0);
									}

								} else {
									System.err.println("Insufficient Balance");
								}
								System.out.println();
								var2 = false;
								break;
							case 2:
								break;

							case 3:
								System.exit(0);
							default:
								System.err.println("CHECK THE INPUT VALUE");

							}

						}
						break;
					}
					case 3: {
						boolean var3 = true;
						while (var3) {
							System.out.println("ENTER AMOUNT TO BE DEPOSITED");
							obj.deposited_amount = scan.nextInt();
							System.out.println("Entered Amount is:" + obj.deposited_amount);
							System.out.println("1)CONFORM\t2)NOT CONFORM\t3)Exit");
							System.out.println("SELECT AN OPTION");
							int inp_opt3 = scan.nextInt();
							switch (inp_opt3) {
							case 1:
								if (obj.deposited_amount > 0) {
									System.out.println("Rs." + obj.deposited_amount + " has been Deposited");
									obj.accbalance = obj.accbalance + obj.deposited_amount;
									if (rs.getInt("ACCOUNT_NUMBER") == obj.accno)
										rs.updateFloat("ACCOUNT_BALANCE", obj.accbalance);
									rs.updateRow();
									obj.mini();
									System.out.println("Money has been Deposited");
									System.out.println("1)Check Balance\t2)Exit");
									System.out.println("SELECT AN OPTION");
									int inp_opt4=scan.nextInt();
									switch(inp_opt4) {
									case 1:System.out.println(obj.accbalance);
									System.exit(0);
									case 2:System.exit(0);
									}
								} else {
									System.err.println("Check the Amount to be Deposit");
								}
								System.out.println();
								var3 = false;
								break;
							case 2:
								break;

							case 3:
								System.exit(0);
							default:
								System.err.println("CHECK THE INPUT VALUE");

							}

						}
						break;
					}
					case 4:
						obj.ministatment();
						System.out.println("Thank You :: Visit Again");
						System.exit(0);

					case 5:
						System.out.println("Thank You :: Visit Again");
						System.exit(0);

					default:
						System.err.println("CHECK THE INPUT VALUE");
					}

				}
			} else {
				System.err.println("Invalid UserName or Password");
				scan.close();

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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}