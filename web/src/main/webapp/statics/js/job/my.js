myApp.onPageInit('job.my', function(page) {
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