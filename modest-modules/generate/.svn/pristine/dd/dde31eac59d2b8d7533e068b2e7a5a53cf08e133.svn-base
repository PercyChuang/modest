package ${serviceImpl.strPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lmw.mybatis.paginator.domain.PageList;
import com.lmw.mybatis.paginator.domain.PageRequest;
import com.lmw.p2p.freamwork.util.req.PaginatorSevReq;
import com.lmw.p2p.freamwork.util.reps.PaginatorSevResp;
import com.lmw.p2p.freamwork.util.enumeration.OrderEnum;
import com.lmw.p2p.freamwork.util.PaginatorUtil;
import ${entity.fullName};
import ${dao.fullName};
import ${service.fullName};
import ${serviceImpl.fullName};


 /**
 * 
 * 描述： 实体Bean
 * 
 * @创建人： ${generate.author}
 * 
 * @创建时间：${generate.createDate}
 * 
 * @Copyright (c) 深圳市利民网有限公司-版权所有
 */
@Service("${service.aliasName}")
public class ${serviceImpl.shortName} implements ${service.shortName}{
	
	@Autowired
	private ${dao.shortName} ${dao.aliasName};
		
		
	public PaginatorSevResp<${entity.shortName}> query${entity.shortName}(PaginatorSevReq request) {
		PageRequest req = PaginatorUtil.toPageRequest(request);
		if(request.getSort()==null||request.getOrder()==null){
			//req.setSort("bizTime");
			req.setOrder(OrderEnum.DESC.getValue());
		}
		PageList<${entity.shortName}> datas = ${dao.aliasName}.query${entity.shortName}(req);
		return  PaginatorUtil.toPaginatorSevResp(datas);
	}	

}
