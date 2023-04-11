function vievVidObespRsp() {
    $("#mainContainer").load("/contract/rsp/vidObespSpisokViev", "", function () {
        let idKontrT = "vidObespRspTable";
        getSpinnerTable(idKontrT)

        let token = $('#_csrf').attr('content');
        let header = $('#_csrf_header').attr('content');
        $.ajax({
            url: "/contract/rsp/vidobesp/all",
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
                        'data-disabledInputVidObespsRsp-id="' + data.id + '"' +
                        "value='" + data.name + "'" +
                        'name="disabledInputVidObespsRsp"' +
                        'type="text"' +
                        'disabled readOnly>' +
                        '</td>' +
                        '<td>' +
                        '<a name="delSpisokRsp" id="' + data.id + '"' +
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
            if ($(this).attr('name') === "disabledInputVidObespsRsp") {
                console.log("enter")
                $(this).blur();
                return false;
            }
        }
    });


    spisokBody.on('blur', 'input', function () { //изменение на событие потери фокуса
        if ($(this).attr('name') === "disabledInputVidObespsRsp") {  //список вид обесп
            let tek = $(this);
            let id = tek.attr('data-disabledInputVidObespsRsp-id');
            let param = "id=" + id + "&name=" + tek.val();
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/rsp/vidobesp/update',
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
        if ($(this).attr('name') === "disabledInputVidObespsRsp") {
            $(this).removeAttr('disabled').removeAttr('readonly');
            $(this).focus();
            return false;
        }
    });

    spisokBody.on('click', 'a', function () {
        if ($(this).attr('id') === "vidObespSpisokVievRsp") {  //список вид обесп
            vievVidObespRsp();
            //return false;
        }
        if ($(this).attr('id') === "buttonaddspisokrsp") {
            $(this).prop("disabled", true);
            //let param = "name=" + $("#addznachspisok").val();
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/rsp/vidobesp/add',
                method: 'post',
                data: $("#addVisObespForm").serialize(),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    vievVidObespRsp();
                    initialToats("Добавление прошло успешно", data, "success").show();
                },
                error: function (jqXHR, textStatus) {
                    initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                }
            });
            $(this).prop("disabled", false);
            return false;
        }
        if ($(this).attr('name') === "delSpisokRsp") {
            let param = "id=" + $(this).attr('id');
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/rsp/vidobesp/delette',
                method: 'post',
                data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    vievVidObespRsp();
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