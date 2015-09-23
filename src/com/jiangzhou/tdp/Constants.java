/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.jiangzhou.tdp;

import com.jiangzhou.tdp.R;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public final class Constants {
	
	public static final boolean LOG_ENABLE = true;
	public static final String CATEGORY_IMAGE_URLS_NAME = "categories";
	public static final String MODE_CHOICE_NAME = "mode";
	public static final int MODE_CHOLICE_HONGLAN = 0;
	public static final int MODE_CHOLICE_ZUOYOU = 1;
	public static final String IMAGES_LIGHT = "light";
	public static final String CATEGORY_IMAGE_DIR = "http://106.186.22.172/tdp/";

	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
	
	public static class Extra {
		public static final String IMAGES = "com.jiangzhou.tdp.IMAGES";
		public static final String IMAGE_POSITION = "com.jiangzhou.tdp.IMAGE_POSITION";
	}
}
