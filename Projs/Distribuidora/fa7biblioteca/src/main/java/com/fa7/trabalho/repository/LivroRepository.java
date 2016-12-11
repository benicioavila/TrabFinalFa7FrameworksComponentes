package com.fa7.trabalho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa7.trabalho.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	Livro findByNome(String nome);
}
