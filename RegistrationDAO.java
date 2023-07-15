package edu.jsu.mcis.cs415.lab5.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    private final String QUERY_SELECT_BY_ID = "SELECT * FROM "
            + "((registration JOIN attendee ON registration.attendeeid = attendee.id) "
            + "JOIN `session` ON registration.sessionid = `session`.id) "
            + "WHERE `session`.id = ? AND attendee.id = ?";
    
    private final String QUERY_CREATE = "INSERT INTO "
            + "registration (sessionid, attendeeid) "
            + "VALUES (?, ?)";
    
    private final String QUERY_UPDATE = "UPDATE registration SET sessionid = ? WHERE attendeeid = ?";
    
    private final String QUERY_DELETE = "DELETE FROM registration WHERE attendeeid = ?";
    
    RegistrationDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }
    
    public String find(int sessionid, int attendeeid) {

        JsonObject json = new JsonObject();
        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
            ps.setInt(1, sessionid);
            ps.setInt(2, attendeeid);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                if (rs.next()) {
                    
                    json.put("success", hasresults);
                    
                    json.put("attendeeid", rs.getInt("attendeeid"));
                    json.put("sessionid", rs.getInt("sessionid"));
                    json.put("firstname", rs.getString("firstname"));
                    json.put("lastname", rs.getString("lastname"));
                    json.put("displayname", rs.getString("displayname"));
                    json.put("session", rs.getString("description"));
                                        
                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) { e.printStackTrace(); }
            }

        }

        return Jsoner.serialize(json);

    }
    
    public Boolean create(int sessionid, int attendeeid) {

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Boolean success = null;

        try {
            ps = conn.prepareStatement(QUERY_CREATE);
            ps.setInt(1, sessionid);
            ps.setInt(2, attendeeid);
            
            boolean hasresults = ps.execute();

            if (hasresults) {
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted == 1){
                    System.err.println("ture");
                }
                return success;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        finally {

            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                }
                catch (Exception e) { 
                    e.printStackTrace(); 
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                    ps = null;
                }
                catch (Exception e) {
                    e.printStackTrace(); 
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                }
                catch (Exception e) {
                    e.printStackTrace(); 
                }
            }

        }
        
        return success;    
    }
    
    public boolean update(int sessionid, int attendeeid) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = daoFactory.getConnection();
            ps = conn.prepareStatement(QUERY_UPDATE);
            ps.setInt(1, sessionid);
            ps.setInt(2, attendeeid);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as per your application's error handling approach
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public boolean delete(int attendeeid) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = daoFactory.getConnection();
            ps = conn.prepareStatement(QUERY_DELETE);
            ps.setInt(1, attendeeid);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        }
        
        catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as per your application's error handling approach
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
       

    
    
