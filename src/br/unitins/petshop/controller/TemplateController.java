package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class TemplateController implements Serializable {

	private static final long serialVersionUID = -2343574977842930993L;
	
	public void encerrarSessao() {
		Session.getInstance().invalidateSession();
		Util.redirect("login.xhtml");
	}

	public Usuario getUsuarioLogado() {
		Object obj =Session.getInstance().getAttribute("usuarioLogado");
		if (obj == null)
			return null;
		return (Usuario) obj;
	}
	
}