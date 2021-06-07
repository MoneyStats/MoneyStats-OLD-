$(document).ready(function () {
    // WALLET NELLA CARD
    function getWallet(){
        $.get('/wallet/list', function (resume) {
            $('#title').text('Capitale ' + document.cookie);
         //   document.cookie.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/");
            console.log(document.cookie);
            const listWallet = $('#capitalewallet');
            for (let i = 0; i < resume.length; i++) {
                $(`<div class="col-xl-3 col-sm-6" style="margin-bottom: 30px;">
                <div class="card card-stats mb-4 mb-xl-0">
                  <div class="card-body">
                    <div class="row">
                      <div class="col">
                        <h5 class="card-title text-uppercase text-muted mb-0">${resume[i].name} (${resume[i].category.name})</h5>
                        <span class="h2 font-weight-bold mb-0" id="tot${resume[i].id}">£ 0.0</span>
                      </div>
                      <div class="col-auto">
                        <div class="icon icon-shape bg-danger text-white rounded-circle shadow">
                          <i class="fas fa-chart-bar"></i>
                        </div>
                      </div>
                    </div>
                    <p class="mt-3 mb-0 text-muted text-sm">
                      <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                      <span class="text-nowrap">Since last month</span>
                    </p>
                  </div>
                </div>
              </div>`).hide().appendTo(listWallet).fadeIn(i * 20);
            //  document.cookie = "id"+i+" = " + resume[i].id;
            }
        })
        
    }
    getWallet();

    var tot = 0;
    var totold = 0;
    var dataold;
    getStatement();
    function getStatement(){
      $.get(`/statement/statementbydate/${document.cookie}`, function (statement) {
        for (let i = 0; i < statement.length; i++) {
          var value = statement[i].value;
          tot += value;
          $(`#tot${statement[i].wallet.id}`).text('£ ' + statement[i].value);
        }
        $('#totale').text("£ " + tot);
        getDate();
      })
    }
    
    function getDate() {
      $.get('/statement/datestatement', function (date) {
        for (let i = 0; i < date.length; i++) {
          if (date[i] === document.cookie){
                dataold = date[i - 1];
                break;
              }
              break;
        }
      })
    }
    getOld();
        
      function getOld() {
        $.get(`/statement/statementbydate/${document.cookie}`, function (performance) {
        console.log(dataold + "Da")
        var percentuale = 0;
          for (let i = 0; i < performance.length; i++) {
            var value1 = performance[i].value;
            totold += value1;
          }
          console.log(totold)
      })

    }    


});