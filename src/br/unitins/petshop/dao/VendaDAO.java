package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import br.unitins.petshop.model.ItemVenda;
import br.unitins.petshop.model.Racao;
import br.unitins.petshop.model.Usuario;
import br.unitins.petshop.model.Venda;


public class VendaDAO implements DAO<Venda> {

	@Override
	public void inserir(Venda obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("venda ");
		sql.append("  (data_venda, id_usuario) ");
		sql.append("VALUES ");
		sql.append("  ( current_timestamp, ?) ");
		PreparedStatement stat = null;

		try {
			// Este statement retorna a chave primaria gerada pelo banco de dados
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setInt(1, obj.getUsuario().getId());
			stat.execute();
			
			// obter a chave primaria gerada pelo banco de dados
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next())
				obj.setId(rs.getInt("id"));
			
			// salvando os itens de venda
			for (ItemVenda itemVenda : obj.getListaItemVenda()) {
				// se der algum problema
				if (!inserirItemVenda(itemVenda, conn, obj.getId())) {
					new SQLException("Erro ao inserir um item de venda");
				}
			}
			
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao inserir");

		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;
		
		
	}
	
	private boolean inserirItemVenda(ItemVenda itemVenda, Connection conn, Integer idVenda) {
		boolean retorno = true;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("item_venda ");
		sql.append("  (preco, id_produto, id_venda) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?) ");
		
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setDouble(1, itemVenda.getPreco());
			stat.setDouble(2, itemVenda.getProduto().getId());
			stat.setDouble(3, idVenda);
			stat.execute();

		} catch (SQLException e) {
			System.out.println("Erro ao realizar um comando sql de insert.");
			e.printStackTrace();
			retorno  = false;
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}
		}
		return retorno;
	}
	
	private List<ItemVenda> obterTodosItensVenda(Venda venda) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<ItemVenda> listaItemVenda = new ArrayList<ItemVenda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  i.id, ");
		sql.append("  i.preco, ");
		sql.append("  i.id_produto ");
		sql.append("FROM  ");
		sql.append(" item_venda i ");
		sql.append("WHERE  ");
		sql.append(" i.id_venda = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, venda.getId());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				ItemVenda itemVenda = new ItemVenda();
				itemVenda.setId(rs.getInt("id"));
				itemVenda.setPreco(rs.getDouble("preco"));
				RacaoDAO dao = new RacaoDAO();
				Racao racao = new Racao();
				racao.setId(rs.getInt("id_produto"));
				itemVenda.setProduto(dao.obterUm(racao));
				listaItemVenda.add(itemVenda);
			}

		} catch (Exception e) {
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em Item de venda");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaItemVenda;
	}

	@Override
	public void alterar(Venda obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Venda obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Venda> obterTodos() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Venda obterUm(Venda obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Venda> obterTodos(Usuario usuario) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Venda> listaVenda = new ArrayList<Venda>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  v.id, ");
		sql.append("  v.data_venda, ");
		sql.append("  v.id_usuario ");
		sql.append("FROM  ");
		sql.append("  venda v ");
		sql.append("WHERE  ");
		sql.append(" v.id_usuario = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				Date data = rs.getDate("data_venda");
				//venda.setData(data == null ? null : data.toLocalDate());
				venda.setData(rs.getTimestamp("data_venda").toLocalDateTime());
				venda.setUsuario(usuario);
				
				venda.setListaItemVenda(obterTodosItensVenda(venda));
				

				listaVenda.add(venda);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em VendaDAO.");
		} finally {
			try {
				if (!stat.isClosed())
					stat.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar o Statement");
				e.printStackTrace();
			}

			try {
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				System.out.println("Erro a o fechar a conexao com o banco.");
				e.printStackTrace();
			}
		}

		if (exception != null)
			throw exception;

		return listaVenda;
	}

}
