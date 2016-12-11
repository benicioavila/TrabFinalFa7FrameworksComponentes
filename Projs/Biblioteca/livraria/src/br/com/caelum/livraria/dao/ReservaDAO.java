package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
//import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Reserva;
import br.com.caelum.livraria.tx.Log;

public class ReservaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Inject
	private EntityManager em;

	private DAO<Reserva> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Reserva>(this.em, Reserva.class);
	}

	public void adiciona(Reserva t) {
		this.dao.adiciona(t);
	}

	public void remove(Reserva t) {
		this.dao.remove(t);
	}

	public void atualiza(Reserva t) {
		this.dao.atualiza(t);
	}

	@Log
	public List<Reserva> listaTodos() {
		return this.dao.listaTodos();
	}

	public Reserva buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}
}
