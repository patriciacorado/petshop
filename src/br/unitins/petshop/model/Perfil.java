package br.unitins.petshop.model;

public enum Perfil {
	USUARIO(1, "Usuário"),
	ADMINISTRADOR(1, "Administrador");
	
	private int id;
	private String label;
	
	Perfil(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Perfil valueOf(int id) {
		for (Perfil perfil : values()) {
			if (id == perfil.getId())
				return perfil;
		}
		return null;
	}
}
