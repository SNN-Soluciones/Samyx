package snn.soluciones.com.models.dao;

import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.Moneda;

public interface IMonedaDao extends CrudRepository<Moneda, Long> {}
