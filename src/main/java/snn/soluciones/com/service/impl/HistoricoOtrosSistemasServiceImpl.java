package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.IHistoricoOtrosSistemasDao;
import snn.soluciones.com.models.entity.HistoricoOtrosSistemas;
import snn.soluciones.com.service.interfaces.IHistoricoOtrosSistemasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HistoricoOtrosSistemasServiceImpl implements IHistoricoOtrosSistemasService {
  @Autowired
  private IHistoricoOtrosSistemasDao _dao;
  
  public Page<HistoricoOtrosSistemas> findAllByIdEmisorAndId(Long id, String q, Pageable pageable) {
    return this._dao.findAllByIdEmisorAndId(id, q, pageable);
  }
  
  public HistoricoOtrosSistemas findByIdEmisorAndId(Long emisorId, Long id) {
    return this._dao.findByIdEmisorAndId(emisorId, id);
  }
}
