package br.unitins.petshop.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;

import br.unitins.petshop.application.Util;
import br.unitins.petshop.dao.UsuarioDAO;
import br.unitins.petshop.model.Usuario;

@Named
@ViewScoped
public class PesquisaUsuarioController implements Serializable {
	private static final long serialVersionUID = -9205249214475543015L;
	List<Usuario> listaUsuario = new ArrayList<Usuario>();
	Usuario usuario = null;
	@NotEmpty(message = "Informe o email por favor")
	String email;

	public void pesquisa() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();

		if (dao.obterUmEmail(email) == null) {
			Util.addErrorMessage("Usuario inexistente.");
			return;
		} else {
			usuario = new Usuario();
			this.usuario = dao.obterUmEmail(email);
			this.listaUsuario.add(this.usuario);
		}

	}

	public void excluir() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		dao.excluir(usuario);
		this.listaUsuario.remove(usuario);
		Util.addInfoMessage("Exclusão realizada com sucesso");
	}

	public void editar() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		this.usuario = dao.obterUmEmail(usuario.getEmail());
		if(this.usuario == null) {
			Util.addErrorMessage("Usuario inexistente");
			return;
		} else {
			Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
			flash.put("usuarioFlash", this.usuario);
			Util.redirect("cadastrousuario.xhtml");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Usuario> getListaUsuario() {
		return (ArrayList<Usuario>) listaUsuario;
	}

	public void setListaUsuario(ArrayList<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
