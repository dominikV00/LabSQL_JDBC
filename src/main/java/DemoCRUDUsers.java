import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCRUDUsers {

    static Scanner sc = new Scanner(System.in);

//    public static void main(String[] args) throws SQLException {
//
//        DemoCRUDUsers object = new DemoCRUDUsers();
//        Scanner sc = new Scanner(System.in);
//        String usernamekb = null, passwordkb = null;
//        User u = new User(usernamekb, passwordkb);
//
//        int x = 0;
//        while (x != -1) {
//            x = menuUsers();
//            switch (x) {
//                case 1:
//                    System.out.println("Username: ");
//                    usernamekb = sc.nextLine();
//                    System.out.println("Password: ");
//                    passwordkb = sc.nextLine();
//                    u.setUsername(usernamekb);
//                    u.setPassword(passwordkb);
//                    boolean isAdded = object.insert(u);
//                    if (isAdded)
//                        System.out.println(usernamekb + " user added successfully!");
//                    else
//                        System.out.println("ERROR");
//                    break;
//                case 2:
//                    List<User> result = object.readAllUsers();
//                    for (User r : result)
//                        System.out.println(r);
//                    break;
//                case 3:
//                    System.out.println("Update username's password : ");
//                    usernamekb = sc.nextLine();
//                    System.out.println("New password: ");
//                    passwordkb = sc.nextLine();
//                    u.setUsername(usernamekb);
//                    u.setPassword(passwordkb);
//                    boolean newPass = object.update(u);
//                    if (newPass)
//                        System.out.println(usernamekb + " user's password updated successfully!");
//                    else
//                        System.out.println("ERROR");
//                    break;
//                case 4:
//                    System.out.println("Username to delete : ");
//                    usernamekb = sc.nextLine();
//                    System.out.println("Password: ");
//                    passwordkb = sc.nextLine();
//                    u.setUsername(usernamekb);
//                    u.setPassword(passwordkb);
//                    boolean deleteUser = object.delete(u);
//                    if (deleteUser)
//                        System.out.println(usernamekb + " user deleted successfully!");
//                    else
//                        System.out.println("ERROR");
//                    break;
//                case -1:
//                    break;
//                default:
//                    System.out.println("Menu is not existing!");
//            }
//        }


    public static boolean insert(User u) throws SQLException {

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("insert into users(username,password) values (?,?)");
        pSt.setString(1, u.getUsername());
        pSt.setString(2, u.getPassword());
        int val = pSt.executeUpdate();

        return val != 0;
    }

    public static List<User> readAllUsers() throws SQLException {

        List<User> lu = new ArrayList<>();

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        Statement pSt = conn.createStatement();
        ResultSet rs = pSt.executeQuery("select * from users order by id asc ");

        while (rs.next()) {
            String user = rs.getString("username").trim();
            String password = rs.getString("password").trim();
            long id = rs.getLong("id");
            User u = new User(user, password);
            u.setId(id);
            lu.add(u);
        }
        return lu;
    }

    public static boolean update(User u) throws SQLException {

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

    public static boolean delete(User u) {

        // conectare la DB
        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        int val = 0;
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare SQL
            PreparedStatement pSt = conn.prepareStatement("delete from users where username = ? and password = ?");
            pSt.setString(1, u.getUsername());
            pSt.setString(2, u.getPassword());
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return val != 0;
    }

    static int menuUsers() {
        System.out.println("CRUD Demo menu :");
        System.out.println("(1)  Insert new user.");
        System.out.println("(2)  Print all users.");
        System.out.println("(3)  Update an existent user.");
        System.out.println("(4)  Delete a user.");
        System.out.println("(5)  Enter food logging menu.");
        System.out.println("(-1)  EXIT");
        return new Scanner(System.in).nextInt();
    }

    static int menuFood() {
        System.out.println("Food logging menu :");
        System.out.println("(1)  Insert new food.");
        System.out.println("(2)  Print all my logged food.");
        System.out.println("(3)  Update my food logging list.");
        System.out.println("(4)  Delete logged food.");
        System.out.println("(-1)  EXIT");
        return new Scanner(System.in).nextInt();
    }

    public static List<Food> readFoodOfAUser(long id) throws SQLException {

        List<Food> lf = new ArrayList<>();

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("select foodname from loggedfood where iduser = ? ");
        pSt.setLong(1, id);
        ResultSet rs = pSt.executeQuery();

        while (rs.next()) {
            String foodname = rs.getString("foodname").trim();
            Food u = new Food(foodname);
            lf.add(u);
        }

        return lf;
    }

    public static boolean insertFoodForAUser(Food food, long id) throws SQLException {

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("insert into loggedfood (foodname,iduser) values (?,?)");
        pSt.setString(1, food.getFoodname());
        pSt.setLong(2, id);
        int val = pSt.executeUpdate();

        return val != 0;

    }

    public static boolean updateFoodOfAUser(Food oldfood, Food newfood, long id) throws SQLException {

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("update loggedfood set foodname = ? where foodname = ? and iduser = ? ");
        pSt.setString(1, newfood.getFoodname());
        pSt.setString(2, oldfood.getFoodname());
        pSt.setLong(3, id);
        int val = pSt.executeUpdate();

        return val != 0;

    }

    public static boolean deleteFoodForAUser(Food food, long id) throws SQLException {

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("delete from loggedfood where foodname = ? and iduser = ? ");
        pSt.setString(1, food.getFoodname());
        pSt.setLong(2, id);
        int val = pSt.executeUpdate();

        return val != 0;
    }

    public long login(User u) throws SQLException {

        long id = -1;

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("select id from users where username=? and password=?");
        pSt.setString(1, u.getUsername());
        pSt.setString(2, u.getPassword());
        ResultSet rs = pSt.executeQuery();


        while (rs.next()) {
            id = rs.getLong("id");
            return id;
        }

        return id;
    }

    public boolean isAdmin(User u) throws SQLException {

        boolean isAdmin = false;

        final String URLDB = "jdbc:postgresql://localhost:5432/javagroup";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "itdomi";
        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        PreparedStatement pSt = conn.prepareStatement("select isadmin from users where username = ? and password = ?");
        pSt.setString(1, u.getUsername());
        pSt.setString(2, u.getPassword());
        ResultSet rs = pSt.executeQuery();


        while (rs.next()) {
            isAdmin = rs.getBoolean("isadmin");
            return isAdmin;
        }

        return isAdmin;
    }

    static void adminMenu(long id) throws SQLException {

        String usernamekb = null, passwordkb = null;
        User u = new User(usernamekb, passwordkb);

        System.out.println("User is ADMIN");

        while (true) {
            int x = 0;
            while (x != -1) {
                x = DemoCRUDUsers.menuUsers();
                switch (x) {
                    case 1:
                        System.out.println("Username: ");
                        usernamekb = sc.nextLine();
                        System.out.println("Password: ");
                        passwordkb = sc.nextLine();
                        u.setUsername(usernamekb);
                        u.setPassword(passwordkb);
                        boolean isAdded = insert(u);
                        if (isAdded)
                            System.out.println(usernamekb + " user added successfully!");
                        else
                            System.out.println("ERROR");
                        break;
                    case 2:
                        List<User> result = readAllUsers();
                        for (User r : result)
                            System.out.println(r);
                        break;
                    case 3:
                        System.out.println("Update username's password : ");
                        usernamekb = sc.nextLine();
                        System.out.println("New password: ");
                        passwordkb = sc.nextLine();
                        u.setUsername(usernamekb);
                        u.setPassword(passwordkb);
                        boolean newPass = update(u);
                        if (newPass)
                            System.out.println(usernamekb + " user's password updated successfully!");
                        else
                            System.out.println("ERROR");
                        break;
                    case 4:
                        System.out.println("Username to delete : ");
                        usernamekb = sc.nextLine();
                        System.out.println("Password: ");
                        passwordkb = sc.nextLine();
                        u.setUsername(usernamekb);
                        u.setPassword(passwordkb);
                        boolean deleteUser = delete(u);
                        if (deleteUser)
                            System.out.println(usernamekb + " user deleted successfully!");
                        else
                            System.out.println("ERROR");
                        break;
                    case 5:
                        int y = 0;
                        while (y != -1) {
                            y = DemoCRUDUsers.menuFood();
                            switch (y) {
                                case 1:
                                    System.out.println("Insert a food please: ");
                                    String foodName = sc.nextLine();
                                    Food f = new Food(foodName);
                                    boolean success = insertFoodForAUser(f, id);
                                    if (success)
                                        System.out.println("Food logged successfully");
                                    else
                                        System.out.println("ERROR");
                                    break;
                                case 2:
                                    System.out.println("Client " + id + " food list is :");
                                    List<Food> foodList = readFoodOfAUser(id);
                                    for (Food fl : foodList)
                                        System.out.println(fl);
                                    break;
                                case 3:
                                    System.out.println("Food to replace:");
                                    String oldFood = sc.nextLine();
                                    System.out.println("Enter the new food");
                                    String newFood = sc.nextLine();
                                    Food oldf = new Food(oldFood);
                                    Food newf = new Food(newFood);
                                    boolean successUpdate = updateFoodOfAUser(oldf, newf, id);
                                    if (successUpdate)
                                        System.out.println("Food updated successfully");
                                    else
                                        System.out.println("ERROR");
                                    break;
                                case 4:
                                    System.out.println("Food to delete from list:");
                                    String deleteFood = sc.nextLine();
                                    Food deletf = new Food(deleteFood);
                                    boolean successDelete = deleteFoodForAUser(deletf, id);
                                    if (successDelete)
                                        System.out.println("Food deleted successfully");
                                    else
                                        System.out.println("ERROR");
                                    break;
                                case -1:
                                    break;
                                default:
                                    System.out.println("Menu is not existing!");
                            }
                        }
                        break;
                    case -1:
                        break;
                    default:
                        System.out.println("Menu is not existing!");
                        break;
                }
            }
            break;
        }
    }

    static void notAdminMenu(long id) throws SQLException {

        while (true) {
            int y = 0;
            while (y != -1) {
                y = DemoCRUDUsers.menuFood();
                switch (y) {
                    case 1:
                        System.out.println("Insert a food please: ");
                        String foodName = sc.nextLine();
                        Food f = new Food(foodName);
                        boolean success = insertFoodForAUser(f, id);
                        if (success)
                            System.out.println("Food logged successfully");
                        else
                            System.out.println("ERROR");
                        break;
                    case 2:
                        System.out.println("Client " + id + " food list is :");
                        List<Food> foodList = readFoodOfAUser(id);
                        for (Food fl : foodList)
                            System.out.println(fl);
                        break;
                    case 3:
                        System.out.println("Food to replace:");
                        String oldFood = sc.nextLine();
                        System.out.println("Enter the new food");
                        String newFood = sc.nextLine();
                        Food oldf = new Food(oldFood);
                        Food newf = new Food(newFood);
                        boolean successUpdate = updateFoodOfAUser(oldf, newf, id);
                        if (successUpdate)
                            System.out.println("Food updated successfully");
                        else
                            System.out.println("ERROR");
                        break;
                    case 4:
                        System.out.println("Food to delete from list:");
                        String deleteFood = sc.nextLine();
                        Food deletf = new Food(deleteFood);
                        boolean successDelete = deleteFoodForAUser(deletf, id);
                        if (successDelete)
                            System.out.println("Food deleted successfully");
                        else
                            System.out.println("ERROR");
                        break;
                    case -1:
                        break;
                    default:
                        System.out.println("Menu is not existing!");
                        break;
                }
            }
            break;
        }
    }


    }