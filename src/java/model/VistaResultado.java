/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author lcevallosc
 */


public class VistaResultado implements Serializable {
    
    private long id;
    private Date fechapago;
    private String cedula;    
    private String nombres;
    private String apellidos;
    private String tipo;
    private String numCuenta;
    private BigDecimal valorTotal;
    private String concepto;

    public VistaResultado(){
    
    }
    
    public VistaResultado(long id, Date fechapago, String cedula, String nombres, String apellidos, String tipo, String numCuenta, BigDecimal valorTotal,String concepto) {
        this.id = id;
        this.fechapago = fechapago;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipo = tipo;
        this.numCuenta = numCuenta;
        this.valorTotal = valorTotal;
        this.concepto=concepto;
    }
    
 
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

  
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
   
    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }
    

   
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
   
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

  
    public String getNumCuenta() {
        return this.numCuenta;
    }
    
    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }
   
   
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

  
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    
}
