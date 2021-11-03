function vievKontragent (){
    $("#mainContainer").load("/contract/main/kontragent/kontragentViev", "", function () {
    });
}

$(document).ready(function () {
    $("body").on('click', 'a', function () {
        if ($(this).attr('id') == "kontragentSpisokViev") {  //список контрагент
            vievKontragent();
            return false;
        }
        if ($(this).attr('id') == "buttonaddKontragent") {
            let param = "name=" + $("#addNameKontragent").val() +
                "&inn=" + $("#addINNKontragent").val();

                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/main/kontragent/add',
                        method: 'post',
                        data: param,
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function (data) {
                            vievKontragent();
                        },
                        error: function (textStatus) {
                            alert("Ошибка !!! " + textStatus);
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
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
                        },
                        error: function (textStatus) {
                            alert("Ошибка !!! " + textStatus);
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
            return false;
        }
    });
});