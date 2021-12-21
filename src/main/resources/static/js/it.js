$(document).ready(function () {

    $("body").on('click', 'a', function () {

        if ($(this).attr('id') === "menuitcontract") { //Кнопка ИТ контракты
            console.log("click");
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/it/vievTable", "", function () {
                console.log("inside");
                let tableContainer = $("#tableItContainer")
                tableContainer.html(getSpinner());
                tableContainer.load("/contract/it/getTable", "", function () {
                });
            });
            return false;
        }

        if ($(this).attr('id') === "menuitaddcontract") { //Кнопка Добавить ИТ контракт
            $("#mainContainer").load("/contract/it/add", "", function () {
            });
            return false;
        }

        if ($(this).attr('id') === "deleteItContract") { //Удалить контракт
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("#" + $(this).attr('name')).children().eq(0).text())){

                let param = "id="+$(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/it/deleteContract',
                    method: 'post',
                    data: param,
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        param = "param=" + activeList("#paginationItContract");//чтобы при удалении осталась текущая страница
                        $("#tableItContainer").load("/contract/it/getTable", param, function () {
                        });
                        initialToats("Удаление прошло успешно",data,"success").show();
                    },
                    error: function(textStatus){
                        initialToats("Ошибка!!!",textStatus,"err").show();
                        //console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                    }
                });
            }
            return false;
        }

        if ($(this).attr('id') === "updateItContract") {
            console.log("update");
            let param = $(this).attr('name');

            $("#mainContainer").load("/contract/it/updateViev/" + param, "", function () {

                    let token = $('#_csrf').attr('content');
                    let header = $('#_csrf_header').attr('content');
                    $.ajax({
                        url: '/contract/it/getContract/' + param,
                        method: 'post',
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        success: function(data){
                            $("#addContractIt").attr("data-id-contract",data.contract.id)
                            $('input[name=nomGK]').val(data.contract.nomGK);
                            $('input[name=dateGK]').val(data.contract.dateGK);
                            $('input[name=sum]').val(data.contract.sum.replace(',','.'));
                            $('input[name=January]').val(data.contract.January);
                            $('input[name=February]').val(data.contract.February);
                            $('input[name=March]').val(data.contract.March);
                            $('input[name=April]').val(data.contract.April);
                            $('input[name=May]').val(data.contract.May);
                            $('input[name=June]').val(data.contract.June);
                            $('input[name=July]').val(data.contract.July);
                            $('input[name=August]').val(data.contract.August);
                            $('input[name=September]').val(data.contract.September);
                            $('input[name=October]').val(data.contract.October);
                            $('input[name=November]').val(data.contract.November);
                            $('input[name=December]').val(data.contract.December);
                            $('textarea[name=doc]').val(data.contract.doc);

                            $('#addContractIt').text("Изменить");
                        },
                        error: function(textStatus){
                            initialToats("Ошибка!!!",textStatus,"err").show();
                            console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                        }
                    });
            });
            return false;
        }

        //переключатели страниц pagination
        if ($(this).parents("#paginationItContract").attr("id") === "paginationItContract") {
            let list = clickPagination($(this),"#paginationItContract");
            let param = "param=" + list;
            $("#tableItContainer").load("/contract/it/getTable", param, function () {
            });
        }
    })

    $("body").on('click', 'button', function () {
        if ($(this).attr('id') === "addContractIt") {
            // проверка заполнения основных полей
            if(
                !$("#nomGK").val().trim()
            ){
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled",true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                let data = $('#formItContract').serialize();

                //console.log(data)

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.post({
                    url: "/contract/it/upload",
                    data: data + "&" + $('#addContractIt').attr("data-id-contract"),
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        //после добавления показать таблицу
                        $("#mainContainer").load("/contract/it/vievTable", "", function () {
                            $("#tableItContainer").load("/contract/it/getTable", "", function () {
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
        }
    })
})