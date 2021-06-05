$(document).ready(function () {
    function getWallet(){
        $.get('/wallet/list', function (resume) {
            console.log(resume);
            const listWallet = $('#listWallet');
            var wallet;
            for (let i = 0; i < resume.length; i++) {
                $(`<option id='walletSelect' value="${resume[i].id}">${resume[i].name}</option>`).hide().appendTo(listWallet).fadeIn(i * 20);
            }
        })
    }
    getWallet();

    $('#aggiungiStatement').click(function () {
        const statement = {
            value: $('#value').val(),
            date: $('#date').val(),
            wallet: {
                id: $('#listWallet').val(),
            }
        }
        addStatement(statement);

    $('#value').val('');
    $('#date').val('');
    $('#listWallet').val('');
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

    function getDate() {
        $.get('/statement/datestatement', function (date) {
            console.log(date);
            const listDate = $('#dateOption');
            for (let i = 0; i < date.length; i++) {
                $(`<option id='dateSelect' value="${date[i]}">${date[i]}</option>`).hide().appendTo(listDate).fadeIn(i * 20);
            }
        })
    }
    getDate();
});