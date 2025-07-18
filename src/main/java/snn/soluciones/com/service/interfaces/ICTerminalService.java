package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CTerminal;

public interface ICTerminalService {
  List<CTerminal> findAllTerminalBySucursalByEmisor(Long paramLong);
  
  List<CTerminal> findAllTerminalBySucursal(Long paramLong);
  
  CTerminal findById(Long paramLong);
  
  void save(CTerminal paramCTerminal);
}
