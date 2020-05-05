package br.com.cogerh.template.controller;

public class Test {
	private Integer integer;
	private Boolean boo1;
	private Boolean boo2;
	private Boolean boo3;
	
	
	private Test(Builder builder){
		this.integer = builder.integer;
		this.boo1 = builder.boo1;
		this.boo2 = builder.boo2;
		this.boo3 = builder.boo3;

		
	}
	
	 public class Builder{
		
		 //OBRIGATORIO
		 private Integer integer;
		 
		 //OPCIONAIS
		 private Boolean boo1;
		 private Boolean boo2;
		 private Boolean boo3;
		 
		 public Builder(Integer integer){
			 this.integer = integer;
			 
		 }
		 
		 public Builder boo1(){
			 this.boo1 = true;
			 return this;
		 }
		 
		 public Builder boo2(){
			 this.boo2 = true;
			 return this;
		 }
		 public Builder boo3(){
			 this.boo3 = true;
			 return this;
		 }
		 
		 public Test builder(){
			 return new Test(this);
		 }
		 
	}
	
	
	
	 public static void main(String[] args) {
			
			//Test test = new Test.Builder(10).boo1().boo2().builder();
			}
	
}

