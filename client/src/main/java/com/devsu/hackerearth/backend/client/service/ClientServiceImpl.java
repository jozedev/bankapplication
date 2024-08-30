package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		return clientRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		// Get clients by id
		return mapToDto(clientRepository.getOne(id));
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client
		return mapToDto(clientRepository.save(mapFromDto(clientDto)));
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		// Update client
		return mapToDto(clientRepository.save(mapFromDto(clientDto)));
	}

	@Override
    public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
        // Partial update account
		ClientDto clientDto = mapToDto(clientRepository.getOne(id));
		clientDto.setActive(partialClientDto.isActive());
		return mapToDto(clientRepository.save(mapFromDto(clientDto)));
    }

	@Override
	public void deleteById(Long id) {
		// Delete client
		clientRepository.deleteById(id);
	}
    
    private Client mapFromDto(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setActive(clientDto.isActive());
        client.setPassword(clientDto.getPassword());
        client.setName(clientDto.getName());
        client.setDni(clientDto.getDni());
        client.setGender(clientDto.getGender());
        client.setAge(clientDto.getAge());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());

        return client;
    }
    
    private ClientDto mapToDto(Client client) {
        return new ClientDto(client.getId(), client.getDni(), client.getName(), client.getPassword(), 
			client.getGender(), client.getAge(), client.getAddress(), client.getPhone(), client.isActive());
    }
}
