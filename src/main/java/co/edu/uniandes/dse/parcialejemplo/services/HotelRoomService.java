package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelRoomService {
	
	@Autowired
	HotelRepository hotelRepository;
	
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
	public HotelEntity addRoom(Long idHotel, Long idRoom) throws EntityNotFoundException, IllegalOperationException {
		//log.info("Inicia proceso de adición de una habitación");
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(idHotel);
		if (hotelEntity.isEmpty())
			throw new EntityNotFoundException("Hotel not found");
		Optional<RoomEntity> roomEntity = roomRepository.findById(idRoom);
		if (hotelEntity.isEmpty())
			throw new EntityNotFoundException("Room not found");
		//log.info("Termina proceso de adición de una habitación");
		hotelEntity.ifPresent(hotel -> hotel.getRooms().add(roomEntity.get()));
		return hotelEntity.get();
	}

}
