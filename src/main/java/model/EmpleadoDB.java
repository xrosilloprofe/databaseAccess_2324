package model;

import connection.MyDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDB implements AlmacenDatosDB {
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

    @Override
    public int updateEmpleado(Empleado empleado) {
        int resultado=0;
        DataSource dataSource = MyDataSource.getMySQLDataSource();

        try(Connection connection= dataSource.getConnection();
        Statement statement= connection.createStatement()) {

            String query = "UPDATE EMPLEADO SET " +
                    "nombre='"+empleado.getNombre()+"',"+
                    "apellidos='"+empleado.getApellidos()+"',"+
                    "domicilio='"+empleado.getDomicilio()+"',"+
                    "CP='"+empleado.getCP()+"',"+
                    "email='"+empleado.getEmail()+"',"+
                    "fechaNac='"+empleado.getFechaNac()+"',"+
                    "cargo='"+empleado.getCargo()+"'"+
                    "WHERE DNI = '"+ empleado.getDNI() +"'";

            resultado = statement.executeUpdate(query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public int deleteEmpleado(String dni) {
        int resultado = 0;
        DataSource dataSource = MyDataSource.getMySQLDataSource();

        try(Connection connection= dataSource.getConnection();
            Statement statement= connection.createStatement()){

            String query = "DELETE FROM EMPLEADO WHERE DNI = '"+dni+"'";
            resultado = statement.executeUpdate(query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public Empleado addEmpleado(Empleado empleado) {
        DataSource dataSource=MyDataSource.getMySQLDataSource();
        try(Connection connection= dataSource.getConnection();
        Statement statement= connection.createStatement()){
            String query = "insert into EMPLEADO (DNI,nombre,apellidos,CP,email,fechaNac,cargo,domicilio) values ("+
                    "'"+empleado.getDNI()+"',"+
                    "'"+empleado.getNombre()+"',"+
                    "'"+empleado.getApellidos()+"',"+
                    "'"+empleado.getCP()+"',"+
                    "'"+empleado.getEmail()+"',"+
                    "'"+empleado.getFechaNac()+"',"+
                    "'"+empleado.getCargo()+"',"+
                    "'"+empleado.getDomicilio()+"')";
            int updatedRows = statement.executeUpdate(query);
            if(updatedRows > 0){
                empleado=getEmpleado(empleado.getDNI());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }

    @Override
    public Empleado getEmpleado(String dni) {
        DataSource dataSource= MyDataSource.getMySQLDataSource();
        Empleado empleado = null;

        try(Connection connection = dataSource.getConnection();
        Statement statement= connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLEADO WHERE DNI = '"+dni+"'")){
            resultSet.next();
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

        } catch (SQLException e){
            e.printStackTrace();
        }

        return empleado;
    }

    @Override
    public boolean authenticate(String login, String passwd) {
        return false;
    }
}
