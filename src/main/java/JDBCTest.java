import java.sql.*;

public class JDBCTest {

    static final String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/men?useSSL=false";

    public static void main (String[] args) {
        try {
            Connection connection = null;
            Statement statement = null;

            System.out.println("Registering JDBC Driver...");
            Object driver = Class.forName(JDBC_Driver);


            connection = DriverManager.getConnection(DATABASE_URL, "root", "1234");

            System.out.println("Executing statement...");
            statement = connection.createStatement();

            String sql = "SELECT * FROM STAFF";

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Retrieving data from database...");

            System.out.println("\nSTAFF: ");

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String secondname = resultSet.getString("secondname");
                String surname = resultSet.getString("surname");
                int salary = resultSet.getInt("salary");


                System.out.println("---------------------------------------");

                System.out.println("ID:  " + id);
                System.out.println("Surname: " + surname);
                System.out.println("Name: " + name);
                System.out.println("Secondname: " + secondname);
                System.out.println("Salary: $" + salary);

            }

            System.out.println("Closing connection and releasing resources...");

            resultSet.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}
