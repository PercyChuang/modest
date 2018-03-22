假设我们在WEB-INF下的xls目录中放了模板xxx.xlsx
web情况下，一般获取模块路径方式为：
String tempFile = request.getSession().getServletContext().getRealPath("/WEB-INF") + File.separator + "xls/xxx.xlsx";