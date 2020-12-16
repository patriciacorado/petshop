package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Perfil;
import br.unitins.petshop.model.Usuario;

public class UsuarioDAO implements DAO<Usuario>{

	@Override
	public void inserir(Usuario obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("usuario ");
		sql.append("  ( email, senha, perfil) ");
		sql.append("VALUES ");
		sql.append("  (?, ?, ?) ");
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getEmail());
			stat.setString(2, Util.hash(obj.getSenha()));
			stat.setObject(3, (obj.getPerfil() == null ? null : obj.getPerfil().getId()));
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
	public void alterar(Usuario obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE usuario SET ");
		sql.append("  nome = ?, ");
		sql.append("  cpf = ?, ");
		sql.append("  email = ?, ");
		sql.append("  senha = ?, ");
		sql.append("  perfil = ?, ");
		sql.append("  data_nascimento = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			stat.setString(3, obj.getEmail());
			stat.setString(4, Util.hash(obj.getSenha()));
			stat.setObject(5, (obj.getPerfil() == null ? null : obj.getPerfil().getId()));
			// convertendo um obj LocalDate para sql.Date
			stat.setDate(6, obj.getDataNascimento() == null ? null : Date.valueOf(obj.getDataNascimento()));
			stat.setInt(7, obj.getId());
			stat.execute();
			// efetivando a transacao
			conn.commit();

		} catch (SQLException e) {

			System.out.println("Erro ao realizar um comando update.");
			e.printStackTrace();
			// cancelando a transacao
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.out.println("Erro ao realizar o rollback.");
				e1.printStackTrace();
			}
			exception = new Exception("Erro ao alterar");

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
	public void excluir(Usuario obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM usuario WHERE id = ?");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());
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
	public List<Usuario> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Usuario> listaUsuario = new ArrayList<Usuario>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
//		sql.append("  u.* ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.perfil, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  usuario u ");
		sql.append("ORDER BY u.nome ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setNome(rs.getString("nome"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));

				listaUsuario.add(usuario);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UsuarioDAO.");
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

		return listaUsuario;
	}

	@Override
	public Usuario obterUm(Usuario obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.perfil, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  usuario u ");
		sql.append("WHERE u.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UsuarioDAO.");
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

		return usuario;
	}

	public Usuario obterUsuario(String email, String senha) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.perfil, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.email = ? ");
		sql.append("  AND u.senha = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, email);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UsuarioDAO.");
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

		return usuario;
	}
	
	public Usuario obterUmEmail(String email) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		
		Usuario usuario = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  u.id, ");
		sql.append("  u.data_nascimento, ");
		sql.append("  u.nome, ");
		sql.append("  u.cpf, ");
		sql.append("  u.perfil, ");
		sql.append("  u.email, ");
		sql.append("  u.senha ");
		sql.append("FROM  ");
		sql.append("  usuario u ");
		sql.append("WHERE u.email = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, email);

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				Date data = rs.getDate("data_nascimento");
				usuario.setDataNascimento(data == null ? null : data.toLocalDate());
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.setEmail(rs.getString("email"));
				usuario.setSenha(rs.getString("senha"));
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Não foi possivel buscar os dados do usuario.");
			e.printStackTrace();
			exception = new Exception("Erro ao executar um sql em UsuarioDAO.");
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

		return usuario;
	}
}
