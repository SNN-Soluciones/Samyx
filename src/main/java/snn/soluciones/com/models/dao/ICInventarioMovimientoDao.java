package snn.soluciones.com.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.CInventarioMovimiento;

public interface ICInventarioMovimientoDao extends CrudRepository<CInventarioMovimiento, Long> {
  @Query("SELECT c FROM CInventarioMovimiento c INNER JOIN c.emisor e INNER JOIN c.proveedor p WHERE e.id = ?1 AND (c.numeroFactura LIKE %?2%  OR UPPER(p.nombreCompleto) LIKE %?2%) ORDER BY c.id DESC")
  Page<CInventarioMovimiento> findAllByEmisorId(Long paramLong, String paramString, Pageable paramPageable);
  
  @Query("SELECT c FROM CInventarioMovimiento c INNER JOIN c.emisor e INNER JOIN c.proveedor p WHERE e.id = ?1 AND c.id=?2")
  CInventarioMovimiento findByEmisorIdAndIdFactura(Long paramLong1, Long paramLong2);

  @Query("""
  SELECT c FROM CInventarioMovimiento c 
  WHERE c.emisor.id = ?1 
  AND c.id = (
    SELECT MAX(c2.id) 
    FROM CInventarioMovimiento c2 
    WHERE c2.emisor.id = ?1
  )
""")
  CInventarioMovimiento secuenciaFacturaCompra(Long emisorId);
}
