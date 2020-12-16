package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.UsuarioDAO;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class PerfilController implements Serializable{

	private static final long serialVersionUID = -682655929689458716L;
	Usuario usuario = null;
	public PerfilController(){
		usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
	}
	
	public void editar() throws Exception {
		Session.getInstance().setAttribute("usuarioLogado", usuario);
		Util.redirect("alterausuario.xhtml");
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
