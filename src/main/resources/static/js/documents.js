$(document).ready(function () {

    $("body").on('click', 'a', function () {
        if ($(this).attr('id') === "addContracts") {

            //TODO проверка заполнения основных полей
            if(
                !$("#receipt_date").val().trim() ||
                !$("#plat_post").val().trim() ||
                $("#vidObesp").val()==0 ||
                $("#kontragent").val()==0 ||
                !$("#sum").val().trim()
                /*!$("#sum").val().trim() &&
                !$("#nomGK").val().trim() &&
                !$("#dateGK").val().trim() &&
                !$("#date_ispolnenija_GK").val().trim() &&
                !$("#col_days").val().trim()*/
            ){
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {

                // Get form
                let form = $('#formContracts')[0];
                // Create an FormData object
                let data = new FormData(form);
                data.append('notifications', $('#notifications div').toArray().map(d => $(d).attr('id')));
                data.append('ispolneno', $('#checkboxAddUpdate').prop("checked"));
                data.append('id', $('#addContracts').attr("data-id-contract"));

                for (let [name, value] of data) {
                    console.log(`${name} = ${value}`); // key1=value1, потом key2=value2
                }

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: "/contract/main/upload",
                    type: 'POST',
                    data: data,
                    cache: false,
                    dataType: 'json',
                    //dataType: 'text',
                    processData: false, // Не обрабатываем файлы (Don't process the files)
                    contentType: false, // Так jQuery скажет серверу что это строковой запрос
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (result) {
                        alert("Данные Добавлены!");
                        //после добавления показать таблицу
                        $("#mainContainer").load("/contract/main/vievTable", "", function () {
                            $("#tableContainer").load("/contract/main/getTable", "", function () {
                                $('#modalDopContainer').modal();
                            });
                        });
                        console.log("result = " + result);
                    },
                    error: function (jqXHR, textStatus) {
                        alert("Ошибка в добавлении " + textStatus);
                        console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                    }
                });
            }
            return false;
        }
    });
});