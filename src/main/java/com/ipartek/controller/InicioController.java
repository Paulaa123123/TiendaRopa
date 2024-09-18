package com.ipartek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipartek.repository.ProductoRepository;
import com.ipartek.repository.TallaRepository;

@Controller
public class InicioController {

	@Autowired
	private ProductoRepository productoRepo;

	@Autowired
	private TallaRepository tallaRepo;

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("atr_lista_productos", productoRepo.findAll());
		model.addAttribute("atr_lista_tallas", tallaRepo.findAll());

		return "index";
	}
}
