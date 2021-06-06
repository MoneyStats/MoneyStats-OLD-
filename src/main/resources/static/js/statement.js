$(document).ready(function () {
    function getWallet(){
        $.get('/wallet/list', function (resume) {
            const listWallet = $('#listWallet');
            for (let i = 0; i < resume.length; i++) {
                document.cookie = "id"+i+" = " + resume[i].id;
                $(`<div class="col"}>
                <div class="card h-100">
                  <img style="background-size: cover; " src="https://blog.revolut.com/content/images/2018/05/Revolut-Metal-Card---Blue-2.png" class="card-img h-100" alt="...">
                  <div class="card-img-overlay">
                  <div class="card h-100" style="background-color: rgba(255, 255, 255, 0.7);">
                  <div class="card-body">
                    <h5 class="card-title">Portafoglio ${resume[i].name}</h5>
                    <input type='hidden' id="wallet${resume[i].id}" value="${resume[i].id}">
                    <p class="card-text">Inserire quantitativo in £GBP</p>
                    <div class='form-floating mb-3'> 
                      <input style="background-color: rgba(255, 255, 255, 0.7);" type='number' id="value${resume[i].id}" class='form-control roundedCorner mx-auto' placeholder="Inserire Valore in £GBP..." required>
                      <label for='value'>Inserire Valore in £GBP</label>
                  </div>
                  </div>
                  </div>
                </div>
                </div>
              </div>`).hide().appendTo(listWallet).fadeIn(i * 20);
            }
        })
    }
    getWallet();

    $('#aggiungiStatement').click(function () {
        Swal.fire({
            title: 'Vuoi salvare lo statement?',
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: `Salva`,
            denyButtonText: `Non Salvare`,
            icon: 'question',
          }).then((result) => {
            if (result.isConfirmed) {
                var cookie = document.cookie;
                cookiearray = cookie.split(';');
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
                    title: 'Salvataggio Completato! Vuoi effettuare un nuovo statement?',
                    showDenyButton: true,
                    showCancelButton: true,
                    confirmButtonText: `Si`,
                    denyButtonText: `No`,
                    icon: 'success'
                  }).then((result) => {
                    if (result.isConfirmed) {
                    } else if (result.isDenied) {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1500,
                            timerProgressBar: true,
                          })
                          
                          Toast.fire({
                            icon: 'success',
                            title: 'Reinderizzazione...'
                          })
                          setTimeout(function () {
                            window.location.href = "homepage.html";
                          }, 1500);
                      }
                    })
            } else if (result.isDenied) {
              Swal.fire('Changes are not saved', '', 'info')
            }
          })
        
        
  })

    function addStatement(statement) {
        console.log("Dentro funzione")
        $.ajax({
            type: "POST",
            url: "/statement/post",
            data: JSON.stringify(statement),
            contentType: 'application/json',
            success: function (response) {
            }
        });
    }

});

