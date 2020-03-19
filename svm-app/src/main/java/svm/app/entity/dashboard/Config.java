package svm.app.entity.dashboard;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Config {
    private long id;
    private int type;
    private int width;
    private int height;
    private int index;
    private int count;
    private String mode;
	private String services;
    private String apis;
}