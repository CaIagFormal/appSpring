// Mostrar total gasto
let total_gasto=0.0;

$(".gasto").each(function(index) {
    total_gasto += parseFloat($(this).text());
});

$("#total_gasto").text(total_gasto.toFixed(2));

// Mostrar total previsto
let total_previsto=0.0;

$(".previsto").each(function(index) {
    total_previsto += parseFloat($(this).text());
});

$("#total_previsto").text(total_previsto.toFixed(2));

$(".btn-refeitorio").click(function() {
    let id = $(this).parent().parent().children(".id_locacao")[0];
    console.log(id);
    $("#id").val($(id).text());
    $("form").trigger('submit');
})