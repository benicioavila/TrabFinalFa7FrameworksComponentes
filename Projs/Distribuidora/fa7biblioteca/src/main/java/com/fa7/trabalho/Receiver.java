package com.fa7.trabalho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fa7.trabalho.model.Livro;
import com.fa7.trabalho.repository.LivroRepository;

@Component
public class Receiver {
	private Livro livro;
	
	@Autowired
	LivroRepository livros;

    @JmsListener(destination = "in-queue", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        System.out.println("Nova mensagem recebida: " + message);
        String[] livrosPedido = message.split("-");
        for (String string : livrosPedido) {
        	String[] livroPedido = string.split(":");
			livro = livros.findByNome(livroPedido[0]);
			if (livro != null) {
				livro.setQuantidade(livro.getQuantidade() + Integer.parseInt(livroPedido[1]));	
			} else {
				livro = new Livro();
				livro.setNome(livroPedido[0]);
				livro.setQuantidade(Integer.parseInt(livroPedido[1]));
			}
			livros.save(livro);
		}
    }

}