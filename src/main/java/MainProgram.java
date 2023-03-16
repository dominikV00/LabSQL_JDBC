import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) throws SQLException {

        DemoCRUDUsers dbAccess = new DemoCRUDUsers();

        long id = -1;
        Scanner sc = new Scanner(System.in);
        String usernamekb = null, passwordkb = null;
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
            if (!isAdmin) {
                int y = 0;
                while (y != -1) {
                    y = DemoCRUDUsers.menuFood();
                    switch (y) {
                        case 1:
                            System.out.println("Insert a food please: ");
                            String foodName = sc.nextLine();
                            Food f = new Food(foodName);
                            boolean success = dbAccess.insertFoodForAUser(f, id);
                            if (success)
                                System.out.println("Food logged successfully");
                            else
                                System.out.println("ERROR");
                            break;
                        case 2:
                            System.out.println(usernamekb+" food list is :");
                            List <Food> foodList = dbAccess.readFoodOfAUser(id);
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
                            boolean successUpdate = dbAccess.updateFoodOfAUser(oldf,newf,id);
                            if (successUpdate)
                                System.out.println("Food updated successfully");
                            else
                                System.out.println("ERROR");
                            break;
                        case 4:
                            System.out.println("Food to delete from list:");
                            String deleteFood = sc.nextLine();
                            Food deletf = new Food(deleteFood);
                            boolean successDelete = dbAccess.deleteFoodForAUser(deletf,id);
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
            } else {
                System.out.println("User is ADMIN");
                while (true) {
                    int x = 0;
                    while (x != -1) {
                        x = DemoCRUDUsers.menuUsers();
                        switch (x) {
                            case 1 :
                                System.out.println("Username: ");
                                usernamekb = sc.nextLine();
                                System.out.println("Password: ");
                                passwordkb = sc.nextLine();
                                u.setUsername(usernamekb);
                                u.setPassword(passwordkb);
                                boolean isAdded = dbAccess.insert(u);
                                if (isAdded)
                                    System.out.println(usernamekb + " user added successfully!");
                                else
                                    System.out.println("ERROR");
                                break;
                            case 2:
                                List<User> result = dbAccess.readAllUsers();
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
                                boolean newPass = dbAccess.update(u);
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
                                boolean deleteUser = dbAccess.delete(u);
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
                                            boolean success = dbAccess.insertFoodForAUser(f, id);
                                            if (success)
                                                System.out.println("Food logged successfully");
                                            else
                                                System.out.println("ERROR");
                                            break;
                                        case 2:
                                            System.out.println(usernamekb+" food list is :");
                                            List <Food> foodList = dbAccess.readFoodOfAUser(id);
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
                                            boolean successUpdate = dbAccess.updateFoodOfAUser(oldf,newf,id);
                                            if (successUpdate)
                                                System.out.println("Food updated successfully");
                                            else
                                                System.out.println("ERROR");
                                            break;
                                        case 4:
                                            System.out.println("Food to delete from list:");
                                            String deleteFood = sc.nextLine();
                                            Food deletf = new Food(deleteFood);
                                            boolean successDelete = dbAccess.deleteFoodForAUser(deletf,id);
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
                break;
            }
        }
    }
}
