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

public class Escritor {
	private Long id;
	private String nomeEscritor;
	private String endereco;

	public Escritor(Long id, String nomeEscritor, String endereco) {
		this(nomeEscritor, endereco);
		this.id = id;		
	}

	public Escritor(String nomeEscritor, String endereco) {
		this.nomeEscritor = nomeEscritor;
		this.endereco = endereco;
	}
	
	public String getnomeEscritor() {
		return nomeEscritor;
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
		return "Escritor [id=" + id + ", Nome: " + nomeEscritor + ", Endereço: " + endereco + "]";
	}
	
	
	

}
