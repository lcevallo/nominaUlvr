/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author jgarciah
 */
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //PrintWriter out = response.getWriter();
        String host="jdbc:postgresql://localhost:5432/reporte_nomina";
        String login="postgres";
        String pswd="PoiZxc357";
        
        /*
        String curmat = request.getParameter("curmat");
        int id = Integer.parseInt(curmat);
        
        String parcial = request.getParameter("parcial");
        int idPArcial = Integer.parseInt(parcial);
        //ServletOutputStream servletOutputStream = response.getOutputStream();
        */
        
        String fecha = request.getParameter("fechapago");
        String funcion = request.getParameter("funcion");
        String fechaActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String nombre_Archivo=funcion+"_"+fechaActual;
        
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(host,login,pswd);
            
            String jrxmlFileName = "";
            
            Map<String, Object> parametros = new HashMap<String, Object>();
            
            jrxmlFileName = "/reportes/archivo_resultado.jasper";
            //parametros.put("parcialID", idPArcial);
            
            //System.out.println("//////////////////////");
            //System.out.println(request.getRealPath(jrxmlFileName));
            File archivoReporte = new File(request.getRealPath(jrxmlFileName));
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            //String dateInString = "7-Jun-2013";
            
            Date date = formatter.parse(fecha);
            
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            
            
            parametros.put("fechapago", date);
            parametros.put("funcion", funcion);
            
            ServletOutputStream servletOutputStream = response.getOutputStream();
 
            byte[] bytes = null;
            try{
                /*
                String filename = JasperFillManager.fillReportToFile(archivoReporte.getPath(), parametros, conn);
                
                
                JRXlsExporter exporter = new JRXlsExporter();

                exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, filename);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://sample_report.xls");

                exporter.exportReport();
                */
                
                
                JasperPrint jp = new JasperPrint();
                
                jp = JasperFillManager.fillReport(archivoReporte.getPath(), parametros, conn);
                //jp = JasperFillManager.fillReport(reportStream, new HashMap(), dataSource);
                generateXLSOutput(nombre_Archivo, jp, response);
                
                
                
                
                /*
                JExcelApiExporter excelApiExporter = new JExcelApiExporter();
                excelApiExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		excelApiExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,"d://PRUEBA.xls");
		excelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		excelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
		excelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,Boolean.TRUE);
		excelApiExporter.exportReport();
                */
                
                /*
                
                bytes = JasperRunManager.runReportToPdf(archivoReporte.getPath(), parametros, conn);
 
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                */
            }catch(JRException e1){                
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e1.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            }
        } catch(Exception e) {            
            String salida = "Error generando Reporte Jasper, el error del Sistema es " + e;
            System.out.println(salida);
            e.printStackTrace();
        }
        
        
                
    }
    
    private void generateXLSOutput(String reportname, JasperPrint jasperPrint, HttpServletResponse resp) throws IOException, JRException {
                    String reportfilename = reportname + ".xls";
                    JExcelApiExporter exporterXLS = new JExcelApiExporter();

                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
                    resp.setHeader("Content-Disposition", "inline;filename=" + reportfilename);
                    resp.setContentType("application/vnd.ms-excel");

                    exporterXLS.exportReport();
                    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
