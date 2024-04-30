import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarDriverManager {

    public static Connection conexionMysql(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proyecto?user=xrosillo&password=1111");
            if(connection != null)
                System.out.println("Conexión exitosa");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
