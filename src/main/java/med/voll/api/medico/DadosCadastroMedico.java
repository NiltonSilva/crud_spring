package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosCadastroMedico(

		@NotBlank	// verifica se não é vazio e se não é nulo
		String nome, 
		
		@Email
		@NotBlank
		String email, 
		
		@NotBlank
		String telefone,
		
		@NotBlank
		@Pattern(regexp = "\\d{4,6}")	// 'd' de dígitos, '{4,6}' diz que varia de 4 a 6 digitos
		String crm, 
		
		@NotNull	// verificar se não é nulo
		Especialidade especialidade, 
		
		@NotNull
		@Valid	// diz que é pra verificar o outro DTO que tem dentro do endereço
		DadosEndereco endereco
) {

}
