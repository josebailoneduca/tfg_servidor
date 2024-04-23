/*
LICENCIA JOSE JAVIER BO
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
Lista de paquetes:
 */

package josebailon.ensayos.servidor.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
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
public class Usuario {
    
    @Id
    @JsonView(Vista.Esencial.class)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @JsonView(Vista.Esencial.class)
    @Column(unique=true)
    private String email;

    @JsonIgnore
    private String password;
    
    @JsonView(Vista.Esencial.class)
    private String role;
    
    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name="grupo_id")
            
    )
    @JsonIgnore
    private Set<Grupo> grupos = new HashSet<>();

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", email=" + email + ", role=" + role + ", grupos=" + grupos + '}';
    }
    
    
}//end UsuarioEntity
