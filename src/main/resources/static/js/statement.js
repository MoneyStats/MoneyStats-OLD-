$(document).ready(function () {
    function getWallet(){
        $.get('/wallet/list', function (resume) {
            const listWallet = $('#listWallet');
            for (let i = 0; i < resume.length; i++) {
                document.cookie = "id"+i+" = " + resume[i].id;
                $(`<div class="col-xl-3 col-sm-6" style="margin-bottom: 30px;">
                <div class="card card-stats mb-4 mb-xl-0">
                  <div class="card-body">
                  <div class="row">
                      <div class="col">
                      <h5 class="card-title text-uppercase text-muted mb-0">${resume[i].name} (${resume[i].category.name})</h5>
                      <hr>
                      <div class='form-floating mb-3'> 
                      <input style="background-color: rgba(255, 255, 255, 0.7);" type='number' id="value${resume[i].id}" class='form-control roundedCorner mx-auto' placeholder="Inserire £GBP..." required>
                      <label for='value'>Inserire £GBP</label>
                  </div>
                      <input type='hidden' id="wallet${resume[i].id}" value="${resume[i].id}">
                      </div>
                      <div class="col-auto">
                      <div class="icon icon-shape bg-danger text-white rounded-circle shadow">
                          <i class="fas fa-chart-bar"></i>
                          </div>
                          </div>
                    </div>
                    <p class="mt-3 mb-0 text-muted text-sm">
                    <span class="text-nowrap sincetot">Inserire Valori</span>
                      </p>
                      </div>
                `).hide().appendTo(listWallet).fadeIn(i * 20);
            }
          })
        }
        getWallet();
        
      $('#aggiungiStatement').click(function () {
        var cookie = document.cookie;
        cookiearray = cookie.split(';');
        for (let i = 0; i < cookiearray.length; i++){
          valueCookie = cookiearray[i].split('=')[1];
          $(`#value${valueCookie}`).prop('required', true);
        }
        Swal.fire({
            title: 'Vuoi salvare lo Statement Corrente?',
            showDenyButton: true,
            confirmButtonText: `Salva`,
            denyButtonText: `Non Salvare`,
            icon: 'question',
          }).then((result) => {
            if (result.isConfirmed) {
                
                console.log(cookiearray);
                for (let i = 0; i < cookiearray.length; i++){
                    console.log("key is" + cookiearray[i]);
                    valueCookie = cookiearray[i].split('=')[1];
                    console.log("cookie is" + cookiearray[i]);
                    console.log("key is" + valueCookie);
                    var statement = {
                        value: $(`#value${valueCookie}`).val(),
                        date: $('#date').val(),
                        wallet: {
                            id: $(`#wallet${valueCookie}`).val(),
                        }
                    }
                    addStatement(statement);

                    $(`#value${valueCookie}`).val('');
                    $(`#wallet${valueCookie}`).val('');
                }
                $('#date').val('');
                document.cookie.split(";").forEach(function(c) { document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); });
                Swal.fire({
                    title: 'Salvataggio Completato!',
                    text: "Effettuare un nuovo Statement?",
                    showDenyButton: true,
                    confirmButtonText: `Si`,
                    denyButtonText: `No, Torna alla Homepage`,
                    icon: 'success'
                  }).then((result) => {
                    if (result.isConfirmed) {
                    } else if (result.isDenied) {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true,
                          })
                          
                          Toast.fire({
                            icon: 'success',
                            title: 'Renderizzazione Homepage...'
                          })
                          setTimeout(function () {
                            window.location.href = "homepage.html";
                          }, 1000);
                      }
                    })
            } else if (result.isDenied) {
              Swal.fire(
                'Operazione Interrotta',
                'Statement non Aggiunto!',
                'error'
              )
            }
          })        
  })

    function addStatement(statement) {
        $.ajax({
            type: "POST",
            url: "/statement/post",
            data: JSON.stringify(statement),
            contentType: 'application/json',
            success: function (response) {
            }
        });
    }

    $('.resetCookies').on('click', function resetCookies(){
      document.cookie.split(";").forEach(function(c) { document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); });
    })

});

