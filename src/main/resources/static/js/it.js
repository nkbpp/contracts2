let SORTK = 0;
let SORTD = 0;
$(document).ready(function () {
    let itbody = $("body");
    itbody.on('click', 'a', function () {

        if ($(this).attr('name') === "menudopcontract") { //Кнопка ИТ контракты
            SORTK = 0;
            SORTD = 0;
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/it/vievTable", "", function () {
                ajaxContractIt(getContractItJson(), "");
                $('.datepicker').datepicker({
                    format: 'dd.mm.yyyy',
                    language: "ru"
                });
            });
            return false;
        }

        if ($(this).attr('name') === "menudopaddcontract") { //Кнопка Добавить ИТ контракт
            $("#mainContainer").load("/contract/it/add", "", function () {
                $('.datepicker').datepicker({
                    format: 'dd.mm.yyyy',
                    language: "ru"
                });
            });
            return false;
        }

        if ($(this).attr('id') === "deleteItContract") { //Удалить контракт
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("tr").children().eq(0).text())) {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/it/' + $(this).attr('name'),
                    method: 'delete',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        ajaxContractIt(getContractItJson(), getContractItParams())
                        initialToats("Удаление прошло успешно", data, "success").show();
                    },
                    error: function (textStatus) {
                        initialToats("Ошибка!!!", textStatus, "err").show();
                    }
                });
            }
            return false;
        }


        if ($(this).attr('id') === "updateItContract") { //перейти на вкладку изменения ItContract
            let param = $(this).attr('name');

            $("#mainContainer").load("/contract/it/updateViev", "", function () {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/it/getContract/' + param,
                    method: 'get',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        $("#addContractIt").attr("data-id-contract", data.id)

                        $('textarea[name=nomGK]').val(data.nomGK);
                        $('textarea[name=kontragent]').val(data.kontragent);
                        $('input[name=dateGK]').val(data.dateGK);
                        $('input[name=dateGKs]').val(data.dateGKs);
                        $('input[name=dateGKpo]').val(data.dateGKpo);
                        $('input[name=sum]').val(data.sum.replace(',', '.'));
                        $('input[name=month1]').val(data.months.month1);
                        $('input[name=month2]').val(data.months.month2);
                        $('input[name=month3]').val(data.months.month3);
                        $('input[name=month4]').val(data.months.month4);
                        $('input[name=month5]').val(data.months.month5);
                        $('input[name=month6]').val(data.months.month6);
                        $('input[name=month7]').val(data.months.month7);
                        $('input[name=month8]').val(data.months.month8);
                        $('input[name=month9]').val(data.months.month9);
                        $('input[name=month10]').val(data.months.month10);
                        $('input[name=month11]').val(data.months.month11);
                        $('input[name=month12]').val(data.months.month12);
                        $('#statusGK').val(data.statusGK);
                        /*$('#budgetClassification').val(data.budgetClassification !== null ? data.budgetClassification.id : "0");*/
                        $('#notificationsSelect').val(data.idzirot);
                        $('#dayNotification').val(data.dayNotification);

                        for (let doc of data.documents) {
                            let dv = $("#documentsItViev");
                            dv.html(
                                dv.html() +
                                "<div class='mb-1' data-id-doc='" + doc.id + "'>" + doc.nameFile + " " +
                                "<button class='btn btn-secondary btn-sm' " +
                                "data-id-doc='" + doc.id + "' data-name-doc='delItDoc'>X</button>" +
                                "</div>"
                            );
                        }

                        $('.datepicker').datepicker({
                            format: 'dd.mm.yyyy',
                            language: "ru"
                        });

                        $('#addContractIt').text("Изменить");

                    },
                    error: function (textStatus) {
                        initialToats("Ошибка!!!", textStatus, "err").show();
                        console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                    }
                });
            });
            return false;
        }

        //переключатели страниц pagination
        if ($(this).parents("#paginationItContract").attr("id") === "paginationItContract") {
            clickPagination($(this), "#paginationItContract");
            fpagination()
        }

    })

    itbody.on('change', '#col', function () {
        fpagination()
    });

    //сортировка
    itbody.on('click', 'th', function () {
        if ($(this).attr('id') === "sortD" || $(this).attr('id') === "sortK") {
            clearPagination($("#paginationItContract  a"));
            if ($(this).attr('id') === "sortD") {
                SORTD = (SORTD === 2 ? --SORTD : ++SORTD);
                SORTK = 0;
                $("#sortDspan").html(SORTD === 2 ? "&#9660" : "&#9650");
                $("#sortKspan").text("");
            }
            if ($(this).attr('id') === "sortK") {
                SORTK = (SORTK === 2 ? --SORTK : ++SORTK);
                SORTD = 0;
                $("#sortKspan").html(SORTK === 2 ? "&#9660" : "&#9650");
                $("#sortDspan").text("");
            }
            ajaxContractIt(getContractItJson(), getContractItParams());
        }

        return false;
    });

    itbody.on('click', 'button', function () {

        if ($(this).attr('name') === "findContractIt") { //поиск
            SORTD = 0;
            SORTK = 0;
            clearPagination($("#paginationItContract  a"))
            ajaxContractIt(getContractItJson(), getContractItParams())
            return false;
        }

        if ($(this).attr('data-name-doc') === "delItDoc") {
            let id = $(this).attr('data-id-doc');
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/it/delItDoc/' + id,
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

        //Добавление Изменение
        if ($(this).attr('id') === "addContractIt") {
            // проверка заполнения основных полей
            if (
                !$("#nomGK").val().trim()
            ) {
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled", true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                let idContractIt = $('#addContractIt').attr("data-id-contract");

                let form = new FormData($('#formItContract')[0]);
                let jsonData = Object.fromEntries(form.entries());
                jsonData.id = idContractIt;
                jsonData.statusGK = $('#statusGK').val();
                jsonData.idzirot = $('#notificationsSelect').val();
                jsonData.months = {
                    month1: $('#month1').val(),
                    month2: $('#month2').val(),
                    month3: $('#month3').val(),
                    month4: $('#month4').val(),
                    month5: $('#month5').val(),
                    month6: $('#month6').val(),
                    month7: $('#month7').val(),
                    month8: $('#month8').val(),
                    month9: $('#month9').val(),
                    month10: $('#month10').val(),
                    month11: $('#month11').val(),
                    month12: $('#month12').val(),
                };

                delete jsonData.dopDocuments;

                let formDataFile = new FormData();
                const blob = new Blob([JSON.stringify(jsonData)], {
                    type: 'application/json'
                });

                formDataFile.append("contract", blob);

                let ins = document.getElementById('dopDocuments').files.length;
                if (ins === 0) {
                    let form2 = new FormData($('#formItContract')[0]);
                    let jsonData2 = Object.fromEntries(form2.entries());
                    let data = {};
                    data.file = jsonData2['dopDocuments'];
                    formDataFile.append("file", data.file);
                } else {
                    for (let x = 0; x < ins; x++) {
                        formDataFile.append("file", document.getElementById('dopDocuments').files[x]);
                    }
                }

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');

                //ДОБАВЛЕНИЕ
                if (idContractIt === undefined) {
                    $.ajax({
                        url: "/contract/it",
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
                            $("#mainContainer").load("/contract/it/vievTable", "", function () {
                                ajaxContractIt(getContractItJson(), "");
                            });
                            initialToats("Добавление прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR) {
                            initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                } else { //ИЗМЕНЕНИЕ
                    $.ajax({
                        url: "/contract/it",
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
                            $("#mainContainer").load("/contract/it/vievTable", "", function () {
                                ajaxContractIt(getContractItJson(), "");
                            });
                            initialToats("Изменение прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR) {
                            initialToats("Ошибка при изменении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                }

            }
            return false;
        }
    })

})


function fpagination() {
    ajaxContractIt(getContractItJson(), getContractItParams())
}

function getContractItJson() {
    let object = {};
    object['poleFindByNomGK'] = $("[data-name=poleFindByNomGK]").val();
    object['poleFindByKontragent'] = $("[data-name=poleFindByKontragent]").val();
    object['dateGK'] = $("#poleFindDateGK").val();
    object['poleStatusGK'] = $("#poleStatusGK").val();
    object['idot'] = $("#poleFindnotificationsSelect").val();
    object['sortd'] = SORTD;
    object['sortk'] = SORTK;
    return JSON.stringify(object);
}

function getContractItParams() {
    return "param=" + activeList("#paginationItContract") +
        "&col=" + $("#col").val();
}

function ajaxContractIt(json, params) {

    getSpinnerTable("tableContractIt")

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/it/findTable?" + params,
        data: json,
        type: 'post',
        contentType: "application/json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            let trHTML = '';
            $('#tableContractIt tbody').html("");
            let start = (+activeList("#paginationItContract") - 1) * $("#col").val();
            console.log("ststartr=" + start)
            $.each(response, function (i, data) {
                let docum = "";
                for (let doc of data.documents) {
                    docum +=
                        '<div><a class="btn btn-link" href="/contract/it/download?id=' + doc.id + '">' + replaceNull(doc.nameFile) + '</a></div>';
                }

                let tecdate = new Date();
                let mydate1 = new Date(replaceNull(data.dateGKpo).replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1'));
                let bDate = (tecdate.getTime() > mydate1.getTime()) && (replaceNull(data.statusGK) === "Действующий")

                trHTML +=
                    '<tr class="' + (replaceNull(data.ostatoc) === "0.00" ?
                            "table-success" :
                            bDate ? "table-danger" : ""
                    ) + '">' +
                    '<th>' + (start + +i + 1) + '</th>' +
                    '<td class="fix">' + replaceNull(data.nomGK) + '</td>' +
                    '<td>' + replaceNull(data.kontragent) + '</td>' +
                    '<td>' + replaceNull(data.dateGK) + '</td>' +
                    '<td>' + replaceNull(data.dateGKs) + '</td>' +
                    '<td>' + replaceNull(data.dateGKpo) + '</td>' +
                    '<td>' + replaceNull(data.sum) + '</td>' +
                    '<td>' + replaceNull(data.statusGK) + '</td>' +
                    /*'<td>' + bc + '</td>' +*/
                    '<td>' + replaceNull(data.months.month1) + '</td>' +
                    '<td>' + replaceNull(data.months.month2) + '</td>' +
                    '<td>' + replaceNull(data.months.month3) + '</td>' +
                    '<td>' + replaceNull(data.months.month4) + '</td>' +
                    '<td>' + replaceNull(data.months.month5) + '</td>' +
                    '<td>' + replaceNull(data.months.month6) + '</td>' +
                    '<td>' + replaceNull(data.months.month7) + '</td>' +
                    '<td>' + replaceNull(data.months.month8) + '</td>' +
                    '<td>' + replaceNull(data.months.month9) + '</td>' +
                    '<td>' + replaceNull(data.months.month10) + '</td>' +
                    '<td>' + replaceNull(data.months.month11) + '</td>' +
                    '<td>' + replaceNull(data.months.month12) + '</td>' +
                    '<td>' + replaceNull(data.ostatoc) + '</td>' +
                    '<td>' + replaceNull(data.nameot) + '</td>' +
                    '<td>' + docum + '</td>' +
                    '<td>' +
                    '<div><a name="' + data.id + '" id="updateItContract" href="#">Изменить</a></div>' +
                    '<div><a name="' + data.id + '" id="deleteItContract" href="#">Удалить</a></div>' +
                    '</td>' +
                    '</tr>';
            });
            let tableContractIt = $('#tableContractIt');
            tableContractIt.append(trHTML);
            $('#wrapper1 .div1').attr('style', 'width: ' + tableContractIt.width() + 'px;');
            $('#wrapper2 .div2').attr('style', 'width: ' + tableContractIt.width() + 'px;');
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractIt tbody').html("");
        }
    });
}