$(document).ready(function () {
    function getCategory(){
        $.get('/category/list', function (resume) {
            console.log(resume);
            const listCategory = $('#listCategory');
            for (let i = resume.length - 1; i >= 0; i--) {
                $(`<tr id='riga-${resume[i].id}'>
                <td>${resume[i].name}</td>
                <td>
                    <div class="btn-group roundedCorner" role="group">
                        <button id="btnGroupDrop1" type="button" class="btn btn-outline-warning roundedCorner dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">Opzioni</button>
                            <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
                                <li><a class="dropdown-item btn-dettaglio" data-bs-toggle="modal" data-bs-target="#dettaglio" data-id='${resume[i].id}'>Dettaglio</a></li>
                                <li><a class="dropdown-item btn-modifica-risto" data-bs-toggle="modal" data-bs-target="#modifica" data-id='${resume[i].id}'>Modifica</a></li>
                                <li><a class="dropdown-item btn-elimina-risto" data-id='${resume[i].id}'>Elimina</a></li>
                            </ul>
                    </div>
                </td>
            </tr>`).hide().appendTo(listCategory).fadeIn(i * 20);
            }
        })
    }
    getCategory();
});