    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author lcevallosc
 */
public class ServletReportePersonalNomina extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private final String logotipo="/reportes/logomin.png";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        // /ServletReportePersonalNomina?fechapago=15-02-2014&cedula=0915740138
        String host="jdbc:postgresql://localhost:5432/reporte_nomina";
        String login="postgres";
        String pswd="PoiZxc357";
        
        
        String fecha = request.getParameter("fechapago");
        String cedula = request.getParameter("cedula");
        
        try {
            
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                //String dateInString = "7-Jun-2013";
                
                Date fechaImpresion = null;
                try {

                fechaImpresion = formatter.parse(fecha);
                
                 String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(fechaImpresion);
                 String nombre_Archivo="Rol"+"_"+cedula+"_"+fechaActual;
                
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(host,login,pswd);

                String jrxmlFileName = "";

                Map<String, Object> parametros = new HashMap<String, Object>();

                
                if(request.getParameter("funcion").equalsIgnoreCase("DOCENTES"))
                {
                    jrxmlFileName = "/reportes/DocenteNomina.jasper";
                }
                else
                {
                    jrxmlFileName = "/reportes/PersonalNomina.jasper";
                }                                
                   
                
                System.out.println(""+jrxmlFileName);
                
                
                
                File archivoReporte = new File(request.getRealPath(jrxmlFileName));
                
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaImpresion);
                
                
                int year=cal.get(Calendar.YEAR);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH)+1; 
                
                parametros.clear();
                parametros.put("dia", day);
                parametros.put("mes", month);
                parametros.put("anio", year);
                parametros.put("logo", this.getServletContext().getResourceAsStream(this.logotipo));
                parametros.put("cedula", cedula);
                
                ServletOutputStream servletOutputStream = response.getOutputStream();              
                
                
 
                byte[] bytes = JasperRunManager.runReportToPdf(archivoReporte.getPath(), parametros,conn);
                
                
                response.setHeader("Content-Disposition", "inline;filename=" + nombre_Archivo);
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes,0,bytes.length);
                
                servletOutputStream.flush();
                servletOutputStream.close();
                

                }
                catch(JRException e1){                
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e1.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            }
                catch (ParseException ex) {
                                                ex.printStackTrace();
                                           }
            
            
        } catch (Exception e) {            
             String salida = "Error generando Reporte Jasper, el error del Sistema es " + e;
             System.out.println(salida);
             e.printStackTrace();
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
