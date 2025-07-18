package snn.soluciones.com;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SamyxMain {
  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("America/Costa_Rica"));
  }
  
  public static void main(String[] args) {
    SpringApplication.run(SamyxMain.class, args);
  }
}