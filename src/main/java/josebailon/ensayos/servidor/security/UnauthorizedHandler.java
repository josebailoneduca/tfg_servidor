/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package josebailon.ensayos.servidor.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Manejador de excepciones de no autorizacion
 * @author Jose Javier Bailon Ortiz
 */
@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint{
        private Logger logger = Logger.getLogger(UnauthorizedHandler.class.getName());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.log(Level.WARNING, "Intento de acceso no autorizado desde {0}",request.getRemoteAddr());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
    }
  
}//end UnauthorizedHandler
