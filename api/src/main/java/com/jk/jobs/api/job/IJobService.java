package com.jk.jobs.api.job;

import java.util.List;

import com.jk.jobs.api.job.bo.Job;
import com.jk.jobs.api.user.bo.UserJob;
import com.jk.jobs.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IJobService {

	/**
	 * 待发布.
	 */
	String TO_PUBLISH = "topublish";

	/**
	 * 已发布.
	 */
	String PUBLISH = "publish";

	/**
	 * 已完成.
	 */
	String FINISH = "finish";

	/**
	 * 撤销.
	 */
	String REVOKE = "revoke";

	/**
	 * 删除.
	 */
	String DELETE = "delete";

	/**
	 * 
	 * @param job
	 * @return
	 */
	int getJobCount(Job job);

	/**
	 * 
	 * @param job
	 * @return
	 */
	List<Job> getJobList(Job job);

	/**
	 * 我发布的项目.
	 * 
	 * @param userId
	 * @return
	 */
	List<Job> getJobList(Long userId);

	/**
	 * 我投递的项目.
	 * 
	 * @param jobId
	 * @return
	 */
	List<Job> getJobList(String[] jobId);

	/**
	 * 项目明细.
	 * 
	 * @param jobId
	 * @return
	 */
	Job getJob(String jobId);

	/**
	 * 我的项目明细.
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	Job getJob(Long userId, String jobId);

	/**
	 * 发布.
	 * 
	 * @param userId
	 * @param job
	 * @return
	 */
	BooleanResult publish(Long userId, Job job);

	/**
	 * 修改.
	 * 
	 * @param userId
	 * @param job
	 * @return
	 */
	BooleanResult update(Long userId, Job job);

	/**
	 * 结束.
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult finish(Long userId, String jobId);

	/**
	 * 撤销.
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult revoke(Long userId, String jobId);

	/**
	 * 删除.
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult delete(Long userId, String jobId);

	/**
	 * 再次发布.
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	BooleanResult copy(Long userId, String jobId);

	// >>>>>>>>>>以下是项目相关简历<<<<<<<<<<

	/**
	 * 项目简历.
	 * 
	 * @param userId
	 * @param jobId
	 * @return
	 */
	List<UserJob> getUserList(Long userId, String jobId);

	/**
	 * 
	 * @param userId
	 * @param jobId
	 * @param userJobId
	 * @return
	 */
	UserJob detail(Long userId, String jobId, String userJobId);

	/**
	 * 忽略.
	 * 
	 * @param userId
	 * @param jobId
	 * @param userJobId
	 * @return
	 */
	BooleanResult ignore(Long userId, String jobId, String userJobId);

}
