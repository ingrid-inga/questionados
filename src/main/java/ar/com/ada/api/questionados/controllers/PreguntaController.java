package ar.com.ada.api.questionados.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.questionados.entities.*;
import ar.com.ada.api.questionados.models.request.InfoPreguntaNueva;
import ar.com.ada.api.questionados.models.response.GenericResponse;
import ar.com.ada.api.questionados.services.*;

@RestController
public class PreguntaController {
    @Autowired
    private PreguntaService service;

    @GetMapping("/preguntas")
    public ResponseEntity<List<Pregunta>> traerPreguntas() {

        return ResponseEntity.ok(service.traerPreguntas());
    }

    @GetMapping("/preguntas/{id}")
    public ResponseEntity <Pregunta> buscarPreguntaPorId(@PathVariable Integer id){

        return ResponseEntity.ok(service.buscarPreguntaPorId(id));
    }

    @PostMapping ("/preguntas")
    public ResponseEntity<?> crearPregunta(@RequestBody InfoPreguntaNueva preguntaNueva){

        GenericResponse respuesta = new GenericResponse();
        Pregunta pregunta = service.crearPregunta(preguntaNueva.enunciado, preguntaNueva.categoriaId, preguntaNueva.opciones);
        respuesta.isOk = true;
        respuesta.id = pregunta.getPreguntaId();
        respuesta.message = "La pregunta fue creada con exito";

        return ResponseEntity.ok(preguntaNueva);

    }

    //Detele/empleados/{id} --> Da de baja un empleado poniendo el campo estado en "baja"
    // y la fecha de baja que sea el dia actual.
   /* @DeleteMapping("/preguntas/{id}")
    public ResponseEntity<?> bajaEmpleada(@PathVariable Integer id){

        service.bajaPreguntaPorId(id);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "La pregunta fue dada de baja con exito";

        return ResponseEntity.ok(respuesta);

    }

    //Get /empleados/categorias/{catId} --> Obtiene la lista de empleados de una categoria.
    @GetMapping("/preguntas/categorias/{catId}")
    public ResponseEntity<List<Pregunta>> obtenerPreguntasPorCategoria(@PathVariable Integer catId){
        
        List<Pregunta> Preguntas = service.traerPreguntaPorCategoria(catId);
        return ResponseEntity.ok(preguntas);
    }

    @PutMapping("/preguntas/{id}/respuestas")
    public ResponseEntity<GenericResponse> modificarSueldo(@PathVariable Integer id, @RequestBody SueldoNuevoEmpleada sueldoNuevoInfo){

        //1) buscar la empleada
        Pregunta pregunta = service.buscarPregunta(id);
        //2) setear su nuevo sueldo
        pregunta.setRespuesta(respuestaNuevoInfo.respuestaNuevo);
        //3) guardarlo  en la base de datos
        service.guardar(pregunta);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "Respuesta actualizada";

        return ResponseEntity.ok(respuesta);
    }*/
}
