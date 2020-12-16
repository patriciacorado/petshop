package br.unitins.petshop.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastroSimples {
	@NotBlank(message="O campo senha � obrigat�rio")
	@Size(min = 3, max = 20, message = "A senha deve conter no m�nimo 3 d�gitos e maximo 20.")
	private String senha;
	@NotBlank(message="O campo email � obrigat�rio")
	private String email;
	@NotBlank(message="O campo confirmar senha � obrigat�rio")
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
