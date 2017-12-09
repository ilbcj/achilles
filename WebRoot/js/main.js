/*! ACHILLES main.js
 * ================
 * Main JS application file for ACHILLES v1. This file
 * should be included in the main page. It controls some layout
 * options and data exchange with server.
 *
 * @Author  ilbcj Studio
 * @Support <http://www.ilbcj.com>
 * @Email   <22833638@qq.com>
 * @version 1.0.0
 * @license MIT <http://opensource.org/licenses/MIT>
 */

//Make sure jQuery has been loaded before main.js
if (typeof jQuery === 'undefined') {
	throw new Error('ACHILLES requires jQuery');
}

///*! global function
// * 
// *  @type function
// *  @usage $.message
// */
//(function ($) {
//  'use strict';
//
//  $.fn.tipMessage = function (message) { 
//		$('#successAndFail_message').empty().append(message);
//		$('#successAndFail_Modal').modal('show');
//		var modalTimeOutId = setTimeout(function(){$('#successAndFail_Modal').modal('hide');},2000);
//		console.log( 'timerId: ' +  modalTimeOutId);
//		$('#successAndFail_Modal').on('hidden.bs.modal', function (e) {
//			clearTimeout(modalTimeOutId);
//		});
//	};
//}(jQuery));


/* ACHILLES
 *
 * @type Object
 * @description $.ACHILLES is the main object for the app.
 *              It's used for implementing functions and options related
 *              to the ACHILLES. Keeping everything wrapped in an object
 *              prevents conflict with other plugins and is a better
 *              way to organize our code.
 */
$.ACHILLES = {};

/* --------------------
 * - AdminLTE Options -
 * --------------------
 * Modify these options to suit your implementation
 */
$.ACHILLES.options = {
	basePath: 'ACHILLESdemo'
};

/* ------------------
 * - Implementation -
 * ------------------
 * The next block of code implements ACHILLES's
 * functions and plugins as specified by the
 * options above.
 */
$(function () {
	'use strict';

	//Extend options if external options exist
	if (typeof ACHILLESOptions !== 'undefined') {
		$.extend(true, $.ACHILLES.options, ACHILLESOptions);
	}

	//Easy access to options
	var o = $.ACHILLES.options;
//	console.log(o.basePath);
	//Set up the object
	_initACHILLES(o);

	//Update table style
	$.extend( true, $.fn.dataTable.defaults, {
		searching: true,
		ordering: false,
		select: 'single',
		pagingType: 'full_numbers',
		dom: 'tr<"row"<"col-xs-2"l><"col-xs-7 text-center"p><"col-xs-3 text-right"i>>',
		bAutoWidth: false,
		lengthMenu: [[1, 10, 25, 50, 100], [1, 10, 25, 50, 100]],
		language: {
			url: o.basePath + '/plugins/datatables/table_label.json'
		},
		drawCallback: function( settings ) {
			//var active_class = 'success';
			var active_class = 'active';
				
			var $th_checkbox = $('table.table > thead > tr > th input[type=checkbox]');
			$th_checkbox.iCheck('uncheck').iCheck('destroy'); 
			$th_checkbox.on('ifClicked', function(){
				var th_checked = !this.checked;//checkbox inside "TH" table header , the value getted equals with the status before clicked
				//alert(th_checked);
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).iCheck('check');//.prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).iCheck('uncheck');//.prop('checked', false);
				});
			});
			
			//select/deselect a row when the checkbox is checked/unchecked
			$('table.table').on('ifToggled', 'tbody > tr > td input[type=checkbox]' , function(){
				//alert(this.checked);
				var $row = $(this).closest('tr');
				if(this.checked) $row.addClass(active_class);
				else $row.removeClass(active_class);
			
				var th_checked2 = true;
				$(this).closest('table').find('tbody > tr > td input[type=checkbox]').each(function(){
					if( $(this).prop('checked') == false ) th_checked2 = false;
				});
				//alert(th_checked2);
				$(this).closest('table').find('th input[type=checkbox]').iCheck(th_checked2?'check':'uncheck');
			});
			
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%' // optional
		    });
		}
	});
	
	//update date.toLocaleString
	Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + "点" + this.getMinutes() + "分" + this.getSeconds() + "秒";
	};
	Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + " " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
	};
	
	//Active the main menu and custom menu
	$.ACHILLES.menu.activate();


	
});

function _refreshTime() {
	'use strict';
	
	var dateStr = "";
	var timeStr = "";
	
	var myDate = new Date();
	
	//year
	dateStr += myDate.getFullYear() + "年";
	//month
	dateStr += parseInt(myDate.getMonth()) + 1 + "月";
	//date
	dateStr += myDate.getDate();
	//week
	var Week = ['日','一','二','三','四','五','六'];  
    dateStr += ' 星期' + Week[myDate.getDay()];  
    
    //time
    timeStr += myDate.getHours() + ":";
    timeStr += myDate.getMinutes() + ":";
    timeStr += myDate.getSeconds() + ".";
    var milliseconds = myDate.getMilliseconds();
//    console.log(milliseconds);
    while( (""+milliseconds).length < 3) {
//    	console.log(milliseconds + "|||" + milliseconds.length);
    	milliseconds += '0';
    }
    timeStr += milliseconds;
    $("#showDate").html(dateStr);
    $("#showTime").html(timeStr);
//  console.log(dateStr);
//  console.log(timeStr);
}

/* ----------------------------------
 * - Initialize the ACHILLES Object -
 * ----------------------------------
 * All ACHILLES functions are implemented below.
 */
function _initACHILLES(o) {
	'use strict';
	/* Menu
	 * ======
	* Create main menu and custom menu
	*
	* @type Object
	* @usage $.ACHILLES.menu.activate()
	*/
	$.ACHILLES.menu = {
		activate: function () {
			//get admin info
			o.basePath && $.post(o.basePath + '/login/queryAdminInfo.action',function(data){
				$('span.user-info').html(data.adminname);
			});
			
			//add logoff event
			$('#logoff').on('click.ACHILLES.admin.off', function(e){
				o.basePath && $.post(o.basePath + '/login/logout.action', function(data){
					window.location = o.basePath;
				});
			});
		  
			//load main menu
			var menu = '<li class="header">控制台</li>';
			menu += '<li class="treeview active">';
			menu += '<a href="javascript:void(0)"><i class="fa fa-gears"></i> <span>选手管理</span>';
            menu +=   '<span class="pull-right-container">';
            menu +=     '<i class="fa fa-angle-left pull-right"></i>';
            menu +=   '</span>';
            menu += '</a>';
            menu += '<ul class="treeview-menu">';
            menu +=   '<li><a id="menu_player_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>选手信息</a></li>';
            menu += '</ul>';
            menu += '</li>';
            
            menu += '<li class="treeview">';
			menu += '<a href="javascript:void(0)"><i class="fa fa-gears"></i> <span>对战管理</span>';
            menu +=   '<span class="pull-right-container">';
            menu +=     '<i class="fa fa-angle-left pull-right"></i>';
            menu +=   '</span>';
            menu += '</a>';
            menu += '<ul class="treeview-menu">';
            menu +=   '<li><a id="menu_battle_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>对战信息</a></li>';
//            menu +=   '<li><a id="menu_device_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>对阵信息</a></li>';
            menu += '</ul>';
            menu += '</li>';
            
            menu += '<li class="treeview">';
			menu += '<a href="javascript:void(0)"><i class="fa fa-gears"></i> <span>积分管理</span>';
            menu +=   '<span class="pull-right-container">';
            menu +=     '<i class="fa fa-angle-left pull-right"></i>';
            menu +=   '</span>';
            menu += '</a>';
            menu += '<ul class="treeview-menu">';
            menu +=   '<li><a id="menu_score_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>积分榜</a></li>';
            menu += '</ul>';
            menu += '</li>';
            
            menu += '<li class="treeview">';
			menu += '<a href="javascript:void(0)"><i class="fa fa-gears"></i> <span>系统管理</span>';
            menu +=   '<span class="pull-right-container">';
            menu +=     '<i class="fa fa-angle-left pull-right"></i>';
            menu +=   '</span>';
            menu += '</a>';
            menu += '<ul class="treeview-menu">';
            menu +=   '<li><a id="menu_config_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>参数配置</a></li>';
            menu += '</ul>';
            menu += '</li>';
            
            $('#mm').html(menu);
    		
    		$('#menu_player_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/player/player_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.player.activate();});
    		});
    		$('#menu_battle_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/battle/battle_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.devicereg.activate();});
    		});
    		$('#menu_score_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/score/score_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.devicereg.activate();});
    		});
    		$('#menu_config_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/config/config_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.devicereg.activate();});
    		});
		}
	};// end of $.ACHILLES.menu
	
	
	/* player
	* ======
	* player infomation maintain page
	*
	* @type Object
	* @usage $.ACHILLES.player.activate()
	* @usage $.ACHILLES.player.queryAttestation()
	* @usage $.ACHILLES.player.clearQueryAttestationCondition()
	* @usage $.ACHILLES.player.openAddAttestationWindow()
	* @usage $.ACHILLES.player.openModAttestationWindow()
	* @usage $.ACHILLES.player.openDelAttestationWindow()
	* @usage $.ACHILLES.player.attestationDel()
	* @usage $.ACHILLES.player.attestationSave()
	* @usage $.ACHILLES.player.attestationDetailReturn()
	*/
	$.ACHILLES.player = {
		activate: function () {
			o.basePath && $('#player_main_table').DataTable( {
				ajax:{
					url: o.basePath + '/player/queryPlayers.action',
					data: function(d) {
						var loginid = $('#player_query_type').val();
				    	var name = $('#player_query_sn').val();
				    	return $.extend( {}, d, {
				    			loginId: loginid,
				    			name: name
				    	});
					},
					type: 'POST',
					dataSrc: 'items'
				},
				processing: true,
				serverSide: true,
				columns: [
					{ data: '' },
					{ data: 'loginId' },
					{ data: 'name' },
					{ data: 'race' },
					{ data: 'timestamp' }
				],
				rowId: 'id',
				columnDefs: [
					{
						render: function ( data, type, row ) {
							return '<input type="checkbox" data-id="' + row.id + '" />';
		
						},
						targets: 0
					},
					{
						render: function ( data, type, row ) {
							var html = '<div class="btn-group">';
							html += '<button class="player_info btn btn-xs btn-success" data-id="' + row.id + '" disabled="disabled"><i class="fa fa-check"></i>详情</button>';
							html += '<button class="player_del btn btn-xs btn-success" data-id="' + row.id + '"><i class="fa fa-check"></i>删除</button>';
							html += '</div>';
							return html;
						},
						targets: 4
					}
				],
				createdRow: function ( row, data, index ) {
					$('td', row).eq(0).addClass('text-center');
				}
			});
			
			//listen page items' event
			$('#query_player').on('click.ACHILLES.player.query', $.ACHILLES.player.queryPlayers);
			$('#query_player_reset').on('click.ACHILLES.player.queryreset', $.ACHILLES.player.clearQueryPlayerCondition);
			$('#add_player').on('click.ACHILLES.player.add', $.ACHILLES.player.openAddWindow);
			$('#devicereg_main_table').on( 'draw.dt', function () {
				$('.player_info').on('click.ACHILLES.player.detail', $.ACHILLES.player.detail);
				$('.player_del').on('click.ACHILLES.player.delete', $.ACHILLES.player.del);
			});
			
		},
		queryPlayers: function () {
			o.basePath && $('#player_main_table').DataTable().ajax.reload();
		},
		clearQueryPlayerCondition: function () {
			$('#player_query_type').val('');
			$('#player_query_sn').val('');
		},
		openAddWindow: function () {
			$("#add_Modal").modal('show');
		},
		detail: function () {
			
			var rowId = $(this).data("id");
			//var rowData = $('#devicereg_main_table').DataTable().row( '#' + rowId ).data();
			
			//console.log( rowData );
//			$('#attestation_aid').html(rowData.attestationId);
//			var message = '您选取了' + $("#enterpriseUserMainTable :checkbox:checked[data-id]").length + '条记录。确认要删除所选行业用户？';
//	    	$("#del_enterpriseUser_message").empty().append(message);
//			$("#del_enterpriseUser_Modal").modal('show');
			
			var poststr = "";
			poststr += "id=" + rowId;
			o.basePath && $.post(o.basePath + "/dev/auditY.action", poststr, function(retObj) {
				if(retObj.result == true) {
					var message = "审核操作完成，设备注册申请审核通过!";
					$.ACHILLES.tipMessage(message);
					
					o.basePath && $('#devicereg_main_table').DataTable().ajax.reload();
				} else {
					var message = "审核设备注册信息操作失败! " + retObj.message;
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
			return;
		},
		del: function() {
			var rowId = $(this).data("id");
			
			var poststr = "";
			poststr += "id=" + rowId;
			o.basePath && $.post(o.basePath + "/dev/auditN.action", poststr, function(retObj) {
				if(retObj.result == true) {
					var message = "审核操作完成，设备注册申请审核被拒绝!";
					$.ACHILLES.tipMessage(message);
					
					o.basePath && $('#devicereg_main_table').DataTable().ajax.reload();
				} else {
					var message = "审核设备注册信息操作失败! " + retObj.message;
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		}

	};// end of $.ACHILLES.player
	
	/* attestation
	* ======
	* devicereg maintain page
	*
	* @type Object
	* @usage $.ACHILLES.devicereg.activate()
	* @usage $.ACHILLES.devicereg.queryAttestation()
	* @usage $.ACHILLES.devicereg.clearQueryAttestationCondition()
	* @usage $.ACHILLES.devicereg.openAddAttestationWindow()
	* @usage $.ACHILLES.devicereg.openModAttestationWindow()
	* @usage $.ACHILLES.devicereg.openDelAttestationWindow()
	* @usage $.ACHILLES.devicereg.attestationDel()
	* @usage $.ACHILLES.devicereg.attestationSave()
	* @usage $.ACHILLES.devicereg.attestationDetailReturn()
	*/
	$.ACHILLES.devicereg = {
		activate: function () {
			o.basePath && $('#devicereg_main_table').DataTable( {
				ajax:{
					url: o.basePath + '/dev/queryDevices.action',
					data: function(d) {
						var type = $('#devicereg_query_type').val();
				    	var sn = $('#devicereg_query_sn').val();
				    	return $.extend( {}, d, {
				    			type: type,
				    			sn: sn
				    	});
					},
					type: 'POST',
					dataSrc: 'items'
				},
				processing: true,
				serverSide: true,
				columns: [
					{ data: '' },
					{ data: 'type' },
					{ data: 'sn' },
					{ data: 'cipher' },
					{ data: 'devid' },
					{ data: 'status' },
					{ data: 'timestamp' }
				],
				rowId: 'id',
				columnDefs: [
					{
						render: function ( data, type, row ) {
							return '<input type="checkbox" data-id="' + row.id + '" />';
		
						},
						targets: 0
					},
					{
						render: function ( data, type, row ) {
							var text = "未知状态";
							if(data === 0) {
								text = "新申请";
							}
							else if(data === 1) {
								text = "已注册";
							}
							else if(data === 2) {
								text = "审核拒绝";
							}
							else if(data === 99) {
								text = "错误状态";
							}
							return text;
						},
						targets: 5
					},
					{
						render: function ( data, type, row ) {
							return new Date(parseInt(data)).toLocaleString();
						},
						targets: 6
					},
					{
						render: function ( data, type, row ) {
							var html = '<div class="btn-group">';
							if(row.status === 1) {
								html += '<button class="devicereg_audit_yes btn btn-xs btn-success disabled" data-id="' + row.id + '" disabled="disabled"><i class="fa fa-check"></i>审核通过</button>';
							}
							else {
								html += '<button class="devicereg_audit_yes btn btn-xs btn-success" data-id="' + row.id + '"><i class="fa fa-check"></i>审核通过</button>';
							}
							
							if(row.status === 2) {
								html += '<button class="devicereg_audit_no btn btn-xs btn-danger disabled" data-id="' + row.id + '" disabled="disabled"><i class="fa fa-times"></i>审核拒绝</button>';
							}
							else {
								html += '<button class="devicereg_audit_no btn btn-xs btn-danger" data-id="' + row.id + '"><i class="fa fa-times"></i>审核拒绝</button>';
							}
							
							html += '</div>';
							return html;
						},
						targets: 7
					}
				],
				createdRow: function ( row, data, index ) {
					$('td', row).eq(0).addClass('text-center');
				}
			});
			
			//listen page items' event
			$('#query_devicereg').on('click.ACHILLES.devicereg.query', $.ACHILLES.devicereg.queryDevices);
			$('#query_devicereg_reset').on('click.ACHILLES.devicereg.queryreset', $.ACHILLES.devicereg.clearQueryDeviceCondition);
			$('#devicereg_main_table').on( 'draw.dt', function () {
				$('.devicereg_audit_yes').on('click.ACHILLES.devicereg.detail', $.ACHILLES.devicereg.auditRequestYes);
				$('.devicereg_audit_no').on('click.ACHILLES.devicereg.detail', $.ACHILLES.devicereg.auditRequestNo);
			});
			
		},
		queryDevices: function () {
			o.basePath && $('#devicereg_main_table').DataTable().ajax.reload();
		},
		clearQueryDeviceCondition: function () {
			$('#devicereg_query_type').val('');
			$('#devicereg_query_sn').val('');
		},
		auditRequestYes: function () {
			
			var rowId = $(this).data("id");
			//var rowData = $('#devicereg_main_table').DataTable().row( '#' + rowId ).data();
			
			//console.log( rowData );
//			$('#attestation_aid').html(rowData.attestationId);
//			var message = '您选取了' + $("#enterpriseUserMainTable :checkbox:checked[data-id]").length + '条记录。确认要删除所选行业用户？';
//	    	$("#del_enterpriseUser_message").empty().append(message);
//			$("#del_enterpriseUser_Modal").modal('show');
			
			var poststr = "";
			poststr += "id=" + rowId;
			o.basePath && $.post(o.basePath + "/dev/auditY.action", poststr, function(retObj) {
				if(retObj.result == true) {
					var message = "审核操作完成，设备注册申请审核通过!";
					$.ACHILLES.tipMessage(message);
					
					o.basePath && $('#devicereg_main_table').DataTable().ajax.reload();
				} else {
					var message = "审核设备注册信息操作失败! " + retObj.message;
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
			return;
		},
		auditRequestNo: function() {
			var rowId = $(this).data("id");
			
			var poststr = "";
			poststr += "id=" + rowId;
			o.basePath && $.post(o.basePath + "/dev/auditN.action", poststr, function(retObj) {
				if(retObj.result == true) {
					var message = "审核操作完成，设备注册申请审核被拒绝!";
					$.ACHILLES.tipMessage(message);
					
					o.basePath && $('#devicereg_main_table').DataTable().ajax.reload();
				} else {
					var message = "审核设备注册信息操作失败! " + retObj.message;
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		}

	};// end of $.ACHILLES.attestation
  
	/* TipMessage(message)
	* ==========
	* Showing the info or warn message.
	*
	* @type Function
	* @usage: $.ACHILLES.tipMessage(message)
	*/
	$.ACHILLES.tipMessage = function(message, isAutoClose) {
		isAutoClose = arguments[1]===undefined ? true : arguments[1];
		$('#tipMessage').empty().append(message);
		$('#tipModal').modal('show');
		if(isAutoClose) {
			var modalTimeOutId = setTimeout(function(){$('#tipModal').modal('hide');},2000);
		}
		
		//console.log( 'timerId: ' +  modalTimeOutId);
		$('#tipModal').on('hidden.bs.modal', function (e) {
			if(isAutoClose)
				clearTimeout(modalTimeOutId);
		});
	};// end of $.ACHILLES.tipMessage
  
	/* CheckLoad()
	* ==========
	* check load response.
	*
	* @type Function
	* @usage: $.ACHILLES.checkLoad(response)
	*/
	$.ACHILLES.checkLoad = function(response) {
		if(! new RegExp('content-wrapper-inner').test(response) ){
			window.location.href = $.ACHILLES.options.basePath;
		}
			
	};// end of $.ACHILLES.checkLoad
  
///* CheckLoad()
//* ==========
//* active function of checkbox in table, which can turn the rows's check status on/off.
//*
//* @type Function
//* @usage: $.ACHILLES.checkLoad()
//*/
//$.ACHILLES.checkLoad = function() {
//		
//};// end of $.ACHILLES.checkLoad
  
}
