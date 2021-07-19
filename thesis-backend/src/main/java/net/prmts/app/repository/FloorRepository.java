package net.prmts.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.prmts.app.model.Floor;
@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {
	@Query("SELECT floor.id From Floor floor")
	List<Integer> findAllIds();
}
