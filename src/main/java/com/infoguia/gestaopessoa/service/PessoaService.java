package com.infoguia.gestaopessoa.service;



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
	public void salvar(Pessoa pessoa, Address address) {
		
		if(addressRepo.findByNuCep(pessoa.getAddress().getNuCep()) != null) {
			
			pessoas.InserirPessoa(pessoa.getNome(), pessoa.getNuCpf(), 
		             addressRepo.cepId(pessoa.getAddress().getNuCep()), 
		             pessoa.getNuEndereco(), pessoa.getNmComplemento(), pessoa.getNmEmail(),
		             pessoa.getDtNascimento() );
			
		}	else {
		
			pessoas.save(pessoa);
			
			
		}
		
		
	}
	
	@Transactional
	public void editar(Pessoa pessoa, Address address) {
				
		if(addressRepo.findByNuCep(pessoa.getAddress().getNuCep()) == null) {
									
			address.setNuCep(pessoa.getAddress().getNuCep());
			address.setNmBairro(pessoa.getAddress().getNmBairro());
			address.setNmLocalidade(pessoa.getAddress().getNmLocalidade());
			address.setNmLogradouro(pessoa.getAddress().getNmLogradouro());
			address.setNmUf(pessoa.getAddress().getNmUf());
			
			addressRepo.save(address);	
        
		}	
			
			pessoas.atualizaPessoa(pessoa.getNome(), pessoa.getNuCpf(), 
					             addressRepo.cepId(pessoa.getAddress().getNuCep()), 
					             pessoa.getNuEndereco(), pessoa.getNmComplemento(),  pessoa.getId(),
					             pessoa.getNmEmail(),pessoa.getDtNascimento());
		
	}
	
}
