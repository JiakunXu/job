myApp.onPageInit('user.role', function(page) {
			$$('form.ajax-submit.user-role-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-role-form').on('submitted', function(e) {
				myApp.hideIndicator();
				var xhr = e.detail.xhr;
				myApp.alert(xhr.responseText, '信息', function() {
							myApp.getCurrentView().router.back({
										url : appUrl
												+ "/pay/index.htm?tradeNo="
												+ $$('#deliver_time_tradeNo')
														.val(),
										force : true,
										ignoreCache : true
									});
						});
			});

			$$('form.ajax-submit.user-role-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function user_role_set() {
	myApp.showIndicator();

	$$('#user/role/set').trigger("submit");
}
