/********************************************************************************
 ******************************* 投票页面 js脚本 *********************************
 ********************************************************************************/

$(document).ready(function(){
	//手机号码录入框, 焦点获得, 离开, 按键up 事件
	$('#phone').keyup(function(){
		if(checkMobile($(this).val())){
			$('#btn-sendverifycode').removeClass('disabled');
		}else{
			$('#btn-sendverifycode').addClass('disabled');
		}
	}).focus(function(){
		if(checkMobile($(this).val())){
			$('#btn-sendverifycode').removeClass('disabled');
		}else{
			$('#btn-sendverifycode').addClass('disabled');
		}
	}).blur(function(){
		if(checkMobile($(this).val())){
			$('#btn-sendverifycode').removeClass('disabled');
		}else{
			$('#btn-sendverifycode').addClass('disabled');
		}
	});
	
	// 验证码输入框 焦点获得, 离开, 按键up 事件
	$('#verifyCode').keyup(function(){
		//只有返回的验证码不为空, 且和录入的验证码相同时, 投票按钮才可用!!!
		var confirmVerifyCode = $('#confirmVerifyCode').val();
		if(checkInteger($(this).val()) && checkCode($(this).val())){
			$('#btn-vote').removeClass('disabled');
		}else{
			$('#btn-vote').addClass('disabled');
		}
	}).focus(function(){
		if(checkInteger($(this).val()) && checkCode($(this).val())){
			$('#btn-vote').removeClass('disabled');
		}else{
			$('#btn-vote').addClass('disabled');
		}
	}).blur(function(){
		if(checkInteger($(this).val()) && checkCode($(this).val())){
			$('#btn-vote').removeClass('disabled');
		}else{
			$('#btn-vote').addClass('disabled');
		}
	});
	
	//发送验证码按钮点击事件
	$('#btn-sendverifycode').click(function(){
		//如果有disabled类样式 表示不可用, 则按钮不可用, 不予以相应相应时间
		if($(this).attr('class').indexOf('disabled') > 0){
		//	$('#notice-msg').html('请先填写正确格式的手机号码.');
			return false;
		}
		var phone = $('#phone').val();
		var projectno = $('#projectno').val();
		$.ajax({
			url:server.url + 'web/sendsms/' + projectno + '/' +phone,
			type:'post',
			dataType:'json',
			success:function(data){
				//发送成功...则将验证码填到隐藏域中.
				if(data.notice == 'success'){
					$('#confirmVerifyCode').val(data.code);
					//按钮马上不可用, 60秒后可用
					$('#btn-sendverifycode').addClass('disabled');
					var tButton = setTimeout(function(){
					//	alert("按钮可用啦");
						$('#btn-sendverifycode').removeClass('disabled');
					},60000);
					
					//提示文字出现, 并不停刷新, 并在60秒后消失
					$('#verifycodenotice').show();
					//刷新
					var iNotice = setInterval(function(){
						var count = $('#count').html();
						//倒计时为0时, 停止倒计时, 并隐
						if(count == 0 || count == '0'){
							clearInterval(iNotice);
							$('#verifycodenotice').hide();
							$('#count').html('60');
						}else{
							$('#count').html(count-1);
						}
					},1000);
				}else{
				//失败 则进行提示
					$('#notice-msg').html(data.notice);
				}
			}
		});
	});
	
	// 投票 按钮点击事件
	$('#btn-vote').click(function(){
		//如果有disabled类样式 表示不可用, 则按钮不可用, 不予以相应相应时间
		if($(this).attr('class').indexOf('disabled') > 0){
		//	$('#notice-msg').html('请先填写正确格式的手机号码.');
			return false;
		}
		
		var phone = $('#phone').val();
		var projectno = $('#projectno').val();
		$.ajax({
			url:server.url + 'web/vote/' + projectno + '/' +phone,
			type:'post',
			dataType:'json',
			success:function(data){
				//发送成功...则将验证码填到隐藏域中.
				if(data.notice == 'success'){
					$('#notice-msg').html('投票成功!');
					$('#btn-vote').addClass('disabled');
				}else{
				//失败 则进行提示
					$('#notice-msg').html(data.notice);
				}
			}
		});
	});
	
});

/**
 * 检查是否为手机号
 * @param s
 * @returns {Boolean}
 */
function checkMobile(str){
	var regu = /^[1][3|4|5|7|8][0-9]{9}$/;
	var re = new RegExp(regu);
	if (re.test(str)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 检查录入的数字是否为四位数字
 * @returns
 */
function checkInteger(str){
	var regu = /^[0-9]{4}$/;
	return regu.test(str);
};


/**
 * 验证输入的验证码, 和返回发送的验证码是否相同
 * @param code
 */
function checkCode(code){
	var confirmVerifyCode = $('#confirmVerifyCode').val();
	if(confirmVerifyCode != null && confirmVerifyCode != '' && confirmVerifyCode != undefined && confirmVerifyCode == code){
		return true;
	}else{
		return false;
	}
	
}

/**
 * 检查该手机号是否向当前项目投过票
 * @param phone
 */
function checkIsVoted(phone){
	var projectno = $('#projectno').val();
	$.ajax({
		url:server.url + 'web/checkvote/'+projectno + '/' + phone,
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.notice == 'success'){
				alert('操作成功!');
				//重新载入页面--以刷新
				window.location.reload();
				return;
			}else{
				alert(data.notice);
			}
		}
	});
}



