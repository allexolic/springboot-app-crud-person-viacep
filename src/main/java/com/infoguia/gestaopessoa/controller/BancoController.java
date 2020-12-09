package com.infoguia.gestaopessoa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.infoguia.gestaopessoa.controller.page.PageWrapper;
import com.infoguia.gestaopessoa.model.Banco;

import com.infoguia.gestaopessoa.repository.BancosRepository;

@Controller
public class BancoController {

	@Autowired
	private BancosRepository bancos;
	
	@GetMapping("/bancos")
	public String listarBanco(@PageableDefault(size=6)Pageable pageable, HttpServletRequest httpServletRequest, Model model) {
		
		Pageable allBancosSortedById = PageRequest.of(pageable.getPageNumber(), 
				   pageable.getPageSize(), 
				   Sort.by("id").descending());

		PageWrapper<Banco> pageWrapper = new PageWrapper<>(bancos.findAll(allBancosSortedById),
                     httpServletRequest);

		model.addAttribute("pagina", pageWrapper);
		return "banco/listarBanco";
	}
	
	@GetMapping("/bancos/novo")
	public String novoBanco(Model model) {
		
		model.addAttribute("banco", new Banco());
		return "banco/formBanco";
	}
	
	@PostMapping("/bancos/salvar")
	public String salvarBanco(@Valid Banco banco, BindingResult result) {
		if(result.hasErrors()) {
			return "banco/formBanco";
			
		}else {
			bancos.save(banco);
		}
		
		return "redirect:/bancos";
	}
	
	@GetMapping("/banco/edit/{id}")
	public String editarBanco(@PathVariable(name="id")Long id, Model model) {
		
		model.addAttribute("banco", bancos.findById(id));
		
		return "banco/formBanco";
	}
}
