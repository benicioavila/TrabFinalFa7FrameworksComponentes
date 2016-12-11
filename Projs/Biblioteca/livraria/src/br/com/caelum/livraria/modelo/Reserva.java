package br.com.caelum.livraria.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Reserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8717494147457314032L;

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToMany
	private List<Livro> livros = new ArrayList<Livro>();

	// @ManyToOne
	// private Usuario usuario;

	public void adicionarLivro(Livro livroaux) {
		livros.add(livroaux);
	}

	public void removerLivro(Livro livroaux) {
		livros.remove(livroaux);
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
