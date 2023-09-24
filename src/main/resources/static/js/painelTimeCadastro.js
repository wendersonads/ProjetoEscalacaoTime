window.onload = () => listarIntegrantes();



function listar(dados) {
    while ($("[name='linha']").length) $('#linha').remove();
    dados.forEach(pessoa => criaLinha(pessoa));
}

function listarIntegrantes() {
    $.ajax({
        method: "GET",
        url: "http://localhost:8081/integrante/",
        dataType: 'json',
        success: function (dados) {
            listar(dados);
        },
        error: function (dados) {
            console.log(dados);
        }
    }).fail(function (err) {
        tratarErro(err)
    });
}

function criaLinha(dados) {
    $("#dados").append(
        '<div class="linha" id="linha" name="linha">' +
        '<div class="coluna-1"><p class="texto">' + dados.nome + '</p></div>' +
        '<div class="coluna-2"><p class="texto">' + dados.franquia + '</p></div>' +
        '<div class="coluna-3"><p class="texto">' + dados.funcao + '</p></div>' +
        '<div class="coluna-4">' +
        '<input type="checkbox" name="selectIntegrante" value="' + dados.id + '"/>' +
        '</div>' +
        '</div>'
    );
}

function salvarTime() {
    $(document).ready(function () {
        $("button").click(function () {
            var selectedIds = [];
            $('input[name="selectIntegrante"]:checked').each(function () {
                selectedIds.push(this.value);
            });

            var selectedIdsJSON = [];
            selectedIdsJSON = JSON.stringify(selectedIds);
            console.log('IDs selecionados (JSON):', selectedIdsJSON);

            $.ajax({
                method: "POST",
                url: "http://localhost:8081/time/",
                dataType: 'json',
                data: selectedIdsJSON,
                contentType: "application/json",
                success: function (dados) {

                    gerarMessageBox(1, "Cadastro Realizado com sucesso!!");

                }
            }).fail(function (dados) {
                tratarErro(dados);
            });
        })

    })

}

function ordenar(filtro) {
    if (filtro == "escolha") gerarMessageBox(2, "Por favor escolha uma das opções", "Tentar novamente");
    else {
        $.ajax({
            method: "GET",
            url: "/pessoa/" + filtro,
            success: function (dados) {
                listar(dados);
            }
        }).fail(function (err) {
            tratarErro(err)
        });
    }
}

function pesquisar() {
    if (!$("#pesquisar").val().length) location.reload();
    $.ajax({
        method: "GET",
        url: "/pessoa/trecho/" + $("#pesquisar").val(),
        success: function (dados) {
            listar(dados);
        }
    }).fail(function (err) {
        tratarErro(err)
    });
}