$(function(){

    const appendTask = function(data){
        var townCode = '<a href="#" class="town-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#town-list')
            .append('<div>' + townCode + '</div>');
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
                var code = '<span>Достопримечательности:' + response.sights + '</span>';
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
    $('#save-sight').click(function()
    {
        var data = $('#sight-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/towns/id',          //  /books/
            data: data,
            success: function(response)
            {
                $('#sight-form').css('display', 'none');
                var sight = {};
                sight.id = response;
                var dataArray = $('#sight-form form').serializeArray();
                for(i in dataArray) {
                    sight[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(sight);
            }
        });
        return false;
    });

});