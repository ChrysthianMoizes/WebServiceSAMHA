package acessControl;

import Persistence.Conection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import org.json.JSONObject;

/**
 * Generates a unique access token for a user who wants to use the service
 * @author Davy Lima
 */
public class AcessTokenControl {
    private static RandomString randomString = new RandomString();

    public static String isValidToken(String token) throws Exception {
        Connection con = Conection.getConexao();
        CallableStatement call = con.prepareCall("call isValidToken(?)");
        call.setString(1, token);
        ResultSet result = call.executeQuery();
        result.first();
        String r = result.getString("result");
        call.close();
        con.close();
        System.out.println(" isValidToken Result: "+r);
        return r;
    }

    public static JSONObject refreshToken(String token) throws Exception {
        Connection con = Conection.getConexao();
        String sql = "update autentication set dateExp = now(), dateRefresh = now()"
                + "where token like ?";
        PreparedStatement sp = con.prepareStatement(sql);
        sp.setString(1, token);
        int executeUpdate = sp.executeUpdate();
        if(executeUpdate == 1){
            JSONObject json = new JSONObject();
            json.put("token", token);
            String sql2 = "select u.name from user u join autentication a on "
                    + "u.idUser = a.idUser where a.token like ? ";
            PreparedStatement sp2 = con.prepareStatement(sql2);
            sp2.setString(1, token);
            ResultSet exq = sp2.executeQuery();
            exq.first();
            String nome = exq.getString(1);
            json.put("name", nome);
            sp2.close();
            sp.close();
            con.close();
            return json;
        }
        else 
            sp.close();
            con.close();
            throw new ExecutionException("SQL update failed", new Exception("Canot execute update"));
    }

    public static JSONObject newToken(String email) throws Exception {
        Connection con = Conection.getConexao();
        try{
            String token = randomString.nextString();
        
            String sqlSelect = "select idUser, name from user where email = ?";
            PreparedStatement sp = con.prepareStatement(sqlSelect);
            sp.setString(1, email);
            ResultSet rs = sp.executeQuery();
            rs.first();
            String id = rs.getString("idUser");
            String nome = rs.getString("name");

            String sqlInsert = "insert into autentication set idUser = ?, "
                    + "token = ?, dateExp = now()";
            sp = con.prepareStatement(sqlInsert);
            sp.setString(1, id);
            sp.setString(2, token);
            int result = sp.executeUpdate();
            con.commit();
            sp.close();
            con.close();
            System.out.println("NEWTOKEN: "+token + " usuario: "+id + " result "+result);

            if(result == 1){
                JSONObject json = new JSONObject();
                json.put("token", token);
                json.put("name", nome);
               return json;
            }else
                throw new ExecutionException("SQL insert failed", new Exception("Canot execute insert"));
        }catch (SQLException e){
            con.close();
            throw new ExecutionException("SQL insert failed", e);
        }
    }

}