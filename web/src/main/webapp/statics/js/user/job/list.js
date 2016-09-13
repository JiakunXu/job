myApp.onPageInit('user.job.list', function(page) {
			// 下拉刷新页面
			var ptrContent = $$('.pull-to-refresh-content');

			// 添加'refresh'监听器
			ptrContent.on('refresh', function(e) {
						// 模拟1s的加载过程
						setTimeout(function() {
									// 列表元素的HTML字符串
									var itemHTML = '';
									// 前插新列表元素
									// ptrContent.find('.more').prepend(itemHTML);
									// 加载完毕需要重置
									myApp.pullToRefreshDone();
								}, 1000);
					});

			$$('form.ajax-submit.user-job-list-revoke').on('beforeSubmit',
					function(e) {
					});
			$$('form.ajax-submit.user-job-list-delete').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-job-list-revoke').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});
			$$('form.ajax-submit.user-job-list-delete').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.user-job-list-revoke').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
			$$('form.ajax-submit.user-job-list-delete').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function user_job_list_revoke(jobId) {
	myApp.confirm('确定撤销？', '简历管理', function() {
				myApp.showIndicator();

				$$('#user_job_list_revoke_jobId').val(jobId);
				$$('#user/job/list/revoke').trigger("submit");
			});
}

function user_job_list_delete(jobId) {
	myApp.confirm('确定删除？', '简历管理', function() {
				myApp.showIndicator();

				$$('#user_job_list_delete_jobId').val(jobId);
				$$('#user/job/list/delete').trigger("submit");
			});
}

function user_job_list_refresh() {
	myApp.getCurrentView().router.refreshPage();
}