public class Food {

    private int id;
    private String foodname;

    public Food(String foodname) {
        this.foodname = foodname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodname='" + foodname + '\'' +
                '}';
    }
}
