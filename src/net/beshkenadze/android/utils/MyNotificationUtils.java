package net.beshkenadze.android.utils;

import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

public class MyNotificationUtils {
	private static final String TAG = MyNotificationUtils.class.getSimpleName();

	private static MyNotificationUtils instance;

	private static Context context;

	private NotificationManager manager; // Системная утилита, упарляющая
											// уведомлениями

	private int lastId = 0; // постоянно увеличивающееся поле, уникальный номер
							// каждого уведомления

	private HashMap<Integer, Notification> notifications; // массив
															// ключ-значение на
															// все отображаемые
															// пользователю
															// уведомления

	// приватный контструктор для Singleton

	private MyNotificationUtils(Context context) {

		MyNotificationUtils.context = context;

		manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		notifications = new HashMap<Integer, Notification>();

	}

	/**
	 * 
	 * Получение ссылки на синглтон
	 */

	public static MyNotificationUtils getInstance(Context context) {

		if (instance == null) {

			instance = new MyNotificationUtils(context);

		} else {

			MyNotificationUtils.context = context;

		}

		return instance;

	}

	public int createInfoNotification(String title, String message, String tickText,
			Intent notificationIntent, int drawable, boolean autoCancel) {

		NotificationCompat.Builder nb = new NotificationCompat.Builder(context)

				.setSmallIcon(drawable)
				.setAutoCancel(autoCancel)
				.setTicker(tickText)
				.setContentText(message)
				.setContentIntent(
						PendingIntent.getActivity(context, 0,
								notificationIntent,
								PendingIntent.FLAG_CANCEL_CURRENT))

				.setWhen(System.currentTimeMillis()) // отображаемое время
														// уведомления
				.setContentTitle(title) // заголовок уведомления
				.setDefaults(Notification.DEFAULT_ALL); // звук, вибро и диодный
														// индикатор
														// выставляются по
														// умолчанию

		Notification notification = nb.getNotification(); // генерируем
		notification.tickerText = tickText;
		manager.notify(lastId, notification); // отображаем его пользователю.
		notifications.put(lastId, notification); // теперь мы можем обращаться к
													// нему по id

		return lastId++;

	}

	/**
	 * 
	 * Создание уведомления с прогрессбаром о загрузке
	 * 
	 * @param fileName
	 *            - текст, отображённый в заголовке уведомления.
	 */

	public int createDownloadNotification(String fileName, int drawable) {
		String text = context.getString(R.string.notification_downloading)
				.concat(" ").concat(fileName); // текст уведомления

		RemoteViews contentView = createProgressNotification(text,
				context.getString(R.string.notification_downloading), drawable);

		contentView.setImageViewResource(
				R.id.notification_download_layout_image, drawable);

		return lastId++;
	}

	/**
	 * 
	 * генерация уведомления с ProgressBar, иконкой и заголовком
	 * 
	 * 
	 * 
	 * @param text
	 *            заголовок уведомления
	 * @param drawable
	 * @param cls
	 * 
	 * @param topMessage
	 *            сообщение, уотображаемое в закрытом статус-баре при появлении
	 *            уведомления
	 * 
	 * @return View уведомления.
	 */

	private RemoteViews createProgressNotification(String text,
			String tickText, int drawable) {

		NotificationCompat.Builder nb = new NotificationCompat.Builder(context)
				.setAutoCancel(false).setSmallIcon(drawable)
				.setTicker(tickText).setContentText(text);

		Notification notification = nb.getNotification();
		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				R.layout.notification_download_layout);

		contentView.setProgressBar(
				R.id.notification_download_layout_progressbar, 100, 0, false);

		contentView.setTextViewText(R.id.notification_download_layout_title,
				text);

		notification.contentView = contentView;
		notification.tickerText = tickText;
		notification.flags = Notification.FLAG_NO_CLEAR
				| Notification.FLAG_ONGOING_EVENT
				| Notification.FLAG_ONLY_ALERT_ONCE;

		Intent notificationIntent = new Intent(context,
				MyNotificationUtils.class);

		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);

		notification.contentIntent = contentIntent;

		manager.notify(lastId, notification);
		notifications.put(lastId, notification);

		return contentView;
	}

	public void removeProgress(int id) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		mNotificationManager.cancel(id);
	}
	public void updateProgress(int id, int progress) {
		Notification notification = notifications.get(id);
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
		RemoteViews contentView = notification.contentView;
		contentView.setProgressBar(
				R.id.notification_download_layout_progressbar, 100, progress,
				false);
		mNotificationManager.notify(id, notification);
	}
}
