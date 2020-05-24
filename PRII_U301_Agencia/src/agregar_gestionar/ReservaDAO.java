/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agregar_gestionar;
import agregar_gestionar.ReservaDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Katherine Arzate
 */
public class ReservaDAO {
    Connection conector = null;
    public ReservaDAO(Connection conn){
        conector = conn;
    }
    
    public int agregarReserva(ReservaDTO reserva){
        PreparedStatement objetoSentSql = null;
        ResultSet generatedKeys = null;
        int id = 0;
        
        String sql = "INSERT INTO Reservas "
                +  "(id_agencia, fecha_inicio, fecha_final, precio_t, ciudad)"
                +  "VALUES (?,?,?,?,?)";    
        
         try{
            conector.setAutoCommit(false);
            objetoSentSql = conector.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            objetoSentSql.setInt(1, reserva.getId_reserva());
            objetoSentSql.setString(2, reserva.getFecha_final());
            objetoSentSql.setString(3, reserva.getFecha_final());
            objetoSentSql.setFloat(4, reserva.getPrecio_t());
            objetoSentSql.setString(5, reserva.getCiudad());
            
            objetoSentSql.executeUpdate();
            generatedKeys = objetoSentSql.getGeneratedKeys();
            if(generatedKeys.next()){
                id = generatedKeys.getInt(1);
            }
            conector.commit();
            
        } catch (SQLException ex) {
            try{
                conector.rollback();
            }catch(SQLException ex1){
                System.out.println("Error en recuperación de transacción");
            }
        }
        return id;
    }
}
