package snn.soluciones.com.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.CSucursal;

public interface ICSucursalDao extends CrudRepository<CSucursal, Long> {
  @Query("SELECT c FROM CSucursal c WHERE c.emisor.id = ?1")
  List<CSucursal> findAllByEmisorId(Long paramLong);
}
