/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import dao.NominaRolDao;
import dao.NominaRolDaoImpl;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Jorge Charmander
 */
public class GridQuincena {
    
    private EmpleadoDepartamento empleado_departamento;
    private ArrayList<Quincena> quincena_detalle;

    public GridQuincena() {
    }

    public GridQuincena(EmpleadoDepartamento de, Date fecha) {
        NominaRolDao nominaRolDao=new NominaRolDaoImpl();
        
        this.empleado_departamento = de;
        quincena_detalle = nominaRolDao.getQuincenasPorEmpleadoYFecha(de.getEmpleado(), fecha);
    }

    public EmpleadoDepartamento getEmpleado_departamento() {
        return empleado_departamento;
    }

    public void setEmpleado_departamento(EmpleadoDepartamento empleado_departamento) {
        this.empleado_departamento = empleado_departamento;
    }
    
    public ArrayList<Quincena> getQuincena_detalle() {
        return quincena_detalle;
    }

    public void setQuincena_detalle(ArrayList<Quincena> quincena_detalle) {
        this.quincena_detalle = quincena_detalle;
    }
    
    public Quincena getQuincena(int rubro_id){
        
        Quincena ret = null;
        
        for(Quincena q: quincena_detalle){
            /*if(q.getRubro().getNombre().equalsIgnoreCase(rubro_nombre))
                ret = q ;
                    */
            if(q.getId().getIdRubro() == rubro_id)
                ret = q ;
        }
        
        return ret;
    }
    
    public void agregarQuincena(Quincena q){
        this.quincena_detalle.add(q);
    }
    public String getDepartamentoActual(){
        String ret="";
        
        ret = this.empleado_departamento.getDepartamento().getNombreDepartamento();
        return ret;
    }
    
    public String getNombres(){
        return this.empleado_departamento.getEmpleado().getNombres();
    }
    
    public String getApellidos(){
        return this.empleado_departamento.getEmpleado().getApellidos();
    }
    public String getCedula(){
        return this.empleado_departamento.getEmpleado().getCedula();
    }
    
    
}
