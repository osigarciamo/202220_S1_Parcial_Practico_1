package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Hoteles 
 *
 * @author ISIS2603
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HotelService.class)
public class HotelServiceTest {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<HotelEntity> hotelsList = new ArrayList<>();
	private List<RoomEntity> roomsList = new ArrayList<>();
	
	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}
	
	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HotelEntity");
		entityManager.getEntityManager().createQuery("delete from RoomEntity");
		
	}
	
	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		for (int i = 0; i < 3; i++) {
			HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class);
			entityManager.persist(hotelEntity);
			hotelsList.add(hotelEntity);
		}

		for (int i = 0; i < 3; i++) {
			RoomEntity roomEntity = factory.manufacturePojo(RoomEntity.class);
			roomEntity.setHotel(hotelsList.get(0));
			entityManager.persist(roomEntity);
			roomsList.add(roomEntity);
		}

	}
	
	/**
	 * Prueba para crear un Hotel
	 */
	@Test
	void testCreateHotel() throws EntityNotFoundException, IllegalOperationException {
		HotelEntity newEntity = factory.manufacturePojo(HotelEntity.class);
		newEntity.setRooms(roomsList.get(0));
		newEntity.setId(1-4028-9462-7L);
		HotelEntity result = hotelService.createHotel(newEntity);
		assertNotNull(result);
		HotelEntity entity = entityManager.find(HotelEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getName(), entity.getName());
		assertEquals(newEntity.getAdress(), entity.getAdress());
		assertEquals(newEntity.getStarsNumber(), entity.getStarsNumber());
	}
	
	/**
	 * Prueba para crear un Hotel sin habitaciones
	 */
	@Test
	void testCreateHotelWithNoRooms() throws EntityNotFoundException, IllegalOperationException {
		HotelEntity newEntity = factory.manufacturePojo(HotelEntity.class);
		newEntity.setRooms(null);
		newEntity.setId(1-4028-9462-7L);
		HotelEntity result = hotelService.createHotel(newEntity);
		assertNotNull(result);
		HotelEntity entity = entityManager.find(HotelEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getName(), entity.getName());
		assertEquals(newEntity.getAdress(), entity.getAdress());
		assertEquals(newEntity.getStarsNumber(), entity.getStarsNumber());
	}

}
