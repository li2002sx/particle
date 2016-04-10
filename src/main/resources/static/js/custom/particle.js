$(function(){

    $('#ugc_form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            ugc: {
                validators: {
                    notEmpty: {
                        message: '车源信息不能为空'
                    }
                }
            }
        }
    });

    $('#btn_reset_ugc').click(function() {
        $('#ugc_form').data('bootstrapValidator').resetForm(true);
    });

    $('#term_form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            terms: {
                validators: {
                    notEmpty: {
                        message: '词汇不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
          e.preventDefault();
          var data = {
              terms : $.trim($('textarea[name="terms"]').val())
          }
          $.post("addExtDics",data,function(result){
             if(result.success){
                  bootbox.alert('操作成功');
             }else{
                  bootbox.alert(result.error);
             }
          });
    });

    $('#btn_add_extdic').click(function(){
        $('#term_form').bootstrapValidator('validate');
    });

    $('#btn_reset_extdic').click(function() {
        $('#term_form').data('bootstrapValidator').resetForm(true);
    });

    $('#brand').autocomplete({
        source:["c++", "java", "php", "coldfusion", "javascript", "asp", "ruby"],
        cb: {
            select: function () {
//                $("#console").append("<div>Select: " + $(this).val() + "</div>")
            },
            force: function () {
//                $("#console").append("<div>Force: " + $(this).val() + "</div>")
            }
        }
    }).focus();
});