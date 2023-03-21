
package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import dmacc.beans.Pokemon;
import dmacc.repository.PokemonRepository;

/**
 * @author akvang - Alexander Vang
 * CIS175 - Spring 2023
 * Mar 21, 2023
 */
@Controller
public class WebController {
	@Autowired
	PokemonRepository repo;
	@GetMapping({ "/", "viewAll" })
	public String viewYourPokemon(Model model) {
		if(repo.findAll().isEmpty()) {
			return addNewPokemon(model);
		}
		model.addAttribute("pokemon", repo.findAll());
		return "pokemon";
	}
	
	@GetMapping("/addPokemon")
	public String addNewPokemon(Model model) {
		Pokemon p = new Pokemon();
		model.addAttribute("newPokemon", p);
		return "add";
	}
	@PostMapping("/addPokemon")
	public String addNewPokemon(@ModelAttribute Pokemon p, Model model) {
		repo.save(p);
		return viewYourPokemon(model);
	}
	@GetMapping("/edit/{id}")
	public String updatePokemon(@PathVariable("id") long id, Model model) {
		Pokemon p = repo.findById(id).orElse(null);
		model.addAttribute("newPokemon", p);
		return "add";
	}
	@PostMapping("/update/{id}")
	public String updatePokemon(Pokemon p, Model model) {
		repo.save(p);
		return viewYourPokemon(model);
	}
	@GetMapping("/delete/{id}")
	public String deletePokemon(@PathVariable("id") long id, Model model) {
		Pokemon p = repo.findById(id).orElse(null);
		repo.delete(p);
		return viewYourPokemon(model);
	}
}
