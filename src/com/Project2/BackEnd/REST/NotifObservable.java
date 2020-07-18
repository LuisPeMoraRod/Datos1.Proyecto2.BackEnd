package com.Project2.BackEnd.REST;

import java.util.Observable;

public class NotifObservable extends Observable {
	private Notification notification;
	private boolean isNewNotif;
	
	public Notification getNotification() {
		return this.notification;
	}
	
	public boolean getIsNewNotif() {
		return this.isNewNotif;
	}
	public void setIsNewNotif(boolean isNewNotif) {
		this.isNewNotif = isNewNotif;
	}
	
	public synchronized void setNotification(Notification notification) {
		this.notification = notification;
		setIsNewNotif(true);
		setChanged();
		notifyObservers();
	}
	
}
