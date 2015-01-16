package com.dream.books.data;

public enum ItemStatus {
	Lost(-100), NotPublished(-10), Borrowed(-1), ReadyForBorrow(0);  
	
	private final Integer value;
//	private final String lable;
	
    private ItemStatus(Integer value) {
    	this.value = value; 
//    	this.lable = lable; 
    }
    
    public Integer getValue() {
    	return value; 
    }

    /*
    public String getLable() {
    	return lable; 
    }
	*/
	
}
