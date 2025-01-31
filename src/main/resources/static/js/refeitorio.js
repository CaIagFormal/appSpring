console.log(data);

var selecionados = [];
var qtd_sel = [];
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
    qtd_sel.push(1);

    calcularTotal();

    let valor = parseFloat(data[id].preco);
    let resultado = "<div class=\"meache\">\n"
    resultado += "\t<p class=\"mb-0\"><b>Produto:</b> "+data[id].descricao+"</p>\n"
    resultado += "\t<div class=\"row mb-0\">\n"
    resultado += "\t\t<div class=\"col-6\">\n"
    resultado += "\t\t\t<p class=\"mb-0\"><b>Valor/Qtd:</b> R$"+valor.toFixed(2)+"</p>\n"
    resultado += "\t\t</div>\n"
    resultado += "\t\t<div class=\"col-6\">\n"
    resultado += "\t\t\t<p class=\"mb-0\"><b>Preço:</b> R$<span class=\"total\">"+valor.toFixed(2)+"</span></p>\n"
    resultado += "\t\t</div>\n"
    resultado += "\t</div>\n"
    resultado += "\t<div class=\"row mb-3\">\n"
    resultado += "\t\t<div class=\"col-4\">\n"
    resultado += "\t\t\t<div class=\"input-group\">\n"
    resultado += "\t\t\t\t<b class=\"input-group-text\">Qtd: </b>"
    resultado += "<input type=\"number\" min=\"1\" max=\"99\" value=\"1\" class=\"form-control quantidade\">\n"
    resultado += "\t\t\t</div>\n"
    resultado += "\t\t</div>\n"
    resultado += "\t\t<div class=\"col-8 text-end\">\n"
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
    let quantidade = $(item).find(".quantidade");
    let valor_total = $(item).find(".total");
    $(remover).click(function() {
        qtd_sel.splice(selecionados.indexOf(id),1)
        selecionados.splice(selecionados.indexOf(id),1)
        if ($("#codigo").val()==data[id].cod_barras){
            $("#codigo").val("");
        }
        calcularTotal();
        item.remove();
    });
    $(quantidade).change(function() {
        let qtd_val = $(quantidade).val();
        if (qtd_val<1) {
            $(quantidade).val(1);
            qtd_val = 1;
        } else if (qtd_val>99) {
            $(quantidade).val(99);
            qtd_val = 99;
        } else if (qtd_val%1>0) {
            $(quantidade).val(qtd_val-(qtd_val%1));
            qtd_val = $(quantidade).val();
        }
        $(valor_total).text((qtd_val*valor).toFixed(2));
        qtd_sel[selecionados.indexOf(id)] = qtd_val;
        calcularTotal();
    });
}

function calcularTotal() {
    let total = 0.0;
    for (var i = 0; i < qtd_sel.length; i++) {
        total += qtd_sel[i]*parseFloat(data[selecionados[i]].preco);
    }
    $("#total_carrinho").text(total.toFixed(2));
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

function enviarmds() {
    if (selecionados.length==0) {
        return;
    }

    lista = ""
    for (let i = 0; i < selecionados.length; i++) {
        lista += selecionados[i]+","+qtd_sel[i]+"|";
    }
    lista = lista.slice(0,lista.length-1);
    $("#lista_itens").val(lista);
    console.log($("#lista_itens")[0]);
    //$("form").trigger("submit");
}

$("#lercamera").click(lerProduto);

$("#produto").change(adicionarPartindoDeTexto);

$("#incluir").click(enviarmds);