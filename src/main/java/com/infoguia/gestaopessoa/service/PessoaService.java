package com.infoguia.gestaopessoa.service;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoguia.gestaopessoa.model.Address;
import com.infoguia.gestaopessoa.model.Pessoa;
import com.infoguia.gestaopessoa.repository.AddressRepository;
import com.infoguia.gestaopessoa.repository.Pessoas;

@Service
public class PessoaService {
	@Autowired
	private Pessoas pessoas;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Transactional
	public void salvar(Pessoa pessoa, Address address) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dtNascimento = pessoa.getDtNascimento() == null ? sdf.parse("1900-01-01") : pessoa.getDtNascimento();
		
		if(addressRepo.findByNuCep(address.getNuCep()) != null) {
			
			
			
			pessoas.InserirPessoa(pessoa.getNome(), pessoa.getNuCpf(), 
		             addressRepo.cepId(address.getNuCep()), 
		             pessoa.getNuEndereco(), pessoa.getNmComplemento(), pessoa.getNmEmail(),
		             dtNascimento);
			
		}	else {
			addressRepo.save(address);
			pessoas.save(pessoa);
			
			pessoas.atualizaPessoaCep(addressRepo.cepId(address.getNuCep()), pessoa.getId());			
			
		}
		
		
	}
	
	@Transactional
	public void editar(Pessoa pessoa, Address address) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(addressRepo.findByNuCep(address.getNuCep()) == null) {
									
			address.setNuCep(address.getNuCep());
			address.setNmBairro(address.getNmBairro());
			address.setNmLocalidade(address.getNmLocalidade());
			address.setNmLogradouro(address.getNmLogradouro());
			address.setNmUf(address.getNmUf());
			
			addressRepo.save(address);	
        
		}	
		
			Date dtNascimento = pessoa.getDtNascimento() == null ? sdf.parse("1900-01-01") : pessoa.getDtNascimento();
			
			pessoas.atualizaPessoa(pessoa.getNome(), pessoa.getNuCpf(), 
					             addressRepo.cepId(address.getNuCep()), 
					             pessoa.getNuEndereco(), pessoa.getNmComplemento(),  pessoa.getId(),
					             pessoa.getNmEmail(),dtNascimento);
		
	}
	
}
