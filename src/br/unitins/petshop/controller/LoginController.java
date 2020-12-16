package br.unitins.petshop.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.petshop.application.Session;
import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.UsuarioDAO;
import br.unitins.petshop.model.Usuario;

@Named
@RequestScoped
public class LoginController {

	private Usuario usuario;

	public void logar() {
		
		UsuarioDAO dao = new UsuarioDAO();
		try {
			Usuario usuarioLogado = 
					dao.obterUsuario(getUsuario().getEmail(),
							Util.hash(getUsuario().getSenha()));
			if (usuarioLogado == null)
				Util.addErrorMessage("Usuário ou senha inválido.");
			else {
				// Usuario existe com as credenciais
				Session.getInstance().setAttribute("usuarioLogado", usuarioLogado);
				Util.redirect("venda.xhtml");
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Problema ao verificar o Login.");
		}
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
