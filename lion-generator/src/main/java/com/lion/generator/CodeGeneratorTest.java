package com.lion.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * <p>
 * 测试生成代码
 * </p>
 *
 * @author K神
 * @date 2017/12/18
 */
public class CodeGeneratorTest {
    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        //此处的fieldName是数据库字段名称
        tableFillList.add(new TableFill("creator", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_date", FieldFill.INSERT));
        tableFillList.add(new TableFill("modifier", FieldFill.UPDATE));
        tableFillList.add(new TableFill("update_date", FieldFill.UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir("D:/develop/code/")//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(false)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(true)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        //.setKotlin(true) 是否生成 kotlin 代码
                        .setAuthor("zlzhou")//作者
                        // 自定义文件命名，注意 %s 会自动填充表实体属性！
                        // .setEntityName("%sEntity");
                        // .setMapperName("%sDao")
                        .setXmlName("%sMapper")
                        .setServiceName("%sService")
                        // .setServiceImplName("%sServiceDiy")
                        .setControllerName("%sResource")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                if (fieldType.toLowerCase().contains("tinyint")) {
                                    return DbColumnType.BOOLEAN;
                                }
                                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                            }
                        })
                        .setDriverName("com.mysql.cj.jdbc.Driver")
                        .setUsername("taxtrain")
                        .setPassword("taxtrain")
                        .setUrl("jdbc:mysql://32.1.1.251:3307/taxtrain?characterEncoding=utf8&useSSL=false&serverTimezone=UTC")
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setRestControllerStyle(true)
                        .setCapitalMode(false)// 全局大写命名
                        .setTablePrefix(new String[]{"sys_"})// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setColumnNaming(NamingStrategy.underline_to_camel)
                        // 需要生成的表
                        .setInclude(new String[]{

                                "sys_client_user",
                                "sys_enterprise_vip_apply",
                                "sys_menu",
                                "sys_menu_operation",
                                "sys_message_publish",
                                "sys_message_receive",
                                "sys_role",
                                "sys_role_menu",
                                "sys_role_operation",
                                "sys_test",
                                "sys_user",
                                "sys_user_account_error_login",
                                "sys_user_account_lock_record",
                                "sys_user_enterprise_rel",
                                "sys_user_role",
                                "sys_userverifycode",
                                "sys_view_log",
                                "sys_vip",

                        })
                        // .setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                        // 自定义实体，公共字段
//                        .setSuperEntityColumns(new String[]{"test_id"})
                        .setLogicDeleteFieldName("disable")
                        .setTableFillList(tableFillList)
                // 自定义 mapper 父类
                // .setSuperMapperClass("com.baomidou.demo.TestMapper")
                // 自定义 service 父类
                // .setSuperServiceClass("com.baomidou.demo.TestService")
                // 自定义 service 实现类父类
                // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                // 自定义 controller 父类
                // .setSuperControllerClass("com.baomidou.demo.TestController")
                // 【实体】是否生成字段常量（默认 false）
                // public static final String ID = "test_id";
                // .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                // public User setName(String name) {this.name = name; return this;}
                // .setEntityBuilderModel(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                // .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        .setModuleName("live")
                        .setParent("com.lion")// 自定义包路径
                        .setController("resources")// 这里是控制器包名，默认 web
                        .setMapper("domain.mapper")
                        .setService("domain.service")
                        .setEntity("domain.model")
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return "D:/develop/code/xml/" + tableInfo.getEntityName() + "Mapper.xml";
                    }
                }))
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                        // .setController("...");
                        // .setEntity("...");
                        // .setMapper("...");
                        // .setXml("...");
                        // .setService("...");
                        //不生成ServiceImpl
                        .setServiceImpl(null)
        );

        mpg.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}
