package net.beshkenadze.android.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = DB.NAME;
	private static final int DATABASE_VERSION = DB.VERSION;
	private List<Class<?>> tables = new ArrayList<Class<?>>();

	public DatabaseHelper(Context context, List<Class<?>> tables) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		// Import DB
		DatabaseInitializer initializer = new DatabaseInitializer(context);
		try {
			initializer.createDatabase();
			initializer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		// Создаем таблицы, если они не существуют
		for (Class<?> dbclass : tables) {
			try {
				TableUtils.createTableIfNotExists(connectionSource, dbclass);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {

		// Обновление, удаляем таблицы,
		for (Class<?> dbclass : tables) {
			try {
				TableUtils.dropTable(connectionSource, dbclass, true);
			} catch (Exception e) {
			}
		}
		onCreate(sqliteDatabase, connectionSource);
	}

	@Override
	public void close() {
		super.close();
	}
}
