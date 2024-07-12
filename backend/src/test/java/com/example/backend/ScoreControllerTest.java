package com.example.backend;

import com.example.backend.controller.ScoreController;
import com.example.backend.model.Score;
import com.example.backend.service.ScoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ScoreController.class)
public class ScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreService scoreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateScore() throws Exception {
        // Mock data setup
        Score newScore = new Score(null, "Player1", "TeamA", 100, 10);
        Score savedScore = new Score(1L, "Player1", "TeamA", 100, 10);
        // Simulate the service layer saving the score
        Mockito.when(scoreService.insert(any(Score.class))).thenReturn(savedScore);

        // Perform POST request
        mockMvc.perform(post("/api/scores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newScore)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.userName").value(savedScore.getUserName()))
                .andExpect(jsonPath("$.team").value(savedScore.getTeam()))
                .andExpect(jsonPath("$.moves").value(savedScore.getMoves()))
                .andExpect(jsonPath("$.time").value(savedScore.getTime()));
    }

    @Test
    public void testCreateScoreInvalidInput() throws Exception {
        // Create an invalid score object
        Score invalidScore = new Score(null, "", "TeamA", 100, 10); // Invalid score with empty userName

        // Perform the POST request to create the score
        mockMvc.perform(post("/api/scores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidScore)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateScoreMissingUserName() throws Exception {
        // Create a score object without userName (invalid input)
        Score scoreWithoutUserName = new Score(null, null, "TeamA", 100, 10);

        // Perform the POST request to create the score
        mockMvc.perform(post("/api/scores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(scoreWithoutUserName)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetScoreByIdExistingScore() throws Exception {
        // Arrange
        Long scoreId = 1L;
        Score mockScore = new Score(scoreId, "Player1", "TeamA", 15, 100); // Crear un score ficticio para el test

        // Define el comportamiento del servicio mock para devolver el score ficticio
        // cuando se llame a findById con el ID 1L
        Mockito.when(scoreService.findById(scoreId)).thenReturn(mockScore);

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/{id}", scoreId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(mockScore.getId()))
                .andExpect(jsonPath("$.userName").value(mockScore.getUserName()))
                .andExpect(jsonPath("$.team").value(mockScore.getTeam()))
                .andExpect(jsonPath("$.moves").value(mockScore.getMoves()))
                .andExpect(jsonPath("$.time").value(mockScore.getTime()));
    }

    @Test
    public void testGetScoreByIdScoreNotFound() throws Exception {
        // Arrange
        Long scoreId = 1L;

        // Define el comportamiento del servicio mock para devolver null cuando se llame
        // a findById con el ID 1L (simulando que no se encontró el score)
        Mockito.when(scoreService.findById(scoreId)).thenReturn(null);

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/{id}", scoreId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetScoreByIdNotValid() throws Exception {
        // Arrange
        Long scoreId = 1L;

        // Define el comportamiento del servicio mock para devolver null cuando se llame
        // a findById con el ID 1L (simulando que no se encontró el score)
        Mockito.when(scoreService.findById(scoreId)).thenReturn(null);

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/{id}", "NotValidId"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateScoreSuccess() throws Exception {
        // Arrange
        Long scoreId = 1L;
        Score existingScore = new Score(scoreId, "JohnDoe", "TeamA", 10, 100); // Suponiendo datos existentes
        Score updatedScore = new Score(null, "UpdatedUser", "TeamB", 8, 120); // Datos actualizados

        // Configura el comportamiento del servicio mock para que retorne el score
        // existente cuando se busque por ID
        Mockito.when(scoreService.findById(scoreId)).thenReturn(existingScore);

        // Configura el comportamiento del servicio mock para actualizar el score
        Mockito.doNothing().when(scoreService).update(updatedScore);

        // Convierte el objeto actualizado a formato JSON

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/scores/{id}", scoreId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedScore)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingScore.getId())) // Verifica que el ID no cambió
                .andExpect(jsonPath("$.userName").value(updatedScore.getUserName())) // Verifica que el nombre de
                                                                                     // usuario se actualizó
                .andExpect(jsonPath("$.team").value(updatedScore.getTeam())) // Verifica que el equipo se actualizó
                .andExpect(jsonPath("$.moves").value(updatedScore.getMoves())) // Verifica que los movimientos se
                                                                               // actualizaron
                .andExpect(jsonPath("$.time").value(updatedScore.getTime())); // Verifica que el tiempo se actualizó
    }

    @Test
    void testUpdateScoreWithInvalidId() throws Exception {
        // Arrange
        String invalidId = "cinco"; // ID no válido que no existe en la base de datos
        Score updatedScore = new Score(null, "UpdatedUser", "TeamB", 8, 120); // Datos actualizados

        // Configura el servicio mock para que devuelva null cuando se busque el score
        // por ID no válido
        // Mockito.when(scoreService.findById(invalidId)).thenReturn(null);

        // Convierte el objeto actualizado a formato JSON
        String updatedScoreJson = objectMapper.writeValueAsString(updatedScore);

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/scores/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedScoreJson))
                .andExpect(status().isBadRequest()); // Espera un código de estado 404 Not Found
    }

    @Test
    void testUpdateScoreWithNonExistingId() throws Exception {
        // Arrange
        Long nonExistingId = 999L; // ID que no existe en la base de datos
        Score updatedScore = new Score(nonExistingId, "UpdatedUser", "TeamB", 8, 120); // Datos actualizados

        // Configura el servicio mock para que devuelva null cuando se busque el score
        // por ID no existente
        Mockito.when(scoreService.findById(nonExistingId)).thenReturn(null);

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/scores/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedScore)))
                .andExpect(status().isNotFound()); // Espera un código de estado 404 Not Found
    }

    @Test
    void testDeleteScoreById() throws Exception {
        // Given
        Long scoreId = 1L;
        Mockito.when(scoreService.findById(scoreId)).thenReturn(new Score(scoreId, "John", "Team A", 10, 100)); // Simulamos
                                                                                                                // que
                                                                                                                // el
                                                                                                                // score
                                                                                                                // existe

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/scores/{id}", scoreId))
                .andExpect(status().isOk());

        // Verifica que el servicio haya sido invocado para eliminar el score con el ID
        // especificado
        Mockito.verify(scoreService, Mockito.times(1)).delete(scoreId);
    }

    @Test
    void testDeleteScoreByIdNotFound() throws Exception {
        // Given
        Long scoreId = 1L;
        Mockito.when(scoreService.findById(scoreId)).thenReturn(null); // Simulamos que el score no existe

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/scores/{id}", scoreId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllScoresWhenListIsNotEmpty() throws Exception {
        // Given
        List<Score> mockScores = Arrays.asList(
                new Score(1L, "user1", "teamA", 10, 30),
                new Score(2L, "user2", "teamB", 15, 45));

        Mockito.when(scoreService.findAll()).thenReturn(mockScores);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userName").value("user1"))
                .andExpect(jsonPath("$[0].team").value("teamA"))
                .andExpect(jsonPath("$[0].moves").value(10))
                .andExpect(jsonPath("$[0].time").value(30))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].userName").value("user2"))
                .andExpect(jsonPath("$[1].team").value("teamB"))
                .andExpect(jsonPath("$[1].moves").value(15))
                .andExpect(jsonPath("$[1].time").value(45));
    }

    @Test
    void getAllScoresWhenListIsEmpty() throws Exception {
        // Given
        List<Score> mockScores = Collections.emptyList(); // Lista vacía

        Mockito.when(scoreService.findAll()).thenReturn(mockScores);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetTopScores() throws Exception {
        // Crear una lista de scores de ejemplo
        List<Score> mockScores = Arrays.asList(
                new Score(1L, "user1", "teamA", 8, 100),
                new Score(2L, "user2", "teamB", 10, 110),
                new Score(3L, "user3", "teamC", 11, 95));

        // Configurar el comportamiento simulado del servicio
        Mockito.when(scoreService.getTopScores()).thenReturn(mockScores);

        // Realizar la solicitud GET a /api/scores/top-scores
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/top-scores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[0].userName").value("user1"))
                .andExpect(jsonPath("$[1].userName").value("user2"))
                .andExpect(jsonPath("$[2].userName").value("user3"))
                .andExpect(jsonPath("$[0].team").value("teamA"))
                .andExpect(jsonPath("$[1].team").value("teamB"))
                .andExpect(jsonPath("$[2].team").value("teamC"))
                .andExpect(jsonPath("$[0].time").value("100"))
                .andExpect(jsonPath("$[1].time").value("110"))
                .andExpect(jsonPath("$[2].time").value("95"));

    }

    @Test
    void testGetTopScoresEmptyList() throws Exception {
        // Crear una lista de scores de ejemplo
        List<Score> mockScores = Collections.emptyList();

        // Configurar el comportamiento simulado del servicio
        Mockito.when(scoreService.getTopScores()).thenReturn(mockScores);

        // Realizar la solicitud GET a /api/scores/top-scores
        mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/top-scores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

    }

    @Test
void testGetPositionExistingScore() throws Exception {
    // ID de ejemplo de score existente
    Long scoreId = 1L;
    int expectedPosition = 5; // Posición esperada para el score con ID 1

    // Configurar el comportamiento simulado del servicio para devolver un score existente
    Score mockScore = new Score(1L, "user1", "teamA", 8, 100);
    
    Mockito.when(scoreService.findById(scoreId)).thenReturn(mockScore);
    Mockito.when(scoreService.getPosition(scoreId)).thenReturn(expectedPosition);

    // Realizar la solicitud GET a /api/scores/{id}/position
    mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/{id}/position", scoreId))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.position").value(expectedPosition));
}

@Test
void testGetPositionScoreNotFound() throws Exception {
    Long nonExistingId = 100L; // ID que no existe en la base de datos

    // Configurar el servicio mock para devolver null, indicando que el score no existe
    Mockito.when(scoreService.findById(nonExistingId)).thenReturn(null);

    // Realizar la solicitud GET a /api/scores/{id}/position
    mockMvc.perform(MockMvcRequestBuilders.get("/api/scores/{id}/position", nonExistingId))
            .andExpect(status().isNotFound()); // Espera recibir un código de estado 404 Not Found
}
}
