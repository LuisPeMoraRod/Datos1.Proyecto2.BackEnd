package com.Project2.BackEnd.REST;

import java.util.Observable;

public class NotifObservable extends Observable {
	
	
	
	
	public static class Notification{
		private String emisorUser;
		private String observerUser;
		public static final int NEW_COMMENT = 0, NEW_FOLLOWER = 1, NEW_LIKE = 2; 
		public int notifType;
		public Notification(String emisorUser, String observerUser, int notifType) {
			this.emisorUser = emisorUser;
			this.observerUser = observerUser;
			this.notifType = notifType;
		}
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
		
	}

}
