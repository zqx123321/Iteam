<%--
  Created by IntelliJ IDEA.
  User: zqx
  Date: 2018/3/6
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Iteam社团账号申请管理员登录</title>
    <meta charset = "UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/loinCss.css">
</head>
<body>
${res}
<canvas class="login-bg" id="bg" height="617" width="1280">
</canvas>
<div class="logIn-cont" style="padding-top: 54.5px;">

    <div class="logIn-main">
        <div class="logIn-title">
            <div class="logIn-maintitle">
                <div>
                    <img src="${pageContext.request.contextPath}/static/img/lineer.png">
                    <h1>Iteam社团账号申请管理员登录</h1>
                    <img src="${pageContext.request.contextPath}/static/img/lineer.png">
                </div>
            </div>

        </div>


        <form id="form1" method="post" action="./doLogin">
            <div class="lodIn-cont">

                <div>
                    <input type="text" name="account" id="account" class="txtfield" tabindex="1" value="请输入用户名">

                </div>
                <div class="pass">

                    <span id="password_text" onclick="this.style.display='none';document.getElementById('password').style.display='inline-block';document.getElementById('password').focus();" >密码</span>
                    <input name="password" type="password" id="password" style="display:none;" onblur="if(this.value==''){document.getElementById('password_text').style.display='inline-block';this.style.display='none'};"/>
                </div>

                <div class="logIn-check">
                    <div>
                        <input type="text" name="check" id="check" class="txtfield" value="请输入验证码" />
                        <img  src ="./captcha"  id="valiImg" alt="validate" width="200" height="50" onclick="reflash();"/>
                        <script>
                            function reflash() {
                                $("#valiImg").attr("src", './captcha?time=' + Math.random());
                            }
                        </script>
                        <%--<img src="images/logIn.jpg">--%>
                    </div>
                </div>
            </div>
            <div class="logIn-button">
                <div>
                    <input type="submit" name="loginbtn" id="loginbtn" class="flatbtn-blu hidemodal" value="登录" tabindex="4">

                </div>
                <div>
                </div>                    <input type="submit" name="zhubtn" id="zhubtn" class="flatbtn-blu hidemodal" value="返回" tabindex="5" >

            </div>

        </form>

    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/logInJs.js"></script>
<script type="text/javascript">
    var bg = document.getElementById("bg");
    var ctx = bg.getContext("2d");

    //making the canvas full screen
    bg.height = window.innerHeight;
    bg.width = window.innerWidth;

    //chinese characters - taken from the unicode charset
    var chinese = "01";
    //converting the string into an array of single characters
    chinese = chinese.split("");

    var font_size = 14;
    var line_height = 18;
    var columns = bg.width/(font_size + line_height); //number of columns for the rain
    //an array of drops - one per column
    var drops = [];
    //x below is the x coordinate
    //1 = y co-ordinate of the drop(same for every drop initially)
    for(var x = 0; x < columns; x++) {
        drops[x] = 1;
    }

    var loop = 0;
    //drawing the characters
    function draw()
    {
        loop++;
        if (loop > 100) {
            //清空背景
            ctx.fillStyle = "rgba(245, 249, 250, .5)";
            //ctx.fillStyle = "rgba(0, 0, 0, .5)";
            ctx.fillRect(0, 0, bg.width, bg.height);
            loop = 0;
        } else {
            //Black BG for the canvas
            //translucent BG to show trail
            ctx.fillStyle = "rgba(245, 249, 250, .12)"; //字符消失速度
            //ctx.fillStyle = "rgba(0, 0, 0, .12)";
            ctx.fillRect(0, 0, bg.width, bg.height);
        }

        ctx.fillStyle = "#9ba0a3"; //green text
        //ctx.fillStyle = "#2ECC71";
        ctx.font = font_size + "px arial";
        //looping over drops
        var dropsL = drops.length
        for(var i = 0; i < dropsL; i++)
        {
            //a random chinese character to print
            var text = chinese[Math.floor(Math.random()*chinese.length)];
            //x = i*font_size, y = value of drops[i]*font_size
            ctx.fillText(text, i*(font_size + line_height), drops[i]*(font_size + 4));

            //sending the drop back to the top randomly after it has crossed the screen
            //adding a randomness to the reset to make the drops scattered on the Y axis
            if(drops[i]*font_size > bg.height && Math.random() > 0.995) //密集程度，越大越稀疏
                drops[i] = 0;

            //incrementing Y coordinate
            drops[i]++;
        }
    }
    setInterval(draw, 40);  //重复绘制，下降速度



</script>
</body>
</html>
