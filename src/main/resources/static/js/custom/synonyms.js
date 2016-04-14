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
                        message: '同义词汇不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
          e.preventDefault();
          var data = {
              input : $.trim($('textarea[name="input"]').val())
          }
          $.post("addSynonyms",data,function(result){
             if(result.success){
                  bootbox.alert('操作成功');
             }else{
                  bootbox.alert(result.error);
             }
          });
    });

    $('#btn_add').click(function(){
        $('#form').bootstrapValidator('validate');
    });
});