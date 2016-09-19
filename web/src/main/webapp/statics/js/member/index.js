myApp.onPageInit('member.index', function(page) {
			member_index_stats();
		});

function member_index_stats() {
	$$.get(appUrl + '/user/job/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/user/job/deliver').html(stats[0]);
				$$('#member/index/user/job/revoke').html(stats[1]);
			});

	$$.get(appUrl + '/bookmark/stats.htm', {}, function(data) {
				$$('#member/index/bookmark').html(data);
			});

	$$.get(appUrl + '/job/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/job/publish').html(stats[0]);
				$$('#member/index/job/finish').html(stats[1]);
				$$('#member/index/job/revoke').html(stats[2]);
			});

	$$.get(appUrl + '/issue/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/issue/u').html(stats[0]);
				$$('#member/index/issue/e').html(stats[1]);
			});
}