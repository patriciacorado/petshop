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
import br.unitins.petshop.dao.UsuarioDAO;
import br.unitins.petshop.model.CadastroSimples;
import br.unitins.petshop.model.Perfil;
import br.unitins.petshop.model.Sexo;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class UsuarioController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = 3940438807747806556L;
	String senha;

	public UsuarioController() {
		super(new UsuarioDAO());
		setEntity((Usuario)Session.getInstance().getAttribute("usuarioLogado"));
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("usuarioFlash");
		setEntity((Usuario)flash.get("usuarioFlash"));
	}

	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}
	
	public Sexo[] getListaSexo() {
		return Sexo.values();
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
//	public void pesquisarUsuario() {
//		this.setEntity((Usuario) Session.getInstance().getAttribute("usuarioLogado"));
////		System.out.println("usuario: "+ usuario.getNome());
//		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
//		flash.put("usuarioFlash", this.getEntity());
//		Util.redirect("perfilusuario.xhtml");
//		
//	}
	
	public void pesquisarUsuario() {
		this.setEntity((Usuario) Session.getInstance().getAttribute("usuarioLogado"));
//		System.out.println("usuario: "+ usuario.getNome());
		Session.getInstance().setAttribute("usuarioLogado", this.getEntity());
		Util.redirect("perfilusuario.xhtml");
	}
	
	public void cadastrar() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario;
		
		usuario = dao.obterUmEmail(this.getEntity().getEmail());
		try {
			if(usuario != null) {
				Util.addErrorMessage("Email cadastrado");
			}else {
				if(this.getEntity().getSenha().equals(this.senha)) {
					this.incluir();
				}else {
					Util.addErrorMessage("Senhas diferentes");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
