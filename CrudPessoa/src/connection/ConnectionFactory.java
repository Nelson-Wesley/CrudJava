/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//MINHA CLASS DE CONEXAO
public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/pessoa";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            //FAZENDO SOBRECARGA E CHAMANDO OS DADOS DE ACESSO
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            //LANÇANDO EXCEPTION
            throw new RuntimeException("Erro na conexão: ", ex);
        }
    }

    //CLASS PARA FECHAR CONEXÕES
    public static void closeConnection(Connection con) {
        try {
            //SE FOR NULL, SIGNIFICA QUE ESTAR ABERTO
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //FECHAR STMT
    public static void closeConnection(Connection con, PreparedStatement stmt) {

        closeConnection(con);

        try {

            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //FECHAR P RS
    public static void closeConnection(Connection con, PreparedStatement pstm, ResultSet rst) {

        closeConnection(con, pstm);

        try {

            if (rst != null) {
                rst.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
