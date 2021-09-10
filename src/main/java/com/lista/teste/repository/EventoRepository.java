
package com.lista.teste.repository;

import com.lista.teste.model.Evento;
import org.springframework.data.repository.CrudRepository;


public interface EventoRepository extends CrudRepository<Evento, String> {
    public Evento findById(long codigo);
}
