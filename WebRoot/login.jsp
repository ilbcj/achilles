<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	byte[] byteRand = new byte[32];
	new java.util.Random().nextBytes(byteRand);
	String random = org.apache.commons.codec.binary.Base64.encodeBase64String(byteRand);
	
	HttpSession sess = request.getSession();
	if (sess == null){
		sess = request.getSession(true);
	}	
	sess.setAttribute("auth.random",random);
	System.out.println("login flag:" + random);
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>ACHILLES | Log in</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path %>/css/vender/bootstrap/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=path %>/css/vender/font-awesome/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=path %>/css/vender/ionicons/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=path %>/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path %>/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="<%=path %>/js/vender/html5shiv.min.js"></script>
  <script src="<%=path %>/js/vender/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
     <b>战神对战信息系统</b><!-- ACHILLES -->
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">用户登录</p>

	<div class="alert alert-info alert-dismissible hidden">
       <i class="icon fa fa-warning"></i> 
       Info alert preview. This alert is dismissable.
    </div>
    <form id="login" name="login" action="<%=path %>/login/loginpwd.action" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="loginid" placeholder="请输入管理员账号" value="admin" />
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="pwd" placeholder="请输入管理员口令" value="admin" />
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-6">
          <button type="submit" class="btn bg-primary btn-block btn-flat">登录</button>
        </div>
        <div class="col-xs-6">
          <button type="reset" class="btn bg-primary btn-block btn-flat">取消</button>
        </div>
        <!-- /.col -->
      </div>
      <input type="hidden" name="random" value="<%= random%>" />
    </form>

    

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/js/vender/bootstrap/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="<%=path %>/plugins/iCheck/icheck.min.js"></script>
<script>
  var message = '<s:property value="message" />';
  $(function () {
    if( message != '' ) {
		$("div.alert").removeClass('hidden').html('<i class="icon fa fa-warning"/>' + message);
	}
  });
</script>
</body>
</html>
