package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.VendaDAO;
import br.unitins.petshop.model.Usuario;
import br.unitins.petshop.model.Venda;


@Named
@ViewScoped
public class HistoricoController implements Serializable {

	private static final long serialVersionUID = 603493583707534054L;
	private List<Venda> listaVenda;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			Object obj = Session.getInstance().getAttribute("usuarioLogado");
			
			if (obj != null)
				try {
					listaVenda = dao.obterTodos((Usuario) obj);
				} catch (Exception e) {
					Util.addErrorMessage("Não foi possível obter o histórico de vendas.");
					listaVenda = new ArrayList<Venda>();
				}
		}
		return listaVenda;
	}
	
	public void detalhes(Venda venda) {
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("detalheFlash", venda);
		Util.redirect("detalhesvenda.xhtml");
		
	}

	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}
	
}
