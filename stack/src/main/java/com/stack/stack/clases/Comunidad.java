package com.stack.stack.clases;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.stack.stack.clases.EstadoPorComunidad;
import com.stack.stack.clases.Incidente;


@Entity
@Table(name = "comunidad")
public class Comunidad {
  @Id
  @GeneratedValue
  @Column(name = "idcomunidad", nullable = true)
  private Long idComunidad;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "puntaje")
  private Double puntaje;

  @Column(name = "estaHabilitada")
  private Integer estaHabilitada;

  //TODO
  //@OneToMany(mappedBy = "comunidad")
  @Transient
  private List<PrestacionDeServicio> prestaciones = new ArrayList<>();

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "miembroPorComunidad",
      joinColumns = @JoinColumn(name = "idComunidad"),
      inverseJoinColumns = @JoinColumn(name = "idMiembro")
  )
  private List<Miembro> miembros = new ArrayList<>();



  //@OneToMany(mappedBy = "comunidad")
  //private List<Incidente> incidentes;

  public Comunidad() {


  }

  public Comunidad(String nombre, String descripcion) {
    this.nombre = nombre;
    this.descripcion = descripcion;
  }

  public void agregarMiembros(Miembro ... miembros) {
    Collections.addAll(this.miembros, miembros);
  }

  public void eliminarMiembros(Miembro ... miembros) {
    for(Miembro miembro : miembros) {
      this.miembros.remove(miembro);
      //this.admins.remove(miembro);
    }
  }

  public Boolean esMiembro(Miembro miembro) {
    return this.miembros.contains(miembro);
  }


  //public Boolean esAdmin(Miembro miembro){
    //return admins.contains(miembro);
  //}

  public void agregarPrestacion(PrestacionDeServicio prestacion){
    this.prestaciones.add(prestacion);
  }


  //public void agregarAdmin(Miembro miembro) {
    //this.admins.add(miembro);
  //}

  public Integer getId() {
    return Math.toIntExact(this.idComunidad);
  }
}
