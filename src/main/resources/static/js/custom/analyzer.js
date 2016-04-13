$(function(){

    $('#analyzer_form').bootstrapValidator({
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

    $('#btn_reset_analyzer').click(function() {
        $('#analyzer_form').data('bootstrapValidator').resetForm(true);
    });

});