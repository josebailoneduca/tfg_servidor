/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
Lista de paquetes:
 */
package josebailon.ensayos.servidor.service;

import java.util.Optional;
import josebailon.ensayos.servidor.model.entity.Usuario;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public interface IUsuarioService {
    public Optional<Usuario> findByEmail(String email);
    public Optional<Usuario> findById(Long idUsuario);
}
