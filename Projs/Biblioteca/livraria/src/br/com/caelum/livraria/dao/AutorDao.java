package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
//import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;

public class AutorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	//@Inject
	EntityManager em;

	private DAO<Autor> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(this.em, Autor.class);
	}

	public void adiciona(Autor t) {
		this.dao.adiciona(t);
	}

	public void remove(Autor t) {
		this.dao.remove(t);
	}

	public void atualiza(Autor t) {
		this.dao.atualiza(t);
	}

	public List<Autor> listaTodos() {
		return this.dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

}
