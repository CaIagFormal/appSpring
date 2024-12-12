function logOut() {
    Swal.fire({
      title: "Tem certeza que quer sair?",
      text: "Você será desconectado e será necessário realizar login novamente para acessar os recursos da página",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      cancelButtonText: "Não, eu quero RAMMMM",
      confirmButtonText: "Sim, eu quero um pc lerdo"
    }).then((result) => {
      if (result.isConfirmed) {
        $.get("/logout",
        function(response){
        if(response) {
        window.location.href="/"
        }
        });
      }
    });

}
$("#btn-logout").click(logOut);