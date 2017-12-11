<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

long random = new Date().getTime();
%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>ACHILLES | Index</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path %>/css/vender/bootstrap/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=path %>/css/vender/font-awesome/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=path %>/css/vender/ionicons/ionicons.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="<%=path %>/plugins/datatables/dataTables.bootstrap.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path %>/plugins/iCheck/square/blue.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=path %>/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="<%=path %>/css/skins/skin-blue.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="<%=path %>/js/vender/html5shiv.min.js"></script>
  <script src="<%=path %>/js/vender/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <header class="main-header">

    <!-- Logo -->
    <a href="<%=path %>/page/index.jsp" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>A</b>CHI</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>战神对战信息系统</b></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="javascript:void(0)" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          
          <!-- User Account Menu -->
          <li class="dropdown user user-menu">
            <!-- Menu Toggle Button -->
            <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
              <!-- The user image in the navbar-->
              <img src="../img/user2-160x160.jpg" class="user-image" alt="User Image">
              <!-- hidden-xs hides the username on small devices so only the image appears. -->
              <span class="hidden-xs user-info">管理员</span>
            </a>
            <ul class="dropdown-menu">
              <!-- The user image in the menu -->
              <li class="user-header">
                <img src="../img/user2-160x160.jpg" class="img-circle" alt="User Image">

                <p>
                  <span class="user-info">管理员</span>
<!--                   <small>用户管理员</small> -->
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
<!--                 <div class="pull-left"> -->
<!--                   <a href="javascript:void(0)" class="btn btn-default btn-flat">Profile</a> -->
<!--                 </div> -->
                <div class="pull-right">
                  <a id="logoff" href="javascript:void(0)" class="btn btn-default btn-flat">退出</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
<!--           <li> -->
<!--             <a href="javascript:void(0)" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a> -->
<!--           </li> -->
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      

      <!-- Sidebar Menu -->
      <ul id="mm" class="sidebar-menu">
        <li class="header">控制台</li>
        <li class="treeview">
          <a href="javascript:void(0)"><i class="fa fa-link"></i> <span>Multilevel</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
        
          <ul class="treeview-menu">
            <li><a href="javascript:void(0)">Link in level 2</a></li>
            <li><a href="javascript:void(0)">Link in level 2</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
  <div class="content-wrapper-inner">
    <!-- Content Header (Page header) -->
    <section class="content-header">
    </section>

    <!-- Main content -->
    <section class="content">
<!--     	<div class="callout callout-info"> -->
<!--           <h4><i id="showDate"></i> <i id="showTime"></i></h4> -->

<!--           <p class="lead">当一切都随风而逝的时候，那些特别的瞬间都成了永恒</p> -->
<!-- 				<p class="lead">您的精彩时刻有我们守护</p> -->
<!--         </div> -->

		<div class="row text-center" style="line-height:550px">
<!--       		<img src="../img/shield_genericapp.png" class="user-image" alt="User Image" /> -->
<!-- 			<img src="../img/Stopwatch-128.png" class="user-image" alt="User Image" /> -->
			<img src="../img/terran.jpg" class="user-image" alt="User Image" />
			<img src="../img/protoss.jpg" class="user-image" alt="User Image" />
			<img src="../img/zerg.jpg" class="user-image" alt="User Image" />
		</div>
		
    	<div  class="row text-center" >
    			<p class="lead">选手管理、赛事安排</p>
				<p class="lead">对战结果、实时排名</p>
    	</div>
		
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper-inner -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
  <footer class="main-footer">
    <!-- To the right -->
<!--     <div class="pull-right hidden-xs"> -->
<!--       Anything you want -->
<!--     </div> -->
    <!-- Default to the left -->
<!--     <strong>Copyright &copy; 2016 <a href="javascript:void(0)">Company</a>.</strong> All rights reserved. -->
  </footer>
  
  <!-- model window -->
  <div class="modal modal-info fade" id="tipModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">提示</h4>
         </div>
         <div class="modal-body" id="tipMessage"></div>
         <div class="modal-footer">
            <button type="button" class="btn btn-outline"  data-dismiss="modal">确定</button>
         </div>
      </div><!-- /.modal-content -->
	</div><!-- /.modal -->
  </div>
  
  <div class="modal fade" id="progress_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	<div class="modal-dialog">
	  <div class="modal-content">
			<div class="modal-header">
			</div>
			<div class="modal-body">
				<div class="progress progress-small progress-striped active">
					<div class="progress-bar progress-bar-success" style="width: 100%;"></div>
				</div>
				<div class="hidden" id="progress_message">
				</div>
			</div>
			<div class="modal-footer hidden" id="progress_btn">
				<button type="button" class="btn btn-sm btn-default"  data-dismiss="modal">确定</button>
			</div>
	  </div><!-- /.modal-content -->
	</div><!-- /.modal -->
	</div>

</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/js/vender/bootstrap/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="<%=path %>/plugins/datatables/jquery.dataTables.js"></script>
<script src="<%=path %>/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- iCheck -->
<script src="<%=path %>/plugins/iCheck/icheck.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=path %>/js/app.js"></script>
<!-- tree -->
<script src="<%=path %>/plugins/fuelux/tree.js"></script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
<script type="text/javascript">
	var ACHILLESOptions = {
		basePath: '<%=path %>'
	};
</script>
<!-- TDMS -->
<script src="<%=path %>/js/main.js?rand=" + <%=random %>></script>
</body>
</html>
