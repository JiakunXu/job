myApp.onPageInit('user.job', function(page) {
			$$('form.ajax-submit.user-job-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-job-form').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									myApp.getCurrentView().router.back();
								});
					});

			$$('form.ajax-submit.user-job-form').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function user_job_deliver() {
	myApp.showIndicator();

	$$('#user/job/deliver').trigger("submit");
}
