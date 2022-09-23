package co.edu.uniandes.dse.parcialejemplo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcialejemplo.entities.RoomEntity;

/**
 * Interface that persists a room
 *
 * @author ISIS2603
 *
 */
@Repository
public interface RoomRepository extends JpaRepository <RoomEntity, Long> {
	Optional <RoomEntity> findById(Long id);
}