package edu.jsu.mcis.cs415.lab5.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrainingSessionDAO {
    
    private final DAOFactory daoFactory;
    
    private final String QUERY_SELECT_BY_ID = "SELECT *,CONCAT(\"R\", LPAD(attendeeid, 6, 0)) AS num FROM registration JOIN attendee ON registration.attendeeid = attendee.id WHERE sessionid=?";
    
    private final String QUERY_SELECT_ALL_SESSIONS = "SELECT * FROM session";
    
    TrainingSessionDAO(DAOFactory dao) {
        this.daoFactory = dao;
    }
    
    public String find(){
        JsonObject json = new JsonObject();
        JsonArray sessions = new JsonArray();
        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SELECT_ALL_SESSIONS);
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                while (rs.next()) {
                    json.put("success", true);
                    int sessionid = rs.getInt("id");
                    String description = rs.getString("description");
                    

                    JsonObject sessionObject = new JsonObject();
                    
                    sessionObject.put("sessionID", sessionid);
                    sessionObject.put("Description", description);

                    sessions.add(sessionObject);
                    json.put("sessions", sessions);
                                        
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

        return Jsoner.serialize(json);

    };    
    
    public String find(int sessionid) {

        JsonObject json = new JsonObject();
        JsonArray attendees = new JsonArray();
        json.put("success", false);

        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement(QUERY_SELECT_BY_ID);
            ps.setInt(1, sessionid);
            
            boolean hasresults = ps.execute();

            if (hasresults) {

                rs = ps.getResultSet();
                
                
                while (rs.next()) {
                    json.put("success", true);
                    int attendeeId = rs.getInt("attendeeid");
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    String displayName = rs.getString("displayname");
                    String num = rs.getString("num");

                    JsonObject attendeeObject = new JsonObject();
                    
                    attendeeObject.put("attendeeId", attendeeId);
                    attendeeObject.put("firstname", firstname);
                    attendeeObject.put("lastname", lastname);
                    attendeeObject.put("displayname", displayName);
                    attendeeObject.put("num", num);

                    attendees.add(attendeeObject);
                    json.put("attendees", attendees);
                                        
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

        return Jsoner.serialize(json);

    }
    
}
