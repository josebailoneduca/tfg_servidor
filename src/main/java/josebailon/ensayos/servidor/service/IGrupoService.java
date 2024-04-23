/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
Lista de paquetes:
 */
package josebailon.ensayos.servidor.service;

import java.util.Optional;
import java.util.UUID;
import josebailon.ensayos.servidor.model.entity.Grupo;
import josebailon.ensayos.servidor.model.entity.Usuario;
import josebailon.ensayos.servidor.service.exception.VersionIncorrectaException;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
public interface IGrupoService {
    public Grupo create(   UUID idGrupo,String nombre, String descripcion, int version, Long idUsuario);

    public Grupo edit(Grupo request, Long idUsuario) throws ResponseStatusException, VersionIncorrectaException;

    public Grupo delete(Grupo request, Long idUsuario)throws ResponseStatusException, VersionIncorrectaException;

    public Grupo addUsuario(UUID idGrupo, String emailusuario, Long idUsuario);

    public Grupo deleteUsuario(UUID idGrupo, String emailusuario, Long idUsuario);
}
