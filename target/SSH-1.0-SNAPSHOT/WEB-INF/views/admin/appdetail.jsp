<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zqx
  Date: 2018/3/6
  Time: 21:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/viewer/viewer.min.css">

</head>
<style>
    .layui-upload-img {
        width: 92px;
        height: 92px;
        margin: 0 10px 10px 0;
    }
</style>
<body>
<fieldset class="layui-elem-field">
    <legend>申请社团名称</legend>
    <div class="layui-field-box">
        ${app.clubName}
    </div>
</fieldset>
<fieldset class="layui-elem-field">
    <legend>联系社团负责人</legend>
    <div class="layui-field-box">
        ${app.clubOwner}
    </div>
</fieldset>
<fieldset class="layui-elem-field">
    <legend>联系邮箱</legend>
    <div class="layui-field-box">
        ${app.clubEmail}
    </div>
</fieldset>
<fieldset class="layui-elem-field">
    <legend>联系电话</legend>
    <div class="layui-field-box">
        ${app.clubPhone}
    </div>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>社团创建时间</legend>
    <div class="layui-field-box">
        ${app.clubCreate}
    </div>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>社团简介</legend>
    <div class="layui-field-box">
        ${app.intro}
    </div>
</fieldset>

<fieldset class="layui-elem-field">
    <legend> 证明材料：</legend>
        <div class="layui-upload-list" id="demo2">
            <ul id="viewer">
                <c:forEach items="${uploads}" var="upload">
                    <li style="float: left"><img src="/${upload.url}" data-original="/${upload.url}" alt="${upload.token}" class="layui-upload-img"></li>
                </c:forEach>
            </ul>
        </div>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>处理状态</legend>
    <div class="layui-field-box">
        <span class="layui-badge layui-bg-orange">${app.type}</span>
    </div>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>处理时间</legend>
    <div class="layui-field-box">
        ${app.deal}
    </div>
</fieldset>

<form class="layui-form" method="post" action="#">
    <input type="hidden" value="${app.id}" id="id">
    <input type="hidden" value="${app.type}" id="state">
    <button type="button" class="layui-btn" id="agree" onclick="doAgree()">同意申请</button>
    <button type="button" class="layui-btn layui-btn-danger" onclick="doRefuse()" lay-submit lay-filter="refuse">拒绝申请</button>
</form>

<script src="${pageContext.request.contextPath}/static/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/fun_detail.js"></script>
<script src="${pageContext.request.contextPath}/static/viewer/viewer.min.js"></script>
<script>
    $('#viewer').viewer();
    <%--JS gloable varilible--%>
    var contextPath = "${pageContext.request.contextPath}";
</script>
</body>
</html>

