package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.dao.ReservaDAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Reserva;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class ReservaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Reserva reserva = new Reserva();

	private Integer livroId;

	private List<Livro> livros;

	@Inject
	private ReservaDAO reservaDao;

	@Inject
	private FacesContext context;

	@Inject
	private LivroDao livroDao;

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();
		}
		return livros;
	}

	@Transacional
	public void gravarReserva() {
		Livro livro = this.livroDao.buscaPorId(this.livroId);
		this.reserva.adicionarLivro(livro);
	}

	@Transacional
	public void gravar() {
		// System.out.println("Gravando livro " + this.livro.getTitulo());

		if (reserva.getLivros().isEmpty()) {
			context.addMessage("reserva", new FacesMessage("Reserva deve ter pelo menos um livro."));
			return;
		}

		if (this.reserva.getId() == null) {
			this.reservaDao.adiciona(this.reserva);
			this.livros = this.livroDao.listaTodos();
			context.addMessage("reserva", new FacesMessage("Reserva realizada com suceso."));
		} else {
			this.reservaDao.atualiza(this.reserva);
		}

		this.reserva = new Reserva();
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		this.livroDao.remove(livro);
		this.livros = this.livroDao.listaTodos();
	}

	@Transacional
	public void removerReservaDoLivro(Livro livroaux) {
		this.reserva.removerLivro(livroaux);
	}

	public String formReserva() {
		return "reserva?faces-redirect=true";
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	// public void InserirNaFila(Livro livro) throws Exception {
	// InitialContext context = new InitialContext();
	// ConnectionFactory factory = (ConnectionFactory)
	// context.lookup("ConnectionFactory");
	// Connection connection = factory.createConnection();
	// connection.start();
	//
	// Session session = connection.createSession(false,
	// Session.AUTO_ACKNOWLEDGE);
	//
	// Destination fila = (Destination) context.lookup("financeiro");
	//
	// MessageProducer producer = session.createProducer(fila);
	//
	// Message message = session.createTextMessage("<pedido><id>" + 1 +
	// "</id></pedido>");
	// producer.send(message);
	//
	// // new Scanner(System.in).nextLine();
	//
	// session.close();
	// connection.close();
	// context.close();
	// }
}
