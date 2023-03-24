$(document).ready(function () {

    let body = $("body");

    body.on('click', 'a', function () {

        //переключатели страниц pagination
        if ($(this).parents("#pagination").attr("id") === "pagination") {
            let list = clickPagination($(this), "#pagination");
            let param = "param=" + list +
                "&poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
                "&poleFindByINN=" + $("[data-name=poleFindByINN]").val() +
                "&poleFindByIspolneno=" + $('[data-name=poleFindByIspolneno]').prop("checked") +
                "&poleFindByNotIspolneno=" + $('[data-name=poleFindByNotIspolneno]').prop("checked");
            ajaxContractAll(param)
        }

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
                        ajaxContractAll(param);
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
                    }
                });
            }
        });

        //дополнительная инфа
        if ($(this).attr('data-a-dop-modal') === "dataADopModal") {
            let id = $(this).attr('name');
            $("#modalDopContainerContent").load("/contract/main/dopTable", "", function () {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: "/contract/main/contract/" + id,
                    cache: false,
                    processData: false,
                    type: 'GET',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        let trHTML = '';
                        let modalDopContainerContent = $('#modalDopContainerContent tbody');
                        modalDopContainerContent.html("");

                        let notif = "";
                        for (let n of data.notifications) {
                            notif += n.name;
                            notif += ", ";
                        }
                        notif = notif.slice(0, -2);

                        let docum = "";
                        for (let doc of data.myDocuments) {
                            docum += '<a ' +
                                'class="btn btn-link" ' +
                                'href="/contract/main/download/' + doc.id + '" ' +
                                '>' + doc.nameFile + '</a>';
                        }

                        trHTML +=
                            '<tr>' +
                            '<td>' + replaceNull(data.date_ispolnenija_GK) + '</td>' +
                            '<td>' + replaceNull(data.raschet_date) + '</td>' +
                            '<td>' + replaceNull(data.nomerZajavkiNaVozvrat) + '</td>' +
                            '<td>' + replaceNull(data.dateZajavkiNaVozvrat) + '</td>' +
                            '<td>' + notif + '</td>' +
                            '<td>' + docum + '</td>' +
                            '</tr>';

                        modalDopContainerContent.append(trHTML);
                    },
                    error: function (response) {
                        initialToats("Ошибка!", response.responseJSON.message, "err").show();
                        $('#tableContainer tbody').html("");
                    }
                });
            });
            return false;
        }

    })


    body.on('click', 'button', function () {

        //поиск
        if ($(this).attr('name') === "findContract") {
            let param = "poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
                "&poleFindByINN=" + $("[data-name=poleFindByINN]").val() +
                "&poleFindByIspolneno=" + $('[data-name=poleFindByIspolneno]').prop("checked") +
                "&poleFindByNotIspolneno=" + $('[data-name=poleFindByNotIspolneno]').prop("checked");
            ajaxContractAll(param);
            clearPagination1();
            return false;
        }

        //изменение контракта
        if ($(this).attr('id') === "addContracts") {
            let vidObesp = $("#vidObesp").val();
            let kontragent = $("#kontragent").val();
            // проверка заполнения основных полей
            if (
                !$("#receipt_date").val().trim() ||
                !$("#plat_post").val().trim() ||
                vidObesp === 0 ||
                kontragent === 0 ||
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
                    id: kontragent
                };
                jsonData.vidObesp = {
                    id: vidObesp
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
                                ajaxContractAll("");
                            });
                            initialToats("Добавление прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR) {
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
                                ajaxContractAll("");
                            });
                            initialToats("Изменение прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR) {
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
                }
            });
            return false;
        }

    })

});

function loadMenuView() {
    let mainContainer = $("#mainContainer");
    mainContainer.html(getSpinner());
    mainContainer.load("/contract/main/vievTable", "", function () {
        ajaxContractAll("");
    });
}

function ajaxContractAll(params) {
    getSpinnerTable("tableContainer")

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/main/All?" + params,
        data: "",
        cache: false,
        processData: false,
        contentType: "application/json",
        dataType: 'json',
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            ajaxContractSuccess(response)
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            tableContainer.html("");
        }
    });
}

function ajaxContractSort(url) {
    getSpinnerTable("tableContainer")

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/main/" + url,
        data: "",
        cache: false,
        processData: false,
        contentType: "application/json",
        dataType: 'json',
        type: 'GET',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            ajaxContractSuccess(response)
        },
        error: function (response) {
            console.log("ddd")
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
        }
    });
}

function ajaxContractSuccess(response) {
    let trHTML = '';
    let tableContainer = $('#tableContainer tbody');
    tableContainer.html("");
    let start = (+activeList("#pagination") - 1) * 30;//$("#col").val();

    console.log("response = ")
    console.log(response)
    $.each(response, function (i, item) {

        trHTML +=
            '<tr>' +
            '<th>' + (start + +i + 1) + '</th>' +
            '<td>' + replaceNull(item.plat_post) + '</td>' +
            '<td>' + replaceNull(item.receipt_date) + '</td>' +
            '<td>' + (item.kontragent === null ? "" : replaceNull(item.kontragent.nameInn)) + '</td>' +
            '<td>' + replaceNull(item.nomGK) + '</td>' +
            '<td>' + replaceNull(item.dateGK) + '</td>' +
            '<td>' + replaceNull(item.predmet_contract) + '</td>' +
            '<td>' + replaceNull(item.vidObesp.name) + '</td>' +
            '<td>' + replaceNull(item.sum) + '</td>' +
            '<td>' +
            '<label>' +
            '<input type="checkbox" class="form-check-input" ' +
            'name="' + item.id + '" id="checkboxIspolneno" ' +
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
            '        name="' + item.id + '">Доп.информация' +
            '</a>' +
            '</div>' +
            '<div><a name="' + item.id + '" id="updateContract" href="#">Изменить</a></div>' +
            '<div><a name="' + item.id + '" id="deleteContract" href="#">Удалить</a></div>' +
            '</td>' +

            '</tr>';
    });
    tableContainer.append(trHTML);
}


