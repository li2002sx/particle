$(function(){

    $('#form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            input: {
                validators: {
                    notEmpty: {
                        message: '车源信息不能为空'
                    }
                }
            }
        }
    });

    $('#btn_reset').click(function() {
        $('#form').data('bootstrapValidator').resetForm(true);
    });


    $('#btn_save').click(function(){
        var $btn = $(this).button('loading');
        var carArr = [];
        $('.table tr:gt(0)').each(function(){
            var car = gettr($(this));
            carArr.push(car);
        });
        bootbox.alert(JSON.stringify(carArr),function(){
            $btn.button('reset')
        });
    });

    function gettr(tr){
        var $tds = $(tr).find("td");
        var car = {
            carType:$($tds[1]).find('select').val(),
            spec:$($tds[2]).find('select').val(),
            outColor:$($tds[3]).find('input').val(),
            inColor:$($tds[4]).find('input').val(),
            mobile:$($tds[5]).find('input').val(),
            price:$($tds[6]).find('input').val(),
            status:$($tds[7]).find('select').val(),
            carFrame:$($tds[8]).find('input').val(),
            city:0
        };
        return car;
    }
});