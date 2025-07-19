package snn.soluciones.com.auth.handler;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
    FlashMap flashMap = new FlashMap();
    flashMap.put("success", "Hola " + authentication.getName() + ", has iniciado sesión correctamente.");
    flashMapManager.saveOutputFlashMap(flashMap, request, response);

    if (logger.isInfoEnabled()) {
      logger.info("El usuario " + authentication.getName() + " inicio sesión con exito.");
    }

    super.onAuthenticationSuccess(request, response, authentication);
  }
}