package snn.soluciones.com.controllers.inventario;

import snn.soluciones.com.models.entity.CCabys;
import snn.soluciones.com.models.entity.Emisor;
import snn.soluciones.com.models.entity.EmisorCabys;
import snn.soluciones.com.service.interfaces.IEmisorCabysService;
import snn.soluciones.com.service.interfaces.IEmisorService;
import snn.soluciones.com.util.PageRender;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping({"/inventario/cabys"})
@SessionAttributes({"cabys"})
public class CabysController {
  @Autowired
  private IEmisorService _emisorService;
  
  @Autowired
  private IEmisorCabysService _cabysService;
  
  @GetMapping({"/"})
  public String home(Model model, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "q", defaultValue = "") String q, HttpSession session) {
    if (session.getAttribute("SESSION_EMPRESA_ID") != null) {
      Long emisorId = Long.valueOf(Long.parseLong(session.getAttribute("SESSION_EMPRESA_ID").toString()));
      PageRequest pageRequest = PageRequest.of(page, 15);
      Page<EmisorCabys> ListaCabys = null;
      ListaCabys = this._cabysService.findAllByIdEmisorId(emisorId, q.toUpperCase(), (Pageable)pageRequest);
      PageRender<EmisorCabys> pageRender = new PageRender("/inventario/cabys/", ListaCabys);
      model.addAttribute("ListaCabys", ListaCabys);
      model.addAttribute("page", pageRender);
      return "/catalogos/productos/inventario/cabys/index";
    } 
    return "redirect:/";
  }
  
  @GetMapping(value = {"/buscar-cabys/"}, produces = {"application/json"})
  @ResponseBody
  public List<CCabys> buscarCabys(HttpSession session, @RequestParam(name = "term", defaultValue = "") String term, @RequestParam(name = "i", defaultValue = "") String i) {
    if (session.getAttribute("SESSION_EMPRESA_ID") != null)
      try {
        return this._cabysService.findByNombreCabysIgnoreCase(term.toString().toUpperCase(), i.toString(), 100);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }  
    return null;
  }
  
  @PostMapping({"/save"})
  public ResponseEntity<?> addCabys(Model model, HttpSession session, Authentication auth, @RequestParam("id") Long codigoId) {
    Map<String, Object> response = new HashMap<>();
    if (session.getAttribute("SESSION_EMPRESA_ID") != null) {
      EmisorCabys ec = new EmisorCabys();
      CCabys c = this._cabysService.findCCabysById(codigoId);
      ec.setEmisor(this._emisorService.findById(Long.valueOf(Long.parseLong(session.getAttribute("SESSION_EMPRESA_ID").toString()))));
      ec.setCabys(c);
      ec.setCodigoHacienda(c.getCodigoHacienda());
      ec.setImpuesto(c.getImpuesto());
      ec.setProductoServicio(c.getProductoServicio());
      this._cabysService.save(ec);
      response.put("response", Integer.valueOf(200));
    } else {
      response.put("response", Integer.valueOf(400));
    } 
    return new ResponseEntity(response, HttpStatus.OK);
  }
  
  @PostMapping({"/delete"})
  public ResponseEntity<?> deleteCabys(Model model, HttpSession session, Authentication auth, @RequestParam("id") Long codigoId) {
    Map<String, Object> response = new HashMap<>();
    if (session.getAttribute("SESSION_EMPRESA_ID") != null) {
      try {
        Emisor e = this._emisorService.findById(Long.valueOf(Long.parseLong(session.getAttribute("SESSION_EMPRESA_ID").toString())));
        this._cabysService.deleteByIdAndEmisorId(e.getId(), codigoId);
        response.put("response", Integer.valueOf(200));
      } catch (Exception e) {
        response.put("response", Integer.valueOf(400));
      } 
    } else {
      response.put("response", Integer.valueOf(400));
    } 
    return new ResponseEntity(response, HttpStatus.OK);
  }
}
