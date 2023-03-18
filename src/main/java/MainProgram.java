import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) throws SQLException {

        DemoCRUDUsers dbAccess = new DemoCRUDUsers();

        long id = -1;
        Scanner sc = new Scanner(System.in);
        String usernamekb, passwordkb;
        User u;

        while (true) {
            System.out.println("Username: ");
            usernamekb = sc.nextLine();
            System.out.println("Password: ");
            passwordkb = sc.nextLine();
            u = new User(usernamekb, passwordkb);
            id = dbAccess.login(u);
            u.setId(id);
            if (id != -1)
                break;
        }

        System.out.println("Logged is successfully!");

        while (true) {
            boolean isAdmin = dbAccess.isAdmin(u);
            if (isAdmin)
                DemoCRUDUsers.adminMenu(id);
            else
                DemoCRUDUsers.notAdminMenu(id);
            break;
        }
    }
}
