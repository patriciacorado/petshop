package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.RacaoDAO;
import br.unitins.petshop.model.Categoria;
import br.unitins.petshop.model.FaixaEtaria;
import br.unitins.petshop.model.Porte;
import br.unitins.petshop.model.Racao;

@Named
@ViewScoped
public class RacaoController extends Controller<Racao> implements Serializable{

	private static final long serialVersionUID = -7251169322215054717L;

	public RacaoController() {
		super(new RacaoDAO());
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		setEntity((Racao)flash.get("produtoFlash"));
	}

	@Override
	public Racao getEntity() {
		if (entity == null)
			entity = new Racao();
		return entity;
	}
	
	public void altera() {
		this.alterar();
		Util.redirect("pesquisaracao.xhtml");
	}
	
	public void remover() {
		this.excluir();
		Util.redirect("pesquisaracao.xhtml");
	}
	
	public FaixaEtaria[] getListaFaixaEtaria() {
		return FaixaEtaria.values();
	}
	
	public Categoria[] getListaCategoria() {
		return Categoria.values();
	}
}
