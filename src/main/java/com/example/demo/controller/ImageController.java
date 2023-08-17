package com.example.demo.controller;



import com.example.demo.model.ImageModel;
import com.example.demo.model.PlacesModel;
import com.example.demo.model.PublicityModel;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PlacesRepository;
import com.example.demo.repository.PublicityRepository;
import com.example.demo.UtilsTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Controller
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private PublicityRepository repositoryPublicity;

    @Autowired
    private PlacesRepository repositoryPlaces;

    @PostMapping( "/api/SaveImage/{type}")
    @ResponseBody
    public ResponseEntity<ImageModel> setSaveImageBase64(@RequestParam("file") MultipartFile file,@PathVariable String type){
        // Get the file and save it somewhere
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
            String encodedString = Base64.getEncoder().encodeToString(bytes);
            String base = "data:image/png;base64,";
            ImageModel foto = new ImageModel(file.getOriginalFilename(), base + encodedString,type);
            repository.save(foto);
            return ResponseEntity.ok(foto);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping( "/api/SaveImageBase64/{type}")
    public ResponseEntity<ImageModel> setSaveImageBase64Params(@PathVariable String type,@RequestParam String name,@RequestParam String base64){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.save(new ImageModel(name, base64,type)));
    }


    @GetMapping("/api/viewImage/{id}")
    public ResponseEntity<ImageModel> viewImageByIdBase64(@PathVariable int id) {
        Optional<ImageModel> imagenOptional = repository.findById(id);
        if (imagenOptional.isPresent()) {
            ImageModel imagen = imagenOptional.get();
            return ResponseEntity.ok(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/viewImage")
    public ResponseEntity<List<ImageModel>> viewImageBase64() {
        Optional<List<ImageModel>> imagenOptional = Optional.of(repository.findAll());
        if (imagenOptional.isPresent()) {
            List<ImageModel> imagen = imagenOptional.get();
            return ResponseEntity.ok(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/viewImageType/{type}")
    public ResponseEntity<List<ImageModel>> viewImageBase64Type(@PathVariable String type) {
        Optional<List<ImageModel>> imagenOptional = Optional.of(repository.findByType(type));
        if (imagenOptional.isPresent()) {
            List<ImageModel> imagen = imagenOptional.get();
            return ResponseEntity.ok(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping( "/api/deleteImage/{id}")
    public ResponseEntity<HttpStatusCode> deleteImage(@PathVariable(name = "id") Integer id){
        if (!usedTable(id)){
            repository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }else{
            return ResponseEntity.ok(HttpStatus.EXPECTATION_FAILED);
        }
    }

    public boolean usedTable(int idImg) {
        List<PublicityModel> filterPubli = repositoryPublicity.findAll().stream().filter(
                publicityModel -> publicityModel.getIdImage().getId().equals(idImg)
        ).toList();

        List<PlacesModel> filterPlac = repositoryPlaces.findAll().stream().filter(
                publicityModel -> publicityModel.getIdImage().getId().equals(idImg)
        ).toList();

        return filterPubli.size() > 0 || filterPlac.size() > 0;
    }

}
