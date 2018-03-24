<%--
  Created by IntelliJ IDEA.
  User: zqx
  Date: 2018/3/6
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>

    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>
<body>

<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
<script src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->

<form class="layui-form" action="${pageContext.request.contextPath}/admin/excel">

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">选择类型</label>
            <div class="layui-input-inline">
                <select id="type" lay-filter="type" name="type">
                    <option value="全部" selected="selected">全部</option>
                    <option value="未审核">未审核</option>
                    <option value="已拒绝">已拒绝</option>
                    <option value="已同意">已同意</option>
                </select>
            </div>
        </div>
    </div>

</form>
<table class="layui-table"
       id="application"
       lay-filter="applications">
</table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn  layui-btn-xs" lay-event="edit">同意</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="refuse">拒绝</a>
    <c:if test="${admin.adminClass==1}">
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </c:if>
</script>

<script>
    <%--JS gloable varilible--%>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/static/js/fun_applications.js"></script>


</body>
</html>