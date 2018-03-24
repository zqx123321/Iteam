<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zqx
  Date: 2018/3/17
  Time: 11:25
  To change this template use File | Settings | File Templates.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Iteam社团账号申请材料填写</title>
</head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css" media="all">
<script src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/layui/layui.js" charset="utf-8"></script>
<style>
    .layui-upload-img {
        width: 92px;
        height: 92px;
        margin: 0 10px 10px 0;
    }
</style>
<body>
<div style="width: 70%; margin: auto;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend style="margin: auto;">Iteam社团账号申请材料填写</legend>
    </fieldset>
    <form class="layui-form layui-form-pane" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">社团名称</label>
            <div class="layui-input-block">
                <%--<input type="hidden" name="token" value="${TOKEN_IN_SESSION}">--%>
                <input type="text" name="clubName" lay-verify="required" autocomplete="off" placeholder="请输入社团名称"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">成立时间</label>
            <div class="layui-input-block">
                <input type="text" name="clubCreate" lay-verify="required" id="date" autocomplete="off"
                       placeholder="请选择社团成立时间" class="layui-input">
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
            <label class="layui-form-label">社团负责人</label>
            <div class="layui-input-block">
                <input type="text" name="clubOwner" lay-verify="required" autocomplete="off" placeholder="请输入社团负责人"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">联系电话</label>
                <div class="layui-input-inline">
                    <input type="tel" name="clubPhone" placeholder="请输入联系电话" lay-verify="required|phone"
                           autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">联系邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="clubEmail" lay-verify="email" placeholder="请输入联系邮箱" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">申请说明</label>
            <div class="layui-input-block">
                <textarea name="intro" lay-verify="required" placeholder="请输入申请说明" class="layui-textarea"></textarea>
            </div>
        </div>


        <div class="layui-form-item">
            <button type="button" class="layui-btn" id="test2">上传相关证明材料（每张图片最大5MB）</button>
            <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                预览图：
                <div class="layui-upload-list" id="demo2"></div>
            </blockquote>
        </div>

        <div class="layui-form-item">
            <button class="layui-btn" style="width: 100%" lay-submit="" lay-filter="submit">提交</button>
        </div>
    </form>

    <div style="margin:auto;width:38%">
        <p>Copyright © 2018 Itstudio All rights reserved.<a style="color: #1C86EE;font-size: 14px;line-height: 35px;" href="http://it.ouc.edu.cn/create/admin">管理登录</a></p>
    </div>


    <script>
        var contextPath = "${pageContext.request.contextPath}";
    </script>

    <!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
    <script>
        layui.use(['form', 'upload', 'laydate'], function () {
            var form = layui.form
                , layer = layui.layer
                , upload = layui.upload
                , laydate = layui.laydate;

            //日期
            laydate.render({
                elem: '#date'
            });

            //监听提交
            form.on('submit(submit)', function (data) {
                var data = data.field;
                layer.confirm("确定提交吗？", function (index) {
                    $.ajax({
                        type: 'POST',
                        url: contextPath + '/application_save',
                        dataType: 'json',
                        data: data,
                        success: function (data) {
                            var messages = data.success;
                            if (messages > 0) {
                                layer.msg('提交成功，请耐心等待管理员审核');
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
                return false;
            });

            //多图片上传
            upload.render({
                elem: '#test2'
                , url: contextPath + '/application_upload/'
                , multiple: true
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                    });
                }
                , done: function (res) {
                    if (res.success > 0) {
                        layer.msg("上传成功！");
                    } else {
                        layer.msg("上失败，请刷新重试");
                    }
                }
            });


        });
    </script>
</div>

</body>
</html>
