function vievVidObesp() {
    $("#mainContainer").load("/contract/main/vidObespSpisokViev", "", function () {
        let idKontrT = "vidObespTable";
        getSpinnerTable(idKontrT)

        let token = $('#_csrf').attr('content');
        let header = $('#_csrf_header').attr('content');
        $.ajax({
            url: "/contract/main/vidobesp/all",
            type: 'get',
            contentType: "application/json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                let trHTML = '';
                $('#' + idKontrT + ' tbody').html("");
                $.each(response, function (i, data) {

                    trHTML +=
                        '<tr>' +
                        '<th>' +
                        (+i + 1) +
                        '</th>' +
                        '<td>' +
                        '<input class="form-control"' +
                        'data-disabledInputVidObesps-id="' + data.id + '"' +
                        "value='" + data.name + "'" +
                        'name="disabledInputVidObesps"' +
                        'type="text"' +
                        'disabled readOnly>' +
                        '</td>' +
                        '<td>' +
                        '<a name="delSpisok" id="' + data.id + '"' +
                        'class="btn btn-secondary">X</a>' +
                        '</td>' +
                        '</tr>';
                });
                $('#' + idKontrT).append(trHTML);
            },
            error: function (response) {
                initialToats("Ошибка!", response.responseJSON.message, "err").show();
                $('#' + idKontrT + ' tbody').html("");
            }
        });

    });
}

$(document).ready(function () {

    let spisokBody = $("body");

    spisokBody.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if (e.keyCode === 13) {
            if ($(this).attr('name') === "disabledInputVidObesps") {
                console.log("enter")
                $(this).blur();
                return false;
            }
        }
    });


    spisokBody.on('blur', 'input', function () { //изменение на событие потери фокуса
        if ($(this).attr('name') === "disabledInputVidObesps") {  //список вид обесп
            let tek = $(this);
            let id = tek.attr('data-disabledInputVidObesps-id');
            let param = "id=" + id + "&name=" + tek.val();
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/vidobesp/update',
                method: 'post',
                data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    tek.prop('disabled', true).prop('readonly', true);
                    initialToats("Изменение прошло успешно", data, "success").show();
                },
                error: function (jqXHR, textStatus) {
                    initialToats("Ошибка при изменении!!!", jqXHR.responseText, "err").show();
                }
            });
        }
    });

    spisokBody.on('dblclick', 'input', function () {
        if ($(this).attr('name') === "disabledInputVidObesps") {
            $(this).removeAttr('disabled').removeAttr('readonly');
            $(this).focus();
            return false;
        }
    });

    spisokBody.on('click', 'a', function () {
        if ($(this).attr('id') === "vidObespSpisokViev") {  //список вид обесп
            vievVidObesp();
            //return false;
        }
        if ($(this).attr('id') === "buttonaddspisok") {
            $(this).prop("disabled", true);
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/vidobesp/add',
                method: 'post',
                data: $("#addVisObespForm").serialize(),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    vievVidObesp();
                    initialToats("Добавление прошло успешно", data, "success").show();
                },
                error: function (jqXHR, textStatus) {
                    initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                }
            });


            $(this).prop("disabled", false);
            return false;
        }
        if ($(this).attr('name') === "delSpisok") {
            let param = "id=" + $(this).attr('id');
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/vidobesp/delette',
                method: 'post',
                data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    vievVidObesp();
                    initialToats("Удаление прошло успешно", data, "success").show();
                },
                error: function (jqXHR, textStatus) {
                    initialToats("Ошибка при удалении!!!", jqXHR.responseText, "err").show();
                }
            });
            return false;
        }
    });
});