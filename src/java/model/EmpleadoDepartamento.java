package model;
// Generated 06/03/2014 04:49:02 PM by Hibernate Tools 3.6.0


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EmpleadoDepartamento generated by hbm2java
 */
@Entity
@Table(name="empleado_departamento"
    ,schema="public"
)
public class EmpleadoDepartamento  implements java.io.Serializable {


     private EmpleadoDepartamentoId id;
     private Departamento departamento;
     private Empleado empleado;
     private Date fechaFinal;
     private Set quincenas = new HashSet(0);

    public EmpleadoDepartamento() {
    }

	
    public EmpleadoDepartamento(EmpleadoDepartamentoId id, Departamento departamento, Empleado empleado) {
        this.id = id;
        this.departamento = departamento;
        this.empleado = empleado;
    }
    public EmpleadoDepartamento(EmpleadoDepartamentoId id, Departamento departamento, Empleado empleado, Date fechaFinal, Set quincenas) {
       this.id = id;
       this.departamento = departamento;
       this.empleado = empleado;
       this.fechaFinal = fechaFinal;
       this.quincenas = quincenas;
    }
   
     @EmbeddedId

    
    @AttributeOverrides( {
        @AttributeOverride(name="cedula", column=@Column(name="cedula", nullable=false, length=15) ), 
        @AttributeOverride(name="idDepartamento", column=@Column(name="id_departamento", nullable=false) ), 
        @AttributeOverride(name="estado", column=@Column(name="estado", nullable=false) ) } )
    public EmpleadoDepartamentoId getId() {
        return this.id;
    }
    
    public void setId(EmpleadoDepartamentoId id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_departamento", nullable=false, insertable=false, updatable=false)
    public Departamento getDepartamento() {
        return this.departamento;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cedula", nullable=false, insertable=false, updatable=false)
    public Empleado getEmpleado() {
        return this.empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_final", length=13)
    public Date getFechaFinal() {
        return this.fechaFinal;
    }
    
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="empleadoDepartamento")
    public Set getQuincenas() {
        return this.quincenas;
    }
    
    public void setQuincenas(Set quincenas) {
        this.quincenas = quincenas;
    }




}


