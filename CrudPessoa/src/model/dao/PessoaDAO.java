
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Pessoa;


public class PessoaDAO {

    
    public void crud(Pessoa p) {
        
        //VAR DE CONEXÃO
        Connection con = ConnectionFactory.getConnection();
        
        //prepara os scripts SQL e executa
        PreparedStatement stmt = null;

        //ADD OS VALORES PELO GET NO SQL "?"
        try {
            stmt = con.prepareStatement("INSERT INTO pessoa (nome,idade,uf)VALUES(?,?,?)");
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getUf());

            //EXECUTANDO O COMANDO SQL
            stmt.executeUpdate();

            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            //AQUI FECHO O STMT E O CON
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    //VAMOS LISTAR A TROPA QUE ESTAR NO BD
    public List<Pessoa> Listar() {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement pstm = null;
       
        //VAI PEGAR O RESULTADO DO SQL COMO UM ARRAY
        ResultSet rts = null;

        //criando lista de pessoa
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            pstm = con.prepareStatement("SELECT * FROM pessoa");
            rts = pstm.executeQuery();
                    
            //VAI PEGAR OS VALORES NESSE LAÇO DO rts E PASSAR 
            while (rts.next()) {

                //INSTANCIANDO O OBJETO PESSOA
                Pessoa pessoa = new Pessoa();

          //VAMOS PEGAR OS VALORES (GET) DO rts E PASSAR PARA AS VARIAVEIS (SET)
                pessoa.setId(rts.getInt("id"));
                pessoa.setNome(rts.getString("nome"));
                pessoa.setIdade(rts.getInt("idade"));
                pessoa.setUf(rts.getString("uf"));
                //add na lista pessoa
                pessoas.add(pessoa);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: "+ex);
        } finally {
            //FECHANDO CONEXÃO DESSA GALERA AI
            ConnectionFactory.closeConnection(con, pstm, rts);
        }

        return pessoas;

    }
    public List<Pessoa> listarNome(String nom) {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Pessoa> pessoas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM pessoa WHERE nome LIKE ?");
            stmt.setString(1, "%"+nom+"%");
            
            rs = stmt.executeQuery();

            while (rs.next()) {

                Pessoa pessoa = new Pessoa();

                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setIdade(rs.getInt("idade"));
                pessoa.setUf(rs.getString("uf"));
                pessoas.add(pessoa);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro: "+ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return pessoas;

    }

    public void atualizar(Pessoa p) {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE pessoa SET nome = ? ,idade = ?,uf = ? WHERE id = ?");
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getIdade());
            stmt.setString(3, p.getUf());
            //importante passar o id da linha
            stmt.setInt(4, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    public void delete(Pessoa p) {

        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM pessoa WHERE id = ?");
            //SETANDO O ID DA LINHA SEÇECIONADA
            stmt.setInt(1, p.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

}
