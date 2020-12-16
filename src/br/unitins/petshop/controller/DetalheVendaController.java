package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.dao.VendaDAO;
import br.unitins.petshop.model.Venda;

@Named
@ViewScoped
public class DetalheVendaController implements Serializable {

	private static final long serialVersionUID = -4709033769037940369L;
	
	private Venda venda;

	public DetalheVendaController() {
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("detalheFlash");
		setVenda((Venda)flash.get("detalheFlash"));
		
		VendaDAO dao = new VendaDAO();
		try {
			dao.obterTodos(this.getVenda().getUsuario());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
