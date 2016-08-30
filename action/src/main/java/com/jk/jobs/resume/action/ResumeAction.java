package com.jk.jobs.resume.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jk.jobs.api.job.IJobCatService;
import com.jk.jobs.api.job.bo.JobCat;
import com.jk.jobs.api.resume.IResumeService;
import com.jk.jobs.api.resume.bo.Resume;
import com.jk.jobs.api.resume.bo.ResumeDetail;
import com.jk.jobs.api.resume.bo.ResumeJobCat;
import com.jk.jobs.framework.action.BaseAction;
import com.jk.jobs.framework.bo.BooleanResult;
import com.jk.jobs.framework.log.Logger4jCollection;
import com.jk.jobs.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ResumeAction extends BaseAction {

	private static final long serialVersionUID = -1350198812498987637L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(ResumeAction.class);

	@Resource
	private IResumeService resumeService;

	@Resource
	private IJobCatService jobCatService;

	private List<Resume> resumeList;

	private String resumeId;

	private Resume resume;

	private List<JobCat> jobCatList;

	// TODO

	private String detailId;

	private String jobCId;

	private String cycle;

	private String content;

	/**
	 * 删除模块.
	 */
	private String jobCId0;

	/**
	 * 
	 * @return
	 */
	public String list() {
		// TODO
		// resumeList = resumeService.getResumeList(new Resume());

		return SUCCESS;
	}

	public String detail() {
		// TODO
		// resume = resumeService.getResume(resumeId);

		return SUCCESS;
	}

	public String my() {
		resume = resumeService.getResume(this.getUser().getUserId());

		return SUCCESS;
	}

	/**
	 * 修改.
	 * 
	 * @return
	 */
	public String edit() {
		resume = resumeService.getResume(this.getUser().getUserId());

		jobCatList = jobCatService.getJobCatList(new JobCat());

		return SUCCESS;
	}

	public String saveOrUpdate() {
		// TODO
		if (resume != null) {
			String[] detailIds = StringUtils.isNotBlank(detailId) ? detailId.split(",") : null;
			String[] jobCIds = StringUtils.isNotBlank(jobCId) ? jobCId.split(",") : null;
			String[] cycles = StringUtils.isNotBlank(cycle) ? cycle.split(",") : null;
			String[] contents = StringUtils.isNotBlank(content) ? content.split(",") : null;

			if (jobCIds != null && jobCIds.length > 0) {
				List<ResumeDetail> list = new ArrayList<ResumeDetail>();

				for (int i = 0; i < jobCIds.length; i++) {
					ResumeDetail resumeDetail = new ResumeDetail();

					String v0 = detailIds[i];
					if (StringUtils.isNotBlank(v0)) {
						try {
							resumeDetail.setDetailId(Long.valueOf(v0.trim()));
						} catch (NumberFormatException e) {
							logger.error(e);
						}
					}

					String v1 = jobCIds[i];
					if (StringUtils.isNotBlank(v1)) {
						try {
							resumeDetail.setJobCId(Long.valueOf(v1.trim()));
						} catch (NumberFormatException e) {
							logger.error(e);
						}
					}

					String v2 = cycles[i];
					if (StringUtils.isNotBlank(v2)) {
						try {
							resumeDetail.setCycle(Integer.parseInt(v2.trim()));
						} catch (NumberFormatException e) {
							logger.error(e);
						}
					}

					resumeDetail.setContent(contents[i]);

					list.add(resumeDetail);
				}

				resume.setResumeDetailList(list);
			}

			String[] jobCId0s = StringUtils.isNotBlank(jobCId0) ? jobCId0.split(",") : null;

			if (jobCId0s != null && jobCId0s.length > 0) {
				List<ResumeJobCat> list = new ArrayList<ResumeJobCat>();

				for (int i = 0; i < jobCId0s.length; i++) {
					String v0 = jobCId0s[i];
					if (StringUtils.isNotBlank(v0)) {
						ResumeJobCat resumeJobCat = new ResumeJobCat();

						try {
							resumeJobCat.setJobCId(Long.valueOf(v0.trim()));
							list.add(resumeJobCat);
						} catch (NumberFormatException e) {
							logger.error(e);
						}
					}
				}

				resume.setResumeJobCatList(list);
			}
		}

		BooleanResult result = resumeService.saveOrUpdate(this.getUser().getUserId(), resume);

		if (result.getResult()) {
			this.setResourceResult("发布成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 删除某一简历明细.
	 * 
	 * @return
	 */
	public String delete() {
		BooleanResult result = resumeService.delete(this.getUser().getUserId(), detailId);

		if (result.getResult()) {
			this.setResourceResult("删除成功");
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public List<Resume> getResumeList() {
		return resumeList;
	}

	public void setResumeList(List<Resume> resumeList) {
		this.resumeList = resumeList;
	}

	public String getResumeId() {
		return resumeId;
	}

	public void setResumeId(String resumeId) {
		this.resumeId = resumeId;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public List<JobCat> getJobCatList() {
		return jobCatList;
	}

	public void setJobCatList(List<JobCat> jobCatList) {
		this.jobCatList = jobCatList;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getJobCId() {
		return jobCId;
	}

	public void setJobCId(String jobCId) {
		this.jobCId = jobCId;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getJobCId0() {
		return jobCId0;
	}

	public void setJobCId0(String jobCId0) {
		this.jobCId0 = jobCId0;
	}

}
