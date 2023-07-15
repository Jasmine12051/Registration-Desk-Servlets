package edu.jsu.mcis.cs415.lab5;

import java.io.PrintWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

import edu.jsu.mcis.cs415.lab5.dao.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;


public class RegistrationServlet extends HttpServlet {

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
            
            int sessionid = Integer.parseInt(request.getParameter("sessionid"));
            int attendeeid = Integer.parseInt(request.getParameter("attendeeid"));
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();
            
            out.println(dao.find(sessionid, attendeeid));
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
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
            
            int sessionid = Integer.parseInt(request.getParameter("sessionid"));
            int attendeeid = Integer.parseInt(request.getParameter("attendeeid"));
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();
            dao.delete(attendeeid);
            out.println(dao.create(sessionid, attendeeid));
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
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
        
        BufferedReader br = null;
        response.setContentType("application/json; charset=UTF-8");
        
        try(PrintWriter out = response.getWriter()){
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String p = URLDecoder.decode(br.readLine().trim(), Charset.defaultCharset());
            
            HashMap<String, String> parameters = new HashMap<>();
            
            String[] pairs = p.trim().split("&");
            for (int i = 0; i < pairs.length; ++i) {
                String[] pair = pairs[i].split("=");
                parameters.put(pair[0], pair[1]);
            }
            
            int sessionid = Integer.parseInt(parameters.get("sessionid"));
            int attendeeid = Integer.parseInt(parameters.get("attendeeid"));
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();
            
            out.println(dao.update(sessionid, attendeeid));

        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
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
        
        BufferedReader br = null;
        response.setContentType("application/json; charset=UTF-8");
        
        try(PrintWriter out = response.getWriter()){
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String p = URLDecoder.decode(br.readLine().trim(), Charset.defaultCharset());
            
            HashMap<String, String> parameters = new HashMap<>();
            
            String[] pairs = p.trim().split("&");
            for (int i = 0; i < pairs.length; ++i) {
                String[] pair = pairs[i].split("=");
                parameters.put(pair[0], pair[1]);
            }
            
            int attendeeid = Integer.parseInt(parameters.get("attendeeid"));
            
            RegistrationDAO dao = daoFactory.getRegistrationDAO();
            
            out.println(dao.delete(attendeeid));

        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }         
    }

    @Override
    public String getServletInfo() {
        return "Registration Servlet";
    }

}