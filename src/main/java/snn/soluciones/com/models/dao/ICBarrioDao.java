package snn.soluciones.com.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.CBarrio;

public interface ICBarrioDao extends CrudRepository<CBarrio, Long> {
  @Query("SELECT c FROM CBarrio c WHERE c.distrito.id = ?1")
  List<CBarrio> findAllByIdDistrito(Long paramLong);
}
