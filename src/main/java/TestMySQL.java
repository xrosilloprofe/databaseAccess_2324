public class TestMySQL {
    public static void main(String[] args) {
//        ConectarDriverManager.conexionMysql();
        MyDataSource.conectarMySQL();
        AlmacenDatosDB empleados = new EmpleadoDB();
        System.out.println(empleados.getEmpleados());
    }
}
