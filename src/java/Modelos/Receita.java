/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Conexao;

/**
 *
 * @author User
 */
public class Receita {

    private int id;
    private int idUsuario;
    private int idCategoria;
    private String descricao;
    private float valor;
    private Date data;
    private Date dataInicial;
    private Date dataFinal;
    
    
    /*
    private int id;
    private String descricao;
    private float valor;
    private Date data;
    private Date dataInicial;
    private Date dataFinal;
*/
    
    
    
    public boolean salvar() {
        String sql = "insert into receita(idusuario, idcategoria, descricao, valor, data)";
        sql += "values(?,?,?,?,?)";
        Connection con = Conexao.conectar();

        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.idUsuario);
            stm.setInt(2, this.idCategoria);
            stm.setString(3, this.descricao);
            stm.setFloat(4, this.valor);
            stm.setDate(5, this.data);
            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }

    /*  
    public boolean salvar() {
        String sql = "insert into receita(descricao, valor, data)";
        sql += "values(?,?,?)";
        Connection con = Conexao.conectar();

        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.descricao);
            stm.setFloat(2, this.valor);
            stm.setDate(3, this.data);

            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }
     */
    public boolean alterar() {
        Connection con = Conexao.conectar();
        String sql = "update receita set ";
        sql += "descricao = ?,";
        sql += "valor = ?,";
        sql += "data = ?";
        sql += " where id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.descricao);
            stm.setFloat(2, this.valor);
            stm.setDate(3, this.data);
            stm.setInt(4, this.id);

            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public Receita consultar(int id) {
        Connection con = Conexao.conectar();
        String sql = "select id, descricao, valor, data"
                + " from receita where id = ?";
        Receita receita = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                receita = new Receita();
                receita.setId(id);
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getFloat("valor"));
                receita.setData(rs.getDate("data"));

            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return receita;
    }

    /*
  public ResultSet consultaReceitaDetalh(int user,String descricao,Float valor,Date dataInicio,Date dataFim){
      Connection con = Conexao.conectar();
      String sql = "select usuario.nome,  ";
               sql += "      receita.descricao,   ";
                sql += "     sum(receita.valor)    ";
                sql += "from receita, usuario ";
                sql += "where receita.idusuario = usuario.id ";
                sql += "  and receita.idusuario = ?";
                sql += "and receita.data between >= ? and receita.data <= ? ";
                sql += "group by usuario.nome, receita.descricao ";
                sql += "order by nome";

      ResultSet rs = null;
      try {
          PreparedStatement stm = con.prepareStatement(sql);
          stm.setInt(1, user);
          stm.setString(2, descricao);
          stm.setFloat(3, valor);
          stm.setDate(4, dataInicio);
          stm.setDate(5, dataFim);
          rs = stm.executeQuery();
      } catch (SQLException ex) {
          Logger.getLogger(Receita.class.getName()).log(Level.SEVERE, null, ex);
      }
      return rs;
  }

    public List<Receita> consultaDetalhada (String inicio, String fim) {
        Connection con = Conexao.conectar();
        List<Receita> lista = new ArrayList<>();
        String sql = "select usuario.nome,  ";
               sql += "      receita.descricao,   ";
                sql += "     sum(receita.valor)    ";
                sql += "from receita, usuario ";
                sql += "where receita.idusuario = usuario.id ";
                sql += "  and receita.idusuario = ?";
                sql += "and receita.data between >= ? and receita.data <= ? ";
                sql += "group by usuario.nome, receita.descricao ";
                sql += "order by nome";
                
                
                
                
              "select id, descricao, valor, data"
                   + " from receita where data >= '"  +inicio+"' and data <= '" + fim + "'";

        Receita receita = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
           // stm.setString(1, inicio);
           // stm.setString(2, fim);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getFloat("valor"));
                receita.setData(rs.getDate("data"));

                lista.add(receita);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
   
     */
    public ResultSet consultarInner(int pIdUser) {
        Connection con = Conexao.conectar();
        String sql = "select r.id, r.idusuario, r.idcategoria, c.descricao categoria, "
                + "r.descricao, r.valor, r.data "
                + "from receita r, categoria c "
                + "where r.idcategoria = c.id "
                + "and r.idusuario = ? "
                + "order by data;";
        ResultSet rs = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, pIdUser);
            rs = stm.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return rs;
    }

    public float getTotalReceita(int idUser, Date dataInicio, Date dataFim){
       List<Receita> lista =  consultaLancamentosByIntervaloData(idUser,dataInicio, dataFim, true);
       float valor = 0f;
       for (Receita r : lista){
           valor += r.getValor();
       }
       return valor;
    }
    
    
    public List<Receita> consultaLancamentosByIntervaloData(int idUser, Date dataInicio, Date dataFim, boolean agrupar) {
        Connection con = Conexao.conectar();
        List<Receita> lista = new ArrayList<>();
        String sql = "select receita.descricao, receita.data, ";

        if (agrupar) {
            sql += " sum(receita.valor) as valor ";
        } else {
            sql += " receita.valor   ";
        }

        sql += " from receita ";
        sql += " where receita.idusuario = ?";
        sql += " and receita.data between ? and ? ";
        if (agrupar) {
            sql += " group by receita.descricao, receita.data ";
        }
        sql += " order by receita.descricao";

        Receita receita = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idUser);
            stm.setDate(2, dataInicio);
            stm.setDate(3, dataFim);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                receita = new Receita();
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getFloat("valor"));
                receita.setData(rs.getDate("data"));
                lista.add(receita);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    }
    
    public List<Receita> consultaLancamentosByIntervaloData(int idUser, Date dataInicio, Date dataFim, boolean agrupar, int categoria) {
        Connection con = Conexao.conectar();
        List<Receita> lista = new ArrayList<>();
        String sql = "select receita.descricao, receita.data, ";

        if (agrupar) {
            sql += " sum(receita.valor) as valor ";
        } else {
            sql += " receita.valor   ";
        }

        sql += " from receita ";
        sql += " where receita.idusuario = ?";
        sql += " and receita.data between ? and ? ";
        sql += " and receita.idcategoria = ?";
        if (agrupar) {
            sql += " group by receita.descricao, receita.data ";
        }
        sql += " order by receita.descricao";

        Receita receita = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idUser);
            stm.setDate(2, dataInicio);
            stm.setDate(3, dataFim);
            stm.setInt(4, categoria);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                receita = new Receita();
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getFloat("valor"));
                receita.setData(rs.getDate("data"));
                lista.add(receita);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    
    
    
    
    
       public List<Receita> consultaTotalBalancete(int idUser, Date dataInicio, Date dataFim, boolean agrupar, int categoria) {
        Connection con = Conexao.conectar();
        List<Receita> lista = new ArrayList<>();
        String sql = "select receita.descricao, receita.data, ";

      
            sql += " sum(receita.valor) as valor ";
        
        sql += " from receita ";
        sql += " where receita.idusuario = ?";
        sql += " and receita.data between ? and ? ";
        sql += " and receita.idcategoria = ?";

        Receita receita = null;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idUser);
            stm.setDate(2, dataInicio);
            stm.setDate(3, dataFim);
            stm.setInt(4, categoria);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                receita = new Receita();
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getFloat("valor"));
                receita.setData(rs.getDate("data"));
                lista.add(receita);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    } 
    
    
    
    
    
    
    
    
    
    
    
    public List<Receita> consultar() {
        List<Receita> lista = new ArrayList<>();
        Connection con = Conexao.conectar();
        String sql = "select id, descricao, valor, data from receita";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Receita receita = new Receita();
                receita.setId(rs.getInt("id"));
                receita.setDescricao(rs.getString("descricao"));
                receita.setValor(rs.getFloat("valor"));
                receita.setData(rs.getDate("data"));

                lista.add(receita);
            }

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return lista;
    }

    public boolean excluir() {
        Connection con = Conexao.conectar();
        String sql = "delete from receita ";
        sql += " where id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, this.id);
            stm.execute();
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

}
