$(document).ready(function () {
    // MODALE WALLET NELLA HOMEPAGE
    function getWallet(){
        $.get('/wallet/list', function (resume) {
            console.log(resume);
            const listWallet = $('#listWallet');
            for (let i = resume.length - 1; i >= 0; i--) {
                $(`<tr id='riga-${resume[i].id}'>
                <td>${resume[i].name}</td>
                <td>${resume[i].category.name}</td>
                <td>
                    <div class="btn-group roundedCorner" role="group">
                        <button id="btnGroupDrop1" type="button" class="btn btn-outline-primary roundedCorner dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">Opzioni</button>
                            <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                <li><a class="dropdown-item btn-modifica-wallet" data-bs-toggle="modal" data-bs-target="#editWallet" data-id='${resume[i].id}'>Modifica</a></li>
                                <li><a class="dropdown-item btn-elimina-wallet" data-id='${resume[i].id}'>Elimina</a></li>
                            </ul>
                    </div>
                </td>
            </tr>`).hide().appendTo(listWallet).fadeIn(i * 20);
            }
        })
    }
    getWallet();

    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          confirmButton: 'btn btn-success',
          cancelButton: 'btn btn-danger',
          denyButton: 'btn btn-danger'
        },
        buttonsStyling: false
      })
    // MODALE AGGIUNGI WALLET HOMEPAGE
        $('#aggiungiWallet').click(function () {
            const wallet = {
                name: $('#name').val(),
                category: {
                    id: $('#catOptionhtml').val(),
                }
            }
            Swal.fire({
                icon: 'question',
                title: `Confermi il salvataggio di ${wallet.name}?`,
                showDenyButton: true,
                confirmButtonText: `Salva`,
                denyButtonText: `Non Salvare`,
              }).then((result) => {
                if (result.isConfirmed) {
                    addWallet(wallet);
                } else if (result.isDenied) {
                  swalWithBootstrapButtons.fire(`Portafoglio non Aggiunto`, '', 'info')
                }
              })
            

        $('#value').val('');
        $('#date').val('');
        $('#listWallet').val('');
    })

    function addWallet(wallet) {
        console.log("Dentro funzione")
        $.ajax({
            type: "POST",
            url: `/wallet/postWallet/${wallet.category.id}`,
            data: JSON.stringify(wallet),
            contentType: 'application/json',
            success: function (response) {
                Swal.fire('Salvato!', '', 'success')
            },
            error: function (err){

            }
        });
    }

    function deleteWallet(id) {
        let idPagina = $(`#riga-${id}`);
        $.ajax({
            type: "DELETE",
            url: `/wallet/delete/${id}`,
            success: function (response) {
                swalWithBootstrapButtons.fire(
                    'Cancellato!',
                    'Il tuo Ristorante è stato Eliminato.',
                    'success'
                )
                idPagina.slideUp(300, function () {
                    idPagina.remove();
                })
            },
            error: function (error) {
                alert("Errore durante la cancellazione. Riprovare.");
            }
        });
    }

    $('#listWallet').on('click', '.btn-elimina-wallet', function () {
        const id = $(this).attr('data-id');
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-danger',
                cancelButton: 'btn btn-primary mx-2'
            },
            buttonsStyling: false
        })
        swalWithBootstrapButtons.fire({
            title: 'Sei Sicuro?',
            text: "Operazione Irreversibile!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Elimina',
            cancelButtonText: 'Esci',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                deleteWallet(id);
            } else if (
                result.dismiss === Swal.DismissReason.cancel
            ) {
                swalWithBootstrapButtons.fire(
                    'Uscita',
                    'Il tuo Wallet è salvo',
                    'error'
                )
            }
        })
    });

    //Modifica il wallet dalla lista principale
    let editMode = false;
    let idModifica = -1;

    function modificaWallet(wallet) {
        $.ajax({
            type: "PUT",
            url: `/wallet/update`,
            data: JSON.stringify(wallet),
            contentType: 'application/json',
            dataType: 'json',
            success: function (response) {
                editMode = false;
                idModifica = -1;
            },
            /* error: function (error) {
                 alert("Problema nella modifica");
             }*/
        });
    }

    $('#listWallet').on('click', '.btn-modifica-wallet', function () {
        editMode = true;
        var idWallet = +$(this).attr('data-id');
        idModifica = idWallet;

        });

    $('#editWalletbtn').click(function () {
        editMode = true;
        const wallet = {
            id: idModifica,
            name:  $('#nameEdit').val(),
            category: {
                id: $('#catOptionhtml2').val(),
            }
        }
        console.log(wallet);
        if (editMode) {
            Swal.fire({
                icon: 'question',
                title: 'Vuoi salvare la Modifica di ' + wallet.name + '?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: `Salva`,
                denyButtonText: `Non Salvare`,
            }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    Swal.fire('Salvato!', '', 'success')
                    wallet.id = idModifica;
                    modificaWallet(wallet);
                    setTimeout(function () {
                        window.location.href = 'homepage.html';
                    }, 2000);
                } else if (result.isDenied) {
                    Swal.fire('Modifiche non salvate', '', 'info')
                }
            })
        }
    })
});