/*
- continue working with your subject area;
- create 1-2 tables in DBMS Postgres with the same structure as your entity classes;
- arrange simple database connection and do some actions (read, write, delete, update data set).
*/
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Assignment3 {
    public static class DatabaseConnection { //working with database
        public static Connection getConnection() { //connection to database
            try {
                String url = "jdbc:postgresql://localhost:5432/ZOO";
                String user = "postgres";
                String password = "michigun228"; //
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        public static void addAnimalToDB(Animal animal) {
            String query = "INSERT INTO animals (name, kind, diet, age) VALUES (?, ?, ?, ?)";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, animal.name);
                stmt.setString(2, animal.kind);
                stmt.setString(3, animal.diet);
                stmt.setInt(4, animal.age);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static ArrayList<Animal> getAllAnimalsFromDB() {
            ArrayList<Animal> animals = new ArrayList<>();
            String query = "SELECT * FROM animals";
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String kind = rs.getString("kind");
                    String diet = rs.getString("diet");
                    int age = rs.getInt("age");
                    Animal animal = new Animal(diet, age, kind, name) {};
                    animals.add(animal);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return animals;
        }
        public static void updateAnimalInDB(int id, String newName, String newKind, String newDiet, int newAge) {
            String query = "UPDATE animals SET name = ?, kind = ?, diet = ?, age = ? WHERE id = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newName);
                stmt.setString(2, newKind);
                stmt.setString(3, newDiet);
                stmt.setInt(4, newAge);
                stmt.setInt(5, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Метод для удаления животного из базы данных
        public static void deleteAnimalFromDB(int id) {
            String query = "DELETE FROM animals WHERE id = ?";
            try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static abstract class Animal {
        private String diet;
        private int age;
        private String kind;
        private String name;

        public Animal(String diet, int age, String kind, String name) {
            this.diet = diet;
            this.age = age;
            this.kind = kind;
            this.name = name;
        }

        @Override
        public String toString() {
            return "This Animal's name is " + name + " and that is a " + kind + ". It is " + age + " years old and loves " + diet + "\n";
        }
    }
    public static class Cat extends Animal {
        public Cat(String diet, int age, String kind, String name) {
            super(diet, age, kind, name);
        }
    }
    public static class Bird extends Animal {
        public Bird(String diet, int age, String kind, String name) {
            super(diet, age, kind, name);
        }
    }
    public static class Fish extends Animal {
        public Fish(String diet, int age, String kind, String name) {
            super(diet, age, kind, name);
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Cat cat1 = new Cat("fish", 3, "Cat", "Barsik-chmarsik");
        Cat cat2 = new Cat("meat", 2, "Cat", "Temka");
        Bird bird1 = new Bird("insects", 2, "Bird", "Karlushka");
        Fish fish1 = new Fish("sand and garbage", 1, "Fish", "Yasik");
        DatabaseConnection.addAnimalToDB(cat1);
        DatabaseConnection.addAnimalToDB(cat2);
        DatabaseConnection.addAnimalToDB(bird1);
        DatabaseConnection.addAnimalToDB(fish1);

        ArrayList<Animal> animalsFromDB = DatabaseConnection.getAllAnimalsFromDB(); //getting all animals from the ZOO
        System.out.println("Animals from database:");
        for (Animal animal : animalsFromDB) {
            System.out.println(animal.toString());
        }

        DatabaseConnection.updateAnimalInDB(1, "Updated Barsik", "Cat", "fish and meat", 4); //updating the information

        DatabaseConnection.deleteAnimalFromDB(2); //deleting the animal
    }
}