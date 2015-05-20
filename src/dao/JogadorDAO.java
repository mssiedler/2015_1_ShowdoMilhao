package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Jogador;

public class JogadorDAO {
    
    
    public Boolean inserir (Jogador jogador)
    {
        Boolean retorno;
        //Monta o sql de insert da tabela
        String sql = "INSERT INTO jogador(login, senha, email)" + "VALUES (?, ?, ?)";
        
        //Prepara a execução do meu sql
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        
        try 
        {
            //insere os parâmetros
            pst.setString(1, jogador.getLogin()); //esse 1 é a ordem dos parâmetros
            pst.setString(2, jogador.getSenha());
            pst.setString(3, jogador.getEmail());
            
            //executa o sql no banco de dados
            pst.executeUpdate();
            retorno = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            retorno = false;
        }
        
        return retorno;
    }
    
    public List<Jogador> listar()
    {
        List <Jogador> lista = new ArrayList<Jogador>();
        String sql = "SELECT * FROM jogador";
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        
        try 
        { 
            //Executo o sql e jogo em um resultset
            ResultSet res = pst.executeQuery();
            //Enquanto tiver registro eu vou relacionar com minha classe Jogador e adicionar na lista
            while (res.next())
            {
                Jogador jogador = new Jogador();
                jogador.setLogin(res.getString("login"));
                jogador.setSenha(res.getString("senha"));
                jogador.setEmail(res.getString("email"));
                lista.add(jogador);
            }
        }
        
        catch (SQLException ex) 
        {
            Logger.getLogger(JogadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
    public Boolean remover (Jogador jogador)
    {
        Boolean retorno;
        
        String sql = "DELETE FROM jogador WHERE login = ?";
        
        //Prepara a execução do meu sql
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        
        try 
        {
            pst.setString(1, jogador.getLogin());
            pst.executeUpdate();
            retorno = true;
        }
        
        catch (Exception ex)
        {
            retorno = false;         
        }
        
        return retorno;
    }
    
    public Jogador login(Jogador jogador)
    {
        Jogador retorno = null;
        
        String sql = "SELECT * FROM jogador where login=? and senha=?";
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, jogador.getLogin());
            pst.setString(2, jogador.getSenha());
            
            ResultSet res = pst.executeQuery();
            
            //se tiver um jogador com login e senha igual ao informado
            //preenche os campos da variável de retorno
            if(res.next())
            {
                retorno = new Jogador();
                retorno.setEmail(res.getString("email"));
                retorno.setLogin(res.getString("login"));
                retorno.setSenha(res.getString("senha"));
            
            }
            
        } catch (Exception e) {
        }
        return retorno;
    }
}
