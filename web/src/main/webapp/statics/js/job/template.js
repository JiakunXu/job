myApp.onPageInit('job.template', function(page) {
			$$('form.ajax-submit.job-template-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.job-template-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									myApp.getCurrentView().router.back({
												url : appUrl + "/job/list.htm",
												force : true,
												ignoreCache : true
											});
								});
					});

			$$('form.ajax-submit.job-template-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

		});

function job_template_publish() {
	myApp.showIndicator();

	$$('#job/template/publish').trigger("submit");
}