package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CCanton;

public interface ICCantonService {
  List<CCanton> findAllByIdProvincia(Long paramLong);
  
  CCanton findById(Long paramLong);
}
