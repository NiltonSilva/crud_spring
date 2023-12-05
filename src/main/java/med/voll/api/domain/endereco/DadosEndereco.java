package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		
		@NotBlank
		String logradouro, 
		
		@NotBlank
		String bairro, 
		
		@NotBlank
		@Pattern(regexp="\\d{8}")	// 'd' de dígitos, '{8}' são 8 dígitos
		String cep, 
		
		@NotBlank
		String cidade, 
		
		@NotBlank
		String uf, 
		
		String complemento,  
		
		String numero
) {

}
