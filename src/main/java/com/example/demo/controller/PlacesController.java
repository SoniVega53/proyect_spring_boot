package com.example.demo.controller;


import com.example.demo.UtilsTools;
import com.example.demo.model.*;
import com.example.demo.model.journey.JourneyHasPlaceModel;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PlacesRepository;
import com.example.demo.repository.journey.JourneyHasPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin
public class PlacesController {

    @Autowired
    private ImageRepository repositoryImage;

    @Autowired
    private PlacesRepository repository;

    @Autowired
    private JourneyHasPlaceRepository repositoryHas;

    /**
     * @param id = de la imagen a seleccionar
     */
    @PostMapping("/api/placeSave/{id}")
    public ResponseEntity<PlacesModel> setSave(@PathVariable int id, @RequestParam String description,
                                                       @RequestParam String name){

        ImageModel image = repositoryImage.findById(id).orElse(null);
        if (image != null ) {
            PlacesModel pubBody = new PlacesModel();
            pubBody.setNamePlace(name);
            pubBody.setDescription(description);
            pubBody.setIdImage(image);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(repository.save(pubBody));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(null);
        }

    }

    /**
     * @param idPlace = del lugar
     * @param idImage = de la imagen
     */
    @PutMapping("/api/placeSave/{idPlace}/{idImage}")
    @ResponseBody
    public ResponseEntity<PlacesModel> setUpdate(@PathVariable int idImage,@PathVariable int idPlace,
                                                 @RequestParam String description,
                                                 @RequestParam String name){
        ImageModel image = repositoryImage.findById(idImage).orElse(null);
        PlacesModel pubBody = repository.findById(idPlace).get();
        if (image != null) {
            pubBody.setNamePlace(name);
            pubBody.setDescription(description);
            pubBody.setIdImage(image);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(repository.save(pubBody));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .header("Content-Type","application/json")
                    .body(pubBody);
        }

    }


    @GetMapping("/api/viewPlace")
    public ResponseEntity<List<PlacesModel>> viewPlaceAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.findAll());
    }

    @GetMapping("/api/viewPlace/{id}")
    public ResponseEntity<PlacesModel> viewPlaceById(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(repository.findById(id).get());
    }

    @GetMapping( "/api/deletePlace/{id}")
    public ResponseEntity<String> viewPlaceDelete(@PathVariable(name = "id") Integer id){
        if (!usedTable(id)){
            repository.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Se elimino correctamente");
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se puede eliminar, El lugar ya que se esta utilizando");
        }
    }

    public boolean usedTable(int place) {
        List<JourneyHasPlaceModel> filter1 = repositoryHas.findAll().stream().filter(
                publicityModel -> publicityModel.getPlace().getId().equals(place)
        ).toList();

        return filter1.size() > 0 ;
    }
}
