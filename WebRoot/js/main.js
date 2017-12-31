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
			
			$('table input').iCheck({
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
			menu += '<li class="treeview">';
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
			menu += '<a href="javascript:void(0)"><i class="fa fa-gears"></i> <span>赛季管理</span>';
            menu +=   '<span class="pull-right-container">';
            menu +=     '<i class="fa fa-angle-left pull-right"></i>';
            menu +=   '</span>';
            menu += '</a>';
            menu += '<ul class="treeview-menu">';
            menu +=   '<li><a id="menu_season_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>赛季信息</a></li>';
            menu +=   '<li><a id="menu_round_maintain" href="javascript:void(0)"><i class="fa fa-circle-o"/>场次信息</a></li>';
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
    		$('#menu_season_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/period/season_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.season.activate();});
    		});
    		$('#menu_round_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/period/round_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.round.activate();});
    		});
    		$('#menu_battle_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/battle/battle_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.match.activate();});
    		});
    		$('#menu_score_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/score/score_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.score.activate();});
    		});
    		$('#menu_config_maintain').on('click.ACHILLES.menu.data-api',function(e){
    			o.basePath && $('div.content-wrapper').load(o.basePath + '/page/config/config_maintain.html?random=' + Math.random() + ' .content-wrapper-inner',
    					function(response,status,xhr){$.ACHILLES.checkLoad(response);$.ACHILLES.config.activate();});
    		});
		}
	};// end of $.ACHILLES.menu
	
	/* season
	* ======
	* match season infomation maintain page
	*
	* @type Object
	* @usage $.ACHILLES.season.activate()
	* @usage $.ACHILLES.season.addSeasonWindow()
	* @usage $.ACHILLES.season.addSeasonConfirm()
	* @usage $.ACHILLES.season.batchDelSeason()
	* @usage $.ACHILLES.season.delSeason()
	* @usage $.ACHILLES.season.delSeasonsConfirm()
	*/
	$.ACHILLES.season = {
		activate: function () {
			o.basePath && $('#season_main_table').DataTable( {
				ajax:{
					url: o.basePath + '/period/querySeasons.action',
					type: 'POST',
					dataSrc: 'items'
				},
				processing: true,
				serverSide: true,
				columns: [
					{ data: '' },
					{ data: 'name' },
					{ data: 'timestamp' },
					{ data: 'memo' }
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
							html += '<button class="season_info btn btn-xs btn-success" data-id="' + row.id + '"><i class="fa fa-edit"></i>详情</button>';
							html += '<button class="season_del btn btn-xs btn-danger" data-id="' + row.id + '"><i class="fa fa-trash-o"></i>删除</button>';
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
			$('#add_season').on('click.ACHILLES.season.add', $.ACHILLES.season.addSeasonWindow);
			$('#season_detail_modal_confirm').on('click.ACHILLES.season.addconfirm', $.ACHILLES.season.saveSeasonConfirm);
			$('#del_seasons').on('click.ACHILLES.season.delete.batch', $.ACHILLES.season.batchDelSeason);
			$('#season_main_table').on( 'draw.dt', function () {
				$('.season_info').on('click.ACHILLES.season.detail', $.ACHILLES.season.addSeasonWindow);
				$('.season_del').on('click.ACHILLES.season.delete.single', $.ACHILLES.season.delSeason);
			});
			$('#season_confirm_modal_confirm').on('click.ACHILLES.season.delconfirm', $.ACHILLES.season.delSeasonsConfirm);
		},
		addSeasonWindow: function () {
			var id = $(this).data('id');
			if( typeof id === 'undefined' ) {
				$('#name').val('');
		    	$('#timestamp').val('');
		    	$('#memo').val('');
		    	$('#season_detail_modal_confirm').data('season_id', 0);
			}
			else {
				var rowData = $('#season_main_table').DataTable().row( '#' + id ).data();
				$('#name').val(rowData.name);
		    	$('#timestamp').val(rowData.timestamp);
		    	$('#memo').val(rowData.memo);
				$('#season_detail_modal_confirm').data('season_id', id);
			}
			$('#season_detail_modal').modal('show');
		},
		saveSeasonConfirm: function () {
			var id = $('#season_detail_modal_confirm').data('season_id');
			var name = $('#name').val();
		    var time = $('#timestamp').val();
		    var memo = $('#memo').val();
		    
			var postData = 'season.id=' + id;
			postData += '&season.name=' + name;
			postData += '&season.timestamp=' + time;
			postData += '&season.memo=' + memo;
	
			o.basePath && $.post(o.basePath + '/period/saveSeason.action?rand=' + Math.random(), postData, function(retObj,textStatus, jqXHR) {
	    		if(retObj.result == true)
				{
					o.basePath && $('#season_main_table').DataTable().ajax.reload();
					var message = '保存赛季信息成功!';
					$.ACHILLES.tipMessage(message);
				} else {
					var message = '保存赛季信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		},
		batchDelSeason: function () {
			if( ($('#season_main_table :checkbox:checked[data-id]').length == 0) ) { 
				var message = "请选择要删除的赛季!";
				$.ACHILLES.tipMessage(message);
				return;
			} else {
				var delIds = '';
				$("#season_main_table :checkbox").each(function(index,checkboxItem){
					if($(checkboxItem).prop('checked') && index != 0){
						delIds += "delIds=" + $(checkboxItem).attr('data-id') + "&";
					}
				});
				var message = '已经选取了' + $("#season_main_table :checkbox:checked[data-id]").length + '条记录。是否要删除这些赛季？';
				$('#season_confirm_modal_message').empty().append(message);
				$('#season_confirm_modal_confirm').data('delIds', delIds);
				$("#season_confirm_modal").modal('show');
			}
		},
		delSeason: function () {
			var rowId = $(this).data('id');
			var delIds = 'delIds=' + rowId;
			var message = '是否要删除此赛季？';
			$('#season_confirm_modal_message').empty().append(message);
			$('#season_confirm_modal_confirm').data('delIds', delIds);
			$("#season_confirm_modal").modal('show');
		},
		delSeasonsConfirm: function () {
			var postData = '';
			postData = $('#season_confirm_modal_confirm').data('delIds');
			o.basePath && $.post(o.basePath + '/period/deleteSeasons.action', postData, function(retObj) {
				if(retObj.result == true) {
					var message = '赛季信息已删除';
					$.ACHILLES.tipMessage(message);
					$('#season_main_table').DataTable().ajax.reload();
				} else {
					var message = '删除赛季信息操作失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		}
	};// end of $.ACHILLES.season
	
	/* round
	* ======
	* match round infomation maintain page
	*
	* @type Object
	* @usage $.ACHILLES.round.activate()
	* @usage $.ACHILLES.round.addRoundWindow()
	* @usage $.ACHILLES.round.addRoundConfirm()
	* @usage $.ACHILLES.round.batchDelRound()
	* @usage $.ACHILLES.round.delRound()
	* @usage $.ACHILLES.round.delRoundsConfirm()
	*/
	$.ACHILLES.round = {
		activate: function () {
			o.basePath && $('#round_main_table').DataTable( {
				ajax:{
					url: o.basePath + '/period/queryRounds.action',
					type: 'POST',
					dataSrc: 'items'
				},
				processing: true,
				serverSide: true,
				columns: [
					{ data: '' },
					{ data: 'name' },
					{ data: 'timestamp' },
					{ data: 'seasonName' },
					{ data: 'status' },
					{ data: 'memo' }
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
							var text = '';
							if(data == 0) {
								text = '已删除场次';
							}
							else if(data == 1) {
								text = '活动中';
							}
							else if(data == 2) {
								text = '上一轮';
							}
							else if(data == 3) {
								text = '历史场次';
							}
							if(data == 9) {
								text = '初始场次';
							}
							return text;
						},
						targets: 4
					},
					{
						render: function ( data, type, row ) {
							var html = '<div class="btn-group">';
							html += '<button class="round_info btn btn-xs btn-success" data-id="' + row.id + '"><i class="fa fa-edit"></i>详情</button>';
							html += '<button class="round_del btn btn-xs btn-danger hidden" data-id="' + row.id + '"><i class="fa fa-trash-o"></i>删除</button>';
							if(row.status == 1) {
								html += '<button class="round_archive btn btn-xs btn-danger" data-id="' + row.id + '"><i class="fa fa-trash-o"></i>归档</button>';	
							}
							html += '</div>';
							return html;
						},
						targets: 6
					}
				],
				createdRow: function ( row, data, index ) {
					$('td', row).eq(0).addClass('text-center');
				}
			});
			
			//listen page items' event
			$('#add_round').on('click.ACHILLES.round.add', $.ACHILLES.round.addRoundWindow);
			$('#round_detail_modal_confirm').on('click.ACHILLES.round.addconfirm', $.ACHILLES.round.saveRoundConfirm);
			$('#del_rounds').on('click.ACHILLES.round.delete.batch', $.ACHILLES.round.batchDelRound);
			$('#round_main_table').on( 'draw.dt', function () {
				$('.round_info').on('click.ACHILLES.round.detail', $.ACHILLES.round.addRoundWindow);
				$('.round_del').on('click.ACHILLES.round.delete.single', $.ACHILLES.round.delRound);
				$('.round_archive').on('click.ACHILLES.round.archive', $.ACHILLES.round.archiveRound);
			});
			$('#round_confirm_modal_confirm').on('click.ACHILLES.round.delconfirm', $.ACHILLES.round.roundConfirmModalConfirm);
		},
		addRoundWindow: function () {
			var id = $(this).data('id');
			o.basePath && $.post(o.basePath + '/period/querySeasons.action?rand=' + Math.random(), {}, function(retObj,textStatus, jqXHR) {
				if(retObj.result == true)
				{
					$('#season option').remove();
					$('#season').append('<option value="">请选择一个所属赛季</option>');
					retObj.items.forEach(function(season){
						$('#season').append('<option value="' + season.id + '">' + season.name + '</option>');
					});
					if( typeof id === 'undefined' ) {
						$('#name').val('');
				    	$('#timestamp').val('');
				    	$('#memo').val('');
				    	$('#status').val('');
				    	$("#status").data('status', '');
				    	$('#season').val('');
				    	$('#last_round').val('');
				    	$('#last_round').data('last_round_id', '');
				    	$('#round_detail_modal_confirm').data('round_id', 0);
					}
					else {
						var rowData = $('#round_main_table').DataTable().row( '#' + id ).data();
						$('#name').val(rowData.name);
				    	$('#timestamp').val(rowData.timestamp);
				    	$('#memo').val(rowData.memo);
				    	var statusText = '';
				    	if(rowData.status == 0) {
				    		statusText = '已删除场次';
				    	}
				    	else if(rowData.status == 1) {
				    		statusText = '当前场次';
				    	}
				    	else if(rowData.status == 2) {
				    		statusText = '上一场次';
				    	}
				    	else if(rowData.status == 3) {
				    		statusText = '历史场次';
				    	}
				    	else if(rowData.status == 9) {
				    		statusText = '初始场次';
				    	}
				    	$("#status").val(statusText);
				    	$("#status").data('status', rowData.status);
				    	$("#season").val(rowData.seasonId);
				    	$('#last_round').val(rowData.lastRoundName);
				    	$('#last_round').data('last_round_id', rowData.lastRoundId);
						$('#round_detail_modal_confirm').data('round_id', id);
					}
					$('#round_detail_modal').modal('show');
				} else {
					var message = '获取赛季信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		},
		saveRoundConfirm: function () {
			var id = $('#round_detail_modal_confirm').data('round_id');
			var name = $('#name').val();
		    var time = $('#timestamp').val();
		    var memo = $('#memo').val();
		    var status = $('#status').data('status');
		    var season = $('#season').val();
		    var lastRoundId = $('#last_round').data('last_round_id');
		    
			var postData = 'round.id=' + id;
			postData += '&round.name=' + name;
			postData += '&round.timestamp=' + time;
			postData += '&round.memo=' + memo;
			postData += '&round.status=' + status;
			postData += '&round.seasonId=' + season;
			postData += '&round.lastPeriodId=' + lastRoundId;
	
			o.basePath && $.post(o.basePath + '/period/saveRound.action?rand=' + Math.random(), postData, function(retObj,textStatus, jqXHR) {
	    		if(retObj.result == true)
				{
					o.basePath && $('#round_main_table').DataTable().ajax.reload();
					var message = '保存场次信息成功!';
					$.ACHILLES.tipMessage(message);
				} else {
					var message = '保存场次信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		},
		roundConfirmModalConfirm: function () {
			// 1 -- delRoundsConfirm()
			// 2 -- archiveRoundConfirm()
			var type = $('#round_confirm_modal_confirm').data('type');
			if(type === 1) {
				var postData = '';
				postData = $('#round_confirm_modal_confirm').data('delIds');
				$.ACHILLES.round.delRoundsConfirm(postData);
			}
			else if(type === 2) {
				var roundId = $('#round_confirm_modal_confirm').data('round_id');
				$.ACHILLES.round.archiveRoundConfirm(roundId);
			}
			else {
				$.ACHILLES.tipMessage("没有找到合适的处理流程，请联系系统维护人员!", false);
			}
		},
		batchDelRound: function () {
			if( ($('#round_main_table :checkbox:checked[data-id]').length == 0) ) { 
				var message = "请选择要删除的场次!";
				$.ACHILLES.tipMessage(message);
				return;
			} else {
				var delIds = '';
				$("#round_main_table :checkbox").each(function(index,checkboxItem){
					if($(checkboxItem).prop('checked') && index != 0){
						delIds += "delIds=" + $(checkboxItem).attr('data-id') + "&";
					}
				});
				var message = '已经选取了' + $("#round_main_table :checkbox:checked[data-id]").length + '条记录。是否要删除这些场次？';
				$('#round_confirm_modal_message').empty().append(message);
				$('#round_confirm_modal_confirm').data('type', 1);
				$('#round_confirm_modal_confirm').data('delIds', delIds);
				$("#round_confirm_modal").modal('show');
			}
		},
		delRound: function () {
			var rowId = $(this).data('id');
			var delIds = 'delIds=' + rowId;
			var message = '是否要删除此场次？';
			$('#round_confirm_modal_message').empty().append(message);
			$('#round_confirm_modal_confirm').data('type', 1);
			$('#round_confirm_modal_confirm').data('delIds', delIds);
			$("#round_confirm_modal").modal('show');
		},
		delRoundsConfirm: function (postData) {
			o.basePath && $.post(o.basePath + '/period/deleteRounds.action', postData, function(retObj) {
				if(retObj.result == true) {
					var message = '场次信息已删除';
					$.ACHILLES.tipMessage(message);
					$('#round_main_table').DataTable().ajax.reload();
				} else {
					var message = '删除场次信息操作失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
				$("#round_main_table :checkbox").each(function(index,checkboxItem){
					$(checkboxItem).iCheck('uncheck');
				});
			}, 'json');
		},
		archiveRound: function () {
			var rowId = $(this).data('id');
			var message = '执行归档操作后，不可再改变本轮比赛的所有对战结果，是否继续归档？';
			$('#round_confirm_modal_message').empty().append(message);
			$('#round_confirm_modal_confirm').data('type', 2);
			$('#round_confirm_modal_confirm').data('round_id', rowId);
			$("#round_confirm_modal").modal('show');
		},
		archiveRoundConfirm: function (roundId) {
			$("#progress_Modal").modal('show');
			var postData = 'roundId=' + roundId;
			o.basePath && $.post(o.basePath + "/period/archiveRound.action", postData, function(retObj) {
				$("#progress_Modal").modal('hide');
				if(retObj.result == true) {
					var message = "本轮比赛数据已归档完成!<br/>接下来可以创建新一轮比赛，或者查看上一轮的排行榜";
					$.ACHILLES.tipMessage(message);
					o.basePath && $('#round_main_table').DataTable().ajax.reload();
				} else {
					var message = "归档比赛数据操作失败![" + retObj.message + "]";
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		}
	};// end of $.ACHILLES.round
	
	/* player
	* ======
	* player infomation maintain page
	*
	* @type Object
	* @usage $.ACHILLES.player.activate()
	* @usage $.ACHILLES.player.queryPlayers()
	* @usage $.ACHILLES.player.clearQueryPlayerCondition()
	* @usage $.ACHILLES.player.openAddWindow()
	* @usage $.ACHILLES.player.addConfirm()
	* @usage $.ACHILLES.player.detail()
	* @usage $.ACHILLES.player.delBatch()
	* @usage $.ACHILLES.player.del()
	* @usage $.ACHILLES.player.delConfirm()
	* @usage $.ACHILLES.player.testBatchCreatePlayers()
	*/
	$.ACHILLES.player = {
		activate: function () {
			o.basePath && $('#player_main_table').DataTable( {
				ajax:{
					url: o.basePath + '/player/queryPlayers.action',
					data: function(d) {
						var loginid = $('#player_query_id').val();
				    	var name = $('#player_query_name').val();
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
							var text = '';
							if(data === 'T') {
								text = '人类';
							}
							else if(data === 'P') {
								text = '星灵';
							}
							else if(data === 'Z') {
								text = '异虫';
							}
							return text;
						},
						targets: 3
					},
					{
						render: function ( data, type, row ) {
							var html = '<div class="btn-group">';
							html += '<button class="player_info btn btn-xs btn-success" data-id="' + row.id + '"><i class="fa fa-check"></i>详情</button>';
							html += '<button class="player_del btn btn-xs btn-danger" data-id="' + row.id + '"><i class="fa fa-check"></i>删除</button>';
							html += '</div>';
							return html;
						},
						targets: 5
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
			$('#add_player_confirm').on('click.ACHILLES.player.addconfirm', $.ACHILLES.player.addConfirm);
			$('#del_players').on('click.ACHILLES.player.delete.batch', $.ACHILLES.player.delBatch);
			$('#player_main_table').on( 'draw.dt', function () {
				$('.player_info').on('click.ACHILLES.player.detail', $.ACHILLES.player.detail);
				$('.player_del').on('click.ACHILLES.player.delete.single', $.ACHILLES.player.del);
			});
			$('#del_players_confirm').on('click.ACHILLES.player.delconfirm', $.ACHILLES.player.delConfirm);
			
			$('#batch_create_players').on('click.ACHILLES.player.batchcreate', $.ACHILLES.player.testBatchCreatePlayers);
			$('#test_init_player_confirm').on('click.ACHILLES.player.batchcreate', $.ACHILLES.player.testBatchCreatePlayersConfirm);
		},
		queryPlayers: function () {
			o.basePath && $('#player_main_table').DataTable().ajax.reload();
		},
		clearQueryPlayerCondition: function () {
			$('#player_query_id').val('');
			$('#player_query_name').val('');
		},
		openAddWindow: function () {
			$('#pwd').val('');
		    $('#name').val('');
		    $('#race').val('');
		    $('#telephone').val('');
		    $('#email').val('');
		    $('#qq').val('');
		    $('#wechat').val('');

			$("#add_Modal").modal('show');
		},
		addConfirm: function () {
			var loginId = $("#loginid").val();
			var postData = "loginId=" + loginId;
			
			o.basePath && $.post(o.basePath + '/player/queryPlayers.action', postData, function(retObj){
		        if(retObj.items.length == 0){ 
		        	postData = 'player.loginId=' + loginId;
		        	var pwd = $('#pwd').val();
		        	postData += '&player.pwd=' + pwd;
					var name = $('#name').val();
					postData += '&player.name=' + name;
					var race = $('#race').val();
					postData += '&player.race=' + race;
					var telephone = $('#telephone').val();
					postData += '&player.tel=' + telephone;
					var email = $('#email').val();
					postData += '&player.email=' + email;
					var qq = $('#qq').val();
					postData += '&player.qq=' + qq;
			    	var wechat = $('#wechat').val();
					postData += '&player.wechat=' + wechat;
					
			    	$.post(o.basePath + '/player/savePlayer.action?rand=' + Math.random(), postData, function(retObj,textStatus, jqXHR) {
			    		if(retObj.result == true)
						{
							$.ACHILLES.player.queryPlayers();
							var message = '保存选手信息成功!';
							$.ACHILLES.tipMessage(message);
						} else {
							var message = '保存选手信息失败![' + retObj.message + ']';
							$.ACHILLES.tipMessage(message, false);
						}
					}, 'json');
		        } else { 
					var message = '该账号已被占用!';
					$.ACHILLES.tipMessage(message, false);
		            return false;
		        } 
		        
		    }, "json");
		},
		detail: function () {
			
			var rowId = $(this).data('id');
			var postData = "";
			postData += "id=" + rowId;
			o.basePath && $.post(o.basePath + "/player/queryPlayerDetail.action", postData, function(retObj) {
				if(retObj.result == true) {
					//TODO: update detail page info
					var info = retObj.detail;
					var message = info.base.loginId + '|' + info.base.name + '|' + info.base.race;
					$('#detail_message').empty().append(message);
					
					$("#detail_Modal").modal('show');
				} else {
					var message = '获取选手详细信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
			return;
		},
		delBatch: function () {
			if( ($('#player_main_table :checkbox:checked[data-id]').length == 0) ) { 
				var message = "请选择要删除的选手!";
				$.ACHILLES.tipMessage(message);
				return;
			} else {
				var delIds = '';
				$("#player_main_table :checkbox").each(function(index,checkboxItem){
					if($(checkboxItem).prop('checked') && index != 0){
						delIds += "delIds=" + $(checkboxItem).attr('data-id') + "&";
					}
				});
				var message = '已经选取了' + $("#player_main_table :checkbox:checked[data-id]").length + '条记录。是否要删除这些选手？';
				$('#del_message').empty().append(message);
				$('#del_message').data('delIds', delIds);
				$("#del_Modal").modal('show');
			}
		},
		del: function () {
			var rowId = $(this).data('id');
			var delIds = 'delIds=' + rowId;
			var message = '是否要删除该选手？';
			$('#del_message').empty().append(message);
			$('#del_message').data('delIds', delIds);
			$("#del_Modal").modal('show');
		},
		delConfirm: function () {
			var postData = '';
			postData = $('#del_message').data('delIds');
			o.basePath && $.post(o.basePath + '/player/deletePlayer.action', postData, function(retObj) {
				if(retObj.result == true) {
					var message = '选手已经被删除';
					$.ACHILLES.tipMessage(message);
					$('#player_main_table').DataTable().ajax.reload();
				} else {
					var message = '删除选手操作失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		},
		testBatchCreatePlayers: function () {
			var message = '该操作会在系统现有用户基础上，批量创建新用户，直到系统中存在50个用户为止。<br/>如果系统中已有50个用户，将不会做任何操作，是否继续？';
			$('#test_init_player_message').empty().append(message);
			$("#test_Init_Player_Modal").modal('show');
		},
		testBatchCreatePlayersConfirm: function () {
			$("#progress_Modal").modal('show');
			o.basePath && $.post(o.basePath + '/player/testInitPlayers.action', {}, function(retObj){
				$("#progress_Modal").modal('hide');
		        if(retObj.result == true)
				{
					$.ACHILLES.player.queryPlayers();
					var message = '生成测试选手数据成功!';
					$.ACHILLES.tipMessage(message);
				} else {
					var message = '生成测试选手数据失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
		    }, "json");
		}
	};// end of $.ACHILLES.player
	
	/* match
	* ======
	* match info maintain page for battle_maintain.html
	*
	* @type Object
	* @usage $.ACHILLES.match.activate()
	* @usage $.ACHILLES.match.queryAttestation()
	* @usage $.ACHILLES.match.clearQueryAttestationCondition()
	* @usage $.ACHILLES.match.openAddAttestationWindow()
	* @usage $.ACHILLES.match.openModAttestationWindow()
	* @usage $.ACHILLES.match.openDelAttestationWindow()
	* @usage $.ACHILLES.match.attestationDel()
	* @usage $.ACHILLES.match.attestationSave()
	* @usage $.ACHILLES.match.attestationDetailReturn()
	*/
	$.ACHILLES.match = {
		activate: function () {
			o.basePath && $('#match_player_registration_info_table').DataTable( {
				ajax:{
					url: o.basePath + '/match/queryActiveMatchRegistrationInfo.action',
					type: 'POST',
					dataSrc: 'activeRegistrationInfo'
				},
				processing: true,
				serverSide: true,
				dom: 'tr',
				columns: [
					{ data: 'playerId' },
					{ data: null},
					{ data: 'loginId' },
					{ data: 'name' },
					{ data: 'race' },
					{ data: 'adversaries' },
					{ data: 'days' }
				],
				rowId: 'playerId',
				columnDefs: [
					{
						visible: false,
		                targets: 0
					},
					{
						render: function ( data, type, row ) {
							var text = '';
							if(data === 'T') {
								text = '人类';
							}
							else if(data === 'P') {
								text = '星灵';
							}
							else if(data === 'Z') {
								text = '异虫';
							}
							return text;
						},
						targets: 4
					},
					{
						render: function ( data, type, row ) {
							var html = '<div class="btn-group">';
							html += '<button class="edit_match_registration btn btn-xs btn-success" data-id="' + row.playerId + '"><i class="fa fa-edit"></i>编辑</button>';
							html += '</div>';
							return html;
						},
						targets: 1
					}
				],
				createdRow: function ( row, data, index ) {
					//$('td', row).eq(0).addClass('text-center');
				}
			});
			
			$.ACHILLES.match.queryActiveMatchInfo();
			//listen page items' event
			$('#batch_create_registration').on('click.ACHILLES.match.batchcreateregistration', $.ACHILLES.match.testBatchCreateRegistration);
			$('#batch_create_match_result').on('click.ACHILLES.match.batchcreatematchresult', $.ACHILLES.match.testBatchCreateMatchResult);
			$('#match_player_registration_info_table').on( 'draw.dt', function () {
				$('.edit_match_registration').on('click.ACHILLES.match.registration.edit', $.ACHILLES.match.editMatchRegistration);
			});
			$('#match_registration_detail_confirm').on('click.ACHILLES.match.editmatchregistrationconfirm', $.ACHILLES.match.editMatchRegistrationConfirm);
			$('#match_info_detail_confirm').on('click.ACHILLES.match.editmatchinfoconfirm', $.ACHILLES.match.editMatchInfoConfirm);
			$('#match_confirm_modal_confirm').on('click.ACHILLES.match.confirmmodalconfirm', $.ACHILLES.match.matchConfirmModalConfirm);
			$('#update_match_info').on('click.ACHILLES.match.updatematchinfo', $.ACHILLES.match.updateMatchInfo);
			$('#archive_match_info').on('click.ACHILLES.match.archivematchinfo', $.ACHILLES.match.archiveMatchInfo);
		},
		testBatchCreateRegistration: function () {
			$("#progress_Modal").modal('show');
			o.basePath && $.post(o.basePath + "/match/testInitRegistration.action", {}, function(retObj) {
				$("#progress_Modal").modal('hide');
				if(retObj.result == true) {
					var message = "批量录入模拟挑战申请完成!";
					$.ACHILLES.tipMessage(message);
					
					o.basePath && $('#match_player_registration_info_table').DataTable().ajax.reload();
				} else {
					var message = "录入模拟挑战申请失败![" + retObj.message + "]";
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		},
		testBatchCreateMatchResult: function () {
			$("#progress_Modal").modal('show');
			o.basePath && $.post(o.basePath + "/match/testCreateMatchResult.action", {}, function(retObj) {
				$("#progress_Modal").modal('hide');
				if(retObj.result == true) {
					var message = "批量创建模拟比赛结果完成!";
					$.ACHILLES.tipMessage(message);
					$.ACHILLES.match.queryActiveMatchInfo();
				} else {
					var message = "创建模拟比赛结果失败![" + retObj.message + "]";
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		},
		editMatchRegistration: function () {
			var playerId = $(this).data('id');
			var rowData = $('#match_player_registration_info_table').DataTable().row( '#' + playerId ).data();
			//console.log(rowData);

			var postData = "playerId=" + playerId; 
			o.basePath && $.post(o.basePath + "/match/queryMatchDetailForEdit.action", postData, function(retObj) {
				if(retObj.result == true) {
					$('#match_registration_detail_player_name').html(rowData.name + ' -- ' + rowData.loginId);
					var html = '';
					var optionStr = '';
					// 对战选手
					html += '<div class="form-group">';
					html += '<label>选择挑战对手</label>'
					html += '<ul class="list-group list-group-unbordered">';
					retObj.regInfoForEdit.adversaries.forEach(function(player, index){
						html += '<li class="list-group-item">';
						html += '<select id="adversary' + index + '" class="form-control"></select>';
						optionStr += '<option value="' + player.id + '">第' + player.ranking + '名 - ' + player.name + ' - ' + player.loginId + '</option>';
					});
					html += '</ul></div>';
					
					
					// 对战日期
					html += '<div class="form-group"><label for="exampleInputEmail1">选择对战日期</label>';
					html += '<ul class="list-group list-group-unbordered"><li class="list-group-item">';
					html += '<span class="margin"><input id="week0" type="checkbox" value="0"><label for="monday">星期一</label></span>';
					html += '<span class="margin"><input id="week1" type="checkbox" value="1"><label for="tuesday">星期二</label></span>';
					html += '<span class="margin"><input id="week2" type="checkbox" value="2"><label for="wednesday">星期三</label></span>';
					html += '<span class="margin"><input id="week3" type="checkbox" value="3"><label for="thusday">星期四</label></span>';
					html += '<span class="margin"><input id="week4" type="checkbox" value="4"><label for="friday">星期五</label></span>';
					html += '<span class="margin"><input id="week5" type="checkbox" value="5"><label for="saturday">星期六</label></span>';
					html += '<span class="margin"><input id="week6" type="checkbox" value="6"><label for="sunday">星期日</label></span>';
					html += '</li></ul></div>';
					
					// 本轮奖惩
					html += '<div class="form-group"><label for="exampleInputEmail1">本轮独立奖惩分数</label><ul class="list-group list-group-unbordered"><li class="list-group-item">';
					html += '<p id="reward_display">当前分数: <span id="reward_display_val">' + rowData.scoreReward + '</span></p><input id="reward_sponsor" type="text" /></li>';
					html += '<li class="list-group-item"><textarea class="form-control" rows="7" id="reward_memo" placeholder="奖惩原因"></textarea></li></ul></div>';
					
					$("#match_registration_detail_modal_body").empty().append(html);
					$('#adversary0, #adversary1, #adversary2, #adversary3, #adversary4').append( optionStr );
					$('#adversary0, #adversary1, #adversary2, #adversary3, #adversary4').each(function(){
						$(this).get(0).selectedIndex = -1;
					});
					rowData.adversaryIds.forEach(function(adversaryId, index){
						$('#adversary'+ index).val(adversaryId);
					});
					
					$('#week0, #week1, #week2, #week3, #week4, #week5, #week6').iCheck({
					    checkboxClass: 'icheckbox_square-blue margin',
						radioClass: 'iradio_square-blue margin',
					    increaseArea: '20%' // optional
					});
					// for rest day
					$('#week0').iCheck('disable');
					$('#week0').parent().next().addClass('text-gray');
					rowData.dayIds.forEach(function(dayId){
						$('#week'+ dayId).iCheck('check');
					});
					  
					$('#reward_sponsor').slider({ 
						id: 'reward_sponsor_slider', 
						ticks: [-100, -50, 0, 50, 100],
						min: -100, 
						max: 100, 
						value: -101
					});
					$('#reward_sponsor').on('change', function(slideEvt) {
						var colorUnselect = '#f9f9f9';
						var colorGreen = '#00a65a';
						var colorRed = '#f56954';
						var colorDefault = '#89cdef';
						$('#reward_sponsor_slider .slider-selection, #reward_sponsor_slider .slider-track-high, #reward_sponsor_slider .slider-tick').css('background', colorUnselect);
						
						if(slideEvt.value.newValue > 0) {
							$('#reward_sponsor_slider .slider-selection, #reward_sponsor_slider .slider-tick.in-selection').css('background', colorGreen);
						}
						else if(slideEvt.value.newValue < 0) {
							$('#reward_sponsor_slider .slider-track-high, #reward_sponsor_slider .slider-tick').css('background', colorRed);
							$('#reward_sponsor_slider .slider-tick.in-selection').css('background', colorUnselect);
						}
						else if(slideEvt.value.newValue == 0) {
							//pass
						}
						$('#reward_display_val').text(slideEvt.value.newValue);
					});
					// for the first value display in correct way
					$('#reward_sponsor').slider('setValue', rowData.scoreReward, true, true);
					$('#reward_memo').val(rowData.scoreRewardMemo);
					
					$('#match_registration_detail_confirm').data('reg_info', rowData);
					$('#match_registration_detail_modal').modal('show');
				} else {
					var message = "获取选手详细报名信息失败![" + retObj.message + "]";
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		},
		editMatchRegistrationConfirm: function (regInfo) {
			var regInfo = $('#match_registration_detail_confirm').data('reg_info');
			
			regInfo.adversaryIds = [];
			$('#adversary0, #adversary1, #adversary2, #adversary3, #adversary4').each(function(index, adversary){
				var self = $(this);
				if( self.val() != null ) {
					regInfo.adversaryIds.push(self.val());
				}
			});
				
			regInfo.dayIds= [];
			$('#week0, #week1, #week2, #week3, #week4, #week5, #week6').each(function(index, day){
				var self = $(this);
				if(self.prop('checked')) {
					var dayId = self.val();
					regInfo.dayIds.push(dayId);
				}
			});
			
			regInfo.scoreReward = $('#reward_sponsor').slider('getValue');
			regInfo.scoreRewardMemo = $('#reward_memo').val();
			//console.log(regInfo);
			
			var postData = 'regInfoForSave.playerId=' + regInfo.playerId;
			postData += '&regInfoForSave.scoreReward=' + regInfo.scoreReward;
			postData += '&regInfoForSave.scoreRewardMemo=' + regInfo.scoreRewardMemo;
			regInfo.adversaryIds.forEach(function(adversaryId){
				postData += '&regInfoForSave.adversaryIds=' + adversaryId;
			});
			regInfo.dayIds.forEach(function(dayId){
				postData += '&regInfoForSave.dayIds=' + dayId;
			});
			o.basePath && $.post(o.basePath + "/match/saveMatchDetail.action", postData, function(retObj) {
				if(retObj.result == true) {
					var message = "选手报名信息更新成功!";
					$.ACHILLES.tipMessage(message);
					
					o.basePath && $('#match_player_registration_info_table').DataTable().ajax.reload();
				} else {
					var message = "选手报名信息更新失败! " + retObj.message;
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
			return;
		},
 		queryActiveMatchInfo: function () {
			o.basePath && $.post(o.basePath + "/match/queryActiveMatchInfo.action", {}, function(retObj) {
				if(retObj.result == true) {
					$('#match_day_info_table_wapper').html('');
					var matchInfos = retObj.activeMatchInfo;
					var index=0, len=matchInfos.length;
					for (; index<len; index++) {
						var matchInfo = matchInfos[index];
						var html = '<div class="box"><div class="box-header"><h3 class="box-title">' + matchInfo.dayName + '</h3></div><div class="box-body no-padding"><table id="active_match_info_day_' 
								+ index + '" class="table table-striped table-hover"></table></div></div>';
						$('#match_day_info_table_wapper').append(html);
						
						$('#active_match_info_day_' + index).DataTable( {
							paging: false,
					        data: matchInfo.matchInfo,
					        rowId: 'id',
					        columns: [
					        	{ title: "操作", data: null, defaultContent: "", width: "100px" },
					            { title: "挑战者", data: "challengerName", width: "200px" },
					            { title: "擂主", data: "adversaryName", width: "200px" },
					            { title: "结果", data: "result" },
					            { title: "比分", data: "score" }					            
					        ],
					        columnDefs: [
					        	{
									render: function ( data, type, row ) {
										var html = '';
										if(data === 1) {
											html = '<span class="winner fa fa-flag">' + row.challengerName + '</span><span class="looser fa fa-bomb">' + row.adversaryName + '</span>';
										}
										else if(data === 2) {
											html = '<span class="looser fa fa-bomb">' + row.challengerName + '</span><span class="winner fa fa-flag">' + row.adversaryName + '</span>';
										}
										else if(data === 3) {
											html = '平局';
										}
										else if(data === 4) {
											html = '<span class="looser fa fa-bomb">' + row.challengerName + ' 缺席</span>';
										}
										else if(data === 5) {
											html = '<span class="looser fa fa-bomb">' + row.adversaryName + ' 缺席</span>';
										}
										return html;
									},
									targets: 3
								},
					        	{
									render: function ( data, type, row ) {
										var html = '<div class="btn-group">';
										html += '<button class="edit_match_info btn btn-xs btn-success" data-id="' + row.id + '" data-index="' + index + '"><i class="fa fa-edit"></i>编辑</button>';
										html += '</div>';
										return html;
									},
									targets: 0
								}
							]
					    });
					    
					    $('#active_match_info_day_' + index).on( 'draw.dt', function () {
							$('.edit_match_info').on('click.ACHILLES.match.info.edit', $.ACHILLES.match.editMatchInfo);
						});
					}
					
				} else {
					var message = '查询对战信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		},
		editMatchInfo: function () {
			var matchInfoId = $(this).data('id');
			var tableIndex = $(this).data('index');
			var rowData = $('#active_match_info_day_' + tableIndex).DataTable().row( '#' + matchInfoId ).data();
			
			$('#cw, #aw, #draw, #ca, #aa').iCheck({
			    checkboxClass: 'icheckbox_square-blue margin',
				radioClass: 'iradio_square-blue margin',
			    increaseArea: '20%' // optional
			});
			$('#cw, #aw, #draw, #ca, #aa').on('ifChecked', function(event){
				var id = $(this).attr('id');
				if( id === 'aa' || id === 'ca' ) {
					$('#match_score').get(0).selectedIndex = -1;
					$('#match_score').attr('disabled', 'disabled');
				}
				else {
					$('#match_score').removeAttr('disabled');
				}
			});
			
			var battleResult = rowData.result;
			$('#cw, #aw, #draw, #ca, #aa').each(function(index, radio){
				var self = $(this);
				if(self.val() == battleResult) {
					self.iCheck('check');
				}
			});
			
			$('#match_score option').remove();
			$('#match_score').append('<option value="0:0">' + rowData.challengerName + ' 0:0 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="1:0">' + rowData.challengerName + ' 1:0 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="2:0">' + rowData.challengerName + ' 2:0 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="3:0">' + rowData.challengerName + ' 3:0 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="0:1">' + rowData.challengerName + ' 0:1 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="1:1">' + rowData.challengerName + ' 1:1 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="2:1">' + rowData.challengerName + ' 2:1 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="3:1">' + rowData.challengerName + ' 3:1 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="0:2">' + rowData.challengerName + ' 0:2 ' + rowData.adversaryName + '</option>');
          	$('#match_score').append('<option value="1:2">' + rowData.challengerName + ' 1:2 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="2:2">' + rowData.challengerName + ' 2:2 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="3:2">' + rowData.challengerName + ' 3:2 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="0:3">' + rowData.challengerName + ' 0:3 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="1:3">' + rowData.challengerName + ' 1:3 ' + rowData.adversaryName + '</option>');
          	//$('#match_score').append('<option value="2:3">' + rowData.challengerName + ' 2:3 ' + rowData.adversaryName + '</option>');
			$('#match_score').val(rowData.score);
			
			var title = rowData.challengerName + ' vs ' + rowData.adversaryName;
			$('#match_info_detail_confirm').data('match_info', rowData);
			$('#match_info_detail_confirm').data('table_index', tableIndex);
			$('#match_info_detail_player_name').html(title);
			$('#match_info_detail_modal').modal('show');
		},
		editMatchInfoConfirm: function () {
			var matchInfo = $('#match_info_detail_confirm').data('match_info');
			var tableIndex = $('#match_info_detail_confirm').data('table_index');
			
			var battleResult = 0;
			$('#cw, #aw, #draw, #ca, #aa').each(function(index, radio){
				var self = $(this);
				if(self.prop('checked')) {
					battleResult = self.val();
				}
			});
			var score = $('#match_score').val();
			
			var postData = 'matchInfoDetail.id=' + matchInfo.id;
			postData += '&matchInfoDetail.result=' + battleResult;
			postData += '&matchInfoDetail.score=' + score;
			o.basePath && $.post(o.basePath + '/match/saveMatchInfoDetail.action', postData, function(retObj) {
				if(retObj.result == true) {
					matchInfo.result = battleResult;
					//$('#active_match_info_day_' + tableIndex).DataTable().draw();
					$.ACHILLES.match.queryActiveMatchInfo();
					var message = '保存对战结果信息成功!';
					$.ACHILLES.tipMessage(message);
				} else {
					var message = '保存对战结果信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');
		},
		matchConfirmModalConfirm: function () {
			// 1 -- updateMatchInfoConfirm()
			var type = $('#match_confirm_modal_confirm').data('type');
			if(type === 1) {
				$.ACHILLES.match.updateMatchInfoConfirm();
			}
			else {
				$.ACHILLES.tipMessage("没有找到合适的处理流程，请联系系统维护人员!", false);
			}
		},		
		updateMatchInfo: function () {
			var message = '执行对战匹配操作后，会丢失现有对战相关结果，是否继续归档？';
			$('#match_confirm_modal_message').empty().append(message);
			$('#match_confirm_modal_confirm').data('type', 1);
			$("#match_confirm_modal").modal('show');
		},
		updateMatchInfoConfirm: function () {
			$("#progress_Modal").modal('show');
			o.basePath && $.post(o.basePath + "/match/updateMatchInfo.action", {}, function(retObj) {
				$("#progress_Modal").modal('hide');
				if(retObj.result == true) {
					var message = "根据最新报名结果匹配对战信息完成!";
					$.ACHILLES.tipMessage(message);
					$.ACHILLES.match.queryActiveMatchInfo();
					//o.basePath && $('#match_day_info_table').DataTable().ajax.reload();
				} else {
					var message = "匹配对战信息失败![" + retObj.message + "]";
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		}		
	};// end of $.ACHILLES.match
	
	/* score
	* ======
	* score infomation maintain page
	*
	* @type Object
	* @usage $.ACHILLES.score.activate()
	* @usage $.ACHILLES.score.save()
	* @usage $.ACHILLES.score.reset()
	*/
	$.ACHILLES.score = {
		activate: function () {
//			$.ACHILLES.score.reset();
			o.basePath && $.post(o.basePath + '/score/queryRoundList.action', {}, function(retObj){
				if(retObj.result == true) {
					var rounds = retObj.rounds;
					var htmlData = '<li class="time-label"><span class="bg-red">第一赛季</span></li>';
					rounds.forEach(function(round, index){
						htmlData += '<li><i class="fa fa-table bg-blue"></i>';
						htmlData +='<div class="timeline-item">';
						htmlData += '<span class="time"><i class="fa fa-clock-o"></i> ' + round.timestamp + '</span>';
						//htmlData += '<h3 class="timeline-header"><a data-toggle="collapse" data-target="#round' + index + '" href="#" ' + ( index === 0 ? '' : 'class="collapsed"' ) +  ' data-round_id="' + round.id + '">' + round.name + '</a></h3>';
						htmlData += '<h3 class="timeline-header"><a data-toggle="collapse" data-target="#round' + index + '" href="#" class="round-score collapsed" data-round_id="' + round.id + '">' + round.name + '</a></h3>';
						htmlData += '<div class="timeline-body">';
						htmlData += '<div class="box box-primary collapse " id="round' + index + '">';
						htmlData += '<div class="box-body no-padding"><table id="round_score_' + round.id + '" class="table table-striped table-hover"></table></div>';
						htmlData += '</div></div></div></li>';
					});
					
					htmlData += '<li><i class="fa fa-clock-o bg-gray"></i></li>';
					
					$('#score_list').html(htmlData);
					
					$('.round-score').on('click.ACHILLES.score.scoreload', $.ACHILLES.score.loadScore);
					
					$('.round-score').get(0).click();
				}
				else {
					var message = '获取比赛场次信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
		            return;
				}
			});
			//listen page items' event
//			$('#score_reset').on('click.ACHILLES.score.reset', $.ACHILLES.score.reset);
//			$('#score_save').on('click.ACHILLES.score.save', $.ACHILLES.score.save);
			
		},
		loadScore: function () {
			var roundTitle = $(this);
			var isLoaded = roundTitle.data('loaded');
			if(isLoaded) {
				return;
			}
			
			var roundId = roundTitle.data('round_id');
			var postData = 'roundId=' + roundId;
			o.basePath && $.post(o.basePath + '/score/queryRoundScore.action', postData, function(retObj){
	    		if(retObj.result == true)
				{
					$('#round_score_' + roundId).DataTable( {
						paging: false,
				        data: retObj.items,
				        rowId: 'id',
				        columns: [
				        	{ title: '操作', data: null, defaultContent: '', width: '100px' },
				        	{ title: '选手', data: 'playerName', width: '200px' },
				        	{ title: '排名', data: 'ranking' },
				            { title: '比分', data: 'score' }	,
				            { title: '挑战胜场', data: 'challengerWin', width: '200px' },
				            { title: '挑战败场', data: 'challengerLose', width: '200px' },
				            { title: '守擂胜场', data: 'adversaryWin', width: '200px' },
				            { title: '守擂败场', data: 'adversaryLose', width: '200px' },
				            { title: '是否缺席', data: 'absent', width: '200px' }					            				            
				        ],
				        columnDefs: [
				        	{
								render: function ( data, type, row ) {
									var html = '';
									if(data === 0) {
										if(row.challengerWin === 0 && row.challengerLose === 0 && row.adversaryWin === 0 && row.adversaryLose === 0) {
											html = '<span class="winner">本周未申请比赛</span>' ;
										}
										else {
											html = '<span class="looser">全部出席</span>' ;
										}
									}
									else if(data === 2) {
										html = '<span class="winner">存在缺席</span>' ;
									}
									return html;
								},
								targets: 8
							},
				        	{
								render: function ( data, type, row ) {
									var html = '<div class="btn-group">';
									html += '<button class="score_detail_info btn btn-xs btn-success" data-memo="' + row.memo + '"><i class="fa fa-edit"></i>积分详情</button>';
									html += '</div>';
									return html;
								},
								targets: 0
							}
						]
				    });
					
					$('#round_score_' + roundId).on( 'draw.dt', function () {
						$('.score_detail_info').on('click.ACHILLES.score.info.detail', $.ACHILLES.score.detailScoreInfo);
					});
					
					roundTitle.data('loaded', true);
				} else {
					var message = '获取比赛积分信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, 'json');			
		},
		detailScoreInfo: function () {
			var memo = $(this).data('memo');
			var strs=memo.split("],"); //字符分割 
			memo = '';
			var i=0;
			for (;i<strs.length ;i++ ) 
			{ 
				if(strs[i].length > 0) {
					memo += strs[i] + ']<br/>'; //分割后的字符输出
				}
			} 
			$('#detail_score_info_message').empty().append(memo);
			$('#detail_score_info_modal').modal('show');
		}
	};// end of $.ACHILLES.score
	
	/* config
	* ======
	* config infomation maintain page
	*
	* @type Object
	* @usage $.ACHILLES.config.activate()
	* @usage $.ACHILLES.config.save()
	* @usage $.ACHILLES.config.reset()
	*/
	$.ACHILLES.config = {
		activate: function () {
			$.ACHILLES.config.reset();
			
			//listen page items' event
			$('#config_reset').on('click.ACHILLES.config.reset', $.ACHILLES.config.reset);
			$('#config_save').on('click.ACHILLES.config.save', $.ACHILLES.config.save);
			
		},
		reset: function () {
			o.basePath && $.post(o.basePath + '/config/query.action', {}, function(retObj){
		        if(retObj.result == true) {
		        	var config = retObj.config;
		        	$('#max_challenge_count').val(config.maxChallengeCount);
		        	$('#max_players_count').val(config.maxPlayersCount);
		        	//$('#max_date_range').val(config.maxDateRange);
		        	$('#init_round_id').val(config.initRoundId);
		        	$('#max_init_top_one_score').val(config.maxInitTopOneScore);
		        	$('#init_score_diminishing_step').val(config.initScoreDiminishingStep);
		        	$('#first_player_accept_challenge_count').val(config.firstPlayerAcceptChallengeCount);
		        	$('#min_accept_challenge_count').val(config.minAcceptChallengeCount);
		        	
		        	$('#max_percent_of_challenger_win').val(config.maxPercentOfChallengerWin);
		        	$('#percent_of_challenger_win_diminishing_step').val(config.percentOfChallengerWinDiminishingStep);
		        	$('#rate_of_chosen_mondy_to_thursday').val(config.rateOfChosenMondyToThursday);
		        	$('#rate_of_chosen_saturday_to_sunday').val(config.rateOfChosenSaturdayToSunday);
		        } else { 
					var message = '加载系统配置失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
		            return false;
		        } 
			});
		},
		save: function () {
			var postData = 'config.maxChallengeCount=' + $('#max_challenge_count').val();
			postData += '&config.maxPlayersCount=' + $('#max_players_count').val();
			postData += '&config.maxDateRange=6';
			postData += '&config.initRoundId=' + $('#init_round_id').val();
		    postData += '&config.maxInitTopOneScore=' + $('#max_init_top_one_score').val();
		    postData += '&config.initScoreDiminishingStep=' + $('#init_score_diminishing_step').val();
			postData += '&config.firstPlayerAcceptChallengeCount=' + $('#first_player_accept_challenge_count').val();
			postData += '&config.minAcceptChallengeCount=' + $('#min_accept_challenge_count').val();
			postData += '&config.maxPercentOfChallengerWin=' + $('#max_percent_of_challenger_win').val();
		    postData += '&config.percentOfChallengerWinDiminishingStep=' + $('#percent_of_challenger_win_diminishing_step').val();
		    postData += '&config.rateOfChosenMondyToThursday=' + $('#rate_of_chosen_mondy_to_thursday').val();
		    postData += '&config.rateOfChosenSaturdayToSunday=' + $('#rate_of_chosen_saturday_to_sunday').val();
			
			o.basePath && $.post(o.basePath + '/config/save.action', postData, function(retObj){
	    		if(retObj.result == true)
				{
					var message = '保存配置信息成功!';
					$.ACHILLES.tipMessage(message);
				} else {
					var message = '保存配置信息失败![' + retObj.message + ']';
					$.ACHILLES.tipMessage(message, false);
				}
			}, "json");
		}
	};// end of $.ACHILLES.config
  
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
