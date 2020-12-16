package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.VendaDAO;
import br.unitins.petshop.model.ItemVenda;
import br.unitins.petshop.model.Usuario;
import br.unitins.petshop.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -4099848791073983275L;
	private Venda venda;

	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
			venda.setListaItemVenda(new ArrayList<ItemVenda>());
		}
		// obtendo o carrinho da sessao
		Object obj = Session.getInstance().getAttribute("carrinho");
		if (obj != null)
			venda.setListaItemVenda((List<ItemVenda>) obj);

		return venda;
	}

	public void remover(ItemVenda itemVenda) {
		ItemVenda item = null;
		item = itemVenda;
		// vcs devem implementar
		Object obj = Session.getInstance().getAttribute("carrinho");
		// montando o item de venda
		List<ItemVenda> listaItemVenda = (List<ItemVenda>) obj;
		listaItemVenda.remove(itemVenda);
		// atualizando a sessao do carrinho de compras
		Session.getInstance().setAttribute("carrinho", listaItemVenda);
		Util.addInfoMessage("O produto: " + item.getProduto().getNome() + " foi removido do carrinho.");
		System.out.println("em carrinho chegou aqui");
	}

	public void finalizar() {
		// obtendo o usuario da sessao
		Object obj = Session.getInstance().getAttribute("usuarioLogado");
		if (obj == null) {
			Util.addErrorMessage("Para finalizar a venda o usuário deve estar logado.");
			return;
		}

		// adicionando o usuario logado na venda
		getVenda().setUsuario((Usuario) obj);

		VendaDAO dao = new VendaDAO();
		try {
			dao.inserir(getVenda());
			Util.addInfoMessage("Inclusão realizada com sucesso.");

			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
			setVenda(null);

		} catch (Exception e) {
			Util.addErrorMessage("Não é possivel fazer uma inclusão.");
			e.printStackTrace();
		}

	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}