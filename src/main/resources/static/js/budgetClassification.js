function budgetClassification() {

    $("#mainContainer").load("/contract/main/budgetClassification/budgetClassificationViev", "", function () {
    });
}

$(document).ready(function () {

    let spisokBudgetClassification = $("body");

    spisokBudgetClassification.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if (e.keyCode === 13) {
            if ($(this).attr('name') === "disabledInputBudgetClassificationKod" ||
                $(this).attr('name') === "disabledInputBudgetClassificationName") {
                console.log("enter")
                $(this).blur();
                return false;
            }
        }
    });

    spisokBudgetClassification.on('blur', 'input', function () { //изменение на событие потери фокуса
        if ($(this).attr('name') === "disabledInputBudgetClassificationKod" ||
            $(this).attr('name') === "disabledInputBudgetClassificationName") {  //список вид обесп
            let tek = $(this);
            let id = tek.attr('data-disabledInputBudgetClassification-id');
            let kod = $('[data-disabledInputBudgetClassification-id=' + id + '][name=disabledInputBudgetClassificationKod]').val();
            let name = $('[data-disabledInputBudgetClassification-id=' + id + '][name=disabledInputBudgetClassificationName]').val();
            //let param = "id=" + id + "&name=" + name + "&inn=" + inn;

            let json = JSON.stringify({
                'id': id,
                'name': name,
                'kod': kod
            });

            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/budgetClassification',
                method: 'put',
                data: json,
                contentType: "application/json",
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

    spisokBudgetClassification.on('dblclick', 'input', function () {
        if ($(this).attr('name') === "disabledInputBudgetClassificationKod" ||
            $(this).attr('name') === "disabledInputBudgetClassificationName") {
            $(this).removeAttr('disabled').removeAttr('readonly');
            $(this).focus();
            return false;
        }
    });

    spisokBudgetClassification.on('click', 'a', function () {

        if ($(this).attr('id') == "budgetClassificationViev") {  //список контрагент
            budgetClassification();
        }

        if ($(this).attr('id') == "buttonAddBudgetClassification") {
            $(this).prop("disabled", true);

            let json = JSON.stringify({
                'name': $("#addNameBudgetClassification").val(),
                'kod': $("#addKodBudgetClassification").val()
            });

            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/budgetClassification',
                method: 'post',
                data: json,
                contentType: "application/json",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    budgetClassification();
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


        if ($(this).attr('name') == "delSpisokBudgetClassification") {
            //let param = "id=" + $(this).attr('id');
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/budgetClassification/' + $(this).attr('id'),
                method: 'delete',
                //data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    budgetClassification();
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