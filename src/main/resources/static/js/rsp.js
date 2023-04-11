$(document).ready(function () {

    let body = $("body");

    body.on('click', 'a', function () {

        if ($(this).attr('id') === "menurspcontract") { //Кнопка просмотр
            loadMenuViewRsp("");
            return false;
        }

        if ($(this).attr('id') === "menurspaddcontract") { //Добавить контракт
            $("#mainContainer").load("/contract/rsp/add", "", function () {
                $("#containerNotification").load("/contract/rsp/getnotification", "", function () {

                    //todo скрыть модальное окно
                    $("[data-bs-target='#modalKontragent']").addClass("d-none");

                });

                /*let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/rsp/dopnotification',
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
                });*/
                $('.datepicker').datepicker({
                    format: 'dd.mm.yyyy',
                    language: "ru"
                });
            });
            return false;
        }

        // viev изменения контракта
        if ($(this).attr('id') === "updateContractRsp") {

            let id = $(this).attr('name');
            let param = "id=" + id;

            $("#mainContainer").load("/contract/rsp/updateViev", param, function () {
                $("#containerNotification").load("/contract/rsp/getnotification", "", function () {
                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/rsp/contract/' + id,
                        method: 'get',
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            $("#addContractsRsp").attr("data-id-contract", data.id)
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

                            $('#addContractsRsp').text("Изменить");

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
        if ($(this).attr('id') === "deleteContractRsp") {
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("#" + $(this).attr('name')).children().eq(0).text())) {

                let id = $(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/rsp/contract/' + id,
                    method: 'delete',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        let param = "param=" + activeList("#pagination");//чтобы при удалении осталась текущая страница
                        ajaxContractRspAll(param);
                        initialToats("Удаление прошло успешно", data, "success").show();
                    },
                    error: function (textStatus) {
                        initialToats("Ошибка!!!", textStatus, "err").show();
                    }
                });
            }
            return false;
        }

        //дополнительная инфа
        if ($(this).attr('data-id-dop-modal') === "dataADopModalRsp") {
            let id = $(this).attr('name');
            $("#modalDopContainerContent").load("/contract/rsp/dopTable", "", function () {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: "/contract/rsp/contract/" + id,
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
                                'href="/contract/rsp/download/' + doc.id + '" ' +
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

        //переключатели страниц pagination
        if ($(this).parents("[name=paginationRsp]").attr("id") === "pagination") {
            let list = clickPagination($(this), "#pagination");
            let param = "param=" + list +
                "&poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
                "&poleFindByINN=" + $("[data-name=poleFindByINN]").val() +
                "&poleFindByIspolneno=" + $('[data-name=poleFindByIspolneno]').prop("checked") +
                "&poleFindByNotIspolneno=" + $('[data-name=poleFindByNotIspolneno]').prop("checked");
            ajaxContractRspAll(param)
        }

    });

    body.on('click', 'button', function () {

        //изменение/добавление контракта
        if ($(this).attr('id') === "addContractsRsp") {
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
                let jsonData = Object.fromEntries(formData.entries());

                delete jsonData.kontragent;
                delete jsonData.vidObesp;
                delete jsonData.myDocuments;
                let id = $('#addContractsRsp').attr("data-id-contract")
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
                        url: "/contract/rsp/contract",
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
                            $("#mainContainer").load("/contract/rsp/vievTable", "", function () {
                                ajaxContractRspAll("");
                            });
                            initialToats("Добавление прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR) {
                            initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                } else { //изменение
                    $.ajax({
                        url: "/contract/rsp/contract",
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
                            $("#mainContainer").load("/contract/rsp/vievTable", "", function () {
                                ajaxContractRspAll("");
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

        //поиск
        if ($(this).attr('name') === "findContractRsp") {
            let param = "poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
                "&poleFindByINN=" + $("[data-name=poleFindByINN]").val() +
                "&poleFindByIspolneno=" + $('[data-name=poleFindByIspolneno]').prop("checked") +
                "&poleFindByNotIspolneno=" + $('[data-name=poleFindByNotIspolneno]').prop("checked");
            ajaxContractRspAll(param);
            clearPagination1();
            return false;
        }

    });

    //checkBox в таблице
    body.on('click', 'input', function () {
        if ($(this).attr('id') === "checkboxIspolnenoRsp") {
            let id = $(this).attr("name");

            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');

            $.ajax({
                url: '/contract/rsp/setIspolneno/' + id,
                method: 'get',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    let param = "id=" + id;
                    $("[data-progress-id=" + id + "]").load("/contract/rsp/getProgress", param, function () {
                    });
                    initialToats("Изменение статуса прошло успешно", data, "success").show();
                },
                error: function (textStatus) {
                    initialToats("Ошибка в изменении статуса!!!", textStatus, "err").show();
                }
            });
        }
    });

});

function loadMenuViewRsp() {
    let mainContainer = $("#mainContainer");
    mainContainer.html(getSpinner());
    mainContainer.load("/contract/rsp/vievTable", "", function () {
        ajaxContractRspAll("");
        $("[name=findContract]").attr("name", "findContractRsp");
        $("#pagination").attr("name", "paginationRsp");
    });

}

function ajaxContractRspAll(params) {
    getSpinnerTable("tableContainer")

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/rsp/All?" + params,
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
            ajaxContractSuccess(response, "Rsp")
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
        }
    });
}