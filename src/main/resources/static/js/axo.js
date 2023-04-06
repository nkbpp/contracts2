$(document).ready(function () {
    let axobody = $("body");
    axobody.on('click', 'a', function () {

        if ($(this).attr('id') === "updateAxoContract") { //перейти на вкладку изменения ItContract
            let param = $(this).attr('name');

            $("#mainContainer").load("/contract/axo/updateViev/" + param, "", function () {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/axo/getContract/' + param,
                    method: 'post',
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        $("#addContractAxo").attr("data-id-contract", data.id);
                        $('textarea[name=nomGK]').val(data.nomGK);
                        $('textarea[name=kontragent]').val(data.kontragent);
                        $('input[name=dateGK]').val(data.dateGK);
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

                        $('input[name=sumNaturalIndicators]').val(data.sumNaturalIndicators);
                        $('#range').val(data.naturalIndicators.length);
                        inputAddClassDnone(data.naturalIndicators.length);

                        console.log("inputs = " + data.naturalIndicators.length)
                        let inputs = $('#nt').find('input');
                        for (let i = 0; i < data.naturalIndicators.length; i++) {
                            console.log("sum[" + i + "] = " + data.naturalIndicators[i].sum)
                            inputs.eq(i).val(data.naturalIndicators[i].sum);
                        }

                        $('textarea[name=doc]').val(data.documentu);

                        for (let doc of data.documents) {
                            let dv = $("#documentsAxoViev");
                            dv.html(
                                dv.html() +
                                "<div class='mb-1' data-id-doc='" + doc.id + "'>" + doc.nameFile + " " +
                                "<button class='btn btn-secondary btn-sm' " +
                                "data-id-doc='" + doc.id + "' data-name-doc='delAxoDoc'>X</button>" +
                                "</div>"
                            );
                        }
                        $('.datepicker').datepicker({
                            format: 'dd.mm.yyyy',
                            language: "ru"
                        });
                        $('#addContractAxo').text("Изменить");
                    },
                    error: function (textStatus) {
                        initialToats("Ошибка!!!", textStatus, "err").show();
                    }
                });
            });
            return false;
        }

        if ($(this).attr('id') === "menuaxoaddcontract") { //Кнопка Добавить Axo контракт TODO
            $("#mainContainer").load("/contract/axo/add", "", function () {
                $('.datepicker').datepicker({
                    format: 'dd.mm.yyyy',
                    language: "ru"
                });
            });
            return false;
        }

        if ($(this).attr('id') === "menuaxocontract") { //Кнопка АХО контракты
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/axo/vievTable", "", function () {
                ajaxContractAxoNoNatural("");
                $('.div1').width($('table').width());
                $('.div2').width($('table').width());
            });
            return false;
        }

        if ($(this).attr('id') === "deleteAxoContract") { //Удалить контракт
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("tr").children().eq(0).text())) {

                //let param = "id=" + $(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/axo/deleteContract/' + $(this).attr('name'),
                    method: 'delete',
                    //data: param,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        ajaxContractAxo()

                        initialToats("Удаление прошло успешно", data, "success").show();
                    },
                    error: function (textStatus) {
                        initialToats("Ошибка!!!", textStatus, "err").show();
                    }
                });
            }
            return false;
        }

        //переключатели страниц pagination
        if ($(this).parents("#paginationAxoContract").attr("id") === "paginationAxoContract") {
            let list = clickPagination($(this), "#paginationAxoContract");
            ajaxContractAxo()
        }

    })

    //сколько выводить на странице
    axobody.on('change', '#col', function () {
        ajaxContractAxo()
    });

    //checkBox в таблице натурального показателя
    axobody.on('click', 'input', function () {
        if ($(this).attr('id') === "flexCheckChecked") {
            ajaxContractAxo()
        }
    });

    axobody.on('click', 'button', function () {

        //Добавление Изменение
        if ($(this).attr('id') === "addContractAxo") {
            // проверка заполнения основных полей
            if (
                !$("#nomGK").val().trim()
            ) {
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled", true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                let idContractAxo = $('#addContractAxo').attr("data-id-contract");

                let form = new FormData($('#formAxoContract')[0]);
                let jsonData = Object.fromEntries(form.entries());
                jsonData.id = idContractAxo;

                let ntinput = $('#nt').find('.col-3:not(.d-none) input');
                let ntArr = []
                for (let i = 0; i < ntinput.length; i++) {
                    ntArr[i] = {sum: ntinput.eq(i).val()}
                }
                ;
                jsonData.naturalIndicators = ntArr;
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

                formDataFile.append("contract", blob)

                let ins = document.getElementById('dopDocuments').files.length;
                if (ins === 0) {
                    let form2 = new FormData($('#formAxoContract')[0]);
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
                if (idContractAxo === undefined) {
                    console.log("add")
                    console.log(jsonData)
                    $.ajax({
                        url: "/contract/axo",
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
                            $("#mainContainer").load("/contract/axo/vievTable", "", function () {
                                ajaxContractAxoNoNatural("");
                            });
                            initialToats("Добавление прошло успешно", data, "success").show();
                        },
                        error: function (jqXHR) {
                            initialToats("Ошибка при добавлении!!!", jqXHR.responseText, "err").show();
                        }
                    });
                } else {
                    $.ajax({
                        url: "/contract/axo",
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
                            //после добавления показать таблицу
                            $("#mainContainer").load("/contract/axo/vievTable", "", function () {
                                ajaxContractAxoNoNatural("");
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


        if ($(this).attr('data-name-doc') === "delAxoDoc") {
            let id = $(this).attr('data-id-doc');
            //let param = "id="+id;
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/axo/delAxoDoc/' + id,
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

        if ($(this).attr('name') === "findContractAxo") { //поиск
            clearPagination($("#paginationAxoContract a"))
            ajaxContractAxo()
            return false;
        }

    })

    axobody.on('input', 'input', function () { //range
        if ($(this).attr('id') === 'range') {
            let value = +$(this).val();
            inputAddClassDnone(value);
        }
    });


})


function inputAddClassDnone(value) {
    $('#spanVal').text(value)
    let inputs = $('#nt').find('.col-3');
    for (let i = 0; i < inputs.length; i++) {
        if (i < value)
            inputs.eq(i).removeClass('d-none');
        else
            inputs.eq(i).addClass('d-none');
    }
}

function ajaxContractAxoNoNatural(params) {

    $("#tableContractAxo thead").html(
        "<tr>" +
        "            <th rowSpan='2'>№</th>" +
        "            <th colSpan='4'>Государственный контракт</th>" +
        "            <th colSpan='3'>1-й квартал</th>" +
        "            <th colSpan='3'>2-й квартал</th>" +
        "            <th colSpan='3'>3-й квартал</th>" +
        "            <th colSpan='3'>4-й квартал</th>" +
        "            <th rowSpan='2'>Остаток</th>" +
        //"            <th rowSpan='2' style='min-width:200px'>Документы</th>" +
        "            <th rowSpan='2'>Прикрепленные файлы</th>" +
        "            <th rowSpan='2'>Действие</th>" +
        "        </tr>" +
        "" +
        "    <tr>" +
        "        <th style='min-width:200px' class='fix'>Номер</th>" +
        "        <th style='min-width:200px'>Контрагент</th>" +
        "        <th>Дата</th>" +
        "        <th>Сумма</th>" +
        "" +
        "        <th>Январь</th>" +
        "        <th>Февраль</th>" +
        "        <th>Март</th>" +
        "" +
        "        <th>Апрель</th>" +
        "        <th>Май</th>" +
        "        <th>Июнь</th>" +
        "" +
        "        <th>Июль</th>" +
        "        <th>Август</th>" +
        "        <th>Сентябрь</th>" +
        "" +
        "        <th>Октябрь</th>" +
        "        <th>Ноябрь</th>" +
        "        <th>Декабрь</th>" +
        "    </tr>"
    )

    getSpinnerTable("tableContractAxo")
    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/axo/findTable?" + params,
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
            let trHTML = '';
            $('#tableContractAxo tbody').html("");
            let start = (+activeList("#paginationAxoContract") - 1) * $("#col").val();
            console.log("ststartr=" + start)
            $.each(response, function (i, data) {
                let docum = "";
                for (let doc of data.documents) {
                    docum +=
                        '<div><a class="btn btn-link" href="/contract/dop/download?id=' + doc.id + '">' + replaceNull(doc.nameFile) + '</a></div>';
                }

                trHTML +=
                    '<tr class="' + (replaceNull(data.ostatoc) === "0.00" ? "table-success" : "") + '">' +
                    '<th>' + (start + +i + 1) + '</th>' +
                    /*'<td>' + replaceNull(data.id) + '</td>' +*/
                    '<td class="fix">' + replaceNull(data.nomGK) + '</td>' +
                    '<td>' + replaceNull(data.kontragent) + '</td>' +
                    '<td>' + replaceNull(data.dateGK) + '</td>' +
                    '<td>' + replaceNull(data.sum) + '</td>' +
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
                    /*'<td>' + replaceNull(data.documentu) + '</td>' +*/
                    '<td>' + docum + '</td>' +
                    '<td>' +
                    '<div><a name="' + data.id + '" id="updateAxoContract" href="#">Изменить</a></div>' +
                    '<div><a name="' + data.id + '" id="deleteAxoContract" href="#">Удалить</a></div>' +
                    '</td>' +
                    '</tr>';
            });

            $('#tableContractAxo').append(trHTML);
            $('#wrapper1 .div1').attr('style', 'width: ' + $('#tableContractAxo').width() + 'px;');
            $('#wrapper2 .div2').attr('style', 'width: ' + $('#tableContractAxo').width() + 'px;');
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractAxo tbody').html("");
        }
    });
}

function ajaxContractAxoNatural(params) {

    $("#tableContractAxo thead").html(
        "<tr>" +
        "        <th rowspan='2'>№</th>" +
        "        <th colspan='4'>Государственный контракт</th>" +
        "        <th colspan='12'>Расход по месяцам</th>" +
        "        <th colspan='2'>Средний расход в месяц</th>" +
        "        <th rowspan='2'>Остаток</th>" +
        "        <th rowspan='2'>Действие</th>" +
        "    </tr>" +
        "" +
        "    <tr>" +
        "        <th style='min-width:200px' class='fix'>Номер</th>" +
        "        <th style='min-width:200px'>Контрагент</th>" +
        "        <th>Дата</th>" +
        "        <th>Сумма (натуральный показатель)</th>" +
        "" +
        "        <th>1</th>" +
        "        <th>2</th>" +
        "        <th>3</th>" +
        "" +
        "        <th>4</th>" +
        "        <th>5</th>" +
        "        <th>6</th>" +
        "" +
        "        <th>7</th>" +
        "        <th>8</th>" +
        "        <th>9</th>" +
        "" +
        "        <th>10</th>" +
        "        <th>11</th>" +
        "        <th>12</th>" +
        "" +
        "        <th>ожидаемый</th>" +
        "        <th>фактический</th>" +
        "    </tr>"
    )

    getSpinnerTable("tableContractAxo")
    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/axo/findTable?" + params,
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
            let trHTML = '';
            $('#tableContractAxo tbody').html("");
            let start = (+activeList("#paginationAxoContract") - 1) * $("#col").val();
            $.each(response, function (i, data) {

                trHTML +=
                    '<tr class="' + (replaceNull(data.ostatoc) === "0.00" ? "table-success" : "") + '">' +
                    '<th>' + (start + +i + 1) + '</th>' +
                    /*'<td>' + replaceNull(data.id) + '</td>' +*/
                    '<td class="fix">' + replaceNull(data.nomGK) + '</td>' +
                    '<td>' + replaceNull(data.kontragent) + '</td>' +
                    '<td>' + replaceNull(data.dateGK) + '</td>' +
                    '<td>' + replaceNull(data.sumNaturalIndicators) + '</td>' +
                    '<td>' + (data.naturalIndicators[0] === null || data.naturalIndicators[0] === undefined ? '' : replaceNull(data.naturalIndicators[0].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[1] === null || data.naturalIndicators[1] === undefined ? '' : replaceNull(data.naturalIndicators[1].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[2] === null || data.naturalIndicators[2] === undefined ? '' : replaceNull(data.naturalIndicators[2].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[3] === null || data.naturalIndicators[3] === undefined ? '' : replaceNull(data.naturalIndicators[3].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[4] === null || data.naturalIndicators[4] === undefined ? '' : replaceNull(data.naturalIndicators[4].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[5] === null || data.naturalIndicators[5] === undefined ? '' : replaceNull(data.naturalIndicators[5].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[6] === null || data.naturalIndicators[6] === undefined ? '' : replaceNull(data.naturalIndicators[6].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[7] === null || data.naturalIndicators[7] === undefined ? '' : replaceNull(data.naturalIndicators[7].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[8] === null || data.naturalIndicators[8] === undefined ? '' : replaceNull(data.naturalIndicators[8].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[9] === null || data.naturalIndicators[9] === undefined ? '' : replaceNull(data.naturalIndicators[9].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[10] === null || data.naturalIndicators[10] === undefined ? '' : replaceNull(data.naturalIndicators[10].sum)) + '</td>' +
                    '<td>' + (data.naturalIndicators[11] === null || data.naturalIndicators[11] === undefined ? '' : replaceNull(data.naturalIndicators[11].sum)) + '</td>' +
                    '<td>' + replaceNull(data.ojidRashodMonth) + '</td>' +
                    '<td>' + replaceNull(data.factRashodMonth) + '</td>' +
                    '<td>' + replaceNull(data.ostatocNatural) + '</td>' +
                    '<td>' +
                    '<div><a name="' + data.id + '" id="updateAxoContract" href="#">Изменить</a></div>' +
                    '<div><a name="' + data.id + '" id="deleteAxoContract" href="#">Удалить</a></div>' +
                    '</td>' +
                    '</tr>';
            });

            $('#tableContractAxo').append(trHTML);
            $('#wrapper1 .div1').attr('style', 'width: ' + $('#tableContractAxo').width() + 'px;');
            $('#wrapper2 .div2').attr('style', 'width: ' + $('#tableContractAxo').width() + 'px;');
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractAxo tbody').html("");
        }
    });
}

function getContractAxoParams() {
    return "param=" + activeList("#paginationAxoContract") +
        "&poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
        "&poleFindByKontragent=" + $("[data-name=poleFindByKontragent]").val() +
        "&col=" + $("#col").val();
}

function ajaxContractAxo() {
    let bool = $("#flexCheckChecked").prop("checked");
    if (bool) {
        ajaxContractAxoNatural(getContractAxoParams())
    } else {
        ajaxContractAxoNoNatural(getContractAxoParams())
    }
}