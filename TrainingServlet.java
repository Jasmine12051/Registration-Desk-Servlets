package edu.jsu.mcis.cs415.lab5;

import java.io.PrintWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

import edu.jsu.mcis.cs415.lab5.dao.*;


public class TrainingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        DAOFactory daoFactory = null;

        ServletContext context = request.getServletContext();

        if (context.getAttribute("daoFactory") == null) {
            System.err.println("*** Creating new DAOFactory ...");
            daoFactory = new DAOFactory();
            context.setAttribute("daoFactory", daoFactory);
        }
        else {
            daoFactory = (DAOFactory) context.getAttribute("daoFactory");
        }
        
        response.setContentType("application/json; charset=UTF-8");
        
        try ( PrintWriter out = response.getWriter()) {
            String p_id = request.getParameter("sessionid");            
            if (p_id == null || "".equals(p_id)) {
                TrainingSessionDAO dao = daoFactory.getTrainingSessionDAO();
                out.println(dao.find());
            }
            
            else{
            
            int sessionid = Integer.parseInt(request.getParameter("sessionid"));
            
            TrainingSessionDAO dao = daoFactory.getTrainingSessionDAO();
            
            out.println(dao.find(sessionid));
            
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }


    @Override
    public String getServletInfo() {
        return "Training Session Servlet";
    }

}