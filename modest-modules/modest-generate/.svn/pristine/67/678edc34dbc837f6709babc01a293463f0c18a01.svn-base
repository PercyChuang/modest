{PartnerPageListResp}={select
  t.f_number userNumber,
	t.f_name userName,
	t.f_mobile mobile,
	t.f_cfp_reg_time registerTime,
	IFNULL(t2.totalFee,0) totalFee,
	IFNULL(t2.quarterFee,0) quarterFee,
  IFNULL(t3.childrenCount,0) childrenCount
from  t_sale_user_info t 
LEFT JOIN
(select
  t.f_number,
	sum(case when t1.ffeetype ='1001' then t1.ffeeamount else 0 end) totalFee,
	sum(case when t1.ffeetype ='1001' and quarter(t1.fbizdate) = quarter(now()) then t1.ffeeamount else 0 end) quarterFee
from  t_sale_user_info t 
LEFT JOIN tfeedetail t1 on t1.fsaleusernumber = t.f_number
where t.f_is_cfp =1
  and t.f_parent_id = '00009272'
GROUP BY t.f_number) t2 on  t.f_number = t2.f_number
LEFT JOIN
(select t.f_number,count(t1.f_number) childrenCount
from  t_sale_user_info t
LEFT JOIN t_sale_user_info t1 on t1.f_parent_id = t.f_number and t1.f_is_cfp =1
where t.f_is_cfp =1
  and t.f_parent_id = '00009272'
GROUP BY t.f_number) t3 on  t.f_number = t3.f_number
where  t.f_parent_id = '00009272'
}
