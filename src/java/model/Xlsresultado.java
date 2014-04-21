package model;
// Generated 06/03/2014 04:49:02 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Xlsresultado generated by hbm2java
 */
@Entity
@Table(name="xlsresultado"
    ,schema="public"
)
public class Xlsresultado  implements java.io.Serializable {


     private XlsresultadoId id;
     private Empleado empleado;
     private short idDepartamento;
     private BigDecimal valorTotal;

    public Xlsresultado() {
    }

    public Xlsresultado(XlsresultadoId id, Empleado empleado, short idDepartamento, BigDecimal valorTotal) {
       this.id = id;
       this.empleado = empleado;
       this.idDepartamento = idDepartamento;
       this.valorTotal = valorTotal;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="cedula", column=@Column(name="cedula", nullable=false, length=15) ), 
        @AttributeOverride(name="fechapago", column=@Column(name="fechapago", nullable=false, length=13) ) } )
    public XlsresultadoId getId() {
        return this.id;
    }
    
    public void setId(XlsresultadoId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cedula", nullable=false, insertable=false, updatable=false)
    public Empleado getEmpleado() {
        return this.empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    
    @Column(name="id_departamento", nullable=false)
    public short getIdDepartamento() {
        return this.idDepartamento;
    }
    
    public void setIdDepartamento(short idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    
    @Column(name="valor_total", nullable=false, precision=8)
    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }




}

