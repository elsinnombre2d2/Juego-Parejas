package com.example.backend.controller;

import com.example.backend.model.Score;
import com.example.backend.service.ScoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
@Tag(name = "Score", description = "API for managing scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    @Operation(summary = "Get best scores", description = "Gets best 10 scores orderer by moves")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Best scores list successfully obtained")
           
    })
    @GetMapping("/top-scores")
    public ResponseEntity<List<Score>> getTopScores() {
        List<Score> topScores = scoreService.getTopScores();
        return new ResponseEntity<>(topScores, HttpStatus.OK);
    }

    @Operation(summary = "Get the position of a score by ID", description = "Given an id, retrieves the position of the score ordered by moves")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the position"),
        @ApiResponse(responseCode = "404", description = "Score not found"),
    })
    @GetMapping("/{id}/position")
    public ResponseEntity<?> getPosition(@PathVariable @Valid Long id) {
        Score score = scoreService.findById(id);
        if (score != null) {
           
        int position = scoreService.getPosition(id);
        Map<String, Integer> response = Collections.singletonMap("position", position);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all scores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of scores"),
    })
    @GetMapping
    public ResponseEntity<List<Score>> getAllScores() {
        List<Score> scores = scoreService.findAll();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }


    @Operation(summary = "Get a score by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the score"),
        @ApiResponse(responseCode = "404", description = "Score not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Score> getScoreById(@PathVariable @Valid @Parameter(description = "ID of the score to be retrieved") Long id) {
        Score score = scoreService.findById(id);
        if (score != null) {
            return new ResponseEntity<>(score, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    
    @Operation(summary = "Create a new score")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created the score"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
    })
    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<Score> createScore(@RequestBody @Valid Score score) {
        Score createdScore=scoreService.insert(score);
        return new ResponseEntity<>(createdScore, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a score by ID", description = "Updates an existing score record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated score"),
            @ApiResponse(responseCode = "400", description = "Bad request due to validation or other reasons"),
            @ApiResponse(responseCode = "404", description = "There is not score with the ID submitted"),
           
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "ID of score to be updated", example = "1") @PathVariable Long id,
            @Valid @RequestBody Score updatedScore) {
                try{
                    if(scoreService.findById(id) !=null){
                    updatedScore.setId(id);
                    scoreService.update(updatedScore);
                    return ResponseEntity.ok(updatedScore);
                    }
                    else{
                        return ResponseEntity.notFound().build();
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating score");
                }

    }

    @Operation(summary = "Delete a score by ID", description = "Deletes an existing score record by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Score deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Score not found"),
            
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid @Parameter(description = "ID of the score to be deleted")  Long id) {
        try {
            // Verifica si el score con el ID especificado existe
            if (scoreService.findById(id) == null) {
                return ResponseEntity.notFound().build();
                //return new ResponseEntity<> ("Score with ID " + id + " not found", HttpStatus.NOT_FOUND);
            }
            // Si existe, procede con la eliminación
            scoreService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Loggea el error y retorna un mensaje de error interno del servidor
             return ResponseEntity.internalServerError().build();
        }
    }
    /*
     * // Manejo personalizado para excepciones de validación
     * 
     * @ExceptionHandler(MethodArgumentNotValidException.class)
     * 
     * @ResponseStatus(HttpStatus.BAD_REQUEST)
     * public ResponseEntity<?>
     * handleValidationExceptions(MethodArgumentNotValidException ex) {
     * return
     * ResponseEntity.badRequest().body(ex.getBindingResult().getAllErrors());
     * }
     */
}
