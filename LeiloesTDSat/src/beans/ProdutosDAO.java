package beans;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false&serverTimezone=UTC";
        String usuario = "root";
        String senha = "root";
        return DriverManager.getConnection(url, usuario, senha);
    }
    
    public void venderProduto (int id) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Vendido");
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao marcar produto como vendido.");
        }
    }
  
    public void cadastrarProduto (ProdutosDTO produto) throws SQLException{
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try (Connection conn = conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());        
         stmt.executeUpdate();
        }
    }
    
    public List<ProdutosDTO> listarProdutosVendidos(){
        String sql = "SELECT * FROM produtos WHERE status = ?";
        
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Vendido");
        ResultSet rs = stmt.executeQuery();
        
        List<ProdutosDTO> listaProdutosVendidos = new ArrayList<>();
        
        while (rs.next()) {
            ProdutosDTO produtos = new ProdutosDTO();
            produtos.setId(rs.getInt("id"));
            produtos.setNome(rs.getString("nome"));
            produtos.setValor(rs.getInt("valor"));
  
            
            listaProdutosVendidos.add(produtos);
        }
        
        return listaProdutosVendidos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            return null;
            
        }
    
    }
    
    public List<ProdutosDTO> listarProdutos(){
        String sql = "SELECT * FROM produtos";
        
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        
        List<ProdutosDTO> listaProdutos = new ArrayList<>();
        
        while (rs.next()) {
            ProdutosDTO produtos = new ProdutosDTO();
            produtos.setId(rs.getInt("id"));
            produtos.setNome(rs.getString("nome"));
            produtos.setValor(rs.getInt("valor"));
            produtos.setStatus(rs.getString("status"));
  
            
            listaProdutos.add(produtos);
        }
        
        return listaProdutos;
        } catch (Exception e) {
            System.out.println("Erro ao buscar produtos: " + e.getMessage());
            return null;
            
        }
    
    }
}