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
                                <li><a class="dropdown-item btn-modifica-risto" data-bs-toggle="modal" data-bs-target="#modifica" data-id='${resume[i].id}'>Modifica</a></li>
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
                    'Il tuo Ristorante è salvo',
                    'error'
                )
            }
        })
    });

    //Modifica un ristorante dalla lista principale
    let editMode = false;
    let idModifica = -1;

    function modificaRistorante(ristorante) {
        $.ajax({
            type: "PUT",
            url: `/user/ristorantiuser`,
            data: JSON.stringify(ristorante),
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

    $('#listaRistorantiUser').on('click', '.btn-modifica-risto', function () {
        editMode = true;
        const id = +$(this).attr('data-id');
        idModifica = id;
        $.get(`/user/ristorantiuser/${id}`, function (modifica) {
            let img = '';
            if (modifica.immagini === null) {
                img = '../logos/logo.png';
            } else {
                img = '../upload/' + modifica.immagini;
            }
        $('#modificaRistoranteLogo').attr('src', img);
            $('#ragionesociale').val(modifica.ragionesociale);
            $('#piva').val(modifica.piva);
            $('#cittaRistorante').val(modifica.citta);
            $('#regioneRistorante').val(modifica.regione);
            $('#viaRistorante').val(modifica.via);
            $('#ncivico').val(modifica.ncivico);
            $('#modificaRistoranteTitle').text('Modifica ' + modifica.ragionesociale);
            $('#modificaRistorante').text('Modifica ' + modifica.ragionesociale);
            $('#title').text('Modifica ' + modifica.ragionesociale);
        });
    });
    $('#modificaRistorante').click(function () {
        const ristorante = {
            ragionesociale: $('#ragionesociale').val(),
            piva: $('#piva').val(),
            citta: $('#cittaRistorante').val(),
            regione: $('#regioneRistorante').val(),
            via: $('#viaRistorante').val(),
            ncivico: $('#ncivico').val()
        }
        console.log(ristorante);
        if (editMode) {
            Swal.fire({
                icon: 'question',
                title: 'Vuoi salvare la Modifica di ' + ristorante.ragionesociale + '?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: `Salva`,
                denyButtonText: `Non Salvare`,
            }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                    Swal.fire('Salvato!', '', 'success')
                    ristorante.id = idModifica;
                    modificaRistorante(ristorante);
                    setTimeout(function () {
                        window.location.href = 'ristorantiUser.html';
                    }, 2000);
                } else if (result.isDenied) {
                    Swal.fire('Modifiche non salvate', '', 'info')
                }
            })
        }
    })
});