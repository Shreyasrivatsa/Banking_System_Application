package mysql;

    import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.Scanner;

	public class Banking_System_Application {

	    public static void main(String[] args) {
	        Scanner sc = new Scanner(System.in);
	        bankaccount bank = null;

	        while (bank == null) {
	            System.out.println("1. Create Account");
	            System.out.println("2. Login");
	            System.out.print("Enter your choice: ");
	            int choice = sc.nextInt();
	            sc.nextLine(); 
	            if (choice == 1) {
	                bank = bankaccount.createAccount(sc);
	            } else if (choice == 2) {
	                bank = bankaccount.login(sc);
	            } else {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }

	        if (bank != null) {
	            bank.ShowMenu();
	        }
	        sc.close();
	    }
	}

	class bankaccount {
	    int balance;
	    int previousTranscation;
	    String customer_name;
	    String customer_id;
	    Connection connection;

	    bankaccount(String cname, String cid, Connection conn) {
	        this.customer_name = cname;
	        this.customer_id = cid;
	        this.connection = conn;
	        this.balance = getBalanceFromDB(); 
	    }

	    private int getBalanceFromDB() {
	        try (PreparedStatement statement = connection.prepareStatement("SELECT balance FROM accounts WHERE customer_id = ?")) {
	            statement.setString(1, this.customer_id);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getInt("balance");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	        return 0; 
	    }

	    static bankaccount createAccount(Scanner sc) {
	        try {
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root", "root"); // Replace with your DB details

	            System.out.print("Enter customer name: ");
	            String cname = sc.nextLine();
	            System.out.print("Enter customer ID: ");
	            String cid = sc.nextLine();

	            // Check if account already exists
	            try (PreparedStatement checkStatement = connection.prepareStatement("SELECT 1 FROM accounts WHERE customer_id = ?")) {
	                checkStatement.setString(1, cid);
	                try (ResultSet resultSet = checkStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                        System.out.println("Account with this ID already exists.");
	                        connection.close();
	                        return null;
	                    }
	                }
	            }

	            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (customer_name, customer_id, balance) VALUES (?, ?, ?)")) {
	                statement.setString(1, cname);
	                statement.setString(2, cid);
	                statement.setInt(3, 0); // Initial balance
	                statement.executeUpdate();
	                System.out.println("Account created successfully.");
	                return new bankaccount(cname, cid, connection);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    static bankaccount login(Scanner sc) {
	        try {
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root", "root"); // Replace with your DB details

	            System.out.print("Enter customer ID: ");
	            String cid = sc.nextLine();
	            System.out.print("Enter customer name: ");
	            String cname = sc.nextLine();

	            try (PreparedStatement statement = connection.prepareStatement("SELECT customer_name FROM accounts WHERE customer_id = ?")) {
	                statement.setString(1, cid);
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    if (resultSet.next()) {
	                        String dbName = resultSet.getString("customer_name");
	                        if (dbName.equals(cname)) { 
	                            System.out.println("Login successful.");
	                            return new bankaccount(cname, cid, connection);
	                        } else {
	                            System.out.println("Incorrect name for this ID.");
	                        }
	                    } else {
	                        System.out.println("Account not found.");
	                    }
	                }
	            }
	            connection.close(); 
	            return null;
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	            return null;
	        }
	    }


	    void deposit(int amt) {
	        if (amt != 0) {
	            balance += amt;
	            previousTranscation = amt;
	            updateBalanceInDB(); // Update the database
	        }
	    }

	    void withdraw(int amt) {
	        if (amt != 0) {
	            if (amt <= balance) { // Check for sufficient balance
	                balance -= amt;
	                previousTranscation = -amt;
	                updateBalanceInDB();// Update the database
	            } else {
	                System.out.println("Insufficient balance.");
	            }
	        }
	    }

	    private void updateBalanceInDB() {
	        try (PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE customer_id = ?")) {
	            statement.setInt(1, this.balance);
	            statement.setString(2, this.customer_id);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle or log
	        }
	    }


	    void getpreviousTranscation() {
	        if (previousTranscation > 0) {
	            System.out.println("deposit :" + previousTranscation);
	        } else if (previousTranscation < 0) {
	            System.out.println("withdraw :" + Math.abs(previousTranscation));
	        } else {
	            System.out.println("no transcation is occured");
	        }
	    }

	    void ShowMenu() {
	        char option = '\0';
	        Scanner sc = new Scanner(System.in);
	        System.out.println("welcome " + customer_name);
	        System.out.println("Your ID is :" + customer_id);
	        System.out.println();
	        System.out.println("A. CHECK BALANCE");
	        System.out.println("B. DEPOSIT");
	        System.out.println("C. WITHDRAW");
	        System.out.println("D. PREVIOUS TRANSCATION");
	        System.out.println("E. EXIT");

	        do {
	            System.out.println("----------------------------");
	            System.out.println("enter the required option ");
	            System.out.println("----------------------------");
	            option = sc.next().charAt(0);

	            switch (option) {
	                case 'A':
	                    System.out.println("----------------------------");
	                    System.out.println("Balance is :" + balance);
	                    System.out.println("----------------------------");
	                    System.out.println();
	                    break;
	                case 'B':
	                    System.out.println("----------------------------");
	                    System.out.println("enter the amount to deposit");
	                    System.out.println("----------------------------");
	                    int amt = sc.nextInt();
	                    deposit(amt);
	                    System.out.println();
	                    break;
	                case 'C':
	                    System.out.println("----------------------------");
	                    System.out.println("enter the amount to be withdrawn");
	                    System.out.println("----------------------------");
	                    int amt1 = sc.nextInt();
	                    withdraw(amt1);
	                    System.out.println();
	                    break;
	                case 'D':
	                    System.out.println("----------------------------");
	                    getpreviousTranscation();
	                    System.out.println("----------------------------");
	                    System.out.println();
	                    break;
	                case 'E':
	                    System.out.println("----------------------------");
	                    break;
	                default:
	                    System.out.println("Invalid option!!! Please try again");
	                    break;
	            }

	        } while (option != 'E');
	        sc.close(); // Close the scanner to avoid resource leak
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

