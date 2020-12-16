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
import br.unitins.petshop.model.Produto;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class UsuarioController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = -8473917611588400923L;
	CadastroSimples cadastro = new CadastroSimples();
	
	public UsuarioController() {
		super(new UsuarioDAO());
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

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	public void cadastrar() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario;
		usuario = dao.obterUmEmail(cadastro.getEmail());
		
		try {
			if(usuario != null) {
				Util.addErrorMessage("Usuário existente");
			}else {
				if(cadastro.getSenha().equals(cadastro.getConfirmarSenha())) {
					usuario = new Usuario();
					usuario.setSenha(cadastro.getSenha());
					usuario.setEmail(cadastro.getEmail());	
					this.setEntity(usuario);
					System.out.println("usuario: "+ this.getEntity());
					this.incluir();
				}else {
					Util.addErrorMessage("Senhas diferentes");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void atualizaPerfil() {
		Usuario usuario = null;
		usuario = pesquisar();
		this.setEntity(usuario);
//		System.out.println("idEntity = "+ this.getEntity().getId() 
//				+ "emailEntity: " + this.getEntity().getEmail());
		
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuarioFlash", this.getEntity());
		Util.redirect("perfilusuario.xhtml");
	}
	
	public Usuario pesquisar() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario;
		try {
			usuario = new Usuario();
			//pega o usuario da sessão
			usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			try {
				//seta a entity com o usuario logado
				this.setEntity(usuario);
				//pega o usuario logado do banco
				usuario = dao.obterUm(this.getEntity());
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return usuario;
	}

	public void editarPerfil() {
		Usuario editarUsuario = null;
		editarUsuario = pesquisar();
		//System.out.println("editar usuario: "+ editarUsuario.getEmail());
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuarioFlash", editarUsuario);
		atualizarPagina();
	}
	
	public void alterarPerfil() {
		this.alterar();
		this.atualizaPerfil();
	}
	
	public CadastroSimples getCadastro() {
		return cadastro;
	}

	public void setCadastro(CadastroSimples cadastro) {
		this.cadastro = cadastro;
	}
	
	
	public void atualizarPagina() {
		Util.redirect("alterausuario.xhtml");
	}
	
	
		
}
