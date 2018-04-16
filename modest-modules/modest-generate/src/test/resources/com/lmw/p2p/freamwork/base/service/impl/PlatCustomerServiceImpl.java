package com.lmw.p2p.freamwork.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lmw.p2p.freamwork.base.dao.PlatCustomerMapper;
import com.lmw.p2p.freamwork.base.service.PlatCustomerService;


 /**
 * 
 * @描述： 实体Bean
 * 
 * @创建人： PercyChuang
 * 
 * @创建时间：2018年04月16日 15:25:28
 * 
 * @Copyright (c) 深圳市利民网有限公司-版权所有
 */
@Service("platCustomerService")
public class PlatCustomerServiceImpl implements PlatCustomerService{
	
	@Autowired
	private PlatCustomerMapper platCustomerMapper;
	


}
