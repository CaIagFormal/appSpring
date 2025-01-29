console.log(data);

var selecionados = [];
function lerProduto() {
    let tentativas_barra = [];

    Quagga.init({
        inputStream : {
          name : "Live",
          type : "LiveStream",
          target: $('#camera')[0]
        },
        decoder : {
          readers : ["code_128_reader"]
        }
      }, function(err) {
          if (err) {
              console.log(err);
              return
          }
          console.log("Initialization finished. Ready to start");
          Quagga.start();
          $("#camera").children('canvas').attr('height','0');
          $("#camera").children('video').attr('width','100%')
    });

    Quagga.onDetected(function (data){

        if (tentativas_barra.length==0) {
            tentativas_barra = [data.codeResult.code];
            return;
        }

        if (tentativas_barra[tentativas_barra.length-1]!=data.codeResult.code) {
            tentativas_barra = []
            return;
        }

        if (tentativas_barra.length<3) {
            tentativas_barra.push(data.codeResult.code);
            return;
        }

        tentativas_barra = []

        for (var i = 0; i<data.length; i++) {
            if (data[i].cod_barras == data.codeResult.code) {
                break;
            }
        };

        adicionarAoCarrinho(i);

    })
}

function adicionarAoCarrinho(id) {
    if (id==data.length||selecionados.indexOf(id)>-1) {
        return;
    }
    // prevenir multiplos do mesmo
    selecionados.push(id);

    let resultado = "<div class=\"meache\">\n"
    resultado += "\t<p class=\"mb-0\"><b>Produto:</b> "+data[id].descricao+"</p>\n"
    resultado += "\t<p class=\"mb-0\"><b>Valor:</b> R$"+parseFloat(data[id].preco).toFixed(2)+"</p>\n"
    resultado += "\t<div class=\"row mb-3\">\n"
    resultado += "\t\t<div class=\"col-6\">"
    resultado += "\t\t\t<div class=\"input-group\">"
    resultado += "\t\t\t\t<b class=\"input-group-text\">Qtd: </b>"
    resultado += "<input type=\"number\" min=\"1\" max=\"99\" value=\"1\" class=\"form-control quantidade\">\n"
    resultado += "\t\t\t</div>\n"
    resultado += "\t\t</div>\n"
    resultado += "\t\t<div class=\"col-2\">\n"
    resultado += "\t\t\t<button class=\"btn btn-danger remover\">X</button>\n"
    resultado += "\t\t</div>\n"
    resultado += "\t</div>\n"
    resultado += "</div>"
    $("#carrinho").append(resultado);
    let item = $("#carrinho").children(".meache")
    item.removeClass("meache")
    console.log(item);
    $("#codigo").val(data[id].cod_barras);

    let remover = $(item).find(".remover");
    remover.click(function() {
        selecionados.splice(selecionados.indexOf(id),1)
        if ($("#codigo").val()==data[id].cod_barras){
            $("#codigo").val("");
        }
        item.remove();
    });

}

function adicionarPartindoDeTexto() {
    let produto = $("#produto").val();

    for (var i = 0; i<data.length; i++) {
        if (data[i].descricao == produto) {
            break;
        }
    };

    adicionarAoCarrinho(i);

    $("#produto").val("");
}

$("#lercamera").click(lerProduto);

$("#produto").change(adicionarPartindoDeTexto);