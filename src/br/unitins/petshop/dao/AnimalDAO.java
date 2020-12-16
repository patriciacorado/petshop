package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Animal;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.FaixaEtaria;
import br.unitins.petshop.model.Porte;
import br.unitins.petshop.model.Racao;
import br.unitins.petshop.dao.DAO;
import br.unitins.petshop.model.Animal;

public class AnimalDAO implements DAO<Animal>{

	@Override
	public void inserir(Animal obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("animal ");
		sql.append("  (categoria, raca) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?) ");
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			// ternario java
			stat.setObject(1, (obj.getCategoria() == null ? null : obj.getCategoria().getId()));
			stat.setString(2, obj.getRaca());
			stat.execute();
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

	@Override
	public void alterar(Animal obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Animal obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Animal> obterTodos() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Animal obterUm(Animal obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		Animal produto = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.categoria, ");
		sql.append("  p.raca ");
		sql.append("FROM  ");
		sql.append("  animal p ");
		sql.append("WHERE p.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				produto = new Animal();
				produto.setId(rs.getInt("id"));
				produto.setRaca(rs.getString("raca"));
				produto.setCategoria(Categoria.valueOf(rs.getInt("categoria")));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("N�o foi possivel buscar os dados do produto.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em ProdutoDAO.");
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

		return produto;
	}
	
	public Animal obterId(Animal animal) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Animal a = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  a.id, ");
		sql.append("  a.categoria, ");
		sql.append("  a.raca ");
		sql.append("FROM  ");
		sql.append("  animal a ");
		sql.append("WHERE a.categoria = ? and a.raca = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, animal.getCategoria().getId());
			stat.setString(2, animal.getRaca());
		
			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				a = new Animal();
				a.setId(rs.getInt("id"));
				a.setCategoria(Categoria.valueOf(rs.getInt("categoria")));
				a.setRaca(rs.getString("raca"));

			}

		} catch (SQLException e) {
			Util.addErrorMessage("Nao foi possivel buscar os dados do animal.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em AnimalDAO.");
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

		return a;
	}


}
