myApp.onPageInit('job.my', function(page) {
			$$('form.ajax-submit.job-my-form').on('beforeSubmit', function(e) {
					});

			$$('form.ajax-submit.job-my-form').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.job-my-form').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

		});

function job_my_revoke(jobId) {
	myApp.confirm('确定撤销？', '项目管理', function() {
				myApp.showIndicator();

				$$('#job_my_jobId').val(jobId);
				$$('#job/my/revoke').trigger("submit");
			});
}