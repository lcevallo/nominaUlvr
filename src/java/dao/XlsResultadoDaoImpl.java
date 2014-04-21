/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Departamento;
import model.Empleado;
import model.VistaResultado;
import model.Xlsresultado;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Administrador
 */
public class XlsResultadoDaoImpl implements XlsResultadoDao{

    @Override
    public List<VistaResultado> getXlsResultado(Integer dia, Integer mes, Integer anio,String funcion) {
        List<VistaResultado> listado=null;
        Transaction tx=null;
        //Session session = HibernateUtil.getSessionFactory().openSession(); 
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String query="Select id,fechapago,cedula,nombres,apellidos,tipo,num_cuenta,valor_total,concepto from mostrar_xls(?,?,?,?) order by apellidos,nombres";
        try {
            tx= session.beginTransaction();
            
             try {
                 CallableStatement cs = session.connection().prepareCall(query);
                 cs.setInt(1, dia);
                 cs.setInt(2, mes);
                 cs.setInt(3, anio);
                 cs.setString(4, funcion);
                 ResultSet rsfinal = cs.executeQuery();
                 listado=new ArrayList<VistaResultado>();
                 while(rsfinal.next()) {
                                                listado.add(new VistaResultado(rsfinal.getLong(1), rsfinal.getDate(2), rsfinal.getString(3),rsfinal.getString(4),rsfinal.getString(5), rsfinal.getString(6), rsfinal.getString(7), rsfinal.getBigDecimal(8),rsfinal.getString(9)));
                                        }
                 
             } catch (SQLException ex) {
                 Logger.getLogger(XlsResultadoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            /*
            Query q=session.createSQLQuery(sql).addEntity(VistaResultado.class);
            q.setParameter("dia", dia);
            q.setParameter("mes", mes);
            q.setParameter("anio", anio);
            */
            tx.commit();
            
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();            
        }
        
        return listado;
    }

    @Override
    public void GuardarValor(String cedula, Date fechaPago, BigDecimal monto,String concepto) {
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaPago);
            int dia;
            int mes;
            int anio;
            dia = cal.get(Calendar.DAY_OF_MONTH);
            mes = cal.get(Calendar.MONTH)+1;
            anio = cal.get(Calendar.YEAR);
            
             Transaction tx=null;
        //Session session = HibernateUtil.getSessionFactory().openSession(); 
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            String query="Select * from ingresar_valores_resultado(?,?,?,?,?,?)";
            
            try {
            tx= session.beginTransaction();            
                        try {
                            CallableStatement cs = session.connection().prepareCall(query);

                            cs.setString(1, cedula);
                            cs.setInt(2, dia);
                            cs.setInt(3, mes);
                            cs.setInt(4, anio);
                            cs.setBigDecimal(5, monto);
                            cs.setString(6, concepto);
                            cs.executeQuery();
                            tx.commit();
                        } catch (SQLException ex) {
                             System.out.println(ex.getMessage());
                             ex.printStackTrace();
                             tx.rollback();   
                        }
            } catch (HibernateException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        tx.rollback();            
                    }
        
        
    }

    @Override
    public List<Departamento> getDepartamentos() {
       //
        List<Departamento> listado=null;
        Transaction tx=null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        String sql="FROM Departamento order by Tipo,nombreDepartamento";
        try {
            tx= session.beginTransaction();
            Query q=session.createQuery(sql);           
            listado=q.list();
            tx.commit();
            
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();            
        }
        
        return listado;
    }

    @Override
    public void GuardarEmpleado(Empleado empleado, Integer idDepartamento, String funcion) throws HibernateException{
         Transaction tx=null;
        //Session session = HibernateUtil.getSessionFactory().openSession(); 
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            String query="Select * from agregar_empleado_widget(?,?,?,?,?,?,?,?)";
            
            try {
            tx= session.beginTransaction();            
                        try {
                            CallableStatement cs = session.connection().prepareCall(query);

                            cs.setString(1, empleado.getCedula());
                            cs.setString(2, empleado.getNombres());
                            cs.setString(3, empleado.getApellidos());
                            cs.setString(4, empleado.getNumCuenta());
                            cs.setString(5,empleado.getTipoCuenta());
                            cs.setString(6,empleado.getEmail());
                            cs.setInt(7,idDepartamento);
                            cs.setString(8,funcion);
                            
                            
                            
                            cs.executeQuery();
                            tx.commit();
                        } catch (SQLException ex) {
                             System.out.println(ex.getMessage());
                             ex.printStackTrace();
                             tx.rollback();   
                        }
            } catch (HibernateException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        tx.rollback();            
                    }
        
        
        
    }

   
    
}
