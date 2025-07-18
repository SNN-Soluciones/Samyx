package snn.soluciones.com.service.interfaces;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import snn.soluciones.com.models.entity.CCabys;
import snn.soluciones.com.models.entity.EmisorCabys;

public interface IEmisorCabysService {
  Page<EmisorCabys> findAllByIdEmisorId(Long paramLong, String paramString, Pageable paramPageable);
  
  List<EmisorCabys> findAll(Long paramLong);
  
  void save(EmisorCabys paramEmisorCabys);
  
  void deleteByIdAndEmisorId(Long paramLong1, Long paramLong2);
  
  List<CCabys> findByNombreCabysIgnoreCase(String paramString1, String paramString2, int paramInt);
  
  CCabys findCCabysById(Long paramLong);
}
