package bankingsystem;
import java.sql.*;
import java.util.Scanner;

public class BankingSystem {

    static final String URL = "jdbc:mysql://localhost:3306/OOP_priti";
    static final String USER = "root";
    static final String PASSWORD = "------"; //enter ur password

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Create Account
    static void createAccount(String name, double balance) {
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO accounts(name, balance, daily_limit) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, balance);
            ps.setDouble(3, 5000); // daily limit
            ps.executeUpdate();
            System.out.println("Account Created Successfully!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Deposit
    static void deposit(int id, double amount) {
        try (Connection con = getConnection()) {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE acc_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Deposit Successful!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Withdraw
    static void withdraw(int id, double amount) {
        try (Connection con = getConnection()) {

            String check = "SELECT balance, daily_limit FROM accounts WHERE acc_id=?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setInt(1, id);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble(1);
                double limit = rs.getDouble(2);

                if (amount > balance) {
                    System.out.println("Insufficient Balance!");
                } else if (amount > limit) {
                    System.out.println("Daily limit exceeded!");
                } else {
                    String sql = "UPDATE accounts SET balance = balance - ? WHERE acc_id = ?";
                    PreparedStatement ps2 = con.prepareStatement(sql);
                    ps2.setDouble(1, amount);
                    ps2.setInt(2, id);
                    ps2.executeUpdate();
                    System.out.println("Withdrawal Successful!");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Check Balance
    static void checkBalance(int id) {
        try (Connection con = getConnection()) {
            String sql = "SELECT balance FROM accounts WHERE acc_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Balance: " + rs.getDouble(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Display Account
    static void display(int id) {
        try (Connection con = getConnection()) {
            String sql = "SELECT * FROM accounts WHERE acc_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Balance: " + rs.getDouble(3));
                System.out.println("Daily Limit: " + rs.getDouble(4));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Display Account");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Initial Balance: ");
                    double bal = sc.nextDouble();
                    createAccount(name, bal);
                    break;

                case 2:
                    System.out.print("Enter Account ID: ");
                    int id1 = sc.nextInt();
                    System.out.print("Enter Amount: ");
                    double dep = sc.nextDouble();
                    deposit(id1, dep);
                    break;

                case 3:
                    System.out.print("Enter Account ID: ");
                    int id2 = sc.nextInt();
                    System.out.print("Enter Amount: ");
                    double wd = sc.nextDouble();
                    withdraw(id2, wd);
                    break;

                case 4:
                    System.out.print("Enter Account ID: ");
                    int id3 = sc.nextInt();
                    checkBalance(id3);
                    break;

                case 5:
                    System.out.print("Enter Account ID: ");
                    int id4 = sc.nextInt();
                    display(id4);
                    break;

                case 6:
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}
