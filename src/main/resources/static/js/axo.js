$(document).ready(function () {
    let itbody = $("body");
    itbody.on('click', 'a', function () {

        if ($(this).attr('id') === "updateAxoContract") { //перейти на вкладку изменения ItContract
            let param = $(this).attr('name');

            $("#mainContainer").load("/contract/axo/updateViev/" + param, "", function () {

                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/axo/getContract/' + param,
                    method: 'post',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        $("#addContractAxo").attr("data-id-contract",data.contract.id)
                        $('textarea[name=nomGK]').val(data.contract.nomGK);
                        $('textarea[name=kontragent]').val(data.contract.kontragent);
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

                        $('input[name=sumNaturalIndicators]').val(data.contract.sumNaturalIndicators);
                        $('#range').val(data.contract.naturalIndicatorsSize);
                        //$('#spanVal').text(data.contract.naturalIndicatorsSize);
                        inputAddClassDnone(data.contract.naturalIndicatorsSize);
                        let vvv = data.contract.naturalIndicators.split(";");
                        let inputs = $('#nt').find('input');
                        for (let i = 0; i < vvv.length; i++) {
                            inputs.eq(i).val(vvv[i]);
                        }

                        $('textarea[name=doc]').val(data.contract.doc);

                        for (let doc of data.documents) {
                            let dv = $("#documentsAxoViev");
                            dv.html(
                                dv.html() +
                                "<div class='mb-1' data-id-doc='" + doc.id + "'>"+doc.nameFile + " " +
                                "<button class='btn btn-secondary btn-sm' " +
                                "data-id-doc='" + doc.id + "' data-name-doc='delAxoDoc'>X</button>" +
                                "</div>"
                            );
                        }

                        $('#addContractAxo').text("Изменить");

                    },
                    error: function(textStatus){
                        initialToats("Ошибка!!!",textStatus,"err").show();
                        console.log('ОШИБКИ AJAX запроса: ' + textStatus);
                    }
                });
            });
            return false;
        }

        if ($(this).attr('id') === "menuaxoaddcontract") { //Кнопка Добавить Axo контракт TODO
            $("#mainContainer").load("/contract/axo/add", "", function () {
            });
            return false;
        }

        if ($(this).attr('id') === "menuaxocontract") { //Кнопка АХО контракты
            let mainContainer = $("#mainContainer");
            mainContainer.html(getSpinner());
            mainContainer.load("/contract/axo/vievTable", "", function () {
                let tableContainer = $("#tableAxoContainer")
                tableContainer.html(getSpinner());
                tableContainer.load("/contract/axo/getTable", "", function () {
                    /*Для скролов*/
                    $('.div1').width($('table').width());
                    $('.div2').width($('table').width());
                });
            });
            return false;
        }

        if ($(this).attr('id') === "deleteAxoContract") { //Удалить контракт
            if (confirm("Вы точно хотите удалить заявление c порядковым номером = " +
                $(this).parents("#" + $(this).attr('name')).children().eq(0).text())){

                let param = "id="+$(this).attr('name');
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.ajax({
                    url: '/contract/axo/deleteContract',
                    method: 'post',
                    data: param,
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function(data){
                        param = "param=" + activeList("#paginationAxoContract");//чтобы при удалении осталась текущая страница
                        $("#tableAxoContainer").load("/contract/axo/getTable", param, function () {
                        });
                        initialToats("Удаление прошло успешно",data,"success").show();
                    },
                    error: function(textStatus){
                        initialToats("Ошибка!!!",textStatus,"err").show();
                    }
                });
            }
            return false;
        }


        //переключатели страниц pagination
        if ($(this).parents("#paginationAxoContract").attr("id") === "paginationAxoContract") {
            let list = clickPagination($(this),"#paginationAxoContract");
            let param = "param=" + list;

            let bool = $("#flexCheckChecked").prop("checked");
            if(bool){
                $("#tableAxoContainer").load("/contract/axo/getTable2", param, function (data) {
                });

            }else{
                $("#tableAxoContainer").load("/contract/axo/getTable", param, function (data,f) {
                });
            }

        }


    })

    //checkBox в таблице
    itbody.on('click', 'input', function () {
        if ($(this).attr('id') === "flexCheckChecked") {
            let bool = $(this).prop("checked");
            let list = clickPagination($("#paginationAxoContract li.active"),"#paginationAxoContract");
            let param = "param=" + list;

            if(bool){
                $("#tableAxoContainer").load("/contract/axo/getTable2", param, function (data) {
                });
            }else{
                $("#tableAxoContainer").load("/contract/axo/getTable", param, function (data,f) {
                });
            }
            activePagination($("#paginationAxoContract a"), list)
        }
    });

    itbody.on('click', 'button', function () {

        if ($(this).attr('id') === "addContractAxo") {
            console.log("sdfsdf")
            // проверка заполнения основных полей
            if(
                !$("#nomGK").val().trim()
            ){
                alert("Не все обязательные поля (отмеченные *) заполнены!")
            } else {
                $(this).prop("disabled",true);//делаем кнопку не активной
                $(this).prepend(getSpinnerButton());// крутилкa

                let param = new FormData($('#formAxoContract')[0]);
                param.append('id', encodeURIComponent($('#addContractAxo').attr("data-id-contract")));

                let ntinput = $('#nt').find('.col-3:not(.d-none) input');
                let strntinput = "";
                for (let i = 0; i < ntinput.length; i++) {
                    strntinput += (ntinput.eq(i).val() + (((i+1)!=ntinput.length)?';':''));
                }
                param.append('naturalIndicators', strntinput);

                // Отправляем запрос
                let token = $('#_csrf').attr('content');
                let header = $('#_csrf_header').attr('content');
                $.post({
                    url: "/contract/axo/upload",
                    data: param,
                    cache: false,
                    processData: false, // Не обрабатываем файлы (Don't process the files)
                    contentType: false, // Так jQuery скажет серверу что это строковой запрос
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    success: function (data) {
                        //после добавления показать таблицу
                        $("#mainContainer").load("/contract/axo/vievTable", "", function () {
                            $("#tableAxoContainer").load("/contract/axo/getTable", "", function () {                    /*Для скролов*/
                                $('.div1').width($('table').width());
                                $('.div2').width($('table').width());
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


        if ($(this).attr('data-name-doc') === "delAxoDoc") {
            let id = $(this).attr('data-id-doc');
            let param = "id="+id;
            let token = $('#_csrf').attr('content');
            let header = $('#_csrf_header').attr('content');
            $.ajax({
                url: '/contract/axo/delAxoDoc',
                method: 'post',
                data: param,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function(data){
                    $("div[data-id-doc="+id+"]").remove();
                    $("a[data-id-doc="+id+"]").remove();
                    initialToats("Удаление прошло успешно",data,"success").show();
                },
                error: function(textStatus){
                    initialToats("Ошибка при удалении документа!!!",textStatus,"err").show();
                    console.log('ОШИБКИ AJAX запроса при удалении документа: ' + textStatus);
                }
            });
            return false;
        }

        if ($(this).attr('name') === "findContractAxo") { //поиск
            let param = "poleFindByNomGK=" + $("[data-name=poleFindByNomGK]").val() +
                "&poleFindByKontragent=" + $("[data-name=poleFindByKontragent]").val();
            $("#tableAxoContainer").load("/contract/axo/findTable", param, function (data) {
                clearPagination1();
            });
            return false;
        }

    })




})






