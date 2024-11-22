$('#achar').click(function() {
        let checkout = $('#chkout').val()
        if (!checkout) {
            alert("Insira uma data de checkout");
            return;
        }
        
        $.ajax({
        url: "/getQuartosDisp",
        method: "POST",
        data: {checkout:checkout,quarto:'Reset'},
        success: function(response){
            $('select').html(response);
            if ($('select').children.length>0) {
                $('#achado').removeClass('d-none')
                $('#chkout2').val(checkout)
                return;
            }
            $('#achado').addClass('d-none')
        },
        error: function(){
            alert("Erro ao comunicar com o servidor");
        }
    });
});
