<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zqx
  Date: 2018/3/9
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>


<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>添加管理员</legend>
</fieldset>

<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">管理员账号</label>
        <div class="layui-input-block">
            <input type="text" name="account" lay-verify="required" autocomplete="off" placeholder="请输入账号"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">管理员姓名</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="请输入姓名"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">所属学院</label>
        <div class="layui-input-block">
            <select name="deptId" lay-filter="aihao">
                <c:forEach items="${depts}" var="dept" varStatus="status">
                    <c:if test="${status.index==0}">
                        <option value="${dept.id}" selected="">${dept.name}</option>
                    </c:if>
                    <c:if test="${status.index!=0}">
                        <option value="${dept.id}">${dept.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">管理员邮箱</label>
        <div class="layui-input-block">
            <input type="text" name="email" lay-verify="email" autocomplete="off" placeholder="请输入邮箱"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script>
    <%--JS gloable varilible--%>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use(['form', 'layer'], function () {
        var form = layui.form
            , layer = layui.layer

        //监听提交
        form.on('submit(demo1)', function (data) {
            layer.confirm("确定要添加吗？", function (index) {
                doAdd(data.field);
            });
            //layer.closeAll();
            return false;
        });

    });

    function doAdd(data) {
        $.ajax({
            type: 'POST',
            url: contextPath+'/admin/doAdd',
            dataType: 'json',
            data: {account: data.account, email: data.email, name: data.name,deptId:data.deptId},
            success: function (data) {
                var messages = data.success;
                console.log(messages);
                if (messages > 0) {
                    layer.msg('添加成功，默认密码为管理员账号');
                    setTimeout(function () {
                        window.location.reload();
                    }, 2000);
                }else if(messages==-1){
                    layer.msg('管理员账号与系统已有账号重复');
                }
                else {
                    alert('网络异常');
                }
            },
            error: function (data) {
                alert('网络异常');
            },
        });
        layer.closeAll();
    }
</script>

</body>
</html>
