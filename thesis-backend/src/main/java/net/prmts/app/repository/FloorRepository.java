package net.prmts.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.prmts.app.model.Floor;
@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {

}
