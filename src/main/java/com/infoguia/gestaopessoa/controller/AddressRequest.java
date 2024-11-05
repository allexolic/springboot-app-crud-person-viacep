package com.infoguia.gestaopessoa.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
@Data
public class AddressRequest {
    @NotEmpty(message="Consulte um CEP Valido!")
    private String nuCep;
    private String nmLogradouro;
    private String nmBairro;
    private String nmLocalidade;
    private String nmUf;
}
