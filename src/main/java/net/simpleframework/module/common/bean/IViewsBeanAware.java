package net.simpleframework.module.common.bean;

import java.io.Serializable;

/**
 * Licensed under the Apache License, Version 2.0
 * 
 * @author 闄堜緝(cknet@126.com, 13910090885)
 *         https://github.com/simpleframework
 *         http://www.simpleframework.net
 */
public interface IViewsBeanAware extends Serializable {

	int getViews();

	void setViews(int views);
}
