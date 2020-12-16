package br.unitins.petshop.model;

public class ItemVenda {
	private Integer id;  // 1-windows --> 55
	private Double preco;// 2-linux   --> 55
	private Racao produto;

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

	public Racao getProduto() {
		return produto;
	}

	public void setProduto(Racao produto) {
		this.produto = produto;
	}


}
