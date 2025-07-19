package snn.soluciones.com.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class HandlerErrorController {

  private static final Logger logger = LoggerFactory.getLogger(HandlerErrorController.class);

  // Manejar errores 404 específicamente
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNotFound(NoHandlerFoundException e, Model model) {
    logger.error("Página no encontrada: {}", e.getRequestURL());
    model.addAttribute("error", "Página no encontrada");
    model.addAttribute("message", e.getMessage());
    return "error/404"; // Necesitarás crear esta vista
  }

  // Manejar errores de recursos estáticos no encontrados
  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleResourceNotFound(NoResourceFoundException e, Model model) {
    logger.warn("Recurso no encontrado: {}", e.getResourcePath());
    // No redirigir, solo registrar y devolver 404
    model.addAttribute("error", "Recurso no encontrado");
    model.addAttribute("resource", e.getResourcePath());
    return "error/404";
  }

  // Manejar errores de acceso denegado
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public String handleAccessDenied(AccessDeniedException e, Model model) {
    logger.error("Acceso denegado: {}", e.getMessage());
    return "error_403"; // Ya existe esta vista
  }

  // Manejar NullPointerException específicamente
  @ExceptionHandler(NullPointerException.class)
  public String handleNullPointer(NullPointerException e, HttpServletRequest request, Model model) {
    logger.error("NullPointerException en {}: {}", request.getRequestURI(), e.getMessage(), e);

    // Si es una petición a recursos estáticos, devolver 404
    String uri = request.getRequestURI();
    if (uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/") ||
        uri.contains("/vendor/") || uri.contains("/fonts/")) {
      return "error/404";
    }

    // Si es una petición AJAX, devolver error genérico
    if (isAjaxRequest(request)) {
      model.addAttribute("error", "Error en el servidor");
      return "error/500";
    }

    // Solo redirigir a login si es una página que requiere autenticación
    if (!uri.contains("/login") && !uri.contains("/error") && !uri.contains("/static")) {
      return "redirect:/login?expired";
    }

    // Para otros casos, mostrar página de error
    model.addAttribute("error", "Error en el servidor");
    return "error/500";
  }

  // Manejar otras excepciones genéricas
  @ExceptionHandler(Exception.class)
  public String handleGenericException(Exception e, HttpServletRequest request, Model model) {
    logger.error("Error no manejado en {}: {}", request.getRequestURI(), e.getMessage(), e);

    // No redirigir automáticamente a login
    model.addAttribute("error", "Ha ocurrido un error inesperado");
    model.addAttribute("message", e.getMessage());
    return "error/500"; // Necesitarás crear esta vista
  }

  private boolean isAjaxRequest(HttpServletRequest request) {
    String requestedWith = request.getHeader("X-Requested-With");
    return "XMLHttpRequest".equals(requestedWith);
  }
}