package com.example.desafiocalcmeback.controller;

import com.example.desafiocalcmeback.model.Dados;
import com.example.desafiocalcmeback.repository.DadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DadosController {
    @Autowired
    DadosRepository dadosRepository;

    @GetMapping("/dados")
    public ResponseEntity<List<Dados>> getAllDados(@RequestParam(required = false) String nome){
        try {
            List<Dados> dadosList = new ArrayList<Dados>();

            if (nome == null){
                dadosRepository.findAll().forEach(dadosList::add);
            }else {
                dadosRepository.findAllByNome(nome).forEach(dadosList::add);
            }
            if (dadosList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(dadosList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dados/{id}")
    public ResponseEntity<Dados> getDadoById(@PathVariable("id") String id){
        Optional<Dados> dadosOptional = dadosRepository.findById(id);

        if (dadosOptional.isPresent()){
            return new ResponseEntity<>(dadosOptional.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/dados")
    public ResponseEntity<Dados> createDado(@RequestBody Dados dados){
        try{
            Dados _dados = dadosRepository.save(new Dados(dados.getNome(), dados.getEmail(), dados.getTelefone()));
            return new ResponseEntity<>(_dados, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/dados/{id}")
    public ResponseEntity<Dados> updadeDado(@PathVariable("id") String id, @RequestBody Dados dados){
        Optional<Dados> dadosOptional = dadosRepository.findById(id);

        if (dadosOptional.isPresent()){
            Dados _dados = dadosOptional.get();
            _dados.setNome(dados.getNome());
            _dados.setEmail(dados.getEmail());
            _dados.setTelefone(dados.getTelefone());
            return new ResponseEntity<>(dadosRepository.save(_dados), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/dados/{id}")
    public ResponseEntity<HttpStatus> deleteDado(@PathVariable("id") String id){
        try{
            dadosRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
