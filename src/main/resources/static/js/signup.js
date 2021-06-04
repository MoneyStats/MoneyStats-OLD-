$(document).ready(function () {
  function addUser(user){
      $.ajax({
          type: "POST",
          url: "/signup/add",
          data: JSON.stringify(user),
          contentType: 'application/json',
          success: function (response) {
              console.log("Sono dentro")
              Swal.fire({
                  icon: 'success',
                  title: 'INSERITO!',
                  text: 'Utente inserito Correttamente',
                  showConfirmButton: false,
                  timer: 1500
              }),
              setTimeout(function (render) {
                  window.location.href='loginpage.html';
              }, 2000)
          },
          error: function () {
              Swal.fire({
                    icon: 'error',
                    title: 'ATTENZIONE!',
                    text: 'Riprova'
                  })           
          }
      });
  }

  $('#registrati').click(function (){
      const user = {
          nome: $('#nome').val(),
          cognome: $('#cognome').val(),
          datadinascita: $('#ddn').val(),
          email: $('#mail').val(),
          username: $('#username').val(),
          password: $('#password').val()
      }
      addUser(user);

      $('#nome').val('');
      $('#cognome').val('');
      $('#ddn').val('');
      $('#mail').val('');
      $('#username').val('');
      $('#password').val('');
  })

  /*
  $('#form').submit(function(){
      var username = $('#usernamelogin').val();
      $.ajax({
          url: $('#form').attr('action'),
          type: 'POST',
          data : $('#form').serialize(),
          async: false,
          success: function(result){
          console.log(result.location)
          if (Boolean (result) == true){
              Swal.fire({
                  icon: 'success',
                  title: 'Credenziali corrette!',
                  text: `Benvenuto ${username}`,
                  showConfirmButton: false,
                  timer: 1500
              }),
              setTimeout(function (render) {
                  window.location.href='homepage.html';
              }, 3000)
          } else {
              Swal.fire({
                    icon: 'error',
                    title: 'Credenziali Errate!',
                    text: 'Riprova'
                  })           
          }
          }
      
      });
});*/
});