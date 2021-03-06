package com.lmw.p2p.freamwork.base.response;

import com.lmw.p2p.freamwork.util.reps.BaseEntity;
import com.lmw.p2p.freamwork.util.WebUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lmw.p2p.freamwork.base.model.PlatCustomer;
 /**
 * 
 * 描述： 实体Bean
 * 
 * @创建人： PercyChuang
 * 
 * @创建时间：2018年04月16日 15:25:28
 * 
 * @Copyright (c) 深圳市利民网有限公司-版权所有
 */
public class PlatCustomerResponse extends BaseEntity {
	
	private static final long serialVersionUID = 7502750267552165579L;
	
	public PlatCustomerResponse() {

	}

	public PlatCustomerResponse(PlatCustomer platCustomer) {
		WebUtil.initObj(this, platCustomer);
	}
	
    /**
     *
     */
	private String id;
    /**
     *客户唯一标识:该字段具有唯一性
     */
	private String customerId;
    /**
     *客户名称
     */
	private String customerName;
    /**
     *真实姓名(PlatCustomerInfo.customer_surname)(冗余字段)
     */
	private String customerSurname;
    /**
     *手机
     */
	private String mobile;
    /**
     *邮箱
     */
	private String email;
    /**
     *登录密码
     */
	private String passwd;
    /**
     *交易密码:为六位数字加密密码
     */
	private String tradingPasswd;
    /**
     *手势密码
     */
	private String gesturePasswd;
    /**
     *
     */
	private String regTime;
    /**
     *推荐人id
     */
	private String recommender;
    /**
     *解锁时间
     */
	private String lockingTime;
    /**
     *用户推广码
     */
	private String scode;
    /**
     *认证步骤:[1:个人详细信息；2:工作认证；3:上传资料]
     */
	private String authStep;
    /**
     *组ID 1-员工，2-普通会员，3-推广渠道
     */
	private String groupId;
    /**
     *登录次数
     */
	private String loginCount;
    /**
     *法人类型(0待定，1表示个人用户，2表示企业用户，3平台用户)
     */
	private String legalType;
    /**
     *风险类型：保守型，稳健型，平衡型，成长型，进取型
     */
	private String riskType;
    /**
     *注册终端：1网站，2Android，3Ios，4Mobile
     */
	private String regChannel;
    /**
     *渠道来源(推广)(plat_generalize_merchant.begin_origin~plat_generalize_merchant.end_origin)
     */
	private String origin;
    /**
     *推广商家ID(plat_generalize_merchant.id)
     */
	private String gmId;
    /**
     *连连支付协议号
     */
	private String noAgree;
    /**
     *绑定的默认快捷银行卡
     */
	private String bankBindId;
    /**
     *用户类型（1普通用户，2为借款人，3担保机构，4结算用户，2000平台账户）
     */
	private String userType;
    /**
     *用户类型附加(1:普通借款人(需绑定结算人);2:独立借款人(借款人自行开通存管);
     */
	private String userTypeExtra;
    /**
     *存管用户角色：用户类型（1投资用户，2为借款人，3担保机构，4居间人，5合作机构，6供应商，2000平台总账户，2001平台代偿账户，2002平台营销款账户，2003平台分润账户，2004平台收入账户，2005平台派息账户，2006平台代充值账户,2007平台中转账户）
     */
	private String dpUserRole;
    /**
     *注册的链接路径
     */
	private String regUrl;
    /**
     *提现状态(默认1启动;2禁止)
     */
	private String cashStatus;
    /**
     *存管授权列表(TENDER=自动投标;REPAYMENT=自动还款;CREDIT_ASSIGNMENT=自动债权认购;COMPENSATORY=自动代偿;WITHDRAW=自动提现;RECHARGE=自动充值)。格式:逗号分隔，示例:TENDER,CREDIT_ASSIGNMENT
     */
	private String dpAuthList;
    /**
     *授权状态：0未授权；1已授权；
     */
	private String dpAuthStatus;
    /**
     *授权期限
     */
	private String dpAuthFailTime;
    /**
     *授权金额
     */
	private String dpAuthAmount;
    /**
     *创建时间
     */
	private String createTime;
    /**
     *存管审核状态：-5未成年；-1未开通；0待激活(存量用户迁移专用)；1审核中；2审核通过；3：审核回退；4审核拒绝
     */
	private String openDpStatus;
    /**
     *存管开通或激活时间
     */
	private String openDpTime;
    /**
     *开通存管证件类型:0身份证(PRC_ID);1护照(PASSPORT);2港澳台通行证(COMPATRIOTS_CARD);3外国人永久居留证(PERMANENT_RESIDENCE)
     */
	private String dpIdcardType;
    /**
     *更新时间
     */
	private String updateTime;
    /**
     *帐号状态0=禁用(不能登录，为冻结状态),1=启用(正常可登录)
     */
	private String enableStatus;
    /**
     *登录密码安全级别:0:弱;1:中;2:强
     */
	private String passwdSafeLevel;
    /**
     *APP分发渠道(谷歌市场，腾讯应用宝，百度手机助手)
     */
	private String appStore;
    /**
     *1.未审核，2.已审核，3.草稿，4.废弃
     */
	private String auditStatus;
    /**
     *交易密码解锁时间
     */
	private String lockingTradingTime;
    /**
     *是否遗留用户(0:否;1:是)，遗留用户即老系统的用户
     */
	private String isLegacyUser;
    /**
     *用户标签属性(0:新用户;1:之前实名正确老用户;2:之前实名不通过老用户)
     */
	private String userTag;
    /**
     *第三方用户名
     */
	private String thridUserName;
    /**
     *归属类型:0-真实账户，1-自用账户。
     */
	private String ownType;
    /**
     *业务类型:0-通用用户，1-车贷用户，2-易优货用户
     */
	private String busType;
    /**
     *法大大客户编号
     */
	private String fddCustomerId;
    /**
     *第三方用户id
     */
	private String thridUserId;
    /**
     *关联结算人(plat_customer.customer_id)
     */
	private String settlementId;
    /**
     *关联担保人(plat_customer.customer_id)
     */
	private String guarantecorpId;
    /**
     *关联商户(plat_customer.customer_id)
     */
	private String merchantId;
    /**
     *有没有续投弹框（,0-没有、1-续投过）
     */
	private String isContinuedFlag;
    /**
     *
     */
	private String regIp;
    /**
     *备注
     */
	private String remarks;
    /**
     *是否已清算：0未清算；1已清算
     */
	private String isLiquidate;
    /**
     *身份证号是否允许重复：0不可重复；1可重复
     */
	private String dpIdcardAllowRepeat;
    /**
     *最近一次密码更新时间
     */
	private String pwUpdateTime;


	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}
	
	public String getCustomerId(){
		return customerId;
	}
	
	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}
	
	public String getCustomerName(){
		return customerName;
	}
	
	public void setCustomerSurname(String customerSurname){
		this.customerSurname = customerSurname;
	}
	
	public String getCustomerSurname(){
		return customerSurname;
	}
	
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	public String getMobile(){
		return mobile;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	public String getPasswd(){
		return passwd;
	}
	
	public void setTradingPasswd(String tradingPasswd){
		this.tradingPasswd = tradingPasswd;
	}
	
	public String getTradingPasswd(){
		return tradingPasswd;
	}
	
	public void setGesturePasswd(String gesturePasswd){
		this.gesturePasswd = gesturePasswd;
	}
	
	public String getGesturePasswd(){
		return gesturePasswd;
	}
	
	public void setRegTime(String regTime){
		this.regTime = regTime;
	}
	
	public String getRegTime(){
		return regTime;
	}
	
	public void setRecommender(String recommender){
		this.recommender = recommender;
	}
	
	public String getRecommender(){
		return recommender;
	}
	
	public void setLockingTime(String lockingTime){
		this.lockingTime = lockingTime;
	}
	
	public String getLockingTime(){
		return lockingTime;
	}
	
	public void setScode(String scode){
		this.scode = scode;
	}
	
	public String getScode(){
		return scode;
	}
	
	public void setAuthStep(String authStep){
		this.authStep = authStep;
	}
	
	public String getAuthStep(){
		return authStep;
	}
	
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	
	public String getGroupId(){
		return groupId;
	}
	
	public void setLoginCount(String loginCount){
		this.loginCount = loginCount;
	}
	
	public String getLoginCount(){
		return loginCount;
	}
	
	public void setLegalType(String legalType){
		this.legalType = legalType;
	}
	
	public String getLegalType(){
		return legalType;
	}
	
	public void setRiskType(String riskType){
		this.riskType = riskType;
	}
	
	public String getRiskType(){
		return riskType;
	}
	
	public void setRegChannel(String regChannel){
		this.regChannel = regChannel;
	}
	
	public String getRegChannel(){
		return regChannel;
	}
	
	public void setOrigin(String origin){
		this.origin = origin;
	}
	
	public String getOrigin(){
		return origin;
	}
	
	public void setGmId(String gmId){
		this.gmId = gmId;
	}
	
	public String getGmId(){
		return gmId;
	}
	
	public void setNoAgree(String noAgree){
		this.noAgree = noAgree;
	}
	
	public String getNoAgree(){
		return noAgree;
	}
	
	public void setBankBindId(String bankBindId){
		this.bankBindId = bankBindId;
	}
	
	public String getBankBindId(){
		return bankBindId;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
	}
	
	public String getUserType(){
		return userType;
	}
	
	public void setUserTypeExtra(String userTypeExtra){
		this.userTypeExtra = userTypeExtra;
	}
	
	public String getUserTypeExtra(){
		return userTypeExtra;
	}
	
	public void setDpUserRole(String dpUserRole){
		this.dpUserRole = dpUserRole;
	}
	
	public String getDpUserRole(){
		return dpUserRole;
	}
	
	public void setRegUrl(String regUrl){
		this.regUrl = regUrl;
	}
	
	public String getRegUrl(){
		return regUrl;
	}
	
	public void setCashStatus(String cashStatus){
		this.cashStatus = cashStatus;
	}
	
	public String getCashStatus(){
		return cashStatus;
	}
	
	public void setDpAuthList(String dpAuthList){
		this.dpAuthList = dpAuthList;
	}
	
	public String getDpAuthList(){
		return dpAuthList;
	}
	
	public void setDpAuthStatus(String dpAuthStatus){
		this.dpAuthStatus = dpAuthStatus;
	}
	
	public String getDpAuthStatus(){
		return dpAuthStatus;
	}
	
	public void setDpAuthFailTime(String dpAuthFailTime){
		this.dpAuthFailTime = dpAuthFailTime;
	}
	
	public String getDpAuthFailTime(){
		return dpAuthFailTime;
	}
	
	public void setDpAuthAmount(String dpAuthAmount){
		this.dpAuthAmount = dpAuthAmount;
	}
	
	public String getDpAuthAmount(){
		return dpAuthAmount;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	
	public String getCreateTime(){
		return createTime;
	}
	
	public void setOpenDpStatus(String openDpStatus){
		this.openDpStatus = openDpStatus;
	}
	
	public String getOpenDpStatus(){
		return openDpStatus;
	}
	
	public void setOpenDpTime(String openDpTime){
		this.openDpTime = openDpTime;
	}
	
	public String getOpenDpTime(){
		return openDpTime;
	}
	
	public void setDpIdcardType(String dpIdcardType){
		this.dpIdcardType = dpIdcardType;
	}
	
	public String getDpIdcardType(){
		return dpIdcardType;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	
	public String getUpdateTime(){
		return updateTime;
	}
	
	public void setEnableStatus(String enableStatus){
		this.enableStatus = enableStatus;
	}
	
	public String getEnableStatus(){
		return enableStatus;
	}
	
	public void setPasswdSafeLevel(String passwdSafeLevel){
		this.passwdSafeLevel = passwdSafeLevel;
	}
	
	public String getPasswdSafeLevel(){
		return passwdSafeLevel;
	}
	
	public void setAppStore(String appStore){
		this.appStore = appStore;
	}
	
	public String getAppStore(){
		return appStore;
	}
	
	public void setAuditStatus(String auditStatus){
		this.auditStatus = auditStatus;
	}
	
	public String getAuditStatus(){
		return auditStatus;
	}
	
	public void setLockingTradingTime(String lockingTradingTime){
		this.lockingTradingTime = lockingTradingTime;
	}
	
	public String getLockingTradingTime(){
		return lockingTradingTime;
	}
	
	public void setIsLegacyUser(String isLegacyUser){
		this.isLegacyUser = isLegacyUser;
	}
	
	public String getIsLegacyUser(){
		return isLegacyUser;
	}
	
	public void setUserTag(String userTag){
		this.userTag = userTag;
	}
	
	public String getUserTag(){
		return userTag;
	}
	
	public void setThridUserName(String thridUserName){
		this.thridUserName = thridUserName;
	}
	
	public String getThridUserName(){
		return thridUserName;
	}
	
	public void setOwnType(String ownType){
		this.ownType = ownType;
	}
	
	public String getOwnType(){
		return ownType;
	}
	
	public void setBusType(String busType){
		this.busType = busType;
	}
	
	public String getBusType(){
		return busType;
	}
	
	public void setFddCustomerId(String fddCustomerId){
		this.fddCustomerId = fddCustomerId;
	}
	
	public String getFddCustomerId(){
		return fddCustomerId;
	}
	
	public void setThridUserId(String thridUserId){
		this.thridUserId = thridUserId;
	}
	
	public String getThridUserId(){
		return thridUserId;
	}
	
	public void setSettlementId(String settlementId){
		this.settlementId = settlementId;
	}
	
	public String getSettlementId(){
		return settlementId;
	}
	
	public void setGuarantecorpId(String guarantecorpId){
		this.guarantecorpId = guarantecorpId;
	}
	
	public String getGuarantecorpId(){
		return guarantecorpId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setIsContinuedFlag(String isContinuedFlag){
		this.isContinuedFlag = isContinuedFlag;
	}
	
	public String getIsContinuedFlag(){
		return isContinuedFlag;
	}
	
	public void setRegIp(String regIp){
		this.regIp = regIp;
	}
	
	public String getRegIp(){
		return regIp;
	}
	
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	
	public String getRemarks(){
		return remarks;
	}
	
	public void setIsLiquidate(String isLiquidate){
		this.isLiquidate = isLiquidate;
	}
	
	public String getIsLiquidate(){
		return isLiquidate;
	}
	
	public void setDpIdcardAllowRepeat(String dpIdcardAllowRepeat){
		this.dpIdcardAllowRepeat = dpIdcardAllowRepeat;
	}
	
	public String getDpIdcardAllowRepeat(){
		return dpIdcardAllowRepeat;
	}
	
	public void setPwUpdateTime(String pwUpdateTime){
		this.pwUpdateTime = pwUpdateTime;
	}
	
	public String getPwUpdateTime(){
		return pwUpdateTime;
	}
	

	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.UseISO8601DateFormat);
	}
}

