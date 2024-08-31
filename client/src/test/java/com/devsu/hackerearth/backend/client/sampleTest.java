package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest
public class sampleTest {

	private ClientService clientService = mock(ClientService.class);
	private ClientController clientController = new ClientController(clientService);

    @Test
    void getAllTest() {
        // Arrange
        List<ClientDto> clients = new ArrayList();
        clients.add(new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true));
        clients.add(new ClientDto(2L, "Dni2", "Name2", "Password2", "Gender2", 2, "Address2", "9999999990", false));
        when(clientService.getAll()).thenReturn(clients);

        // Act
        ResponseEntity<List<ClientDto>> response = clientController.getAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }

    @Test
    void getOneTest() {
        // Arrange
        ClientDto client = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.getById(1L)).thenReturn(client);

        // Act
        ResponseEntity<ClientDto> response = clientController.get(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void createClientTest() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto createdClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientService.create(newClient)).thenReturn(createdClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.create(newClient);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdClient, response.getBody());
    }

    @Test
    void updateClient() {
        // Arrange
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 2, "Address", "9999999999", true);
        when(clientService.update(newClient)).thenReturn(newClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.update(1L, newClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newClient, response.getBody());
    }

    @Test
    void partialUpdateClient() {
        // Arrange
        ClientDto oldClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", false);
        PartialClientDto partialClientDto = new PartialClientDto(false);
        when(clientService.partialUpdate(oldClient.getId(), partialClientDto)).thenReturn(newClient);

        // Act
        ResponseEntity<ClientDto> response = clientController.partialUpdate(oldClient.getId(), partialClientDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newClient, response.getBody());
    }

    @Test
    void delete() {
        // Act
        ResponseEntity<Void> response = clientController.delete(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
