package snn.soluciones.com.service.interfaces;

import snn.soluciones.com.dto.GometaActividadDto;
import java.util.List;

public interface IGometaService {

    List<GometaActividadDto> obtenerActividadesPorIdentificacion(String identificacion) throws Exception;

    List<GometaActividadDto> obtenerTodosLosCodigosActividades() throws Exception;

    boolean validarCodigoActividad(String codigo) throws Exception;
}