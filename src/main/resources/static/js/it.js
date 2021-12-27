$(document).ready(function () {
    let itbody = $("body");
    itbody.on('click', 'a', function () {

        if ($(this).attr('id') === "menuitcontract") { //Кнопка ИТ контракты
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/it/vievTable", "", function () {
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
            //console.log("update");
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

    itbody.on('click', 'button', function () {
        if ($(this).attr('id') === "addContractIt") {
            // проверка заполнения основных полей
            if(
                !$("#nomGK").val().trim()
            ){
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled",true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                let param = $('#formItContract').serialize();
                param += "&id=" + encodeURIComponent($('#addContractIt').attr("data-id-contract"))

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.post({
                    url: "/contract/it/upload",
                    data: param,
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

    itbody.on('focus', 'input', function () {
        if($("#formItContract").find($("#"+$(this).attr('id')+"[type=number]")).length>0){
            this.select();
        }
    })

    itbody.on('dblclick', 'td', function () { //Двойной клик в табле

        let tek = $(this);

        let td = tek.closest('td');

        let span = td.find('span').first();
        let input = td.find('input').first();
        let textarea = td.find('textarea').first();

        let text = span.text();
        if(input.attr('type')==="date"){
            text = text.split(".").reverse().join("-");
        }

        input.val(text);
        textarea.val(text);
        span.addClass('d-none');
        input.removeClass('d-none');
        textarea.removeClass('d-none');

        input.select();
        textarea.select();

    });

    itbody.on('keypress', 'input', function (e) { //изменение на событие клик по enter
        if(e.keyCode === 13) {
            if($(this).closest($('#tableItContainer')).length>0){
                $(this).blur();
            }
        }
    });

    itbody.on('keypress', 'textarea', function (e) { //изменение на событие клик по enter
        if(e.keyCode === 13) {
            if($(this).closest($('#tableItContainer')).length>0){
                $(this).blur();
            }
        }
    });

    itbody.on('blur', 'input', function () { //изменение на событие потери фокуса input
        if($(this).closest($('#tableItContainer')).length>0){

            let tek = $(this);

            let tr = tek.closest('tr');
            let td = tek.closest('td');

            let span = td.find('span').first();
            let input = td.find('input').first();


            let text = input.val();
            if(input.attr('type')==="date"){
                text = text.split("-").reverse().join(".");
            } else if(input.attr('type')==="number"){
                let regExp = '[.]+';
                if(text.match(regExp) == null) {
                    text += '.00';
                }
            }
            span.text(text);
            input.addClass('d-none');
            span.removeClass('d-none');

            // Отправляем запрос

            let param = getParamItContract(tr);
            updateItContract(param);

            let val = 0; //подсчет остатка
            let tdNumbers = tr.find('td[name]:has(input[type=number])');
            let maxIndex = tdNumbers.length;
            tdNumbers
                .each(function (index, el){
                    let t = $(el).text().trim();
                    if(+index===+0){
                        val+=t;
                    } else {
                        val-=t;
                    }
                    if (+index===+(maxIndex-1)){ //запись в поле остаток
                        let regExp = '[.]+';
                        if((val+'').match(regExp) == null) {
                            val += '.00';
                        }
                        ($(el).closest('td')).next().text(val);
                    }
                });
            return false;
        }

    });

    itbody.on('blur', 'textarea', function () { //изменение на событие потери фокуса textarea
        if($(this).closest($('#tableItContainer')).length>0){

            let tek = $(this);

            let tr = tek.closest('tr');
            let td = tek.closest('td');

            let span = td.find('span').first();
            let textarea = td.find('textarea').first();

            let text = textarea.val();

            span.text(text);
            textarea.addClass('d-none');
            span.removeClass('d-none');

            // Отправляем запрос
            let param = getParamItContract(tr);

            updateItContract(param);

            return false;
        }
    });

    function updateItContract(param){
        // Отправляем запрос
        let token = $('#_csrf').attr('content');
        let header = $('#_csrf_header').attr('content');
        $.post({
            url: "/contract/it/upload",
            data: param,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                initialToats("Изменение прошло успешно",data,"success").show();
            },
            error: function (jqXHR, textStatus) {
                initialToats("Ошибка при изменении!!!",jqXHR.responseText,"err").show();
            }
        });
    }

    function getParamItContract(tr){
        let id = tr.attr('name');
        let param = "id="+id;
        let tds = tr.find('td[name]');
        tds.each(function (index, el){
            let e = $(el);
            let val = encodeURIComponent(e.text().trim());

            param+=("&"+e.attr('name')+"="+val);
        });
        return param;
    }

})