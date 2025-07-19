package snn.soluciones.com.controllers;

import snn.soluciones.com.service.interfaces.IFEBitacoraService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @Autowired
  private IFEBitacoraService _bitacoraService;

  @GetMapping({"/"})
  @PreAuthorize("isAuthenticated()") // Asegurar que solo usuarios autenticados puedan acceder
  public String Home(Model model, HttpSession session) {
    // Verificar primero si existe el atributo antes de usarlo
    Object empresaId = session.getAttribute("SESSION_EMPRESA_ID");
    Object identificacionEmpresa = session.getAttribute("SESSION_IDENTIFICACION_EMPRESA");

    if (empresaId != null && identificacionEmpresa != null) {
      String emisorIdentificacion = identificacionEmpresa.toString();
      int tfe = this._bitacoraService.countFe(emisorIdentificacion);
      int tnd = this._bitacoraService.countNd(emisorIdentificacion);
      int tnc = this._bitacoraService.countNc(emisorIdentificacion);
      int tte = this._bitacoraService.countTe(emisorIdentificacion);
      int fec = this._bitacoraService.countFec(emisorIdentificacion);
      int fee = this._bitacoraService.countFee(emisorIdentificacion);
      int totalDocs = tfe + tnd + tnc + tte + fec + fee;

      try {
        if (totalDocs > 0) {
          model.addAttribute("pfe", tfe * 100 / totalDocs);
          model.addAttribute("pnd", tnd * 100 / totalDocs);
          model.addAttribute("pnc", tnc * 100 / totalDocs);
          model.addAttribute("pte", tte * 100 / totalDocs);
          model.addAttribute("pfec", fec * 100 / totalDocs);
          model.addAttribute("pfee", fee * 100 / totalDocs);
        } else {
          // Si no hay documentos, poner todos los porcentajes en 0
          model.addAttribute("pfe", 0);
          model.addAttribute("pnd", 0);
          model.addAttribute("pnc", 0);
          model.addAttribute("pte", 0);
          model.addAttribute("pfec", 0);
          model.addAttribute("pfee", 0);
        }
      } catch (Exception e) {
        model.addAttribute("pfe", 0);
        model.addAttribute("pnd", 0);
        model.addAttribute("pnc", 0);
        model.addAttribute("pte", 0);
        model.addAttribute("pfec", 0);
        model.addAttribute("pfee", 0);
      }

      model.addAttribute("tfe", tfe);
      model.addAttribute("tnd", tnd);
      model.addAttribute("tnc", tnc);
      model.addAttribute("tte", tte);
      model.addAttribute("tfec", fec);
      model.addAttribute("tfee", fee);
      return "/home/home";
    }

    // Si no hay empresa en sesión, redirigir a selección de empresa
    return "redirect:/emisor/";
  }
}