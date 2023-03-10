import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCRUDUsers {

    public static void main(String[] args) throws SQLException {

        DemoCRUDUsers object = new DemoCRUDUsers();

        int x = 0;
        while (x != -1) {
            x = menu();
            switch (x) {
                case 1 :
                    User u = new User("jon", "password231",9);
                    boolean isAdded = object.insert(u);
                    System.out.println(isAdded);
                    break;
                case 2 :
                    List<User> result = object.readAllUsers();
                    for (User r : result)
                        System.out.println(r);
                    break;
                case 3 :
                    User uPass = new User("jon", "iamnotjon",9);
                    boolean newPass = object.update(uPass);
                    System.out.println(newPass);
                    break;
                case 4 :
                    User uDelete = new User("jon", "iamnotjon",9);
                    boolean deleteUser = object.delete(uDelete);
                    System.out.println(deleteUser);
                    break;
                case -1 :
                    break;
                default :
                    System.out.println("Menu is not existing!");
            }
        }

    }

    private boolean insert(User u) throws SQLException {

        // conectare la DB
        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare SQL
        PreparedStatement pSt = conn.prepareStatement("insert into users(username,password,id) values (?,?,?)");
        pSt.setString(1, u.getUsername());
        pSt.setString(2, u.getPassword());
        pSt.setInt(3, u.getId());
        int val = pSt.executeUpdate();

        return val != 0;
    }

    private List<User> readAllUsers() throws SQLException {

        List<User> lu = new ArrayList<>();

        // conectare la DB
        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare SQL
        Statement pSt = conn.createStatement();
        ResultSet rs = pSt.executeQuery("select * from users order by id asc ");

        while (rs.next()) {
            String user = rs.getString("username").trim();
            String password = rs.getString("password").trim();
            int id = rs.getInt("id");
            User u = new User(user, password, id);
            lu.add(u);
        }
        return lu;
    }

    private boolean update(User u) throws SQLException {

        // conectare la DB
        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare SQL
        PreparedStatement pSt = conn.prepareStatement("update users set password = ? where username = ? ");
        pSt.setString(1, u.getPassword());
        pSt.setString(2, u.getUsername());
        int val = pSt.executeUpdate();

        return val != 0;
    }

    private boolean delete(User u) throws SQLException {

        // conectare la DB
        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        // rulare SQL
        PreparedStatement pSt = conn.prepareStatement("delete from users where username = ? ");
        pSt.setString(1, u.getUsername());
        int val = pSt.executeUpdate();

        return val != 0;
    }

    private static int menu() {
        System.out.println("CRUD Demo menu :");
        System.out.println("(1)  Insert new user.");
        System.out.println("(2)  Print all users.");
        System.out.println("(3)  Update an existent user.");
        System.out.println("(4)  Delete a user.");
        System.out.println("(-1)  EXIT");
        return new Scanner(System.in).nextInt();
    }

}