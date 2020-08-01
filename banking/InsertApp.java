package banking;

import java.sql.*;

public class InsertApp {

    private Connection connect(String fileName) {
        String url = url = "jdbc:sqlite:" + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public void insert( String actNum, String pin, String fileName) {
        String sql = "INSERT INTO card ( number, pin) VALUES(?,?)";

        try (Connection conn = this.connect(fileName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, actNum);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String[] selectAct(String actNum, String fileName) {
        String sql = "SELECT number, pin, balance FROM card WHERE number = ?";
        String[] arr = {" ", " "};

        try (Connection conn = this.connect(fileName);
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,actNum);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set

            arr[0] = rs.getString("number");
            arr[1] = rs.getString("pin");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arr;
    }

    public int selectBalance(String actNum, String fileName) {
        String sql = "SELECT balance WHERE number = ? FROM card";
        int bal = 0;

        try (Connection conn = this.connect(fileName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, actNum);
            ResultSet rs = pstmt.executeQuery();
            bal = rs.getInt("balance");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return bal;
    }

    public void updateBalance(String fileName, String actNum, int num) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (Connection conn = this.connect(fileName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, num);
            pstmt.setString(2, actNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(String fileName, String actNum, String rNum, int num) {
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?";
        String sql2 = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (Connection conn = this.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, num);
            pstmt.setString(2, actNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (Connection conn = this.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, num);
            pstmt.setString(2, rNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String fileName, String actNum) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = this.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, actNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
