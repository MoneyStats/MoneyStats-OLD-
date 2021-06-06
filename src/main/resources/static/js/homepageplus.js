$(document).ready(function () {
    // MODALE SELEZIONE DATA SEZIONE HOMEPAGE
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

    $('#dataConfirm').click(function () {
        document.cookie = $('#dateOption').val();
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
        
        console.log (document.cookie);
    })
});