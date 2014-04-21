/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.ArrayList;
import java.util.Date;
import model.Departamento;
import model.Empleado;
import model.EmpleadoDepartamento;
import model.Quincena;
import model.Rubro;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Administrador
 */
public class NominaRolDaoImpl implements NominaRolDao{

    @Override
    public ArrayList<Quincena> getQuincenasPorEmpleadoYFecha(Empleado e, Date fecha){
        ArrayList<Quincena> ret = null;
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="from Quincena where cedula = '"+e.getCedula()+"' and fecha_pago= '"+fecha.toString()+"'";
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql); 
            ret=(ArrayList<Quincena>)q.list();
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
        return ret;
    }
    
    @Override
    public ArrayList<Rubro> getAllRubros(){
        ArrayList<Rubro> ret = null;
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="from Rubro";
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql); 
            ret=(ArrayList<Rubro>)q.list();
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
        
        return ret;
    }

    @Override
    public ArrayList<Empleado> getEmpleadosByDepartamento(int departamento) {
        ArrayList<Empleado> ret = null;
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="select ed.empleado from EmpleadoDepartamento as ed where ed.departamento="+departamento;
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql); 
            ret=(ArrayList<Empleado>)q.list();
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
            
        
        return ret;
    }
    
    public ArrayList<Empleado> getEmpleadosByFuncion(String funcion){
        ArrayList<Empleado> ret = null;
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="select fun.empleado from Funcion as fun where fun.tipo='"+funcion+"'";
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql); 
            ret=(ArrayList<Empleado>)q.list();
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
            
        
        return ret;
    }
    
    @Override
    public ArrayList<EmpleadoDepartamento> getEmpleadoDepartamentoByFuncion(String funcion){
        ArrayList<EmpleadoDepartamento> ret = null;
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="from EmpleadoDepartamento as ed where ed.fechaFinal is null and ed.empleado in (select fun.empleado from Funcion as fun where fun.tipo=:funcion)";
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql);
            q.setParameter("funcion", funcion);
            ret=(ArrayList<EmpleadoDepartamento>)q.list();
            for(EmpleadoDepartamento ed : ret){
                Hibernate.initialize(ed.getDepartamento());
                Hibernate.initialize(ed.getEmpleado());
            }
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
            
        
        return ret;
    }
    
    public String getDepartamentoActualDeEmpleado(String cedula){
        String ret="";
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="select ed.departamento from EmpleadoDepartamento as ed where ed.fechaFinal is null and cedula='"+cedula+"'";
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql); 
            ret=((Departamento)q.list().get(0)).getNombreDepartamento();
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
        
        return ret;
    }
    
    @Override
    public Quincena updateQuincena(Quincena q){
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            tx= session.beginTransaction();
            session.saveOrUpdate(q);
            tx.commit();   
            return q;
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
            return null;
        }
    }

    @Override
    public boolean guardarNuevoRubro(Rubro r) {
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            tx= session.beginTransaction();
            session.save(r);
            tx.commit();   
            return true;
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
            return false;
        }
    }

    @Override
    public ArrayList<EmpleadoDepartamento> getEmpleadoDepartamentoByFuncionxCedula(String funcion, String cedula) {
       
        ArrayList<EmpleadoDepartamento> ret = null;
        
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        String sql="from EmpleadoDepartamento as ed where ed.id.cedula=:cedula and ed.fechaFinal is null and ed.empleado in (select fun.empleado from Funcion as fun where fun.tipo=:funcion)";
        
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql);
            q.setParameter("funcion", funcion);
            q.setParameter("cedula", cedula);
            ret=(ArrayList<EmpleadoDepartamento>)q.list();
            for(EmpleadoDepartamento ed : ret){
                Hibernate.initialize(ed.getDepartamento());
                Hibernate.initialize(ed.getEmpleado());
            }
            tx.commit();   
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            if(tx != null)
                tx.rollback();            
        }
            
        
        return ret;
        
        
    }
    
}
