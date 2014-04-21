/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import model.Departamento;
import model.Empleado;
import model.VistaResultado;
import model.Xlsresultado;

/**
 *
 * @author Administrador
 */
public interface XlsResultadoDao {
    public List<VistaResultado> getXlsResultado(Integer dia,Integer mes,Integer anio,String funcion);
    public void GuardarValor(String cedula,Date fechaPago,BigDecimal monto,String concepto);
    public List<Departamento> getDepartamentos();
    public void GuardarEmpleado(Empleado empleado,Integer idDepartamento,String funcion);
    
}
