/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ces3.carritoCompras.DAO;

import co.com.ces3.carritoCompras.model.carts;
import co.com.ces3.carritoCompras.model.productos;
import co.com.ces3.carritoCompras.util.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class cartsDAO {
    private Connection con;
    public cartsDAO(){
        conexion c = new conexion();
        con = c.getConnection();
    }
    
    public List<carts> consultarProductos(String email){
        List<carts> carts = new ArrayList<carts>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement("SELECT * FROM carts WHERE user_email='"+email+"'");
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                carts Carts = new carts();
                Carts.setId_cart(rs.getInt("id_cart"));
                Carts.setUser_email(rs.getString("user_email"));
                Carts.setId_product(rs.getInt("id_product"));
                Carts.setPrice(rs.getInt("price"));
                if(rs.getBoolean("status")){
                    Carts.setStatus("Comprado");
                }else{
                   Carts.setStatus("Pendiente");
                }
                Carts.setTotal(rs.getInt("total"));
                carts.add(Carts);
            }
        } catch (SQLException ex) {
            Logger.getLogger(cartsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         return carts;
    } 
    
    public void modificarCarrito(carts cart){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement("UPDATE CARTS SET STATUS = ? WHERE ID_CART = ?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, cart.getId_cart());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cartsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void crearCarrito(carts cart) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement("INSERT INTO carts (user_email, id_product, price, status, total) values (?,?,?,?,?)");
            preparedStatement.setString(1, cart.getUser_email());
            preparedStatement.setInt(2, cart.getId_product());
            preparedStatement.setInt(3, cart.getPrice());
            preparedStatement.setBoolean(4, false);
            preparedStatement.setInt(5, cart.getTotal());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(cartsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
