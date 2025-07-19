package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.IFEFacturasCXCDao;
import snn.soluciones.com.models.entity.FEFacturasCXC;
import snn.soluciones.com.service.interfaces.IFEFacturasCXCServices;
import java.util.Date;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FEFacturasCXCServicesImpl implements IFEFacturasCXCServices {
  @Autowired
  private IFEFacturasCXCDao _dao;
  
  @PersistenceContext
  EntityManager em;
  
  public void save(FEFacturasCXC entity) {
    this._dao.save(entity);
  }
  
  public void deleteById(Long id) {}
  
  public Page<FEFacturasCXC> findAllCXC(Long id, String estadoPago, String q, Pageable pageable) {
    return this._dao.findAllCXC(id, estadoPago, q, pageable);
  }
  
  public Page<FEFacturasCXC> findAllCXCCanceladas(Long id, String estadoPago, String q, Pageable pageable) {
    return this._dao.findAllCXCCanceladas(id, estadoPago, q, pageable);
  }
  
  public List<FEFacturasCXC> findAllCXCDetalle(Long id, Long idUsuario, String estadoPago) {
    return this._dao.findAllCXCDetalle(id, idUsuario, estadoPago);
  }
  
  @Transactional
  public void modificaEstadoFacturaCXC(String estado, Long emisorId, Long id) {
    this._dao.modificaEstadoFacturaCXC(estado, emisorId, id);
  }
  
  @Transactional
  public void anularFacturaCXC(String estado, Long emisorId, Long id) {
    this._dao.anularFacturaCXC(estado, emisorId, id);
  }
  
  public Double findAllCXCPagadas(Long id, String medioDePago, Long userId, Date fechaRegistroPago, String moneda) {
    return this._dao.findAllCXCPagadas(id, medioDePago, userId, fechaRegistroPago, moneda);
  }
  
  public FEFacturasCXC estadoClienteParaFacturar(Long emisorId, Long clienteId) {
    return this._dao.estadoClienteParaFacturar(emisorId, clienteId);
  }
  
  public FEFacturasCXC obtenerIdFECXCPorClave(String clave) {
    return this._dao.obtenerIdFECXCPorClave(clave);
  }
}
