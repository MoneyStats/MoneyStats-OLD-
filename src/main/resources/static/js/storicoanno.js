$(document).ready(function () {
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
      for (let i = data.length-1; i >= 0; i--) {
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