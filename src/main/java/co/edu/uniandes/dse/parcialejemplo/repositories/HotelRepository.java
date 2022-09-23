package co.edu.uniandes.dse.parcialejemplo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;


/**
 * Interface that persists a hotel
 *
 * @author ISIS2603
 *
 */
@Repository
public interface HotelRepository extends JpaRepository <HotelEntity, Long> {
	Optional <HotelEntity> findById(Long id);
	Optional <HotelEntity> findByName(String name);
}