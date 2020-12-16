package br.unitins.petshop.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastroSimples {
	@NotBlank(message="O campo senha é obrigatório")
	@Size(min = 3, max = 20, message = "A senha deve conter no mínimo 3 dígitos e maximo 20.")
	private String senha;
	@NotBlank(message="O campo email é obrigatório")
	private String email;
	@NotBlank(message="O campo confirmar senha é obrigatório")
	private String confirmarSenha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}
}
