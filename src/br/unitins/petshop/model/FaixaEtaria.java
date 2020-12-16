package br.unitins.petshop.model;

public enum FaixaEtaria {
	FILHOTE(1,"Filhote"),
	ADULTO(2,"Adulto"),
	IDOSO(3, "Idoso");
	
	private String label;
	private int id;
	
	FaixaEtaria(int id, String label) {
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
	
	public static 	FaixaEtaria valueOf(int id) {
		for (FaixaEtaria tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}
