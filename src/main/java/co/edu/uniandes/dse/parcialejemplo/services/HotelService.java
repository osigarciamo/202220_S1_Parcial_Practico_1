package co.edu.uniandes.dse.parcialejemplo.services;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelService {
	
	@Autowired
	HotelRepository hotelRepository;
	
	/**
	 * Crear un nuevo hotel
	 *
	 * @param hotelEntity La entidad de tipo hotel a persistir.
	 * @return La entidad luego de persistirla
	 * @throws IllegalOperationException Si el nombre del hotel ya existe
	 */
	@Transactional
	public HotelEntity createHotel(HotelEntity hotelEntity) throws EntityNotFoundException, IllegalOperationException {
		//log.info("Inicia proceso de creación del hotel");

		if (!hotelRepository.findByName(hotelEntity.getName()).isEmpty())
			throw new IllegalOperationException("Name already exists");

		//log.info("Termina proceso de creación del hotel");
		return hotelRepository.save(hotelEntity);
	}

}
