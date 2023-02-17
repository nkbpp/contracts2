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
            mainContainer.load("/contract/dop/vievTable", "", function () {
                ajaxContractIt(getContractItJson(), "");
                $('.datepicker').datepicker({
                    format: 'dd.mm.yyyy',
                    language: "ru"
                });
            });
            return false;
        }

        if ($(this).attr('name') === "menudopaddcontract") { //Кнопка Добавить ИТ контракт
            $("#mainContainer").load("/contract/dop/add", "", function () {
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

                //let param = "id=" + $(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/dop/' + $(this).attr('name'),
                    method: 'delete',
                    //data: param,
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

            $("#mainContainer").load("/contract/dop/updateViev/" + param, "", function () {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/dop/getContract/' + param,
                    method: 'get',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        console.log(data)
                        $("#addContractIt").attr("data-id-contract", data.id)

                        $('textarea[name=nomGK]').val(data.nomGK);
                        $('textarea[name=kontragent]').val(data.kontragent);
                        $('input[name=dateGK]').val(data.dateGK);
                        $('input[name=dateGKs]').val(data.dateGKs);
                        $('input[name=dateGKpo]').val(data.dateGKpo);
                        $('input[name=sum]').val(data.sum.replace(',', '.'));
                        $('input[name=january]').val(data.month1);
                        $('input[name=february]').val(data.month2);
                        $('input[name=march]').val(data.month3);
                        $('input[name=april]').val(data.month4);
                        $('input[name=may]').val(data.month5);
                        $('input[name=june]').val(data.month6);
                        $('input[name=july]').val(data.month7);
                        $('input[name=august]').val(data.month8);
                        $('input[name=september]').val(data.month9);
                        $('input[name=october]').val(data.month10);
                        $('input[name=november]').val(data.month11);
                        $('input[name=december]').val(data.month12);
                        $('#statusGK').val(data.statusGK);
                        /*$('#budgetClassification').val(data.budgetClassification !== null ? data.budgetClassification.id : "0");*/
                        $('#notificationsSelect').val(data.idzirot);

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
            let list = clickPagination($(this), "#paginationItContract");
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
            //let param = "id=" + id;
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/dop/delItDoc/' + id,
                method: 'delete',
                //data: param,
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

        if ($(this).attr('id') === "addContractIt") {
            // проверка заполнения основных полей
            if (
                !$("#nomGK").val().trim()
            ) {
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled", true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                let form = new FormData($('#formItContract')[0]);
                let jsonData = Object.fromEntries(form.entries());
                let id = $('#addContractIt').attr("data-id-contract")
                jsonData.id = id;
                jsonData.statusGK = $('#statusGK').val();
                jsonData.idzirot = $('#notificationsSelect').val();
                /*let bc = {};
                bc.id = $('#budgetClassification').val()
                jsonData.budgetClassification = bc;*/

                let data = {};
                data.file = jsonData['itDocuments'];
                delete jsonData.itDocuments;

                let formDataFile = new FormData();

                const blob = new Blob([JSON.stringify(jsonData)], {
                    type: 'application/json'
                });

                console.log(JSON.stringify(jsonData))
                formDataFile.append("contract", blob)

                let ins = document.getElementById('itDocuments').files.length;
                if (ins === 0) {
                    formDataFile.append("file", data.file);
                } else {
                    for (let x = 0; x < ins; x++) {
                        formDataFile.append("file", document.getElementById('itDocuments').files[x]);
                    }
                }

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');

                //ДОБАВЛЕНИЕ
                if ($('#addContractIt').attr("data-id-contract") === undefined) {
                    $.ajax({
                        url: "/contract/dop",
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
                            $("#mainContainer").load("/contract/dop/vievTable", "", function () {
                                ajaxContractIt(getContractItJson(), "");
                            });
                            initialToats("Добавление прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR, textStatus) {
                            initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                } else { //ИЗМЕНЕНИЕ
                    $.ajax({
                        url: "/contract/dop",
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
                            $("#mainContainer").load("/contract/dop/vievTable", "", function () {
                                ajaxContractIt(getContractItJson(), "");
                            });
                            initialToats("Изменение прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR, textStatus) {
                            initialToats("Ошибка при изменении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                }

                /*$.post({
                    url: "/contract/dop/upload",
                    data: param,
                    cache: false,
                    processData: false, // Не обрабатываем файлы (Don't process the files)
                    contentType: false, // Так jQuery скажет серверу что это строковой запрос
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        //после добавления показать таблицу
                        $("#mainContainer").load("/contract/dop/vievTable", "", function () {
                            ajaxContractIt(getContractItJson(), "");
                        });
                        initialToats("Добавление прошло успешно", data, "success").show();
                    },
                    error: function (jqXHR, textStatus) {
                        initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                    }
                });*/
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
        url: "/contract/dop/findTable?" + params,
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
            $.each(response, function (i, item) {
                let docum = "";
                for (let doc of item.documents) {
                    docum +=
                        '<div><a class="btn btn-link" href="/contract/dop/download?id=' + doc.id + '">' + replaceNull(doc.nameFile) + '</a></div>';
                }

                /*let bc = "";
                if (item.budgetClassification === null || item.budgetClassification === "" || item.budgetClassification === undefined) {
                } else {
                    bc = replaceNull(item.budgetClassification.kod);
                }*/
                let tecdate = new Date();
                let mydate1 = new Date(replaceNull(item.dateGKpo).replace(/(\d+).(\d+).(\d+)/, '$3/$2/$1'));
                let bDate = (tecdate.getTime() > mydate1.getTime()) && (replaceNull(item.statusGK) === "Действующий")
                console.log(tecdate)
                console.log(mydate1)
                console.log(bDate)
                trHTML +=
                    '<tr class="' + (replaceNull(item.ostatoc) === "0.00" ?
                            "table-success" :
                            bDate ? "table-danger" : ""
                    ) + '">' +
                    '<th>' + (start + +i + 1) + '</th>' +
                    /*'<td>' + replaceNull(item.id) + '</td>' +*/
                    '<td class="fix">' + replaceNull(item.nomGK) + '</td>' +
                    '<td>' + replaceNull(item.kontragent) + '</td>' +
                    '<td>' + replaceNull(item.dateGK) + '</td>' +
                    '<td>' + replaceNull(item.dateGKs) + '</td>' +
                    '<td>' + replaceNull(item.dateGKpo) + '</td>' +
                    '<td>' + replaceNull(item.sum) + '</td>' +
                    '<td>' + replaceNull(item.statusGK) + '</td>' +
                    /*'<td>' + bc + '</td>' +*/
                    '<td>' + replaceNull(item.month1) + '</td>' +
                    '<td>' + replaceNull(item.month2) + '</td>' +
                    '<td>' + replaceNull(item.month3) + '</td>' +
                    '<td>' + replaceNull(item.month4) + '</td>' +
                    '<td>' + replaceNull(item.month5) + '</td>' +
                    '<td>' + replaceNull(item.month6) + '</td>' +
                    '<td>' + replaceNull(item.month7) + '</td>' +
                    '<td>' + replaceNull(item.month8) + '</td>' +
                    '<td>' + replaceNull(item.month9) + '</td>' +
                    '<td>' + replaceNull(item.month10) + '</td>' +
                    '<td>' + replaceNull(item.month11) + '</td>' +
                    '<td>' + replaceNull(item.month12) + '</td>' +
                    '<td>' + replaceNull(item.ostatoc) + '</td>' +
                    '<td>' + replaceNull(item.nameot) + '</td>' +
                    '<td>' + docum + '</td>' +
                    '<td>' +
                    '<div><a name="' + item.id + '" id="updateItContract" href="#">Изменить</a></div>' +
                    '<div><a name="' + item.id + '" id="deleteItContract" href="#">Удалить</a></div>' +
                    '</td>' +
                    '</tr>';
            });
            $('#tableContractIt').append(trHTML);
            $('#wrapper1 .div1').attr('style', 'width: ' + $('#tableContractIt').width() + 'px;');
            $('#wrapper2 .div2').attr('style', 'width: ' + $('#tableContractIt').width() + 'px;');
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractIt tbody').html("");
        }
    });
}