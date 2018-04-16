package ${service.strPackage};

import com.lmw.p2p.freamwork.util.req.PaginatorSevReq;
import com.lmw.p2p.freamwork.util.reps.PaginatorSevResp;
import ${entity.fullName};
import ${service.fullName};
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
public interface ${service.shortName}{

	public PaginatorSevResp<${entity.shortName}> query${entity.shortName}(PaginatorSevReq request);
	
}
