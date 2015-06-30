package com.fanqielaile.toms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class OmsImg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id; /**/
	private Integer omsInnId; /* OMS客栈编号 */
	private Integer roomTypeId; /* 房型编号 */
	private String imgName; /* 图片名称 */
	private String imgUrl; /* 图片相对路径 */
	private Integer isCover; /* 是否封面 1- 是 0-否 */
	private Integer seq; /* 排序 */
	private Integer type; /*
						 * 图片类型 首页 1 房型 2 客栈风光 3 周边风光 4
						 */
	@JsonIgnore
	private Date createAt; /**/
	@JsonIgnore
	private Date updateAt; /**/

	public Integer getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOmsInnId() {
		return omsInnId;
	}

	public void setOmsInnId(Integer omsInnId) {
		this.omsInnId = omsInnId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getIsCover() {
		return isCover;
	}

	public void setIsCover(Integer isCover) {
		this.isCover = isCover;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}