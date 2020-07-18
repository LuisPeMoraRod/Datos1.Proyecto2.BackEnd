package com.Project2.BackEnd.REST;

public class Notification {
	private String emisorUser;
	private String recieverUser;
	public static final int NEW_COMMENT = 0, NEW_FOLLOWER = 1, NEW_LIKE = 2, NEW_SHARE = 3;
	private int notifType;
	private String newComment;
	private String recipe;

	public String getEmisorUser() {
		return emisorUser;
	}

	public void setEmisorUser(String emisorUser) {
		this.emisorUser = emisorUser;
	}

	public String getRecieverUser() {
		return recieverUser;
	}

	public void setRecieverUser(String recieverUser) {
		this.recieverUser = recieverUser;
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

	
	public String getRecipe() {
		return recipe;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	public Notification(String emisorUser, String observerUser, int notifType, String newComment, String recipe) {
		this.emisorUser = emisorUser;
		this.recieverUser = observerUser;
		this.notifType = notifType;
		this.newComment = newComment;
		this.recipe = recipe;
	}

}
