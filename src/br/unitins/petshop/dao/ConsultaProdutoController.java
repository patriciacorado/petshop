package br.unitins.petshop.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Produto;

@Named
@ViewScoped
public class ConsultaProdutoController implements Serializable{

	
	private static final long serialVersionUID = -7064857362220414218L;

	private Integer tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;
	
	public void novoProduto() {
		Util.redirect("produto.xhtml");
	}
	
	public void pesquisar() {
		ProdutoDAO dao = new ProdutoDAO();
		try {
			setListaProduto(dao.obterListaProduto(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaProduto(null);
		}
	}
	
	public void editar(Produto produto) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto editarProduto = null;
		try {
			editarProduto = dao.obterUm(produto);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Não foi possível encontrar a midia no banco de dados.");
			return;
		}
		
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("midiaFlash", editarProduto);
		novoProduto();
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null)
			listaProduto = new ArrayList<Produto>();
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}
}
