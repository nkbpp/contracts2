$(document).ready(function () {

    ajaxJurauditTable(getJurauditJson(), "");

    $("body").on('click', 'a', function () {

        if ($(this).attr('id') === "logiClear") {

            $("#ttt").load("/contract/admin/juraudit/clear", function () {
                // Вешаем сортировку на таблицу:
                sortTable($("#tab").get(0), sortAsc1, sortDesc1, sortRules);
            });
            return false;

        }

    });

});

function getJurauditParams() {
    return "param=" + activeList("#paginationItContract") +
        "&col=" + $("#col").val();
}

function getJurauditJson() {
    let object = {};
    object['dateBefore'] = $("#dateBefore").val();
    object['dateAfter'] = $("#dateAfter").val();
    object['login'] = $("#login").val();
    object['type'] = $("#type").val();
    object['text'] = $("#text").val();
    console.log(JSON.stringify(object))
    return JSON.stringify(object);
}

function ajaxJurauditTable(json, params) {
    //getSpinnerTable("jurauditTable")

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');

    $.ajax({
        url: "/contract/admin/juraudit/tables?" + params,
        data: json,
        contentType: "application/json",
        dataType: 'json',
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            console.log("1")
            ajaxJurauditTableSuccess(response)
        },
        error: function (response) {
            initialToats("Ошибка!", response.responseJSON.message, "err").show();
        }
    });

}

function ajaxJurauditTableSuccess(response) {
    let trHTML = '';
    let tableContainer = $('#jurauditTable tbody');
    tableContainer.html("");
    let start = (+activeList("#pagination") - 1) * 30; //todo

    $.each(response, function (i, item) {

        trHTML +=
            '<tr>' +
            '<td>' + item.date + '</td>' +
            '<td>' + item.user + '</td>' +
            '<td>' + item.type + '</td>' +
            '<td>' + item.text + '</td>' +
            '</tr>';
    });
    tableContainer.append(trHTML);
}