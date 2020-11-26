package br.unitins.petshop.model;

public enum Categoria {

	CACHORRO(1,"Cachorro"),
	GATO(2,"Gato"),
	PASSARO(3, "Pássaro"),
	PEIXE(4,"Peixe");
	
	private String label;
	private int id;
	
	Categoria(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public static Categoria valueOf(int id) {
		for (Categoria tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}
