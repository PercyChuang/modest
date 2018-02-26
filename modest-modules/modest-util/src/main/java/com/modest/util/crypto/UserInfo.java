package com.modest.util.crypto;

import java.util.Date;

/**
 * Table: user_info
 */
public class UserInfo {
    /**
     * Table:     user_info
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 用户id
     *
     * Table:     user_info
     * Column:    user_id
     * Nullable:  false
     */
    private String userId;

    /**
     * 用户 登录账号
     *
     * Table:     user_info
     * Column:    user_login_name
     * Nullable:  false
     */
    private String userLoginName;

    /**
     * 用户别名
     *
     * Table:     user_info
     * Column:    user_as
     * Nullable:  true
     */
    private String userAs;

    /**
     * 登录密码
     *
     * Table:     user_info
     * Column:    login_pwd
     * Nullable:  true
     */
    private String loginPwd;

    /**
     * 密码盐
     *
     * Table:     user_info
     * Column:    salt
     * Nullable:  true
     */
    private String salt;

    /**
     * 支付密码
     *
     * Table:     user_info
     * Column:    payment_pwd
     * Nullable:  true
     */
    private String paymentPwd;

    /**
     * 手机号码
     *
     * Table:     user_info
     * Column:    phone_number
     * Nullable:  true
     */
    private String phoneNumber;

    /**
     * 邮箱
     *
     * Table:     user_info
     * Column:    email
     * Nullable:  true
     */
    private String email;

    /**
     * 用户类型
     *
     * Table:     user_info
     * Column:    user_type
     * Nullable:  true
     */
    private String userType;

    /**
     * 状态
     *
     * Table:     user_info
     * Column:    status
     * Nullable:  true
     */
    private Integer status;

    /**
     * 创建时间
     *
     * Table:     user_info
     * Column:    create_date
     * Nullable:  true
     */
    private Date createDate;

    /**
     * 修改时间
     *
     * Table:     user_info
     * Column:    update_date
     * Nullable:  true
     */
    private Date updateDate;

    /**
     * 最后登录时间
     *
     * Table:     user_info
     * Column:    lase_logintime
     * Nullable:  true
     */
    private Date laseLogintime;

    /**
     * 是否实名认证
     *
     * Table:     user_info
     * Column:    is_certification
     * Nullable:  true
     */
    private Integer isCertification;

    /**
     * Table:     user_info
     * Column:    is_question
     * Nullable:  true
     */
    private Integer isQuestion;

    /**
     * 是否修改支付密码(0/1)
     *
     * Table:     user_info
     * Column:    is_payment_pwd
     * Nullable:  true
     */
    private Integer isPaymentPwd;

    public String getCredentialsSalt() {
        return userLoginName + salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName == null ? null : userLoginName.trim();
    }

    public String getUserAs() {
        return userAs;
    }

    public void setUserAs(String userAs) {
        this.userAs = userAs == null ? null : userAs.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getPaymentPwd() {
        return paymentPwd;
    }

    public void setPaymentPwd(String paymentPwd) {
        this.paymentPwd = paymentPwd == null ? null : paymentPwd.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getLaseLogintime() {
        return laseLogintime;
    }

    public void setLaseLogintime(Date laseLogintime) {
        this.laseLogintime = laseLogintime;
    }

    public Integer getIsCertification() {
        return isCertification;
    }

    public void setIsCertification(Integer isCertification) {
        this.isCertification = isCertification;
    }

    public Integer getIsQuestion() {
        return isQuestion;
    }

    public void setIsQuestion(Integer isQuestion) {
        this.isQuestion = isQuestion;
    }

    public Integer getIsPaymentPwd() {
        return isPaymentPwd;
    }

    public void setIsPaymentPwd(Integer isPaymentPwd) {
        this.isPaymentPwd = isPaymentPwd;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", userLoginName=").append(userLoginName);
        sb.append(", userAs=").append(userAs);
        sb.append(", loginPwd=").append(loginPwd);
        sb.append(", salt=").append(salt);
        sb.append(", paymentPwd=").append(paymentPwd);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", email=").append(email);
        sb.append(", userType=").append(userType);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", laseLogintime=").append(laseLogintime);
        sb.append(", isCertification=").append(isCertification);
        sb.append(", isQuestion=").append(isQuestion);
        sb.append(", isPaymentPwd=").append(isPaymentPwd);
        sb.append("]");
        return sb.toString();
    }
}