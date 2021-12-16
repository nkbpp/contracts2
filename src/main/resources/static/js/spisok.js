function vievVidObesp (){
    $("#mainContainer").load("/contract/main/vidObespSpisokViev", "", function () {
    });
}

$(document).ready(function () {

    let spisokBody = $("body");

    spisokBody.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if(e.keyCode === 13) {
            if ($(this).attr('name') === "disabledInputVidObesps") {
                console.log("enter")
                $(this).blur();
                return false;
            }
        }
    });


    spisokBody.on('blur', 'input', function () { //изменение на событие потери фокуса
        if ($(this).attr('name') === "disabledInputVidObesps") {  //список вид обесп
            let tek = $(this);
            let id = tek.attr('data-disabledInputVidObesps-id');
            let param = "id=" + id + "&name=" + tek.val();
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/main/vidobesp/update',
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
                error: function (jqXHR,textStatus) {
                    initialToats("Ошибка при изменении!!!", jqXHR.responseText,"err").show();
                }
            });
        }
    });

    spisokBody.on('dblclick', 'input', function () {
        if ($(this).attr('name') === "disabledInputVidObesps") {
            /*let id = $(this).attr('data-disabledInputVidObesps-id');
            console.log("dbclick",id);*/
            $(this).removeAttr('disabled').removeAttr('readonly');
            $(this).focus();
            return false;
        }
    });

    spisokBody.on('click', 'a', function () {
        if ($(this).attr('id') === "vidObespSpisokViev") {  //список вид обесп
            vievVidObesp();
            //return false;
        }
        if ($(this).attr('id') === "buttonaddspisok") {
            $(this).prop("disabled",true);
            //let param = "name=" + $("#addznachspisok").val();
            switch ($(this).attr('value')) {
                case "vidObesp": {
                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/vidobesp/add',
                        method: 'post',
                        data: $("#addVisObespForm").serialize(),
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            vievVidObesp();
                            initialToats("Добавление прошло успешно",data,"success").show();
                        },
                        error: function (jqXHR,textStatus) {
                            initialToats("Ошибка при добавлении!!!", jqXHR.responseText,"err").show();
                        }
                    });
                }
                    break;
            }
            $(this).prop("disabled",false);
            return false;
        }
        if ($(this).attr('name') === "delSpisok") {
            let param = "id=" + $(this).attr('id');
            switch ($(this).attr('value')) {
                case "vidObesp": {
                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/vidobesp/delette',
                        method: 'post',
                        data: param,
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            vievVidObesp();
                            initialToats("Удаление прошло успешно",data,"success").show();
                        },
                        error: function (jqXHR,textStatus) {
                            initialToats("Ошибка при удалении!!!", jqXHR.responseText,"err").show();
                        }
                    });
                }
                    break;
            }
            return false;
        }
    });
});