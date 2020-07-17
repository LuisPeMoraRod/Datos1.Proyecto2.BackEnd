package com.Project2.BackEnd.REST;

import java.util.Observable;

public class NotifObservable extends Observable {
	private Notification notification;
	
	public Notification getNotification() {
		return this.notification;
	}
	
	public void setNotification(String emisorUser, String recieverUser, int type, String comment) {
		notification = new Notification(emisorUser,recieverUser,type, comment);
		setChanged();
		notifyObservers();
	}
	
}
