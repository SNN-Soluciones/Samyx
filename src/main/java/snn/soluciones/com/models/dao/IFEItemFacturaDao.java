package snn.soluciones.com.models.dao;

import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.FEFacturaItem;

public interface IFEItemFacturaDao extends CrudRepository<FEFacturaItem, Long> {}
