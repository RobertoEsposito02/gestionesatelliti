package it.prova.gestionesatelliti.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteService;

	@GetMapping
	public ModelAndView listAll(Model model) {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listAll();
		model.addAttribute("todayDate_attr", new Date());
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search(Model model) {
		model.addAttribute("todayDate_attr", new Date());
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		model.addAttribute("todayDate_attr", new Date());
		return "satellite/list";
	}
	
	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/insert")
	public String insert(Model model) {
		model.addAttribute("satellite_insert_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("satellite_insert_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		/* controllo se la data di lancio e dopo quella di ritorno */
		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataLancio().after(satellite.getDataRientro())) {
			result.rejectValue("dataLancio", "error.dataLancio.dataMaggioreDiDataRientro");
			return "satellite/insert";
		}

		/* controllo se la data di rientro e stata inserita ma quella di lancio no */
		if (satellite.getDataRientro() != null && satellite.getDataLancio() == null) {
			result.rejectValue("dataRientro", "error.dataRientro.notNullButDataLancioNull");
			return "satellite/insert";
		}

		/* controllo se sono state inserite entrambe le date e sono precedenti ad adesso ma lo stato è diverso da disabilitato */
		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null && satellite.getDataLancio().before(new Date())
				&& satellite.getDataRientro().before(new Date()) && (satellite.getStato() == null || satellite.getStato() != StatoSatellite.DISABILITATO)) {
			result.rejectValue("stato", "error.stato.nonDisabilitatoConDateEntrambePassate");
			return "satellite/insert";
		}

		/* controllo se non ho inserito date ma ho inserito uno stato */
		if(satellite.getDataLancio() == null && satellite.getDataRientro() == null && satellite.getStato() != null) {
			result.rejectValue("stato", "error.stato.inseritoMaDateNo");
			return "satellite/insert";
		}
		
		/* controllo se ho inserito una data di lancio che è precedente ad adesso ma non ho inserito uno stato */
		if(satellite.getDataLancio() != null && satellite.getDataLancio().before(new Date()) && satellite.getStato() == null) {
			result.rejectValue("stato", "error.stato.nonInseritoMaDataLancioInseritaPassata");
			return "satellite/insert";
		}
		
		/* controllo se la data di lancio inserita è dopo adesso ma lo stato è diverso da null */
		if(satellite.getDataLancio() != null && satellite.getDataLancio().after(new Date()) && satellite.getStato() != null) {
			result.rejectValue("stato", "error.stato.inseritoMaDataLancioInseritaFutura");
			return "satellite/insert";
		}

		if (result.hasErrors())
			return "satellite/insert";

		satelliteService.inserisciNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@PostMapping("/lounch")
	public String lounch(@RequestParam(name = "idSatellite") Long idSatellite, ModelMap model) {
		Satellite satellite = satelliteService.caricaSingoloElemento(idSatellite);
		satellite.setDataLancio(new Date());
		satelliteService.aggiorna(satellite);
		model.addAttribute("todayDate_attr", new Date());
		model.addAttribute("satellite_list_attribute", satelliteService.listAll());
		return "satellite/list";
	}
	
	@PostMapping("/rientro")
	public String rientro(@RequestParam(name = "idSatellite") Long idSatellite, ModelMap model) {
		Satellite satellite = satelliteService.caricaSingoloElemento(idSatellite);
		if(satellite.getDataLancio() != null) {
			satellite.setDataRientro(new Date());
			satellite.setStato(StatoSatellite.DISABILITATO);
			satelliteService.aggiorna(satellite);
		}
		model.addAttribute("todayDate_attr", new Date());
		model.addAttribute("satellite_list_attribute", satelliteService.listAll());
		return "satellite/list";
	}
	
	@GetMapping("/delete/{idSatellite}")
	public String delete(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("delete_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/delete";
	}
	
	@PostMapping("/saveDelete")
	public String saveDelete(@RequestParam(name = "idSatellite" ) Long idSatellite,
			RedirectAttributes redirectAttrs) {
		
		satelliteService.rimuovi(idSatellite);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@GetMapping("/update/{idSatellite}")
	public String update(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("update_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		model.addAttribute("todayDate_attr", new Date());
		return "satellite/update";
	}
	
	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("update_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs, @RequestParam(name = "dataLancio") Date dataLancio) {
	
		satellite.setDataLancio(dataLancio);
		satelliteService.aggiorna(satellite);
			
		
		/* controllo se la data di lancio e dopo quella di ritorno */
		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataLancio().after(satellite.getDataRientro())) {
			result.rejectValue("dataLancio", "error.dataLancio.dataMaggioreDiDataRientro");
			return "satellite/update";
		}

		/* controllo se la data di rientro e stata inserita ma quella di lancio no */
		if (satellite.getDataRientro() != null && satellite.getDataLancio() == null) {
			result.rejectValue("dataRientro", "error.dataRientro.notNullButDataLancioNull");
			return "satellite/update";
		}

		/* controllo se sono state inserite entrambe le date e sono precedenti ad adesso ma lo stato è diverso da disabilitato */
		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null && satellite.getDataLancio().before(new Date())
				&& satellite.getDataRientro().before(new Date()) && (satellite.getStato() == null || satellite.getStato() != StatoSatellite.DISABILITATO)) {
			result.rejectValue("stato", "error.stato.nonDisabilitatoConDateEntrambePassate");
			return "satellite/update";
		}

		/* controllo se non ho inserito date ma ho inserito uno stato */
		if(satellite.getDataLancio() == null && satellite.getDataRientro() == null && satellite.getStato() != null) {
			result.rejectValue("stato", "error.stato.inseritoMaDateNo");
			return "satellite/update";
		}
		
		/* controllo se ho inserito una data di lancio che è precedente ad adesso ma non ho inserito uno stato */
		if(satellite.getDataLancio() != null && satellite.getDataLancio().before(new Date()) && satellite.getStato() == null) {
			result.rejectValue("stato", "error.stato.nonInseritoMaDataLancioInseritaPassata");
			return "satellite/update";
		}
		
		/* controllo se la data di lancio inserita è dopo adesso ma lo stato è diverso da null */
		if(satellite.getDataLancio() != null && satellite.getDataLancio().after(new Date()) && satellite.getStato() != null) {
			result.rejectValue("stato", "error.stato.inseritoMaDataLancioInseritaFutura");
			return "satellite/update";
		}
		
		if (result.hasErrors())
			return "satellite/update";
		
		satelliteService.aggiorna(satellite);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite"; 
	}
	
	@GetMapping("/cercaQuelliDisattivatiNonAncoraRientrati")
	public String cercaQuelliDisattivatiNonAncoraRientrati(ModelMap model) {
		Date data=null;
		model.addAttribute("todayDate_attr", new Date());
		model.addAttribute("satellite_list_attribute", satelliteService.cercaQuelliDisattivatiAncoraNonRientrati(StatoSatellite.DISABILITATO,data));
		return "satellite/list";
	}
	
	@GetMapping("/cercaQuelliDaDueAnniInOrbitaNonDisattivati")
	public String cercaQuelliDaDueAnniInOrbitaNonDisattivati(ModelMap model) {
		model.addAttribute("todayDate_attr", new Date());
		model.addAttribute("satellite_list_attribute", satelliteService.cercaQuelliDaDueAnniInOrbitaNonDisattivati());
		return "satellite/list";
	}
	
	@GetMapping("/cercaQuelliDaAlmenoDieciAnniInOrbitaCheOraSonoFissi")
	public String cercaQuelliDaAlmenoDieciAnniInOrbitaCheOraSonoFissi(ModelMap model) {
		model.addAttribute("todayDate_attr", new Date());
		model.addAttribute("satellite_list_attribute", satelliteService.cercaQuelliDaAlmenoDieciAnniInOrbitaCheOraSonoFissi());
		return "satellite/list";
	}
}
