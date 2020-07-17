package com.Project2.BackEnd.REST;

public class Notification {
	private String emisorUser;
	private String observerUser;
	public static final int NEW_COMMENT = 0, NEW_FOLLOWER = 1, NEW_LIKE = 2, NEW_SHARE = 3;
	private int notifType;
	private String newComment;

	public String getEmisorUser() {
		return emisorUser;
	}

	public void setEmisorUser(String emisorUser) {
		this.emisorUser = emisorUser;
	}

	public String getObserverUser() {
		return observerUser;
	}

	public void setObserverUser(String observerUser) {
		this.observerUser = observerUser;
	}

	public int getNotifType() {
		return notifType;
	}

	public void setNotifType(int notifType) {
		this.notifType = notifType;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public Notification(String emisorUsr, String observerUser, int notifType, String newComment) {
		this.emisorUser = emisorUsr;
		this.observerUser = observerUser;
		this.notifType = notifType;
		this.newComment = newComment;

	}

}
