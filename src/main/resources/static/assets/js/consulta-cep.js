var nuCep = document.querySelector('#nuCep');

function consultaCep() {
		
	var getCep = nuCep.value;
	
	Array.prototype.forEach.call(nuCep, function(node){
		node.parentNode.removeChild(node);
	});
	
	axios.get(`https://viacep.com.br/ws/`+getCep+`/json/`)
		.then(function(response){
			//console.log(response.data.logradouro);
			var inputNmLogradouro = document.querySelector('#nmLogradouro');
			var nmLogradouro = document.createTextNode(response.data.logradouro);
			
			inputNmLogradouro.setAttribute('value', nmLogradouro.data);
			
			var inputNmBairro = document.querySelector('#nmBairro');
			var nmBairro = document.createTextNode(response.data.bairro);
			inputNmBairro.setAttribute('value', nmBairro.data);
			
			var inputNmMunicipio = document.querySelector('#nmLocalidade');
			var nmMunicipio = document.createTextNode(response.data.localidade);
			inputNmMunicipio.setAttribute('value', nmMunicipio.data);
			
			var inputNmUf = document.querySelector('#nmUf');
			var nmUf = document.createTextNode(response.data.uf);
			inputNmUf.setAttribute('value', nmUf.data);
			
		})
		.catch(function(error){
			console.warn(error);
		});
}