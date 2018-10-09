package com.callx.aws.lambda.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.beanutils.BeanUtils;

public class ResultSetMapper<T> {

	
	@SuppressWarnings("unchecked")
	public List<T> mapRersultSetToObject(ResultSet rs, Class outputClass) {
		List<T> outputList = new ArrayList<T>();
		try {
			// make sure resultset is not null
			if (rs != null) {
				// check if outputClass has 'Entity' annotation
				if (outputClass.isAnnotationPresent(Entity.class)) {
					// get the resultset metadata4
					ResultSetMetaData rsmd = rs.getMetaData();
					// get all the attributes of outputClass
					Field[] fields = outputClass.getDeclaredFields();
					while (rs.next()) {
						T bean = (T) outputClass.newInstance();
						for (int _iterator = 0; _iterator < rsmd
								.getColumnCount(); _iterator++) {
							// getting the SQL column name
							String columnName = rsmd
									.getColumnName(_iterator + 1);
							// reading the value of the SQL column
							Object columnValue = rs.getObject(_iterator + 1);
							// iterating over outputClass attributes to check if any attribute has 'Column' annotation with matching 'name' value
							int i = 0;
							for (Field field : fields) {
								i = i + 1;
								
									Column column = field.getAnnotation(Column.class);
									if (column.name().equalsIgnoreCase(columnName)) {
										
										if(columnValue == null) {
											BeanUtils.setProperty(bean, field.getName(), "");
											
										}else if(columnValue.getClass().isAssignableFrom(Long.class)) {
											BeanUtils.setProperty(bean, field.getName(), (Long)columnValue);
											
										}else if(columnValue.getClass().isAssignableFrom(BigDecimal.class)) {
											BeanUtils.setProperty(bean, field.getName(), (BigDecimal)columnValue);	
											
										}else if(columnValue.getClass().isAssignableFrom(Integer.class)) {
											BeanUtils.setProperty(bean, field.getName(), (Integer)columnValue);	
											
										}else if(columnValue.getClass().isAssignableFrom(String.class)) {
											BeanUtils.setProperty(bean, field.getName(), (String)columnValue);	
											
										}else if(!columnValue.toString().valueOf(columnValue).equalsIgnoreCase("NaN") && !columnValue.toString().valueOf(columnValue).equalsIgnoreCase("Infinity")
												  && columnValue.getClass().isAssignableFrom(Double.class)) {
											BeanUtils.setProperty(bean, field.getName(), new BigDecimal((Double)columnValue));	
										}

										break;
								}
							}
						}
						outputList.add(bean);
					}

				} else {
					System.out.println(" Annotation Entity is not Defined on Class Level");
				}
			} else {
				System.out.println(" Result Set is NULL");
				return null;
			}
			}catch (Exception e) {
			System.out.println("Exception: "+e);
			e.printStackTrace();
		}
		return outputList;
	}
}