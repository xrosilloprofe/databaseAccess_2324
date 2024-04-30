import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDB implements AlmacenDatosDB{
    @Override
    public List<Empleado> getEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        DataSource dataSource = MyDataSource.getMySQLDataSource();

        try(Connection connection=dataSource.getConnection();
            Statement statement= connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM EMPLEADO")){

            Empleado empleado;
            while(resultSet.next()){
                empleado = new Empleado(
                        resultSet.getInt("idEmpleado"),
                        resultSet.getString("DNI"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("CP"),
                        resultSet.getString("email"),
                        resultSet.getDate("fechaNac"),
                        resultSet.getString("cargo"),
                        resultSet.getString("domicilio"));

                empleados.add(empleado);
            }


        } catch (Exception e){
            e.printStackTrace();
        }

        return empleados;
    }
}
