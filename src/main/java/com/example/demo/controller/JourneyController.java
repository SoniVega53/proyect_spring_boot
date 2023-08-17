package com.example.demo.controller;


import com.example.demo.UtilsTools;
import com.example.demo.model.*;
import com.example.demo.model.journey.JourneyHasPlaceModel;
import com.example.demo.model.journey.JourneyModel;
import com.example.demo.model.object.JourneyHasPlaceViewModel;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PlacesRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.journey.JourneyHasPlaceRepository;
import com.example.demo.repository.journey.JourneyRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@CrossOrigin
public class JourneyController {

    @Autowired
    private JourneyRepository repository;
    @Autowired
    private PlacesRepository repositoryPlace;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private JourneyHasPlaceRepository repositoryHas;


    private ResponseEntity<JourneyModel> getSaveJourney(String status,String description,String dateInitial,String dateEnd,int count){
        JourneyModel newBody = new JourneyModel();
        newBody.setStatus(status);
        newBody.setDescription(description);
        newBody.setDateInitial(generateDate(dateInitial));
        newBody.setDateEnd(generateDate(dateEnd));
        newBody.setCount(count);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.save(newBody));
    }

    private ResponseEntity<JourneyModel> getUpdateJourney(int id,String status,String description,String dateInitial,String dateEnd,int count){
        JourneyModel updateBody = repository.findById(id).get();
        updateBody.setStatus(status);
        updateBody.setDescription(description != null ? description: updateBody.getDescription());
        updateBody.setDateInitial(dateInitial != null ? generateDate(dateInitial): updateBody.getDateInitial());
        updateBody.setDateEnd(dateEnd != null ? generateDate(dateEnd): updateBody.getDateEnd());
        updateBody.setCount(count != -1 ? count: updateBody.getCount());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.save(updateBody));
    }

    private ResponseEntity<JourneyHasPlaceModel> getSaveJourney(int idJourney,int idPlace,String type){
        JourneyHasPlaceModel pubBody = new JourneyHasPlaceModel();
        pubBody.setJourney(repository.findById(idJourney).get());
        pubBody.setPlace(repositoryPlace.findById(idPlace).get());
        pubBody.setType(type);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repositoryHas.save(pubBody));
    }

    private ResponseEntity<JourneyHasPlaceModel> getUpdateJourney(int idJourney,int idPlace,String type){
        JourneyHasPlaceModel pubBody = repositoryHas.findAllByJourney_IdAndType(idJourney,type);
        pubBody.setPlace(repositoryPlace.findById(idPlace).get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repositoryHas.save(pubBody));
    }

    /**
     * Metodo para crear un nuevo viaje
     * @param idPlaceOri = id lugares de origen
     * @param idPlaceDes = id lugares de Destino
     */

    @PostMapping("/api/SaveJourneyHasPlace/{idPlaceOri}/{idPlaceDes}")
    public ResponseEntity<String> setSaveJourneyHasPlace(@PathVariable int idPlaceOri,@PathVariable int idPlaceDes,
                                                                           @RequestParam String status,@RequestParam String description,
                                                                           @RequestParam String dateInitial,@RequestParam String dateEnd,@RequestParam int count){
        Gson gson = new Gson();
        //JourneyHasPlaceViewModel
        if (idPlaceOri != idPlaceDes){
            if (!String.valueOf(count).contains("-") && count != 0){
                ResponseEntity<JourneyModel> journey = getSaveJourney(status.toLowerCase(),description.toLowerCase(),dateInitial,dateEnd,count);

                if (journey.getStatusCode().equals(HttpStatus.OK)){
                    ResponseEntity<JourneyHasPlaceModel> jouHasOri = getSaveJourney(Objects.requireNonNull(journey.getBody()).getId(),idPlaceOri,"ORI");
                    ResponseEntity<JourneyHasPlaceModel> jouHasDes = getSaveJourney(Objects.requireNonNull(journey.getBody()).getId(),idPlaceDes,"DES");

                    if (jouHasOri.getStatusCode().equals(HttpStatus.OK) && jouHasDes.getStatusCode().equals(HttpStatus.OK)){
                        JourneyHasPlaceViewModel body = new JourneyHasPlaceViewModel();
                        body.setJourney(journey.getBody());
                        body.setPlaceDes(jouHasDes.getBody().getPlace());
                        body.setPlaceOri(jouHasDes.getBody().getPlace());
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .header("Content-Type","application/json")
                                .body(gson.toJson(body));
                    }else{
                        repository.deleteById(journey.getBody().getId());
                    }
                }
            }else{
                String message = count == 0 ? "Cantidad no puede ser cero" :
                        "La Cantidad no puede ser Negativa";
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(message);
            }
        }else{
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("No puede seleccionar el mismo destino en el Origen");
        }


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("error");
    }

    /**
     * Metodo Para Actualizar
     * @param id = id de viajes o journey
     * @param idPlaceOri = id lugares de origen
     * @param idPlaceDes = id lugares de Destino
     */

    @PutMapping("/api/SaveJourneyHasPlace/{id}/{idPlaceOri}/{idPlaceDes}")
    public ResponseEntity<String> setUpdateJourneyHasPlace(@PathVariable int id,@PathVariable int idPlaceOri,@PathVariable int idPlaceDes,
                                                                           @RequestParam String status,@RequestParam String description,
                                                                           @RequestParam String dateInitial,@RequestParam String dateEnd,@RequestParam int count){
        Gson gson = new Gson();
        //JourneyHasPlaceViewModel
        if (idPlaceOri != idPlaceDes){
            if (!String.valueOf(count).contains("-") && count != 0){
                ResponseEntity<JourneyModel> journey = getUpdateJourney(id,status,description,dateInitial,dateEnd,count);

                if (journey.getStatusCode().equals(HttpStatus.OK)){
                    ResponseEntity<JourneyHasPlaceModel> jouHasOri = getUpdateJourney(Objects.requireNonNull(journey.getBody()).getId(),idPlaceOri,"ORI");
                    ResponseEntity<JourneyHasPlaceModel> jouHasDes = getUpdateJourney(Objects.requireNonNull(journey.getBody()).getId(),idPlaceDes,"DES");

                    if (jouHasOri.getStatusCode().equals(HttpStatus.OK) && jouHasDes.getStatusCode().equals(HttpStatus.OK)){
                        JourneyHasPlaceViewModel body = new JourneyHasPlaceViewModel();
                        body.setJourney(journey.getBody());
                        body.setPlaceDes(jouHasDes.getBody().getPlace());
                        body.setPlaceOri(jouHasDes.getBody().getPlace());
                        return ResponseEntity
                                .status(HttpStatus.OK)
                                .header("Content-Type","application/json")
                                .body(gson.toJson(body));
                    }else{
                        repository.deleteById(journey.getBody().getId());
                    }
                }
            }else{
                String message = count == 0 ? "Cantidad no puede ser cero" :
                        "La Cantidad no puede ser Negativa";
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(message);
            }
        }else{
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("No puede seleccionar el mismo destino en el Origen");
        }


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("error");
    }


    @PutMapping("/api/SaveJourneyHasPlaceStatus/{id}/{idPlaceOri}/{idPlaceDes}")
    public ResponseEntity<JourneyHasPlaceViewModel> setUpdateJourneyHasPlaceStatus(@PathVariable int id,@PathVariable int idPlaceOri,@PathVariable int idPlaceDes,
                                                                             @RequestParam String status){
        ResponseEntity<JourneyModel> journey = getUpdateJourney(id,status,null,null,null,-1);

        if (journey.getStatusCode().equals(HttpStatus.OK)){
            ResponseEntity<JourneyHasPlaceModel> jouHasOri = getUpdateJourney(Objects.requireNonNull(journey.getBody()).getId(),idPlaceOri,"ORI");
            ResponseEntity<JourneyHasPlaceModel> jouHasDes = getUpdateJourney(Objects.requireNonNull(journey.getBody()).getId(),idPlaceDes,"DES");

            if (jouHasOri.getStatusCode().equals(HttpStatus.OK) && jouHasDes.getStatusCode().equals(HttpStatus.OK)){
                JourneyHasPlaceViewModel body = new JourneyHasPlaceViewModel();
                body.setJourney(journey.getBody());
                body.setPlaceDes(jouHasDes.getBody().getPlace());
                body.setPlaceOri(jouHasDes.getBody().getPlace());
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(body);
            }else{
                repository.deleteById(journey.getBody().getId());
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }


    /**
     * @param id = id de viajes o journey
     *           trae los lugares de tipo originen y destino que este relacionado con el id del viaje
     */
    @GetMapping("/api/viewJourneyHasPlace/{id}")
    public ResponseEntity<List<JourneyHasPlaceModel>> viewJourneyBy(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repositoryHas.findAllByJourney_Id(id));
    }

    /**
     * id de viajes o journey
     * trae los lugares de tipo seleccionado con el id de viajes
     */
    @GetMapping("/api/viewJourneyHasPlace/{id}/{type}")
    public ResponseEntity<JourneyHasPlaceModel> viewJourneyBy(@PathVariable int id,@PathVariable String type){
        //TIPOS: ORI = Origen, DES = Destino
        //id = JOURNEY - viaje
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(repositoryHas.findAllByJourney_IdAndType(id,type));
    }


    /**
     * Trae todos los viajes que estan activos  y con el orden de fecha inicial e la mas reciente a la mas antigua
     * tambien trae solo el lugar de destino
     */
    @GetMapping("/api/viewJourneyHasPlace")
    public ResponseEntity<List<JourneyHasPlaceModel>> viewJourneyByMo(){
        List<JourneyModel> listJourneyOrDateStatus = repository.findAllByStatusOrderByDateInitialDesc(UtilsTools.STATUS_ACTIVE);
        List<JourneyHasPlaceModel> filterList = new ArrayList<>();

        for (JourneyModel item:listJourneyOrDateStatus) {
            JourneyHasPlaceModel ItemDes = repositoryHas.findAllByJourney_IdAndType(item.getId(), "DES");
            if (ItemDes != null)
                filterList.add(ItemDes);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(filterList);

    }

    @GetMapping("/api/viewJourneyHasPlaces")
    public ResponseEntity<String> viewJourney(){
        Gson gson = new Gson();
        try {
            String jsonInf = gson.toJson(repository.findAll());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jsonInf);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al convertir el array a JSON.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/api/viewJourneyHasPlacesAll")
    public ResponseEntity<String> viewJourneys(){
        Gson gson = new Gson();
        try {
            List<JourneyHasPlaceViewModel> filterHas = new ArrayList<>();
            for (JourneyModel item:repository.findAll()) {
                filterHas.add(new JourneyHasPlaceViewModel(item,
                        repositoryHas.findAllByJourney_IdAndType(item.getId(), "ORI").getPlace(),
                        repositoryHas.findAllByJourney_IdAndType(item.getId(), "DES").getPlace()));
            }
            String jsonInf = gson.toJson(filterHas);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jsonInf);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al convertir el array a JSON.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/viewJourneyHasPlaces/{id}")
    public ResponseEntity<JourneyHasPlaceViewModel> viewJourneyById(@PathVariable(name = "id") Integer id){

        JourneyHasPlaceViewModel filterHas;
        JourneyModel item = repository.findById(id).get();
        if (item != null){
            filterHas = new JourneyHasPlaceViewModel(item,
                    repositoryHas.findAllByJourney_IdAndType(item.getId(), "ORI").getPlace(),
                    repositoryHas.findAllByJourney_IdAndType(item.getId(), "DES").getPlace());


            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(filterHas);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping( "/api/deleteJourneyHasPlace/{id}")
    public ResponseEntity<String> viewPlaceDelete(@PathVariable(name = "id") Integer id){

        if (!usedTable(id)){
            ResponseEntity<List<JourneyHasPlaceModel>> jourList = viewJourneyBy(id);
            for (JourneyHasPlaceModel item : jourList.getBody()){
                repositoryHas.deleteById(item.getId());
            }
            repository.deleteById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Se elimino correctamente");
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se puede eliminar, El Viaje ya que se esta utilizando");
        }

    }

    private Date generateDate(String dateSelect){
        SimpleDateFormat sdf = new SimpleDateFormat(UtilsTools.FORMAT_DATE);
        try {
            return sdf.parse(dateSelect);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean usedTable(int idJour) {
        List<ReservationModel> filterRes = reservationRepository.findAll().stream().filter(
                publicityModel -> publicityModel.getJourney().getId().equals(idJour)
        ).toList();

        List<CommentModel> filterComment = commentRepository.findAll().stream().filter(
                publicityModel -> publicityModel.getJourney().getId().equals(idJour)
        ).toList();

        return filterRes.size() > 0 || filterComment.size() > 0;
    }
}
