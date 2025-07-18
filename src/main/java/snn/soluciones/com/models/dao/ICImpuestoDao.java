package snn.soluciones.com.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.CImpuesto;

public interface ICImpuestoDao extends CrudRepository<CImpuesto, Long> {
  @Query("SELECT c FROM CImpuesto c ORDER BY c.id")
  List<CImpuesto> findAllImpuestos();
}
