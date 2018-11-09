package com.deliver.util;

import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;  
import java.util.zip.CRC32;  
import java.util.zip.CheckedOutputStream;  
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;  
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;



public class UnZipUtil {
	
	//解压文件
	/*public static boolean unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception{
		if (StringUtil.isEmpty(zipFilePath) || StringUtil.isEmpty(unzipFilePath))  
        {  
            return false;            
        }  
        File zipFile = new File(zipFilePath);  
        //如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径  
        if (includeZipFileName)  
        {  
            String fileName = zipFile.getName();  
            if (StringUtil.isNotEmpty(fileName))  
            {  
                fileName = fileName.substring(0, fileName.lastIndexOf("."));  
            }  
            unzipFilePath = unzipFilePath + File.separator + fileName;  
        }  
        //创建解压缩文件保存的路径  
        File unzipFileDir = new File(unzipFilePath);  
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())  
        {  
            unzipFileDir.mkdirs();  
        }  
          
        //开始解压  
        ZipEntry entry = null;  
        String entryFilePath = null, entryDirPath = null;  
        File entryFile = null, entryDir = null;  
        int index = 0, count = 0, bufferSize = 1024;  
        byte[] buffer = new byte[bufferSize];  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));  
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();  
        //循环对压缩包里的每一个文件进行解压      
        
        while(entries.hasMoreElements())  
        {  
            entry = entries.nextElement();  
            //构建压缩包中一个文件解压后保存的文件全路径  
            entryFilePath = unzipFilePath + File.separator + entry.getName();  
            //构建解压后保存的文件夹路径  
            index = entryFilePath.lastIndexOf(File.separator);  
            if (index != -1)  
            {  
                entryDirPath = entryFilePath.substring(0, index);  
            }  
            else  
            {  
                entryDirPath = "";  
            }             
            entryDir = new File(entryDirPath);  
            //如果文件夹路径不存在，则创建文件夹  
            if (!entryDir.exists() || !entryDir.isDirectory())  
            {  
                entryDir.mkdirs();  
            }  
              
            //创建解压文件  
            entryFile = new File(entryFilePath);  
            if (entryFile.exists())  
            {  
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException  
                SecurityManager securityManager = new SecurityManager();  
                securityManager.checkDelete(entryFilePath);  
                //删除已存在的目标文件  
                entryFile.delete();   
            }  
              
            //写入文件  
            try {
				bos = new BufferedOutputStream(new FileOutputStream(entryFile));  
				bis = new BufferedInputStream(zip.getInputStream(entry));  
				while ((count = bis.read(buffer, 0, bufferSize)) != -1)  
				{  
				    bos.write(buffer, 0, count);  
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}  
            bos.flush();  
            bos.close();              
        }
		return true; 
	   }    */
	
	/** 
     * 对文件或文件目录进行压缩 
     * @param srcPath 要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径 
     * @param zipPath 压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹 
     * @param zipFileName 压缩文件名 
     * @throws Exception 
     */  
   /* public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception  
    {  
        if (StringUtils.isEmpty(srcPath) || StringUtils.isEmpty(zipPath) || StringUtils.isEmpty(zipFileName))  
        {  
            throw new ParameterException(ICommonResultCode.PARAMETER_IS_NULL);  
        }  
        CheckedOutputStream cos = null;  
        ZipOutputStream zos = null;                       
        try  
        {  
            File srcFile = new File(srcPath);  
              
            //判断压缩文件保存的路径是否为源文件路径的子文件夹，如果是，则抛出异常（防止无限递归压缩的发生）  
            if (srcFile.isDirectory() && zipPath.indexOf(srcPath)!=-1)   
            {  
                throw new ParameterException(ICommonResultCode.INVALID_PARAMETER, "zipPath must not be the child directory of srcPath.");  
            }  
              
            //判断压缩文件保存的路径是否存在，如果不存在，则创建目录  
            File zipDir = new File(zipPath);  
            if (!zipDir.exists() || !zipDir.isDirectory())  
            {  
                zipDir.mkdirs();  
            }  
              
            //创建压缩文件保存的文件对象  
            String zipFilePath = zipPath + File.separator + zipFileName;  
            File zipFile = new File(zipFilePath);             
            if (zipFile.exists())  
            {  
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException  
                SecurityManager securityManager = new SecurityManager();  
                securityManager.checkDelete(zipFilePath);  
                //删除已存在的目标文件  
                zipFile.delete();                 
            }  
              
            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());  
            zos = new ZipOutputStream(cos);  
              
            //如果只是压缩一个文件，则需要截取该文件的父目录  
            String srcRootDir = srcPath;  
            if (srcFile.isFile())  
            {  
                int index = srcPath.lastIndexOf(File.separator);  
                if (index != -1)  
                {  
                    srcRootDir = srcPath.substring(0, index);  
                }  
            }  
            //调用递归压缩方法进行目录或文件压缩  
            zip(srcRootDir, srcFile, zos);  
            zos.flush();  
        }  
        catch (Exception e)   
        {  
            throw e;  
        }  
        finally   
        {             
            try  
            {  
                if (zos != null)  
                {  
                    zos.close();  
                }                 
            }  
            catch (Exception e)  
            {  
                e.printStackTrace();  
            }             
        }  
    }  */
	
	/**
	 * 解压文件夹
	 * 
	 * **/
	public boolean unZipFolder(String zipfile,String outputFolder) throws IOException {
	      //检查是否是zip文件,并判断文件是否存在
	      if(!checkFileName(zipfile)){
	    	  return false;
	      }
	      File outputfile = new File(outputFolder);  
          //如果文件夹路径不存在，则创建文件夹   
          if (!outputfile.exists() || !outputfile.isDirectory())  
          {  
        	  outputfile.mkdirs();  
          }  
	      
	      long startTime = System.currentTimeMillis();
	      File zfile=new File(zipfile);
	      //获取待解压文件的父路径
	      String Parent=zfile.getParent()+"/";
	      String outputpath = outputFolder;
	      FileInputStream fis=new FileInputStream(zfile);
	      Charset charset = Charset.forName("GBK");//默认UTF-8
	    // CheckedInputStream cis = new CheckedInputStream(fis,new CRC32());
	      ZipInputStream zis = new ZipInputStream(fis,charset);// 输入源zip路径
	      ZipEntry entry=null;
	      BufferedOutputStream bos=null;
	      while ((entry=zis.getNextEntry())!=null) {
	        if (entry.isDirectory()) {
		        File filePath=new File(outputFolder+"/"+entry.getName());
		        //如果目录不存在，则创建
		        if (!filePath.exists()) {
		          filePath.mkdirs();
		        }
	        }else{
	        FileOutputStream fos=new FileOutputStream(outputFolder+"/"+entry.getName());
	        bos=new BufferedOutputStream(fos);
	        byte buf[] = new byte[1024];
	        int len;
	        while ((len = zis.read(buf)) != -1) {
	          bos.write(buf, 0, len);
	        }
	        zis.closeEntry();
	        //关闭的时候会刷新
	        bos.close();
	        }
	      }
	      zis.close();
	      long endTime = System.currentTimeMillis();
	      System.out.println("解压完成！所需时间为："+(endTime-startTime)+"ms");
	    // System.out.println("校验和："+cis.getChecksum().getValue());
	      return true;
	}
	     
	      private boolean checkFileName(String name) {
	      //文件是否存在
		      if (!new File(name).exists()) {
		        //System.err.println("要解压的文件不存在！");
		        System.exit(1);
		        return false;
		      }
		      // 判断是否是zip文件
		      int index = name.lastIndexOf(".");
		      String str=name.substring(index+1);
		      if (!"zip".equalsIgnoreCase(str)) {
		        //System.err.println("不是zip文件,无法解压！");
		        System.exit(1);
		        return false;
		      } 
		      return true;
	      }

	 
	   

	
	public static void main(String[] args)   
    {  
          
        String zipFilePath = "D:/tomcat/apache-tomcat-7.0.34/webapps/images/scene.zip";  
        String unzipFilePath = "D:/tomcat/apache-tomcat-7.0.34/webapps/images/unzipscene";  
        try   
        {  
            //unzip(zipFilePath, unzipFilePath, true);
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
	

}
