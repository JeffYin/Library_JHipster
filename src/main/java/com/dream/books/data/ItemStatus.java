package com.dream.books.data;

public enum ItemStatus {
	Lost("Lost"), NotPublished("NotPublished"), Borrowed("Borrowed"), ReadyForBorrow("ReadyForBorrow");  
	
	private final String value;
//	private final String lable;
	
	
    private ItemStatus(String value) {
    	this.value = value; 
//    	this.lable = lable; 
    }
    
    public String getValue() {
    	return value; 
    }

    /*
    public String getLable() {
    	return lable; 
    }
	*/
	
}
