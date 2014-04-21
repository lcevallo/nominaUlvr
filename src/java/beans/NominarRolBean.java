/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.NominaRolDao;
import dao.NominaRolDaoImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.EmpleadoDepartamento;
import model.GridQuincena;
import model.Quincena;
import model.QuincenaId;
import model.Rubro;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.data.FilterEvent;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class NominarRolBean implements Serializable{

    /**
     * Creates a new instance of NominarRolBean
     */
    private ArrayList<GridQuincena> grid;
    private ArrayList<String> columnas;
    private Map<String, Integer> columns;
    private Date fechaPago;
    private String funcion;
    private BigDecimal nuevoValorRubro;
    private Rubro nuevoRubro;
    private String fechaFormateada;
    private String cedulaBusqueda;
    

    /**
     * Creates a new instance of PruebaBean
     */
    public NominarRolBean() {
        
        
        //inicializar las variables
        columnas = new ArrayList<>();
        columns = new HashMap<>();
        nuevoRubro = new Rubro(0, "", "");
        
        NominaRolDao nominaRolDao= new NominaRolDaoImpl();
        ArrayList<Rubro> ret = nominaRolDao.getAllRubros();
        for(Rubro r: ret){
            columnas.add(r.getNombre());
            columns.put(r.getNombre(), r.getIdRubro());
        }
        
    }

    public Rubro getNuevoRubro() {
        return nuevoRubro;
    }

    public BigDecimal getNuevoValorRubro() {
        return nuevoValorRubro;
    }

    public void setNuevoValorRubro(BigDecimal nuevoValorRubro) {
        this.nuevoValorRubro = nuevoValorRubro;
    }

    public String getFuncion() {
        return funcion;
    }

    public String getFechaFormateada() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(fechaPago);
        return fechaFormateada;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    

    public Date getFechaPago() {
        return fechaPago;
    }

    public String getCedulaBusqueda() {
        return cedulaBusqueda;
    }

    public void setCedulaBusqueda(String cedulaBusqueda) {
        this.cedulaBusqueda = cedulaBusqueda;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Map<String, Integer> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Integer> columns) {
        this.columns = columns;
    }

    public ArrayList<GridQuincena> getGrid() {
        return grid;
    }

    public void setGrid(ArrayList<GridQuincena> grid) {
        this.grid = grid;
    }

    public ArrayList<String> getColumnas() {
        return columnas;
    }

    public void setColumnas(ArrayList<String> columnas) {
        this.columnas = columnas;
    }
    
    public void buscarPagosEmpleado()
    {
        boolean buscar=true;
        Integer dia;
        Integer mes;
        Integer anio;
        
        
        if(this.fechaPago==null)
        {
           buscar=false;
        }
        if(buscar)
        {
            grid = new ArrayList<>();
            
            NominaRolDao nominaRolDao=new NominaRolDaoImpl();
            
            /*
            ArrayList<Empleado> empleadosPorDepartamento = xlsResultadoDao.getEmpleadosByFuncion(funcion);
            for(Empleado emp : empleadosPorDepartamento){
                grid.add(new GridQuincena(emp, fechaPago));
            }
            */
            
            if(this.cedulaBusqueda.length()>0)
            {
                ArrayList<EmpleadoDepartamento> empleados_departamentos = nominaRolDao.getEmpleadoDepartamentoByFuncionxCedula(funcion,this.cedulaBusqueda);
                for(EmpleadoDepartamento ed : empleados_departamentos){
                        grid.add(new GridQuincena(ed, fechaPago));
                    }
                //this.cedulaBusqueda=null;
            }
            else
            {
                ArrayList<EmpleadoDepartamento> empleados_departamentos = nominaRolDao.getEmpleadoDepartamentoByFuncion(funcion);
                for(EmpleadoDepartamento ed : empleados_departamentos){
                        grid.add(new GridQuincena(ed, fechaPago));
                    }
            
            }
            
            
            
            
        }
        
    }

    
    public void onCellEdit(CellEditEvent event) {  
            DataTable source = (DataTable)event.getSource();
            //GridQuincena seleccionado = (GridQuincena)source.getRowData();
            GridQuincena seleccionado = (GridQuincena)source.getRowData();
            NominaRolDao nominaRolDao=new NominaRolDaoImpl();
            boolean guardar=true;
            
            
            BigDecimal viejoValorRubro;
            
            int id_rubro_modificado = columns.get(event.getColumn().getHeaderText());
            
            Quincena quincena_modificada = seleccionado.getQuincena(id_rubro_modificado);
            if(quincena_modificada != null){
                viejoValorRubro = quincena_modificada.getValorRubro();
                quincena_modificada.setValorRubro(nuevoValorRubro);
                nominaRolDao.updateQuincena(quincena_modificada);
            }
            else{
                viejoValorRubro = BigDecimal.ZERO;
                QuincenaId q_id;
                quincena_modificada = new Quincena();
                if(seleccionado.getQuincena_detalle().size() == 0){
                    q_id = new QuincenaId();
                    q_id.setCedula(seleccionado.getCedula());
                    q_id.setFechaPago(fechaPago);
                    q_id.setIdDepartamento(seleccionado.getEmpleado_departamento().getId().getIdDepartamento());
                    
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(this.fechaPago);
                    
                    switch(Calendar.MONTH)
                    {
                        case 1:case 3:case 5:case 7:case 8:case 10:case 12: //Tienen 31 dias
                            if(cal.get(Calendar.DAY_OF_MONTH) == 15){
                                                                                quincena_modificada.setTipoPago('q');
                                                                          }
                                 else if(cal.get(Calendar.DAY_OF_MONTH) == 31){
                                                            quincena_modificada.setTipoPago('f');
                                                        }
                                 else
                                 {
                                     guardar=false;
                                 }
                            
                        break;
                        case 4:case 6:case 9:case 11: //Tienen 30 dias
                                 if(cal.get(Calendar.DAY_OF_MONTH) == 15){
                                                                                quincena_modificada.setTipoPago('q');
                                                                          }
                                 else if(cal.get(Calendar.DAY_OF_MONTH) == 30){
                                                            quincena_modificada.setTipoPago('f');
                                                        }
                                 else
                                 {
                                     guardar=false;
                                 }
                        break;    
                        
                        case 2: //FEBRERO
                                if(cal.get(Calendar.DAY_OF_MONTH) == 15){
                                                                                quincena_modificada.setTipoPago('q');
                                                                          }
                                 else if(cal.get(Calendar.DAY_OF_MONTH) == 28 || cal.get(Calendar.DAY_OF_MONTH) == 29  ){
                                                            quincena_modificada.setTipoPago('f');
                                                        }
                                 else
                                 {
                                     guardar=false;
                                 }                            
                            
                       break;
                    
                    
                    }
                    
                   
                }
                else{
                    Quincena q_ref = seleccionado.getQuincena_detalle().get(0);
                    q_id = q_ref.getId();
                    quincena_modificada.setTipoPago(q_ref.getTipoPago());
                }
                
                q_id.setIdRubro(id_rubro_modificado);
                
                quincena_modificada.setId(q_id);
                quincena_modificada.setValorRubro(nuevoValorRubro);
                
                
               if(guardar)
               {
                   quincena_modificada = nominaRolDao.updateQuincena(quincena_modificada);
                    seleccionado.agregarQuincena(quincena_modificada);
               }
                
            }
            
            if(nuevoValorRubro != null && !nuevoValorRubro.equals(viejoValorRubro)) {  
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor cambiado con exito", "Antiguo Valor: " + viejoValorRubro + ", Nuevo Valor:" + nuevoValorRubro);  
                FacesContext.getCurrentInstance().addMessage(null, msg);  
            }
            
            nuevoValorRubro=null;
    }
    
    //Para Imprimir Global
    public void ImpresionGlobal()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance(); 
        String url=null;
        boolean mostrar=true;
        if(this.fechaPago==null)
        {
           mostrar=false;
        
        }
        if(this.funcion==null || this.funcion.equals(""))
        {
            mostrar=false;
        
        } 
        
        if(mostrar)
        {
          url="/nominaUlvr/faces/ServletReporteNomina?fechapago="+this.fechaFormateada+"&funcion="+this.funcion;            
          System.out.println("openWin('"+url+"','"+this.funcion+"');");
          requestContext.execute("openWin('"+url+"','"+this.funcion+"');");
        }
        
        
    
    }
    
    
    public void guardarRubro()
    {
        RequestContext rc = RequestContext.getCurrentInstance();
      
        FacesContext context = FacesContext.getCurrentInstance();
        
        Boolean guardar=true;
        
        if(this.nuevoRubro.getNombre() == null || this.nuevoRubro.getNombre().length() == 0)
        {
            FacesMessage message = new FacesMessage("Debe de colocar una nombre válido");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("nRubro", message);
            guardar=false;            
        }
        
       
       if(this.nuevoRubro.getTipo() == null || this.nuevoRubro.getTipo().length()==0)
        {
            
            FacesMessage message = new FacesMessage("Debe Seleccionar un tipo de rubro");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("tRubro", message);
            guardar=false;            
        }
       
       
        if(guardar)
        {
            //TODO METODO PARA GUARDAR O ACTUALIZAR AL EMPLEADO
            NominaRolDao nominaRolDao = new NominaRolDaoImpl();
            nominaRolDao.guardarNuevoRubro(nuevoRubro);

            FacesMessage message = new FacesMessage("Informacion de Ingreso", "Se ha agregado el rubro de "+this.nuevoRubro.getTipo()+": "+this.nuevoRubro.getNombre());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(":formXlsResultado:messages", message);
            //Ejecuto el metodo para ocultar el widget
            
            RequestContext.getCurrentInstance().execute("rubroDlg.hide()");
            
            columnas = new ArrayList<>();
            columns = new HashMap<>();
            nuevoRubro = new Rubro(0, "", "");

            ArrayList<Rubro> ret = nominaRolDao.getAllRubros();
            for(Rubro r: ret){
                columnas.add(r.getNombre());
                columns.put(r.getNombre(), r.getIdRubro());
            }
            //buscarPagosEmpleado();
            guardar=false;
        }
    }
    
    public void filterListenerCedula(FilterEvent filterEvent)
    {
      
     if(filterEvent.getFilters().size()==0)   
    // if(filterEvent.getFilters().containsValue(""))
      {
         this.buscarPagosEmpleado();
      
      }
      else
      {
          System.out.println("Si tiene valor");
      
      }
    
    }
    
    
    
}
