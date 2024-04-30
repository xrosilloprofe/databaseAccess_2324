package model;

import java.util.List;

public interface AlmacenDatosDB {
    List<Empleado> getEmpleados();
    boolean updateEmpleado(Empleado empleado);
}
