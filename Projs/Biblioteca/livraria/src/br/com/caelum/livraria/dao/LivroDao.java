package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
//import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.tx.Log;

public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Inject
	private EntityManager em;

	private DAO<Livro> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Livro>(this.em, Livro.class);

	}

	public void adiciona(Livro t) {
		t.setTemp(true);
		this.dao.adiciona(t);
	}

	public void remove(Livro t) {
		this.dao.remove(t);
	}

	public void atualiza(Livro t) {
		this.dao.atualiza(t);
	}

	@Log
	public List<Livro> listaTodos() {
		return this.dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return this.dao.buscaPorId(id);
	}

	public void AtualizarLivro(Livro livro) {
		if (em == null) {
			em = new JPAUtil().getEntityManager();

		}
		try {
			em.getTransaction().begin();

			em.createQuery(
					"update Livro set quantidade = " + livro.getQuantidade() + " where titulo='" + livro.getTitulo()+"'")
					.executeUpdate();
			
			em.createQuery(
					"update Livro set temp = false where titulo='" + livro.getTitulo()+"'")
					.executeUpdate();
			em.getTransaction().commit();

		} catch (NoResultException ex) {
			// return false;
		}

	}

}
