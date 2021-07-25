package com.example.desafiocalcmeback.repository;

import com.example.desafiocalcmeback.model.Dados;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DadosRepository extends MongoRepository<Dados, String> {
    List<Dados> findAllByNome(String nome);
}
