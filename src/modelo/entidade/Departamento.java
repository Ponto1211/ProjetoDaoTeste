package modelo.entidade;

import java.io.Serializable;

public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer Id;
	private String nome;

	public Departamento() {
	}

	public Departamento(Integer id, String nome) {
		Id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Departamento [Id=" + Id + ", nome=" + nome + "]";
	}
}
