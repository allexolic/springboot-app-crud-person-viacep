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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import com.infoguia.gestaopessoa.controller.page.PageWrapper;
import com.infoguia.gestaopessoa.model.Address;
import com.infoguia.gestaopessoa.model.Pessoa;
import com.infoguia.gestaopessoa.model.PessoaBanco;
import com.infoguia.gestaopessoa.repository.BancosRepository;
import com.infoguia.gestaopessoa.repository.PessoaBancos;
import com.infoguia.gestaopessoa.repository.Pessoas;
import com.infoguia.gestaopessoa.service.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {
	@Autowired
	private Pessoas pessoas;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private BancosRepository bancos;
	
	@Autowired
	private PessoaBancos pessoaBancos;
	
	@GetMapping
	public ModelAndView listarPessoa (@PageableDefault(size=6)Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/pessoa/listarPessoa");
		
		Pageable allPessoasSortedById = PageRequest.of(pageable.getPageNumber(), 
													   pageable.getPageSize(), 
													   Sort.by("id").descending());
		
		PageWrapper<Pessoa> pageWrapper = new PageWrapper<>(pessoas.findAll(allPessoasSortedById),
				                                            httpServletRequest);
	    
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	/*
	@RequestMapping("/novo")
	public ModelAndView novo(Pessoa pessoa) {
		
		return new ModelAndView ("/pessoa/formPessoa");
	}
	*/
	@GetMapping("/novo")
	public String novoPessoa(Model model) {
		model.addAttribute("pessoa", new Pessoa());
		
		return "pessoa/formPessoa";
	}
	
	@PostMapping(value = "/salvar")
	public String salvar(@Valid @ModelAttribute("pessoa") Pessoa pessoa, BindingResult result, Address address) {
		
		if(result.hasErrors()) {
			
			return "pessoa/formPessoa";
		
		} 
		else if(pessoa.getId() == null){
			
		    pessoaService.salvar(pessoa, address);
		    
		}else {
			
			pessoaService.editar(pessoa, address);
			
		}
	 
		return "redirect:/pessoas";
		
	}
	
	@GetMapping(value = "/edit/{id}")
	public ModelAndView editPessoa(@PathVariable(name="id")Pessoa pessoa, PessoaBanco pessoaBanco, Address address) {
		ModelAndView mv = new ModelAndView("/pessoa/formPessoa");
		
		mv.addObject("bancos", bancos.findAll());
		mv.addObject(pessoa); 	
		mv.addObject(pessoaBanco);
		mv.addObject("newPessoaBanco", new PessoaBanco());
		mv.addObject("pessoaCBanco", pessoaBancos.findByPessoaId(pessoa.getId()));
		
		return mv;
	}
	
	@GetMapping(value="/pessoaBanco/{id}")
	public ModelAndView addContaBanco(@PathVariable(name="id")Pessoa id) {
		ModelAndView mv = new ModelAndView("redirect:/pessoas/edit/1#cco");		
		
		return mv;
	}
	
 

}
