package projectireas.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationList {

	@JsonProperty
	private Long userId;
	
	@JsonProperty
	List<Notification> notificationList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Notification> getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(List<Notification> notificationList) {
		this.notificationList = notificationList;
	}

	public NotificationList(Long userId, List<Notification> notificationList) {
		super();
		this.userId = userId;
		this.notificationList = notificationList;
	}

	public NotificationList() {
		super();
	}
}
