
/**
 * Homework 4 - ITCS/ITIS 5180
 * 1. Naga Bijesh Roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */

package com.mad.homework04;

import java.io.Serializable;

public class TopicPerson implements Serializable {

	private static final long serialVersionUID = 1L;
	String title;
	@Override
	public String toString() {
		return title;
	}

	int id;

	public TopicPerson() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
