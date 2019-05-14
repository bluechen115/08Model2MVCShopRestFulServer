package com.model2.mvc.service.domain;

import org.springframework.web.multipart.MultipartFile;

public class ProductInfo {

	private int boardNo;
	private String title;
	private String boardDetail;
	private int quantity;
	private String boardRegDate;
	private int viewCount;
	private int prodPrice;
	
	private String fileName;
	private String manuDate;
	private int price;
	private String prodName;
	private int prodNo;
	private String proTranCode;
	private int resultPrice;
	private String saleStatus;
	private MultipartFile uploadFile;

	public ProductInfo() {
		System.out.println(this.getClass());
	}
	
	public Product getProduct() {
		Product product = new Product();
		product.setBoardNo(boardNo);
		product.setFileName(fileName);
		product.setManuDate(manuDate);
		product.setPrice(price);
		product.setProdName(prodName);
		product.setProdNo(prodNo);
		product.setProTranCode(proTranCode);
		product.setResultPrice(resultPrice);
		product.setSaleStatus(saleStatus);
		product.setUploadFile(uploadFile);
		
		return product;
	}
	
	public ProductBoard getProductBoard() {
		ProductBoard productBoard = new ProductBoard();
		productBoard.setBoardDetail(boardDetail);
		productBoard.setBoardRegDate(boardRegDate);
		productBoard.setProdPrice(prodPrice);
		productBoard.setQuantity(quantity);
		productBoard.setTitle(title);
		productBoard.setViewCount(viewCount);
		
		return productBoard;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBoardDetail() {
		return boardDetail;
	}

	public void setBoardDetail(String boardDetail) {
		this.boardDetail = boardDetail;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getBoardRegDate() {
		return boardRegDate;
	}

	public void setBoardRegDate(String boardRegDate) {
		this.boardRegDate = boardRegDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(int prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getManuDate() {
		return manuDate;
	}

	public void setManuDate(String manuDate) {
		this.manuDate = manuDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getProTranCode() {
		return proTranCode;
	}

	public void setProTranCode(String proTranCode) {
		this.proTranCode = proTranCode;
	}

	public int getResultPrice() {
		return resultPrice;
	}

	public void setResultPrice(int resultPrice) {
		this.resultPrice = resultPrice;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	@Override
	public String toString() {
		return "ProductInfo [boardNo=" + boardNo + ", title=" + title + ", boardDetail=" + boardDetail + ", quantity="
				+ quantity + ", boardRegDate=" + boardRegDate + ", viewCount=" + viewCount + ", prodPrice=" + prodPrice
				+ ", fileName=" + fileName + ", manuDate=" + manuDate + ", price=" + price + ", prodName=" + prodName
				+ ", prodNo=" + prodNo + ", proTranCode=" + proTranCode + ", resultPrice=" + resultPrice
				+ ", saleStatus=" + saleStatus + ", uploadFile=" + uploadFile + "]";
	}

}
