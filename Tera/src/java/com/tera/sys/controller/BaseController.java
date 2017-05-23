package com.tera.sys.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//日期转换
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(java.sql.Date.class, new CustomDateEditor(dateFormat, true));
		//时间转换
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateTimeFormat.setLenient(false);
		binder.registerCustomEditor(Timestamp.class, new TimestampEditor(dateTimeFormat, true));
		
		//针对Int，double，long等form传入为空串的情况
		binder.registerCustomEditor(Integer.class, null, new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(Double.class, null, new CustomNumberEditor(Double.class, null, true));
		
		binder.registerCustomEditor(int.class, null, new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(long.class, null, new CustomNumberEditor(Long.class, null, true));
		binder.registerCustomEditor(double.class, null, new CustomNumberEditor(Double.class, null, true));
		
//		binder.registerCustomEditor(int.class, null, new IntEditor());
//		binder.registerCustomEditor(long.class, null, new LongEditor());
//		binder.registerCustomEditor(double.class, null, new DoubleEditor());
	}
}

class IntEditor extends CustomNumberEditor {
	public IntEditor() {
		super(Integer.class, true);
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			super.setValue(0);
		} else {
			super.setValue(value);
		}
	}
}

class LongEditor extends CustomNumberEditor {
	public LongEditor() {
		super(Long.class, true);
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			super.setValue(0);
		} else {
			super.setValue(value);
		}
	}
}

class DoubleEditor extends CustomNumberEditor {
	public DoubleEditor() {
		super(Double.class, true);
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			super.setValue(0.0);
		} else {
			super.setValue(value);
		}
	}
}

class TimestampEditor extends PropertyEditorSupport {
	
	private final DateFormat dateFormat;

	private final boolean allowEmpty;

	private final int exactDateLength;
	
	public TimestampEditor(DateFormat dateFormat, boolean allowEmpty) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}
	public TimestampEditor(DateFormat dateFormat, boolean allowEmpty, int exactDateLength) {
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}

	@Override
	public String getAsText() {
		Timestamp value = (Timestamp) getValue();
		return ((value != null) ? dateFormat.format(value) : "");
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
			throw new IllegalArgumentException(
					"Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
		}
		else {
			try {
				setValue(new Timestamp(this.dateFormat.parse(text).getTime()));
			}
			catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse timestamp: " + ex.getMessage(), ex);
			}
		}
	}

}

