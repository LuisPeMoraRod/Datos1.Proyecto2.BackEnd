package com.Project2.BackEnd.REST;

public class Notification {
	private String emisorUser;
	private String recieverUser;
	public static final int NEW_COMMENT = 0, NEW_FOLLOWER = 1, NEW_UNFOLLLOW = 2, NEW_LIKE = 3, NEW_UNLIKE = 4,
			NEW_SHARE = 5, NEW_UNSHARE = 6, NEW_LIKE_COMPANY = 7, NEW_UNLIKE_COMPANY=8, CHEF_ACCEPTED =9, CHEF_DENIED = 10;
	private int notifType;
	private String newComment;
	private String recipe;
	private String messageType;

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

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(int notifType) {
		switch (notifType) {
		case NEW_COMMENT:
			this.messageType = "commented a recipe.";
			break;
		case NEW_FOLLOWER:
			this.messageType = "followed you.";
			break;
		case NEW_UNFOLLLOW:
			this.messageType = "unfollowed you.";
			break;
		case NEW_LIKE:
			this.messageType = "liked a recipe.";
			break;
		case NEW_UNLIKE:
			this.messageType = "unliked a recipe.";
			break;
		case NEW_SHARE:
			this.messageType = "shared a recipe.";
			break;
		case NEW_UNSHARE:
			this.messageType = "unshared a recipe.";
			break;
		case NEW_LIKE_COMPANY:
			this.messageType = "liked your company.";
			break;
		case NEW_UNLIKE_COMPANY:
			this.messageType = "unliked your company.";
			break;
		case CHEF_ACCEPTED:
			this.messageType = "accepted your chef request";
			break;
		case CHEF_DENIED:
			this.messageType = "denied your chef request";
		default:
			break;
		}
	}

	public Notification(String emisorUser, String observerUser, int notifType, String newComment, String recipe) {
		this.emisorUser = emisorUser;
		this.recieverUser = observerUser;
		this.notifType = notifType;
		this.newComment = newComment;
		this.recipe = recipe;
		setMessageType(notifType);
	}

}
