<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zqx
  Date: 2018/3/6
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Iteam社团账号申请后台管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css">
</head>
<body class="layui-layout-body">

<div class="layui-layout layui-layout-admin">
    <div class="layui-header" id="layerDemo">
        <div class="layui-logo">Iteam社团账号申请后台管理</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <p style="visibility: hidden" id="userId">${admin.id}</p>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    ${admin.name}
                </a>
                <dl class="layui-nav-child">
                    <button data-method="setTop" class="layui-btn">修改密码</button>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="${pageContext.request.contextPath}/logout">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item"><a id="aApplication">申请总览</a></li>
                <%--<li class="layui-nav-item"><a href="ContactList.aspx">管理员管理</a></li>--%>
            </ul>
            <c:if test="${admin.adminClass==1}">
                <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                    <li class="layui-nav-item"><a id="aAdmin">添加管理员</a></li>
                </ul>
            </c:if>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;" id="contentFrame" >
            <iframe src="./admin/applications" height="500px" frameborder="0" width="100%"></iframe>
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        ©  Copyright © 2018 Itstudio All rights reserved.
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;

    });

    $("#aApplication").click(function()  {
        $("#contentFrame iframe").attr("src", "./admin/applications");
    });

    $("#aAdmin").click(function()  {
        $("#contentFrame iframe").attr("src", "./admin/add");
    });

</script>
<script>
    <%--JS gloable varilible--%>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script>
    layui.use('layer', function(){ //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

        //触发事件
        var active = {
            setTop: function(){
                var that = this;
                //多窗口模式，层叠置顶
                layer.open({
                    type: 1//此处以iframe举例
                    ,title: '修改密码'
                    ,area: ['500px', '200px']
                    ,shade: 0
                    ,maxmin: true
                    ,content: '<form class="layui-form" style="margin-top: 50px" action="">\n' +
                    '  <div class="layui-form-item">\n' +
                    '    <label class="layui-form-label">输入新密码</label>\n' +
                    '    <div class="layui-input-block">\n' +
                    '      <input style="width: 300px" id="pwd" type="text" name="title" lay-verify="title" autocomplete="off" placeholder="请输入新密码" class="layui-input">\n' +
                    '    </div>\n' +
                    '  </div>\n'+
                    '</form>'
                    ,btn: ['确定修改','关闭']
                    ,yes: function(){
                        changePwd();
                    }
                    ,btn2: function(){
                        layer.closeAll();
                    }
                    ,zIndex: layer.zIndex //重点1
                    ,success: function(layero){
                        layer.setTop(layero); //重点2
                    }
                });
            }
        };

        $('#layerDemo .layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });

    });

    function changePwd() {
        var id=$("#userId").text();
        var pwd=$("#pwd").val();
        layer.closeAll();
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.confirm('确定修改？', function (index) {
                $.ajax({
                    type: 'POST',
                    url: contextPath+'/admin/changePwd',
                    dataType: 'json',
                    data: {id: id, pwd: pwd},
                    success: function (data) {
                        var messages = data.success;
                        if (messages > 0) {
                            layer.msg('修改成功');
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
    }
</script>

</body>
</html>


