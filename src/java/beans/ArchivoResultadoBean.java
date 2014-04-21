/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.XlsResultadoDao;
import dao.XlsResultadoDaoImpl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Departamento;
import model.Empleado;
import model.VistaResultado;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author lcevallosc
 */
@ManagedBean
@ViewScoped
public class ArchivoResultadoBean {
    private VistaResultado vistaResultado;
    private List<VistaResultado> listVistaResultado;
    private List<Departamento> listDepartamento;
    private Departamento departamentoSelected;
    
    private String concepto;
    private Date fechaPago;
    private String funcion;
    private Empleado empleadoSelected;
    
    public ArchivoResultadoBean() {
        if(this.empleadoSelected==null)
        {
            this.empleadoSelected=new Empleado();
        }
        
         if(this.departamentoSelected==null)
        {
            this.departamentoSelected=new Departamento();
        }
        
    }
    
    //GETTER
    
    public VistaResultado getVistaResultado() {
      
        return vistaResultado;
    }
    
    public List<VistaResultado> getListVistaResultado() {
       // XlsResultadoDao xlsResultadoDao=new XlsResultadoDaoImpl();
       // this.listVistaResultado=xlsResultadoDao.getXlsResultado(28,2,2014,"ADMINISTRATIVO");
        return listVistaResultado;
    }

    public String getConcepto() {
        return concepto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public String getFuncion() {
        return funcion;
    }

    public Empleado getEmpleadoSelected() {
        return empleadoSelected;
    }

    public List<Departamento> getListDepartamento() {
        XlsResultadoDao xlsResultadoDao= new XlsResultadoDaoImpl();
        this.listDepartamento= xlsResultadoDao.getDepartamentos();        
        return listDepartamento;
    }

    public Departamento getDepartamentoSelected() {
        return departamentoSelected;
    }

    
    ///SETTER
    public void setListDepartamento(List<Departamento> listDepartamento) {
        this.listDepartamento = listDepartamento;
    }
    
    
    public void setDepartamentoSelected(Departamento departamentoSelected) {
        this.departamentoSelected = departamentoSelected;
    }
    
     public void setEmpleadoSelected(Empleado empleadoSelected) {
        this.empleadoSelected = empleadoSelected;
    }
    
    
    public void setVistaResultado(VistaResultado vistaResultado) {
        this.vistaResultado = vistaResultado;
    }

    public void setListVistaResultado(List<VistaResultado> listVistaResultado) {
        this.listVistaResultado = listVistaResultado;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    
    //FIN DE LOS SETTERS
    
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
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.fechaPago);
            dia=cal.get(Calendar.DAY_OF_MONTH);
            mes=cal.get(Calendar.MONTH)+1;
            anio=cal.get(Calendar.YEAR);
            
            XlsResultadoDao xlsResultadoDao=new XlsResultadoDaoImpl();
            this.listVistaResultado=xlsResultadoDao.getXlsResultado(dia,mes,anio,this.funcion);
            System.out.println("Vamos a ver que paso");
        }
        
    }
    
    
    public void exportarAExcel(){
        
        if(this.funcion.length()<=0 || this.fechaPago == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error al Generar Reporte", "Por favor ingrese función y fecha"));  
        }
        else{
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            
            //System.out.println(date);
            //System.out.println(formatter.format(date));
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/nominaUlvr/NewServlet?fechapago="+formatter.format(this.fechaPago)+"&funcion="+this.funcion);

            }catch(Exception e){
                System.out.println("exception del generar el pdf   "+e.getMessage());
                //flag=true;
            }
        }
    }
    
    public void GuardarEmpleado()
    {
   
        RequestContext rc = RequestContext.getCurrentInstance();
      
        FacesContext context = FacesContext.getCurrentInstance();
        
        Boolean guardar=true;
        
        if(this.empleadoSelected.getCedula()==null || this.empleadoSelected.getCedula().length()<10)
        {
            
            FacesMessage message = new FacesMessage("Debe de colocar una cedula valida");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("cedulaW", message);
            guardar=false;            
        }
        
       if(this.empleadoSelected.getNombres()==null || this.empleadoSelected.getNombres().length()==0)
        {
            
            FacesMessage message = new FacesMessage("Debe de colocar un nombre valido");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("nombresW", message);
            guardar=false;            
        }
       
       if(this.empleadoSelected.getApellidos()==null || this.empleadoSelected.getApellidos().length()==0)
        {
            
            FacesMessage message = new FacesMessage("Debe de colocar un apellido valido");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("apellidosW", message);
            guardar=false;            
        }
       
       if(this.empleadoSelected.getNumCuenta()==null || this.empleadoSelected.getNumCuenta().length()==0)
        {
            
            FacesMessage message = new FacesMessage("Debe de colocar un numero de cuenta");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("numCuentaW", message);
            guardar=false;            
        }
       
       if(this.empleadoSelected.getTipoCuenta()==null || this.empleadoSelected.getTipoCuenta().length()==0)
        {
            
            FacesMessage message = new FacesMessage("Debe de colocar un tipo de cuenta valido");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("tipoCuentaW", message);
            guardar=false;            
        }
       
       if(this.empleadoSelected.getEmail()==null || this.empleadoSelected.getEmail().length()==0)
        {
            
            FacesMessage message = new FacesMessage("Debe de colocar un correo valido");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("emailW", message);
            guardar=false;            
        }
       
       
        if(this.departamentoSelected.getIdDepartamento()==0)
        {
            FacesMessage message = new FacesMessage("Debe de colocar un Departamento");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("departamentoW", message);
            guardar=false;            
        }
       
        if(guardar)
        {
            //TODO METODO PARA GUARDAR O ACTUALIZAR AL EMPLEADO
            XlsResultadoDao xlsResultadoDao=new XlsResultadoDaoImpl();
            xlsResultadoDao.GuardarEmpleado(empleadoSelected, this.departamentoSelected.getIdDepartamento(), this.funcion);
            FacesMessage message = new FacesMessage("Informacion de Ingreso", "Se ha agregado al empleado: "+this.empleadoSelected.getNombres()+" "+this.empleadoSelected.getApellidos());
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(":formXlsResultado:messages", message);
            this.empleadoSelected=null;
            this.empleadoSelected=new Empleado();
            this.departamentoSelected=null;
            this.departamentoSelected=new Departamento();
            //Ejecuto el metodo para ocultar el widget
            
            RequestContext.getCurrentInstance().execute("emplDlg.hide()");
            buscarPagosEmpleado();
        }
    }
    
    public void handleDialogCloseEmpleado(CloseEvent event)
    {
        String msg=event.getComponent().getId()+" La ventana de ingreso de Empleado ha sido cerrada";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg);
        facesContext.addMessage("Termino el ingreso del empleado", message);
    }
    
    
    public void onCellEdit(CellEditEvent event) {  
                                                    
                                                    Object oldValue = event.getOldValue();  
                                                    Object newValue = event.getNewValue();  
                                                    DataTable source = (DataTable)event.getSource();
                                                    VistaResultado seleccionado = (VistaResultado)source.getRowData();
                                                    
                                                    XlsResultadoDao xlsResultadoDao=new XlsResultadoDaoImpl();
                                                    
                                                    xlsResultadoDao.GuardarValor(seleccionado.getCedula(),this.fechaPago,(BigDecimal)newValue,this.concepto);
                                                     
                                                    if(newValue != null && !newValue.equals(oldValue)) {  
                                                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor cambiado con exito", "Antiguo Valor: " + oldValue + ", Nuevo Valor:" + newValue);  
                                                        FacesContext.getCurrentInstance().addMessage(null, msg);  
                                                    }  

                                            }
}
