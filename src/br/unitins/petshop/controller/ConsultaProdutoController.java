package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.RacaoDAO;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.Racao;

@Named
@ViewScoped
public class ConsultaProdutoController implements Serializable{

	
	private static final long serialVersionUID = -7064857362220414218L;

	@NotEmpty(message="Informe a marca por favor.")
	private String marca;
	private Racao racao=new Racao();
	private List<Racao> listaProduto;
	
	public ConsultaProdutoController(){
		RacaoDAO dao = new RacaoDAO();
		try {
			this.listaProduto = dao.obterTodos();
		} catch (Exception e) {
			Util.addErrorMessage("Não foi possivel acessar a lista.");
			e.printStackTrace();
		}
	}
	
	public void novoProduto() {
		Util.redirect("cadastroracao.xhtml");
	}
	
	public void pesquisar() {
		RacaoDAO dao = new RacaoDAO();
		try {
			this.racao.setMarca(marca);
			setListaProduto(dao.obterListaProduto(racao));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("não foi possivel encontrar a racao");
			setListaProduto(null);
		}
	}
	
	public void editar(Racao produto) {
		RacaoDAO dao = new RacaoDAO();
		Racao editarProduto = null;
		try {
			editarProduto = dao.obterUm(produto);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Nao foi possível encontrar o produto no banco de dados.");
			return;
		}
		
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("produtoFlash", editarProduto);
		novoProduto();
	}

	public List<Racao> getListaProduto() {
		if (listaProduto == null)
			listaProduto = new ArrayList<Racao>();
		return listaProduto;
	}

	public void setListaProduto(List<Racao> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public Racao getRacao() {
		if(racao==null) {
			racao=new Racao();
		}
		return racao;
	}

	public void setRacao(Racao racao) {
		this.racao = racao;
	}
	
	public Categoria[] getListaCategoria() {
		return Categoria.values();
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
}
