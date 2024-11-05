package com.infoguia.gestaopessoa.controller;

import com.infoguia.gestaopessoa.model.Banco;
import com.infoguia.gestaopessoa.model.PessoaBanco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest implements Serializable {
    @NotEmpty(message="Nome é obrigatório")
    private String nome;
    @NotEmpty(message="Cpf é obrigatório")
    @Pattern(regexp="[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]-[0-9][0-9]", message="cpf inválido!")
    private String nuCpf;
    private AddressRequest address;
    private String nmComplemento;
    private String nuEndereco;
    private String nmEmail;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dtNascimento;
    private Long id;
    private String nuAgencia;
    private String nuContaCorrente;
}
