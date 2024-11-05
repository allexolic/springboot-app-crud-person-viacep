package com.infoguia.gestaopessoa.service;

import javax.transaction.Transactional;

import com.infoguia.gestaopessoa.controller.AddressRequest;
import com.infoguia.gestaopessoa.controller.PessoaRequest;
import com.infoguia.gestaopessoa.model.Banco;
import com.infoguia.gestaopessoa.model.PessoaBanco;
import com.infoguia.gestaopessoa.repository.BancosRepository;
import com.infoguia.gestaopessoa.repository.PessoaBancos;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.stereotype.Service;

import com.infoguia.gestaopessoa.model.Address;
import com.infoguia.gestaopessoa.model.Pessoa;
import com.infoguia.gestaopessoa.repository.AddressRepository;
import com.infoguia.gestaopessoa.repository.Pessoas;

import java.util.Optional;

@Slf4j
@Service
public class PessoaService {
	private Pessoas pessoas;
	private AddressRepository addressRepository;
	private PessoaBancos pessoaBancos;
	private BancosRepository bancosRepository;

	public PessoaService(Pessoas pessoas, AddressRepository addressRepository, PessoaBancos pessoaBancos, BancosRepository bancosRepository) {
		this.addressRepository = addressRepository;
		this.pessoas = pessoas;
		this.pessoaBancos = pessoaBancos;
		this.bancosRepository = bancosRepository;
	}

	@Transactional
	public void salvar(PessoaRequest request, AddressRequest requestAddress) {
		val documento = request.getNuCpf().replace(".", "").replace("-", "");
		val pessoa = pessoas.findByNuCpf(documento);
		if( pessoa == null ) {
			val newPessoa = new Pessoa();
			savePessoa(newPessoa, request, requestAddress);
			log.info("User add successful.");
		}else {
			savePessoa(pessoa, request, requestAddress);
			log.info("User updated successful.");
		}
	}

	private void savePessoa(Pessoa pessoa, PessoaRequest request, AddressRequest requestAddress) {
		val responsePessoa = addPessoa(pessoa,request,requestAddress);
		addPessoaBanco(request, responsePessoa);
	}

	private void addPessoaBanco(PessoaRequest request, Pessoa responsePessoa) {
		val newPessoaBanco = new PessoaBanco();
		Optional<Banco> banco = bancosRepository.findById(request.getId());
		newPessoaBanco.setBanco(banco.get());
		newPessoaBanco.setPessoa(responsePessoa);
		newPessoaBanco.setNuAgencia(request.getNuAgencia());
		newPessoaBanco.setNuContaCorrente(request.getNuContaCorrente());
		pessoaBancos.save(newPessoaBanco);
	}

	private Pessoa addPessoa(Pessoa pessoa, PessoaRequest request, AddressRequest requestAddress) {
		pessoa.setNome(request.getNome());
		pessoa.setNuCpf(request.getNuCpf().replace(".", "").replace("-", ""));
		pessoa.setAddress(addAddress(requestAddress));
		pessoa.setNmEmail(request.getNmEmail());
		pessoa.setDtNascimento(request.getDtNascimento());
		pessoa.setNuEndereco(request.getNuEndereco());
		pessoa.setNmComplemento(request.getNmComplemento());
		return pessoas.save(pessoa);
	}

	private Address addAddress(AddressRequest request) {
		val address = addressRepository.findByNuCep(request.getNuCep());
		if(address != null) {
			return address;
		}
		val newAddress = new Address();
		newAddress.setNuCep(request.getNuCep());
		newAddress.setNmBairro(request.getNmBairro());
		newAddress.setNmUf(request.getNmUf());
		newAddress.setNmLocalidade(request.getNmLocalidade());
		newAddress.setNmLogradouro(request.getNmLogradouro());
		return addressRepository.save(newAddress);
	}

	public Pessoa getPessoaById(Long id) {
		return pessoas.findById(id).get();
	}
}
