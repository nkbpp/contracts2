$(document).ready(function () {

    $("body").on('click', 'button', function () {
        /*        if ($(this).attr('id') === "addContracts") {
                    // проверка заполнения основных полей
                    if(
                        !$("#receipt_date").val().trim() ||
                        !$("#plat_post").val().trim() ||
                        $("#vidObesp").val()===0 ||
                        $("#kontragent").val()===0 ||
                        !$("#sum").val().trim()
                    ){
                        alert("Не все обязательные поля (отмеченные *) заполнены!")
                    } else {
                        $(this).prop("disabled",true);
                        $(this).prepend(getSpinnerButton());// крутилкa

                        // Get form
                        let form = $('#formContracts')[0];
                        // Create an FormData object
                        let data = new FormData(form);
                        data.append('notifications', $('#notifications div[id]').toArray().map(d => $(d).attr('id')));
                        data.append('ispolneno', $('#checkboxAddUpdate').prop("checked"));
                        data.append('id', $('#addContracts').attr("data-id-contract"));



                        // Отправляем запрос
                        let token = $('#_csrf').attr('content');
                        let header = $('#_csrf_header').attr('content');
                        $.ajax({
                            url: "/contract/main/upload",
                            type: 'POST',
                            data: data,
                            cache: false,
                            //dataType: 'json',
                            //dataType: 'text',
                            processData: false, // Не обрабатываем файлы (Don't process the files)
                            contentType: false, // Так jQuery скажет серверу что это строковой запрос
                            beforeSend: function (xhr) {
                                xhr.setRequestHeader(header, token);
                            },
                            success: function (data) {
                                //после добавления показать таблицу
                                $("#mainContainer").load("/contract/main/vievTable", "", function () {
                                    $("#tableContainer").load("/contract/main/getTable", "", function () {
                                    });
                                });
                                initialToats("Добавление прошло успешно",data,"success").show();
                            },
                            error: function (jqXHR, textStatus) {
                                initialToats("Ошибка при добавлении!!!",jqXHR.responseText,"err").show();
                            }
                        });
                    }
                    return false;
                }*/
    });
});