function verificar() {
    var erros = [];
    var numeroErros = 0;

    var nome = $("#nome").val();
    var funcao = $("#funcao").val();
    var franquia = $("#franquia").val();




    if (nome.substring() == null || nome === "") {
        erros.push("<br><br>- É necessário que seu nome tenha mais de  '2 letras'");
        numeroErros++;
        $("#nome").val(" ")
    }

    if (numeroErros > 0) gerarMessageBox(2, "Erro(s):" + erros, "Tentar novamente");
    else salvar();
}

function salvar() {
    $.ajax({
        method: "POST",
        url: "http://localhost:8081/integrante",
        data: JSON.stringify({
                franquia: $('#franquia').val(),
                nome: $("#nome").val(),
                funcao: $("#funcao").val(),

            }),
        contentType: "application/json",
        success: function (dados) {

            gerarMessageBox(1, "Cadastro Realizado com sucesso!!");

        },
        error: function (dados) {
            gerarMessageBox(1, dados);

        }
    }).fail(function (dados) {
        tratarErro(dados);
    });
}