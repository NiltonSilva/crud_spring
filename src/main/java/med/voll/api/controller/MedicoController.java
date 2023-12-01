package med.voll.api.controller;

import java.util.List;

import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.Endereco;

@RestController
@RequestMapping("medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository repository;

	@PostMapping
	@Transactional		// esse eu uso quando for adicionar, excluir ou editar informações do banco de dados
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		repository.save(new Medico(dados));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
		// por padrão o backend me traz 20 registros e ordenados pela ordem de cadastro no banco de dados. Essa notação @PageableDefault ajuda a configurar quantos registros vem por requisição e se vem ordenado por alguns dos parâmetros. 
		/*
			nesta opção de retorno ele retorna todos os médicos, sejam os ativos sejam os inativos. 
			return repository.findAll(paginacao).map(DadosListagemMedico::new);
		*/
		// para nao retornar todos os dados que compoem o cadastro do médico eu faço um DTO (DadosListagemMedico) para os parametros que desejo exibir, trago para cá e converto o Médico que recebo pelo Repository em DadosCadastroMédico.
		
		//aqui vou chamar um método que eu criei no meu repository para retornar somente os médicos que estão ativos.
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);

		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir(@PathVariable Long id) {	// @PathVarible é pra informar que o "id" que está como parâmetro é o mesmo que vem da URL
		/*
			repository.deleteById(id);		// dessa forma ele apaga de fato o registro do banco de dados. O ideal é que seja uma 'exclusão lógica', ou seja, só tornar o registro inativo para não aparecer na listagem. Pois apagar o registro de fato do bando de dados pode ocasionar problema, caso ele esteja relacionado a alguma outra tabela.
		*/
		
		var medico = repository.getReferenceById(id);
		medico.excluir();

		return ResponseEntity.noContent().build();
	}
	
	
}
