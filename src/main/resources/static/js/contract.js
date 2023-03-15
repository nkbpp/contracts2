$(document).ready(function () {

    let body = $("body");

    body.on('click', 'a', function () {

        if ($(this).attr('id') === "menuViev") { //Кнопка просмотр
            loadMenuView("");
            return false;
        }

        if ($(this).attr('id') === "menuAdd") { //Добавить контракт
            $("#mainContainer").load("/contract/main/add", "", function () {
                $("#containerNotification").load("/contract/main/getnotification", "", function () {
                });

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/main/dopnotification',
                    method: 'post',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        if (!$.isEmptyObject(data)) {
                            for (let notif of data) {
                                let dv = $("#notifications");
                                dv.html(
                                    dv.html() + getDopNotifHtml(notif.name, notif.id_user)
                                );
                            }
                        }

                        $('.datepicker').datepicker({
                            format: 'dd.mm.yyyy',
                            language: "ru"
                        });

                    },
                    error: function (textStatus) {
                    }
                });
            });
            return false;
        }

        // viev изменения контракта
        if ($(this).attr('id') === "updateContract") {

            let id = $(this).attr('name');
            let param = "id=" + id;

            $("#mainContainer").load("/contract/main/updateViev", param, function () {
                $("#containerNotification").load("/contract/main/getnotification", "", function () {
                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/contract/' + id,
                        method: 'get',
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            $("#addContracts").attr("data-id-contract", data.id)
                            $('input[name=receipt_date]').val(data.receipt_date);
                            $('input[name=plat_post]').val(data.plat_post);
                            $('input[name=nomGK]').val(data.nomGK);
                            $('input[name=dateGK]').val(data.dateGK);
                            $('[name=predmet_contract]').val(data.predmet_contract);
                            $('input[name=sum]').val(data.sum);
                            let kontragent = data.kontragent;
                            if (kontragent !== "") {
                                $("#kontragent option[value=" + data.kontragent.id + "]").prop("selected", "true");
                            }
                            let vidObesp = data.vidObesp;
                            if (vidObesp !== "") {
                                $("#vidObesp option[value=" + data.vidObesp.id + "]").prop("selected", "true");
                            }

                            $('input[name=date_ispolnenija_GK]').val(data.date_ispolnenija_GK);
                            $('input[name=col_days]').val(data.col_days);
                            $('input[name=nomerZajavkiNaVozvrat]').val(data.nomerZajavkiNaVozvrat);
                            $('input[name=dateZajavkiNaVozvrat]').val(data.dateZajavkiNaVozvrat);
                            $('#checkboxAddUpdate').prop("checked", data.ispolneno === true);

                            $('#addContracts').text("Изменить");

                            if (data.notifications !== undefined) {
                                for (let notif of data.notifications) {
                                    let dv = $("#notifications");
                                    dv.html(
                                        dv.html() + getDopNotifHtml(notif.name, notif.id_user)
                                    );
                                }
                            }

                            if (data.myDocuments !== undefined) {
                                for (let doc of data.myDocuments) {
                                    let dv = $("#documentsViev");
                                    dv.html(
                                        dv.html() +
                                        "<div class='mb-1' data-id-doc='" + doc.id + "'>" + doc.nameFile + " " +
                                        "<button class='btn btn-secondary btn-sm' " +
                                        "data-id-doc='" + doc.id + "' data-name-doc='delDoc'>X</button>" +
                                        "</div>"
                                    );
                                }
                            }

                            $('.datepicker').datepicker({
                                format: 'dd.mm.yyyy',
                                language: "ru"
                            });

                        },
                        error: function (textStatus) {
                            initialToats("Ошибка!!!", textStatus, "err").show();
                            console.log('ОШИБКИ AJAX запроса: ' + logObj(textStatus));
                        }
                    });
                });
            });
            return false;
        }

        //Удалить контракт
        if ($(this).attr('id') === "deleteContract") {
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("#" + $(this).attr('name')).children().eq(0).text())) {

                let id = $(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/main/contract/' + id,
                    method: 'delete',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        let param = "param=" + activeList("#pagination");//чтобы при удалении осталась текущая страница
                        $("#tableContainer").load("/contract/main/getTable", param, function () {
                        });
                        initialToats("Удаление прошло успешно", data, "success").show();
                    },
                    error: function (textStatus) {
                        initialToats("Ошибка!!!", textStatus, "err").show();
                    }
                });
            }
            return false;
        }

        //checkBox в таблице
        body.on('click', 'input', function () {
            if ($(this).attr('id') === "checkboxIspolneno") {
                let id = $(this).attr("name");
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');

                $.ajax({
                    url: '/contract/main/setIspolneno/' + id,
                    method: 'get',
                    //data: param,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        let param = "id=" + id;
                        $("[data-progress-id=" + id + "]").load("/contract/main/getProgress", param, function () {
                        });
                        initialToats("Изменение статуса прошло успешно", data, "success").show();
                    },
                    error: function (textStatus) {
                        initialToats("Ошибка в изменении статуса!!!", textStatus, "err").show();
                        console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                    }
                });
            }
        });

    })


    body.on('click', 'button', function () {

        //изменение контракта
        if ($(this).attr('id') === "addContracts") {
            // проверка заполнения основных полей
            if (
                !$("#receipt_date").val().trim() ||
                !$("#plat_post").val().trim() ||
                $("#vidObesp").val() === 0 ||
                $("#kontragent").val() === 0 ||
                !$("#sum").val().trim()
            ) {
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled", true);
                $(this).prepend(getSpinnerButton());// крутилкa

                // Get form
                let form = $('#formContracts')[0];
                // Create an FormData object
                let formData = new FormData(form);
                //let formData = new FormData();
                let jsonData = Object.fromEntries(formData.entries());

                delete jsonData.kontragent;
                delete jsonData.vidObesp;
                delete jsonData.myDocuments;
                let id = $('#addContracts').attr("data-id-contract")
                jsonData.id = id;
                jsonData.ispolneno = $('#checkboxAddUpdate').prop("checked");
                jsonData.kontragent = {
                    id: $("#kontragent").val()
                };
                jsonData.vidObesp = {
                    id: $("#vidObesp").val()
                };
                jsonData.notifications = $('#notifications div[id]').toArray()
                    .map(d => {
                        return {
                            id_user: $(d).attr('id')
                        }
                    });

                let formDataFile = new FormData();
                const blob = new Blob([JSON.stringify(jsonData)], {
                    type: 'application/json'
                });

                formDataFile.append("contract", blob);

                let ins = document.getElementById('myDocuments').files.length;
                if (ins === 0) {
                    let formData2 = new FormData(form);
                    let jsonData2 = Object.fromEntries(formData2.entries());
                    let data = {};
                    data.file = jsonData2['myDocuments'];
                    formDataFile.append("file", data.file);
                } else {
                    for (let x = 0; x < ins; x++) {
                        formDataFile.append(
                            "file",
                            document.getElementById('myDocuments').files[x]);
                    }
                }

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');

                //добавление
                if (id === undefined) {
                    $.ajax({
                        url: "/contract/main/contract",
                        data: formDataFile,
                        processData: false,
                        contentType: false,
                        type: "POST",
                        headers: {
                            "Content-Type": undefined
                        },
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            //после добавления показать таблицу
                            $("#mainContainer").load("/contract/main/vievTable", "", function () {
                                $("#tableContainer").load("/contract/main/getTable", "", function () {
                                });
                            });
                            initialToats("Добавление прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR, textStatus) {
                            initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                } else { //изменение
                    $.ajax({
                        url: "/contract/main/contract",
                        data: formDataFile,
                        processData: false,
                        contentType: false,
                        type: "PUT",
                        headers: {
                            "Content-Type": undefined
                        },
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            //после изменения показать таблицу
                            $("#mainContainer").load("/contract/main/vievTable", "", function () {
                                $("#tableContainer").load("/contract/main/getTable", "", function () {
                                });
                            });
                            initialToats("Изменение прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR, textStatus) {
                            initialToats("Ошибка при изменение!!!", jqXHR.responseText, "err").show();
                        }
                    });
                }

            }
            return false;
        }

        //удалить документ
        if ($(this).attr('data-name-doc') === "delDoc") {
            let id = $(this).attr('data-id-doc');
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/delDoc/' + id,
                method: 'delete',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    $("div[data-id-doc=" + id + "]").remove();
                    $("a[data-id-doc=" + id + "]").remove();
                    initialToats("Удаление прошло успешно", data, "success").show();
                },
                error: function (textStatus) {
                    initialToats("Ошибка при удалении документа!!!", textStatus, "err").show();
                    console.log('ОШИБКИ AJAX запроса при удалении документа: ' + textStatus);
                }
            });
            return false;
        }

    })

});

function loadMenuView(param) {
    let mainContainer = $("#mainContainer");
    mainContainer.html(getSpinner());
    mainContainer.load("/contract/main/vievTable", "", function () {
        let tableContainer = $("#tableContainer")
        tableContainer.html(getSpinner());
        tableContainer.load("/contract/main/getTable", param, function () {
        });
    });
}

/*
function ajaxContractAll(params){
    getSpinnerTable("tableContainer")

    $.ajax({
        url: "/overpayment/referenceBook/district/All?" + params,
        data: "",
        cache: false,
        processData: false,
        contentType: "application/json",
        dataType: 'json',
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader($('#_csrf').attr('content'),
                                 $('#_csrf_header').attr('content'));
        },
        success: function (response) {
            let trHTML = '';
            $('#tableDistrict tbody').html("");
            $.each(response, function (i, item) {
                trHTML +=
                    '<tr>' +
                    '<th>' + (+i+1) + '</th>' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.kod + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +
                    '<button ' +
                    'class="btn  btn-secondary btn-sm deleteDistrictBtn" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Удалить</button>' +

                    '<button ' +
                    'class="btn  btn-secondary btn-sm updateDistrictBtn mx-1" ' +
                    'data-bs-toggle="modal" ' +
                    'data-bs-target="#modalDistrict" ' +
                    'type="button" ' +
                    'name="' + item.id + '" ' +
                    '>Изменить</button>' +


                    '</div>' +
                    '</td>' +
                    '</tr>';
            });
            $('#tableDistrict').append(trHTML);
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message , "err").show();
            $('#tableDistrict tbody').html("");
        }
    });
}*/
