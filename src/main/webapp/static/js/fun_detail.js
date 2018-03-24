var layer;
layui.use('layer', function () {
    layer = layui.layer;
});
function doAgree() {
    var state = $("#state").val();
    if(state=="已同意"){
        layer.msg('该条申请已经被同意');
        return;
    }
    var id = $("#id").val();
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.confirm('确定同意申请？', function (index) {
            $.ajax({
                type: 'POST',
                url: contextPath+'/admin/applications_agree',
                dataType: 'json',
                data: {id: id},
                success: function (data) {
                    var messages = data.success;
                    if (messages > 0) {
                        layer.msg('已同意!');
                        setTimeout(function () {
                            window.location.reload();
                        }, 500);
                    }
                    else {
                        alert('网络异常');
                    }
                },
                error: function (data) {
                    alert('网络异常');
                },
            });
            layer.close(index);
        });
    });

    return false;
}

function agreeChek() {
    var state = $("#state").val();
    if(state=="已同意"){
        layer.msg('该条申请已经被同意');
        return;
    }
    var id = $("#id").val();
    $.ajax({
        type: 'POST',
        url: contextPath+'/admin/applications_agreeCheck',
        dataType: 'json',
        data: {id: id},
        success: function (data) {
            var messages = data.count;
            if (messages > 0) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.confirm('此时间段已经有'+messages+"人的申请被通过，确认再次通过该申请？", function (index) {
                        doAgree();
                        layer.close(index);
                    });

                });
            }
            else {
                doAgree();
            }
        },
        error: function (data) {
            alert('网络异常');
        },
    });
}

function doRefuse() {
    var state = $("#state").val();
    if(state=="已拒绝"){
        layer.msg('该条申请已经被拒绝');
        return;
    }
    var id = $("#id").val();
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.confirm('确定拒绝申请？', function (index) {
            $.ajax({
                type: 'POST',
                url: contextPath+'/admin/applications_refuse',
                dataType: 'json',
                data: {id: id},
                success: function (data) {
                    var messages = data.success;
                    if (messages > 0) {
                        layer.msg('已拒绝!');
                        setTimeout(function () {
                            window.location.reload();
                        }, 500);
                    }
                    else {
                        alert('网络异常');
                    }
                },
                error: function (data) {
                    alert('网络异常');
                },
            });
            layer.close(index);
        });
    });
    return false;
}
