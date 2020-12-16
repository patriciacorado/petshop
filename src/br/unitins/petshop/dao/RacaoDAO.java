package br.unitins.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Animal;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.FaixaEtaria;
import br.unitins.petshop.model.Racao;

public class RacaoDAO implements DAO<Racao> {

	@Override
	public void inserir(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		AnimalDAO animal = new AnimalDAO();
		StringBuffer sql = new StringBuffer();
		
		sql.append("INSERT INTO ");
		sql.append("racao ");
		sql.append("  (marca, idanimal, faixaetaria, peso, preco, estoque, datavalidade, foto) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			
			stat.setObject(1, obj.getMarca());
			if(animal.obterId(obj.getAnimal())==null) animal.inserir(obj.getAnimal());
			obj.getAnimal().setId(animal.obterId(obj.getAnimal()).getId());
			
			stat.setObject(2, obj.getAnimal().getId());
			stat.setObject(3, (obj.getFaixaEtaria() == null ? null : obj.getFaixaEtaria().getId()));
			stat.setDouble(4, obj.getPeso());
			stat.setDouble(5, obj.getPreco());
			stat.setInt(6, obj.getEstoque());
			stat.setDate(7, Date.valueOf(obj.getDataValidade()));
			stat.setString(8, obj.getFoto());
			

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
	public void alterar(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		AnimalDAO animal = new AnimalDAO();
		StringBuffer sql = new StringBuffer();
		
		sql.append("UPDATE racao SET ");
		sql.append("  id = ?, ");
		sql.append("  idanimal = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  marca = ?, ");
		sql.append("  faixaetaria = ?, ");
		sql.append("  peso = ?, ");
		sql.append("  preco = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  datavalidade = ?, ");
		sql.append("  foto = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");

		PreparedStatement stat = null;

		try {
			stat = conn.prepareStatement(sql.toString());
			
			stat.setInt(1, obj.getId());
			if(animal.obterId(obj.getAnimal())==null) 
					animal.inserir(obj.getAnimal());
			obj.getAnimal().setId(animal.obterId(obj.getAnimal()).getId());
			
			stat.setObject(2, obj.getAnimal().getId());
			stat.setString(3, obj.getDescricao());
			stat.setString(4, obj.getMarca());
			stat.setObject(5, (obj.getFaixaEtaria() == null ? null : obj.getFaixaEtaria().getId()));
			stat.setDouble(6, obj.getPeso());
			stat.setDouble(7, obj.getPreco());
			stat.setInt(8, obj.getEstoque());
			stat.setDate(9, Date.valueOf(obj.getDataValidade()));
			stat.setString(10, obj.getFoto());
			
			stat.setInt(11, obj.getId());
			
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
	public void excluir(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();

		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM racao WHERE id = ?");

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
	public List<Racao> obterTodos() throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Racao> listaProduto = new ArrayList<Racao>();
		AnimalDAO a = new AnimalDAO();
		Animal animal = new Animal();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.idanimal,");
		sql.append("  p.marca, ");
		sql.append("  p.faixaetaria, ");
		sql.append("  p.peso, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.datavalidade, ");
		sql.append("  p.foto, ");
		sql.append("  p.descricao ");
		sql.append("FROM  ");
		sql.append("  racao p ");
		sql.append("ORDER BY p.marca ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Racao produto = new Racao();
				produto.setId(rs.getInt("id"));
				//seta o id do meu animal pegando o dado do banco
				animal.setId(rs.getInt("idanimal"));
				//seta meu animal pegando o animal que existe na tabela animal
				//setado com o msm id da minha tabela de racao.
				produto.setAnimal(a.obterUm(animal));
				produto.setMarca(rs.getString("marca"));
				produto.setFaixaEtaria(FaixaEtaria.valueOf(rs.getInt("faixaetaria")));
				produto.setPeso(rs.getDouble("peso"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				produto.setFoto(rs.getString("foto"));
				produto.setDescricao(rs.getString("descricao"));
				Date data = rs.getDate("datavalidade");
				produto.setDataValidade(data == null ? null : data.toLocalDate());
				listaProduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Nao foi possivel buscar os dados do produto.");
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

		return listaProduto;
	}

	@Override
	public Racao obterUm(Racao obj) throws Exception {
		Exception exception = null;
		Connection conn = DAO.getConnection();
		Animal a = new Animal();
		Racao produto = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.descricao,");
		sql.append("  p.idanimal,");
		sql.append("  p.marca, ");
		sql.append("  p.faixaetaria, ");
		sql.append("  p.peso, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.datavalidade ");
		sql.append("FROM  ");
		sql.append("  racao p ");
		sql.append("WHERE p.id = ? ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, obj.getId());

			ResultSet rs = stat.executeQuery();

			if (rs.next()) {
				produto = new Racao();
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
				a.setId(rs.getInt("idanimal"));
				produto.setAnimal(a);
				produto.setMarca(rs.getString("marca"));
				produto.setFaixaEtaria(FaixaEtaria.valueOf(rs.getInt("faixaetaria")));
				produto.setPeso(rs.getDouble("peso"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("datavalidade");
				produto.setDataValidade(data == null ? null : data.toLocalDate());
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

	public List<Racao> obterListaProduto(Racao obj) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Racao> listaProduto = new ArrayList<Racao>();
		AnimalDAO animal = new AnimalDAO();
		Animal a = new Animal();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.marca, ");
		sql.append("  p.idanimal, ");
		sql.append("  p.faixaetaria, ");
		sql.append("  p.descricao, ");
		sql.append("  p.peso, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.datavalidade ");
		sql.append("FROM  ");
		sql.append("  racao p ");
		sql.append("WHERE ");
		sql.append("  upper(p.marca) LIKE upper( ? ) ");
		sql.append("ORDER BY p.marca ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getMarca());

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Racao produto = new Racao();
				produto.setId(rs.getInt("id"));
				//Como pegar um campo int no bd e adicionar a um obj no java?
				//atribuir o valor a um atributo do msm tipo do obj e dps mandar o obj
				a.setId(rs.getInt("idanimal"));
				a = animal.obterUm(a);
				produto.setAnimal(a);
				System.out.println(a.getId());
				produto.setMarca(rs.getString("marca"));
				produto.setFaixaEtaria(FaixaEtaria.valueOf(rs.getInt("faixaetaria")));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPeso(rs.getDouble("peso"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("datavalidade");
				produto.setDataValidade(data == null ? null : data.toLocalDate());
				listaProduto.add(produto);
			}

		} catch (SQLException e) {
			Util.addErrorMessage("Nao foi possivel buscar os dados do produto.");
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

		return listaProduto;
	}

	public List<Racao> obterListaProdutoComEstoque(Integer tipo, String filtro) throws Exception {
		// tipo - 1 Nome; 2 Descricao
		Exception exception = null;
		Connection conn = DAO.getConnection();
		List<Racao> listaProduto = new ArrayList<Racao>();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  p.id, ");
		sql.append("  p.marca, ");
		sql.append("  p.faixaetaria, ");
		sql.append("  p.peso, ");
		sql.append("  p.preco, ");
		sql.append("  p.estoque, ");
		sql.append("  p.datavalidade");
		sql.append("FROM  ");
		sql.append("  produto p ");
		sql.append("WHERE ");
		sql.append("  upper(p.marca) LIKE upper( ? ) ");
		sql.append("  AND upper(p.marca) LIKE upper( ? ) ");
		sql.append("  AND p.estoque > 0 ");
		sql.append("ORDER BY p.marca ");

		PreparedStatement stat = null;
		try {

			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, tipo == 1 ? "%" + filtro + "%" : "%");
			stat.setString(2, tipo == 2 ? "%" + filtro + "%" : "%");

			ResultSet rs = stat.executeQuery();

			while (rs.next()) {
				Racao produto = new Racao();
				produto = new Racao();
				produto.setId(rs.getInt("id"));
				produto.setMarca(rs.getString("marca"));
				produto.setFaixaEtaria(FaixaEtaria.valueOf(rs.getInt("faixaetaria")));
				produto.setPeso(rs.getDouble("peso"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setEstoque(rs.getInt("estoque"));
				Date data = rs.getDate("datavalidade");
				listaProduto.add(produto);
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

		return listaProduto;
	}
}
