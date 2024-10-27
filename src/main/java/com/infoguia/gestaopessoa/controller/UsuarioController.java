package com.infoguia.gestaopessoa.controller;


import javax.servlet.http.HttpServletRequest;

//import javax.validation.Valid;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import com.infoguia.gestaopessoa.model.User;
import com.infoguia.gestaopessoa.repository.Permissoes;
import com.infoguia.gestaopessoa.repository.UserRepository;

import com.infoguia.gestaopessoa.service.UserService;
import com.infoguia.gestaopessoa.validator.UserValidator;


@Controller
@Slf4j
public class UsuarioController {

	private UserRepository users;
	private UserService usuarioService;
	private Permissoes permissoes;
    private UserValidator userValidator;

	public UsuarioController(UserRepository users, UserService usuarioService,
							 Permissoes permissoes, UserValidator userValidator){
		this.users = users;
		this.usuarioService = usuarioService;
		this.permissoes = permissoes;
		this.userValidator = userValidator;
	}

 	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) boolean error,
						final Model model){
		if(error)
			model.addAttribute("error", "Your username and/or password is invalid.");
		return "authentication/login";
	}	
	
	@GetMapping("/usuario")
	public ModelAndView getUsuario(@PageableDefault(size=6)Pageable pageable,HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/listarUsuario");
		
		Pageable allUsuariosSortedById = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
													   Sort.by("id"));
		
		PageWrapper<User> pageWrapper = new PageWrapper<>(
				users.findAll(allUsuariosSortedById),httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
	@GetMapping("usuario/novoUsuario")
	public ModelAndView novoUsuario(User user) {
		ModelAndView mv = new ModelAndView("usuario/formUsuario");
		mv.addObject("ListaPermissoes", permissoes.findAll());
		
		mv.addObject("userForm", new User());
		return mv;
	}
	
	@PostMapping("usuario/salvar")
	//public ModelAndView salvarUsuario(@Valid User user, Errors errors) {
	public ModelAndView salvarUsuario(@ModelAttribute("userForm")User userForm, BindingResult result)	{
		
		ModelAndView mv = new ModelAndView("usuario/formUsuario");
		mv.addObject("ListaPermissoes", permissoes.findAll());
		
		userValidator.validate(userForm, result);
		//if(null != errors && errors.getErrorCount()>0) {
		if(result.hasErrors()) {
			return mv;
			
		}else if(userForm.getId() == null){
			usuarioService.salvar(userForm);

		}else {
			usuarioService.editar(userForm);
			
		}
		
		return new ModelAndView("redirect:/usuario");
	}	
	
	@GetMapping("usuario/edit/{id}")
	public ModelAndView usuarioEdit(@PathVariable(name="id")Long id) {
		User user = users.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid user id: " + id));
		
		ModelAndView mv = new ModelAndView("usuario/formUsuario");
		
		mv.addObject("userForm", user);
		mv.addObject("ListaPermissoes", permissoes.findAll());
		return mv;
	}
}
