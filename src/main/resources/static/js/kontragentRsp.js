function vievKontragent() {
    $("#mainContainer").load("/contract/rsp/kontragent/kontragentViev", "", function () {

        let idKontrT = "spravKontrTable";
        getSpinnerTable(idKontrT)

        let token = $('#_csrf').attr('content');
        let header = $('#_csrf_header').attr('content');
        $.ajax({
            url: "/contract/rsp/kontragent/all",
            type: 'get',
            contentType: "application/json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (response) {
                let trHTML = '';
                $('#' + idKontrT + ' tbody').html("");
                console.log("1")
                $.each(response, function (i, data) {

                    trHTML +=
                        '<tr>' +
                        '<th>' +
                        (+i + 1) +
                        '</th>' +
                        '<td>' +
                        '<input class="form-control"' +
                        'data-disabledInputKontragent-id="' + data.id + '"' +
                        "value='" + data.name + "'" +
                        'name="disabledInputKontragentNameRsp"' +
                        'type="text"' +
                        'disabled readOnly>' +
                        '</td>' +
                        '<td>' +
                        '<input class="form-control"' +
                        'data-disabledInputKontragent-id="' + data.id + '"' +
                        'value="' + data.inn + '"' +
                        'name="disabledInputKontragentInnRsp"' +
                        'type="text"' +
                        'disabled readOnly>' +
                        '</td>' +
                        '<td>' +
                        '<a name="delSpisokKontragentRsp" id="' + data.id + '"' +
                        'class="btn btn-secondary">X</a>' +
                        '</td>' +
                        '</tr>';
                });
                console.log("2")
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

    let spisokKontr = $("body");

    spisokKontr.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if (e.keyCode === 13) {
            if ($(this).attr('name') === "disabledInputKontragentNameRsp" ||
                $(this).attr('name') === "disabledInputKontragentInnRsp") {
                console.log("enter")
                $(this).blur();
                return false;
            }
        }
    });

    spisokKontr.on('blur', 'input', function () { //изменение на событие потери фокуса
        if ($(this).attr('name') === "disabledInputKontragentNameRsp" ||
            $(this).attr('name') === "disabledInputKontragentInnRsp") {  //список вид обесп
            let tek = $(this);
            let id = tek.attr('data-disabledInputKontragent-id');
            let name = $('[data-disabledInputKontragent-id=' + id + '][name=disabledInputKontragentNameRsp]').val();
            let inn = $('[data-disabledInputKontragent-id=' + id + '][name=disabledInputKontragentInnRsp]').val();
            let param = "id=" + id + "&name=" + name + "&inn=" + inn;
            console.log(param)
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/rsp/kontragent/update',
                method: 'post',
                data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    console.log("Данные изменены", data);
                    tek.prop('disabled', true).prop('readonly', true);
                    initialToats("Изменение прошло успешно", data, "success").show();
                },
                error: function (textStatus) {
                    initialToats("Ошибка!!!", textStatus, "err").show();
                }
            });
        }
    });

    spisokKontr.on('dblclick', 'input', function () {
        if ($(this).attr('name') === "disabledInputKontragentNameRsp" ||
            $(this).attr('name') === "disabledInputKontragentInnRsp") {
            /*let id = $(this).attr('data-disabledInputKontragent-id');
            console.log("dbclick",id);*/
            $(this).removeAttr('disabled').removeAttr('readonly');
            $(this).focus();
            return false;
        }
    });

    spisokKontr.on('click', 'button', function () {
        //загрузить таблицу в модальное окно
        if ($(this).attr('data-bs-target') === "#modalKontragent") {
            let param = "param=1";
            $("#dliazamenyKontragentTable").load("/contract/rsp/kontragent/modalKontragentViev", param, function () {
                clearPaginationMK();

                let idKontrT = "kontragentTableVibor";
                getSpinnerTable(idKontrT)

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: "/contract/rsp/kontragent/all",
                    type: 'get',
                    contentType: "application/json",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (response) {
                        let trHTML = '';
                        $('#' + idKontrT + ' tbody').html("");
                        /*let start = (+activeList("#paginationItContract") - 1) * $("#col").val();
                        console.log("ststartr=" + start)*/
                        $.each(response, function (i, data) {

                            trHTML +=
                                '<tr>' +
                                '<td>' + data.name + '</td>' +
                                '<td>' + data.inn + '</td>' +
                                '<td><a class="btn btn-link" data-id-modal-kontragent="' + data.id + '"' +
                                'data-bs-dismiss="modal">Выбрать</a></td>' +
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
    });


    spisokKontr.on('click', 'a', function () {

        if ($(this).attr('id') == "kontragentSpisokVievRsp") {  //список контрагент
            vievKontragent();
        }

        if ($(this).attr('id') == "buttonaddKontragentRsp") {
            $(this).prop("disabled", true);

            //let param = "name=" + $("#addNameKontragent").val() + "&inn=" + $("#addINNKontragent").val();

            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/rsp/kontragent/add',
                method: 'post',
                data: $("#addKontrFormRsp").serialize(),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    vievKontragent();
                    initialToats("Добавление прошло успешно", data, "success").show();
                },
                error: function (textStatus) {
                    initialToats("Ошибка!!!", textStatus, "err").show();
                    console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                }
            });
            $(this).prop("disabled", false);
            return false;
        }


        if ($(this).attr('name') == "delSpisokKontragentRsp") {
            let param = "id=" + $(this).attr('id');
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/rsp/kontragent/delette',
                method: 'post',
                data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    vievKontragent();
                    initialToats("Удаление прошло успешно", data, "success").show();
                },
                error: function (textStatus) {
                    initialToats("Ошибка!!!", textStatus, "err").show();
                    console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                }
            });
            return false;
        }
    });
});