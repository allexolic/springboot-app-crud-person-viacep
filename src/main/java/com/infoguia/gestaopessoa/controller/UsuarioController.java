package com.infoguia.gestaopessoa.controller;


import javax.servlet.http.HttpServletRequest;

//import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
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

	@Autowired
	private UserRepository users;
	
	@Autowired
	private UserService usuarioService;
	
	@Autowired
	private Permissoes permissoes;

    @Autowired
    private UserValidator userValidator;	
	
 	@GetMapping("/login")
	public String login(Model model, String error){
		 		
		if (error != null) {
			model.addAttribute("error", "Your username and/or password is invalid.");
			log.error("Error = " + error);
		}
		
		
		return "login";  
	}	
	
	@GetMapping("/usuario")
	public ModelAndView getUsuario(@PageableDefault(size=6)Pageable pageable,HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/listarUsuario");
		
		Pageable allUsuariosSortedById = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
													   Sort.by("id").descending());
		
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
