$(document).ready(function () {

    $('#mainContainer').html("");
    statContract();
    statContractIt()

    //Контракты
    $("body").on('click', 'a', function () {
        if ($(this).attr('id') === "statAll") { //Статистика показать все
            loadMenuView();
            return false;
        }
        if ($(this).attr('id') === "statIspolneno") { //Статистика показать исполненые
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/main/vievTable", "", function () {
                ajaxContractAll("poleFindByNotIspolneno=false");
                $("#poleFindByNotIspolneno").removeAttr("checked");
            });
            return false;
        }
        if ($(this).attr('id') === "statNotIspolneno") { //Статистика показать не исполненые
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/main/vievTable", "", function () {
                ajaxContractAll("poleFindByIspolneno=false");
                $("#poleFindByIspolneno").removeAttr("checked");
            });
            return false;
        }

        if ($(this).attr('id') === "statNotIspolnenoSrok") { //Статистика показать не исполненые приближается срок исполнения
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/main/vievTable", "", function () {
                ajaxContractSort("statNotIspolnenoSrok");
                $("#poleFindByIspolneno").removeAttr("checked");
            });
            return false;
        }
        if ($(this).attr('id') === "statNodate") { //Статистика показать без даты
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/main/vievTable", "", function () {
                ajaxContractSort("statNodate");
                $("#poleFindByIspolneno").removeAttr("checked");
            });
            return false;
        }
        if ($(this).attr('id') === "statProsrocheno") { //Статистика показать просроченные
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/main/vievTable", "", function () {
                ajaxContractSort("statProsrocheno");
                $("#poleFindByIspolneno").removeAttr("checked");
            });
            return false;
        }
    })

    //Контракты ИТ
    $("body").on('click', 'a', function () {

        if ($(this).attr('id') === "statItAll") { //СтатистикаИТ показать все
            ajaxContractItStatusGK("")
        }
        if ($(this).attr('id') === "statItExecuted") { //Статистика показать исполненые
            ajaxContractItStatusGK("Исполнен")
        }
        if ($(this).attr('id') === "statItCurrent") { //Статистика действующие
            ajaxContractItStatusGK("Действующий")
        }
        if ($(this).attr('id') === "statItTerminated") { //Статистика показать Расторгнут
            ajaxContractItStatusGK("Расторгнут")
        }
        if ($(this).attr('id') === "statItNoStatus") { //Статистика показать без даты
            ajaxContractItStatusGK("Без статуса")
        }
    })

})


function statContract() {

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/main/stat",
        type: 'GET',
        contentType: "application/json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            let tableContract = $('#mainContainer');

            let trHTML = '' +
                '<h3>Обеспечение исполнения контрактов</h3>' +
                '<h6>Всего контрактов: ' +
                '<a class="link" href="#!" id="statAll">' + response.size + '</a>' +
                '</h6>' +
                '<h6>Исполнено контрактов: ' +
                '<a class="link" href="#!" id="statIspolneno">' + response.ispolneno + '</a>' +
                '</h6>' +
                '<h6>Не исполнено контрактов: ' +
                '<a class="link" href="#!" id="statNotIspolneno">' + response.notispolneno + '</a>' +
                '</h6>' +
                '<h6>Приближается срок исполнения контрактов: ' +
                '<a class="link" href="#!" id="statNotIspolnenoSrok">' + response.notispolnenosrok + '</a>' +
                '</h6>' +
                '<h6>Не задан срок исполнения контракта: ' +
                '<a class="link" href="#!" id="statNodate">' + response.nodate + '</a>' +
                '</h6>' +
                '<h6>Просрочено контрактов: ' +
                '<a class="link" href="#!" id="statProsrocheno">' + response.prosrocheno + '</a>' +
                '</h6>'

            tableContract.append(trHTML);

        },
        error: function (response) {
            /*initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractIt tbody').html("");*/
        }
    });
}

function statContractIt() {

    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    $.ajax({
        url: "/contract/it/stat",
        type: 'GET',
        contentType: "application/json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (response) {
            let tableContract = $('#mainContainer');

            let trHTML = '' +
                '<h3>Отдел ИТ</h3>' +
                '<h6>Всего контрактов: ' +
                '<a class="link" href="#!" id="statItAll">' + response.size + '</a>' +
                '</h6>' +
                '<h6>В статусе исполнено: ' +
                '<a class="link" href="#!" id="statItExecuted">' + response.executed + '</a>' +
                '</h6>' +
                '<h6>В статусе действующий: ' +
                '<a class="link" href="#!" id="statItCurrent">' + response.current + '</a>' +
                '</h6>' +
                '<h6>В статусе расторгнут: ' +
                '<a class="link" href="#!" id="statItTerminated">' + response.terminated + '</a>' +
                '</h6>' +
                '<h6>Без статуса: ' +
                '<a class="link" href="#!" id="statItNoStatus">' + response.noStatus + '</a>' +
                '</h6>'

            tableContract.append(trHTML);

        },
        error: function (response) {
            /*initialToats("Ошибка!", response.responseJSON.message, "err").show();
            $('#tableContractIt tbody').html("");*/
        }
    });
}

function ajaxContractItStatusGK(statusGK) {
    let mainContainer = $("#mainContainer");
    mainContainer.html(getSpinner());
    mainContainer.load("/contract/it/vievTable", "", function () {
        ajaxContractIt(JSON.stringify({poleStatusGK: statusGK}), "");
        $('.datepicker').datepicker({
            format: 'dd.mm.yyyy',
            language: "ru"
        });
        $("#poleStatusGK").val(statusGK);
    });
    return false;
}