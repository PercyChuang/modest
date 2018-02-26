package com.modest.file.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class VSFtpUtil {
	/** 
	    * Description: 向FTP服务器上传文件 
	    * @param url FTP服务器 hostname 
	    * @param port FTP服务器端口 默认端 21 
	    * @param username FTP登录账号 
	    * @param password FTP登录密码 
	    * @param path FTP服务器保存目录 
	    * @param filename 上传到FTP服务器上的文件名 
	    * @param input 输入流 
	    * @return 成功返回true，否则返回false 
	    */   
	    public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {   
	        boolean success = false;   
	        FTPClient ftp = new FTPClient();   
	        try {   
	            int reply;   
	            ftp.connect(url, port);//连接FTP服务器   
//	          ftp.connect(url);//连接FTP服务器   
	            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器   
	            ftp.login(username, password);//登录   
	          ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  
	          ftp.setControlEncoding("GBK");  
	            reply = ftp.getReplyCode();  
	            if (!FTPReply.isPositiveCompletion(reply)) {  
	                ftp.disconnect();   
	                return success;   
	            }  
	            ftp.changeWorkingDirectory(path);   
	            // 设置文件名上传的编码格式为 utf-8  
	            ftp.storeFile(new String(filename.getBytes("utf-8"),"iso-8859-1"), input);            
	               
	            input.close();   
	            ftp.logout();   
	            success = true;   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        } finally {   
	            if (ftp.isConnected()) {   
	                try {   
	                    ftp.disconnect();   
	                } catch (IOException ioe) {   
	                }   
	            }   
	        }   
	        return success;    
	} 
	    
	    
    /** 
     * Description: 从FTP服务器下载文件 
     * @param url FTP服务器hostname 
     * @param port FTP服务器端口 
     * @param username FTP登录账号 
     * @param password FTP登录密码 
     * @param remotePath FTP服务器上的相对路径 
     * @param fileName 要下载的文件名 
     * @param localPath 下载后保存到本地的路径 
     * @return 
     */   
     public static boolean downFile(String url, int port,String username, String password, String remotePath,String fileName,String localPath) {   
         boolean success = false;   
         FTPClient ftp = new FTPClient();  
         try {   
             int reply;   
             ftp.connect(url, port);   
             //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器   
             ftp.login(username, password);//登录   
             reply = ftp.getReplyCode();   
             if (!FTPReply.isPositiveCompletion(reply)) {   
                 ftp.disconnect();   
                 return success;   
             }   
             ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录   
             FTPFile[] fs = ftp.listFiles();   
             for(FTPFile ff:fs){   
                 if(ff.getName().equals(fileName)){   
                     File localFile = new File(localPath+"/"+ff.getName());   
                     OutputStream is = new FileOutputStream(localFile);    
                     ftp.retrieveFile(ff.getName(), is);   
                     is.close();   
                 }   
             }   
                
             ftp.logout();   
             success = true;   
         } catch (IOException e) {   
             e.printStackTrace();   
         } finally {   
             if (ftp.isConnected()) {   
                 try {   
                     ftp.disconnect();   
                 } catch (IOException ioe) {   
                 }   
             }   
         }   
         return success;   
     }  
     
     
     public static void main(String[] args) throws Exception {  
         
         
       // =======上传测试============  
         String localFile = "F:\\borrow_Borrower.xls";  
           
         String url = "106.15.74.107";  
         int port = 21;  
         String username ="lm";  
         String password ="lm@PassOrd";  
         String path ="lm/accept";  
         String filename ="borrow_Borrower.xls";  
         InputStream input = new FileInputStream(new File(localFile));  
           
         uploadFile(url, port, username, password, path, filename, input);  
//       // =======上传测试============  
           
         // =======下载测试============  
//       String localPath = "D:\\";  
//       String url = "192.168.2.228";
//       int port = 21;  
//       String username ="testlimincom";  
//       String password ="testlimincom";  
//       String remotePath ="/lm/accept";  
//       String fileName = "borrow_Borrower.xls";  
//       downFile(url, port, username, password, remotePath, fileName, localPath);  
         // =======下载测试============  
           
     }  
}
