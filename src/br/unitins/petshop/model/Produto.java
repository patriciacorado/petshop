package br.unitins.petshop.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class Produto implements Cloneable{

	private Integer id;
	private String nome;
	private String descricao;
	private Double preco;
	private Integer estoque;
	@NotNull(message = "A data deve ser específicada.")
	private LocalDate validade;
	private Categoria categoria;
	
	public Produto()	{
		
	}
	
	public Produto(Integer i) {
		this.id = i;
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public LocalDate getValidade() {
		return validade;
	}
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
