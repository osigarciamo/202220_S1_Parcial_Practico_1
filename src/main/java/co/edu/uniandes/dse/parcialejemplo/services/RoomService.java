package co.edu.uniandes.dse.parcialejemplo.services;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoomService {
	
	@Autowired
	RoomRepository roomRepository;
	
	/**
	 * Crear una nueva habitación
	 *
	 * @param roomEntity La entidad de tipo habitación a persistir.
	 * @return La entidad luego de persistirla
	 * @throws IllegalOperationException Si el numero de personas es menor al número de camas *2
	 */
	@Transactional
	public RoomEntity createRoom(RoomEntity roomEntity) throws EntityNotFoundException, IllegalOperationException {
		//log.info("Inicia proceso de creación de la habitación");

		if (roomEntity.getHostsNumber() >= (roomEntity.getBedsNumber()*2))
			throw new IllegalOperationException("The number of hosts is not available by the name of beds");

		//log.info("Termina proceso de creación de la habitación");
		return roomRepository.save(roomEntity);
	}

}
