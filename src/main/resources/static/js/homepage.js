$(document).ready(function () {
    getDate();
    // MODALE SELEZIONE DATA SEZIONE HOMEPAGE
    function getDate() {
        var lastDate = "";
        var firstDate = "";
        var lastStatement = "";
        var totLastDate = 0;
        var totLastStatement = 0;
        var totFirstDate = 0;

        // VAR GRAFICO A TORTA
        var wallet = 0;
        var nameWallet = "";

        // VAR GRAFICO
        var year = 0;

        $.get('/statement/datestatement', function (date) {
            const listDate = $('#dateOption');
            for (let i = 0; i < date.length; i++) {
                $(`<option id='dateSelect' value="${date[i]}">${date[i]}</option>`).hide().appendTo(listDate).fadeIn(i * 20);
                lastDate = date[date.length-1];
                lastStatement = date[date.length-2];
                firstDate = date[0];
            }
            
            // TOTALE CAPITALI HOMEPAGE DATA ATTUALE
            $.get(`/statement/statementbydate/${lastDate}`, function (total) {
              for (let i = 0; i < total.length; i++){
                totLastDate += total[i].value;
                wallet += [total[i].value + ","];
                nameWallet += [total[i].wallet.name + ','] ;
              }
              // Graph
              var ctx1 = document.getElementById("chart-pie");
              splitName = nameWallet.split(",");
              splitWallet = wallet.split(",");

              var myChart = new Chart(ctx1, {
                type: "pie",
                data: {
                  labels: splitName,
                  datasets: [{
                    data: splitWallet,
                    backgroundColor: [
                      "rgba(241, 182, 176, 0.6)",
                      "rgba(227, 192, 67, 0.6)",
                      "rgba(224, 99, 3, 0.9)",
                      "rgba(205, 157, 105, 0.1)",
                      "rgba(204, 107, 78, 0.8)",
                      "rgba(201, 199, 115, 0.3)",
                      "rgba(202, 149, 232, 0.2)",
                      "rgba(181, 223, 238, 0.6)",
                      "rgba(141, 88, 1, 0.2)",
                      "rgba(189, 9, 95, 0.1)",
                      "rgba(166, 73, 170, 0.5)",
                      "rgba(133, 159, 99, 0.3)",
                      "rgba(132, 211, 117, 0.9)",
                      "rgba(73, 197, 30, 0.7)",
                      "rgba(54, 16, 27, 0.6)",
                      "rgba(94, 187, 84, 0.7)",
                      "rgba(52, 127, 38, 0.5)",
                      "rgba(36, 26, 13, 0.2)",
                      "rgba(35, 81, 109, 0.3)",
                      "rgba(33, 130, 53, 0.8)",
                      "rgba(29, 60, 205, 0.8)",
                      "rgba(20, 242, 54, 0.3)",
                      "rgba(19, 26, 45, 0.4)",
                      "rgba(5, 158, 54, 0.2)",
                      "rgba(0, 138, 131, 0.3)",
                    ],
                  }, ],
                }
              });
              $('#totalecapitale').text('£ ' + totLastDate.toFixed(2));
              $('.sincetotalecapitale').text("Since " + lastStatement);
              // TESTO GRAFICO A TORTA PER LA DATA
              $('#dataAttuale').text("Grafico Capitali in Data " + lastDate).val(lastDate);
            })
            // PERFORMANCE CAPITALI HOMEPAGE DATA ATTUALE
            $.get(`/statement/statementbydate/${lastStatement}`, function (performance) {
              for (let i = 0; i < performance.length; i++){
                totLastStatement += performance[i].value;
              }
              var calcolo = ((totLastDate - totLastStatement) / totLastStatement) * 100;
              var pil = totLastDate - totLastStatement;
              if (calcolo > 0){
                $(`<span class="text-success mr-2"><i class="fa fa-arrow-up"></i> ${calcolo.toFixed(2)}%</span>`).appendTo(`.performanceLastStatement`)
                $('#pil').text("£ " + pil.toFixed(2)).addClass('text-success');
              } else if (calcolo === 0){
                $(`<span class="text-warning mr-2"><i class="fa fa-arrow-down"></i> ${calcolo.toFixed(2)}%</span>`).appendTo(`.performanceLastStatement`)
                $('#pil').text("£ " + pil.toFixed(2)).addClass('text-warning');
              } else {
                $(`<span class="text-danger mr-2"><i class="fa fa-arrow-down"></i> ${calcolo.toFixed(2)}%</span>`).appendTo(`.performanceLastStatement`)
                $('#pil').text("£ " + pil.toFixed(2)).addClass('text-danger');
              }
            })
            // PERFORMANCE TOTALI HOMEPAGE ATTUALE
            $.get(`/statement/statementbydate/${firstDate}`, function (performanceTot) {
              for (let i = 0; i < performanceTot.length; i++){
                totFirstDate += performanceTot[i].value;
              }
              var calcoloTot = ((totLastDate - totFirstDate) / totFirstDate) * 100;
              var pilTot = totLastDate - totFirstDate;
              if (calcoloTot > 0){
                $(`<span class="text-success h2 font-weight-bold mb-0"><i class="fa fa-arrow-up"></i> ${calcoloTot.toFixed(0)}%</span>`).appendTo(`.performanceFirstDate`)
                $('#pilTotale').text("£ " + pilTot.toFixed(2)).addClass('text-success');
              } else if (calcoloTot === 0){
                $(`<span class="text-warning h2 font-weight-bold mb-0"><i class="fa fa-arrow-down"></i> ${calcoloTot.toFixed(0)}%</span>`).appendTo(`.performanceFirstDate`)
                $('#pilTotale').text("£ " + pilTot.toFixed(2)).addClass('text-warning');
              } else {
                $(`<span class="text-danger h2 font-weight-bold mb-0"><i class="fa fa-arrow-down"></i> ${calcoloTot.toFixed(0)}%</span>`).appendTo(`.performanceFirstDate`)
                $('#pilTotale').text("£ " + pilTot.toFixed(2)).addClass('text-danger');
              }
            })
        })
    }
    

    $('#dataConfirm').click(function () {
        document.cookie = $('#dateOption').val() + "; path=/";
        const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
          })
          
          Toast.fire({
            icon: 'success',
            title: 'Data inserite, reinderizzazione...'
          })
          setTimeout(function () {
            window.location.href = "capitalewallet.html";
          }, 1500);
    })


      var arrayDates = [];
      var arrayValues = [];
      var dataI = 0;
      var totValues = 0;
      var graphValues = [];
      var graphDate = [];

      // CALCOLO PIL
      var firstValue = 0;
      var piltot= 0;
      var arraypil = [];
      var graphpil = [];
      $.get('/statement/datestatement', function (data) {
        for (let i = 0; i < data.length; i++) {
            // Calcolo anno corrente(mi serve per la lista di date secondo anno)
            year = data[data.length-1].split("-")[0];
            $('#year').text('Andamento Anno ' + year);
            $('#listStatement').text('Statement Anno ' + year);
            // Fine calcolo
            // Ciclo per ottenere una lista di statement secondo anno corrente
            if (data[i].includes(year)){
              arrayDates += [data[i] + ","]; 
              dataI = data[i];
              $.get(`/statement/statementbydate/${dataI}`, function (graph) {
                for (let y = 0; y < graph.length; y++){
                  totValues += graph[y].value;
                }
                getValue(totValues);
                totValues = 0;
              })
            }
          }
          function getValue(totValues){
            arrayValues += [totValues + ","];
            getGraph(arrayValues, arrayDates);
          }
      })
      
    function getGraph(arrayValues, arrayDates) {
      graphDate = arrayDates.split(",");
      graphValues = arrayValues.split(",");
      firstValue = graphValues[0];

      /*for (let z = 0; z < graphValues.length; z++){
        piltot = graphValues[z] - firstValue;
        arraypil += [piltot] + ",";
      }
      graphpil = arraypil.split(",");*/
        // GRAFICO
        // Graph
        var ctx = document.getElementById("myChart");

        var myChart = new Chart(ctx, {
          type: "line",
          data: {
            labels: graphDate,
            datasets: [
              {
                label: "Capitali Totali",
                data: graphValues,
                backgroundColor: "transparent",
                borderColor: "#007bff",
                borderWidth: 4,
                backgroundColor: [
                  'rgba(105, 0, 132, .2)',
                  ],
                  borderColor: [
                  'rgba(200, 99, 132, .7)',
                  ],
                  borderWidth: 2
              },
              {
                label: "PIL",
                data: graphValues,
                backgroundColor: [
                'rgba(0, 137, 132, .2)',
                ],
                borderColor: [
                'rgba(0, 10, 130, .7)',
                ],
                borderWidth: 2
                }
            ],
          },
          
          options: {
            scales: {
              yAxes: [
                {
                  ticks: {
                    beginAtZero: true,
                  },
                },
              ],
            },
            legend: {
              display: true,
            },
          },
        });
        }


        // LISTA STATEMENT
        function getWallet(){
          $.get('/wallet/list', function (resume) {
              const listWallet = $('#titoloTab');
              for (let i = 0; i < resume.length; i++) {
                  $(`<th scope="col">${resume[i].name}</th>`).hide().appendTo(listWallet).fadeIn(i * 20);
                  
              }
          })
      }
      getWallet();

      $.get('/statement/datestatement', function (data) {
        year = data[data.length-1].split("-")[0];
        console.log(year)
        const dataTabella = $('#data');
        for (let i = 0; i < data.length; i++) {
          if (data[i].includes(year)){
            $.get(`/statement/statementbydate/${data[i]}`, function (statementTab) {
              // DATA PER TABELLA
              $(`<tr id="data${i}"><th scope="row">${data[i]}</th></tr>`).hide().appendTo(dataTabella).fadeIn(i * 20);
              const statTab = $(`#data${i}`)
            // Fine calcolo
              for (let y = 0; y < statementTab.length; y++){
                $(`<td>£ ${statementTab[y].value}</td>`).hide().appendTo(statTab).fadeIn(i * 20);
              }
            })
          }
        }
          
      })
    
  
});