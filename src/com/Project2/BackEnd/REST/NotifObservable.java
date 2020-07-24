package com.Project2.BackEnd.REST;

import java.util.Observable;

import org.json.simple.JSONObject;

public class NotifObservable extends Observable {
	private Notification notification;
	private JSONObject chefRequest;
	private boolean newNotif;
	private boolean newChefRequest;
	
	public Notification getNotification() {
		return this.notification;
	}
	
	public JSONObject getChefRequest() {
		return this.chefRequest;
	}
	
	public boolean isNewNotif() {
		return this.newNotif;
	}
	public void setNewNotif(boolean isNewNotif) {
		this.newNotif = isNewNotif;
	}
	
	
	public boolean isNewChefRequest() {
		return newChefRequest;
	}

	public void setNewChefRequest(boolean isNewChefRequest) {
		this.newChefRequest = isNewChefRequest;
	}

	public synchronized void setNotification(Notification notification) {
		this.notification = notification;
		setNewNotif(true);
		setChanged();
		notifyObservers();
	}
	
	public synchronized void setChefRequest (JSONObject chefRequest) {
		this.chefRequest = chefRequest;
		setNewChefRequest(true);
		setChanged();
		notifyObservers();
	}
	
}
