myApp.onPageInit('issue.list', function(page) {
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

			$$('form.ajax-submit.issue-list-revoke').on('beforeSubmit',
					function(e) {
					});
			$$('form.ajax-submit.issue-list-delete').on('beforeSubmit',
					function(e) {
					});
			$$('form.ajax-submit.issue-list-ignore').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.issue-list-revoke').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});
			$$('form.ajax-submit.issue-list-delete').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});
			$$('form.ajax-submit.issue-list-ignore').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.getCurrentView().router.refreshPage();
					});

			$$('form.ajax-submit.issue-list-revoke').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
			$$('form.ajax-submit.issue-list-delete').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
			$$('form.ajax-submit.issue-list-ignore').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function issue_list_refresh() {
	myApp.getCurrentView().router.refreshPage();
}

function issue_list_back(issueId, id) {
	var url = myApp.getCurrentView().history[myApp.getCurrentView().history.length
			- 2];
	if (id == '') {
		url = url + "&issueId=" + issueId;
	} else {
		url = url.replace("&issueId=" + id, "&issueId=" + issueId);
	}

	myApp.getCurrentView().router.back({
				url : url,
				force : true,
				ignoreCache : true
			});
}

function issue_list_revoke(issueId) {
	myApp.confirm('确定撤销？', '咨询管理', function() {
				myApp.showIndicator();

				$$('#issue_list_revoke_issueId').val(issueId);
				$$('#issue/list/revoke').trigger("submit");
			});
}

function issue_list_delete(issueId) {
	myApp.confirm('确定删除？', '咨询管理', function() {
				myApp.showIndicator();

				$$('#issue_list_delete_issueId').val(issueId);
				$$('#issue/list/delete').trigger("submit");
			});
}

function issue_list_ignore(issueId) {
	myApp.confirm('确定忽略？', '咨询管理', function() {
				myApp.showIndicator();

				$$('#issue_list_ignore_issueId').val(issueId);
				$$('#issue/list/ignore').trigger("submit");
			});
}
