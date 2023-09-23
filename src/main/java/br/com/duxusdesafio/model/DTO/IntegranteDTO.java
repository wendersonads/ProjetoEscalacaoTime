package br.com.duxusdesafio.model.DTO;

public class IntegranteDTO {

	private Long id;

	private String franquia;

	private String nome;
	
	private String funcao;
    

    
	public IntegranteDTO() {
	}

	public IntegranteDTO(String franquia, String nome, String funcao) {
		this.franquia = franquia;
		this.nome = nome;
		this.funcao = funcao;
	}
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getFranquia() {
		return franquia;
	}

	public void setFranquia(String franquia) {
		this.franquia = franquia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
}
