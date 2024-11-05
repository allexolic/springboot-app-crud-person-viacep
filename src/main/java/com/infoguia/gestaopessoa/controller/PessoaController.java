package com.infoguia.gestaopessoa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import com.infoguia.gestaopessoa.controller.page.PageWrapper;
import com.infoguia.gestaopessoa.model.Address;
import com.infoguia.gestaopessoa.model.Pessoa;
import com.infoguia.gestaopessoa.model.PessoaBanco;
import com.infoguia.gestaopessoa.repository.BancosRepository;
import com.infoguia.gestaopessoa.repository.PessoaBancos;
import com.infoguia.gestaopessoa.repository.Pessoas;
import com.infoguia.gestaopessoa.service.PessoaService;

import java.text.ParseException;

@Slf4j
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
													   Sort.by("id"));
		PageWrapper<Pessoa> pageWrapper = new PageWrapper<>(pessoas.findAll(allPessoasSortedById), httpServletRequest);
		mv.addObject("pagina", pageWrapper);
		return mv;
	}

	@GetMapping("/novo")
	public String novoPessoa(Model model) {
		val pessoaRequest = new PessoaRequest();
		pessoaRequest.setAddress(new AddressRequest());
		model.addAttribute("pessoa", pessoaRequest);
		model.addAttribute("address", pessoaRequest.getAddress());
		model.addAttribute("bancos", bancos.findAll());
		model.addAttribute("pessoaBanco", pessoaRequest);
		return "pessoa/formPessoa";
	}
	
	@PostMapping(value = "/salvar")
	public String salvar(@Valid @ModelAttribute("pessoa") PessoaRequest requestPessoa, BindingResult resultPessoa,
						 @Valid	@ModelAttribute("address") AddressRequest requestAddress, BindingResult resultAddress) throws Exception {
		if(resultPessoa.hasErrors() || resultAddress.hasErrors()) {
			log.error("Error on send request: " + requestPessoa + resultAddress);
			return "pessoa/formPessoa";
		}
		else {
			salvarPessoa(requestPessoa, requestAddress);
		}
		return "redirect:/pessoas";
	}

	private void salvarPessoa(PessoaRequest requestPessoa, AddressRequest requestAddress) throws ParseException {
		pessoaService.salvar(requestPessoa, requestAddress);
	}

	@GetMapping(value = "/edit/{id}")
	public ModelAndView editPessoa(@PathVariable(name="id")Long id, PessoaBanco pessoaBanco, Address address) {
		ModelAndView mv = new ModelAndView("/pessoa/formPessoa");
		val pessoa = pessoaService.getPessoaById(id);
		mv.addObject("pessoa", pessoa);
		mv.addObject("address", pessoa.getAddress());
		mv.addObject("bancos", bancos.findAll());
		mv.addObject("pessoaBanco", pessoaBanco);
		//mv.addObject("newPessoaBanco", new PessoaBanco());
		mv.addObject("pessoaCBanco", pessoaBancos.findByPessoaId(pessoa.getId()));
		return mv;
	}
	
	@GetMapping(value="/pessoaBanco/{id}")
	public ModelAndView addContaBanco(@PathVariable(name="id")Pessoa id) {
		ModelAndView mv = new ModelAndView("redirect:/pessoas/edit/1#cco");
		return mv;
	}

}
