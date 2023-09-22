window.onload = () => listarPessoas();

function listar(dados){
    while($("[name='linha']").length) $('#linha').remove();
    dados.forEach(pessoa => criaLinha(pessoa));
}

function listarPessoas(){
    $.ajax({
        method: "GET",
        url: "http://localhost:8081/integrante/",
        dataType: 'json',
        success: function (dados){
            listar(dados);
        },
        error: function(dados){
            console.log(dados);
        }
    }).fail(function(err){
        tratarErro(err)
    });
}

function criaLinha(dados){
    $("#dados").append(
        '<div class="linha" id="linha" name="linha">'+
            '<div class="coluna-1"><p class="texto">'+dados.nome+'</p></div>'+
            '<div class="coluna-2"><p class="texto">'+dados.franquia+'</p></div>'+
            '<div class="coluna-3"><p class="texto">'+dados.funcao+'</p></div>'+
            '<div class="coluna-4">'+
                '<button class="editar"  onclick="editar('+dados.id+')">Editar</button>'+
                '<button class="excluir" onclick="deletar('+dados.id+')">Excluir</button>'+
            '</div>'+
        '</div>'
    );
}

function editar(codigo){
    $.ajax({
        method: "GET",
        url: "/pessoa/"+codigo,
        success: function (dados){
            $("#codigo").val(dados.codigo);
            $("#nome").val(dados.nome);
            $("#idade").val(dados.idade);
            $("#email").val(dados.email);
        }
    }).fail(function(err){
        tratarErro(err)
    });
}

function deletar(codigo){
    $.ajax({
        method: "DELETE",
        url: "/pessoa/"+codigo,
        success: function (dados){
            gerarMessageBox(1, dados, "Prosseguir");
        }
    }).fail(function(err){
        tratarErro(err)
    });
}

function ordenar(filtro){
    if(filtro == "escolha") gerarMessageBox(2, "Por favor escolha uma das opções", "Tentar novamente");
    else{
        $.ajax({
            method: "GET",
            url: "/pessoa/"+filtro,
            success: function (dados){
                listar(dados);
            }
        }).fail(function(err){
            tratarErro(err)
        });
    }
}

function pesquisar(){
    if(!$("#pesquisar").val().length) location.reload();
    $.ajax({
        method: "GET",
        url: "/pessoa/trecho/"+$("#pesquisar").val(),
        success: function (dados){
            listar(dados);
        }
    }).fail(function(err){
        tratarErro(err)
    });
}