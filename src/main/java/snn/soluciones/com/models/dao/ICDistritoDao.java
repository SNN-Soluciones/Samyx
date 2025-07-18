package snn.soluciones.com.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.CDistrito;

public interface ICDistritoDao extends CrudRepository<CDistrito, Long> {
  @Query("SELECT c FROM CDistrito c WHERE c.canton.id = ?1")
  List<CDistrito> findAllByIdCanton(Long paramLong);
}
