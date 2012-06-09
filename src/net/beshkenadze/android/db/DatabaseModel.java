package net.beshkenadze.android.db;

import java.sql.SQLException;
import java.util.List;

import net.beshkenadze.android.utils.Debug;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

public class DatabaseModel {
	private Dao mDao = null;
	private DatabaseHelper mHelper;
	private Class<?> mClass;
	protected Dao<Object, Integer> getDao() {
		return mDao;
	}
	public DatabaseModel(DatabaseHelper h, Class<?> c) {

		mHelper = h;
		mClass = c;

		try {
            mDao = mHelper.getDao(mClass);
        } catch (SQLException e) {
			Debug.e("Can't get dao", e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

	public boolean add(Object mObject) {
		try {
			getDao().create(mObject);
			return true;
		} catch (SQLException e) {
			Debug.e("Can't get dao", e.getStackTrace());
			throw new RuntimeException(e);
		}
	}
	public void update(Object mObject) {
		try {
			Debug.i("update object:" + mObject);
			getDao().update(mObject);
		} catch (SQLException e) {
			Debug.e("Can't get dao", e.getStackTrace());
			throw new RuntimeException(e);
		}
	}
	public List<?> getAll() {
		try {
			return getDao().queryForAll();
		} catch (SQLException e) {
			Debug.e("Can't get dao: " + e.toString());
			throw new RuntimeException(e);
		}
	}

	public void flush() {
		try {
			TableUtils.clearTable(mHelper.getConnectionSource(), mClass);
		} catch (SQLException e) {
			Debug.e("Can't flush table: " + e.toString());
		}
	}
	public void remove(Object mObject) {
		if (mObject != null) {
			try {
				getDao().delete(mObject);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
