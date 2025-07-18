package snn.soluciones.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICInventarioAjusteDao;
import snn.soluciones.com.models.entity.CInventarioAjuste;
import snn.soluciones.com.service.interfaces.ICInventarioMovimientoAjusteService;

@Service
public class CInventarioMovimientoAjusteServiceImpl implements ICInventarioMovimientoAjusteService {
  @Autowired
  private ICInventarioAjusteDao _dao;
  
  public void save(CInventarioAjuste entity) {
    this._dao.save(entity);
  }
  
  public Page<CInventarioAjuste> findAllByEmisorId(Long emisorId, String q, Pageable pageable) {
    return this._dao.findAllByEmisorId(emisorId, q, pageable);
  }
  
  public CInventarioAjuste findByEmisorIdAndIdFactura(Long emisorId, Long facturaId) {
    return this._dao.findByEmisorIdAndIdFactura(emisorId, facturaId);
  }
  
  public CInventarioAjuste numeroAjuste(Long emisorId) {
    return this._dao.numeroAjuste(emisorId);
  }
}
