package br.unitins.petshop.model;

public class ItemVenda {
	private Integer id;  // 1-windows --> 55
	private Double preco;// 2-linux   --> 55
	private Produto produto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}


}
