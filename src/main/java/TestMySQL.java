import model.AlmacenDatosDB;
import model.Empleado;
import model.EmpleadoDB;
import connection.MyDataSource;

public class TestMySQL {
    public static void main(String[] args) {
//        ConectarDriverManager.conexionMysql();
        MyDataSource.conectarMySQL();
        AlmacenDatosDB empleados = new EmpleadoDB();
        System.out.println(empleados.getEmpleados());
        Empleado empleado= empleados.getEmpleados().get(0);
        empleado.setCargo("superjefe");
        System.out.println(empleados.updateEmpleado(empleado));
        System.out.println(empleados.getEmpleados());
    }
}
