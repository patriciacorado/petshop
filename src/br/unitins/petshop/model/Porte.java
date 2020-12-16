package br.unitins.petshop.model;

public enum Porte {
	PEQUENO(1,"Pequeno"),
	MEDIO(2,"Médio"),
	GRANDE(3, "Grande");
	
	private String label;
	private int id;
	
	Porte(int id, String label) {
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
	
	public static 	Porte valueOf(int id) {
		for (Porte tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}
