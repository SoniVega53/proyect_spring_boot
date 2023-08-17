package com.example.demo.controller;


import com.example.demo.UtilsTools;
import com.example.demo.model.ImageModel;
import com.example.demo.model.object.PublicityDateCorruselModel;
import com.example.demo.model.PublicityModel;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.PublicityRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;


@Controller
@CrossOrigin
@EnableScheduling
public class PublicityController {

    @Autowired
    private PublicityRepository repository;

    @Autowired
    private ImageRepository repositoryImage;

    @PostMapping("/api/publicitySave/{id}")
    public ResponseEntity<PublicityModel> setSave(@PathVariable int id,@RequestParam String description,
                                                          @RequestParam String dateInit,@RequestParam String dateEnd,
                                                          @RequestParam String hourInit,@RequestParam String hourEnd,
                                                          @RequestParam String type){

        ImageModel image = repositoryImage.findById(id).orElse(null);
        if (image != null ) {
            PublicityModel pubBody = new PublicityModel(
                    description,generateDate(dateInit),
                    generateDate(dateEnd),type,
                    generateTime(hourInit),generateTime(hourEnd),image);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(repository.save(pubBody));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(null);
        }
    }

    @PutMapping("/api/publicitySave/{idPub}/{idImage}")
    @ResponseBody
    public ResponseEntity<PublicityModel> setUpdate(@PathVariable int idImage,@PathVariable int idPub,
                                                            @RequestParam String description,@RequestParam String dateInit,
                                                            @RequestParam String dateEnd,@RequestParam String hourInit,
                                                            @RequestParam String hourEnd,@RequestParam String type){
        ImageModel image = repositoryImage.findById(idImage).orElse(null);
        PublicityModel pubUp = repository.findById(idPub).get();
        if (image != null) {
            pubUp.setIdImage(image);
            pubUp.setDescription(description);
            pubUp.setDateInitial(generateDate(dateInit));
            pubUp.setDateEnd(generateDate(dateEnd));
            pubUp.setHourInitial(generateTime(hourInit));
            pubUp.setHourEnd(generateTime(hourEnd));
            pubUp.setType(type);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(repository.save(pubUp));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .header("Content-Type","application/json")
                    .body(pubUp);
        }
    }

    @GetMapping("/api/viewPublicity")
    public ResponseEntity<List<PublicityModel>> viewPublicityAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.findAll());
    }

    @GetMapping("/api/viewPublicity/{id}")
    public ResponseEntity<PublicityModel> viewPublicityById(@PathVariable int id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(repository.findById(id).get());
    }

    /**
     * Trae toda las publicidades segun esten en el rango de la fecha actual
     * @return
     */

    @GetMapping("/api/viewPublicityDate")
    @Scheduled(cron = "0 * * * * *")
    public ResponseEntity<List<PublicityModel>> viewPublicityAllDate(){
        LocalDateTime dateAc = LocalDateTime.now();
        List<PublicityModel> listFilter = new ArrayList<>();
        List<PublicityModel> listPub = repository.findAll();


        for (PublicityModel item:listPub) {
            Calendar dateInit = UtilsTools.getDateForCalendar(item.getDateInitial());
            Calendar timeInit = UtilsTools.getDateForCalendar(item.getHourInitial());

            Calendar dateEnd = UtilsTools.getDateForCalendar(item.getDateEnd());
            Calendar timeEnd = UtilsTools.getDateForCalendar(item.getHourEnd());

            LocalDateTime dateInitLocal = LocalDateTime.of(dateInit.get(Calendar.YEAR), dateInit.get(Calendar.MONTH) + 1, dateInit.get(Calendar.DAY_OF_MONTH),
                    timeInit.get(Calendar.HOUR_OF_DAY), timeInit.get(Calendar.MINUTE));

            LocalDateTime dateEndLocal = LocalDateTime.of(dateEnd.get(Calendar.YEAR), dateEnd.get(Calendar.MONTH) + 1, dateEnd.get(Calendar.DAY_OF_MONTH),
                    timeEnd.get(Calendar.HOUR_OF_DAY), timeEnd.get(Calendar.MINUTE));

            if (dateAc.isAfter(dateInitLocal) && dateAc.isBefore(dateEndLocal)) {
                listFilter.add(item);
            }

        }


        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(listFilter);
    }

    /**
     * Trae las publicidades segun el index o posicion en el array de publicidades que tengan el rango de la fecha inicial y la final
     * @param index
     * @return
     */

    @GetMapping("/api/viewPublicityDate/{index}")
    public ResponseEntity<PublicityDateCorruselModel> viewPublicityAllDateCa(@PathVariable int index){
        List<PublicityModel> listFilter = new ArrayList<>(Objects.requireNonNull(viewPublicityAllDate().getBody()));
        PublicityDateCorruselModel body = new PublicityDateCorruselModel();
        body.setCount(listFilter.size() - 1);

        if (index <= listFilter.size() - 1){
            body.setPublicitySelect(listFilter.get(index));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("Content-Type","application/json")
                    .body(body);
        }else if (listFilter.size() - 1 == -1){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header("Content-Type","application/json")
                    .body(body);
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type","application/json")
                    .body(body);
        }

    }


    @GetMapping( "/api/deletePublicity/{id}")
    public ResponseEntity<String> viewPublicityDelete(@PathVariable(name = "id") Integer id){
        repository.deleteById(id);
        return ResponseEntity.ok("Se elimino correctamente");
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

    private Time generateTime(String timeSelect){
        SimpleDateFormat sdf = new SimpleDateFormat(UtilsTools.FORMAT_TIME);
        try {
            return new Time(sdf.parse(timeSelect).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
