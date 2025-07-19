package snn.soluciones.com.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedirectLoopPreventionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RedirectLoopPreventionInterceptor.class);
    private static final String REDIRECT_COUNT_KEY = "redirect_count_";
    private static final int MAX_REDIRECTS = 5;
    private static final long RESET_INTERVAL_SECONDS = 2;
    
    // Cache para rastrear redirecciones
    private final Map<String, RedirectTracker> redirectCache = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        
        // Solo verificar rutas problemáticas
        if (requestURI.contains("/login") || requestURI.contains("/error")) {
            String sessionId = getSessionIdentifier(request);
            String cacheKey = sessionId + "_" + requestURI;
            
            RedirectTracker tracker = redirectCache.compute(cacheKey, (key, existing) -> {
                Instant now = Instant.now();
                
                if (existing == null) {
                    return new RedirectTracker(1, now);
                }
                
                // Resetear si ha pasado el intervalo
                if (now.minusSeconds(RESET_INTERVAL_SECONDS).isAfter(existing.lastAccess)) {
                    return new RedirectTracker(1, now);
                }
                
                // Incrementar contador
                existing.count++;
                existing.lastAccess = now;
                return existing;
            });
            
            if (tracker.count > MAX_REDIRECTS) {
                logger.error("Bucle de redirección detectado para URI: {} después de {} intentos", 
                           requestURI, tracker.count);
                
                // Limpiar el tracker
                redirectCache.remove(cacheKey);
                
                // Enviar error
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(
                    "<!DOCTYPE html>" +
                    "<html><head><title>Error</title></head>" +
                    "<body>" +
                    "<h1>Error: Bucle de redirección detectado</h1>" +
                    "<p>Se detectó un bucle infinito de redirecciones.</p>" +
                    "<p>URI: " + requestURI + "</p>" +
                    "<p>Por favor, contacte al administrador.</p>" +
                    "<a href='/'>Ir al inicio</a>" +
                    "</body></html>"
                );
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                          Object handler, ModelAndView modelAndView) throws Exception {
        // Limpiar cache antiguo periódicamente
        cleanOldEntries();
    }
    
    private String getSessionIdentifier(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getId();
        }
        return request.getRemoteAddr();
    }
    
    private void cleanOldEntries() {
        Instant threshold = Instant.now().minusSeconds(60);
        redirectCache.entrySet().removeIf(entry -> 
            entry.getValue().lastAccess.isBefore(threshold));
    }
    
    private static class RedirectTracker {
        int count;
        Instant lastAccess;
        
        RedirectTracker(int count, Instant lastAccess) {
            this.count = count;
            this.lastAccess = lastAccess;
        }
    }
}