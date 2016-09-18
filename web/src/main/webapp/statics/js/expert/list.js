myApp.onPageInit('expert.list', function(page) {
			// 分类
			$$('.a47 .a3u').find('li').each(function() {
				$$(this).click(function() {
							if ($$(this).hasClass("a3x")) {
								return;
							}

							var jobCId = $$(this).data("jobcid");
							var url;
							if (jobCId === undefined) {
								url = appUrl + "/expert/list.htm"
							} else {
								url = appUrl + "/expert/list.htm?jobCId="
										+ jobCId;
							}
							myApp.getCurrentView().router.load({
										url : url,
										ignoreCache : true,
										reload : true
									});
						});
			});
		});