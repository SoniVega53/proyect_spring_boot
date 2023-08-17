package com.example.demo.controller;

import com.example.demo.UtilsTools;
import com.example.demo.model.CommentModel;
import com.example.demo.model.ReservationModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.journey.JourneyModel;
import com.example.demo.repository.CommentRepository;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.journey.JourneyRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@CrossOrigin
@EnableScheduling
public class CommentController {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/comment/{idUser}/{idJourney}")
        public ResponseEntity<String> setSave(@PathVariable int idUser, @PathVariable int idJourney,@RequestParam String description){

        Gson gson = new Gson();

        UserModel user = userRepository.findById(idUser).orElse(null);
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);

        if (user != null && journey != null ) {
            CommentModel comment = new CommentModel(parseDate(),description,user,journey);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(gson.toJson(repository.save(comment)));
        } else {
            String message = user == null ?  "Usuario no encontrado": "Viaje no encontrado";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }
    }

    @PostMapping("/api/comment/{id}/{idUser}/{idJourney}")
    public ResponseEntity<String> setUpdate(@PathVariable int id,@PathVariable int idUser, @PathVariable int idJourney,@RequestParam String description){

        Gson gson = new Gson();

        UserModel user = userRepository.findById(idUser).orElse(null);
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);

        if (user != null && journey != null ) {
            CommentModel comment = repository.findById(id).orElse(null);
            if (comment != null){
                comment.setDate(parseDate());
                comment.setDescription(description);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Content-Type","application/json")
                        .body(gson.toJson(repository.save(comment)));
            }else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Commentario no encontrado");
            }

        } else {
            String message = user == null ?  "Usuario no encontrado": "Viaje no encontrado";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }
    }

    @GetMapping("/api/comments/{idJourney}")
    public ResponseEntity<String> viewCommentsAllByJourney(@PathVariable int idJourney){
        Gson gson = new Gson();
        JourneyModel journey = journeyRepository.findById(idJourney).orElse(null);
        if (journey != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(gson.toJson(repository.findAllByJourneyOrderByDateDesc(journey)));
        }else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Viaje no encontrado");
        }

    }

    @GetMapping("/api/comments")
    public ResponseEntity<String> viewCommentsAll(){
        Gson gson = new Gson();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(gson.toJson(repository.findAll()));
    }
    private Date parseDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(UtilsTools.FORMAT_DATETIME);
        String formattedDateTime = currentDateTime.format(formatter);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(UtilsTools.FORMAT_DATETIME);
            return sdf.parse(formattedDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
