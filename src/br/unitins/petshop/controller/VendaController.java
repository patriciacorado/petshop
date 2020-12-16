package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.RacaoDAO;
import br.unitins.petshop.model.ItemVenda;
import br.unitins.petshop.model.Racao;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = -2660303294844733390L;
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Racao> listaProduto;
	
	public void novaProduto() {
		Util.redirect("cadastroproduto.xhtml");
	}
	
	public void pesquisar() {
		System.out.println(tipoFiltro);
		RacaoDAO dao = new RacaoDAO();
		try {
			setListaProduto(dao.obterListaProdutoComEstoque(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaProduto(null);
		}
	}
	
	public void addCarrinho(Racao produto) {
		try {
			RacaoDAO dao = new RacaoDAO();
			// obtendo os dados atuais da racao
			produto = dao.obterUm(produto);
			
			List<ItemVenda> listaItemVenda = null;
			Object obj = Session.getInstance().getAttribute("carrinho");
			
			if (obj == null) 
				listaItemVenda = new ArrayList<ItemVenda>();
			else 
				listaItemVenda = (List<ItemVenda>) obj;
			
			// montando o item de venda
			ItemVenda item = new ItemVenda();
			item.setProduto(produto);
			item.setPreco(produto.getPreco());
			listaItemVenda.add(item);
			
			// atualizando a sessao do carrinho de compras
			Session.getInstance().setAttribute("carrinho", listaItemVenda);
			
			Util.addInfoMessage("O produto: " + produto.getMarca() + " foi adicionado ao carrinho.");
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Problema ao adicionar o produto ao carrinho. Tente novamente.");
		}
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

	public List<Racao> getListaProduto() {
		if (listaProduto == null)
			listaProduto = new ArrayList<Racao>();
		return listaProduto;
	}

	public void setListaProduto(List<Racao> listaProduto) {
		this.listaProduto = listaProduto;
	}

}