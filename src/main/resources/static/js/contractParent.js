$(document).ready(function () {

    let body = $("body");

    body.on('click', 'button', function () {
        if ($(this).hasClass('minusNotif')) { //удалить уведомление
            $("#notifications #" + $(this).attr('name')).detach();
            return false;
        }
    });

    body.on('click', 'a', function () {
        if ($(this).attr('id') === "notificationsVibor") {
            console.log("notificationsVibor");
            let val = $("#notificationsSelect").val();
            if ($("#notifications div").is('#' + val)) {
                alert("Вы уже добавили этого человека!");
            } else {
                let notif = $("#notifications");
                notif.html(notif.html() +

                    "<div class='mb-1' id='" + val + "'>" +
                    $("#notificationsSelect option:selected").text() +
                    "<button class='btn btn-secondary btn-sm minusNotif' type='button' " +
                    "name=" + val + ">X</button></div>")
            }
            return false;
        }
    });

});

function ajaxContractSuccess(response, otdel = "") {
    let trHTML = '';
    let tableContainer = $('#tableContainer tbody');
    tableContainer.html("");
    let start = (+activeList("#pagination") - 1) * 30;

    $.each(response, function (i, item) {

        trHTML +=
            '<tr>' +
            '<th>' + (start + +i + 1) + '</th>' +
            '<td>' + replaceNull(item.plat_post) + '</td>' +
            '<td>' + replaceNull(item.receipt_date) + '</td>' +
            '<td>' + (item.kontragent === null ? "" : replaceNull(item.kontragent.nameInn)) + '</td>' +
            '<td>' + replaceNull(item.nomGK) + '</td>' +
            '<td>' + replaceNull(item.dateGK) + '</td>' +
            '<td style="max-width: 290px">' + replaceNull(item.predmet_contract) + '</td>' +
            '<td>' + replaceNull(item.vidObesp.name) + '</td>' +
            '<td>' + replaceNull(item.sum) + '</td>' +
            '<td>' +
            '<label>' +
            '<input type="checkbox" class="form-check-input" ' +
            'name="' + item.id + '" id="checkboxIspolneno' + otdel + '" ' +
            (item.ispolneno ? 'checked=true' : '') + '/>' +
            '<span class="marginonospan"></span>' +
            '</label>' +
            '<div data-progress-id="' + item.id + '">' +
            '<div class="progress" title="Осталось ' + item.daysOst + ' дней">' +
            '<div class="progress-bar ' +
            (item.ispolneno ? 'bg-success' :
                (item.daysOst <= 4 && item.daysOst >= 0) ? 'bg-danger progress-bar-striped ' :
                    (item.procent >= 75) ? 'bg-danger' :
                        (item.procent >= 50 ? 'bg-warning' :
                            (item.procent >= 25 ? 'bg-info' : ''
                            ))) + '" ' +

            'role="progressbar" ' +
            'style="width: ' + (item.ispolneno ? '100' : item.procent) + '%" ' +
            'aria-valuenow="' + (item.ispolneno ? '100' : item.procent) + '" ' +
            'aria-valuemin="0" ' +
            'aria-valuemax="100" ' +

            '">' + (item.ispolneno ? '' : item.daysOst) +
            '</div>' +
            '</div>' +
            '</div>' +
            '</td>' +
            '<td>' +
            '<div>' +
            '<a data-bs-toggle="modal"' +
            '        data-bs-target="#modalDopContainer"' +
            '        href="#!"' +
            '        data-a-dop-modal="dataADopModal"' +
            '        data-id-dop-modal="dataADopModal' + otdel + '"' +
            '        name="' + item.id + '">Доп.информация' +
            '</a>' +
            '</div>' +
            '<div><a name="' + item.id + '" id="updateContract' + otdel + '" href="#">Изменить</a></div>' +
            '<div><a name="' + item.id + '" id="deleteContract' + otdel + '" href="#">Удалить</a></div>' +
            '</td>' +

            '</tr>';
    });
    tableContainer.append(trHTML);
}