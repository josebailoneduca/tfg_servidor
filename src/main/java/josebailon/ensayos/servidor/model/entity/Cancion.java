/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package josebailon.ensayos.servidor.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import josebailon.ensayos.servidor.model.vistas.Vista;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Jose Javier Bailon Ortiz
 */
@Entity
@Getter
@Setter
public class Cancion {
    
    @Id
    @JsonView(Vista.Esencial.class)
    private UUID id;
    @JsonView(Vista.Esencial.class)
    private String nombre;
    @JsonView(Vista.Esencial.class)
    private String descripcion;
    @JsonView(Vista.Esencial.class)
    private int duracion;
    @JsonView(Vista.Esencial.class)
    private int version;
    @JsonView(Vista.Esencial.class)
    private boolean borrado;

    @ManyToOne
    @JoinColumn(name="grupo_id", nullable=false)
    @JsonIgnore
    private Grupo grupo;

    @OneToMany(mappedBy="cancion")
    @JsonView(Vista.Completa.class)
    private Set<Nota> notas;
    @Override
    public String toString() {
        return "Grupo{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", version=" + version + ", borrado=" + borrado + ", grupo="+ grupo.getNombre()+'}';
    }
    
    
}//end UsuarioEntity