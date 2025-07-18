package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICTerminalDao;
import snn.soluciones.com.models.entity.CTerminal;
import snn.soluciones.com.service.interfaces.ICTerminalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CTerminalServiceImpl implements ICTerminalService {
  @Autowired
  private ICTerminalDao _terminalDao;
  
  public List<CTerminal> findAllTerminalBySucursalByEmisor(Long emisorId) {
    return this._terminalDao.findAllTerminalBySucursalByEmisor(emisorId);
  }
  
  public List<CTerminal> findAllTerminalBySucursal(Long sucursalId) {
    return this._terminalDao.findAllTerminalBySucursal(sucursalId);
  }
  
  public CTerminal findById(Long id) {
    return this._terminalDao.findById(id).orElse(null);
  }
  
  public void save(CTerminal entity) {
    this._terminalDao.save(entity);
  }
}
