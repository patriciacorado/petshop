package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.dao.ProdutoDAO;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.Produto;

@Named
@ViewScoped
public class ProdutoController extends Controller<Produto> implements Serializable{

	public ProdutoController() {
		super(new ProdutoDAO());
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		setEntity((Produto)flash.get("produtoFlash"));
	}

	private static final long serialVersionUID = -9119846944469331050L;

	@Override
	public Produto getEntity() {
		if (entity == null)
			entity = new Produto();
		return entity;
	}

	public Categoria[] getListaCategoria() {
		return Categoria.values();
	}

}
