package com.modest.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;

public enum DoubleOperation {
	/**
	 * 大于 
	 */
	GT{

		@Override
		public boolean compare(Double a, Double b) {
			return  NumberUtils.compare(a, b)==1;
		}
	},
	/**
	 * 大于等于
	 */
	GE{

		@Override
		public boolean compare(Double a, Double b) {
			return  NumberUtils.compare(a, b)>-1;
		}
		
	},
	/**
	 * 小于
	 */
	LT{

		@Override
		public boolean compare(Double a, Double b) {
			return NumberUtils.compare(a, b)==-1;
		}
		
	},
	/**
	 * 小于等于
	 */
	LE{

		@Override
		public boolean compare(Double a, Double b) {
			return NumberUtils.compare(a, b)<1;
		}
		
	},
	/**
	 * 等于
	 */
	EQ{

		@Override
		public boolean compare(Double a, Double b) {
			return ObjectUtils.equals(a,b);
		}
		
	},
	/**
	 * 不等于
	 */
	NE{

		@Override
		public boolean compare(Double a, Double b) {
			return !(ObjectUtils.equals(a,b));
		}
		
	};
	
	public abstract boolean compare(Double a,Double b);
}
