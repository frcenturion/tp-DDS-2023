package com.stack.stack;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.stack.stack.clases.EstadoPorComunidad;
import com.stack.stack.clases.Incidente;
import com.stack.stack.clases.IncidenteConEstado;

import java.time.format.DateTimeFormatter;

@Controller
public class IncidenteController {

	@Autowired
	private RepositorioIncidentes repositorioIncidentes;

	//@GetMapping("/incidentes")
	//public String getIncidentes(Model model) {
	//	List<IncidenteConEstado> incidentesConEstado = new ArrayList<>();
//		model.addAttribute("incidentesConEstado", incidentesConEstado);
		//return "incidentes";
	//}

	@GetMapping("/incidentes")
	public String mostrarIncidentesPorComunidad(@RequestParam(name = "idComunidad", required = false) String idCom, Model model) {

		List<IncidenteConEstado> incidentesConEstado = new ArrayList<>();

		if(idCom != null){
			Long idComunidad = Long.valueOf(idCom);



			List<EstadoPorComunidad> estados = this.repositorioIncidentes.buscarPorComunidad(idComunidad);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			for (EstadoPorComunidad estado : estados) {
				Incidente incidente = estado.getIncidente();
				IncidenteConEstado incidenteConEstado = new IncidenteConEstado();
				incidenteConEstado.setIncidente(incidente);
				incidenteConEstado.setEstado(estado);

				System.out.println(estado);

				// Formatear la fecha de cierre si no es nula
				if (estado.getFechaDeCierre() != null) {
					String fechaCierreFormateada = estado.getFechaDeCierre().format(formatter);
					model.addAttribute("fechaCierreFormateada", fechaCierreFormateada);
				} else {
					model.addAttribute("fechaCierreFormateada", "-");
				}

				incidentesConEstado.add(incidenteConEstado);
			}
		}



		model.addAttribute("incidentesConEstado", incidentesConEstado);

		return "incidentes";
	}
}
