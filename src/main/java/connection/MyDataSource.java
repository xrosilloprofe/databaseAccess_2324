package connection;

import com.mysql.cj.jdbc.MysqlDataSource;
import oracle.jdbc.pool.OracleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class MyDataSource {

    public static DataSource getMySQLDataSource() {
        Properties properties=new Properties();
        MysqlDataSource ds = null;

        try(FileInputStream fis=new FileInputStream("db.properties")){
            properties.load(fis);

            ds = new MysqlDataSource();
            ds.setURL(properties.getProperty("MYSQL_DB_URL"));
            ds.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            ds.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

        } catch (Exception e){
            e.printStackTrace();
        }
        return ds;
    }

    public static Connection conectarMySQL(){
        Connection con = null;
        try{
            DataSource ds = getMySQLDataSource();
            con = ds.getConnection();
            if(con != null)
                System.out.println("Conexión establecida.");
        } catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

    public static DataSource getMyOracleDataSource() {
        Properties properties=new Properties();
        OracleDataSource ds = null;

        try(FileInputStream fis=new FileInputStream("db.properties")){
            properties.load(fis);

            ds = new OracleDataSource();
            ds.setURL(properties.getProperty("ORACLE_DB_URL"));
            ds.setUser(properties.getProperty("ORACLE_DB_USERNAME"));
            ds.setPassword(properties.getProperty("ORACLE_DB_PASSWORD"));

        } catch (Exception e){
            e.printStackTrace();
        }
        return ds;
    }

    public static Connection conectarOracle(){
        Connection con = null;
        try{
            DataSource ds = getMyOracleDataSource();
            con = ds.getConnection();
            if(con != null)
                System.out.println("Conexión establecida.");
        } catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

}
