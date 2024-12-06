$('#chkin').change(function() {
    $('#chkout').attr('min',$('#chkin').val());
})

$('#chkin').val((new Date()).toISOString().split('T')[0]);
$('#chkin').attr('min',$('#chkin').val());

let amanha = new Date();
amanha.setDate(amanha.getDate()+1);
$('#chkout').val(amanha.toISOString().split('T')[0]);

$('#achar').click(function() {
        let checkout = $('#chkout').val()
        if (!checkout) {
            alert("Insira uma data de checkout");
            return;
        }

        let checkin = $('#chkin').val()
        if (!checkin) {
            alert("Insira uma data de checkin");
            return;
        }
        
        $.ajax({
        url: "/getQuartosDisp",
        method: "POST",
        data: {checkin:checkin,checkout:checkout},
        success: function(response){
            $('select').html(response);
            if ($('select').children.length>0) {
                $('#achado').removeClass('d-none')
                $('#chkout2').val(checkout)
                $('#chkin2').val(checkin)
                return;
            }
            $('#achado').addClass('d-none')
        },
        error: function(){
            alert("Erro ao comunicar com o servidor");
        }
    });
});

$("#enviar").click(function() {
    let checkout = $('#chkout').val()
    if (!checkout) {
        alert("Insira uma data de checkout");
        return;
    }

    let checkin = $('#chkin').val()
    if (!checkin) {
        alert("Insira uma data de checkin");
        return;
    }

    let quarto = $('#quarto').val()
        if (!quarto) {
            alert("Selecione um quarto");
            return;
        }

    $.post({
        url: '/cadLocacao',
        method: 'POST',
        data: {checkin:checkin,checkout:checkout,quarto:quarto},
        success: function(response)
        {
            if (response.charAt(0)=="<") {
                $("body").empty();
                $("body").append(response);
                return;
            }
            $("#erro").removeClass("d-none");
            $("#erro").text(response);
        }
    });
});