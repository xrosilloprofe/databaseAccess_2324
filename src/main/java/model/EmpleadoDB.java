package model;

import connection.MyDataSource;

import javax.sql.DataSource;
import java.sql.*;
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

//    @Override
//    public Empleado getEmpleado(String dni) {
//        DataSource dataSource= MyDataSource.getMySQLDataSource();
//        Empleado empleado = null;
//
//        try(Connection connection = dataSource.getConnection();
//        Statement statement= connection.createStatement();
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM EMPLEADO WHERE DNI = '"+dni+"'")){
//            resultSet.next();
//            empleado = new Empleado(
//                    resultSet.getInt(1),
//                    resultSet.getString(2),
//                    resultSet.getString(3),
//                    resultSet.getString(4),
//                    resultSet.getString(5),
//                    resultSet.getString(6),
//                    resultSet.getDate(7),
//                    resultSet.getString(8),
//                    resultSet.getString(11));
//
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return empleado;
//    }

//    @Override
//    public boolean authenticate(String login, String passwd) {
//        boolean autenticado = false;
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        try(Connection connection= dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(" SELECT COUNT(*) " +
//                "FROM EMPLEADO WHERE DNI = '"+login+"' AND PASSWORD = '"+passwd+"'")){
//            resultSet.next();
//            if(resultSet.getInt(1) > 0)
//                autenticado = true;
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return autenticado;
//    }

    @Override
    public Empleado getEmpleado(String dni) {
        DataSource dataSource= MyDataSource.getMySQLDataSource();
        Empleado empleado = null;
        String query = "SELECT * FROM EMPLEADO WHERE DNI = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps= connection.prepareStatement(query)
            ){
            ps.setString(1,dni);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            empleado = new Empleado(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getDate(7),
                    resultSet.getString(8),
                    resultSet.getString(11));

        } catch (SQLException e){
            e.printStackTrace();
        }

        return empleado;
    }

//    @Override
//    public boolean authenticate(String login, String passwd) {
//        boolean autenticado = false;
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        String query = "SELECT COUNT(*) FROM EMPLEADO WHERE " +
//                "DNI = ? AND PASSWORD = ?";
//        try(Connection connection= dataSource.getConnection();
//            PreparedStatement ps = connection.prepareStatement(query)
//        ){
//            ps.setString(1,login);
//            ps.setString(2,passwd);
//            ResultSet resultSet = ps.executeQuery();
//            resultSet.next();
//            if(resultSet.getInt(1)>0)
//                autenticado=true;
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return autenticado;
//    }

    @Override
    public boolean authenticate(String login, String passwd) {
        boolean autenticado = false;
        DataSource dataSource = MyDataSource.getMySQLDataSource();
        String query = "{ ? = call autentificar(?,?) }";

        try(Connection connection= dataSource.getConnection();
            CallableStatement cs = connection.prepareCall(query);
        ){
            cs.setString(2,login);
            cs.setString(3,passwd);
            ResultSet resultSet = cs.executeQuery();
            resultSet.next();
            if(resultSet.getInt(1)>0)
                autenticado=true;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return autenticado;
    }

//    @Override
//    public List<Empleado> getEmpleadosPorCargo(String cargo) {
//        List<Empleado> empleados = new ArrayList<>();
//        DataSource dataSource = MyDataSource.getMySQLDataSource();
//        String query = "SELECT * FROM EMPLEADO WHERE CARGO = ?";
//        try(Connection connection= dataSource.getConnection();
//        PreparedStatement ps = connection.prepareStatement(query)){
//            ps.setString(1,cargo);
//             ResultSet resultSet = ps.executeQuery();
//
//                Empleado empleado;
//                while(resultSet.next()){
//                    empleado = new Empleado(
//                            resultSet.getInt("idEmpleado"),
//                            resultSet.getString("DNI"),
//                            resultSet.getString("nombre"),
//                            resultSet.getString("apellidos"),
//                            resultSet.getString("CP"),
//                            resultSet.getString("email"),
//                            resultSet.getDate("fechaNac"),
//                            resultSet.getString("cargo"),
//                            resultSet.getString("domicilio"));
//                    empleados.add(empleado);
//                }
//
//            } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return empleados;
//    }
@Override
public List<Empleado> getEmpleadosPorCargo(String cargo) {
    List<Empleado> empleados = new ArrayList<>();
    DataSource dataSource = MyDataSource.getMySQLDataSource();
    String query = "call empleadoPorCargo(?)";
    try(Connection connection= dataSource.getConnection();
        CallableStatement cs = connection.prepareCall(query)){
        cs.setString(1,cargo);
        ResultSet resultSet = cs.executeQuery();
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

    } catch (SQLException e){
        e.printStackTrace();
    }
    return empleados;
}

    @Override
    public int addEmpleadoProcedure(Empleado empleado) {
        DataSource ds = MyDataSource.getMySQLDataSource();
        String query = "{call addEmpleado(?,?,?,?,?,?,?,?,?)}";
        int resultado=0;

        try(Connection connection = ds.getConnection();
        CallableStatement cs = connection.prepareCall(query)){
            cs.setString(1, empleado.getDNI());
            cs.setString(2, empleado.getNombre());
            cs.setString(3, empleado.getApellidos());
            cs.setString(4, empleado.getCP());
            cs.setString(5, empleado.getEmail());
            cs.setDate(6,empleado.getFechaNac());
            cs.setString(7, empleado.getCargo());
            cs.setString(8, empleado.getDomicilio());
            cs.registerOutParameter(9,Types.INTEGER); //Recomendable
            cs.executeUpdate();
            resultado = cs.getInt(9);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return resultado;
    }
}
