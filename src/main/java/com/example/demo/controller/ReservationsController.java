package com.example.demo.controller;


import com.example.demo.UtilsTools;
import com.example.demo.model.*;
import com.example.demo.model.journey.JourneyModel;
import com.example.demo.model.object.PublicityDateCorruselModel;
import com.example.demo.model.object.ReservationViewModel;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PublicityRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.journey.JourneyHasPlaceRepository;
import com.example.demo.repository.journey.JourneyRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@CrossOrigin
@EnableScheduling
public class ReservationsController {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JourneyHasPlaceRepository repositoryHas;

    @PostMapping("/api/reservation/{idUser}/{idJourney}")
    public ResponseEntity<String> setSave(@PathVariable int idUser,@PathVariable int idJourney,
                                                  @RequestParam int countPeople,@RequestParam String observation){

        Gson gson = new Gson();

        UserModel user = userRepository.findById(idUser).orElse(null);
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);

        if (user != null && journey != null ) {
        	if(journey.getStatus().equalsIgnoreCase("activo")) {
        		if (journey.getCount() >= countPeople && !isNeg(countPeople)){
                    ReservationModel reservation = new ReservationModel(countPeople,observation.toLowerCase(),"activo",user,journey);

                    getUpdateJourneyCount(idJourney,(journey.getCount() - countPeople));

                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .header("Content-Type","application/json")
                            .body(gson.toJson(repository.save(reservation)));
                }else {
                    String message = isNeg(countPeople) ? "No puede Ingresar números negativos" :
                            "No puede agregar esa cantidad de personas ya que sobre pasa el limite permitido";
                    return ResponseEntity
                            .status(HttpStatus.CONFLICT)
                            .body(message);
                }

        	}else {
        		return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Este Viaje ya no esta Activo");
        	}
            

        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Usuario o Viaje no encontrado");
        }
    }


    @PostMapping("/api/reservation/{id}/{idUser}/{idJourney}")
    public ResponseEntity<String> setUpdate(@PathVariable int id,@PathVariable int idUser,@PathVariable int idJourney,
                                          @RequestParam int countPeople,@RequestParam String observation, @RequestParam String status){
        Gson gson = new Gson();

        UserModel user = userRepository.findById(idUser).orElse(null);
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);

        if (user != null && journey != null ) {
        	if(journey.getStatus().equalsIgnoreCase("activo")) {
        		 ReservationModel reservation = repository.findById(id).get();
                 int countUpdate =  reservation.getCountPeople() - countPeople;
                 boolean isPermit = !isNeg(countUpdate) ? true : journey.getCount() >= resNeg(countUpdate) ? true : false;

                 if (isPermit && !isNeg(countPeople)){
                     reservation.setCountPeople(countPeople);
                     reservation.setObservations(observation);
                     reservation.setStatus(status);
                     reservation.setUser(user);
                     reservation.setJourney(journey);

                     getUpdateJourneyCount(idJourney,(journey.getCount() + countUpdate));
                     return ResponseEntity
                             .status(HttpStatus.OK)
                             .header("Content-Type","application/json")
                             .body(gson.toJson(repository.save(reservation)));
                 }else {
                     String message = isNeg(countPeople) ? "No puede Ingresar números negativos" :
                             "No puede agregar esa cantidad de personas ya que sobre pasa el limite permitido";
                     return ResponseEntity
                             .status(HttpStatus.CONFLICT)
                             .body(message);
                 }
        	}else {
        		return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Este Viaje ya no esta Activo");
        	}


        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Usuario o Viaje no encontrado");
        }
    }

    @GetMapping("/api/reservation/{id}")
    public ResponseEntity<String> setCancel(@PathVariable int id){
        ReservationModel reservation = repository.findById(id).orElse(null);

        if (reservation != null) {
            getUpdateJourneyCount(reservation.getJourney().getId(),(reservation.getJourney().getCount() + reservation.getCountPeople()));
            repository.deleteById(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Se Cancelo correctamente");
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Reservacion no encontrada");
        }
    }

    @GetMapping("/api/reservationsUser/{idUser}")
    public ResponseEntity<String> viewReservationsAll(@PathVariable int idUser){
        Gson gson = new Gson();
        UserModel user = userRepository.findById(idUser).orElse(null);

        if (user != null){
            List<ReservationViewModel> filterRes = new ArrayList<>();
            for (ReservationModel item:repository.findAllByUser(user)) {
                ReservationViewModel reser = new ReservationViewModel();
                reser.setId(item.getId());
                reser.setPlace(repositoryHas.findAllByJourney_IdAndType(item.getJourney().getId(),"DES").getPlace());
                reser.setObservations(item.getObservations());
                reser.setJourney(item.getJourney());
                reser.setCountPeople(item.getCountPeople());
                filterRes.add(reser);
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(gson.toJson(filterRes));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Usuario no encontrado");
        }

    }

    @GetMapping("/api/reservations/{idJourney}")
    public ResponseEntity<String> viewReservationsAllJourney(@PathVariable int idJourney){
        Gson gson = new Gson();
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);

        if (journey != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(gson.toJson(repository.findAllByJourney(journey)));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Viaje no encontrado");
        }

    }


    @GetMapping("/api/reservations/{idUser}/{idJourney}")
    public ResponseEntity<String> viewReservationsAllJourney(@PathVariable int idUser,@PathVariable int idJourney){
        Gson gson = new Gson();
        UserModel user = userRepository.findById(idUser).orElse(null);
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);

        if (user != null && journey != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(gson.toJson(repository.findAllByUserAndJourney(user,journey)));
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Usuario no encontrado");
        }

    }

    @GetMapping("/api/reservations")
    public ResponseEntity<String> viewReservationsAll(){
        Gson gson = new Gson();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gson.toJson(repository.findAll()));
    }

    private int resNeg(int num){
        return Integer.parseInt(String.valueOf(num).contains("-") ? String.valueOf(num).replace("-","") : String.valueOf(num));
    }

    private boolean isNeg(int num){
        return String.valueOf(num).contains("-") ? true : false;
    }

    public void getUpdateJourneyCount(int id, int count){
        JourneyModel updateBody = journeyRepository.findById(id).get();
        updateBody.setCount(count);
        journeyRepository.save(updateBody);
    }
}
