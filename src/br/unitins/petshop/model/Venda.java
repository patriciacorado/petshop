package br.unitins.petshop.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class Venda {
	private Integer id;
	private LocalDateTime data;
	private Usuario usuario;
	private List<ItemVenda> listaItemVenda;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}

	public Double getTotalVenda() {
		int i = 0;
		Double valor = 0.0;
		while(i<this.listaItemVenda.size() ) {
			valor = valor + this.listaItemVenda.get(i).getPreco();
			i++;
		}
		return valor;
	}
}

