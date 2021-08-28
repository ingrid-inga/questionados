package ar.com.ada.api.questionados.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.questionados.entities.*;
import ar.com.ada.api.questionados.repos.PreguntaRepository;

@Service
public class PreguntaService {

    @Autowired
    PreguntaRepository repo;
    
    @Autowired
    CategoriaService categoriaService;


    public Pregunta buscarPreguntaPorId(Integer preguntaId) {

        Optional<Pregunta> resultado = repo.findById(preguntaId);

        if (resultado.isPresent())
            return resultado.get();

        return null;
    }


    public List<Pregunta> traerPreguntas() {
        return repo.findAll();
    }


    public Pregunta crearPregunta(String enunciado, Integer categoriaId, List<Respuesta> opciones ) {
        
        Pregunta pregunta = new Pregunta();
        pregunta.setEnunciado(enunciado);

        Categoria categoria = categoriaService.buscarCategoria(categoriaId);

        pregunta.setCategoria(categoria);
      
        for (Respuesta respuesta: opciones) {
            pregunta.agregarRespuesta(respuesta);
        }
        
        repo.save(pregunta);
        return pregunta;
    }


    public void eliminarPreguntaPorId(Integer id) {
        Pregunta pregunta = repo.findByPreguntaId(id);
        repo.delete  (pregunta);
    }
}


