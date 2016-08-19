myApp.onPageInit('user.role', function(page) {
			$$('form.ajax-submit.user-role-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-role-form').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.load({
									url : xhr.responseText,
									ignoreCache : true,
									reload : true
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
