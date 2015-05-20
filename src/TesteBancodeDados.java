
import dao.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Jogador;

public class TesteBancodeDados {

    public static void main(String[] args) {
        inserir();
        List<Jogador> lista = listar(); //colocou numa variável lista toda a lista de jogadores
        
        for (Jogador jogador : lista) //percorre a lista colocando cada jogador na variável jogador  
        {
            System.out.println("LOGIN:" + jogador.getLogin() + "\n");
            System.out.println("EMAIL:" + jogador.getEmail() + "\n");
        }
        
    }
   
    public static List<Jogador> listar()  //pega o q está no banco, coloca na classe jogador e adc o jogador na lista 
        {
        List<Jogador> jogadores = new ArrayList<Jogador>();
        try 
        {
            String sql = "SELECT * FROM JOGADOR";
        
            PreparedStatement pst = Conexao.getPreparedStatement(sql); //prepara o comando
            ResultSet res = pst.executeQuery(); //retorna os meus dados do banco, coloca no res todos os dados da tabela jogador
            
            while (res.next()) //vai andando nos registros, ele pega o login, senha e email do primeiro jogador, e assim vai indo com os outros jogadores...
            {
                //Pega a estrutura do ResultSet e
                //relaciona a classe Jogador    No res vai ter o login, senha e email de cada jogador, um por vez 
                Jogador jog = new Jogador();
                jog.setLogin(res.getString("login")); //login é o nome da coluna. Ele alimenta a classe jogador pegando o login la do banco
                jog.setSenha(res.getString("senha"));
                jog.setEmail(res.getString("email"));
                 
                //adiciona na lista
                jogadores.add(jog);
            }
        }
        
        catch (SQLException ex) 
        {
            Logger.getLogger(TesteBancodeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jogadores;
    }
    
    
    public static void inserir()
    {
        String sql;
        
        String login, senha, email;
        login = JOptionPane.showInputDialog("Informe o login: ");
        senha = JOptionPane.showInputDialog("Informe a senha: ");
        email = JOptionPane.showInputDialog("Informe o email: ");
        
        sql = "INSERT INTO jogador (login, senha, email)" + 
                "VALUES (?, ?, ?)";   //"VALUES ('"+login+"' + '"+senha+"' + '"+email+"')";
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        
        try 
        {
            pst.setString(1, login);
            pst.setString(2, senha);
            pst.setString(3, email);
            
            pst.executeUpdate(); //faz uma ação no banco e retorna se deu certo ou não, ele não retorna dados
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(TesteBancodeDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
