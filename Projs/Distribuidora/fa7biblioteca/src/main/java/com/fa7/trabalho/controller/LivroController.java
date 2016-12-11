package com.fa7.trabalho.controller;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fa7.trabalho.model.Livro;
import com.fa7.trabalho.repository.LivroRepository;

@Controller
@RequestMapping(value="/livro")
public class LivroController {
	@Autowired
	LivroRepository livros;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@RequestMapping
	public ModelAndView listaLivros() {
		List<Livro> todosLivros = livros.findAll();
		return new ModelAndView("livros","todosLivros",todosLivros);
	}
	
	@RequestMapping(value="/novo", method=RequestMethod.POST)
	public String finalizaLivro(Livro livro) {
		System.out.println("Nova quantidade: " + livro.getQuantidade());
		jmsTemplate.send("out-queue", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				System.out.println("mandando mensagem...");
				return session.createTextMessage(livro.getNome()+":"+livro.getQuantidade());
			}
		});
		livros.save(livro);
		
		return "redirect:/livro";
	}
	
	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Livro livro) {
		ModelAndView mv = new ModelAndView("editLivro"); 
		mv.addObject(livro);
		return mv;
	}
}
