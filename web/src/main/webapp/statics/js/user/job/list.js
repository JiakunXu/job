myApp.onPageInit('user.job.list', function(page) {
			$$('form.ajax-submit.user-job-list-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-job-list-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.user-job-list-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function user_job_list_revoke(jobId) {
	myApp.confirm('确定撤销？', '简历管理', function() {
				myApp.showIndicator();

				$$('#user_job_list_jobId').val(jobId);
				$$('#user/job/list/revoke').trigger("submit");
			});
}