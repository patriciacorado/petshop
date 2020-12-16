package br.unitins.petshop.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.UsuarioDAO;
import br.unitins.petshop.model.Usuario;
@Named
@ViewScoped
public class AlterarUsuarioController implements Serializable{

	private static final long serialVersionUID = -9023164511938899396L;
	Usuario usuario = null;
	public AlterarUsuarioController() {
		usuario=new Usuario();
		usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
	}
	
	public void alterar() throws Exception {
		UsuarioDAO	dao = new UsuarioDAO();
		dao.alterar(usuario);
		usuario = dao.obterUm(usuario);
		Session.getInstance().setAttribute("usuarioLogado", usuario);
		Util.redirect("perfilusuario.xhtml");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
