import model.AlmacenDatosDB;
import model.Empleado;
import model.EmpleadoDB;
import connection.MyDataSource;

import java.sql.Date;

public class TestMySQL {
    public static void main(String[] args) {
//        ConectarDriverManager.conexionMysql();
        MyDataSource.conectarMySQL();
        AlmacenDatosDB almacenEmpleados = new EmpleadoDB();
//        System.out.println(almacenEmpleados.getEmpleados());
//        Empleado empleado= almacenEmpleados.getEmpleados().get(0);
//        empleado.setCargo("superjefe");
//        System.out.println(almacenEmpleados.updateEmpleado(empleado));
//        System.out.println(almacenEmpleados.getEmpleados());
//        System.out.println(almacenEmpleados.deleteEmpleado("4X"));
        Empleado empleado1 = new Empleado(1,"88X","Jesús","Aray Jesús","46888","empleados@empleado.es",new Date(104,4,29),"jefes","su casa,55");
        System.out.println(almacenEmpleados.addEmpleado(empleado1));
        System.out.println(almacenEmpleados.authenticate("8X","1111"));
        System.out.println(almacenEmpleados.authenticate("8X","1' OR PASSWORD != '1"));
        System.out.println(almacenEmpleados.getEmpleadosPorCargo("jefes"));
//        System.out.println(almacenEmpleados.getEmpleados());

    }
}
