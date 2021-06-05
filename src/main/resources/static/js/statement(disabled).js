$(document).ready(function () {
    function getWallet(){
        $.get('/wallet/list', function (resume) {
            console.log(resume);
            const listWallet = $('#listWallet');
            var wallet;
            for (let i = 0; i < resume.length; i++) {
                wallet = [resume[i].id];
                $(`<div class="col"}>
                <div class="card h-100">
                  <img style="background-size: cover; " src="https://blog.revolut.com/content/images/2018/05/Revolut-Metal-Card---Blue-2.png" class="card-img h-100" alt="...">
                  <div class="card-img-overlay">
                  <div class="card h-100" style="background-color: rgba(255, 255, 255, 0.7);">
                  <div class="card-body">
                    <h5 class="card-title">Portafoglio ${resume[i].name}</h5>
                    <input type='hidden' id="wallet" value="${resume[i].id}" data-id='${resume[i].id}>
                    <p class="card-text">Inserire quantitativo in £GBP</p>
                    <div class='form-floating mb-3'> 
                      <input style="background-color: rgba(255, 255, 255, 0.7);" type='number' id="value" name='value' class='form-control roundedCorner mx-auto' placeholder="Inserire Valore in £GBP..." required>
                      <label for='value'>Inserire Valore in £GBP</label>
                  </div>
                  <button type="button" class="btn btn-outline-success float-end" id="aggiungiStatement">Conferma</button>
                  </div>
                  </div>
                </div>
                </div>
              </div>`).hide().appendTo(listWallet).fadeIn(i * 20);
            }
        })
    }
    getWallet();
    $('#listWallet').on('click', '#aggiungiStatement',function () {
            let idWallet = $(this).attr('data-id');
            var statement = {
                value: $('#value').val(),
                date: $('#date').val(),
                wallet: {
                    id: $('#wallet').val(),
                }
            }
            addStatement(statement);

        $('#value').val('');
        $('#date').val('');
        $('#wallet').val('');
      })

    function addStatement(statement) {
        console.log("Dentro funzione")
        $.ajax({
            type: "POST",
            url: "/statement/post",
            data: JSON.stringify(statement),
            contentType: 'application/json',
            success: function (response) {
                console.log("Sono aggiunto")
            }
        });
    }
});