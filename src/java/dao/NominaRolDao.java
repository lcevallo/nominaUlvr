/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.ArrayList;
import java.util.Date;
import model.Empleado;
import model.EmpleadoDepartamento;
import model.Quincena;
import model.Rubro;

/**
 *
 * @author Administrador
 */
public interface NominaRolDao {
    /**
     * funcion para retornar todos los rubros de una quincena dado un empleado y la quincena a consultar
     * @param e empleado a consultar
     * @param fecha fecha de la quincena
     * @return un arreglo de Quincena, que son todos los rubros asociados
     */
    public ArrayList<Quincena> getQuincenasPorEmpleadoYFecha(Empleado e, Date fecha);
    
    /**
     * funcion para obtener todos los rubros
     * @return 
     */
    public ArrayList<Rubro> getAllRubros();
    
    /**
     * obtener todos los empleados dado un departamento
     * @param departamento
     * @return 
     */
    
    /**
     * 
     * @param departamento
     * @return 
     */
    public ArrayList<Empleado> getEmpleadosByDepartamento(int departamento);
    
    /**
     * 
     * @param cedula
     * @return 
     */
    public String getDepartamentoActualDeEmpleado(String cedula);
    
    /**
     * 
     * @param funcion
     * @return 
     */
    public ArrayList<Empleado> getEmpleadosByFuncion(String funcion);
    
    /**
     * 
     * @param funcion
     * @return 
     */
    public ArrayList<EmpleadoDepartamento> getEmpleadoDepartamentoByFuncion(String funcion);
    
    
    /**
     * 
     * @param funcion
     * @param cedula
     * @return Devuelve un arrayList de los pagos por funcion pero con filtro de cedula
     */
    
    public ArrayList<EmpleadoDepartamento> getEmpleadoDepartamentoByFuncionxCedula(String funcion,String cedula);
    
    /**
     * 
     * @param q
     * @return 
     */
    public Quincena updateQuincena(Quincena q);
    
    public boolean guardarNuevoRubro(Rubro r);
    
}
