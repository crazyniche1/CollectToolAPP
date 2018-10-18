package com.example.test1_19;

public class APNMatchTools {

	
	  public static class APNNet{   
	        /**
	         * 中国移动cmwap
	         */   
	        public static String CMWAP = "cmwap";   
	           
	        /**
	         * 中国移动cmnet
	         */   
	        public static String CMNET = "cmnet";   
	           
	        //中国联通3GWAP设置        中国联通3G因特网设置        中国联通WAP设置        中国联通因特网设置   
	        //3gwap                 3gnet                uniwap            uninet   
	           
	           
	        /**
	         * 3G wap 中国联通3gwap APN 
	         */   
	        public static String GWAP_3 = "3gwap";   
	           
	        /**
	         * 3G net 中国联通3gnet APN 
	         */   
	        public static String GNET_3="3gnet";   
	           
	        /**
	         * uni wap 中国联通uni wap APN 
	         */   
	        public static String UNIWAP="uniwap";   
	        /**
	         * uni net 中国联通uni net APN 
	         */   
	        public static String UNINET="uninet";   
	    }   
	  
	  
	  
	  public static String matchAPN(String currentName) {           
	        if("".equals(currentName) || null==currentName){   
	            return "";   
	        }   
	        currentName = currentName.toLowerCase();   
	        if(currentName.startsWith(APNNet.CMNET))   
	            return APNNet.CMNET;   
	        else if(currentName.startsWith(APNNet.CMWAP))   
	            return APNNet.CMWAP;   
	        else if(currentName.startsWith(APNNet.GNET_3))   
	            return APNNet.GNET_3;   
	        else if(currentName.startsWith(APNNet.GWAP_3))   
	            return APNNet.GWAP_3;   
	        else if(currentName.startsWith(APNNet.UNINET))   
	            return APNNet.UNINET;   
	        else if(currentName.startsWith(APNNet.UNIWAP))   
	            return APNNet.UNIWAP;   
	        else if(currentName.startsWith("default"))   
	            return "default";   
	        else return "";   
	       // return currentName.substring(0, currentName.length() - SUFFIX.length());   
	    }   

}
