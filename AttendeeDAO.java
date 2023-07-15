package edu.jsu.mcis.cs415.lab5.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendeeDAO {
    
    private final DAOFactory daoFactory;
    
    private final String QUERY_SELECT_BY_ID = "SELECT * FROM "
            + "attendee WHERE id = ?";
    
    private final String QUERY_CREATE = "INSERT INTO "
            + "attendee (firstname, lastname, displayname) "
            + "VALUES (?, ?, ?)";
    
    private final String QUERY_UPDATE = "UPDATE attendee SET firstname = ?, lastname = ?, displayname = ? WHERE id = ?";

    
    AttendeeDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }
        
    
    public String find(int id) {

        JsonObject json = new JsonObject();
        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
            ps.setInt(1, id);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                
                while (rs.next()) {
                    json.put("success", true);
                    json.put("attendeeId", rs.getInt("id"));
                    json.put("firstname", rs.getString("firstname"));
                    json.put("lastname", rs.getString("lastname"));
                    json.put("displayname", rs.getString("displayname"));             
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
    
    public Boolean create(String firstname, String lastname, String displayname) {

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_CREATE);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, displayname);
            
            boolean hasresults = ps.execute();

            if (hasresults) {
                int rowsInserted = ps.executeUpdate();
                return rowsInserted > 0;
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
        return false;    
    }
    
    
    public boolean update(String firstname, String lastname, String displayname, int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = daoFactory.getConnection();
            ps = conn.prepareStatement(QUERY_UPDATE);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, displayname);
            ps.setInt(4, id);

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
