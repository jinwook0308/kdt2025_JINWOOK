#StudentDAO
```
package DaoP;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    private Connection con;

    public StudentDAO() {
        try {
            String url = "jdbc:mysql://localhost:3306/school_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
            String user = "root";
            String pass = "1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("DB 연결 오류: " + e.getMessage());
        }
    }

    public void insertStudent(Student s) throws SQLException {
        String sql = "INSERT INTO student (stdno, stdname, phone, email) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, s.getStdno());
        pstmt.setString(2, s.getStdname());
        pstmt.setString(3, s.getPhone());
        pstmt.setString(4, s.getEmail());
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void updatePhone(int stdno, String newPhone) throws SQLException {
        String sql = "UPDATE student SET phone = ? WHERE stdno = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, newPhone);
        pstmt.setInt(2, stdno);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void deleteStudent(int stdno) throws SQLException {
        String sql = "DELETE FROM student WHERE stdno = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, stdno);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public ArrayList<Student> selectAll() throws SQLException {
        ArrayList<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Student s = new Student(
                rs.getInt("stdno"),
                rs.getString("stdname"),
                rs.getString("phone"),
                rs.getString("email")
            );
            list.add(s);
        }
        rs.close();
        stmt.close();
        return list;
    }

    public Student selectByPhone(String phone) throws SQLException {
        String sql = "SELECT * FROM student WHERE phone = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, phone);
        ResultSet rs = pstmt.executeQuery();
        Student s = null;
        if (rs.next()) {
            s = new Student(
                rs.getInt("stdno"),
                rs.getString("stdname"),
                rs.getString("phone"),
                rs.getString("email")
            );
        }
        rs.close();
        pstmt.close();
        return s;
    }

    public void close() throws SQLException {
        if (con != null) con.close();
    }
}
```
