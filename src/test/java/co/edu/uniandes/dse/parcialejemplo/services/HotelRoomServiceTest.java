package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
@Import({HotelService.class, RoomService.class, HotelRoomServiceTest.class})
public class HotelRoomServiceTest {
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private HotelRoomService hotelRoomService;
	
	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();
	
	private List<HotelEntity> hotelsList = new ArrayList<>();
	private List<RoomEntity> roomsList = new ArrayList<>();
	
	/**
	 * Configuración inicial de la prueba.
	 */
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
	 * Prueba para añadir una habitación al hotel
	 */
	@Test
	void testAddRoomHotel() throws EntityNotFoundException, IllegalOperationException {
		HotelEntity newHotelEntity = factory.manufacturePojo(HotelEntity.class);
		RoomEntity newRoomEntity = factory.manufacturePojo(RoomEntity.class);
		HotelEntity response = hotelRoomService.addRoom(newHotelEntity.getId(), newRoomEntity.getId());
		assertNotNull(response);
		assertEquals(newHotelEntity.getId(), response.getId());
	}
	
	
	/**
	 * Prueba para añadir una habitación al hotel que no existe
	 */
	@Test
	void testAddRoomHotelThatDoNotExist() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			RoomEntity entity = roomsList.get(0);
			hotelRoomService.addRoom(0L, entity.getId());
		});
	}
	
	
	/**
	 * Prueba para una habitación que no existe
	 */
	@Test
	void testAddRoomThatDoNotExist() throws EntityNotFoundException, IllegalOperationException {
		assertThrows(EntityNotFoundException.class, ()->{
			HotelEntity entity = hotelsList.get(0);
			hotelRoomService.addRoom(entity.getId(), 0L);
		});
	}
	
	
	

}
