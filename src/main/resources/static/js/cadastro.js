function enviar() {
    let conferir = ["Usuario","Apelido","Senha"];

    for (let i = 0; i<conferir.length; i++) {
        if ($('#'+conferir[i]).val() != $('#'+conferir[i]+'Conf').val()) {
            return;
        }
    }

    $("form").trigger("submit");
}

$("#cadastrar").click(enviar)