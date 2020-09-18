package aula20200821.repositoryCompleto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class Editora {
	private Long id;
	private String nomeEditora;
	private String endereco;

	public Editora(Long id, String nomeEditora, String endereco) {
		this(nomeEditora, endereco);
		this.id = id;		
	}

	public Editora(String nomeEditora, String endereco) {
		this.nomeEditora = nomeEditora;
		this.endereco = endereco;
	}
	
	public String getnomeEditora() {
		return nomeEditora;
	}
	public String getEndereco() {
		return endereco;
	}
	public Long getId() {
		return id;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "livro")
    //@JoinColumn(name = "livro_id")
    private List<Livro> livros = new ArrayList<>();

	@Override
	public String toString() {
		return "Editora [id=" + id + ", Nome: " + nomeEditora + ", Endereço: " + endereco + "]";
	}
	
	
	

}
