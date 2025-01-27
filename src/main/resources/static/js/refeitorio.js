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
          $('#camera').css({"height":$("#camera").children('canvas').attr('height')});
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
        $("#resultados").append("<p><b>Produto:</b> Sei lá</p>\n<p><b>Valor:</b> Muito</p>");
        $("#codigo").val(data.codeResult.code)

    })
}

$("#lercamera").click(lerProduto);