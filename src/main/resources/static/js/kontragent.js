function vievKontragent (){
    $("#mainContainer").load("/contract/main/kontragent/kontragentViev", "", function () {
    });
}

$(document).ready(function () {

    let spisokKontr = $("body");

    spisokKontr.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if(e.keyCode === 13) {
            if ($(this).attr('name') === "disabledInputKontragentName" ||
                $(this).attr('name') === "disabledInputKontragentInn") {
                console.log("enter")
                $(this).blur();
                return false;
            }
        }
    });

    spisokKontr.on('blur', 'input', function () { //изменение на событие потери фокуса
        if ($(this).attr('name') === "disabledInputKontragentName" ||
            $(this).attr('name') === "disabledInputKontragentInn") {  //список вид обесп
            let tek = $(this);
            let id = tek.attr('data-disabledInputKontragent-id');
            let name = $('[data-disabledInputKontragent-id='+id+'][name=disabledInputKontragentName]').val();
            let inn = $('[data-disabledInputKontragent-id='+id+'][name=disabledInputKontragentInn]').val();
            let param = "id=" + id + "&name=" + name + "&inn=" + inn;
            console.log(param)
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/kontragent/update',
                method: 'post',
                data: param,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    console.log("Данные изменены",data);
                    tek.prop('disabled',true).prop('readonly',true);
                    initialToats("Изменение прошло успешно",data,"success").show();
                },
                error: function (textStatus) {
                    initialToats("Ошибка!!!",textStatus,"err").show();
                }
            });
        }
    });

    spisokKontr.on('dblclick', 'input', function () {
        if ($(this).attr('name') === "disabledInputKontragentName" ||
            $(this).attr('name') === "disabledInputKontragentInn") {
            /*let id = $(this).attr('data-disabledInputKontragent-id');
            console.log("dbclick",id);*/
            $(this).removeAttr('disabled').removeAttr('readonly');
            $(this).focus();
            return false;
        }
    });

    spisokKontr.on('click', 'a', function () {

        if ($(this).attr('id') == "kontragentSpisokViev") {  //список контрагент
            vievKontragent();
        }

        if ($(this).attr('id') == "buttonaddKontragent") {
            $(this).prop("disabled",true);

            //let param = "name=" + $("#addNameKontragent").val() + "&inn=" + $("#addINNKontragent").val();

                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/kontragent/add',
                        method: 'post',
                        data: $("#addKontrForm").serialize(),
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            vievKontragent();
                            initialToats("Добавление прошло успешно",data,"success").show();
                        },
                        error: function (textStatus) {
                            initialToats("Ошибка!!!",textStatus,"err").show();
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
            $(this).prop("disabled",false);
            return false;
        }


        if ($(this).attr('name') == "delSpisokKontragent") {
            let param = "id=" + $(this).attr('id');
                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/kontragent/delette',
                        method: 'post',
                        data: param,
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            vievKontragent();
                            initialToats("Удаление прошло успешно",data,"success").show();
                        },
                        error: function (textStatus) {
                            initialToats("Ошибка!!!",textStatus,"err").show();
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
            return false;
        }
    });
});