/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package josebailon.ensayos.servidor.repository;

import java.util.UUID;
import josebailon.ensayos.servidor.model.entity.Cancion;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio CRUD de acceso a las canciones en la base de datos
 * @author Jose Javier Bailon Ortiz
 */
public interface CancionRepository  extends CrudRepository<Cancion, UUID>{
}//end UsuarioRepository
