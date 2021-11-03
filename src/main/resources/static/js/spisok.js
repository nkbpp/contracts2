function vievVidObesp (){
    $("#mainContainer").load("/contract/main/vidObespSpisokViev", "", function () {
    });
}

$(document).ready(function () {
    $("body").on('click', 'a', function () {
        if ($(this).attr('id') == "vidObespSpisokViev") {  //список вид обесп
            vievVidObesp();
            return false;
        }
        if ($(this).attr('id') == "buttonaddspisok") {
            let param = "name=" + $("#addznachspisok").val();
            switch ($(this).attr('value')) {
                case "vidObesp": {
                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/vidobesp/add',
                        method: 'post',
                        data: param,
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            vievVidObesp();
                        },
                        error: function (textStatus) {
                            alert("Ошибка !!! " + textStatus);
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
                }
                    break;
            }
            return false;
        }
        if ($(this).attr('name') == "delSpisok") {
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
                        },
                        error: function (textStatus) {
                            alert("Ошибка !!! " + textStatus);
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
                }
                    break;
            }
            return false;
        }
    });
});