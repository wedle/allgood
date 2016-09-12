package com.yiqihao.loan.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CarInfoModel implements Parcelable {

	/**
	 * 保单
	 */
	private String insurancePolicy;
	/**
	 * 公里数
	 */
	private String carKilometers;
	/**
	 *车身颜色
	 */
	private String carColor;
	/**
	 * 品牌
	 */
	private String carBrandsOther;
	/**
	 * 违章记录
	 */
	private String endorsement;
	/**
	 * 车牌号
	 */
	private String carPlate;
	/**
	 * 车架号
	 */
	private String vehicleFrameNO;
	/**
	 * 发动机号
	 */
	private String engineNumber;
	/**
	 * 登记证书
	 */
	private String certificate;
	/**
	 * 初次登记日期
	 */
	private String initDate;
	/**
	 * 过户次数
	 */
	private String transferCount;
	/**
	 * 过户时间
	 */
	private String transferTime;
	/**
	 * 开票价格
	 */
	private String invoicePrice;
	/**
	 * 开票日期
	 */
	private String invoiceDate;
	/**
	 * 贷款类型
	 */
	private String creditType;

	public String getInsurancePolicy() {
		return insurancePolicy;
	}

	public void setInsurancePolicy(String insurancePolicy) {
		this.insurancePolicy = insurancePolicy;
	}

	public String getCarKilometers() {
		return carKilometers;
	}

	public void setCarKilometers(String carKilometers) {
		this.carKilometers = carKilometers;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarBrandsOther() {
		return carBrandsOther;
	}

	public void setCarBrandsOther(String carBrandsOther) {
		this.carBrandsOther = carBrandsOther;
	}

	public String getEndorsement() {
		return endorsement;
	}

	public void setEndorsement(String endorsement) {
		this.endorsement = endorsement;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getVehicleFrameNO() {
		return vehicleFrameNO;
	}

	public void setVehicleFrameNO(String vehicleFrameNO) {
		this.vehicleFrameNO = vehicleFrameNO;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getInitDate() {
		return initDate;
	}

	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}

	public String getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(String transferCount) {
		this.transferCount = transferCount;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(String invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.insurancePolicy);
		dest.writeString(this.carKilometers);
		dest.writeString(this.carColor);
		dest.writeString(this.carBrandsOther);
		dest.writeString(this.endorsement);
		dest.writeString(this.carPlate);
		dest.writeString(this.vehicleFrameNO);
		dest.writeString(this.engineNumber);
		dest.writeString(this.certificate);
		dest.writeString(this.initDate);
		dest.writeString(this.transferCount);
		dest.writeString(this.transferTime);
		dest.writeString(this.invoicePrice);
		dest.writeString(this.invoiceDate);
		dest.writeString(this.creditType);
	}

	public CarInfoModel() {
	}

	protected CarInfoModel(Parcel in) {
		this.insurancePolicy = in.readString();
		this.carKilometers = in.readString();
		this.carColor = in.readString();
		this.carBrandsOther = in.readString();
		this.endorsement = in.readString();
		this.carPlate = in.readString();
		this.vehicleFrameNO = in.readString();
		this.engineNumber = in.readString();
		this.certificate = in.readString();
		this.initDate = in.readString();
		this.transferCount = in.readString();
		this.transferTime = in.readString();
		this.invoicePrice = in.readString();
		this.invoiceDate = in.readString();
		this.creditType = in.readString();
	}

	public static final Parcelable.Creator<CarInfoModel> CREATOR = new Parcelable.Creator<CarInfoModel>() {
		@Override
		public CarInfoModel createFromParcel(Parcel source) {
			return new CarInfoModel(source);
		}

		@Override
		public CarInfoModel[] newArray(int size) {
			return new CarInfoModel[size];
		}
	};
}
