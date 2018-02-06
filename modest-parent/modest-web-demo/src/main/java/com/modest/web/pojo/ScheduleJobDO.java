package com.modest.web.pojo;

import java.util.Date;

/**
 * Table: schedule_job
 */
public class ScheduleJobDO {

    /** 任务调度的参数key */
    public static final String JOB_PARAM_KEY    = "jobParam";

    /**
     * Table:     schedule_job
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * Table:     schedule_job
     * Column:    SCHEDULE_JOB_ID
     * Nullable:  true
     */
    private Long scheduleJobId;

    /**
     * Table:     schedule_job
     * Column:    JOB_NAME
     * Nullable:  true
     */
    private String jobName;

    /**
     * Table:     schedule_job
     * Column:    ALIAS_NAME
     * Nullable:  true
     */
    private String aliasName;

    /**
     * job执行类
     */
    private String jobClass;

    /**
     * Table:     schedule_job
     * Column:    JOB_GROUP
     * Nullable:  true
     */
    private String jobGroup;

    /**
     * Table:     schedule_job
     * Column:    JOB_TRIGGER
     * Nullable:  true
     */
    private String jobTrigger;

    /**
     * Table:     schedule_job
     * Column:    STATUS
     * Nullable:  true
     */
    /** 0/停止, 1/运行中 */
    private String status;

    /**
     * Table:     schedule_job
     * Column:    CRON_EXPRESSION
     * Nullable:  true
     */
    private String cronExpression;

    /**
     * Table:     schedule_job
     * Column:    IS_SYNC
     * Nullable:  true
     */
    private int isSync;

    /**
     * Table:     schedule_job
     * Column:    DESCRIPTION
     * Nullable:  true
     */
    private String description;

    /**
     * Table:     schedule_job
     * Column:    GMT_CREATE
     * Nullable:  true
     */
    private Date gmtCreate;
    private String gmtCreateStr;

    /**
     * Table:     schedule_job
     * Column:    GMT_MODIFY
     * Nullable:  true
     */
    private Date gmtModify;

    /**
     * 操作标识
     */
    private String keywords;

    /**
     * 天
     */
    private Integer dayOfMonth;

    /**
     * 时
     */
    private Integer hour;

    /**
     * 分
     */
    private Integer minute;

    /**
     * 秒
     */
    private Integer second;


    /**
     * 任务执行时间
     */
    private String jobExecTime;

    /**
     * 执行时间
     */
    private Date execTime;

    /**
     * 下次执行时间
     */
    private Date nextExecTime;

    /**
     * 定时任务参数
     */
    private String params;


    public void ScheduleJob() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Long scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName == null ? null : aliasName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobTrigger() {
        return jobTrigger;
    }

    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger == null ? null : jobTrigger.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGmtCreateStr() {
        return gmtCreateStr;
    }

    public void setGmtCreateStr(String gmtCreateStr) {
        this.gmtCreateStr = gmtCreateStr;
    }

    public String getJobExecTime() {
        return jobExecTime;
    }

    public void setJobExecTime(String jobExecTime) {
        this.jobExecTime = jobExecTime;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Date getExecTime() {
        return execTime;
    }

    public void setExecTime(Date execTime) {
        this.execTime = execTime;
    }

    public Date getNextExecTime() {
        return nextExecTime;
    }

    public void setNextExecTime(Date nextExecTime) {
        this.nextExecTime = nextExecTime;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}