package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICActividadEconomicaDao;
import snn.soluciones.com.models.entity.CActividadEconomica;
import snn.soluciones.com.service.interfaces.ICActividadEconomicaService;

@Service
public class CActividadEconomicaServiceImpl implements ICActividadEconomicaService {
  @Autowired
  private ICActividadEconomicaDao _dao;
  
  public List<CActividadEconomica> findAll() {
    return (List<CActividadEconomica>)this._dao.findAll();
  }
}
