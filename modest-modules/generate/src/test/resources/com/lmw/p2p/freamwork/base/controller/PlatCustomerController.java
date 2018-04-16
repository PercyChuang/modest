package com.lmw.p2p.freamwork.base.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.xn.channel.base.BaseController;
import com.xiaoniu.mybatis.paginator.domain.PageRequest;
import cn.xn.channel.utils.ResponseUtil;
import com.lmw.p2p.freamwork.base.model.PlatCustomer;
import com.lmw.p2p.freamwork.base.service.PlatCustomerService;

 /**
 * 
 * @描述： 实体Bean
 * 
 * @创建人： PercyChuang
 * 
 * @创建时间：2018年04月16日 15:25:28
 * 
 * Copyright (c) 深圳市小牛科技有限公司-版权所有
 */
@Controller
@RequestMapping(value = "api/platCustomer")
public class PlatCustomerController extends BaseController{

	@Autowired
	private PlatCustomerService platCustomerService;
	
	private static final Map<String,String> mapping = getMapping();

	@RequestMapping(value = "init")
	public String init() {
		return "api/platCustomerList";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(PageRequest page) {
		if(page.getSort()!=null&&!"".equals(page.getSort())){
			page.setSort(mapping.get(page.getSort()));
		}
		return platCustomerService.list(page).toPageResponseDefaultFormate();
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(PlatCustomer platCustomer) {
	    PlatCustomer save = null;
		if (platCustomer.getId()==null||"".equals(platCustomer.getId())) {
			save = new PlatCustomer();
     		save.setCustomerId(platCustomer.getCustomerId());
     		save.setCustomerName(platCustomer.getCustomerName());
     		save.setCustomerSurname(platCustomer.getCustomerSurname());
     		save.setMobile(platCustomer.getMobile());
     		save.setEmail(platCustomer.getEmail());
     		save.setPasswd(platCustomer.getPasswd());
     		save.setTradingPasswd(platCustomer.getTradingPasswd());
     		save.setGesturePasswd(platCustomer.getGesturePasswd());
     		save.setRegTime(platCustomer.getRegTime());
     		save.setRecommender(platCustomer.getRecommender());
     		save.setLockingTime(platCustomer.getLockingTime());
     		save.setScode(platCustomer.getScode());
     		save.setAuthStep(platCustomer.getAuthStep());
     		save.setGroupId(platCustomer.getGroupId());
     		save.setLoginCount(platCustomer.getLoginCount());
     		save.setLegalType(platCustomer.getLegalType());
     		save.setRiskType(platCustomer.getRiskType());
     		save.setRegChannel(platCustomer.getRegChannel());
     		save.setOrigin(platCustomer.getOrigin());
     		save.setGmId(platCustomer.getGmId());
     		save.setNoAgree(platCustomer.getNoAgree());
     		save.setBankBindId(platCustomer.getBankBindId());
     		save.setUserType(platCustomer.getUserType());
     		save.setUserTypeExtra(platCustomer.getUserTypeExtra());
     		save.setDpUserRole(platCustomer.getDpUserRole());
     		save.setRegUrl(platCustomer.getRegUrl());
     		save.setCashStatus(platCustomer.getCashStatus());
     		save.setDpAuthList(platCustomer.getDpAuthList());
     		save.setDpAuthStatus(platCustomer.getDpAuthStatus());
     		save.setDpAuthFailTime(platCustomer.getDpAuthFailTime());
     		save.setDpAuthAmount(platCustomer.getDpAuthAmount());
     		save.setCreateTime(platCustomer.getCreateTime());
     		save.setOpenDpStatus(platCustomer.getOpenDpStatus());
     		save.setOpenDpTime(platCustomer.getOpenDpTime());
     		save.setDpIdcardType(platCustomer.getDpIdcardType());
     		save.setUpdateTime(platCustomer.getUpdateTime());
     		save.setEnableStatus(platCustomer.getEnableStatus());
     		save.setPasswdSafeLevel(platCustomer.getPasswdSafeLevel());
     		save.setAppStore(platCustomer.getAppStore());
     		save.setAuditStatus(platCustomer.getAuditStatus());
     		save.setLockingTradingTime(platCustomer.getLockingTradingTime());
     		save.setIsLegacyUser(platCustomer.getIsLegacyUser());
     		save.setUserTag(platCustomer.getUserTag());
     		save.setThridUserName(platCustomer.getThridUserName());
     		save.setOwnType(platCustomer.getOwnType());
     		save.setBusType(platCustomer.getBusType());
     		save.setFddCustomerId(platCustomer.getFddCustomerId());
     		save.setThridUserId(platCustomer.getThridUserId());
     		save.setSettlementId(platCustomer.getSettlementId());
     		save.setGuarantecorpId(platCustomer.getGuarantecorpId());
     		save.setMerchantId(platCustomer.getMerchantId());
     		save.setIsContinuedFlag(platCustomer.getIsContinuedFlag());
     		save.setRegIp(platCustomer.getRegIp());
     		save.setRemarks(platCustomer.getRemarks());
     		save.setIsLiquidate(platCustomer.getIsLiquidate());
     		save.setDpIdcardAllowRepeat(platCustomer.getDpIdcardAllowRepeat());
     		save.setPwUpdateTime(platCustomer.getPwUpdateTime());
			platCustomerService.add(save);
			return ResponseUtil.getSuccessResponse();
		} else {
		    save = platCustomerService.getByPrimaryKey(platCustomer.getId());
     		save.setCustomerId(platCustomer.getCustomerId());
     		save.setCustomerName(platCustomer.getCustomerName());
     		save.setCustomerSurname(platCustomer.getCustomerSurname());
     		save.setMobile(platCustomer.getMobile());
     		save.setEmail(platCustomer.getEmail());
     		save.setPasswd(platCustomer.getPasswd());
     		save.setTradingPasswd(platCustomer.getTradingPasswd());
     		save.setGesturePasswd(platCustomer.getGesturePasswd());
     		save.setRegTime(platCustomer.getRegTime());
     		save.setRecommender(platCustomer.getRecommender());
     		save.setLockingTime(platCustomer.getLockingTime());
     		save.setScode(platCustomer.getScode());
     		save.setAuthStep(platCustomer.getAuthStep());
     		save.setGroupId(platCustomer.getGroupId());
     		save.setLoginCount(platCustomer.getLoginCount());
     		save.setLegalType(platCustomer.getLegalType());
     		save.setRiskType(platCustomer.getRiskType());
     		save.setRegChannel(platCustomer.getRegChannel());
     		save.setOrigin(platCustomer.getOrigin());
     		save.setGmId(platCustomer.getGmId());
     		save.setNoAgree(platCustomer.getNoAgree());
     		save.setBankBindId(platCustomer.getBankBindId());
     		save.setUserType(platCustomer.getUserType());
     		save.setUserTypeExtra(platCustomer.getUserTypeExtra());
     		save.setDpUserRole(platCustomer.getDpUserRole());
     		save.setRegUrl(platCustomer.getRegUrl());
     		save.setCashStatus(platCustomer.getCashStatus());
     		save.setDpAuthList(platCustomer.getDpAuthList());
     		save.setDpAuthStatus(platCustomer.getDpAuthStatus());
     		save.setDpAuthFailTime(platCustomer.getDpAuthFailTime());
     		save.setDpAuthAmount(platCustomer.getDpAuthAmount());
     		save.setCreateTime(platCustomer.getCreateTime());
     		save.setOpenDpStatus(platCustomer.getOpenDpStatus());
     		save.setOpenDpTime(platCustomer.getOpenDpTime());
     		save.setDpIdcardType(platCustomer.getDpIdcardType());
     		save.setUpdateTime(platCustomer.getUpdateTime());
     		save.setEnableStatus(platCustomer.getEnableStatus());
     		save.setPasswdSafeLevel(platCustomer.getPasswdSafeLevel());
     		save.setAppStore(platCustomer.getAppStore());
     		save.setAuditStatus(platCustomer.getAuditStatus());
     		save.setLockingTradingTime(platCustomer.getLockingTradingTime());
     		save.setIsLegacyUser(platCustomer.getIsLegacyUser());
     		save.setUserTag(platCustomer.getUserTag());
     		save.setThridUserName(platCustomer.getThridUserName());
     		save.setOwnType(platCustomer.getOwnType());
     		save.setBusType(platCustomer.getBusType());
     		save.setFddCustomerId(platCustomer.getFddCustomerId());
     		save.setThridUserId(platCustomer.getThridUserId());
     		save.setSettlementId(platCustomer.getSettlementId());
     		save.setGuarantecorpId(platCustomer.getGuarantecorpId());
     		save.setMerchantId(platCustomer.getMerchantId());
     		save.setIsContinuedFlag(platCustomer.getIsContinuedFlag());
     		save.setRegIp(platCustomer.getRegIp());
     		save.setRemarks(platCustomer.getRemarks());
     		save.setIsLiquidate(platCustomer.getIsLiquidate());
     		save.setDpIdcardAllowRepeat(platCustomer.getDpIdcardAllowRepeat());
     		save.setPwUpdateTime(platCustomer.getPwUpdateTime());
			platCustomerService.update(save);
			return ResponseUtil.getSuccessResponse();
		}
	}

	

	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(String[] ids) {
	    if(ids!=null&&ids.length>0){
	       platCustomerService.deleteBatch(ids);
	    }
		return ResponseUtil.getSuccessResponse();
	}

	@RequestMapping(value = "get")
	@ResponseBody
	public Object get(String id) {
		return platCustomerService.getByPrimaryKey(id);
	}
	
	private static Map<String,String> getMapping(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", "id");
		map.put("customerId", "customer_id");
		map.put("customerName", "customer_name");
		map.put("customerSurname", "customer_surname");
		map.put("mobile", "mobile");
		map.put("email", "email");
		map.put("passwd", "passwd");
		map.put("tradingPasswd", "trading_passwd");
		map.put("gesturePasswd", "gesture_passwd");
		map.put("regTime", "reg_time");
		map.put("recommender", "recommender");
		map.put("lockingTime", "locking_time");
		map.put("scode", "scode");
		map.put("authStep", "auth_step");
		map.put("groupId", "group_id");
		map.put("loginCount", "login_count");
		map.put("legalType", "legal_type");
		map.put("riskType", "risk_type");
		map.put("regChannel", "reg_channel");
		map.put("origin", "origin");
		map.put("gmId", "gm_id");
		map.put("noAgree", "no_agree");
		map.put("bankBindId", "bank_bind_id");
		map.put("userType", "user_type");
		map.put("userTypeExtra", "user_type_extra");
		map.put("dpUserRole", "dp_user_role");
		map.put("regUrl", "reg_url");
		map.put("cashStatus", "cash_status");
		map.put("dpAuthList", "dp_auth_list");
		map.put("dpAuthStatus", "dp_auth_status");
		map.put("dpAuthFailTime", "dp_auth_fail_time");
		map.put("dpAuthAmount", "dp_auth_amount");
		map.put("createTime", "create_time");
		map.put("openDpStatus", "open_dp_status");
		map.put("openDpTime", "open_dp_time");
		map.put("dpIdcardType", "dp_idcard_type");
		map.put("updateTime", "update_time");
		map.put("enableStatus", "enable_status");
		map.put("passwdSafeLevel", "passwd_safe_level");
		map.put("appStore", "app_store");
		map.put("auditStatus", "audit_status");
		map.put("lockingTradingTime", "locking_trading_time");
		map.put("isLegacyUser", "is_legacy_user");
		map.put("userTag", "user_tag");
		map.put("thridUserName", "thrid_user_name");
		map.put("ownType", "own_type");
		map.put("busType", "bus_type");
		map.put("fddCustomerId", "fdd_customer_id");
		map.put("thridUserId", "thrid_user_id");
		map.put("settlementId", "settlement_id");
		map.put("guarantecorpId", "guarantecorp_id");
		map.put("merchantId", "merchant_id");
		map.put("isContinuedFlag", "is_continued_flag");
		map.put("regIp", "reg_ip");
		map.put("remarks", "remarks");
		map.put("isLiquidate", "is_liquidate");
		map.put("dpIdcardAllowRepeat", "dp_idcard_allow_repeat");
		map.put("pwUpdateTime", "pw_update_time");
		return map;
	}
}
