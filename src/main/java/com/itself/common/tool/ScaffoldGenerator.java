package com.itself.common.tool;

import com.itself.common.tool.scaffold.ScaffoldGen;

 

public class ScaffoldGenerator {

	public static void main(String[] args) {
		// arg1 子系统名
		// arg2 业务对象名,即Model,双词以上要求单词首字大写
		// arg3 表名

		ScaffoldGen generator1 = new ScaffoldGen("itself", "User", "it_user");
		generator1.execute(false);
}
}