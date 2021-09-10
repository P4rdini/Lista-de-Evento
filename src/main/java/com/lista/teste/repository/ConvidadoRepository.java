package com.lista.teste.repository;

import com.lista.teste.model.Convidado;
import com.lista.teste.model.Evento;
import org.springframework.data.repository.CrudRepository;


public interface ConvidadoRepository extends CrudRepository<Convidado, String>  {

    public Iterable<Convidado> findAllByEvento(Evento evento);

    public Convidado findByRg(String rg);

    
    
    
}
