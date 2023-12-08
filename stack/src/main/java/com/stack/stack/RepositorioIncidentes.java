package com.stack.stack;

import com.stack.stack.clases.EstadoPorComunidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import com.stack.stack.clases.Incidente;

@Repository
@Transactional
public class RepositorioIncidentes {

    @PersistenceContext
    private EntityManager entityManager;

    public void agregar(Incidente unIncidente) {
        entityManager.persist(unIncidente);
    }

    public void eliminar(Incidente unIncidente) {
        entityManager.remove(unIncidente);
    }

    public Incidente buscarPorId(long id) {
        return entityManager.find(Incidente.class, id);
    }

    public List<Incidente> buscarTodos() {
        TypedQuery<Incidente> query = entityManager.createQuery("from Incidente", Incidente.class);
        return query.getResultList();
    }

    public List<EstadoPorComunidad> buscarEstados(Long idIncidente) {
        TypedQuery<EstadoPorComunidad> query = entityManager.createQuery(
                "SELECT e FROM EstadoPorComunidad e WHERE e.incidente.idIncidente = :idIncidente", EstadoPorComunidad.class);
        query.setParameter("idIncidente", idIncidente);
        return query.getResultList();
    }

    public List<EstadoPorComunidad> buscarPorComunidad(Long idComunidad) {
        TypedQuery<EstadoPorComunidad> query = entityManager.createQuery(
                "SELECT ec FROM EstadoPorComunidad ec WHERE ec.comunidad.id = :idComunidad", EstadoPorComunidad.class);
        query.setParameter("idComunidad", idComunidad);
        return query.getResultList();
    }

    public List<EstadoPorComunidad> buscarTodosEstados() {
        TypedQuery<EstadoPorComunidad> query = entityManager.createQuery("FROM EstadoPorComunidad", EstadoPorComunidad.class);
        return query.getResultList();
    }


    public List<Incidente> obtenerHistorialDeIncidentesDeMiembro(Integer idMiembro) {
        String jpql = "SELECT i FROM Incidente i WHERE i.miembroCreador.id = :miembroId";
        TypedQuery<Incidente> query = entityManager.createQuery(jpql, Incidente.class);
        query.setParameter("miembroId", idMiembro);
        return query.getResultList();
    }


    public Optional<EstadoPorComunidad> buscarEstadoPorComunidad(Long idIncidente, Long idComunidad) {
        TypedQuery<EstadoPorComunidad> query = entityManager.createQuery(
                "SELECT ec FROM EstadoPorComunidad ec " +
                        "WHERE ec.incidente.idIncidente = :idIncidente AND ec.comunidad.id = :idComunidad", EstadoPorComunidad.class);

        query.setParameter("idIncidente", idIncidente);
        query.setParameter("idComunidad", idComunidad);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
