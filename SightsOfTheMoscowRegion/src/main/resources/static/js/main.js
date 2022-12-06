$(function(){

    const appendTask = function(data){
        var townCode = '<a href="#" class="town-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#town-list')
            .append('<div>' + taskCode + '</div>');
    };


    //Show adding book form
    $('#show-add-town-form').click(function(){
        $('#town-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#town-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting book
    $(document).on('click', '.town-link', function(){
        var link = $(this);
        var townId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/towns/' + townId,  //  /books/
            success: function(response)
            {
                var code = '<span>Описание:' + response.description + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Дело не найдено!');
                }
            }
        });
        return false;
    });

    //Adding book
    $('#save-town').click(function()
    {
        var data = $('#task-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/towns/',          //  /books/
            data: data,
            success: function(response)
            {
                $('#town-form').css('display', 'none');
                var town = {};
                town.id = response;
                var dataArray = $('#town-form form').serializeArray();
                for(i in dataArray) {
                    town[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(town);
            }
        });
        return false;
    });

});