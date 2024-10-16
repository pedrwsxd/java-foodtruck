package br.projeto.spring.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable //Usaremos essa anotação em uma classe para informar que essa classe  possui instancias que fazem parte de uma entidade
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	
	 private String logradouro;
	 private String bairro;
	 private String cep;
	 private String cidade;
	 private String numero;
	 private String complemento;

	 public Endereco (DadosEndereco dados) {
		 
		 this.setLogradouro(dados.logradouro());
		 this.setNumero(dados.numero());
		 this.setBairro(dados.bairro());
		 this.setCep(dados.cep());

		 this.setCidade(dados.cidade());
		 this.setComplemento(dados.complemento());
	 }

	public void atualizarInformacoes(DadosEndereco dados) {
		
		  if(dados.logradouro() != null) {
		    	 this.setLogradouro(dados.logradouro());
		     }
				
		     if(dados.bairro() != null) {
		    	 this.setBairro(dados.bairro());
		     }
		     
		     if(dados.cep() != null) {
		    	 this.setCep(dados.cep());
		     }
		     
		     if(dados.cidade() != null) {
		    	 this.setCidade(dados.cidade());
		     }
		     if(dados.numero() != null) {
		    	 this.setNumero(dados.numero());
		     }
		
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
