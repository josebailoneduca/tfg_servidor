/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */
package josebailon.ensayos.servidor.service.impl;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import josebailon.ensayos.servidor.config.AudioPropiedades;
import josebailon.ensayos.servidor.model.entity.Audio;
import josebailon.ensayos.servidor.model.entity.Cancion;
import josebailon.ensayos.servidor.model.entity.Grupo;
import josebailon.ensayos.servidor.model.entity.Nota;
import josebailon.ensayos.servidor.model.entity.Usuario;
import josebailon.ensayos.servidor.repository.AudioRepository;
import josebailon.ensayos.servidor.repository.CancionRepository;
import josebailon.ensayos.servidor.repository.GrupoRepository;
import josebailon.ensayos.servidor.repository.NotaRepository;
import josebailon.ensayos.servidor.repository.UsuarioRepository;
import josebailon.ensayos.servidor.security.ResolutorPermisos;
import josebailon.ensayos.servidor.service.IAudioService;
import josebailon.ensayos.servidor.service.ICancionService;
import josebailon.ensayos.servidor.service.INotaService;
import josebailon.ensayos.servidor.service.exception.VersionIncorrectaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
@Service
@RequiredArgsConstructor

public class AudioServiceImpl implements IAudioService {

    private final ResolutorPermisos resolutorPermisos;
    private final UsuarioRepository repositorioUsuario;
    private final NotaRepository repositorioNota;
    private final AudioRepository repositorioAudio;
    private final AudioPropiedades audioPropiedades;

    @Override
    @Transactional
    public Audio create(UUID idAudio, int version, MultipartFile archivo, Long idUsuario) throws ResponseStatusException {
        Optional<Usuario> usuario = repositorioUsuario.findById(idUsuario);
        Optional<Nota> nota = repositorioNota.findById(idAudio);

        if (usuario.isPresent()) {

            Usuario u = usuario.get();
            Nota n = nota.get();
            Audio a = new Audio();
            if (resolutorPermisos.permitido(u, n)) {
                String nombreArchivo;
                try {
                    nombreArchivo = this.guardaArchivo(archivo);
//                    a.setId(idAudio);
                    a.setNombreArchivo(nombreArchivo);
                    a.setVersion(version + 1);
                    a.setNota(n);
                    System.out.println(n);
                    System.out.println(a);
                    return repositorioAudio.save(a);
                } catch (IllegalStateException | IOException ex) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Audio edit(Audio request, MultipartFile archivo, Long idUsuario) throws ResponseStatusException, VersionIncorrectaException {
        Optional<Usuario> usuario = repositorioUsuario.findById(idUsuario);
        Optional<Audio> audio = repositorioAudio.findById(request.getId());
        if (usuario.isPresent() && audio.isPresent()) {
            Usuario u = usuario.get();
            Audio a = audio.get();
            if (resolutorPermisos.permitido(u, a)) {
                if (request.getVersion() == a.getVersion()) {
                    String nombreArchivo;
                    String nombreArchivoAnterior = a.getNombreArchivo();
                    try {
                        nombreArchivo = this.guardaArchivo(archivo);
                        eliminaArchivo(nombreArchivoAnterior);
                        a.setNombreArchivo(nombreArchivo);
                        a.setVersion(request.getVersion() + 1);
                        a.setNombreArchivo(nombreArchivo);
                        return repositorioAudio.save(a);

                    } catch (IllegalStateException | IOException ex) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    throw new VersionIncorrectaException("", a);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Audio request, Long idUsuario) throws ResponseStatusException, VersionIncorrectaException {
        Optional<Usuario> usuario = repositorioUsuario.findById(idUsuario);
        Optional<Audio> audio = repositorioAudio.findById(request.getId());
        if (usuario.isPresent() && audio.isPresent()) {
            Usuario u = usuario.get();
            Audio a = audio.get();
            if (resolutorPermisos.permitido(u, a)) {
                if (request.getVersion() == a.getVersion()) {
                    try {
                        System.out.println("Nombre archivo "+ a.getNombreArchivo()+"   id:"+a.getId());
                        Nota n = a.getNota();
                        n.setAudio(null);
                        repositorioAudio.deleteById(a.getId());
                        this.eliminaArchivo(a.getNombreArchivo());
                    } catch (IllegalStateException | IOException ex) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    

                } else {
                    throw new VersionIncorrectaException("", a);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

//    @Override
//    public Nota edit(Nota request, Long idUsuario) throws ResponseStatusException, VersionIncorrectaException {
//        Optional<Usuario> usuario = repositorioUsuario.findById(idUsuario);
//        Optional<Nota> nota = repositorioNota.findById(request.getId());
//        if (usuario.isPresent() && nota.isPresent() && !nota.get().isBorrado()){
//            Usuario u= usuario.get();
//            Nota n= nota.get();
//            if(resolutorPermisos.permitido(u,n)){
//                if (request.getVersion()==n.getVersion()){
//                    n.setVersion(request.getVersion()+1);
//                    n.setNombre(request.getNombre());
//                    n.setTexto(request.getTexto());
//                    n.setAudio(request.getAudio());
//                  return repositorioNota.save(n);
//                }
//                else{
//                  throw new VersionIncorrectaException("",n);
//                }
//            }else{
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN); 
//            }
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
//        }
//    }
//
//   
//    @Override
//    public void delete(Nota request, Long idUsuario) throws ResponseStatusException, VersionIncorrectaException{
//         Optional<Usuario> usuario = repositorioUsuario.findById(idUsuario);
//        Optional<Nota> nota = repositorioNota.findById(request.getId());
//        if (usuario.isPresent() && nota.isPresent()&& !nota.get().isBorrado()){
//            Usuario u= usuario.get();
//            Nota n= nota.get();
//            if(resolutorPermisos.permitido(u,n)){
//                if (request.getVersion()==n.getVersion()){
//                    repositorioNota.deleteById(n.getId());
//                }
//                else{
//                  throw new VersionIncorrectaException("",n);
//                }
//            }else{
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN); 
//            }
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
//        }
//        
//        
//    }
    private String guardaArchivo(MultipartFile archivo) throws IllegalStateException, IOException {

        //comprobar que es un mp3
        if (!archivo.getContentType().equalsIgnoreCase("audio/mpeg")) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        String rutaFinal = audioPropiedades.getRuta() + "/";
        String nombre = UUID.randomUUID() + ".mp3";
        rutaFinal += nombre;
        archivo.transferTo(new File(rutaFinal));
        return nombre;
    }

    private boolean eliminaArchivo(String nombre) throws IllegalStateException, IOException {
         File rutaAlmacenamiento = new File(audioPropiedades.getRuta()+"/");
         File archivo = new File (audioPropiedades.getRuta()+"/"+nombre);
         boolean enAlmacenamiento = archivo.getCanonicalPath().startsWith(rutaAlmacenamiento.getCanonicalPath() + File.separator);
        if (!StringUtils.hasText(nombre) || !archivo.exists() ||archivo.isDirectory() || !enAlmacenamiento)
            return false;
        return archivo.delete();
    }
}//end UserService