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

@Entity
public class Livro {
	@Id
	private Long id;

	private String titulo;

	@Column(name="qtdepaginas")
	private Double qtdePaginas;

	@Column(name="id_escritor")
	private Long idEscritor;

	@Column(name="id_editora")
	private Long idEditora;

	public Livro(Long id, String titulo, Double qtdePaginas) {
		this(titulo, qtdePaginas);
		this.id = id;		
	}

	public Livro(String titulo, String qtdePaginas) {
		this.titulo = titulo;
		this.qtdePaginas = qtdePaginas;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public Double getqtdePaginas() {
		return qtdePaginas;
	}
	public Long getId() {
		return id;
	}

	public Long getIdEscritor() {
		return escritor.id;
	}

	public Long getIdEditora() {
		return editora.id;
	}

	@ManyToOne
	@JoinColumn(name = "idEditora")
	private Editora editora;

	@ManyToOne
	@JoinColumn(name = "idEscritor")
	private Escritor escritor;

	@Override
	public String toString() {
		return "Livro [id=" + id + ", Nome: " + titulo + ", Endereço: " + qtdePaginas + "]";
	}
	
	
	

}
